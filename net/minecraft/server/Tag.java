package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class Tag<T> {

    private final MinecraftKey a;
    private final Set<T> b;
    private final Collection<Tag.b<T>> c;

    public Tag(MinecraftKey minecraftkey) {
        this.a = minecraftkey;
        this.b = Collections.emptySet();
        this.c = Collections.emptyList();
    }

    public Tag(MinecraftKey minecraftkey, Collection<Tag.b<T>> collection, boolean flag) {
        this.a = minecraftkey;
        this.b = (Set) (flag ? Sets.newLinkedHashSet() : Sets.newHashSet());
        this.c = collection;
        Iterator iterator = collection.iterator();

        while (iterator.hasNext()) {
            Tag.b tag_b = (Tag.b) iterator.next();

            tag_b.a((Collection) this.b);
        }

    }

    public JsonObject a(Function<T, MinecraftKey> function) {
        JsonObject jsonobject = new JsonObject();
        JsonArray jsonarray = new JsonArray();
        Iterator iterator = this.c.iterator();

        while (iterator.hasNext()) {
            Tag.b tag_b = (Tag.b) iterator.next();

            tag_b.a(jsonarray, function);
        }

        jsonobject.addProperty("replace", false);
        jsonobject.add("values", jsonarray);
        return jsonobject;
    }

    public boolean isTagged(T t0) {
        return this.b.contains(t0);
    }

    public Collection<T> a() {
        return this.b;
    }

    public Collection<Tag.b<T>> b() {
        return this.c;
    }

    public T a(Random random) {
        ArrayList arraylist = Lists.newArrayList(this.a());

        return arraylist.get(random.nextInt(arraylist.size()));
    }

    public MinecraftKey c() {
        return this.a;
    }

    public static class c<T> implements Tag.b<T> {

        @Nullable
        private final MinecraftKey a;
        @Nullable
        private Tag<T> b;

        public c(MinecraftKey minecraftkey) {
            this.a = minecraftkey;
        }

        public c(Tag<T> tag) {
            this.a = tag.c();
            this.b = tag;
        }

        public boolean a(Function<MinecraftKey, Tag<T>> function) {
            if (this.b == null) {
                this.b = (Tag) function.apply(this.a);
            }

            return this.b != null;
        }

        public void a(Collection<T> collection) {
            if (this.b == null) {
                throw new IllegalStateException("Cannot build unresolved tag entry");
            } else {
                collection.addAll(this.b.a());
            }
        }

        public MinecraftKey a() {
            if (this.b != null) {
                return this.b.c();
            } else if (this.a != null) {
                return this.a;
            } else {
                throw new IllegalStateException("Cannot serialize an anonymous tag to json!");
            }
        }

        public void a(JsonArray jsonarray, Function<T, MinecraftKey> function) {
            jsonarray.add("#" + this.a());
        }
    }

    public static class d<T> implements Tag.b<T> {

        private final Collection<T> a;

        public d(Collection<T> collection) {
            this.a = collection;
        }

        public void a(Collection<T> collection) {
            collection.addAll(this.a);
        }

        public void a(JsonArray jsonarray, Function<T, MinecraftKey> function) {
            Iterator iterator = this.a.iterator();

            while (iterator.hasNext()) {
                Object object = iterator.next();
                MinecraftKey minecraftkey = (MinecraftKey) function.apply(object);

                if (minecraftkey == null) {
                    throw new IllegalStateException("Unable to serialize an anonymous value to json!");
                }

                jsonarray.add(minecraftkey.toString());
            }

        }

        public Collection<T> a() {
            return this.a;
        }
    }

    public interface b<T> {

        default boolean a(Function<MinecraftKey, Tag<T>> function) {
            return true;
        }

        void a(Collection<T> collection);

        void a(JsonArray jsonarray, Function<T, MinecraftKey> function);
    }

    public static class a<T> {

        private final Set<Tag.b<T>> a = Sets.newLinkedHashSet();
        private boolean b;

        public a() {}

        public static <T> Tag.a<T> a() {
            return new Tag.a();
        }

        public Tag.a<T> a(Tag.b<T> tag_b) {
            this.a.add(tag_b);
            return this;
        }

        public Tag.a<T> a(T t0) {
            this.a.add(new Tag.d(Collections.singleton(t0)));
            return this;
        }

        @SafeVarargs
        public final Tag.a<T> a(T... at) {
            this.a.add(new Tag.d(Lists.newArrayList(at)));
            return this;
        }

        public Tag.a<T> a(Collection<T> collection) {
            this.a.add(new Tag.d(collection));
            return this;
        }

        public Tag.a<T> a(MinecraftKey minecraftkey) {
            this.a.add(new Tag.c(minecraftkey));
            return this;
        }

        public Tag.a<T> a(Tag<T> tag) {
            this.a.add(new Tag.c(tag));
            return this;
        }

        public Tag.a<T> a(boolean flag) {
            this.b = flag;
            return this;
        }

        public boolean a(Function<MinecraftKey, Tag<T>> function) {
            Iterator iterator = this.a.iterator();

            Tag.b tag_b;

            do {
                if (!iterator.hasNext()) {
                    return true;
                }

                tag_b = (Tag.b) iterator.next();
            } while (tag_b.a(function));

            return false;
        }

        public Tag<T> b(MinecraftKey minecraftkey) {
            return new Tag(minecraftkey, this.a, this.b);
        }

        public Tag.a<T> a(Predicate<MinecraftKey> predicate, Function<MinecraftKey, T> function, JsonObject jsonobject) {
            JsonArray jsonarray = ChatDeserializer.u(jsonobject, "values");

            if (ChatDeserializer.a(jsonobject, "replace", false)) {
                this.a.clear();
            }

            Iterator iterator = jsonarray.iterator();

            while (true) {
                while (iterator.hasNext()) {
                    JsonElement jsonelement = (JsonElement) iterator.next();
                    String s = ChatDeserializer.a(jsonelement, "value");

                    if (!s.startsWith("#")) {
                        MinecraftKey minecraftkey = new MinecraftKey(s);
                        Object object = function.apply(minecraftkey);

                        if (object == null || !predicate.test(minecraftkey)) {
                            throw new JsonParseException("Unknown value '" + minecraftkey + "'");
                        }

                        this.a(object);
                    } else {
                        this.a(new MinecraftKey(s.substring(1)));
                    }
                }

                return this;
            }
        }
    }
}
