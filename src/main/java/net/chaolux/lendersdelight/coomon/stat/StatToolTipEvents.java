package net.chaolux.lendersdelight.coomon.stat;

import net.chaolux.lendersdelight.ServerConfig;
import net.chaolux.lendersdelight.client.ClientStatTooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = "lendersdelight", bus = Bus.GAME, value = Dist.CLIENT)
public class StatToolTipEvents {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent itemTooltipEvent) {
        if(Minecraft.getInstance().player == null || !ServerConfig.SPEC.isLoaded()) return;
        if(!ServerConfig.SHOW_TOOLTIP_STAT.get() || itemTooltipEvent.getItemStack().isEmpty() || !ServerConfig.ENABLE_STAT.get()) return;
        ResourceLocation resourceLocation= BuiltInRegistries.ITEM.getKey(itemTooltipEvent.getItemStack().getItem());
        if(resourceLocation == null) return;
        Map<StatType,Float> map=StatReloadListener.getClient(resourceLocation);
        if(map.isEmpty()) return;
        Map<StatType,Float> statTypeFloatMap=new EnumMap<>(StatType.class);
        for(var entry : map.entrySet()) {
            if(!ServerConfig.isEnableStat(entry.getKey())) continue;
            float limit=ServerConfig.multiplierValue(entry.getValue());
            if(limit > 0) statTypeFloatMap.put(entry.getKey(),limit);
        }
        if(statTypeFloatMap.isEmpty()) return;
        StatMode statMode=ServerConfig.STAT_MODE.get();
        List<Component> componentList=itemTooltipEvent.getToolTip();
        componentList.add(Component.empty());
        int value=statMode == StatMode.CAPPED ? StatReloadListener.getClientCapped(resourceLocation) : 0;
        int use=ClientStatTooltipHelper.getFoodUse(resourceLocation);
        if(statMode == StatMode.ONCE && ClientStatTooltipHelper.wasAlreadyConsumed(resourceLocation)) {
            componentList.add(Component.translatable("tooltip.lendersdelight.stat_already_obtained").withStyle(ChatFormatting.DARK_GREEN));
        } else if(statMode == StatMode.CAPPED && use >= value) {
            componentList.add(Component.translatable("tooltip.lendersdelight.stat_capped_reached").withStyle(ChatFormatting.DARK_GREEN));
        } else if(statMode == StatMode.LIMITED && ClientStatTooltipHelper.isFullyLimit(statTypeFloatMap.keySet())) {
            componentList.add(Component.translatable("tooltip.lendersdelight.stat_limit_reached").withStyle(ChatFormatting.DARK_GREEN));
        } else {
            componentList.add(Component.translatable("tooltip.lendersdelight.stat").withStyle(ChatFormatting.DARK_GREEN));
            for (var entry : statTypeFloatMap.entrySet()) {
                if(statMode == StatMode.LIMITED && ClientStatTooltipHelper.isLimitReachClient(entry.getKey())) continue;
                componentList.add(Component.literal("+" + StatType.string(entry.getValue()) + "% ").append(Component.translatable(entry.getKey().getLangKey())).withStyle(ChatFormatting.BLUE));
            }
        }
        if(statMode == StatMode.CAPPED && ServerConfig.SHOW_STAT_USE.get()) componentList.add((Screen.hasControlDown() ? Component.translatable("tooltip.lendersdelight.stat_capped_ctrl",Math.max(0,value - use),value) : Component.translatable("tooltip.lendersdelight.stat_more")).withStyle(ChatFormatting.GRAY));
    }
}
