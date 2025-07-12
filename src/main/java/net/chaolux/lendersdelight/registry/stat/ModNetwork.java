package net.chaolux.lendersdelight.registry.stat;

import net.chaolux.lendersdelight.coomon.stst.StatSyncPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class ModNetwork {
    private static final String PROTOCOL_V="1.0";
    private static int packetId=0;
    public static final SimpleChannel INSTANCE= NetworkRegistry.newSimpleChannel(new ResourceLocation("lendersdelight","main"), () -> PROTOCOL_V,PROTOCOL_V::equals,PROTOCOL_V::equals);

    public static void register() {
        INSTANCE.registerMessage(packetId++, StatSyncPacket.class,StatSyncPacket::encode,StatSyncPacket::decode,StatSyncPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    public static void sendToClient(ServerPlayer player,StatSyncPacket packet) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player),packet);
    }
}
