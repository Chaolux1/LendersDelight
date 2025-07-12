package net.chaolux.lendersdelight.coomon.stst;

import net.chaolux.lendersdelight.registry.stat.ModNetwork;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

public class PlayerStatProvider implements ICapabilitySerializable<CompoundTag> {
    public static final Capability<IPlayerStat> PLAYER_STAT=CapabilityManager.get(new CapabilityToken<IPlayerStat>() {});
    private final PlayerStatCapability backend=new PlayerStatCapability();
    private final LazyOptional<IPlayerStat> optional=LazyOptional.of(() -> backend);

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @NotNull Direction side) {
        return cap==PLAYER_STAT ? optional.cast():LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return backend.saveNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        backend.loadNBT(nbt);
    }

    public static void attach(Player player, AttachCapabilitiesEvent<Player> event) {
        PlayerStatProvider provider=new PlayerStatProvider();
        event.addCapability(StatCapabilityID.PLAYER_STAT_ID,provider);
    }

    public static LazyOptional<IPlayerStat> get(Player player) {
        return player.getCapability(PLAYER_STAT);
    }

    public static void sync(Player player) {
        if(!(player instanceof ServerPlayer serverPlayer)) return;
        player.getCapability(PLAYER_STAT).ifPresent(cap -> {
            Map<StatType, Float> data=new EnumMap<>(StatType.class);
            for(StatType type:StatType.values()) {
                data.put(type,cap.getStat(type));
            }
            ModNetwork.sendToClient(serverPlayer, new StatSyncPacket(data));
        });
    }
}
