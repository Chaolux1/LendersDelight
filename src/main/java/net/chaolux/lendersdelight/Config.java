package net.chaolux.lendersdelight;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = LendersDelight.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.BooleanValue ENABLE_STAT;
    public static final ModConfigSpec.BooleanValue SHOW_TOOLTIP_STAT;
    public static boolean showTooltipStat=true;

    static {
        BUILDER.push("Stat");
        ENABLE_STAT=BUILDER.comment("Enable or disable stat effects.").define("enableStat",true);
        SHOW_TOOLTIP_STAT=BUILDER.comment("Show stat in food tooltips.").define("showStatTip",true);
        BUILDER.pop();
        SPEC=BUILDER.build();
    }

    public static final ModConfigSpec SPEC;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        showTooltipStat=SHOW_TOOLTIP_STAT.get();
    }
}
