package net.chaolux.lendersdelight.client;

import net.chaolux.lendersdelight.Config;
import net.chaolux.lendersdelight.client.gui.PlayerStatScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
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
            if(!Config.SHOW_TOOLTIP_STAT.get()) continue;
            if(minecraft.player == null || minecraft.screen != null) continue;
            minecraft.setScreen(new PlayerStatScreen());
        }
    }
}
