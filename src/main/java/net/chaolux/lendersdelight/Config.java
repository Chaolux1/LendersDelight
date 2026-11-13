package net.chaolux.lendersdelight;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.BooleanValue PREFER;

    static {
        BUILDER.push("Compatibility");
        PREFER=BUILDER.comment("Prefer recipes from mods over overlapping Lender's Delight recipes.").define("prefer",true);
        BUILDER.pop();
        SPEC=BUILDER.build();
    }

    public static final ForgeConfigSpec SPEC;
}
