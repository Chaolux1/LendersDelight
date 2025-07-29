package net.chaolux.lendersdelight.coomon.item;

import net.chaolux.lendersdelight.registry.item.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vectorwing.farmersdelight.common.item.KnifeItem;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = "lendersdelight", bus = Bus.FORGE)
public class LacrimaAnchorKnife extends KnifeItem {
    public LacrimaAnchorKnife(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.phase !=TickEvent.Phase.END) return;
        Player player=event.player;
        if(!player.isInWater()) return;
        boolean holdKnife=player.getMainHandItem().getItem() == ModItems.LACRIMA_ANCHOR_KNIFE.get() || player.getOffhandItem().getItem() == ModItems.LACRIMA_ANCHOR_KNIFE.get();
        if(holdKnife) {
            player.setDeltaMovement(player.getDeltaMovement().x,Math.max(-0.6,player.getDeltaMovement().y - 0.1),player.getDeltaMovement().z);
        }
    }
}
