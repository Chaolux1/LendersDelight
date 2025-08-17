package net.chaolux.lendersdelight.coomon.loot.modifier;

import net.chaolux.lendersdelight.registry.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = "lendersdelight", bus = Bus.FORGE)
public class AddLootTableModifier {
    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        if(event.getName().equals(new ResourceLocation("cataclysm","entities/coral_golem"))) {
            LootPool CoralGolemMeatPool = LootPool.lootPool().name("lendersdelight_coral_golem_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.CORAL_GOLEM_MEAT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(CoralGolemMeatPool);
        }

        if(event.getName().equals(new ResourceLocation("cataclysm","entities/deepling")) || event.getName().equals(new ResourceLocation("cataclysm","entities/deepling_angler")) || event.getName().equals(new ResourceLocation("cataclysm","entities/deepling_priest"))) {
            LootPool DeeplingMeatPool = LootPool.lootPool().name("lendersdelight_coral_deepling_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.RAW_DEEPLING_MEAT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(DeeplingMeatPool);
        }

        if(event.getName().equals(new ResourceLocation("cataclysm","entities/deepling_brute"))) {
            LootPool DeeplingBruteMeatPool = LootPool.lootPool().name("lendersdelight_raw_brute_deepling_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.RAW_BRUTE_DEEPLING_MEAT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(DeeplingBruteMeatPool);
        }

        if(event.getName().equals(new ResourceLocation("cataclysm","entities/deepling_warlock"))) {
            LootPool DeeplingWarlockMeatPool = LootPool.lootPool().name("lendersdelight_raw_warlock_deepling_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.RAW_WARLOCK_DEEPLING_MEAT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(DeeplingWarlockMeatPool);
        }

        if(event.getName().equals(new ResourceLocation("cataclysm","entities/ender_golem"))) {
            LootPool EnderGolemMeatPool = LootPool.lootPool().name("lendersdelight_ender_golem_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.ENDER_GOLEM_MEAT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(EnderGolemMeatPool);
        }

        if(event.getName().equals(new ResourceLocation("cataclysm","entities/ender_guardian"))) {
            LootPool EnderGuardianMeatPool = LootPool.lootPool().name("lendersdelight_ender_guardian_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.ENDER_GUARDIAN_CRYSTAL.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(EnderGuardianMeatPool);
        }

        if(event.getName().equals(new ResourceLocation("cataclysm","entities/ignis"))) {
            LootPool IgnisMeatPool = LootPool.lootPool().name("lendersdelight_ignis_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.IGNIS.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(IgnisMeatPool);
        }

        if(event.getName().equals(new ResourceLocation("cataclysm","entities/ignited_berserker"))) {
            LootPool IgnitedBerserkerMeatPool = LootPool.lootPool().name("lendersdelight_ignited_berserker_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.BERSERKER.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(IgnitedBerserkerMeatPool);
        }

        if(event.getName().equals(new ResourceLocation("cataclysm","entities/kobolediator")) || event.getName().equals(new ResourceLocation("cataclysm","entities/koboleton"))) {
            LootPool KobolediatorMeatPool = LootPool.lootPool().name("lendersdelight_kobolediator_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.KOBOLETON_MEAT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(KobolediatorMeatPool);
        }

        if(event.getName().equals(new ResourceLocation("cataclysm","entities/maledictus"))) {
            LootPool MaledictusMeatPool = LootPool.lootPool().name("lendersdelight_maledictus_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.MALEDICTUS_HEART.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(MaledictusMeatPool);
        }

        if(event.getName().equals(new ResourceLocation("cataclysm","entities/netherite_monstrosity"))) {
            LootPool NetheriteMonstrosityMeatPool = LootPool.lootPool().name("lendersdelight_netherite_monstrosity_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.NETHERITE_MONSTROSITY_MEAT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(NetheriteMonstrosityMeatPool);
        }

        if(event.getName().equals(new ResourceLocation("cataclysm","entities/the_leviathan"))) {
            LootPool TheLeviathanMeatPool = LootPool.lootPool().name("lendersdelight_the_leviathan_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.LEVIATHAN.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(TheLeviathanMeatPool);
        }

        if(event.getName().equals(new ResourceLocation("cataclysm","entities/the_watcher"))) {
            LootPool TheWatchermMeatPool = LootPool.lootPool().name("lendersdelight_the_watcher_meat").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.WATCHER_HEART.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(TheWatchermMeatPool);
        }

        if(event.getName().equals(new ResourceLocation("cataclysm","entities/cindaria"))) {
            LootPool CindariaPool = LootPool.lootPool().name("lendersdelight_cindaria").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.CINDARIA_HAT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 1.0f)))).build();
            event.getTable().addPool(CindariaPool);
        }

        if(event.getName().equals(new ResourceLocation("cataclysm","entities/clawdian"))) {
            LootPool ClawdianPool = LootPool.lootPool().name("lendersdelight_clawdian").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.CRAB_LEGS.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(ClawdianPool);
        }

        if(event.getName().equals(new ResourceLocation("cataclysm","entities/hippocamtus"))) {
            LootPool HippocamtusPool = LootPool.lootPool().name("lendersdelight_hippocamtus").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.HIPPOCAMTUS.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))).build();
            event.getTable().addPool(HippocamtusPool);
        }

        if(event.getName().equals(new ResourceLocation("cataclysm","entities/scylla"))) {
            LootPool ScyllaPool = LootPool.lootPool().name("lendersdelight_scylla").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.SCYLLA_SNAKE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f)))).build();
            event.getTable().addPool(ScyllaPool);
        }

        if(event.getName().equals(new ResourceLocation("cataclysm","entities/symbiocto"))) {
            LootPool SymbioctoPool = LootPool.lootPool().name("lendersdelight_symbiocto").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.SYMBIOCTO_TENTACLE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 8.0f)))).build();
            event.getTable().addPool(SymbioctoPool);
        }

        if(event.getName().equals(new ResourceLocation("cataclysm","entities/urchinkin"))) {
            LootPool UrchinkinPool = LootPool.lootPool().name("lendersdelight_urchinkin").setRolls(UniformGenerator.between(1.0f, 1.0f)).add(LootItem.lootTableItem(ModItems.URCHINKIN.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 1.0f)))).build();
            event.getTable().addPool(UrchinkinPool);
        }
    }
}
