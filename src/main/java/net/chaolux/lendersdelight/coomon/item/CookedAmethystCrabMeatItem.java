package net.chaolux.lendersdelight.coomon.item;

import net.chaolux.lendersdelight.coomon.utility.LEDTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.utility.TextUtils;

import javax.annotation.Nullable;
import java.util.List;

public class CookedAmethystCrabMeatItem extends ConsumableItem {
    private final boolean hasFoodEffectTooltip;
    private final boolean hasCustomTooltip;
    public CookedAmethystCrabMeatItem(Properties properties) {
        super(properties);
        this.hasFoodEffectTooltip = false;
        this.hasCustomTooltip = true;
    }

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        if(level.isClientSide) return;
        BlockPos.betweenClosedStream(consumer.blockPosition().offset(-10,-5,-10),consumer.blockPosition().offset(10,5,10)).forEach(pos -> {
            Block block=level.getBlockState(pos).getBlock();
            if(block == Blocks.AMETHYST_BLOCK || block == Blocks.AMETHYST_CLUSTER || block == Blocks.GLASS) {
                level.playSound(null,pos, SoundEvents.AMETHYST_BLOCK_CHIME,SoundSource.BLOCKS,0.8f,1.2f);
                if(level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.END_ROD,pos.getX() + 0.5,pos.getY() + 0.5,pos.getZ() + 0.5,5,0.3,0.3,0.3,0.01);
                }
            }
        });


        if(consumer instanceof Player player) {
            level.playSound(null, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_BREAK,SoundSource.PLAYERS,1.5f,0.6f);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        if ((Boolean) Configuration.ENABLE_FOOD_EFFECT_TOOLTIP.get()) {
            if (this.hasCustomTooltip) {
                MutableComponent textEmpty = LEDTextUtils.getTranslation("tooltip." + this, new Object[0]);
                tooltip.add(textEmpty.withStyle(ChatFormatting.BLUE));
            }

            if (this.hasFoodEffectTooltip) {
                TextUtils.addFoodEffectTooltip(stack, tooltip, 1.0F);
            }
        }
    }
}

