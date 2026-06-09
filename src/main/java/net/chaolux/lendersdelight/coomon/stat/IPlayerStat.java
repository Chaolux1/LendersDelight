package net.chaolux.lendersdelight.coomon.stat;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface IPlayerStat {
    float getStat(StatType type);
    void setStat(StatType type, float value);
    void addStat(StatType type, float amount);
    void copyFrom(IPlayerStat other);
    boolean hasConsumed(ResourceLocation resourceLocation);
    void markConsumed(ResourceLocation resourceLocation);
    Set<ResourceLocation> getConsumed();
    void setConsumed(Set<ResourceLocation> foods);

    default CompoundTag saveToNBT() {
        CompoundTag tag=new CompoundTag();
        for(StatType type:StatType.values()) {
            tag.putFloat(type.name(),getStat(type));
        }
        ListTag consumedList=new ListTag();
        for(ResourceLocation resourceLocation : getConsumed()) {
            consumedList.add(StringTag.valueOf(resourceLocation.toString()));
        }
        tag.put("ConsumedList",consumedList);
        return tag;
    }

    default void loadFromNBT(CompoundTag tag) {
        for(StatType type:StatType.values()) {
            if(tag.contains(type.name(), Tag.TAG_FLOAT)) {
                setStat(type,tag.getFloat(type.name()));
            }
        }
        if(tag.contains("ConsumedList",Tag.TAG_LIST)) {
            Set<ResourceLocation> foods=new HashSet<>();
            ListTag consumedList=tag.getList("ConsumedList",Tag.TAG_STRING);
            for(int i=0;i<consumedList.size();i++) {
                foods.add(new ResourceLocation(consumedList.getString(i)));
            }
            setConsumed(foods);
        }
    }
}
