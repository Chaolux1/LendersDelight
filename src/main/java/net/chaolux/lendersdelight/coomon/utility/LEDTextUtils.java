package net.chaolux.lendersdelight.coomon.utility;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class LEDTextUtils {
    public static MutableComponent getTranslation(String key, Object... args) {
        return Component.translatable("lendersdelight." + key, args);
    }
}
