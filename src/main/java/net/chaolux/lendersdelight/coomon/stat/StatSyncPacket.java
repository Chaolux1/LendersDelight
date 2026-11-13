package net.chaolux.lendersdelight.coomon.stat;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.*;
import java.util.function.Supplier;

public class StatSyncPacket {
    private final Map<StatType, Float> data;
    private final Map<ResourceLocation,Integer> foods;
    public StatSyncPacket(Map<StatType,Float> data,Map<ResourceLocation,Integer> foods) {
        this.data=data;
        this.foods=foods;
    }

    public static void encode(StatSyncPacket packet, FriendlyByteBuf buf) {
        buf.writeVarInt(packet.data.size());
        for(Map.Entry<StatType,Float> entry:packet.data.entrySet()) {
            buf.writeEnum(entry.getKey());
            buf.writeFloat(entry.getValue());
        }
        buf.writeVarInt(packet.foods.size());
        for(Map.Entry<ResourceLocation,Integer> entry : packet.foods.entrySet()) {
            buf.writeResourceLocation(entry.getKey());
            buf.writeVarInt(entry.getValue());
        }
    }

    public static StatSyncPacket decode(FriendlyByteBuf buf) {
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
    }

    public static void handle(StatSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> applyToClient(packet.data,packet.foods));
        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void applyToClient(Map<StatType,Float> map, Map<ResourceLocation,Integer> foods) {
        Player player= Minecraft.getInstance().player;
        if(player==null) return;
        player.getCapability(PlayerStatProvider.PLAYER_STAT).ifPresent(cap -> {
            for(Map.Entry<StatType,Float> entry : map.entrySet()) {
                cap.setStat(entry.getKey(),entry.getValue());
            }
            cap.setFoodUses(foods);
        });
    }
}
