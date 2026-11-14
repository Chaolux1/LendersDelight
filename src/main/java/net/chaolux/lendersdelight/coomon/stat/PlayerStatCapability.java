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
    private final Map<ResourceLocation,Integer> foods=new HashMap<>();
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
        stats.put(type,Math.max(0.0f,value));
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
        setFoodUses(other.MAP());
    }

    @Override
    public Map<StatType,Float> getAll() {
        return stats;
    }

    @Override
    public int getFoodUse(ResourceLocation resourceLocation) {
        return foods.getOrDefault(resourceLocation,0);
    }

    @Override
    public void setFoodUse(ResourceLocation resourceLocation,int value) {
        if(value <= 0) {
            foods.remove(resourceLocation);
        } else {
            foods.put(resourceLocation,value);
        }
    }

    @Override
    public Map<ResourceLocation,Integer> MAP() {
        return new HashMap<>(foods);
    }

    @Override
    public void setFoodUses(Map<ResourceLocation,Integer> resourceLocationIntegerMap) {
        foods.clear();
        resourceLocationIntegerMap.forEach((resourceLocation,value) -> {
            if(resourceLocation != null && value != null && value > 0) foods.put(resourceLocation,value);
        });
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
            ModNetwork.sendToClient(serverPlayer,new StatSyncPacket(getAll(),MAP()));
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
