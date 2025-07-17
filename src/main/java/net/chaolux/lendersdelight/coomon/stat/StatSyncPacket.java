package net.chaolux.lendersdelight.coomon.stat;

import com.mojang.logging.LogUtils;
import net.chaolux.lendersdelight.coomon.stat.client.ClientStat;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.slf4j.Logger;


import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public record StatSyncPacket(Map<StatType, Float> data) implements CustomPacketPayload {
    public static final ResourceLocation ID=ResourceLocation.fromNamespaceAndPath("lendersdelight","stat_sync");
    public static final Type<StatSyncPacket> TYPE=new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf,StatSyncPacket> STREAM_CODEC = StreamCodec.of((buf, packet) -> {
        buf.writeVarInt(packet.data.size());
        for(Map.Entry<StatType,Float> entry:packet.data.entrySet()) {
            buf.writeEnum(entry.getKey());
            buf.writeFloat(entry.getValue());
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
        return new StatSyncPacket(map);
    });

    @Override
    public Type<StatSyncPacket> type() {
        return TYPE;
    }

    public static void handle(StatSyncPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player=Minecraft.getInstance().player;
            if(player !=null) {
                ClientStat.sync(player,packet.data());
            }
        });
    }
}