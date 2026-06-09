package net.chaolux.lendersdelight.coomon.stat;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class StatSyncPacket {
    private final Map<StatType, Float> data;
    private final Set<ResourceLocation> foods;
    public StatSyncPacket(Map<StatType,Float> data,Set<ResourceLocation> foods) {
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
        for(ResourceLocation resourceLocation : packet.foods) {
            buf.writeResourceLocation(resourceLocation);
        }
    }

    public static StatSyncPacket decode(FriendlyByteBuf buf) {
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
    }

    public static void handle(StatSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> applyToClient(packet.data,packet.foods));
        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void applyToClient(Map<StatType,Float> map, Set<ResourceLocation> foods) {
        Player player= Minecraft.getInstance().player;
        if(player==null) return;
        player.getCapability(PlayerStatProvider.PLAYER_STAT).ifPresent(cap -> {
            for(Map.Entry<StatType,Float> entry:map.entrySet()) {
                cap.setStat(entry.getKey(),entry.getValue());
            }
            cap.setConsumed(foods);
        });
    }
}
