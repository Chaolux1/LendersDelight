package net.chaolux.lendersdelight.registry.block;

import net.chaolux.lendersdelight.registry.item.ModItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.block.PieBlock;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS;
    public static final RegistryObject<Block> CRYSTALLIZED_CORAL_PIE;

    static {
        BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "lendersdelight");
        CRYSTALLIZED_CORAL_PIE = BLOCKS.register("crystallized_coral_pie", () -> new PieBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), ModItems.CRYSTALLIZED_CORAL_PIE_SLICE));

    }
}
