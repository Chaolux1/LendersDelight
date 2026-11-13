package net.chaolux.lendersdelight.coomon.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.ConsumableItem;

public class VoidPopsicle extends ConsumableItem {
    public VoidPopsicle(Properties properties) {
        super(properties);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        consumer.removeEffect(BuiltInRegistries.MOB_EFFECT.get(new ResourceLocation("cataclysm","abyssal_burn")));
    }
}
