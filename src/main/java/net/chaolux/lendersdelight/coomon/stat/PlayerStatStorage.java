package net.chaolux.lendersdelight.coomon.stat;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerStatStorage extends SavedData {
    private final Map<UUID, CompoundTag> playerStat=new HashMap<>();
    public static final String DATA_NAME="lendersdelight_player_stat";
    public static PlayerStatStorage get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(new SavedData.Factory<>(PlayerStatStorage::new,PlayerStatStorage::load),DATA_NAME);
    }

    public static PlayerStatStorage load(CompoundTag tag, HolderLookup.Provider provider) {
        PlayerStatStorage storage=new PlayerStatStorage();
        for(String key:tag.getAllKeys()) {
            storage.playerStat.put(UUID.fromString(key),tag.getCompound(key));
        }
        return storage;
    }

    public void savePlayer(Player player, IPlayerStat stat) {
        playerStat.put(player.getUUID(),stat.saveToNBT());
        setDirty();
    }

    public CompoundTag getStatData(UUID uuid) {
        return playerStat.getOrDefault(uuid,new CompoundTag());
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        for(Map.Entry<UUID,CompoundTag> entry:playerStat.entrySet()) {
            tag.put(entry.getKey().toString(),entry.getValue());
        }
        return tag;    }
}
