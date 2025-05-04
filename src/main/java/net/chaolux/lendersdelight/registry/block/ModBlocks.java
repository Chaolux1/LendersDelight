package net.chaolux.lendersdelight.registry.block;

import net.chaolux.lendersdelight.registry.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.block.PieBlock;

import java.util.function.Supplier;


public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS;
    public static final Supplier<Block> CRYSTALLIZED_CORAL_PIE;

    static {
        BLOCKS = DeferredRegister.create(Registries.BLOCK, "lendersdelight");
        CRYSTALLIZED_CORAL_PIE = BLOCKS.register("crystallized_coral_pie", () -> new PieBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), ModItems.CRYSTALLIZED_CORAL_PIE_SLICE));

    }
}
