package net.chaolux.lendersdelight.coomon.stat;

import net.chaolux.lendersdelight.ServerConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class StatDefinitionPacket implements CustomPacketPayload {
    private final Map<ResourceLocation,Map<StatType,Float>> map;
    private final Map<ResourceLocation,Integer> resourceLocationIntegerMap;
    public static final Type<StatDefinitionPacket> TYPE=new Type<>(ResourceLocation.fromNamespaceAndPath("lendersdelight","stat_definition"));
    public static final StreamCodec<RegistryFriendlyByteBuf,StatDefinitionPacket> STREAM_CODEC=StreamCodec.of((buffer, packet) -> encode(packet,buffer),StatDefinitionPacket::decode);
    public StatDefinitionPacket(Map<ResourceLocation,Map<StatType,Float>> map, Map<ResourceLocation,Integer> resourceLocationIntegerMap) {
        this.map=map;
        this.resourceLocationIntegerMap=resourceLocationIntegerMap;
    }

    public static StatDefinitionPacket from() {
        return new StatDefinitionPacket(StatReloadListener.getServerStat(),StatReloadListener.getServerCappedOverride());
    }

    public static void encode(StatDefinitionPacket statDefinitionPacket, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeVarInt(statDefinitionPacket.map.size());
        for (Map.Entry<ResourceLocation,Map<StatType,Float>> entry : statDefinitionPacket.map.entrySet()) {
            friendlyByteBuf.writeResourceLocation(entry.getKey());
            friendlyByteBuf.writeVarInt(entry.getValue().size());
            for (Map.Entry<StatType,Float> statTypeFloatEntry : entry.getValue().entrySet()) {
                friendlyByteBuf.writeEnum(statTypeFloatEntry.getKey());
                friendlyByteBuf.writeFloat(statTypeFloatEntry.getValue());
            }
        }
        friendlyByteBuf.writeVarInt(statDefinitionPacket.resourceLocationIntegerMap.size());
        for(Map.Entry<ResourceLocation,Integer> entry : statDefinitionPacket.resourceLocationIntegerMap.entrySet()) {
            friendlyByteBuf.writeResourceLocation(entry.getKey());
            friendlyByteBuf.writeVarInt(entry.getValue());
        }
    }

    public static StatDefinitionPacket decode(FriendlyByteBuf friendlyByteBuf) {
        int limit=friendlyByteBuf.readVarInt();
        if(limit < 0 || limit > 10000) throw new IllegalArgumentException("Invalid stat limit");
        Map<ResourceLocation,Map<StatType,Float>> resourceLocationMapMap=new HashMap<>();
        for(int index=0;index < limit; index++) {
            ResourceLocation resourceLocation=friendlyByteBuf.readResourceLocation();
            int values=friendlyByteBuf.readVarInt();
            if(values < 0 || values > StatType.values().length) throw new IllegalArgumentException("Invalid values");
            EnumMap<StatType,Float> floatEnumMap=new EnumMap<>(StatType.class);
            for (int indexValue=0;indexValue < values;indexValue++) {
                floatEnumMap.put(friendlyByteBuf.readEnum(StatType.class),friendlyByteBuf.readFloat());
            }
            resourceLocationMapMap.put(resourceLocation,floatEnumMap);
        }
        int data=friendlyByteBuf.readVarInt();
        Map<ResourceLocation,Integer> integerMap=new HashMap<>();
        for(int index=0;index < data;index++) {
            ResourceLocation resourceLocation=friendlyByteBuf.readResourceLocation();
            int use=friendlyByteBuf.readVarInt();
            integerMap.put(resourceLocation,use);
        }
        return new StatDefinitionPacket(resourceLocationMapMap,integerMap);
    }

    public void handle() {
        StatReloadListener.setLimit(map,resourceLocationIntegerMap);
    }

    @Override
    public Type<StatDefinitionPacket> type() {
        return TYPE;
    }

}
