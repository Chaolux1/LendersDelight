package net.chaolux.lendersdelight.coomon.stat;

import com.mojang.logging.LogUtils;
import net.chaolux.lendersdelight.Config;
import net.chaolux.lendersdelight.LendersDelight;
import net.chaolux.lendersdelight.ServerConfig;
import net.chaolux.lendersdelight.client.ClientStat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = LendersDelight.MOD_ID, bus = Bus.GAME)
public class PlayerStatEvents {
    private static final ResourceLocation SPEED_MODIFIER_ID=ResourceLocation.fromNamespaceAndPath(LendersDelight.MOD_ID, "speed_modifier");
    private static final ResourceLocation ATTACK_SPEED_MODIFIER_ID=ResourceLocation.fromNamespaceAndPath(LendersDelight.MOD_ID, "attack_speed_modifier");
    private static final ResourceLocation KNOCKBACK_RESISTANCE_MODIFIER_ID=ResourceLocation.fromNamespaceAndPath(LendersDelight.MOD_ID, "knockback_resistance_modifier");
    private static final ResourceLocation ATTACK_DAMAGE_MODIFIER_ID=ResourceLocation.fromNamespaceAndPath(LendersDelight.MOD_ID, "attack_damege_modifier");
    private static final ResourceLocation ARMOR_MODIFIER_ID=ResourceLocation.fromNamespaceAndPath(LendersDelight.MOD_ID, "armor_modifier");
    private static final ResourceLocation SWIM_SPEED_MODIFIER_ID=ResourceLocation.fromNamespaceAndPath(LendersDelight.MOD_ID, "swim_speed_modifier");
    private static final Map<UUID, CompoundTag> SAVED_STAT=new HashMap<>();

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if(ServerConfig.RESET_ON_DEATH.get()) return;
        if (!(event.getEntity() instanceof Player player)) return;
        PlayerStatProvider.get(player).ifPresent(stat -> {
            CompoundTag saved=stat.saveToNBT();
            SAVED_STAT.put(player.getUUID(),saved);
        });
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player=event.getEntity();
        player.getPersistentData().putBoolean("lendersdelight_need_sync",true);
        CompoundTag saved=PlayerStatStorage.get(player.getServer().overworld()).getStatData(player.getUUID());
        PlayerStatProvider.get(player).ifPresent(stat -> {
            stat.loadFromNBT(saved);
        });
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        CompoundTag saved = SAVED_STAT.remove(player.getUUID());
        PlayerStatProvider.get(player).ifPresent(stat -> {
            if(ServerConfig.RESET_ON_DEATH.get() && !event.isEndConquered()) {
                for(StatType statType : StatType.values()) {
                    stat.setStat(statType,0.0f);
                }
                stat.setFoodUses(Map.of());
            } else if(saved != null) {
                stat.loadFromNBT(saved);
            }
            PlayerStatProvider.sync(player);
        });
    }

    @SubscribeEvent
    public static void onLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player=event.getEntity();
        var cap=PlayerStatProvider.get(player);
        cap.ifPresent(stat -> {
            PlayerStatStorage.get(player.getServer().overworld()).savePlayer(player,stat);
        });
        if(!event.getEntity().level().isClientSide) {
            PlayerStatCapability.clear(event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onWorldLeave(LevelEvent.Unload event) {
        if(!event.getLevel().isClientSide() && event.getLevel() instanceof ServerLevel) {
            PlayerStatCapability.clearAll();
        }
    }

    @SubscribeEvent
    public static void onTick(PlayerTickEvent.Post event) {
        Player player=event.getEntity();
        if(player.level().isClientSide) return;
        if(player.getPersistentData().getBoolean("lendersdelight_need_sync")) {
            player.getPersistentData().remove("lendersdelight_need_sync");
            PlayerStatProvider.get(player).ifPresent(stat -> {
                PlayerStatProvider.sync(player);
            });
        }
        PlayerStatProvider.get(player).ifPresent(stats -> {
            var speedAttr=player.getAttribute(Attributes.MOVEMENT_SPEED);
            var atkSpeedAttr=player.getAttribute(Attributes.ATTACK_SPEED);
            var knockAttr=player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            var attackAttr=player.getAttribute(Attributes.ATTACK_DAMAGE);
            var armorAttr=player.getAttribute(Attributes.ARMOR);
            var swimAttr=player.getAttribute(NeoForgeMod.SWIM_SPEED);
            float regen=getStat(stats,StatType.PASSIVE_REGEN);
            update(speedAttr,SPEED_MODIFIER_ID,getStat(stats,StatType.SPEED_BOOST) / 100f);
            update(atkSpeedAttr,ATTACK_SPEED_MODIFIER_ID,getStat(stats,StatType.ATTACK_SPEED) / 100f);
            update(knockAttr,KNOCKBACK_RESISTANCE_MODIFIER_ID,getStat(stats,StatType.KNOCKBACK_RESISTANCE) / 100f);
            update(attackAttr,ATTACK_DAMAGE_MODIFIER_ID,getStat(stats,StatType.ATTACK_BOOTS) / 100f);
            update(armorAttr,ARMOR_MODIFIER_ID,getStat(stats,StatType.ARMOR_BOOST) / 100f);
            update(swimAttr,SWIM_SPEED_MODIFIER_ID,getStat(stats,StatType.SWIM_SPEED) / 100f);
            if(player.tickCount % 1200 == 0 && player.getServer() != null) PlayerStatStorage.get(player.getServer().overworld()).savePlayer(player,stats);
            if(regen > 0f && player.tickCount % 100 == 0) {
                player.heal(regen / 100f);
            }
        });
    }

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event) {
        if(!(event.getEntity() instanceof Player player)) return;
        if(ServerConfig.JUMP_BOOST_EFFECT.get() && !player.hasEffect(MobEffects.JUMP)) return;
        IPlayerStat iPlayerStat=player.level().isClientSide ? ClientStat.get(player) : PlayerStatProvider.get(player).orElse(null);
        if(iPlayerStat == null) return;
        float jump=getStat(iPlayerStat,StatType.JUMP_BOOST);
        if(jump > 0f) {
            player.setDeltaMovement(player.getDeltaMovement().x,player.getDeltaMovement().y+(jump / 500f),player.getDeltaMovement().z);
        }
    }

    @SubscribeEvent
    public static void onCrit(CriticalHitEvent event) {
        Player player=event.getEntity();
        PlayerStatProvider.get(player).ifPresent(stats -> {
            float chance=getStat(stats,StatType.CRIT_CHANCE);
            if(chance > 0f && player.level().random.nextFloat() < (chance / 100f)) {
                event.setCriticalHit(true);
                event.setDamageMultiplier(1.5f);
            }
        });
    }

    @SubscribeEvent
    public static void onDimensionChange(PlayerEvent.PlayerChangedDimensionEvent playerChangedDimensionEvent) {
        PlayerStatProvider.sync(playerChangedDimensionEvent.getEntity());
    }

    private static void update(AttributeInstance attributeInstance, ResourceLocation resourceLocation, double value) {
        if(attributeInstance == null) return;
        AttributeModifier attributeModifier=attributeInstance.getModifier(resourceLocation);
        if(!Double.isFinite(value) || value <= 0.0) {
            if(attributeModifier != null) attributeInstance.removeModifier(resourceLocation);
            return;
        }
        if(attributeModifier != null && Double.compare(attributeModifier.amount(),value) == 0 && attributeModifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_BASE) return;
        if(attributeModifier != null) attributeInstance.removeModifier(resourceLocation);
        attributeInstance.addTransientModifier(new AttributeModifier(resourceLocation,value,AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    }

    private static float getStat(IPlayerStat iPlayerStat,StatType statType) {
        return ServerConfig.isEnableStat(statType) ? iPlayerStat.getStat(statType) : 0.0f;
    }
}