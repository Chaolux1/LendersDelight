package net.chaolux.lendersdelight;

import net.chaolux.lendersdelight.coomon.stat.StatMode;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = LendersDelight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.BooleanValue ENABLE_STAT;
    public static final ForgeConfigSpec.BooleanValue SHOW_TOOLTIP_STAT;
    public static final ForgeConfigSpec.BooleanValue RESET_ON_DEATH;
    public static final ForgeConfigSpec.EnumValue<StatMode> STAT_MODE;
    public static boolean showTooltipStat=true;

    static {
        BUILDER.push("Stat");
        ENABLE_STAT=BUILDER.comment("Enable or disable stat effects.").define("enableStat",true);
        SHOW_TOOLTIP_STAT=BUILDER.comment("Show stat in food tooltips.").define("showStatTip",true);
        RESET_ON_DEATH=BUILDER.comment("Reset player stats on death.").define("resetOnDeath",true);
        STAT_MODE=BUILDER.comment("ACCUBULATE=food can stats every time").comment("ONCE=each food can give only once per player").defineEnum("statMode",StatMode.ACCUMULATE);
        BUILDER.pop();
        SPEC=BUILDER.build();
    }

    public static final ForgeConfigSpec SPEC;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        showTooltipStat=SHOW_TOOLTIP_STAT.get();
    }
}
