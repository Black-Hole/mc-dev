package net.minecraft.server;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;

public interface VillagerType {

    VillagerType a = a("desert");
    VillagerType b = a("jungle");
    VillagerType c = a("plains");
    VillagerType d = a("savanna");
    VillagerType e = a("snow");
    VillagerType f = a("swamp");
    VillagerType g = a("taiga");
    Map<BiomeBase, VillagerType> h = (Map) SystemUtils.a((Object) Maps.newHashMap(), (hashmap) -> {
        hashmap.put(Biomes.BADLANDS, VillagerType.a);
        hashmap.put(Biomes.BADLANDS_PLATEAU, VillagerType.a);
        hashmap.put(Biomes.DESERT, VillagerType.a);
        hashmap.put(Biomes.DESERT_HILLS, VillagerType.a);
        hashmap.put(Biomes.DESERT_LAKES, VillagerType.a);
        hashmap.put(Biomes.ERODED_BADLANDS, VillagerType.a);
        hashmap.put(Biomes.MODIFIED_BADLANDS_PLATEAU, VillagerType.a);
        hashmap.put(Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, VillagerType.a);
        hashmap.put(Biomes.WOODED_BADLANDS_PLATEAU, VillagerType.a);
        hashmap.put(Biomes.BAMBOO_JUNGLE, VillagerType.b);
        hashmap.put(Biomes.BAMBOO_JUNGLE_HILLS, VillagerType.b);
        hashmap.put(Biomes.JUNGLE, VillagerType.b);
        hashmap.put(Biomes.JUNGLE_EDGE, VillagerType.b);
        hashmap.put(Biomes.JUNGLE_HILLS, VillagerType.b);
        hashmap.put(Biomes.MODIFIED_JUNGLE, VillagerType.b);
        hashmap.put(Biomes.MODIFIED_JUNGLE_EDGE, VillagerType.b);
        hashmap.put(Biomes.SAVANNA_PLATEAU, VillagerType.d);
        hashmap.put(Biomes.SAVANNA, VillagerType.d);
        hashmap.put(Biomes.SHATTERED_SAVANNA, VillagerType.d);
        hashmap.put(Biomes.SHATTERED_SAVANNA_PLATEAU, VillagerType.d);
        hashmap.put(Biomes.DEEP_FROZEN_OCEAN, VillagerType.e);
        hashmap.put(Biomes.FROZEN_OCEAN, VillagerType.e);
        hashmap.put(Biomes.FROZEN_RIVER, VillagerType.e);
        hashmap.put(Biomes.ICE_SPIKES, VillagerType.e);
        hashmap.put(Biomes.SNOWY_BEACH, VillagerType.e);
        hashmap.put(Biomes.SNOWY_MOUNTAINS, VillagerType.e);
        hashmap.put(Biomes.SNOWY_TAIGA, VillagerType.e);
        hashmap.put(Biomes.SNOWY_TAIGA_HILLS, VillagerType.e);
        hashmap.put(Biomes.SNOWY_TAIGA_MOUNTAINS, VillagerType.e);
        hashmap.put(Biomes.SNOWY_TUNDRA, VillagerType.e);
        hashmap.put(Biomes.SWAMP, VillagerType.f);
        hashmap.put(Biomes.SWAMP_HILLS, VillagerType.f);
        hashmap.put(Biomes.GIANT_SPRUCE_TAIGA, VillagerType.g);
        hashmap.put(Biomes.GIANT_SPRUCE_TAIGA_HILLS, VillagerType.g);
        hashmap.put(Biomes.GIANT_TREE_TAIGA, VillagerType.g);
        hashmap.put(Biomes.GIANT_TREE_TAIGA_HILLS, VillagerType.g);
        hashmap.put(Biomes.GRAVELLY_MOUNTAINS, VillagerType.g);
        hashmap.put(Biomes.MODIFIED_GRAVELLY_MOUNTAINS, VillagerType.g);
        hashmap.put(Biomes.MOUNTAIN_EDGE, VillagerType.g);
        hashmap.put(Biomes.MOUNTAINS, VillagerType.g);
        hashmap.put(Biomes.TAIGA, VillagerType.g);
        hashmap.put(Biomes.TAIGA_HILLS, VillagerType.g);
        hashmap.put(Biomes.TAIGA_MOUNTAINS, VillagerType.g);
        hashmap.put(Biomes.WOODED_MOUNTAINS, VillagerType.g);
    });

    static VillagerType a(final String s) {
        return (VillagerType) IRegistry.a((IRegistry) IRegistry.VILLAGER_TYPE, new MinecraftKey(s), (Object) (new VillagerType() {
            public String toString() {
                return s;
            }
        }));
    }

    static VillagerType a(BiomeBase biomebase) {
        return (VillagerType) VillagerType.h.getOrDefault(biomebase, VillagerType.c);
    }
}
