package net.minecraft.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugReportAdvancement implements DebugReportProvider {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson c = (new GsonBuilder()).setPrettyPrinting().create();
    private final DebugReportGenerator d;
    private final List<Consumer<Consumer<Advancement>>> e = ImmutableList.of(new DebugReportAdvancementTheEnd(), new DebugReportAdvancementHusbandry(), new DebugReportAdvancementAdventure(), new DebugReportAdvancementNether(), new DebugReportAdvancementStory());

    public DebugReportAdvancement(DebugReportGenerator debugreportgenerator) {
        this.d = debugreportgenerator;
    }

    @Override
    public void a(HashCache hashcache) throws IOException {
        java.nio.file.Path java_nio_file_path = this.d.b();
        Set<MinecraftKey> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (advancement) -> {
            if (!set.add(advancement.getName())) {
                throw new IllegalStateException("Duplicate advancement " + advancement.getName());
            } else {
                java.nio.file.Path java_nio_file_path1 = a(java_nio_file_path, advancement);

                try {
                    DebugReportProvider.a(DebugReportAdvancement.c, hashcache, advancement.a().b(), java_nio_file_path1);
                } catch (IOException ioexception) {
                    DebugReportAdvancement.LOGGER.error("Couldn't save advancement {}", java_nio_file_path1, ioexception);
                }

            }
        };
        Iterator iterator = this.e.iterator();

        while (iterator.hasNext()) {
            Consumer<Consumer<Advancement>> consumer1 = (Consumer) iterator.next();

            consumer1.accept(consumer);
        }

    }

    private static java.nio.file.Path a(java.nio.file.Path java_nio_file_path, Advancement advancement) {
        return java_nio_file_path.resolve("data/" + advancement.getName().b() + "/advancements/" + advancement.getName().getKey() + ".json");
    }

    @Override
    public String a() {
        return "Advancements";
    }
}
