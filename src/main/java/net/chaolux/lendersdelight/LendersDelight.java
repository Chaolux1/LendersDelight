package net.chaolux.lendersdelight;

import net.chaolux.lendersdelight.registry.block.ModBlocks;
import net.chaolux.lendersdelight.registry.item.ModItems;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(LendersDelight.MOD_ID)
public class LendersDelight
{
    public static final String MOD_ID = "lendersdelight";
    private static final Logger LOGGER = LogUtils.getLogger();

    public LendersDelight(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModItems.AMETHYST_CRAB_MEAT_STICK.get());
            event.accept(ModItems.AMETHYST_CRAB_SANDWICH.get());
            event.accept(ModItems.BERSERKER.get());
            event.accept(ModItems.BERSERKER_STICK.get());
            event.accept(ModItems.COBOLETON_MEAT.get());
            event.accept(ModItems.COOKED_AMETHYST_CRAB_MEAT.get());
            event.accept(ModItems.COOKED_BERSERKER.get());
            event.accept(ModItems.COOKED_COBOLETON_MEAT.get());
            event.accept(ModItems.COOKED_CORAL_GOLEM_MEAT.get());
            event.accept(ModItems.COOKED_ENDER_GOLEM_MEAT.get());
            event.accept(ModItems.COOKED_LEVIATHAN.get());
            event.accept(ModItems.COOKED_LIONFISH.get());
            event.accept(ModItems.COOKED_NETHERITE_MONSTROSITY_MEAT.get());
            event.accept(ModItems.CORAL_AND_VOID.get());
            event.accept(ModItems.CORAL_CHUNK_SANDWICH.get());
            event.accept(ModItems.CORAL_GOLEM_MEAT.get());
            event.accept(ModItems.CRYSTALLIZED_CORAL_POTATO.get());
            event.accept(ModItems.CRYSTALLIZED_CORAL_ROLL.get());
            event.accept(ModItems.AMETHYST_CRAB_MEAT_SLICE.get());
            event.accept(ModItems.COOKED_AMETHYST_CRAB_MEAT_SLICE.get());
            event.accept(ModItems.CORAL_CHUNK_RED_SLICE.get());
            event.accept(ModItems.MALEDICTUS_HEART_SLICE.get());
            event.accept(ModItems.DEEPLING_MEAT.get());
            event.accept(ModItems.ENDER_BLOOD.get());
            event.accept(ModItems.ENDER_BLOOD_GLAZED_MALEDICTUS_HEART.get());
            event.accept(ModItems.FRIED_ABYSSAL_EGG.get());
            event.accept(ModItems.GLAZED_REMNANT_SKULL.get());
            event.accept(ModItems.GRILLED_LIONFISH.get());
            event.accept(ModItems.HAM_OF_BERSERKER.get());
            event.accept(ModItems.IGNIS.get());
            event.accept(ModItems.IMPROVED_DOG_FOOD.get());
            event.accept(ModItems.KOBOLETON_PUMPKIN.get());
            event.accept(ModItems.LEVIATHAN.get());
            event.accept(ModItems.LEVIATHAN_AND_ABYSSAL_EGG.get());
            event.accept(ModItems.LIONFISH_ROLL.get());
            event.accept(ModItems.LIONFISH_SLICE.get());
            event.accept(ModItems.MALEDICTUS_HEART.get());
            event.accept(ModItems.MALEDICTUS_HEART_STEW.get());
            event.accept(ModItems.NETHERITE_MONSTROSITY_MEAT.get());
            event.accept(ModItems.PASTA_WITH_WITHERITE.get());
            event.accept(ModItems.RAW_DEEPLING_MEAT.get());
            event.accept(ModItems.RED_CORAL_STEW.get());
            event.accept(ModItems.VOID_CUSTARD.get());
            event.accept(ModItems.VOID_POPSICLE.get());
            event.accept(ModItems.WATCHER_HEART.get());
            event.accept(ModItems.ENDER_GOLEM_MEAT.get());
            event.accept(ModItems.HONEY_GLAZED_HORN.get());
            event.accept(ModItems.ENDER_GUARDIAN_CRYSTAL.get());
            event.accept(ModItems.CRYSTALLIZED_CORAL_PIE.get());
            event.accept(ModItems.CRYSTALLIZED_CORAL_PIE_SLICE.get());
        }

        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.ABYSSAL_KNIFE.get());
            event.accept(ModItems.ANCIENT_KNIFE.get());
            event.accept(ModItems.BLACK_STEEL_KNIFE.get());
            event.accept(ModItems.IGNITIUM_KNIFE.get());
            event.accept(ModItems.WITHERITE_KNIFE.get());
            event.accept(ModItems.CURSIUM_KNIFE.get());
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
