package net.chaolux.lendersdelight.registry.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class AbyssalKnife extends KnifeItem {
    public AbyssalKnife(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
}
