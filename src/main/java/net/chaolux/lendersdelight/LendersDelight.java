package net.chaolux.lendersdelight;

import com.mojang.logging.LogUtils;
import net.chaolux.lendersdelight.registry.block.ModBlocks;
import net.chaolux.lendersdelight.registry.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(LendersDelight.MOD_ID)
public class LendersDelight
{
    public static final String MOD_ID = "lendersdelight";
    private static final Logger LOGGER = LogUtils.getLogger();

    public LendersDelight()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModItems.AMETHYST_CRAB_MEAT_STICK);
            event.accept(ModItems.AMETHYST_CRAB_SANDWICH);
            event.accept(ModItems.BERSERKER);
            event.accept(ModItems.BERSERKER_STICK);
            event.accept(ModItems.COBOLETON_MEAT);
            event.accept(ModItems.COOKED_AMETHYST_CRAB_MEAT);
            event.accept(ModItems.COOKED_BERSERKER);
            event.accept(ModItems.COOKED_COBOLETON_MEAT);
            event.accept(ModItems.COOKED_CORAL_GOLEM_MEAT);
            event.accept(ModItems.COOKED_ENDER_GOLEM_MEAT);
            event.accept(ModItems.COOKED_LEVIATHAN);
            event.accept(ModItems.COOKED_LIONFISH);
            event.accept(ModItems.COOKED_NETHERITE_MONSTROSITY_MEAT);
            event.accept(ModItems.CORAL_AND_VOID);
            event.accept(ModItems.CORAL_CHUNK_SANDWICH);
            event.accept(ModItems.CORAL_GOLEM_MEAT);
            event.accept(ModItems.CRYSTALLIZED_CORAL_POTATO);
            event.accept(ModItems.CRYSTALLIZED_CORAL_ROLL);
            event.accept(ModItems.AMETHYST_CRAB_MEAT_SLICE);
            event.accept(ModItems.COOKED_AMETHYST_CRAB_MEAT_SLICE);
            event.accept(ModItems.CORAL_CHUNK_RED_SLICE);
            event.accept(ModItems.MALEDICTUS_HEART_SLICE);
            event.accept(ModItems.DEEPLING_MEAT);
            event.accept(ModItems.ENDER_BLOOD);
            event.accept(ModItems.ENDER_BLOOD_GLAZED_MALEDICTUS_HEART);
            event.accept(ModItems.FRIED_ABYSSAL_EGG);
            event.accept(ModItems.GLAZED_REMNANT_SKULL);
            event.accept(ModItems.GRILLED_LIONFISH);
            event.accept(ModItems.HAM_OF_BERSERKER);
            event.accept(ModItems.IGNIS);
            event.accept(ModItems.IMPROVED_DOG_FOOD);
            event.accept(ModItems.KOBOLETON_PUMPKIN);
            event.accept(ModItems.LEVIATHAN);
            event.accept(ModItems.LEVIATHAN_AND_ABYSSAL_EGG);
            event.accept(ModItems.LIONFISH_ROLL);
            event.accept(ModItems.LIONFISH_SLICE);
            event.accept(ModItems.MALEDICTUS_HEART);
            event.accept(ModItems.MALEDICTUS_HEART_STEW);
            event.accept(ModItems.NETHERITE_MONSTROSITY_MEAT);
            event.accept(ModItems.PASTA_WITH_WITHERITE);
            event.accept(ModItems.RAW_DEEPLING_MEAT);
            event.accept(ModItems.RED_CORAL_STEW);
            event.accept(ModItems.VOID_CUSTARD);
            event.accept(ModItems.VOID_POPSICLE);
            event.accept(ModItems.WATCHER_HEART);
            event.accept(ModItems.ENDER_GOLEM_MEAT);
            event.accept(ModItems.HONEY_GLAZED_HORN);
        }

        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.ABYSSAL_KNIFE);
            event.accept(ModItems.ANCIENT_KNIFE);
            event.accept(ModItems.BLACK_STEEL_KNIFE);
            event.accept(ModItems.IGNITIUM_KNIFE);
            event.accept(ModItems.WITHERITE_KNIFE);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
