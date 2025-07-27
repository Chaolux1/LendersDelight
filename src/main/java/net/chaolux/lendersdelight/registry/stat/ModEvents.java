package net.chaolux.lendersdelight.registry.stat;

import net.chaolux.lendersdelight.coomon.stat.PlayerStatCapability;
import net.chaolux.lendersdelight.coomon.stat.PlayerStatProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(modid = "lendersdelight", bus = Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void onPayloadRegister(RegisterPayloadHandlersEvent event) {

    }
}
