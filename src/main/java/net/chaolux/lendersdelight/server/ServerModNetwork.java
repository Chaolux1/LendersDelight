package net.chaolux.lendersdelight.server;

import net.chaolux.lendersdelight.client.ClientStatSyncHandler;
import net.chaolux.lendersdelight.coomon.stat.StatSyncPacket;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(modid = "lendersdelight", bus = Bus.MOD, value = Dist.DEDICATED_SERVER)
public class ServerModNetwork {
    @SubscribeEvent
    public static void onPayloadRegister(RegisterPayloadHandlersEvent event) {
        event.registrar("1.0").playToClient(StatSyncPacket.TYPE,StatSyncPacket.STREAM_CODEC, (packet,context) -> {});
    }
}
