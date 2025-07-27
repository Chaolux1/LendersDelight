package net.chaolux.lendersdelight.coomon.stat;

import net.chaolux.lendersdelight.registry.stat.ModNetwork;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerStatCapability implements IPlayerStat, INBTSerializable<CompoundTag> {
    private final EnumMap<StatType, Float> stats=new EnumMap<>(StatType.class);
    private static final Map<UUID,PlayerStatCapability> CACHE=new ConcurrentHashMap<>();
    public static PlayerStatCapability getOrCreate(Player player) {
        return CACHE.computeIfAbsent(player.getUUID(),uuid -> new PlayerStatCapability());
    }

    public static void clear(Player player) {
        CACHE.remove(player.getUUID());
    }

    public static void clearAll() {
        CACHE.clear();
    }

    public PlayerStatCapability() {
        for(StatType type:StatType.values()) {
            stats.put(type, 0.0f);
        }
    }

    @Override
    public float getStat(StatType type) {
        return stats.getOrDefault(type, 0.0f);
    }

    @Override
    public void setStat(StatType type, float value) {
        stats.put(type,value);
    }

    @Override
    public void addStat(StatType type, float amount) {
        stats.put(type,getStat(type)+amount);
    }

    @Override
    public void copyFrom(IPlayerStat other) {
        for(StatType type:StatType.values()) {
            setStat(type,other.getStat(type));
        }
    }

    @Override
    public Map<StatType,Float> getAll() {
        return stats;
    }

    public CompoundTag saveNBT() {
        CompoundTag tag=new CompoundTag();
        for(StatType type:StatType.values()) {
            tag.putFloat(type.name(),getStat(type));
        }
        return tag;
    }

    public void loadNBT(CompoundTag tag) {
        for(StatType type:StatType.values()) {
            if(tag.contains(type.name(), Tag.TAG_FLOAT)) {
                setStat(type,tag.getFloat(type.name()));
            }
        }
    }

    public void sync(Player player) {
        if(player instanceof ServerPlayer serverPlayer) {
            if(!serverPlayer.connection.isAcceptingMessages()) return;
            ModNetwork.sendToClient(serverPlayer,new StatSyncPacket(getAll()));
        }
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return saveToNBT();
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        loadFromNBT(nbt);
    }
}
