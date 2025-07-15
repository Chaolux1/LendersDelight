package net.chaolux.lendersdelight.coomon.stat.client;

import net.chaolux.lendersdelight.coomon.stat.IPlayerStat;
import net.chaolux.lendersdelight.coomon.stat.PlayerStatCapability;
import net.chaolux.lendersdelight.coomon.stat.StatType;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientStat {
    private static final Map<UUID, IPlayerStat> CLIENT_STAT=new HashMap<>();
    public static IPlayerStat get(Player player) {
        return CLIENT_STAT.computeIfAbsent(player.getUUID(),id -> new PlayerStatCapability());
    }

    public static void sync(Player player, Map<StatType,Float> data) {
        IPlayerStat stat=get(player);
        data.forEach(stat::setStat);
    }
}
