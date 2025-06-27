package net.chaolux.lendersdelight.coomon.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class AbyssalKnife extends KnifeItem {
    public AbyssalKnife(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
}
