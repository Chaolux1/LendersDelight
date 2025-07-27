package net.chaolux.lendersdelight.client;

import net.chaolux.lendersdelight.coomon.stat.StatSyncPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@OnlyIn(Dist.CLIENT)
public class ClientStatSyncHandler {
    public static void handle(StatSyncPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player= Minecraft.getInstance().player;
            if(player !=null) {
                ClientStat.sync(player,packet.data());
            }
        });
    }
}
