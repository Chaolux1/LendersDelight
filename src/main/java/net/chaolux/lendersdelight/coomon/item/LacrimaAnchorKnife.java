package net.chaolux.lendersdelight.coomon.item;

import net.chaolux.lendersdelight.registry.item.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import vectorwing.farmersdelight.common.item.KnifeItem;

@EventBusSubscriber(modid = "lendersdelight", bus = Bus.GAME)
public class LacrimaAnchorKnife extends KnifeItem {
    public LacrimaAnchorKnife(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player=event.getEntity();
        if(player.level().isClientSide) return;
        if(!player.isInWater()) return;
        boolean holdKnife=player.getMainHandItem().getItem() == ModItems.LACRIMA_ANCHOR_KNIFE.get() || player.getOffhandItem().getItem() == ModItems.LACRIMA_ANCHOR_KNIFE.get();
        if(holdKnife) {
            player.setDeltaMovement(player.getDeltaMovement().x,Math.max(-0.6,player.getDeltaMovement().y - 0.1),player.getDeltaMovement().z);
            player.hurtMarked=true;
        }
    }
}
