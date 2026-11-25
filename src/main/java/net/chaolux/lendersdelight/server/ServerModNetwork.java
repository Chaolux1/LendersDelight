package net.chaolux.lendersdelight.server;

import net.chaolux.lendersdelight.client.ClientStatSyncHandler;
import net.chaolux.lendersdelight.coomon.stat.ServerConfigPacket;
import net.chaolux.lendersdelight.coomon.stat.StatDefinitionPacket;
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
        event.registrar("3.0").playToClient(StatSyncPacket.TYPE,StatSyncPacket.STREAM_CODEC, (packet,context) -> {}).playToClient(StatDefinitionPacket.TYPE,StatDefinitionPacket.STREAM_CODEC,(packet, context) -> {}).playToClient(ServerConfigPacket.TYPE,ServerConfigPacket.STREAM_CODEC,(packet,context) -> {});
    }
}
