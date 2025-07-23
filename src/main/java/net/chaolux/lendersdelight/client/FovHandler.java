package net.chaolux.lendersdelight.client;

import net.chaolux.lendersdelight.coomon.stat.IPlayerStat;
import net.chaolux.lendersdelight.coomon.stat.PlayerStatProvider;
import net.chaolux.lendersdelight.coomon.stat.StatType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "lendersdelight", value = Dist.CLIENT)
public class FovHandler {

    @SubscribeEvent
    public static void onFovUpdate(ComputeFovModifierEvent event) {
        if(!(event.getPlayer() instanceof LocalPlayer player)) return;
        boolean isSprint=player.isSprinting();
        float fov=1.0f;
        if(isSprint) {
            event.setNewFovModifier(fov * 1.15f);
        } else {
            event.setNewFovModifier(fov);
        }
    }
}
