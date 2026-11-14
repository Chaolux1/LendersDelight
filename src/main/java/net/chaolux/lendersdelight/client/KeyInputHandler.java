package net.chaolux.lendersdelight.client;

import net.chaolux.lendersdelight.ServerConfig;
import net.chaolux.lendersdelight.client.gui.PlayerStatScreen;
import net.chaolux.lendersdelight.coomon.stat.StatReloadListener;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = "lendersdelight", bus = Bus.GAME,value = Dist.CLIENT)
public class KeyInputHandler {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post clientTickEvent) {
        while (ClientKeyBind.OPEN_STAT.consumeClick()) {
            Minecraft minecraft=Minecraft.getInstance();
            if(!ServerConfig.SHOW_STAT_SCREEN.get()) continue;
            if(minecraft.player == null || minecraft.screen != null) continue;
            minecraft.setScreen(new PlayerStatScreen());
        }
    }

    @SubscribeEvent
    public static void onClientPlayerNetwork(ClientPlayerNetworkEvent.LoggingOut loggingOut) {
        ClientStat.clear();
        StatReloadListener.clearClient();
    }
}
