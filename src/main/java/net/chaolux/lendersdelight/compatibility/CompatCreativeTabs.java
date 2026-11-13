package net.chaolux.lendersdelight.compatibility;

import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.RegistryObject;

public class CompatCreativeTabs {
    private final BuildCreativeModeTabContentsEvent buildCreativeModeTabContentsEvent;
    private final String string;
    public CompatCreativeTabs(BuildCreativeModeTabContentsEvent buildCreativeModeTabContentsEvent,String string) {
        this.buildCreativeModeTabContentsEvent=buildCreativeModeTabContentsEvent;
        this.string=string;
    }

    public void accept(RegistryObject<Item> itemRegistryObject) {
        if(!CompatManager.isPrefer(string)) buildCreativeModeTabContentsEvent.accept(itemRegistryObject);
    }
}
