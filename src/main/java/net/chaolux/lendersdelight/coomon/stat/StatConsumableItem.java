package net.chaolux.lendersdelight.coomon.stat;

import com.mojang.logging.LogUtils;
import net.chaolux.lendersdelight.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;
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
            if(Config.STAT_MODE.get() == StatMode.ONCE && stats.hasConsumed(resourceLocation)) {
                PlayerStatProvider.sync(player);
                return;
            }
            for(Map.Entry<StatType,Float> entry : statBonus.entrySet()) {
                stats.addStat(entry.getKey(),entry.getValue());
            }
            if(Config.STAT_MODE.get() == StatMode.ONCE) stats.markConsumed(resourceLocation);
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
        if (Config.STAT_MODE.get() == StatMode.ONCE && wasAlreadyConsumed(stack)) {
            if (tooltip.size() > currentSize) {
                tooltip.add(Component.empty());
            }
            tooltip.add(Component.translatable("tooltip.lendersdelight.stat_already_obtained").withStyle(ChatFormatting.DARK_GREEN));
            return;
        }
        if (tooltip.size() > currentSize) {
            tooltip.add(Component.empty());
        }
        tooltip.add(Component.translatable("tooltip.lendersdelight.stat").withStyle(ChatFormatting.DARK_GREEN));
        for (Map.Entry<StatType, Float> entry : statBonus.entrySet()) {
            StatType type = entry.getKey();
            float value = entry.getValue();
            tooltip.add(Component.literal("+" + value + "% ").append(Component.translatable(type.getLangKey())).withStyle(ChatFormatting.BLUE));
        }
    }
    private boolean wasAlreadyConsumed(ItemStack itemStack) {
        Player player= Minecraft.getInstance().player;
        if(player == null) return false;
        ResourceLocation resourceLocation=ForgeRegistries.ITEMS.getKey(itemStack.getItem());
        if(resourceLocation == null) return false;
        return player.getCapability(PlayerStatProvider.PLAYER_STAT).map(stats -> stats.hasConsumed(resourceLocation)).orElse(false);
    }
}
