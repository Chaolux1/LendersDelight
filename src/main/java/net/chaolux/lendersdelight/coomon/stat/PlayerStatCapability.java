package net.chaolux.lendersdelight.coomon.stat;

import net.chaolux.lendersdelight.registry.stat.ModNetwork;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerStatCapability implements IPlayerStat, INBTSerializable<CompoundTag> {
    private final EnumMap<StatType, Float> stats=new EnumMap<>(StatType.class);
    private final Set<ResourceLocation> foods=new HashSet<>();
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
        setConsumed(other.getConsumed());
    }

    @Override
    public Map<StatType,Float> getAll() {
        return stats;
    }

    @Override
    public boolean hasConsumed(ResourceLocation resourceLocation) {
        return foods.contains(resourceLocation);
    }

    @Override
    public void markConsumed(ResourceLocation resourceLocation) {
        foods.add(resourceLocation);
    }

    @Override
    public Set<ResourceLocation> getConsumed() {
        return new HashSet<>(foods);
    }

    @Override
    public void setConsumed(Set<ResourceLocation> resourceLocations) {
        foods.clear();
        foods.addAll(resourceLocations);
    }

    public CompoundTag saveNBT() {
        return saveToNBT();
    }

    public void loadNBT(CompoundTag tag) {
        loadFromNBT(tag);
    }

    public void sync(Player player) {
        if(player instanceof ServerPlayer serverPlayer) {
            if(!serverPlayer.connection.isAcceptingMessages()) return;
            ModNetwork.sendToClient(serverPlayer,new StatSyncPacket(getAll(),getConsumed()));
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
