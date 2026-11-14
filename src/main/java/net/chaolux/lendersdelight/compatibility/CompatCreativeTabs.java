package net.chaolux.lendersdelight.compatibility;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.function.Supplier;


public class CompatCreativeTabs {
    private final BuildCreativeModeTabContentsEvent buildCreativeModeTabContentsEvent;
    private final String string;
    public CompatCreativeTabs(BuildCreativeModeTabContentsEvent buildCreativeModeTabContentsEvent,String string) {
        this.buildCreativeModeTabContentsEvent=buildCreativeModeTabContentsEvent;
        this.string=string;
    }

    public void accept(Supplier<Item> itemRegistryObject) {
        if(!CompatManager.isPrefer(string)) buildCreativeModeTabContentsEvent.accept(itemRegistryObject.get());
    }
}
