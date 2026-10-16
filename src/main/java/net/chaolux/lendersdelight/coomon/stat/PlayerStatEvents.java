package net.chaolux.lendersdelight.coomon.stat;

import com.mojang.logging.LogUtils;
import net.chaolux.lendersdelight.Config;
import net.chaolux.lendersdelight.LendersDelight;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = LendersDelight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerStatEvents {
    private static final UUID SPEED_MODIFIER_ID=UUID.fromString("59d51325-e4b3-4665-8a07-b36c5e137129");
    private static final UUID ATTACK_SPEED_MODIFIER_ID=UUID.fromString("9fd29c91-1c38-47b4-bc7a-8ed1be70fc7e");
    private static final UUID KNOCKBACK_RESISTANCE_MODIFIER_ID=UUID.fromString("ec4d9633-0151-46c5-bbb1-e571b34537e2");
    private static final UUID ATTACK_DAMAGE_MODIFIER_ID=UUID.fromString("00f6b2f5-a105-4abb-b7de-fee601a4e377");
    private static final UUID ARMOR_MODIFIER_ID=UUID.fromString("96a277cd-1712-4a4a-b1ab-b84a6429da51");
    private static final UUID SWIM_SPEED_MODIFIER_ID=UUID.fromString("1a94d5b0-0dbd-43fc-a39d-b5bbf7e1b838");
    private static final Map<UUID, CompoundTag> SAVED_STAT=new HashMap<>();

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if(Config.RESET_ON_DEATH.get()) return;
        if (!(event.getEntity() instanceof Player player)) return;
        LazyOptional<IPlayerStat> capOptional = player.getCapability(PlayerStatProvider.PLAYER_STAT);
        if (capOptional.isPresent()) {
            IPlayerStat cap = capOptional.orElse(null);
            if (cap != null) {
                CompoundTag saved = cap.saveToNBT();
                SAVED_STAT.put(player.getUUID(), saved);
            }
        }
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerStatProvider.sync(event.getEntity());
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        CompoundTag saved = SAVED_STAT.remove(player.getUUID());
        if (saved != null) {
            LazyOptional<IPlayerStat> capOptional = player.getCapability(PlayerStatProvider.PLAYER_STAT);
            if (capOptional.isPresent()) {
                IPlayerStat cap = capOptional.orElse(null);
                if (cap != null) {
                    cap.loadFromNBT(saved);
                }
            }
            PlayerStatProvider.sync(event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        if(event.phase !=TickEvent.Phase.END) return;
        Player player=event.player;
        player.getCapability(PlayerStatProvider.PLAYER_STAT).ifPresent(stats -> {
            var speedAttr=player.getAttribute(Attributes.MOVEMENT_SPEED);
            var atkSpeedAttr=player.getAttribute(Attributes.ATTACK_SPEED);
            var knockAttr=player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            var attackAttr=player.getAttribute(Attributes.ATTACK_DAMAGE);
            var armorAttr=player.getAttribute(Attributes.ARMOR);
            var swimAttr=player.getAttribute(ForgeMod.SWIM_SPEED.get());
            float regen=stats.getStat(StatType.PASSIVE_REGEN);

            update(speedAttr,SPEED_MODIFIER_ID,"Speed Boost",stats.getStat(StatType.SPEED_BOOST) / 100f);
            update(atkSpeedAttr,ATTACK_SPEED_MODIFIER_ID,"Attack Speed Boost",stats.getStat(StatType.ATTACK_SPEED) / 100f);
            update(knockAttr,KNOCKBACK_RESISTANCE_MODIFIER_ID,"Knockback Resistance",stats.getStat(StatType.KNOCKBACK_RESISTANCE) / 100f);
            update(attackAttr,ATTACK_DAMAGE_MODIFIER_ID,"Attack Boost",stats.getStat(StatType.ATTACK_BOOTS) / 100f);
            update(armorAttr,ARMOR_MODIFIER_ID,"Armor",stats.getStat(StatType.ARMOR_BOOST) / 100f);
            update(swimAttr,SWIM_SPEED_MODIFIER_ID,"Swim Speed Boost",stats.getStat(StatType.SWIM_SPEED) / 100f);

            if(!player.level().isClientSide && regen > 0f && player.tickCount % 100 == 0) {
                player.heal(regen / 100f);
            }
        });
    }

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event) {
        if(!(event.getEntity() instanceof Player player)) return;
        player.getCapability(PlayerStatProvider.PLAYER_STAT).ifPresent(stats -> {
            float jump=stats.getStat(StatType.JUMP_BOOST);
            if(jump > 0f) {
                player.setDeltaMovement(player.getDeltaMovement().x,player.getDeltaMovement().y+(jump / 500f),player.getDeltaMovement().z);
            }
        });
    }

    @SubscribeEvent
    public static void onCrit(CriticalHitEvent event) {
        Player player=event.getEntity();
        player.getCapability(PlayerStatProvider.PLAYER_STAT).ifPresent(stats -> {
            float chance=stats.getStat(StatType.CRIT_CHANCE);
            if(chance > 0f && player.level().random.nextFloat() < (chance / 100f)) {
                event.setResult(Event.Result.ALLOW);
                event.setDamageModifier(1.5f);
            }
        });
    }

    @SubscribeEvent
    public static void onDimensionChange(PlayerEvent.PlayerChangedDimensionEvent playerChangedDimensionEvent) {
        PlayerStatProvider.sync(playerChangedDimensionEvent.getEntity());
    }

    private static void update(AttributeInstance attributeInstance,UUID uuid,String string,double value) {
        if(attributeInstance == null) return;
        AttributeModifier attributeModifier=attributeInstance.getModifier(uuid);
        if(!Double.isFinite(value) || value <= 0.0) {
            if(attributeModifier != null) attributeInstance.removeModifier(uuid);
            return;
        }
        if(attributeModifier != null && Double.compare(attributeModifier.getAmount(),value) == 0 && attributeModifier.getOperation() == AttributeModifier.Operation.MULTIPLY_BASE) return;
        if(attributeModifier != null) attributeInstance.removeModifier(uuid);
        attributeInstance.addTransientModifier(new AttributeModifier(uuid,string,value,AttributeModifier.Operation.MULTIPLY_BASE));
    }
}