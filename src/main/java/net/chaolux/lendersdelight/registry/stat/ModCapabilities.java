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
    private static final Logger LOGGER= LogUtils.getLogger();
    public static final EntityCapability<IPlayerStat, Void> PLAYER_STAT=
            EntityCapability.createVoid(ResourceLocation.fromNamespaceAndPath("lendersdelight","player_stat"), IPlayerStat.class);
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        LOGGER.debug("Register capability PLAYER_STAT");
        event.registerEntity(PLAYER_STAT, EntityType.PLAYER,(entity, context) -> {
            if(entity instanceof Player player) {
                LOGGER.debug("Crate new PlayerStatCapability for player: {}", player.getName().getString());
                return PlayerStatCapability.getOrCreate(player);
            }
            return null;
        });
    }
}
