package net.chaolux.lendersdelight;

import net.chaolux.lendersdelight.coomon.stat.StatMode;
import net.chaolux.lendersdelight.coomon.stat.StatType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
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
    public static final Map<StatType,ForgeConfigSpec.DoubleValue> STAT_LIMITS=new EnumMap<>(StatType.class);
    public static boolean showTooltipStat=true;

    static {
        BUILDER.push("Stat");
        ENABLE_STAT=BUILDER.comment("Enable or disable stat effects.").define("enableStat",true);
        SHOW_TOOLTIP_STAT=BUILDER.comment("Show stat in food tooltips.").define("showStatTip",true);
        RESET_ON_DEATH=BUILDER.comment("Reset player stats on death.").define("resetOnDeath",true);
        STAT_MODE=BUILDER.comment("ACCUBULATE=food can stats every time.").comment("ONCE=each food can give only once per player.").comment("LIMITED=food gives stats only Minecraft attribute is below configured limit.").defineEnum("statMode",StatMode.ACCUMULATE);
        BUILDER.pop();
        BUILDER.push("Stat Limits");
        STAT_LIMITS.put(StatType.ARMOR_BOOST,BUILDER.comment("Maximum Armor attribute value").defineInRange("maxArmor",20.0,0.0,10000.0));
        STAT_LIMITS.put(StatType.ATTACK_BOOTS,BUILDER.comment("Maximum Attack Damage attribute value").defineInRange("maxAttackDamage",20.0,0.0,10000.0));
        STAT_LIMITS.put(StatType.SPEED_BOOST,BUILDER.comment("Maximum Movement Speed attribute value").defineInRange("maxMovementSpeed",0.2,0.0,10000.0));
        STAT_LIMITS.put(StatType.JUMP_BOOST,BUILDER.comment("Maximum stored Jump Boost stat percent").defineInRange("maxJumpBoost",20.0,0.0,10000.0));
        STAT_LIMITS.put(StatType.ATTACK_SPEED,BUILDER.comment("Maximum Attack Speed attribute value").defineInRange("maxAttackSpeed",8.0,0.0,10000.0));
        STAT_LIMITS.put(StatType.CRIT_CHANCE,BUILDER.comment("Maximum stored Crit Chance stat percent").defineInRange("maxCritChance",20.0,0.0,10000.0));
        STAT_LIMITS.put(StatType.KNOCKBACK_RESISTANCE,BUILDER.comment("Maximum Knockback Resistance attribute value").defineInRange("maxKnockbackResistance",0.5,0.0,10000.0));
        STAT_LIMITS.put(StatType.PASSIVE_REGEN,BUILDER.comment("Maximum Passive Regen stat percent").defineInRange("maxPassiveRegen",20.0,0.0,10000.0));
        STAT_LIMITS.put(StatType.SWIM_SPEED,BUILDER.comment("Maximum Swim Speed attribute value").defineInRange("maxSwimSpeed",10.0,0.0,10000.0));
        BUILDER.pop();
        SPEC=BUILDER.build();
    }

    public static final ForgeConfigSpec SPEC;

    public static double getStatLimit(StatType type) {
        ForgeConfigSpec.DoubleValue doubleValue=STAT_LIMITS.get(type);
        return doubleValue == null ? Double.MAX_VALUE : doubleValue.get();
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        showTooltipStat=SHOW_TOOLTIP_STAT.get();
    }
}
