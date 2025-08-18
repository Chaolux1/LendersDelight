package net.chaolux.lendersdelight.coomon.item;

import net.chaolux.lendersdelight.coomon.utility.LEDTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.utility.TextUtils;

import javax.annotation.Nullable;
import java.util.List;

public class KoboletonPumpkinItem extends ConsumableItem {
    private final boolean hasFoodEffectTooltip;
    private final boolean hasCustomTooltip;
    public KoboletonPumpkinItem(Properties properties) {
        super(properties);
        this.hasFoodEffectTooltip = false;
        this.hasCustomTooltip = true;
    }

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        if(level.isClientSide) return;
        double radius=12.0;
        List<LivingEntity> mobs=level.getEntitiesOfClass(LivingEntity.class,consumer.getBoundingBox().inflate(radius),entity -> entity !=consumer && !(entity instanceof Player) && entity.isAlive() && !(entity instanceof ArmorStand));
        for(LivingEntity livingEntity : mobs) {
            for(InteractionHand hand : InteractionHand.values()) {
                ItemStack itemStack=livingEntity.getItemInHand(hand);
                if(!itemStack.isEmpty()) {
                    livingEntity.setItemInHand(hand,ItemStack.EMPTY);
                    livingEntity.spawnAtLocation(itemStack);
                }
            }
        }
        if(consumer instanceof Player player) {
            level.playSound(null, player.blockPosition(), SoundEvents.BONE_BLOCK_BREAK,SoundSource.PLAYERS,1.2f,0.8f);
        }
        if(level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.ITEM_SNOWBALL,consumer.getX(),consumer.getY() + 1,consumer.getZ(),20,1,1,1,0.1);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag isAdvanced) {
        if ((Boolean) Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            if (this.hasCustomTooltip) {
                MutableComponent textEmpty = LEDTextUtils.getTranslation("tooltip." + BuiltInRegistries.ITEM.getKey(this).getPath(), new Object[0]);
                tooltip.add(textEmpty.withStyle(ChatFormatting.BLUE));
            }

            if (this.hasFoodEffectTooltip) {
                TextUtils.addFoodEffectTooltip(stack, tooltip::add, 1.0F, context.tickRate());
            }
        }
    }
}

