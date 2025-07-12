package net.chaolux.lendersdelight.coomon.stst;

import com.mojang.logging.LogUtils;
import net.chaolux.lendersdelight.LendersDelight;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = LendersDelight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerStatEvents {
    private static final UUID SPEED_MODIFIER_ID=UUID.fromString("59d51325-e4b3-4665-8a07-b36c5e137129");
    private static final UUID ATTACK_SPEED_MODIFIER_ID=UUID.fromString("9fd29c91-1c38-47b4-bc7a-8ed1be70fc7e");
    private static final UUID KNOCKBACK_RESISTANCE_MODIFIER_ID=UUID.fromString("ec4d9633-0151-46c5-bbb1-e571b34537e2");
    private static final UUID ATTACK_DAMAGE_MODIFIER_ID=UUID.fromString("00f6b2f5-a105-4abb-b7de-fee601a4e377");
    private static final UUID ARMOR_MODIFIER_ID=UUID.fromString("96a277cd-1712-4a4a-b1ab-b84a6429da51");
    private static final Logger LOGGER= LogUtils.getLogger();

    static {
        System.out.println("PlayerStatEvents class load");
    }

    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone event) {
        if(!event.isWasDeath()) return;
        event.getOriginal().getCapability(PlayerStatProvider.PLAYER_STAT).ifPresent(oldCap -> {
            event.getEntity().getCapability(PlayerStatProvider.PLAYER_STAT).ifPresent(newCap -> {
                newCap.copyFrom(oldCap);
                LOGGER.debug("Clone player stats after death from {} to {}",event.getOriginal().getName().getString(),event.getEntity().getName().getString());
            });
        });
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerStatProvider.sync(event.getEntity());
        LOGGER.debug("Player {} log in sunc stats",event.getEntity().getName().getString());
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
            float regen=stats.getStat(StatType.PASSIVE_REGEN);

            if(speedAttr !=null) {
                speedAttr.removeModifier(SPEED_MODIFIER_ID);
                float speed=stats.getStat(StatType.SPEED_BOOST);
                if(speed > 0f) {
                    speedAttr.addTransientModifier(new AttributeModifier(SPEED_MODIFIER_ID, "Speed Boost", speed / 100f, AttributeModifier.Operation.MULTIPLY_BASE));
                }
            }

            if(atkSpeedAttr !=null) {
                atkSpeedAttr.removeModifier(ATTACK_SPEED_MODIFIER_ID);
                float atkSpeed=stats.getStat(StatType.ATTACK_SPEED);
                if(atkSpeed > 0f) {
                    atkSpeedAttr.addTransientModifier(new AttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Attack Speed Boost", atkSpeed / 100f, AttributeModifier.Operation.MULTIPLY_BASE));
                }
            }

            if(knockAttr !=null) {
                knockAttr.removeModifier(KNOCKBACK_RESISTANCE_MODIFIER_ID);
                float knock=stats.getStat(StatType.KNOCKBACK_RESISTANCE);
                if(knock > 0f) {
                    knockAttr.addTransientModifier(new AttributeModifier(KNOCKBACK_RESISTANCE_MODIFIER_ID, "Knockback Resistance", knock / 100f, AttributeModifier.Operation.MULTIPLY_BASE));
                }
            }

            if(attackAttr !=null) {
                attackAttr.removeModifier(ATTACK_DAMAGE_MODIFIER_ID);
                float attack=stats.getStat(StatType.ATTACK_BOOTS);
                if(attack > 0f) {
                    attackAttr.addTransientModifier(new AttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Attack Boost", attack / 100f, AttributeModifier.Operation.MULTIPLY_BASE));
                }
            }

            if(armorAttr !=null) {
                armorAttr.removeModifier(ARMOR_MODIFIER_ID);
                float armor=stats.getStat(StatType.ARMOR_BOOST);
                if(armor > 0f) {
                    armorAttr.addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_ID, "Armor", armor / 100f, AttributeModifier.Operation.MULTIPLY_BASE));
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
        player.getCapability(PlayerStatProvider.PLAYER_STAT).ifPresent(stats -> {
            float jump=stats.getStat(StatType.JUMP_BOOST);
            if(jump > 0f) {
                player.setDeltaMovement(player.getDeltaMovement().x,player.getDeltaMovement().y+(jump / 100f),player.getDeltaMovement().z);
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
}