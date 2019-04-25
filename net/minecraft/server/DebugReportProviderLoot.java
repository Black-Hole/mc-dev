package net.minecraft.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugReportProviderLoot implements DebugReportProvider {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson c = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private final DebugReportGenerator d;
    private final List<Pair<Supplier<Consumer<BiConsumer<MinecraftKey, LootTable.a>>>, LootContextParameterSet>> e;

    public DebugReportProviderLoot(DebugReportGenerator debugreportgenerator) {
        this.e = ImmutableList.of(Pair.of(DebugReportLootFishing::new, LootContextParameterSets.FISHING), Pair.of(DebugReportLootChest::new, LootContextParameterSets.CHEST), Pair.of(DebugReportLootEntity::new, LootContextParameterSets.ENTITY), Pair.of(DebugReportLootBlock::new, LootContextParameterSets.BLOCK), Pair.of(DebugReportLootGift::new, LootContextParameterSets.GIFT));
        this.d = debugreportgenerator;
    }

    @Override
    public void a(HashCache hashcache) {
        java.nio.file.Path java_nio_file_path = this.d.b();
        Map<MinecraftKey, LootTable> map = Maps.newHashMap();

        this.e.forEach((pair) -> {
            ((Consumer) ((Supplier) pair.getFirst()).get()).accept((minecraftkey, loottable_a) -> {
                if (map.put(minecraftkey, loottable_a.a((LootContextParameterSet) pair.getSecond()).b()) != null) {
                    throw new IllegalStateException("Duplicate loot table " + minecraftkey);
                }
            });
        });
        LootCollector lootcollector = new LootCollector();
        Set<MinecraftKey> set = Sets.difference(LootTables.a(), map.keySet());
        Iterator iterator = set.iterator();

        while (iterator.hasNext()) {
            MinecraftKey minecraftkey = (MinecraftKey) iterator.next();

            lootcollector.a("Missing built-in table: " + minecraftkey);
        }

        map.forEach((minecraftkey1, loottable) -> {
            LootTableRegistry.a(lootcollector, minecraftkey1, loottable, map::get);
        });
        Multimap<String, String> multimap = lootcollector.a();

        if (!multimap.isEmpty()) {
            multimap.forEach((s, s1) -> {
                DebugReportProviderLoot.LOGGER.warn("Found validation problem in " + s + ": " + s1);
            });
            throw new IllegalStateException("Failed to validate loot tables, see logs");
        } else {
            map.forEach((minecraftkey1, loottable) -> {
                java.nio.file.Path java_nio_file_path1 = a(java_nio_file_path, minecraftkey1);

                try {
                    DebugReportProvider.a(DebugReportProviderLoot.c, hashcache, LootTableRegistry.a(loottable), java_nio_file_path1);
                } catch (IOException ioexception) {
                    DebugReportProviderLoot.LOGGER.error("Couldn't save loot table {}", java_nio_file_path1, ioexception);
                }

            });
        }
    }

    private static java.nio.file.Path a(java.nio.file.Path java_nio_file_path, MinecraftKey minecraftkey) {
        return java_nio_file_path.resolve("data/" + minecraftkey.b() + "/loot_tables/" + minecraftkey.getKey() + ".json");
    }

    @Override
    public String a() {
        return "LootTables";
    }
}
