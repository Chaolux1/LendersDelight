package net.chaolux.lendersdelight.registry.client;

import net.chaolux.lendersdelight.client.ClientKeyBind;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;

import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;


@EventBusSubscriber(modid = "lendersdelight", bus = Bus.MOD, value = Dist.CLIENT)
public class ModEvents {
    @SubscribeEvent
    static void onRegisterKeyMapping(RegisterKeyMappingsEvent event) {
        event.register(ClientKeyBind.OPEN_STAT);
    }
}
