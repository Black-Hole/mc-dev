package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTableRegistry implements IResourcePackListener {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson d = (new GsonBuilder()).registerTypeAdapter(LootValueBounds.class, new LootValueBounds.a()).registerTypeAdapter(LootValueBinomial.class, new LootValueBinomial.a()).registerTypeAdapter(LootValueConstant.class, new LootValueConstant.a()).registerTypeAdapter(LootIntegerLimit.class, new LootIntegerLimit.a()).registerTypeAdapter(LootSelector.class, new LootSelector.b()).registerTypeAdapter(LootTable.class, new LootTable.b()).registerTypeHierarchyAdapter(LootEntryAbstract.class, new LootEntries.a()).registerTypeHierarchyAdapter(LootItemFunction.class, new LootItemFunctions.a()).registerTypeHierarchyAdapter(LootItemCondition.class, new LootItemConditions.a()).registerTypeHierarchyAdapter(LootTableInfo.EntityTarget.class, new LootTableInfo.EntityTarget.a()).create();
    private final Map<MinecraftKey, LootTable> e = Maps.newHashMap();
    private final Set<MinecraftKey> f;
    public static final int a = "loot_tables/".length();
    public static final int b = ".json".length();

    public LootTableRegistry() {
        this.f = Collections.unmodifiableSet(this.e.keySet());
    }

    public LootTable getLootTable(MinecraftKey minecraftkey) {
        return (LootTable) this.e.getOrDefault(minecraftkey, LootTable.a);
    }

    @Override
    public void a(IResourceManager iresourcemanager) {
        this.e.clear();
        Iterator iterator = iresourcemanager.a("loot_tables", (s) -> {
            return s.endsWith(".json");
        }).iterator();

        while (iterator.hasNext()) {
            MinecraftKey minecraftkey = (MinecraftKey) iterator.next();
            String s = minecraftkey.getKey();
            MinecraftKey minecraftkey1 = new MinecraftKey(minecraftkey.b(), s.substring(LootTableRegistry.a, s.length() - LootTableRegistry.b));

            try {
                IResource iresource = iresourcemanager.a(minecraftkey);
                Throwable throwable = null;

                try {
                    LootTable loottable = (LootTable) ChatDeserializer.a(LootTableRegistry.d, IOUtils.toString(iresource.b(), StandardCharsets.UTF_8), LootTable.class);

                    if (loottable != null) {
                        this.e.put(minecraftkey1, loottable);
                    }
                } catch (Throwable throwable1) {
                    throwable = throwable1;
                    throw throwable1;
                } finally {
                    if (iresource != null) {
                        if (throwable != null) {
                            try {
                                iresource.close();
                            } catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        } else {
                            iresource.close();
                        }
                    }

                }
            } catch (Throwable throwable3) {
                LootTableRegistry.LOGGER.error("Couldn't read loot table {} from {}", minecraftkey1, minecraftkey, throwable3);
            }
        }

        this.e.put(LootTables.a, LootTable.a);
        LootCollector lootcollector = new LootCollector();

        this.e.forEach((minecraftkey2, loottable1) -> {
            Map map = this.e;

            this.e.getClass();
            a(lootcollector, minecraftkey2, loottable1, map::get);
        });
        lootcollector.a().forEach((s1, s2) -> {
            LootTableRegistry.LOGGER.warn("Found validation problem in " + s1 + ": " + s2);
        });
    }

    public static void a(LootCollector lootcollector, MinecraftKey minecraftkey, LootTable loottable, Function<MinecraftKey, LootTable> function) {
        Set<MinecraftKey> set = ImmutableSet.of(minecraftkey);

        loottable.a(lootcollector.b("{" + minecraftkey.toString() + "}"), function, set, loottable.a());
    }

    public static JsonElement a(LootTable loottable) {
        return LootTableRegistry.d.toJsonTree(loottable);
    }

    public Set<MinecraftKey> a() {
        return this.f;
    }
}
