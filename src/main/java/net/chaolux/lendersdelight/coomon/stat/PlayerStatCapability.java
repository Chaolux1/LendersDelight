package net.chaolux.lendersdelight.coomon.stat;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

public class PlayerStatCapability implements IPlayerStat {
    private final EnumMap<StatType, Float> stats=new EnumMap<>(StatType.class);
    private final Set<ResourceLocation> foods=new HashSet<>();

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
}
