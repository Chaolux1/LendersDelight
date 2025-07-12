package net.chaolux.lendersdelight.registry.item;

import net.chaolux.lendersdelight.coomon.item.AbyssalKnife;
import net.chaolux.lendersdelight.coomon.item.FoodValues;
import net.chaolux.lendersdelight.coomon.item.ImprovedDogFoodItem;
import net.chaolux.lendersdelight.coomon.item.VoidPopsicle;
import net.chaolux.lendersdelight.coomon.stat.StatConsumableItem;
import net.chaolux.lendersdelight.coomon.stat.StatType;
import net.chaolux.lendersdelight.registry.block.ModBlocks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.Map;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS;

    public static final RegistryObject<Item> AMETHYST_CRAB_MEAT_STICK;
    public static final RegistryObject<Item> AMETHYST_CRAB_SANDWICH;
    public static final RegistryObject<Item> BERSERKER;
    public static final RegistryObject<Item> BERSERKER_STICK;
    public static final RegistryObject<Item> KOBOLETON_MEAT;
    public static final RegistryObject<Item> COOKED_AMETHYST_CRAB_MEAT;
    public static final RegistryObject<Item> COOKED_BERSERKER;
    public static final RegistryObject<Item> COOKED_KOBOLETON_MEAT;
    public static final RegistryObject<Item> COOKED_CORAL_GOLEM_MEAT;
    public static final RegistryObject<Item> COOKED_ENDER_GOLEM_MEAT;
    public static final RegistryObject<Item> COOKED_LEVIATHAN;
    public static final RegistryObject<Item> COOKED_LIONFISH;
    public static final RegistryObject<Item> COOKED_NETHERITE_MONSTROSITY_MEAT;
    public static final RegistryObject<Item> CORAL_AND_VOID;
    public static final RegistryObject<Item> CORAL_CHUNK_SANDWICH;
    public static final RegistryObject<Item> CORAL_GOLEM_MEAT;
    public static final RegistryObject<Item> CRYSTALLIZED_CORAL_POTATO;
    public static final RegistryObject<Item> CRYSTALLIZED_CORAL_ROLL;
    public static final RegistryObject<Item> AMETHYST_CRAB_MEAT_SLICE;
    public static final RegistryObject<Item> COOKED_AMETHYST_CRAB_MEAT_SLICE;
    public static final RegistryObject<Item> CORAL_CHUNK_RED_SLICE;
    public static final RegistryObject<Item> MALEDICTUS_HEART_SLICE;
    public static final RegistryObject<Item> DEEPLING_MEAT;
    public static final RegistryObject<Item> ENDER_BLOOD;
    public static final RegistryObject<Item> ENDER_BLOOD_GLAZED_MALEDICTUS_HEART;
    public static final RegistryObject<Item> FRIED_ABYSSAL_EGG;
    public static final RegistryObject<Item> GLAZED_REMNANT_SKULL;
    public static final RegistryObject<Item> GRILLED_LIONFISH;
    public static final RegistryObject<Item> HAM_OF_BERSERKER;
    public static final RegistryObject<Item> IGNIS;
    public static final RegistryObject<Item> IMPROVED_DOG_FOOD;
    public static final RegistryObject<Item> KOBOLETON_PUMPKIN;
    public static final RegistryObject<Item> LEVIATHAN;
    public static final RegistryObject<Item> LEVIATHAN_AND_ABYSSAL_EGG;
    public static final RegistryObject<Item> LIONFISH_ROLL;
    public static final RegistryObject<Item> LIONFISH_SLICE;
    public static final RegistryObject<Item> MALEDICTUS_HEART;
    public static final RegistryObject<Item> MALEDICTUS_HEART_STEW;
    public static final RegistryObject<Item> NETHERITE_MONSTROSITY_MEAT;
    public static final RegistryObject<Item> PASTA_WITH_WITHERITE;
    public static final RegistryObject<Item> RAW_DEEPLING_MEAT;
    public static final RegistryObject<Item> RED_CORAL_STEW;
    public static final RegistryObject<Item> VOID_CUSTARD;
    public static final RegistryObject<Item> VOID_POPSICLE;
    public static final RegistryObject<Item> WATCHER_HEART;
    public static final RegistryObject<Item> ENDER_GOLEM_MEAT;
    public static final RegistryObject<Item> ABYSSAL_KNIFE;
    public static final RegistryObject<Item> ANCIENT_KNIFE;
    public static final RegistryObject<Item> BLACK_STEEL_KNIFE;
    public static final RegistryObject<Item> IGNITIUM_KNIFE;
    public static final RegistryObject<Item> WITHERITE_KNIFE;
    public static final RegistryObject<Item> HONEY_GLAZED_HORN;
    public static final RegistryObject<Item> CURSIUM_KNIFE;
    public static final RegistryObject<Item> ENDER_GUARDIAN_CRYSTAL;
    public static final RegistryObject<Item> CRYSTALLIZED_CORAL_PIE;
    public static final RegistryObject<Item> CRYSTALLIZED_CORAL_PIE_SLICE;

    public static RegistryObject<Item> registerWithTab(String name, Supplier<Item> supplier) {
        RegistryObject<Item> block = ITEMS.register(name, supplier);
        return block;
    }

    public static Item.Properties basicItem() {
        return new Item.Properties();
    }

    public static Item.Properties bowlFoodItem(FoodProperties food) {
        return (new Item.Properties()).food(food).craftRemainder(Items.BOWL).stacksTo(16);
    }

    public static Item.Properties drinkItem(FoodProperties food) {
        return (new Item.Properties()).food(food).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16);
    }

    public static Item.Properties stickItem(FoodProperties food) {
        return (new Item.Properties()).food(food).craftRemainder(Items.STICK);
    }

    public static Item.Properties foodItem(FoodProperties food) {
        return (new Item.Properties()).food(food);
    }

    static {
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "lendersdelight");

        CORAL_AND_VOID = registerWithTab("coral_and_void", () -> new ConsumableItem(bowlFoodItem(FoodValues.CORAL_AND_VOID), true));
        ENDER_BLOOD_GLAZED_MALEDICTUS_HEART = registerWithTab("ender_blood_glazed_maledictus_heart", () -> new ConsumableItem(bowlFoodItem(FoodValues.ENDER_BLOOD_GLAZED_MALEDICTUS_HEART), true));
        GLAZED_REMNANT_SKULL = registerWithTab("glazed_remnant_skull", () -> new ConsumableItem(bowlFoodItem(FoodValues.GLAZED_REMNANT_SKULL), true));
        GRILLED_LIONFISH = registerWithTab("grilled_lionfish", () -> new Item(bowlFoodItem(FoodValues.GRILLED_LIONFISH)));
        HAM_OF_BERSERKER = registerWithTab("ham_of_berserker", () -> new ConsumableItem(bowlFoodItem(FoodValues.HAM_OF_BERSERKER), true));
        LEVIATHAN_AND_ABYSSAL_EGG = registerWithTab("leviathan_and_abyssal_egg", () -> new ConsumableItem(bowlFoodItem(FoodValues.LEVIATHAN_AND_ABYSSAL_EGG), true));
        MALEDICTUS_HEART_STEW = registerWithTab("maledictus_heart_stew", () -> new ConsumableItem(bowlFoodItem(FoodValues.MALEDICTUS_HEART_STEW), true));
        PASTA_WITH_WITHERITE = registerWithTab("pasta_with_witherite", () -> new ConsumableItem(bowlFoodItem(FoodValues.PASTA_WITH_WITHERITE), true));
        RED_CORAL_STEW = registerWithTab("red_coral_stew", () -> new ConsumableItem(bowlFoodItem(FoodValues.RED_CORAL_STEW), true));
        HONEY_GLAZED_HORN = registerWithTab("honey_glazed_horn", () -> new ConsumableItem(bowlFoodItem(FoodValues.HONEY_GLAZED_HORN), true));

        ENDER_BLOOD = registerWithTab("ender_blood", () -> new ConsumableItem(drinkItem(FoodValues.ENDER_BLOOD), true));
        VOID_CUSTARD = registerWithTab("void_custard", () -> new ConsumableItem(drinkItem(FoodValues.VOID_CUSTARD), true));

        AMETHYST_CRAB_MEAT_STICK = registerWithTab("amethyst_crab_meat_stick", () -> new ConsumableItem(stickItem(FoodValues.AMETHYST_CRAB_MEAT_STICK), true));
        BERSERKER_STICK = registerWithTab("berserker_stick", () -> new ConsumableItem(stickItem(FoodValues.BERSERKER_STICK), true));
        VOID_POPSICLE = registerWithTab("void_popsicle", () -> new VoidPopsicle(stickItem(FoodValues.VOID_POPSICLE)));

        AMETHYST_CRAB_SANDWICH = registerWithTab("amethyst_crab_sandwich", () -> new Item(foodItem(FoodValues.AMETHYST_CRAB_SANDWICH)));
        BERSERKER = registerWithTab("berserker", () -> new Item(foodItem(FoodValues.BERSERKER)));
        KOBOLETON_MEAT = registerWithTab("koboleton_meat", () -> new Item(foodItem(FoodValues.KOBOLETON_MEAT)));
        COOKED_AMETHYST_CRAB_MEAT = registerWithTab("cooked_amethyst_crab_meat", () -> new Item(foodItem(FoodValues.COOKED_AMETHYST_CRAB_MEAT)));
        COOKED_BERSERKER = registerWithTab("cooked_berserker", () -> new Item(foodItem(FoodValues.COOKED_BERSERKER)));
        COOKED_KOBOLETON_MEAT = registerWithTab("cooked_koboleton_meat", () -> new Item(foodItem(FoodValues.COOKED_KOBOLETON_MEAT)));
        COOKED_CORAL_GOLEM_MEAT = registerWithTab("cooked_coral_golem_meat", () -> new Item(foodItem(FoodValues.COOKED_CORAL_GOLEM_MEAT)));
        COOKED_ENDER_GOLEM_MEAT = registerWithTab("cooked_ender_golem_meat", () -> new Item(foodItem(FoodValues.COOKED_ENDER_GOLEM_MEAT)));
        COOKED_LEVIATHAN = registerWithTab("cooked_leviathan", () -> new ConsumableItem(foodItem(FoodValues.COOKED_LEVIATHAN), true));
        COOKED_LIONFISH = registerWithTab("cooked_lionfish", () -> new Item(foodItem(FoodValues.COOKED_LIONFISH)));
        COOKED_NETHERITE_MONSTROSITY_MEAT = registerWithTab("cooked_netherite_monstrosity_meat", () -> new Item(foodItem(FoodValues.COOKED_NETHERITE_MONSTROSITY_MEAT)));
        CORAL_CHUNK_SANDWICH = registerWithTab("coral_chunk_sandwich", () -> new Item(foodItem(FoodValues.CORAL_CHUNK_SANDWICH)));
        CORAL_GOLEM_MEAT = registerWithTab("coral_golem_meat", () -> new Item(foodItem(FoodValues.CORAL_GOLEM_MEAT)));
        CRYSTALLIZED_CORAL_POTATO = registerWithTab("crystallized_coral_potato", () -> new ConsumableItem(foodItem(FoodValues.CRYSTALLIZED_CORAL_POTATO), true));
        CRYSTALLIZED_CORAL_ROLL = registerWithTab("crystallized_coral_roll", () -> new Item(foodItem(FoodValues.CRYSTALLIZED_CORAL_ROLL)));
        AMETHYST_CRAB_MEAT_SLICE = registerWithTab("amethyst_crab_meat_slice", () -> new Item(foodItem(FoodValues.AMETHYST_CRAB_MEAT_SLICE)));
        COOKED_AMETHYST_CRAB_MEAT_SLICE = registerWithTab("cooked_amethyst_crab_meat_slice", () -> new Item(foodItem(FoodValues.COOKED_AMETHYST_CRAB_MEAT_SLICE)));
        CORAL_CHUNK_RED_SLICE = registerWithTab("coral_chunk_red_slice", () -> new Item(foodItem(FoodValues.CORAL_CHUNK_RED_SLICE)));
        MALEDICTUS_HEART_SLICE = registerWithTab("maledictus_heart_slice", () -> new Item(foodItem(FoodValues.MALEDICTUS_HEART_SLICE)));
        DEEPLING_MEAT = registerWithTab("deepling_meat", () -> new Item(foodItem(FoodValues.DEEPLING_MEAT)));
        FRIED_ABYSSAL_EGG = registerWithTab("fried_abyssal_egg", () -> new ConsumableItem(foodItem(FoodValues.FRIED_ABYSSAL_EGG),true));
        IGNIS = registerWithTab("ignis", () -> new ConsumableItem(foodItem(FoodValues.IGNIS), true));
        KOBOLETON_PUMPKIN = registerWithTab("koboleton_pumpkin", () -> new ConsumableItem(foodItem(FoodValues.KOBOLETON_PUMPKIN), true));
        LEVIATHAN = registerWithTab("leviathan", () -> new ConsumableItem(foodItem(FoodValues.LEVIATHAN), true));
        LIONFISH_ROLL = registerWithTab("lionfish_roll", () -> new StatConsumableItem(foodItem(FoodValues.LIONFISH_ROLL),true, Map.of(StatType.JUMP_BOOST,50.0f,StatType.CRIT_CHANCE,30.0f)));
        LIONFISH_SLICE = registerWithTab("lionfish_slice", () -> new Item(foodItem(FoodValues.LIONFISH_SLICE)));
        MALEDICTUS_HEART = registerWithTab("maledictus_heart", () -> new ConsumableItem(foodItem(FoodValues.MALEDICTUS_HEART), true));
        NETHERITE_MONSTROSITY_MEAT = registerWithTab("netherite_monstrosity_meat", () -> new Item(foodItem(FoodValues.NETHERITE_MONSTROSITY_MEAT)));
        RAW_DEEPLING_MEAT = registerWithTab("raw_deepling_meat", () -> new Item(foodItem(FoodValues.RAW_DEEPLING_MEAT)));
        WATCHER_HEART = registerWithTab("watcher_heart", () -> new ConsumableItem(foodItem(FoodValues.WATCHER_HEART), true));
        ENDER_GOLEM_MEAT = registerWithTab("ender_golem_meat", () -> new Item(foodItem(FoodValues.ENDER_GOLEM_MEAT)));
        ENDER_GUARDIAN_CRYSTAL = registerWithTab("ender_guardian_crystal", () -> new ConsumableItem(foodItem(FoodValues.ENDER_GUARDIAN_CRYSTAL), true));
        CRYSTALLIZED_CORAL_PIE_SLICE = registerWithTab("crystallized_coral_pie_slice", () -> new Item(foodItem(FoodValues.CRYSTALLIZED_CORAL_PIE_SLICE)));
        CRYSTALLIZED_CORAL_PIE = registerWithTab("crystallized_coral_pie", () -> new BlockItem((Block) ModBlocks.CRYSTALLIZED_CORAL_PIE.get(), basicItem()));

        IMPROVED_DOG_FOOD = registerWithTab("improved_dog_food", () -> new ImprovedDogFoodItem(bowlFoodItem(FoodValues.IMPROVED_DOG_FOOD)));

        ABYSSAL_KNIFE = registerWithTab("abyssal_knife", () -> new AbyssalKnife(Tiers.NETHERITE, 0.5F, -2.0F, basicItem().fireResistant()));
        ANCIENT_KNIFE = registerWithTab("ancient_knife", () -> new KnifeItem(Tiers.DIAMOND, 0.5F, -2.0F, basicItem()));
        BLACK_STEEL_KNIFE = registerWithTab("black_steel_knife", () -> new KnifeItem(Tiers.DIAMOND, 0.5F, -2.0F, basicItem()));
        IGNITIUM_KNIFE = registerWithTab("ignitium_knife", () -> new KnifeItem(Tiers.DIAMOND, 0.5F, -2.0F, basicItem()));
        WITHERITE_KNIFE = registerWithTab("witherite_knife", () -> new KnifeItem(Tiers.DIAMOND, 0.5F, -2.0F, basicItem()));
        CURSIUM_KNIFE = registerWithTab("cursium_knife", () -> new KnifeItem(Tiers.NETHERITE, 0.5F, -2.0F, basicItem().fireResistant()));

    }
}
