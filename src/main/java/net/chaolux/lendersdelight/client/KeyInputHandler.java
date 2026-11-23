package net.chaolux.lendersdelight.client;

import net.chaolux.lendersdelight.ServerConfig;
import net.chaolux.lendersdelight.client.gui.PlayerStatScreen;
import net.chaolux.lendersdelight.coomon.stat.StatReloadListener;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = "lendersdelight", bus = Bus.FORGE,value = Dist.CLIENT)
public class KeyInputHandler {
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent clientTickEvent) {
        if(clientTickEvent.phase != TickEvent.Phase.END) return;
        while (ClientKeyBind.OPEN_STAT.consumeClick()) {
            Minecraft minecraft=Minecraft.getInstance();
            if(!ServerConfig.SHOW_STAT_SCREEN.get()) continue;
            if(minecraft.player == null || minecraft.screen != null || !ServerConfig.SPEC.isLoaded()) continue;
            minecraft.setScreen(new PlayerStatScreen());
        }
    }

    @SubscribeEvent
    public static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut loggingOut) {
        StatReloadListener.clearClient();
    }
}
