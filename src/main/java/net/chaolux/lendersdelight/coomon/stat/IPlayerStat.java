package net.chaolux.lendersdelight.coomon.stat;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public interface IPlayerStat {
    float getStat(StatType type);
    void setStat(StatType type, float value);
    void addStat(StatType type, float amount);
    void copyFrom(IPlayerStat other);
    int getFoodUse(ResourceLocation resourceLocation);
    void setFoodUse(ResourceLocation resourceLocation,int value);
    Map<ResourceLocation,Integer> MAP();
    void setFoodUses(Map<ResourceLocation,Integer> resourceLocationIntegerMap);

    default boolean hasConsumed(ResourceLocation resourceLocation) {
        return getFoodUse(resourceLocation) > 0;
    }

    default void markConsumed(ResourceLocation resourceLocation) {
        setFoodUse(resourceLocation,Math.max(1,getFoodUse(resourceLocation)));
    }

    default Set<ResourceLocation> getConsumed() {
        return new HashSet<>(MAP().keySet());
    }

    default void setConsumed(Set<ResourceLocation> foods) {
        Map<ResourceLocation,Integer> resourceLocationIntegerMap=new HashMap<>();
        for (ResourceLocation resourceLocation : foods) {
            resourceLocationIntegerMap.put(resourceLocation,1);
        }
        setFoodUses(resourceLocationIntegerMap);
    }

    default CompoundTag saveToNBT() {
        CompoundTag tag=new CompoundTag();
        for(StatType type:StatType.values()) {
            tag.putFloat(type.name(),getStat(type));
        }
        ListTag consumedList=new ListTag();
        for(Map.Entry<ResourceLocation,Integer> entry : MAP().entrySet()) {
            if(entry.getValue() <= 0) continue;
            CompoundTag compoundTag=new CompoundTag();
            compoundTag.putString("Item",entry.getKey().toString());
            compoundTag.putInt("Use",entry.getValue());
            consumedList.add(compoundTag);
        }
        tag.put("FoodUse",consumedList);
        return tag;
    }

    default void loadFromNBT(CompoundTag tag) {
        for(StatType type:StatType.values()) {
            setStat(type,tag.contains(type.name(),Tag.TAG_ANY_NUMERIC) ? tag.getFloat(type.name()) : 0.0f);
        }
        Map<ResourceLocation,Integer> resourceLocationIntegerMap=new HashMap<>();
        if(tag.contains("FoodUse",Tag.TAG_LIST)) {
            ListTag consumedList=tag.getList("FoodUse",Tag.TAG_COMPOUND);
            for(int index=0;index<consumedList.size();index++) {
                CompoundTag compoundTag=consumedList.getCompound(index);
                ResourceLocation resourceLocation=ResourceLocation.tryParse(compoundTag.getString("Item"));
                int value=compoundTag.getInt("Use");
                if(resourceLocation != null && value > 0) resourceLocationIntegerMap.put(resourceLocation,value);
            }
        } else if(tag.contains("ConsumedList",Tag.TAG_LIST)) {
            ListTag consumedList=tag.getList("ConsumedList",Tag.TAG_STRING);
            for(int i=0;i<consumedList.size();i++) {
                ResourceLocation resourceLocation=ResourceLocation.tryParse(consumedList.getString(i));
                if(resourceLocation != null) resourceLocationIntegerMap.put(resourceLocation,1);
            }
        }
        setFoodUses(resourceLocationIntegerMap);
    }
}
