package net.chaolux.lendersdelight.coomon.loot.modifier;

import net.chaolux.lendersdelight.registry.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.LootTableLoadEvent;


@EventBusSubscriber(modid = "lendersdelight", bus = Bus.GAME)
public class AddLootTableModifier {
    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        if(event.getName().equals(ResourceLocation.fromNamespaceAndPath("cataclysm", "entities/coral_golem"))) {
            LootPool CoralGolemMeatPool = LootPool.lootPool().name("lendersdelight_coral_golem_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.CORAL_GOLEM_MEAT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(CoralGolemMeatPool);
        }

        if(event.getName().equals(ResourceLocation.fromNamespaceAndPath("cataclysm","entities/deepling")) || event.getName().equals(ResourceLocation.fromNamespaceAndPath("cataclysm","entities/deepling_angler")) || event.getName().equals(ResourceLocation.fromNamespaceAndPath("cataclysm","entities/deepling_brute")) || event.getName().equals(ResourceLocation.fromNamespaceAndPath("cataclysm","entities/deepling_priest")) || event.getName().equals(ResourceLocation.fromNamespaceAndPath("cataclysm","entities/deepling_warlock"))) {
            LootPool DeeplingMeatPool = LootPool.lootPool().name("lendersdelight_coral_deepling_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.RAW_DEEPLING_MEAT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(DeeplingMeatPool);
        }

        if(event.getName().equals(ResourceLocation.fromNamespaceAndPath("cataclysm","entities/ender_golem"))) {
            LootPool EnderGolemMeatPool = LootPool.lootPool().name("lendersdelight_ender_golem_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.ENDER_GOLEM_MEAT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(EnderGolemMeatPool);
        }

        if(event.getName().equals(ResourceLocation.fromNamespaceAndPath("cataclysm","entities/ender_guardian"))) {
            LootPool EnderGuardianMeatPool = LootPool.lootPool().name("lendersdelight_ender_guardian_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.ENDER_GUARDIAN_CRYSTAL.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(EnderGuardianMeatPool);
        }

        if(event.getName().equals(ResourceLocation.fromNamespaceAndPath("cataclysm","entities/ignis"))) {
            LootPool IgnisMeatPool = LootPool.lootPool().name("lendersdelight_ignis_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.IGNIS.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(IgnisMeatPool);
        }

        if(event.getName().equals(ResourceLocation.fromNamespaceAndPath("cataclysm","entities/ignited_berserker"))) {
            LootPool IgnitedBerserkerMeatPool = LootPool.lootPool().name("lendersdelight_ignited_berserker_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.BERSERKER.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(IgnitedBerserkerMeatPool);
        }

        if(event.getName().equals(ResourceLocation.fromNamespaceAndPath("cataclysm","entities/kobolediator")) || event.getName().equals(ResourceLocation.fromNamespaceAndPath("cataclysm","entities/koboleton"))) {
            LootPool KobolediatorMeatPool = LootPool.lootPool().name("lendersdelight_kobolediator_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.KOBOLETON_MEAT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(KobolediatorMeatPool);
        }

        if(event.getName().equals(ResourceLocation.fromNamespaceAndPath("cataclysm","entities/maledictus"))) {
            LootPool MaledictusMeatPool = LootPool.lootPool().name("lendersdelight_maledictus_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.MALEDICTUS_HEART.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(MaledictusMeatPool);
        }

        if(event.getName().equals(ResourceLocation.fromNamespaceAndPath("cataclysm","entities/netherite_monstrosity"))) {
            LootPool NetheriteMonstrosityMeatPool = LootPool.lootPool().name("lendersdelight_netherite_monstrosity_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.NETHERITE_MONSTROSITY_MEAT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(NetheriteMonstrosityMeatPool);
        }

        if(event.getName().equals(ResourceLocation.fromNamespaceAndPath("cataclysm","entities/the_leviathan"))) {
            LootPool TheLeviathanMeatPool = LootPool.lootPool().name("lendersdelight_the_leviathan_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.LEVIATHAN.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(TheLeviathanMeatPool);
        }

        if(event.getName().equals(ResourceLocation.fromNamespaceAndPath("cataclysm","entities/the_watcher"))) {
            LootPool TheWatchermMeatPool = LootPool.lootPool().name("lendersdelight_the_watcher_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.WATCHER_HEART.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(TheWatchermMeatPool);
        }
    }
}
