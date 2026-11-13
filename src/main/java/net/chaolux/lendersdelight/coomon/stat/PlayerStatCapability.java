package net.chaolux.lendersdelight.coomon.stat;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class PlayerStatCapability implements IPlayerStat {
    private final EnumMap<StatType, Float> stats=new EnumMap<>(StatType.class);
    private final Map<ResourceLocation,Integer> foods=new HashMap<>();

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
        setStat(type,getStat(type) + amount);
    }

    @Override
    public void copyFrom(IPlayerStat other) {
        for(StatType type:StatType.values()) {
            setStat(type,other.getStat(type));
        }
        setFoodUses(other.MAP());
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
}
