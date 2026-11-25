package net.chaolux.lendersdelight.compatibility;

import net.chaolux.lendersdelight.Config;
import net.neoforged.fml.ModList;

import java.util.Map;

public class CompatManager {
    private static final Map<String,String> DATAPACKS=Map.of("cataclysm_delicacies","cataclysm_delicacies_compat");
    public static Map<String,String> getDatapacks() {
        return DATAPACKS;
    }

    public static boolean isPrefer(String string) {
        if(!ModList.get().isLoaded(string)) return false;
        if(!DATAPACKS.containsKey(string)) return false;
        return Config.SPEC.isLoaded() ? Config.PREFER.get() : Config.PREFER.getDefault();
    }
}
