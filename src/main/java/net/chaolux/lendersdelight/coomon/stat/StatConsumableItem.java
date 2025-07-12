package net.chaolux.lendersdelight.coomon.stat;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import org.slf4j.Logger;
import vectorwing.farmersdelight.common.item.ConsumableItem;

import java.util.Map;

public class StatConsumableItem extends ConsumableItem {
    private final Map<StatType, Float> statBonus;

    public StatConsumableItem(Properties properties, boolean hasFoodEffectTooltip, Map<StatType, Float> statBonus) {
        super(properties, hasFoodEffectTooltip);
        this.statBonus = statBonus;
        LOGGER.debug("StatConsumableItem create with stat: {}", statBonus);
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    public Map<StatType, Float> getStatBonus() {
        return statBonus;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        LOGGER.debug("finishUsingItem call for: {}", stack.getItem());
        ItemStack result = super.finishUsingItem(stack, level, entity);
        LOGGER.debug("super.finishUsingItem return {}", result.getItem());
        if (!level.isClientSide && entity instanceof Player player) {
            LOGGER.debug("Stat bonus to apply: {}", statBonus);
            if (statBonus.isEmpty()) {
                LOGGER.debug("No stat bonus to apply for {}", stack.getItem());
            } else {
                level.getServer().execute(() -> {
                    LazyOptional<IPlayerStat> optional = player.getCapability(PlayerStatProvider.PLAYER_STAT);
                    if (optional.isPresent()) {
                        optional.ifPresent(stats -> {
                            LOGGER.debug("Capability find for player {}", player.getName().getString());
                            for (Map.Entry<StatType, Float> entry : statBonus.entrySet()) {
                                LOGGER.debug("Apply stat {} + {}", entry.getKey(), entry.getValue());
                                stats.addStat(entry.getKey(), entry.getValue().floatValue());
                            }
                            PlayerStatProvider.sync(player);
                            LOGGER.debug("Stats apply and sync for player {}", player.getName().getString());
                        });
                    } else {
                        LOGGER.debug("Capability NOT present for player {}", player.getName().getString());
                    }
                });
            }
        }
        return result;
    }
}
