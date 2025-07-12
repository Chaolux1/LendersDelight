package net.chaolux.lendersdelight.coomon.stst;

public enum StatType {
    ARMOR_BOOST,ATTACK_BOOTS,SPEED_BOOST,JUMP_BOOST,ATTACK_SPEED,CRIT_CHANCE,KNOCKBACK_RESISTANCE,PASSIVE_REGEN,SWIM_SPEED;

    public String getDisplayName() {
        return this.name().toLowerCase().replace("_","");
    }
}
