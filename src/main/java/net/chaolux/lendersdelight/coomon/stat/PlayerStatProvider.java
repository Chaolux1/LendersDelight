package net.chaolux.lendersdelight.coomon.stat;

import com.mojang.logging.LogUtils;
import net.chaolux.lendersdelight.registry.stat.ModCapabilities;
import net.chaolux.lendersdelight.registry.stat.ModNetwork;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class PlayerStatProvider {
    public static Optional<IPlayerStat> get(Player player) {
        LOGGER.debug("getCapability call for player: {}",player.getName().getString());
        IPlayerStat cap=player.getCapability(ModCapabilities.PLAYER_STAT);
        if(cap == null) {
            LOGGER.debug("Capability is NULL for player {}",player.getName().getString());
        } else {
            LOGGER.debug("Capability call for player {}",player.getName().getString());
        }
        return cap !=null ? Optional.of(cap):Optional.empty();
    }
    private static final Logger LOGGER= LogUtils.getLogger();

    public static void sync(Player player) {
        if(!(player instanceof ServerPlayer serverPlayer)) return;
        get(player).ifPresent(cap -> {
            LOGGER.debug("Final stat map before sync: {}",cap.getAll());
            StatSyncPacket packet=new StatSyncPacket(cap.getAll());
            ModNetwork.sendToClient(serverPlayer, packet);
        });
    }
}
