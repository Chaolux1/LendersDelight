package net.chaolux.lendersdelight.coomon.stat;

import com.mojang.logging.LogUtils;
import net.chaolux.lendersdelight.Config;
import net.chaolux.lendersdelight.client.ClientStatTooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.ForgeRegistries;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import vectorwing.farmersdelight.common.item.ConsumableItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class StatConsumableItem extends ConsumableItem {
    public StatConsumableItem(Properties properties, boolean hasFoodEffectTooltip) {
        super(properties, hasFoodEffectTooltip);
    }
}
