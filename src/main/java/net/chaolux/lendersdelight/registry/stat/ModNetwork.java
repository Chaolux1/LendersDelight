package net.chaolux.lendersdelight.registry.stat;

import net.chaolux.lendersdelight.ServerConfig;
import net.chaolux.lendersdelight.coomon.stat.StatDefinitionPacket;
import net.chaolux.lendersdelight.coomon.stat.StatSyncPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class ModNetwork {
    private static final String PROTOCOL_V="3.0";
    private static int packetId=0;
    public static final SimpleChannel INSTANCE= NetworkRegistry.newSimpleChannel(new ResourceLocation("lendersdelight","main"), () -> PROTOCOL_V,PROTOCOL_V::equals,PROTOCOL_V::equals);

    public static void register() {
        INSTANCE.registerMessage(packetId++, StatSyncPacket.class,StatSyncPacket::encode,StatSyncPacket::decode,StatSyncPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        INSTANCE.registerMessage(packetId++, StatDefinitionPacket.class,StatDefinitionPacket::encode,StatDefinitionPacket::decode,StatDefinitionPacket::handle,Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        INSTANCE.registerMessage(packetId++,byte[].class,(bytes,friendlyByteBuf) -> friendlyByteBuf.writeByteArray(bytes),FriendlyByteBuf::readByteArray,(bytes, contextSupplier) -> {
            contextSupplier.get().enqueueWork(() -> setConfig(bytes));
            contextSupplier.get().setPacketHandled(true);
        },Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    public static void sendToClient(ServerPlayer player,StatSyncPacket packet) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player),packet);
    }

    public static void sendToClient(ServerPlayer serverPlayer,StatDefinitionPacket statDefinitionPacket) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer),statDefinitionPacket);
    }

    public static void sendToClient(ServerPlayer player,byte[] bytes) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player),bytes);
    }

    @OnlyIn(Dist.CLIENT)
    private static void setConfig(byte[] bytes) {
        if(Minecraft.getInstance().isLocalServer()) return;
        ModConfig modConfig= ConfigTracker.INSTANCE.fileMap().get("lendersdelight-server.toml");
        if(modConfig != null && modConfig.<ForgeConfigSpec>getSpec() == ServerConfig.SPEC) modConfig.acceptSyncedConfig(bytes);
    }
}
