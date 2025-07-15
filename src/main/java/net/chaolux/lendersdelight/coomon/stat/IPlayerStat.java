package net.chaolux.lendersdelight.coomon.stat;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import java.util.Map;

public interface IPlayerStat {
    float getStat(StatType type);
    void setStat(StatType type, float value);
    void addStat(StatType type, float amount);
    void copyFrom(IPlayerStat other);
    Map<StatType,Float> getAll();

    default CompoundTag saveToNBT() {
        CompoundTag tag=new CompoundTag();
        for(StatType type:StatType.values()) {
            tag.putFloat(type.name(),getStat(type));
        }
        return tag;
    }

    default void loadFromNBT(CompoundTag tag) {
        for(StatType type:StatType.values()) {
            if(tag.contains(type.name(), Tag.TAG_FLOAT)) {
                setStat(type,tag.getFloat(type.name()));
            }
        }
    }
}
