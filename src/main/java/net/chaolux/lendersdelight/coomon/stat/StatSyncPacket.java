package net.chaolux.lendersdelight.coomon.stat;

import net.chaolux.lendersdelight.client.ClientStat;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;


import java.util.*;

public record StatSyncPacket(Map<StatType, Float> data, Map<ResourceLocation,Integer> foods) implements CustomPacketPayload {
    public static final ResourceLocation ID=ResourceLocation.fromNamespaceAndPath("lendersdelight","stat_sync");
    public static final Type<StatSyncPacket> TYPE=new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf,StatSyncPacket> STREAM_CODEC = StreamCodec.of((buf, packet) -> {
        buf.writeVarInt(packet.data.size());
        packet.data().forEach((statType, value) -> {
            buf.writeEnum(statType);
            buf.writeFloat(value);
        });
        buf.writeVarInt(packet.foods().size());
        packet.foods().forEach((resourceLocation, value) -> {
            buf.writeResourceLocation(resourceLocation);
            buf.writeVarInt(value);
        });
    },
            buf -> {
        int size=buf.readVarInt();
        Map<StatType,Float> map=new EnumMap<>(StatType.class);
        for(int i=0; i < size; i++) {
            map.put(buf.readEnum(StatType.class),buf.readFloat());
        }
        int consumedSize=buf.readVarInt();
        Map<ResourceLocation,Integer> resourceLocationIntegerMap=new HashMap<>();
        for(int i=0;i<consumedSize;i++) {
            ResourceLocation resourceLocation=buf.readResourceLocation();
            int value=buf.readVarInt();
            if(value > 0) resourceLocationIntegerMap.put(resourceLocation,value);
        }
        return new StatSyncPacket(map,resourceLocationIntegerMap);
    });

    @Override
    public Type<StatSyncPacket> type() {
        return TYPE;
    }
}