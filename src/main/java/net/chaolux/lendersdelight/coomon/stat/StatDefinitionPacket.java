package net.chaolux.lendersdelight.coomon.stat;

import net.chaolux.lendersdelight.Config;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class StatDefinitionPacket {
    private final Map<ResourceLocation,Map<StatType,Float>> map;
    private final StatMode statMode;
    private final Map<StatType,Double> statTypeDoubleMap;
    public StatDefinitionPacket(Map<ResourceLocation,Map<StatType,Float>> map,StatMode statMode,Map<StatType,Double> statTypeDoubleMap) {
        this.map=map;
        this.statMode=statMode;
        this.statTypeDoubleMap=statTypeDoubleMap;
    }

    public static StatDefinitionPacket from() {
        EnumMap<StatType,Double> enumMap=new EnumMap<>(StatType.class);
        for(StatType statType : StatType.values()) {
            enumMap.put(statType, Config.getStatLimit(statType));
        }
        return new StatDefinitionPacket(StatReloadListener.getServerStat(),Config.STAT_MODE.get(),enumMap);
    }

    public static void encode(StatDefinitionPacket statDefinitionPacket, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeEnum(statDefinitionPacket.statMode);
        friendlyByteBuf.writeVarInt(statDefinitionPacket.statTypeDoubleMap.size());
        for (Map.Entry<StatType,Double> entry : statDefinitionPacket.statTypeDoubleMap.entrySet()) {
            friendlyByteBuf.writeEnum(entry.getKey());
            friendlyByteBuf.writeDouble(entry.getValue());
        }
        friendlyByteBuf.writeVarInt(statDefinitionPacket.map.size());
        for (Map.Entry<ResourceLocation,Map<StatType,Float>> entry : statDefinitionPacket.map.entrySet()) {
            friendlyByteBuf.writeResourceLocation(entry.getKey());
            friendlyByteBuf.writeVarInt(entry.getValue().size());
            for (Map.Entry<StatType,Float> statTypeFloatEntry : entry.getValue().entrySet()) {
                friendlyByteBuf.writeEnum(statTypeFloatEntry.getKey());
                friendlyByteBuf.writeFloat(statTypeFloatEntry.getValue());
            }
        }
    }

    public static StatDefinitionPacket decode(FriendlyByteBuf friendlyByteBuf) {
        StatMode mode=friendlyByteBuf.readEnum(StatMode.class);
        EnumMap<StatType,Double> enumMap=new EnumMap<>(StatType.class);
        int limit=friendlyByteBuf.readVarInt();
        if(limit < 0 || limit > StatType.values().length) throw new IllegalArgumentException("Invalid stat limit");
        for(int index=0;index < limit;index++) {
            enumMap.put(friendlyByteBuf.readEnum(StatType.class),friendlyByteBuf.readDouble());
        }
        int value=friendlyByteBuf.readVarInt();
        Map<ResourceLocation,Map<StatType,Float>> resourceLocationMapMap=new HashMap<>();
        for(int index=0;index < value; index++) {
            ResourceLocation resourceLocation=friendlyByteBuf.readResourceLocation();
            int values=friendlyByteBuf.readVarInt();
            if(values < 0 || values > StatType.values().length) throw new IllegalArgumentException("Invalid values");
            EnumMap<StatType,Float> floatEnumMap=new EnumMap<>(StatType.class);
            for (int indexValue=0;indexValue < values;indexValue++) {
                floatEnumMap.put(friendlyByteBuf.readEnum(StatType.class),friendlyByteBuf.readFloat());
            }
            resourceLocationMapMap.put(resourceLocation,floatEnumMap);
        }
        return new StatDefinitionPacket(resourceLocationMapMap,mode,enumMap);
    }

    public static void handle(StatDefinitionPacket statDefinitionPacket, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> StatReloadListener.setLimit(statDefinitionPacket.map,statDefinitionPacket.statMode,statDefinitionPacket.statTypeDoubleMap));
        contextSupplier.get().setPacketHandled(true);
    }
}
