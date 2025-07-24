package net.chaolux.lendersdelight.registry.stat;

import net.chaolux.lendersdelight.coomon.stat.StatSyncPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;


import java.util.Optional;

public class ModNetwork {
    public static void sendToClient(ServerPlayer player, StatSyncPacket packet) {
        PacketDistributor.sendToPlayer(player,packet);
    }
}
