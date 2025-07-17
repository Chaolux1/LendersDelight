package net.chaolux.lendersdelight.coomon.stat;

import com.mojang.logging.LogUtils;
import net.chaolux.lendersdelight.LendersDelight;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
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
        if (!(event.getEntity() instanceof Player player)) return;
        PlayerStatProvider.get(player).ifPresent(cap -> {
                CompoundTag saved = cap.saveToNBT();
                SAVED_STAT.put(player.getUUID(), saved);
        });
    }
    private static final Logger LOGGER= LogUtils.getLogger();

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player=event.getEntity();
        LOGGER.debug("PlayerLoggedInEvent for: {}",player.getName().getString());
        PlayerStatProvider.sync(player);
        PlayerStatProvider.get(player);
        CompoundTag saved=PlayerStatStorage.get((ServerLevel) player.level()).getStatData(player.getUUID());
        PlayerStatProvider.get(player).ifPresent(stat -> {
            stat.loadFromNBT(saved);
            stat.sync(player);
        });
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        CompoundTag saved = SAVED_STAT.remove(player.getUUID());
        if(saved !=null) {
            PlayerStatProvider.get(player).ifPresent(cap -> {
                cap.loadFromNBT(saved);
                PlayerStatProvider.sync(player);
            });
        }
    }

    @SubscribeEvent
    public static void onLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player=event.getEntity();
        var cap=PlayerStatProvider.get(player);
        cap.ifPresent(stat -> {
            PlayerStatStorage.get((ServerLevel) player.level()).savePlayer(player,stat);
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
        PlayerStatProvider.get(player).ifPresent(stats -> {
            var speedAttr=player.getAttribute(Attributes.MOVEMENT_SPEED);
            var atkSpeedAttr=player.getAttribute(Attributes.ATTACK_SPEED);
            var knockAttr=player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            var attackAttr=player.getAttribute(Attributes.ATTACK_DAMAGE);
            var armorAttr=player.getAttribute(Attributes.ARMOR);
            var swimAttr=player.getAttribute(NeoForgeMod.SWIM_SPEED);
            float regen=stats.getStat(StatType.PASSIVE_REGEN);

            if(speedAttr !=null) {
                AttributeModifier modifier=null;
                for(AttributeModifier mod:speedAttr.getModifiers()) {
                    if(mod.is(SPEED_MODIFIER_ID)) {
                        modifier=mod;
                        break;
                    }
                }
                if(modifier !=null) speedAttr.removeModifier(modifier);
                float speed=stats.getStat(StatType.SPEED_BOOST);
                if(speed > 0f) {
                    speedAttr.addTransientModifier(new AttributeModifier(SPEED_MODIFIER_ID, speed / 100f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                }
            }

            if(atkSpeedAttr !=null) {
                AttributeModifier modifier=null;
                for(AttributeModifier mod:atkSpeedAttr.getModifiers()) {
                    if(mod.is(ATTACK_SPEED_MODIFIER_ID)) {
                        modifier=mod;
                        break;
                    }
                }
                if(modifier !=null) atkSpeedAttr.removeModifier(modifier);
                float atkSpeed=stats.getStat(StatType.ATTACK_SPEED);
                if(atkSpeed > 0f) {
                    atkSpeedAttr.addTransientModifier(new AttributeModifier(ATTACK_SPEED_MODIFIER_ID, atkSpeed / 100f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                }
            }

            if(knockAttr !=null) {
                AttributeModifier modifier=null;
                for(AttributeModifier mod:knockAttr.getModifiers()) {
                    if(mod.is(KNOCKBACK_RESISTANCE_MODIFIER_ID)) {
                        modifier=mod;
                        break;
                    }
                }
                if(modifier !=null) knockAttr.removeModifier(modifier);
                float knock=stats.getStat(StatType.KNOCKBACK_RESISTANCE);
                if(knock > 0f) {
                    knockAttr.addTransientModifier(new AttributeModifier(KNOCKBACK_RESISTANCE_MODIFIER_ID, knock / 100f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                }
            }

            if(attackAttr !=null) {
                AttributeModifier modifier=null;
                for(AttributeModifier mod:attackAttr.getModifiers()) {
                    if(mod.is(ATTACK_DAMAGE_MODIFIER_ID)) {
                        modifier=mod;
                        break;
                    }
                }
                if(modifier !=null) attackAttr.removeModifier(modifier);
                float attack=stats.getStat(StatType.ATTACK_BOOTS);
                if(attack > 0f) {
                    attackAttr.addTransientModifier(new AttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, attack / 100f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                }
            }

            if(armorAttr !=null) {
                AttributeModifier modifier=null;
                for(AttributeModifier mod:armorAttr.getModifiers()) {
                    if(mod.is(ARMOR_MODIFIER_ID)) {
                        modifier=mod;
                        break;
                    }
                }
                if(modifier !=null) armorAttr.removeModifier(modifier);
                float armor=stats.getStat(StatType.ARMOR_BOOST);
                if(armor > 0f) {
                    armorAttr.addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_ID, armor / 100f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                }
            }

            if(swimAttr !=null) {
                AttributeModifier modifier=null;
                for(AttributeModifier mod:swimAttr.getModifiers()) {
                    if(mod.is(SWIM_SPEED_MODIFIER_ID)) {
                        modifier=mod;
                        break;
                    }
                }
                if(modifier !=null) swimAttr.removeModifier(modifier);
                float swim=stats.getStat(StatType.SWIM_SPEED);
                if(swim > 0f) {
                    swimAttr.addTransientModifier(new AttributeModifier(SWIM_SPEED_MODIFIER_ID, swim / 100f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                }
            }

            if(regen > 0f && player.tickCount % 100 == 0) {
                player.heal(regen / 100f);
            }
        });
    }

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event) {
        if(!(event.getEntity() instanceof Player player)) return;
        PlayerStatProvider.get(player).ifPresent(stats -> {
            float jump=stats.getStat(StatType.JUMP_BOOST);
            if(jump > 0f) {
                player.setDeltaMovement(player.getDeltaMovement().x,player.getDeltaMovement().y+(jump / 500f),player.getDeltaMovement().z);
            }
        });
    }

    @SubscribeEvent
    public static void onCrit(CriticalHitEvent event) {
        Player player=event.getEntity();
        PlayerStatProvider.get(player).ifPresent(stats -> {
            float chance=stats.getStat(StatType.CRIT_CHANCE);
            if(chance > 0f && player.level().random.nextFloat() < (chance / 100f)) {
                event.setCriticalHit(true);
                event.setDamageMultiplier(1.5f);
            }
        });
    }
}