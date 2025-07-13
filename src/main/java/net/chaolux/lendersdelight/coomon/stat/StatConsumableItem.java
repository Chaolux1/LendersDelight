package net.chaolux.lendersdelight.coomon.stat;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
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
        if (!level.isClientSide && entity instanceof Player player) {
            if (!statBonus.isEmpty()) {
                level.getServer().execute(() -> {
                    LazyOptional<IPlayerStat> optional = player.getCapability(PlayerStatProvider.PLAYER_STAT);
                    if (optional.isPresent()) {
                        optional.ifPresent(stats -> {
                            for (Map.Entry<StatType, Float> entry : statBonus.entrySet()) {
                                stats.addStat(entry.getKey(), entry.getValue().floatValue());
                            }
                            PlayerStatProvider.sync(player);
                        });
                    }
                });
            }
        }
        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        int currentSize=tooltip.size();
        super.appendHoverText(stack,level,tooltip,isAdvanced);
        if(!statBonus.isEmpty()) {
            if(tooltip.size() > currentSize) {
                tooltip.add(Component.empty());
            }
            tooltip.add(Component.translatable("tooltip.lendersdelight.stat").withStyle(ChatFormatting.DARK_GREEN));
            for(Map.Entry<StatType, Float> entry:statBonus.entrySet()) {
                StatType type=entry.getKey();
                float value=entry.getValue();
                tooltip.add(Component.literal("+"+value+"% ").append(Component.translatable(type.getLangKey())).withStyle(ChatFormatting.BLUE));
            }
        }
    }
}
