package net.chaolux.lendersdelight.coomon.stat;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ServerConfigPacket(byte[] bytes) implements CustomPacketPayload {
    public static final Type<ServerConfigPacket> TYPE=new Type<>(ResourceLocation.fromNamespaceAndPath("lendersdelight","server_config_sync"));
    public static final StreamCodec<RegistryFriendlyByteBuf,ServerConfigPacket> STREAM_CODEC=StreamCodec.of(((buffer, value) -> buffer.writeByteArray(value.bytes)),buffer -> new ServerConfigPacket(buffer.readByteArray()));

    @Override
    public Type<ServerConfigPacket> type() {
        return TYPE;
    }
}
