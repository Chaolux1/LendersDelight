package net.chaolux.lendersdelight.coomon.stat;

import net.chaolux.lendersdelight.ServerConfig;
import net.chaolux.lendersdelight.registry.stat.ModNetwork;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

import java.util.Map;

@EventBusSubscriber(modid = "lendersdelight", bus = Bus.GAME)
public class StatEvents {
    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent addReloadListenerEvent) {
        addReloadListenerEvent.addListener(new StatReloadListener());
    }

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent onDatapackSyncEvent) {
        StatDefinitionPacket statDefinitionPacket=StatDefinitionPacket.from();
        onDatapackSyncEvent.getRelevantPlayers().forEach(serverPlayer -> ModNetwork.sendToClient(serverPlayer,statDefinitionPacket));
    }

    @SubscribeEvent
    public static void onFinish(LivingEntityUseItemEvent.Finish finish) {
        if(!(finish.getEntity() instanceof ServerPlayer serverPlayer)) return;
        if(!ServerConfig.ENABLE_STAT.get()) return;
        ItemStack itemStack=finish.getItem();
        if(itemStack.isEmpty() || itemStack.getFoodProperties(serverPlayer) == null) return;
        ResourceLocation resourceLocation= BuiltInRegistries.ITEM.getKey(itemStack.getItem());
        if(resourceLocation == null) return;
        Map<StatType,Float> statTypeFloatMap=StatReloadListener.getServer(resourceLocation);
        if(statTypeFloatMap.isEmpty()) return;
        PlayerStatProvider.get(serverPlayer).ifPresent(stat -> {
            StatMode statMode= ServerConfig.STAT_MODE.get();
            if(statMode == StatMode.ONCE && stat.hasConsumed(resourceLocation)) return;
            if(statMode == StatMode.CAPPED && stat.getFoodUse(resourceLocation) >= StatReloadListener.getServerCapped(resourceLocation)) return;
            boolean once=false;
            for(Map.Entry<StatType,Float> statTypeFloatEntry : statTypeFloatMap.entrySet()) {
                if(statMode == StatMode.LIMITED && isLimit(serverPlayer,stat,statTypeFloatEntry.getKey())) continue;
                stat.addStat(statTypeFloatEntry.getKey(),statTypeFloatEntry.getValue());
                once=true;
            }
            if(once) {
                if(statMode == StatMode.ONCE) stat.markConsumed(resourceLocation);
                if(statMode == StatMode.CAPPED) stat.setFoodUse(resourceLocation,stat.getFoodUse(resourceLocation) + 1);
                PlayerStatProvider.sync(serverPlayer);
            }
        });
    }

    private static boolean isLimit(Player player,IPlayerStat iPlayerStat,StatType statType) {
        double limit= ServerConfig.getStatLimit(statType);
        return switch (statType) {
            case ARMOR_BOOST -> getAttributeValue(player, Attributes.ARMOR) >= limit;
            case ATTACK_BOOTS -> getAttributeValue(player, Attributes.ATTACK_DAMAGE) >= limit;
            case SPEED_BOOST -> getAttributeValue(player, Attributes.MOVEMENT_SPEED) >= limit;
            case ATTACK_SPEED -> getAttributeValue(player, Attributes.ATTACK_SPEED) >= limit;
            case KNOCKBACK_RESISTANCE -> getAttributeValue(player, Attributes.KNOCKBACK_RESISTANCE) >= limit;
            case SWIM_SPEED -> getAttributeValue(player, NeoForgeMod.SWIM_SPEED) >= limit;
            default -> iPlayerStat.getStat(statType) >=limit;
        };
    }

    private static double getAttributeValue(Player player, Holder<Attribute> attribute) {
        AttributeInstance attributeInstance=player.getAttribute(attribute);
        return attributeInstance == null ? 0.0 : attributeInstance.getValue();
    }
}
