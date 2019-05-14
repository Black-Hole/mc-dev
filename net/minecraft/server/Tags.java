package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Tags<T> {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson b = new Gson();
    private static final int c = ".json".length();
    private final Map<MinecraftKey, Tag<T>> d = Maps.newHashMap();
    private final Function<MinecraftKey, Optional<T>> e;
    private final String f;
    private final boolean g;
    private final String h;

    public Tags(Function<MinecraftKey, Optional<T>> function, String s, boolean flag, String s1) {
        this.e = function;
        this.f = s;
        this.g = flag;
        this.h = s1;
    }

    public void a(Tag<T> tag) {
        if (this.d.containsKey(tag.c())) {
            throw new IllegalArgumentException("Duplicate " + this.h + " tag '" + tag.c() + "'");
        } else {
            this.d.put(tag.c(), tag);
        }
    }

    @Nullable
    public Tag<T> a(MinecraftKey minecraftkey) {
        return (Tag) this.d.get(minecraftkey);
    }

    public Tag<T> b(MinecraftKey minecraftkey) {
        Tag<T> tag = (Tag) this.d.get(minecraftkey);

        return tag == null ? new Tag<>(minecraftkey) : tag;
    }

    public Collection<MinecraftKey> a() {
        return this.d.keySet();
    }

    public void b() {
        this.d.clear();
    }

    public CompletableFuture<Map<MinecraftKey, Tag.a<T>>> a(IResourceManager iresourcemanager, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            Map<MinecraftKey, Tag.a<T>> map = Maps.newHashMap();
            Iterator iterator = iresourcemanager.a(this.f, (s) -> {
                return s.endsWith(".json");
            }).iterator();

            while (iterator.hasNext()) {
                MinecraftKey minecraftkey = (MinecraftKey) iterator.next();
                String s = minecraftkey.getKey();
                MinecraftKey minecraftkey1 = new MinecraftKey(minecraftkey.b(), s.substring(this.f.length() + 1, s.length() - Tags.c));

                try {
                    Iterator iterator1 = iresourcemanager.c(minecraftkey).iterator();

                    while (iterator1.hasNext()) {
                        IResource iresource = (IResource) iterator1.next();

                        try {
                            JsonObject jsonobject = (JsonObject) ChatDeserializer.a(Tags.b, IOUtils.toString(iresource.b(), StandardCharsets.UTF_8), JsonObject.class);

                            if (jsonobject == null) {
                                Tags.LOGGER.error("Couldn't load {} tag list {} from {} in data pack {} as it's empty or null", this.h, minecraftkey1, minecraftkey, iresource.d());
                            } else {
                                ((Tag.a) map.computeIfAbsent(minecraftkey1, (minecraftkey2) -> {
                                    return (Tag.a) SystemUtils.a((Object) Tag.a.a(), (tag_a) -> {
                                        tag_a.a(this.g);
                                    });
                                })).a(this.e, jsonobject);
                            }
                        } catch (RuntimeException | IOException ioexception) {
                            Tags.LOGGER.error("Couldn't read {} tag list {} from {} in data pack {}", this.h, minecraftkey1, minecraftkey, iresource.d(), ioexception);
                        } finally {
                            IOUtils.closeQuietly(iresource);
                        }
                    }
                } catch (IOException ioexception1) {
                    Tags.LOGGER.error("Couldn't read {} tag list {} from {}", this.h, minecraftkey1, minecraftkey, ioexception1);
                }
            }

            return map;
        }, executor);
    }

    public void a(Map<MinecraftKey, Tag.a<T>> map) {
        while (true) {
            if (!map.isEmpty()) {
                boolean flag = false;
                Iterator iterator = map.entrySet().iterator();

                Entry entry;

                while (iterator.hasNext()) {
                    entry = (Entry) iterator.next();
                    if (((Tag.a) entry.getValue()).a(this::a)) {
                        flag = true;
                        this.a(((Tag.a) entry.getValue()).b((MinecraftKey) entry.getKey()));
                        iterator.remove();
                    }
                }

                if (flag) {
                    continue;
                }

                iterator = map.entrySet().iterator();

                while (iterator.hasNext()) {
                    entry = (Entry) iterator.next();
                    Tags.LOGGER.error("Couldn't load {} tag {} as it either references another tag that doesn't exist, or ultimately references itself", this.h, entry.getKey());
                }
            }

            Iterator iterator1 = map.entrySet().iterator();

            while (iterator1.hasNext()) {
                Entry<MinecraftKey, Tag.a<T>> entry1 = (Entry) iterator1.next();

                this.a(((Tag.a) entry1.getValue()).b((MinecraftKey) entry1.getKey()));
            }

            return;
        }
    }

    public Map<MinecraftKey, Tag<T>> c() {
        return this.d;
    }
}
