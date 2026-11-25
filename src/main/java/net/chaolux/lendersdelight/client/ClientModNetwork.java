package net.chaolux.lendersdelight.client;

import net.chaolux.lendersdelight.Config;
import net.chaolux.lendersdelight.coomon.stat.ServerConfigPacket;
import net.chaolux.lendersdelight.coomon.stat.StatDefinitionPacket;
import net.chaolux.lendersdelight.coomon.stat.StatSyncPacket;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.network.ConfigSync;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(modid = "lendersdelight", bus = Bus.MOD, value = Dist.CLIENT)
public class ClientModNetwork {
    @SubscribeEvent
    public static void onPayloadRegister(RegisterPayloadHandlersEvent event) {
        event.registrar("3.0").playToClient(StatSyncPacket.TYPE,StatSyncPacket.STREAM_CODEC,ClientStatSyncHandler::handle).playToClient(StatDefinitionPacket.TYPE,StatDefinitionPacket.STREAM_CODEC,((packet, context) -> context.enqueueWork(packet::handle))).playToClient(ServerConfigPacket.TYPE,ServerConfigPacket.STREAM_CODEC,(((packet, context) -> context.enqueueWork(() -> {
            ConfigSync.receiveSyncedConfig(packet.bytes(),"lendersdelight-server.toml");
        }))));
    }
}
