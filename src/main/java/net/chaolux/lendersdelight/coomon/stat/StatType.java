package net.chaolux.lendersdelight.coomon.stat;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum StatType {
    ARMOR_BOOST,ATTACK_BOOTS,SPEED_BOOST,JUMP_BOOST,ATTACK_SPEED,CRIT_CHANCE,KNOCKBACK_RESISTANCE,PASSIVE_REGEN,SWIM_SPEED;

    public String getDisplayName() {
      return this.name().toLowerCase().replace("_","");
    }

    public String getLangKey() {
        return "stat.lendersdelight." +name().toLowerCase();
    }

    public static String string(float value) {
        if(!Float.isFinite(value)) return "?";
        if(value != 0.0f && (Math.abs(value) < 0.0001f || Math.abs(value) >= 1000000.0f)) return Float.toString(value);
        return new BigDecimal(Float.toString(value)).setScale(4, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
    }

}
