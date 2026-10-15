package net.chaolux.lendersdelight.registry.item;

import net.chaolux.lendersdelight.coomon.item.*;
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
    public static final RegistryObject<Item> CINDARIA_HAT;
    public static final RegistryObject<Item> COOKED_CRAB_LEGS;
    public static final RegistryObject<Item> CRAB_LEGS;
    public static final RegistryObject<Item> HIPPOCAMTUS;
    public static final RegistryObject<Item> SCYLLA_SNAKE;
    public static final RegistryObject<Item> SCYLLA_SNAKE_GLAZED_CINDARIA_HAT;
    public static final RegistryObject<Item> SEA_MEAT_SALAD;
    public static final RegistryObject<Item> SYMBIOCTO_TENTACLE;
    public static final RegistryObject<Item> URCHINKIN;
    public static final RegistryObject<Item> URCHINKIN_PUMPKIN;
    public static final RegistryObject<Item> LACRIMA_ANCHOR_KNIFE;
    public static final RegistryObject<Item> RAW_BRUTE_DEEPLING_MEAT;
    public static final RegistryObject<Item> RAW_WARLOCK_DEEPLING_MEAT;

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

        CORAL_AND_VOID = registerWithTab("coral_and_void", () -> new StatConsumableItem(bowlFoodItem(FoodValues.CORAL_AND_VOID), true));
        ENDER_BLOOD_GLAZED_MALEDICTUS_HEART = registerWithTab("ender_blood_glazed_maledictus_heart", () -> new StatConsumableItem(bowlFoodItem(FoodValues.ENDER_BLOOD_GLAZED_MALEDICTUS_HEART), true));
        GLAZED_REMNANT_SKULL = registerWithTab("glazed_remnant_skull", () -> new StatConsumableItem(bowlFoodItem(FoodValues.GLAZED_REMNANT_SKULL), true));
        GRILLED_LIONFISH = registerWithTab("grilled_lionfish", () -> new StatConsumableItem(bowlFoodItem(FoodValues.GRILLED_LIONFISH),false));
        HAM_OF_BERSERKER = registerWithTab("ham_of_berserker", () -> new StatConsumableItem(bowlFoodItem(FoodValues.HAM_OF_BERSERKER), true));
        LEVIATHAN_AND_ABYSSAL_EGG = registerWithTab("leviathan_and_abyssal_egg", () -> new StatConsumableItem(bowlFoodItem(FoodValues.LEVIATHAN_AND_ABYSSAL_EGG), true));
        MALEDICTUS_HEART_STEW = registerWithTab("maledictus_heart_stew", () -> new StatConsumableItem(bowlFoodItem(FoodValues.MALEDICTUS_HEART_STEW), true));
        PASTA_WITH_WITHERITE = registerWithTab("pasta_with_witherite", () -> new StatConsumableItem(bowlFoodItem(FoodValues.PASTA_WITH_WITHERITE), true));
        RED_CORAL_STEW = registerWithTab("red_coral_stew", () -> new StatConsumableItem(bowlFoodItem(FoodValues.RED_CORAL_STEW), true));
        HONEY_GLAZED_HORN = registerWithTab("honey_glazed_horn", () -> new StatConsumableItem(bowlFoodItem(FoodValues.HONEY_GLAZED_HORN), true));

        ENDER_BLOOD = registerWithTab("ender_blood", () -> new StatConsumableItem(drinkItem(FoodValues.ENDER_BLOOD), true));
        VOID_CUSTARD = registerWithTab("void_custard", () -> new StatConsumableItem(drinkItem(FoodValues.VOID_CUSTARD), true));

        AMETHYST_CRAB_MEAT_STICK = registerWithTab("amethyst_crab_meat_stick", () -> new StatConsumableItem(stickItem(FoodValues.AMETHYST_CRAB_MEAT_STICK), true));
        BERSERKER_STICK = registerWithTab("berserker_stick", () -> new StatConsumableItem(stickItem(FoodValues.BERSERKER_STICK), true));
        VOID_POPSICLE = registerWithTab("void_popsicle", () -> new VoidPopsicle(stickItem(FoodValues.VOID_POPSICLE)));

        AMETHYST_CRAB_SANDWICH = registerWithTab("amethyst_crab_sandwich", () -> new StatConsumableItem(foodItem(FoodValues.AMETHYST_CRAB_SANDWICH),true));
        BERSERKER = registerWithTab("berserker", () -> new StatConsumableItem(foodItem(FoodValues.BERSERKER),false));
        KOBOLETON_MEAT = registerWithTab("koboleton_meat", () -> new StatConsumableItem(foodItem(FoodValues.KOBOLETON_MEAT),false));
        COOKED_AMETHYST_CRAB_MEAT = registerWithTab("cooked_amethyst_crab_meat", () -> new CookedAmethystCrabMeatItem(foodItem(FoodValues.COOKED_AMETHYST_CRAB_MEAT)));
        COOKED_BERSERKER = registerWithTab("cooked_berserker", () -> new StatConsumableItem(foodItem(FoodValues.COOKED_BERSERKER),false));
        COOKED_KOBOLETON_MEAT = registerWithTab("cooked_koboleton_meat", () -> new StatConsumableItem(foodItem(FoodValues.COOKED_KOBOLETON_MEAT),false));
        COOKED_CORAL_GOLEM_MEAT = registerWithTab("cooked_coral_golem_meat", () -> new StatConsumableItem(foodItem(FoodValues.COOKED_CORAL_GOLEM_MEAT),false));
        COOKED_ENDER_GOLEM_MEAT = registerWithTab("cooked_ender_golem_meat", () -> new StatConsumableItem(foodItem(FoodValues.COOKED_ENDER_GOLEM_MEAT),false));
        COOKED_LEVIATHAN = registerWithTab("cooked_leviathan", () -> new StatConsumableItem(foodItem(FoodValues.COOKED_LEVIATHAN), true));
        COOKED_LIONFISH = registerWithTab("cooked_lionfish", () -> new ConsumableItem(foodItem(FoodValues.COOKED_LIONFISH),true));
        COOKED_NETHERITE_MONSTROSITY_MEAT = registerWithTab("cooked_netherite_monstrosity_meat", () -> new StatConsumableItem(foodItem(FoodValues.COOKED_NETHERITE_MONSTROSITY_MEAT),false));
        CORAL_CHUNK_SANDWICH = registerWithTab("coral_chunk_sandwich", () -> new StatConsumableItem(foodItem(FoodValues.CORAL_CHUNK_SANDWICH),true));
        CORAL_GOLEM_MEAT = registerWithTab("coral_golem_meat", () -> new StatConsumableItem(foodItem(FoodValues.CORAL_GOLEM_MEAT),true));
        CRYSTALLIZED_CORAL_POTATO = registerWithTab("crystallized_coral_potato", () -> new StatConsumableItem(foodItem(FoodValues.CRYSTALLIZED_CORAL_POTATO), true));
        CRYSTALLIZED_CORAL_ROLL = registerWithTab("crystallized_coral_roll", () -> new StatConsumableItem(foodItem(FoodValues.CRYSTALLIZED_CORAL_ROLL),true));
        AMETHYST_CRAB_MEAT_SLICE = registerWithTab("amethyst_crab_meat_slice", () -> new ConsumableItem(foodItem(FoodValues.AMETHYST_CRAB_MEAT_SLICE),true));
        COOKED_AMETHYST_CRAB_MEAT_SLICE = registerWithTab("cooked_amethyst_crab_meat_slice", () -> new ConsumableItem(foodItem(FoodValues.COOKED_AMETHYST_CRAB_MEAT_SLICE),true));
        CORAL_CHUNK_RED_SLICE = registerWithTab("coral_chunk_red_slice", () -> new ConsumableItem(foodItem(FoodValues.CORAL_CHUNK_RED_SLICE),true));
        MALEDICTUS_HEART_SLICE = registerWithTab("maledictus_heart_slice", () -> new StatConsumableItem(foodItem(FoodValues.MALEDICTUS_HEART_SLICE),true));
        DEEPLING_MEAT = registerWithTab("deepling_meat", () -> new StatConsumableItem(foodItem(FoodValues.DEEPLING_MEAT),true));
        FRIED_ABYSSAL_EGG = registerWithTab("fried_abyssal_egg", () -> new StatConsumableItem(foodItem(FoodValues.FRIED_ABYSSAL_EGG),true));
        IGNIS = registerWithTab("ignis", () -> new StatConsumableItem(foodItem(FoodValues.IGNIS), true));
        KOBOLETON_PUMPKIN = registerWithTab("koboleton_pumpkin", () -> new KoboletonPumpkinItem(foodItem(FoodValues.KOBOLETON_PUMPKIN)));
        LEVIATHAN = registerWithTab("leviathan", () -> new StatConsumableItem(foodItem(FoodValues.LEVIATHAN), true));
        LIONFISH_ROLL = registerWithTab("lionfish_roll", () -> new Item(foodItem(FoodValues.LIONFISH_ROLL)));
        LIONFISH_SLICE = registerWithTab("lionfish_slice", () -> new Item(foodItem(FoodValues.LIONFISH_SLICE)));
        MALEDICTUS_HEART = registerWithTab("maledictus_heart", () -> new StatConsumableItem(foodItem(FoodValues.MALEDICTUS_HEART), true));
        NETHERITE_MONSTROSITY_MEAT = registerWithTab("netherite_monstrosity_meat", () -> new StatConsumableItem(foodItem(FoodValues.NETHERITE_MONSTROSITY_MEAT),true));
        RAW_DEEPLING_MEAT = registerWithTab("raw_deepling_meat", () -> new StatConsumableItem(foodItem(FoodValues.RAW_DEEPLING_MEAT),true));
        RAW_BRUTE_DEEPLING_MEAT = registerWithTab("raw_brute_deepling_meat", () -> new StatConsumableItem(foodItem(FoodValues.RAW_DEEPLING_MEAT),true));
        RAW_WARLOCK_DEEPLING_MEAT = registerWithTab("raw_warlock_deepling_meat", () -> new StatConsumableItem(foodItem(FoodValues.RAW_DEEPLING_MEAT),false));
        WATCHER_HEART = registerWithTab("watcher_heart", () -> new StatConsumableItem(foodItem(FoodValues.WATCHER_HEART), true));
        ENDER_GOLEM_MEAT = registerWithTab("ender_golem_meat", () -> new StatConsumableItem(foodItem(FoodValues.ENDER_GOLEM_MEAT),false));
        ENDER_GUARDIAN_CRYSTAL = registerWithTab("ender_guardian_crystal", () -> new StatConsumableItem(foodItem(FoodValues.ENDER_GUARDIAN_CRYSTAL), true));
        CRYSTALLIZED_CORAL_PIE_SLICE = registerWithTab("crystallized_coral_pie_slice", () -> new StatConsumableItem(foodItem(FoodValues.CRYSTALLIZED_CORAL_PIE_SLICE),true));
        CINDARIA_HAT = registerWithTab("cindaria_hat", () -> new CindariaHatItem(foodItem(FoodValues.CINDARIA_HAT)));
        COOKED_CRAB_LEGS = registerWithTab("cooked_crab_legs", () -> new StatConsumableItem(foodItem(FoodValues.COOKED_CRAB_LEGS),false));
        CRAB_LEGS = registerWithTab("crab_legs", () -> new StatConsumableItem(foodItem(FoodValues.CRAB_LEGS),false));
        HIPPOCAMTUS = registerWithTab("hippocamtus", () -> new StatConsumableItem(foodItem(FoodValues.HIPPOCAMTUS),false));
        SCYLLA_SNAKE = registerWithTab("scylla_snake", () -> new StatConsumableItem(foodItem(FoodValues.SCYLLA_SNAKE),true));
        SCYLLA_SNAKE_GLAZED_CINDARIA_HAT = registerWithTab("scylla_snake_glazed_cindaria_hat", () -> new StatConsumableItem(bowlFoodItem(FoodValues.SCYLLA_SNAKE_GLAZED_CINDARIA_HAT),true));
        SEA_MEAT_SALAD = registerWithTab("sea_meat_salad", () -> new StatConsumableItem(bowlFoodItem(FoodValues.SEA_MEAT_SALAD),false));
        SYMBIOCTO_TENTACLE = registerWithTab("symbiocto_tentacle", () -> new SymbioctoTentacleItem(foodItem(FoodValues.SYMBIOCTO_TENTACLE)));
        URCHINKIN = registerWithTab("urchinkin", () -> new ConsumableItem(foodItem(FoodValues.URCHINKIN),true));
        URCHINKIN_PUMPKIN = registerWithTab("urchinkin_pumpkin", () -> new UrchinkinPumpkinItem(foodItem(FoodValues.URCHINKIN_PUMPKIN)));
        CRYSTALLIZED_CORAL_PIE = registerWithTab("crystallized_coral_pie", () -> new BlockItem((Block) ModBlocks.CRYSTALLIZED_CORAL_PIE.get(), basicItem()));

        IMPROVED_DOG_FOOD = registerWithTab("improved_dog_food", () -> new ImprovedDogFoodItem(bowlFoodItem(FoodValues.IMPROVED_DOG_FOOD)));

        ABYSSAL_KNIFE = registerWithTab("abyssal_knife", () -> new AbyssalKnife(Tiers.NETHERITE, 0.5F, -2.0F, basicItem().fireResistant()));
        ANCIENT_KNIFE = registerWithTab("ancient_knife", () -> new KnifeItem(Tiers.DIAMOND, 0.5F, -2.0F, basicItem()));
        BLACK_STEEL_KNIFE = registerWithTab("black_steel_knife", () -> new KnifeItem(Tiers.DIAMOND, 0.5F, -2.0F, basicItem()));
        IGNITIUM_KNIFE = registerWithTab("ignitium_knife", () -> new KnifeItem(Tiers.DIAMOND, 0.5F, -2.0F, basicItem()));
        WITHERITE_KNIFE = registerWithTab("witherite_knife", () -> new KnifeItem(Tiers.DIAMOND, 0.5F, -2.0F, basicItem()));
        CURSIUM_KNIFE = registerWithTab("cursium_knife", () -> new KnifeItem(Tiers.NETHERITE, 0.5F, -2.0F, basicItem().fireResistant()));
        LACRIMA_ANCHOR_KNIFE = registerWithTab("lacrima_anchor_knife", () -> new KnifeItem(Tiers.NETHERITE, 0.5F, -2.0F, basicItem().fireResistant()));

    }
}
