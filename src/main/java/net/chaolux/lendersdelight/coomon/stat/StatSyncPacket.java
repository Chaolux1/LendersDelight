package net.chaolux.lendersdelight.coomon.stat;

import net.chaolux.lendersdelight.client.ClientStat;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;


import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public record StatSyncPacket(Map<StatType, Float> data, Set<ResourceLocation> foods) implements CustomPacketPayload {
    public static final ResourceLocation ID=ResourceLocation.fromNamespaceAndPath("lendersdelight","stat_sync");
    public static final Type<StatSyncPacket> TYPE=new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf,StatSyncPacket> STREAM_CODEC = StreamCodec.of((buf, packet) -> {
        buf.writeVarInt(packet.data.size());
        for(Map.Entry<StatType,Float> entry:packet.data.entrySet()) {
            buf.writeEnum(entry.getKey());
            buf.writeFloat(entry.getValue());
        }
        buf.writeVarInt(packet.foods.size());
        for(ResourceLocation resourceLocation : packet.foods) {
            buf.writeResourceLocation(resourceLocation);
        }
    },
            buf -> {
        int size=buf.readVarInt();
        Map<StatType,Float> map=new EnumMap<>(StatType.class);
        for(int i=0; i < size; i++) {
            StatType type=buf.readEnum(StatType.class);
            float value=buf.readFloat();
            map.put(type,value);
        }
        int consumedSize=buf.readVarInt();
        Set<ResourceLocation> foods=new HashSet<>();
        for(int i=0;i<consumedSize;i++) {
            foods.add(buf.readResourceLocation());
        }
        return new StatSyncPacket(map,foods);
    });

    @Override
    public Type<StatSyncPacket> type() {
        return TYPE;
    }
}