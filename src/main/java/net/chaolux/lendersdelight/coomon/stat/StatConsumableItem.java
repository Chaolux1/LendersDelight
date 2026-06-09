package net.chaolux.lendersdelight.coomon.stat;

import com.mojang.logging.LogUtils;
import net.chaolux.lendersdelight.Config;
import net.chaolux.lendersdelight.client.ClientStat;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;
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
        if (!Config.ENABLE_STAT.get()) return result;
        if (level.isClientSide) return result;
        if (!(entity instanceof Player player)) return result;
        if (statBonus.isEmpty()) return result;
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (resourceLocation == null) return result;
        PlayerStatProvider.get(player).ifPresent(stats -> {
            StatMode mode = Config.STAT_MODE.get();
            if (mode == StatMode.ONCE && stats.hasConsumed(resourceLocation)) {
                PlayerStatProvider.sync(player);
                return;
            }
            boolean addAnyStat = false;
            for (Map.Entry<StatType, Float> entry : statBonus.entrySet()) {
                if (mode == StatMode.LIMITED && isLimitReach(player, stats, entry.getKey())) continue;
                stats.addStat(entry.getKey(), entry.getValue());
                addAnyStat = true;
            }
            if (mode == StatMode.ONCE && addAnyStat) stats.markConsumed(resourceLocation);
            PlayerStatProvider.sync(player);
        });
        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag isAdvanced) {
        if (!Config.showTooltipStat) return;
        int currentSize = tooltip.size();
        super.appendHoverText(stack, context, tooltip, isAdvanced);
        if (statBonus.isEmpty()) return;
        if (Config.STAT_MODE.get() == StatMode.ONCE && wasAlreadyConsumed(stack)) {
            if (tooltip.size() > currentSize) {
                tooltip.add(Component.empty());
            }
            tooltip.add(Component.translatable("tooltip.lendersdelight.stat_already_obtained").withStyle(ChatFormatting.DARK_GREEN));
            return;
        }
        if (Config.STAT_MODE.get() == StatMode.LIMITED && isFullyLimit()) {
            if (tooltip.size() > currentSize) {
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
            if (Config.STAT_MODE.get() == StatMode.LIMITED && isLimitReachClient(type)) continue;
            tooltip.add(Component.literal("+" + value + "% ").append(Component.translatable(type.getLangKey())).withStyle(ChatFormatting.BLUE));
        }
    }

    private boolean wasAlreadyConsumed(ItemStack itemStack) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return false;
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
        if (resourceLocation == null) return false;
        return ClientStat.get(player).hasConsumed(resourceLocation);
    }

    private boolean isFullyLimit() {
        Player player = Minecraft.getInstance().player;
        if (player == null) return false;
        IPlayerStat stats = ClientStat.get(player);
        for (StatType type : statBonus.keySet()) {
            if (!isLimitReach(player, stats, type)) {
                return false;
            }
        }
        return true;
    }

    private boolean isLimitReachClient(StatType type) {
        Player player=Minecraft.getInstance().player;
        if(player == null) return false;
        return isLimitReach(player,ClientStat.get(player),type);
    }

    private boolean isLimitReach(Player player, IPlayerStat stats, StatType type) {
        double limit=Config.getStatLimit(type);
        if(type == StatType.ARMOR_BOOST) return getAttributeValue(player, Attributes.ARMOR) >= limit;
        if(type == StatType.ATTACK_BOOTS) return getAttributeValue(player, Attributes.ATTACK_DAMAGE) >= limit;
        if(type == StatType.SPEED_BOOST) return getAttributeValue(player, Attributes.MOVEMENT_SPEED) >= limit;
        if(type == StatType.ATTACK_SPEED) return getAttributeValue(player, Attributes.ATTACK_SPEED) >= limit;
        if(type == StatType.KNOCKBACK_RESISTANCE) return getAttributeValue(player, Attributes.KNOCKBACK_RESISTANCE) >= limit;
        if(type == StatType.SWIM_SPEED) return getAttributeValue(player, NeoForgeMod.SWIM_SPEED) >= limit;
        if(type == StatType.JUMP_BOOST) return stats.getStat(type) >= limit;
        if(type == StatType.CRIT_CHANCE) return stats.getStat(type) >= limit;
        if(type == StatType.PASSIVE_REGEN) return stats.getStat(type) >= limit;
        return false;
    }

    private double getAttributeValue(Player player, Holder<Attribute> attribute) {
        AttributeInstance attributeInstance=player.getAttribute(attribute);
        return attributeInstance == null ? 0.0 : attributeInstance.getValue();
    }
}
