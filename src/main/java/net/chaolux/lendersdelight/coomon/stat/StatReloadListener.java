package net.chaolux.lendersdelight.coomon.stat;

import com.google.gson.*;
import com.mojang.logging.LogUtils;
import net.chaolux.lendersdelight.Config;
import net.chaolux.lendersdelight.LendersDelight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.*;

public class StatReloadListener extends SimpleJsonResourceReloadListener {
    private static final Logger LOGGER= LogUtils.getLogger();
    private static final Gson GSON=new Gson();
    private static volatile Map<ResourceLocation,Map<StatType,Float>> server=Map.of();
    private static volatile Map<ResourceLocation,Map<StatType,Float>> client=Map.of();
    private static volatile StatMode statMode=StatMode.ACCUMULATE;
    private static volatile Map<StatType,Double> statTypeDoubleMap=Map.of();
    public StatReloadListener() {
        super(GSON,"stats");
    }
    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        Map<ResourceLocation,Map<StatType,Float>> resourceLocationMapMap=new LinkedHashMap<>();
        map.entrySet().stream().filter(entry -> entry.getKey().getNamespace().equals(LendersDelight.MOD_ID)).sorted(Map.Entry.comparingByKey()).forEach(entry -> {
            try {
                JsonObject jsonObject=entry.getValue().getAsJsonObject();
                ResourceLocation resourceLocation=new ResourceLocation(jsonObject.get("item").getAsString());
                if(!ForgeRegistries.ITEMS.containsKey(resourceLocation)) {
                    LOGGER.warn("Ignoring food stats in {} unknown {}",entry.getKey(),resourceLocation);
                    return;
                }
                JsonObject object=jsonObject.getAsJsonObject("stats");
                if(object == null) throw new JsonSyntaxException("Missing stat");
                EnumMap<StatType,Float> enumMap=new EnumMap<>(StatType.class);
                for (Map.Entry<String,JsonElement> stringJsonElementEntry : object.entrySet()) {
                    StatType statType=StatType.valueOf(stringJsonElementEntry.getKey().toUpperCase(Locale.ROOT));
                    float value=stringJsonElementEntry.getValue().getAsFloat();
                    if(!Float.isFinite(value) || value <= 0.0f) throw new JsonParseException("Stat value must be finite and 0 " + stringJsonElementEntry.getKey());
                    enumMap.put(statType,value);
                }
                if(resourceLocationMapMap.put(resourceLocation, Collections.unmodifiableMap(enumMap)) != null) LOGGER.warn("Multiple stat entry for {} apply definition form {}",resourceLocation,entry.getKey());
            } catch (RuntimeException exception) {
                LOGGER.error("Invalid stat definition {}: {}",entry.getKey(),exception.toString());
            }
        });
        server=Collections.unmodifiableMap(resourceLocationMapMap);
        LOGGER.info("Load {} stat definition",server.size());
    }

    public static Map<StatType,Float> getServer(ResourceLocation resourceLocation) {
        return server.getOrDefault(resourceLocation,Map.of());
    }

    public static Map<StatType,Float> getClient(ResourceLocation resourceLocation) {
        return client.getOrDefault(resourceLocation,Map.of());
    }

    public static Map<ResourceLocation,Map<StatType,Float>> getServerStat() {
        return server;
    }

    public static StatMode getStatMode() {
        return statMode;
    }

    public static double getLimit(StatType statType) {
        return statTypeDoubleMap.getOrDefault(statType, Config.getStatLimit(statType));
    }

    public static void setLimit(Map<ResourceLocation,Map<StatType,Float>> map,StatMode mode,Map<StatType,Double> doubleMap) {
        Map<ResourceLocation,Map<StatType,Float>> resourceLocationMapMap=new LinkedHashMap<>();
        for (Map.Entry<ResourceLocation,Map<StatType,Float>> entry : map.entrySet()) {
            EnumMap<StatType,Float> enumMap=new EnumMap<>(StatType.class);
            enumMap.putAll(entry.getValue());
            resourceLocationMapMap.put(entry.getKey(),Collections.unmodifiableMap(enumMap));
        }
        client=Collections.unmodifiableMap(resourceLocationMapMap);
        EnumMap<StatType,Double> enumMap=new EnumMap<>(StatType.class);
        enumMap.putAll(doubleMap);
        statTypeDoubleMap=Collections.unmodifiableMap(enumMap);
        statMode=mode;
    }
}
