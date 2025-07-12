package net.chaolux.lendersdelight.registry.stat;

import net.chaolux.lendersdelight.coomon.stat.PlayerStatCapability;
import net.chaolux.lendersdelight.coomon.stat.PlayerStatProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = "lendersdelight", bus = Bus.FORGE)
public class ModEvents {
    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        PlayerStatProvider.attach(event);
    }
}
