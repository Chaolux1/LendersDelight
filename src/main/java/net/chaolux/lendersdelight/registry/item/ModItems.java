package net.chaolux.lendersdelight.registry.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DogFoodItem;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.function.Supplier;

import static vectorwing.farmersdelight.common.registry.ModItems.knifeItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS;

    public static final Supplier<Item> AMETHYST_CRAB_MEAT_STICK;
    public static final Supplier<Item> AMETHYST_CRAB_SANDWICH;
    public static final Supplier<Item> BERSERKER;
    public static final Supplier<Item> BERSERKER_STICK;
    public static final Supplier<Item> COBOLETON_MEAT;
    public static final Supplier<Item> COOKED_AMETHYST_CRAB_MEAT;
    public static final Supplier<Item> COOKED_BERSERKER;
    public static final Supplier<Item> COOKED_COBOLETON_MEAT;
    public static final Supplier<Item> COOKED_CORAL_GOLEM_MEAT;
    public static final Supplier<Item> COOKED_ENDER_GOLEM_MEAT;
    public static final Supplier<Item> COOKED_LEVIATHAN;
    public static final Supplier<Item> COOKED_LIONFISH;
    public static final Supplier<Item> COOKED_NETHERITE_MONSTROSITY_MEAT;
    public static final Supplier<Item> CORAL_AND_VOID;
    public static final Supplier<Item> CORAL_CHUNK_SANDWICH;
    public static final Supplier<Item> CORAL_GOLEM_MEAT;
    public static final Supplier<Item> CRYSTALLIZED_CORAL_POTATO;
    public static final Supplier<Item> CRYSTALLIZED_CORAL_ROLL;
    public static final Supplier<Item> AMETHYST_CRAB_MEAT_SLICE;
    public static final Supplier<Item> COOKED_AMETHYST_CRAB_MEAT_SLICE;
    public static final Supplier<Item> CORAL_CHUNK_RED_SLICE;
    public static final Supplier<Item> MALEDICTUS_HEART_SLICE;
    public static final Supplier<Item> DEEPLING_MEAT;
    public static final Supplier<Item> ENDER_BLOOD;
    public static final Supplier<Item> ENDER_BLOOD_GLAZED_MALEDICTUS_HEART;
    public static final Supplier<Item> FRIED_ABYSSAL_EGG;
    public static final Supplier<Item> GLAZED_REMNANT_SKULL;
    public static final Supplier<Item> GRILLED_LIONFISH;
    public static final Supplier<Item> HAM_OF_BERSERKER;
    public static final Supplier<Item> IGNIS;
    public static final Supplier<Item> IMPROVED_DOG_FOOD;
    public static final Supplier<Item> KOBOLETON_PUMPKIN;
    public static final Supplier<Item> LEVIATHAN;
    public static final Supplier<Item> LEVIATHAN_AND_ABYSSAL_EGG;
    public static final Supplier<Item> LIONFISH_ROLL;
    public static final Supplier<Item> LIONFISH_SLICE;
    public static final Supplier<Item> MALEDICTUS_HEART;
    public static final Supplier<Item> MALEDICTUS_HEART_STEW;
    public static final Supplier<Item> NETHERITE_MONSTROSITY_MEAT;
    public static final Supplier<Item> PASTA_WITH_WITHERITE;
    public static final Supplier<Item> RAW_DEEPLING_MEAT;
    public static final Supplier<Item> RED_CORAL_STEW;
    public static final Supplier<Item> VOID_CUSTARD;
    public static final Supplier<Item> VOID_POPSICLE;
    public static final Supplier<Item> WATCHER_HEART;
    public static final Supplier<Item> ENDER_GOLEM_MEAT;
    public static final Supplier<Item> ABYSSAL_KNIFE;
    public static final Supplier<Item> ANCIENT_KNIFE;
    public static final Supplier<Item> BLACK_STEEL_KNIFE;
    public static final Supplier<Item> IGNITIUM_KNIFE;
    public static final Supplier<Item> WITHERITE_KNIFE;

    public static Supplier<Item> registerWithTab(String name, Supplier<Item> supplier) {
        Supplier<Item> block = ITEMS.register(name, supplier);
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
        ITEMS = DeferredRegister.create(Registries.ITEM, "lendersdelight");

        CORAL_AND_VOID = registerWithTab("coral_and_void", () -> new ConsumableItem(bowlFoodItem(FoodValues.CORAL_AND_VOID), true));
        ENDER_BLOOD_GLAZED_MALEDICTUS_HEART = registerWithTab("ender_blood_glazed_maledictus_heart", () -> new ConsumableItem(bowlFoodItem(FoodValues.ENDER_BLOOD_GLAZED_MALEDICTUS_HEART), true));
        GLAZED_REMNANT_SKULL = registerWithTab("glazed_remnant_skull", () -> new ConsumableItem(bowlFoodItem(FoodValues.GLAZED_REMNANT_SKULL), true));
        GRILLED_LIONFISH = registerWithTab("grilled_lionfish", () -> new ConsumableItem(bowlFoodItem(FoodValues.GRILLED_LIONFISH), true));
        HAM_OF_BERSERKER = registerWithTab("ham_of_berserker", () -> new ConsumableItem(bowlFoodItem(FoodValues.HAM_OF_BERSERKER), true));
        LEVIATHAN_AND_ABYSSAL_EGG = registerWithTab("leviathan_and_abyssal_egg", () -> new ConsumableItem(bowlFoodItem(FoodValues.LEVIATHAN_AND_ABYSSAL_EGG), true));
        MALEDICTUS_HEART_STEW = registerWithTab("maledictus_heart_stew", () -> new ConsumableItem(bowlFoodItem(FoodValues.MALEDICTUS_HEART_STEW), true));
        PASTA_WITH_WITHERITE = registerWithTab("pasta_with_witherite", () -> new ConsumableItem(bowlFoodItem(FoodValues.PASTA_WITH_WITHERITE), false));
        RED_CORAL_STEW = registerWithTab("red_coral_stew", () -> new ConsumableItem(bowlFoodItem(FoodValues.RED_CORAL_STEW), true));

        ENDER_BLOOD = registerWithTab("ender_blood", () -> new ConsumableItem(drinkItem(FoodValues.ENDER_BLOOD)));
        VOID_CUSTARD = registerWithTab("void_custard", () -> new ConsumableItem(drinkItem(FoodValues.VOID_CUSTARD)));

        AMETHYST_CRAB_MEAT_STICK = registerWithTab("amethyst_crab_meat_stick", () -> new ConsumableItem(stickItem(FoodValues.AMETHYST_CRAB_MEAT_STICK)));
        BERSERKER_STICK = registerWithTab("berserker_stick", () -> new ConsumableItem(stickItem(FoodValues.BERSERKER_STICK)));
        VOID_POPSICLE = registerWithTab("void_popsicle", () -> new ConsumableItem(stickItem(FoodValues.VOID_POPSICLE)));

        AMETHYST_CRAB_SANDWICH = registerWithTab("amethyst_crab_sandwich", () -> new Item(foodItem(FoodValues.AMETHYST_CRAB_SANDWICH)));
        BERSERKER = registerWithTab("berserker", () -> new Item(foodItem(FoodValues.BERSERKER)));
        COBOLETON_MEAT = registerWithTab("coboleton_meat", () -> new Item(foodItem(FoodValues.COBOLETON_MEAT)));
        COOKED_AMETHYST_CRAB_MEAT = registerWithTab("cooked_amethyst_crab_meat", () -> new Item(foodItem(FoodValues.COOKED_AMETHYST_CRAB_MEAT)));
        COOKED_BERSERKER = registerWithTab("cooked_berserker", () -> new Item(foodItem(FoodValues.COOKED_BERSERKER)));
        COOKED_COBOLETON_MEAT = registerWithTab("cooked_coboleton_meat", () -> new Item(foodItem(FoodValues.COOKED_COBOLETON_MEAT)));
        COOKED_CORAL_GOLEM_MEAT = registerWithTab("cooked_coral_golem_meat", () -> new Item(foodItem(FoodValues.COOKED_CORAL_GOLEM_MEAT)));
        COOKED_ENDER_GOLEM_MEAT = registerWithTab("cooked_ender_golem_meat", () -> new Item(foodItem(FoodValues.COOKED_ENDER_GOLEM_MEAT)));
        COOKED_LEVIATHAN = registerWithTab("cooked_leviathan", () -> new Item(foodItem(FoodValues.COOKED_LEVIATHAN)));
        COOKED_LIONFISH = registerWithTab("cooked_lionfish", () -> new Item(foodItem(FoodValues.COOKED_LIONFISH)));
        COOKED_NETHERITE_MONSTROSITY_MEAT = registerWithTab("cooked_netherite_monstrosity_meat", () -> new Item(foodItem(FoodValues.COOKED_NETHERITE_MONSTROSITY_MEAT)));
        CORAL_CHUNK_SANDWICH = registerWithTab("coral_chunk_sandwich", () -> new Item(foodItem(FoodValues.CORAL_CHUNK_SANDWICH)));
        CORAL_GOLEM_MEAT = registerWithTab("coral_golem_meat", () -> new Item(foodItem(FoodValues.CORAL_GOLEM_MEAT)));
        CRYSTALLIZED_CORAL_POTATO = registerWithTab("crystallized_coral_potato", () -> new Item(foodItem(FoodValues.CRYSTALLIZED_CORAL_POTATO)));
        CRYSTALLIZED_CORAL_ROLL = registerWithTab("crystallized_coral_roll", () -> new Item(foodItem(FoodValues.CRYSTALLIZED_CORAL_ROLL)));
        AMETHYST_CRAB_MEAT_SLICE = registerWithTab("amethyst_crab_meat_slice", () -> new Item(foodItem(FoodValues.AMETHYST_CRAB_MEAT_SLICE)));
        COOKED_AMETHYST_CRAB_MEAT_SLICE = registerWithTab("cooked_amethyst_crab_meat_slice", () -> new Item(foodItem(FoodValues.COOKED_AMETHYST_CRAB_MEAT_SLICE)));
        CORAL_CHUNK_RED_SLICE = registerWithTab("coral_chunk_red_slice", () -> new Item(foodItem(FoodValues.CORAL_CHUNK_RED_SLICE)));
        MALEDICTUS_HEART_SLICE = registerWithTab("maledictus_heart_slice", () -> new Item(foodItem(FoodValues.MALEDICTUS_HEART_SLICE)));
        DEEPLING_MEAT = registerWithTab("deepling_meat", () -> new Item(foodItem(FoodValues.DEEPLING_MEAT)));
        FRIED_ABYSSAL_EGG = registerWithTab("fried_abyssal_egg", () -> new Item(foodItem(FoodValues.FRIED_ABYSSAL_EGG)));
        IGNIS = registerWithTab("ignis", () -> new Item(foodItem(FoodValues.IGNIS)));
        KOBOLETON_PUMPKIN = registerWithTab("koboleton_pumpkin", () -> new Item(foodItem(FoodValues.KOBOLETON_PUMPKIN)));
        LEVIATHAN = registerWithTab("leviathan", () -> new Item(foodItem(FoodValues.LEVIATHAN)));
        LIONFISH_ROLL = registerWithTab("lionfish_roll", () -> new Item(foodItem(FoodValues.LIONFISH_ROLL)));
        LIONFISH_SLICE = registerWithTab("lionfish_slice", () -> new Item(foodItem(FoodValues.LIONFISH_SLICE)));
        MALEDICTUS_HEART = registerWithTab("maledictus_heart", () -> new Item(foodItem(FoodValues.MALEDICTUS_HEART)));
        NETHERITE_MONSTROSITY_MEAT = registerWithTab("netherite_monstrosity_meat", () -> new Item(foodItem(FoodValues.NETHERITE_MONSTROSITY_MEAT)));
        RAW_DEEPLING_MEAT = registerWithTab("raw_deepling_meat", () -> new Item(foodItem(FoodValues.RAW_DEEPLING_MEAT)));
        WATCHER_HEART = registerWithTab("watcher_heart", () -> new Item(foodItem(FoodValues.WATCHER_HEART)));
        ENDER_GOLEM_MEAT = registerWithTab("ender_golem_meat", () -> new Item(foodItem(FoodValues.ENDER_GOLEM_MEAT)));

        IMPROVED_DOG_FOOD = registerWithTab("improved_dog_food", () -> new DogFoodItem(bowlFoodItem(FoodValues.IMPROVED_DOG_FOOD)));

        ABYSSAL_KNIFE = registerWithTab("abyssal_knife", () -> new AbyssalKnife(Tiers.NETHERITE, knifeItem(Tiers.NETHERITE).fireResistant()));
        ANCIENT_KNIFE = registerWithTab("ancient_knife", () -> new KnifeItem(Tiers.DIAMOND, knifeItem(Tiers.DIAMOND)));
        BLACK_STEEL_KNIFE = registerWithTab("black_steel_knife", () -> new KnifeItem(Tiers.DIAMOND, knifeItem(Tiers.DIAMOND)));
        IGNITIUM_KNIFE = registerWithTab("ignitium_knife", () -> new KnifeItem(Tiers.DIAMOND, knifeItem(Tiers.DIAMOND)));
        WITHERITE_KNIFE = registerWithTab("witherite_knife", () -> new KnifeItem(Tiers.DIAMOND, knifeItem(Tiers.DIAMOND)));
    }
}
