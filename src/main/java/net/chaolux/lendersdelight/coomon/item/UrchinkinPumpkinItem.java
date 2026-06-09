package net.chaolux.lendersdelight.coomon.item;

import net.chaolux.lendersdelight.coomon.utility.LEDTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.utility.TextUtils;

import javax.annotation.Nullable;
import java.util.List;

public class UrchinkinPumpkinItem extends ConsumableItem {
    private final boolean hasFoodEffectTooltip;
    private final boolean hasCustomTooltip;
    public UrchinkinPumpkinItem(Properties properties) {
        super(properties);
        this.hasFoodEffectTooltip = false;
        this.hasCustomTooltip = true;
    }

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        if(level.isClientSide) return;
        AreaEffectCloud cloud=new AreaEffectCloud(level,consumer.getX(),consumer.getY(),consumer.getZ());
        cloud.setRadius(4.0f);
        cloud.setDuration(200);
        cloud.setOwner(consumer);
        cloud.addEffect(new MobEffectInstance(MobEffects.POISON,200) {
            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });
        level.addFreshEntity(cloud);
        if(consumer instanceof Player player) {
            level.playSound(null, player.blockPosition(), SoundEvents.PUFFER_FISH_BLOW_UP,SoundSource.PLAYERS,1.0f,1.0f);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag isAdvanced) {
        if ((Boolean) Configuration.ENABLE_FOOD_EFFECT_TOOLTIP.get()) {
            if (this.hasCustomTooltip) {
                MutableComponent textEmpty = LEDTextUtils.getTranslation("tooltip." + BuiltInRegistries.ITEM.getKey(this).getPath(), new Object[0]);
                tooltip.add(textEmpty.withStyle(ChatFormatting.RED));
            }

            if (this.hasFoodEffectTooltip) {
                TextUtils.addFoodEffectTooltip(stack, tooltip::add, 1.0F, context.tickRate());
            }
        }
    }
}

