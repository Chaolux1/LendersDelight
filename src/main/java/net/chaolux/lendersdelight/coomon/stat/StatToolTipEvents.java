package net.chaolux.lendersdelight.coomon.stat;

import net.chaolux.lendersdelight.ServerConfig;
import net.chaolux.lendersdelight.client.ClientStatTooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.Map;

@EventBusSubscriber(modid = "lendersdelight", bus = Bus.GAME, value = Dist.CLIENT)
public class StatToolTipEvents {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent itemTooltipEvent) {
        if(!ServerConfig.SHOW_TOOLTIP_STAT.get() || itemTooltipEvent.getItemStack().isEmpty()) return;
        ResourceLocation resourceLocation= BuiltInRegistries.ITEM.getKey(itemTooltipEvent.getItemStack().getItem());
        if(resourceLocation == null) return;
        Map<StatType,Float> map=StatReloadListener.getClient(resourceLocation);
        if(map.isEmpty()) return;
        StatMode statMode=StatReloadListener.getStatMode();
        if(statMode == StatMode.ONCE && ClientStatTooltipHelper.wasAlreadyConsumed(resourceLocation)) {
            itemTooltipEvent.getToolTip().add(Component.empty());
            itemTooltipEvent.getToolTip().add(Component.translatable("tooltip.lendersdelight.stat_already_obtained").withStyle(ChatFormatting.DARK_GREEN));
            return;
        }
        if(statMode == StatMode.LIMITED && ClientStatTooltipHelper.isFullyLimit(map.keySet())) {
            itemTooltipEvent.getToolTip().add(Component.empty());
            itemTooltipEvent.getToolTip().add(Component.translatable("tooltip.lendersdelight.stat_limit_reached").withStyle(ChatFormatting.DARK_GREEN));
            return;
        }
        if(statMode == StatMode.CAPPED) {
            int value=StatReloadListener.getClientCapped(resourceLocation);
            int data=ClientStatTooltipHelper.getFoodUse(resourceLocation);
            if(data >= value) {
                itemTooltipEvent.getToolTip().add(Component.empty());
                itemTooltipEvent.getToolTip().add(Component.translatable("tooltip.lendersdelight.stat_capped_reached").withStyle(ChatFormatting.DARK_GREEN));
                return;
            }
        }

        itemTooltipEvent.getToolTip().add(Component.empty());
        itemTooltipEvent.getToolTip().add(Component.translatable("tooltip.lendersdelight.stat").withStyle(ChatFormatting.DARK_GREEN));
        for (Map.Entry<StatType,Float> entry : map.entrySet()) {
            if(statMode == StatMode.LIMITED && ClientStatTooltipHelper.isLimitReachClient(entry.getKey())) continue;
            itemTooltipEvent.getToolTip().add(Component.literal("+" + entry.getValue() + "% ").append(Component.translatable(entry.getKey().getLangKey())).withStyle(ChatFormatting.BLUE));
        }
    }
}
