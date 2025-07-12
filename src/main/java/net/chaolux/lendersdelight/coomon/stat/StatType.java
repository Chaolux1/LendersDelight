package net.chaolux.lendersdelight.coomon.stat;

public enum StatType {
    ARMOR_BOOST,ATTACK_BOOTS,SPEED_BOOST,JUMP_BOOST,ATTACK_SPEED,CRIT_CHANCE,KNOCKBACK_RESISTANCE,PASSIVE_REGEN,SWIM_SPEED;

    public String getDisplayName() {
      return this.name().toLowerCase().replace("_","");
    }

    public String getLangKey() {
        return "stat.lendersdelight." +name().toLowerCase();
    }
}
