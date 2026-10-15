package net.chaolux.lendersdelight.coomon.stat;

import net.chaolux.lendersdelight.Config;
import net.chaolux.lendersdelight.registry.stat.ModNetwork;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

@EventBusSubscriber(modid = "lendersdelight", bus = Bus.FORGE)
public class StatEvents {
    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent addReloadListenerEvent) {
        addReloadListenerEvent.addListener(new StatReloadListener());
    }

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent onDatapackSyncEvent) {
        StatDefinitionPacket statDefinitionPacket=StatDefinitionPacket.from();
        if(onDatapackSyncEvent.getPlayer() != null) {
            ModNetwork.sendToClient(onDatapackSyncEvent.getPlayer(),statDefinitionPacket);
        } else {
            for (ServerPlayer serverPlayer : onDatapackSyncEvent.getPlayerList().getPlayers()) {
                ModNetwork.sendToClient(serverPlayer,statDefinitionPacket);
            }
        }
    }

    @SubscribeEvent
    public static void onFinish(LivingEntityUseItemEvent.Finish finish) {
        if(!(finish.getEntity() instanceof ServerPlayer serverPlayer)) return;
        if(!Config.ENABLE_STAT.get()) return;
        ItemStack itemStack=finish.getItem();
        if(itemStack.isEmpty() || !itemStack.isEdible()) return;
        ResourceLocation resourceLocation= ForgeRegistries.ITEMS.getKey(itemStack.getItem());
        if(resourceLocation == null) return;
        Map<StatType,Float> statTypeFloatMap=StatReloadListener.getServer(resourceLocation);
        if(statTypeFloatMap.isEmpty()) return;
        serverPlayer.getCapability(PlayerStatProvider.PLAYER_STAT).ifPresent(stat -> {
            StatMode statMode=Config.STAT_MODE.get();
            if(statMode == StatMode.ONCE && stat.hasConsumed(resourceLocation)) return;
            boolean once=false;
            for(Map.Entry<StatType,Float> statTypeFloatEntry : statTypeFloatMap.entrySet()) {
                if(statMode == StatMode.LIMITED && isLimit(serverPlayer,stat,statTypeFloatEntry.getKey())) continue;
                stat.addStat(statTypeFloatEntry.getKey(),statTypeFloatEntry.getValue());
                once=true;
            }
            if(once) {
                if(statMode == StatMode.ONCE) stat.markConsumed(resourceLocation);
                PlayerStatProvider.sync(serverPlayer);
            }
        });
    }

    private static boolean isLimit(Player player,IPlayerStat iPlayerStat,StatType statType) {
        double limit=Config.getStatLimit(statType);
        return switch (statType) {
            case ARMOR_BOOST -> getAttributeValue(player, Attributes.ARMOR) >= limit;
            case ATTACK_BOOTS -> getAttributeValue(player, Attributes.ATTACK_DAMAGE) >= limit;
            case SPEED_BOOST -> getAttributeValue(player, Attributes.MOVEMENT_SPEED) >= limit;
            case ATTACK_SPEED -> getAttributeValue(player, Attributes.ATTACK_SPEED) >= limit;
            case KNOCKBACK_RESISTANCE -> getAttributeValue(player, Attributes.KNOCKBACK_RESISTANCE) >= limit;
            case SWIM_SPEED -> getAttributeValue(player, ForgeMod.SWIM_SPEED.get()) >= limit;
            default -> iPlayerStat.getStat(statType) >=limit;
        };
    }

    private static double getAttributeValue(Player player, Attribute attribute) {
        AttributeInstance attributeInstance=player.getAttribute(attribute);
        return attributeInstance == null ? 0.0 : attributeInstance.getValue();
    }
}
