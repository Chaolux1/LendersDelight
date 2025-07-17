package net.chaolux.lendersdelight.registry.stat;

import com.mojang.logging.LogUtils;
import net.chaolux.lendersdelight.coomon.stat.IPlayerStat;
import net.chaolux.lendersdelight.coomon.stat.PlayerStatCapability;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.slf4j.Logger;

@EventBusSubscriber(modid = "lendersdelight", bus = Bus.MOD)
public class ModCapabilities {
    public static final EntityCapability<IPlayerStat, Void> PLAYER_STAT=
            EntityCapability.createVoid(ResourceLocation.fromNamespaceAndPath("lendersdelight","player_stat"), IPlayerStat.class);

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.registerEntity(PLAYER_STAT, EntityType.PLAYER,(entity, context) -> {
            if(entity instanceof Player player) {
                return PlayerStatCapability.getOrCreate(player);
            }
            return null;
        });
    }
}
