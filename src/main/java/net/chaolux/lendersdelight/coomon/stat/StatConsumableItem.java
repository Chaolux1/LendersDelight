package net.chaolux.lendersdelight.coomon.stat;

import com.mojang.logging.LogUtils;
import net.chaolux.lendersdelight.Config;
import net.chaolux.lendersdelight.client.ClientStatTooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.ForgeRegistries;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import vectorwing.farmersdelight.common.item.ConsumableItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class StatConsumableItem extends ConsumableItem {
    private final Map<StatType, Float> statBonus;

    public StatConsumableItem(Properties properties, boolean hasFoodEffectTooltip, Map<StatType, Float> statBonus) {
        super(properties, hasFoodEffectTooltip);
        this.statBonus = statBonus;
    }


    public Map<StatType, Float> getStatBonus() {
        return statBonus;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);
        if(!Config.ENABLE_STAT.get()) return result;
        if(level.isClientSide) return result;
        if(!(entity instanceof Player player)) return result;
        if(statBonus.isEmpty()) return result;
        ResourceLocation resourceLocation= ForgeRegistries.ITEMS.getKey(stack.getItem());
        if(resourceLocation == null) return result;
        LazyOptional<IPlayerStat> lazyOptional=player.getCapability(PlayerStatProvider.PLAYER_STAT);
        lazyOptional.ifPresent(stats -> {
            StatMode mode=Config.STAT_MODE.get();
            if(mode == StatMode.ONCE && stats.hasConsumed(resourceLocation)) {
                PlayerStatProvider.sync(player);
                return;
            }
            boolean addAnyStat=false;
            for(Map.Entry<StatType,Float> entry : statBonus.entrySet()) {
                if(mode == StatMode.LIMITED && isLimitReach(player,stats,entry.getKey())) continue;
                stats.addStat(entry.getKey(),entry.getValue());
                addAnyStat=true;
            }
            if(mode == StatMode.ONCE && addAnyStat) stats.markConsumed(resourceLocation);
            PlayerStatProvider.sync(player);
        });
        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        if (!Config.showTooltipStat) return;
        int currentSize = tooltip.size();
        super.appendHoverText(stack, level, tooltip, isAdvanced);
        if (statBonus.isEmpty()) return;
        if (Config.STAT_MODE.get() == StatMode.ONCE && isClientAlreadyConsumed(stack)) {
            if (tooltip.size() > currentSize) {
                tooltip.add(Component.empty());
            }
            tooltip.add(Component.translatable("tooltip.lendersdelight.stat_already_obtained").withStyle(ChatFormatting.DARK_GREEN));
            return;
        }
        if(Config.STAT_MODE.get() == StatMode.LIMITED && isClientFullyLimit()) {
            if(tooltip.size() > currentSize) {
                tooltip.add(Component.empty());
            }
            tooltip.add(Component.translatable("tooltip.lendersdelight.stat_limit_reached").withStyle(ChatFormatting.DARK_GREEN));
            return;
        }
        if (tooltip.size() > currentSize) {
            tooltip.add(Component.empty());
        }
        tooltip.add(Component.translatable("tooltip.lendersdelight.stat").withStyle(ChatFormatting.DARK_GREEN));
        for (Map.Entry<StatType, Float> entry : statBonus.entrySet()) {
            StatType type = entry.getKey();
            float value = entry.getValue();
            if(Config.STAT_MODE.get() == StatMode.LIMITED && isClientLimitReach(type)) continue;
            tooltip.add(Component.literal("+" + value + "% ").append(Component.translatable(type.getLangKey())).withStyle(ChatFormatting.BLUE));
        }
    }
    private boolean isClientAlreadyConsumed(ItemStack itemStack) {
        ResourceLocation resourceLocation=ForgeRegistries.ITEMS.getKey(itemStack.getItem());
        if(resourceLocation == null) return false;
        return DistExecutor.unsafeCallWhenOn(Dist.CLIENT,() -> () -> ClientStatTooltipHelper.wasAlreadyConsumed(resourceLocation));
    }

    private boolean isClientFullyLimit() {
        return DistExecutor.unsafeCallWhenOn(Dist.CLIENT,() -> () -> ClientStatTooltipHelper.isFullyLimit(statBonus.keySet()));
    }

    private boolean isClientLimitReach(StatType type) {
        return DistExecutor.unsafeCallWhenOn(Dist.CLIENT,() -> () -> ClientStatTooltipHelper.isLimitReachClient(type));
    }

    private boolean isLimitReach(Player player, IPlayerStat stats, StatType type) {
        double limit=Config.getStatLimit(type);
        if(type == StatType.ARMOR_BOOST) return getAttributeValue(player, Attributes.ARMOR) >= limit;
        if(type == StatType.ATTACK_BOOTS) return getAttributeValue(player, Attributes.ATTACK_DAMAGE) >= limit;
        if(type == StatType.SPEED_BOOST) return getAttributeValue(player, Attributes.MOVEMENT_SPEED) >= limit;
        if(type == StatType.ATTACK_SPEED) return getAttributeValue(player, Attributes.ATTACK_SPEED) >= limit;
        if(type == StatType.KNOCKBACK_RESISTANCE) return getAttributeValue(player, Attributes.KNOCKBACK_RESISTANCE) >= limit;
        if(type == StatType.SWIM_SPEED) return getAttributeValue(player, ForgeMod.SWIM_SPEED.get()) >= limit;
        if(type == StatType.JUMP_BOOST) return stats.getStat(type) >= limit;
        if(type == StatType.CRIT_CHANCE) return stats.getStat(type) >= limit;
        if(type == StatType.PASSIVE_REGEN) return stats.getStat(type) >= limit;
        return false;
    }

    private double getAttributeValue(Player player, Attribute attribute) {
        AttributeInstance attributeInstance=player.getAttribute(attribute);
        return attributeInstance == null ? 0.0 : attributeInstance.getValue();
    }
}
