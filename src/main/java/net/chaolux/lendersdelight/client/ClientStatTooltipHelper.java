package net.chaolux.lendersdelight.client;

import net.chaolux.lendersdelight.coomon.stat.IPlayerStat;
import net.chaolux.lendersdelight.coomon.stat.PlayerStatProvider;
import net.chaolux.lendersdelight.coomon.stat.StatReloadListener;
import net.chaolux.lendersdelight.coomon.stat.StatType;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;

public class ClientStatTooltipHelper {
    public static boolean wasAlreadyConsumed(ResourceLocation resourceLocation) {
        Player player= Minecraft.getInstance().player;
        if(player == null) return false;
        return player.getCapability(PlayerStatProvider.PLAYER_STAT).map(stats -> stats.hasConsumed(resourceLocation)).orElse(false);
    }

    public static boolean isLimitReachClient(StatType type) {
        Player player=Minecraft.getInstance().player;
        if(player == null) return false;
        return player.getCapability(PlayerStatProvider.PLAYER_STAT).map(stats -> isLimitReach(player,stats,type)).orElse(false);
    }

    public static boolean isFullyLimit(Iterable<StatType> types) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return false;
        return player.getCapability(PlayerStatProvider.PLAYER_STAT).map(stats -> {
            for(StatType type : types) {
                if(!isLimitReach(player,stats,type)) return false;
            }
            return true;
        }).orElse(false);
    }

    private static boolean isLimitReach(Player player, IPlayerStat stats, StatType type) {
        double limit= StatReloadListener.getLimit(type);
        if(type == StatType.ARMOR_BOOST) return getAttributeValue(player, Attributes.ARMOR) >= limit;
        if(type == StatType.ATTACK_BOOTS) return getAttributeValue(player, Attributes.ATTACK_DAMAGE) >= limit;
        if(type == StatType.SPEED_BOOST) return getAttributeValue(player, Attributes.MOVEMENT_SPEED) >= limit;
        if(type == StatType.ATTACK_SPEED) return getAttributeValue(player, Attributes.ATTACK_SPEED) >= limit;
        if(type == StatType.KNOCKBACK_RESISTANCE) return getAttributeValue(player, Attributes.KNOCKBACK_RESISTANCE) >= limit;
        if(type == StatType.SWIM_SPEED) return getAttributeValue(player, ForgeMod.SWIM_SPEED.get()) >= limit;
        return stats.getStat(type) >= limit;
    }

    private static double getAttributeValue(Player player, Attribute attribute) {
        AttributeInstance attributeInstance=player.getAttribute(attribute);
        return attributeInstance == null ? 0.0 : attributeInstance.getValue();
    }

    public static boolean hasAttributeLimit(StatType statType) {
        return switch (statType) {
            case ARMOR_BOOST,ATTACK_BOOTS,SPEED_BOOST,ATTACK_SPEED,KNOCKBACK_RESISTANCE,SWIM_SPEED -> true;
            default -> false;
        };
    }

    public static double getLimitValue(Player player,IPlayerStat iPlayerStat,StatType statType) {
        return switch (statType) {
            case ARMOR_BOOST -> getAttributeValue(player,Attributes.ARMOR);
            case ATTACK_BOOTS -> getAttributeValue(player,Attributes.ATTACK_DAMAGE);
            case SPEED_BOOST -> getAttributeValue(player,Attributes.MOVEMENT_SPEED);
            case ATTACK_SPEED -> getAttributeValue(player,Attributes.ATTACK_SPEED);
            case KNOCKBACK_RESISTANCE -> getAttributeValue(player,Attributes.KNOCKBACK_RESISTANCE);
            case SWIM_SPEED -> getAttributeValue(player,ForgeMod.SWIM_SPEED.get());
            default -> iPlayerStat.getStat(statType);
        };
    }

    public static int getFoodUse(ResourceLocation resourceLocation) {
        Player player=Minecraft.getInstance().player;
        if(player == null) return 0;
        return player.getCapability(PlayerStatProvider.PLAYER_STAT).map(stat -> stat.getFoodUse(resourceLocation)).orElse(0);
    }
}
