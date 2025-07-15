package net.chaolux.lendersdelight.coomon.stat;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
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
    private static final Logger LOGGER=LogUtils.getLogger();

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        LOGGER.debug("finishUsingItem call for: {}",stack.getItem());
        ItemStack result = super.finishUsingItem(stack, level, entity);
        LOGGER.debug("super.finishUsingItem return {}",result.getItem());
        if (!level.isClientSide && entity instanceof Player player) {
            LOGGER.debug("Detect server-side use player: {}", player.getName().getString());
            LOGGER.debug("Attempt to apply stat: {}", statBonus);
            if (statBonus.isEmpty()) {
                LOGGER.debug("No stat bonus to apply for {}", stack.getItem());
                return result;
            }
            LOGGER.debug("Attempt to apply stat: {}", statBonus);
            level.getServer().execute(() -> {
                var optional = PlayerStatProvider.get(player);
                if (optional.isPresent()) {
                    PlayerStatCapability cap= (PlayerStatCapability) optional.get();
                    LOGGER.debug("Capability good");
                    LOGGER.debug("Stat before apply bonus: {}", cap.getAll());

                    for (Map.Entry<StatType, Float> entry : statBonus.entrySet()) {
                        StatType type = entry.getKey();
                        float value = entry.getValue();
                        LOGGER.debug("Apply stat {} += {}", type, value);
                        cap.addStat(type, value);
                    }
                    LOGGER.debug("Stat after apply bonus: {}", cap.getAll());
                    cap.sync(player);
                    LOGGER.debug("Stats sync sent for player {}", player.getName().getString());
                } else {
                    LOGGER.debug("Capability NOT present for player {}", player.getName().getString());
                }
            });
        } else {
            LOGGER.debug("finishUsingItem skip");
        }
        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag isAdvanced) {
        int currentSize=tooltip.size();
        super.appendHoverText(stack,context,tooltip,isAdvanced);
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
