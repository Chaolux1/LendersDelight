package net.chaolux.lendersdelight;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import net.chaolux.lendersdelight.coomon.stat.StatMode;
import net.chaolux.lendersdelight.coomon.stat.StatType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.BooleanValue PREFER;

    static {
        BUILDER.push("Compatibility");
        PREFER = BUILDER.comment("Prefer recipes from mods over overlapping Lender's Delight recipes.").define("prefer", true);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    public static final ModConfigSpec SPEC;
}
