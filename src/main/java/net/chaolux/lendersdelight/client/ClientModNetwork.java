package net.chaolux.lendersdelight.client;

import net.chaolux.lendersdelight.coomon.stat.StatSyncPacket;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(modid = "lendersdelight", bus = Bus.MOD, value = Dist.CLIENT)
public class ClientModNetwork {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        event.registrar("1.0").playToClient(StatSyncPacket.TYPE,StatSyncPacket.STREAM_CODEC,StatSyncPacket::handle);
    }
}
