package net.chaolux.lendersdelight.registry.client;

import net.chaolux.lendersdelight.client.ClientKeyBind;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = "lendersdelight", bus = Bus.MOD, value = Dist.CLIENT)
public class ModEvents {
    @SubscribeEvent static void onRegisterKeyMapping(RegisterKeyMappingsEvent event) {
        event.register(ClientKeyBind.OPEN_STAT);
    }
}
