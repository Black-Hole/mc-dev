package net.minecraft.server;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

public class TagsItem {

    private static Tags<Item> H = new Tags((minecraftkey) -> {
        return false;
    }, (minecraftkey) -> {
        return null;
    }, "", false, "");
    private static int I;
    public static final Tag<Item> a = a("wool");
    public static final Tag<Item> b = a("planks");
    public static final Tag<Item> c = a("stone_bricks");
    public static final Tag<Item> d = a("wooden_buttons");
    public static final Tag<Item> e = a("buttons");
    public static final Tag<Item> f = a("carpets");
    public static final Tag<Item> g = a("wooden_doors");
    public static final Tag<Item> h = a("wooden_stairs");
    public static final Tag<Item> i = a("wooden_slabs");
    public static final Tag<Item> j = a("wooden_pressure_plates");
    public static final Tag<Item> k = a("doors");
    public static final Tag<Item> l = a("saplings");
    public static final Tag<Item> m = a("logs");
    public static final Tag<Item> n = a("dark_oak_logs");
    public static final Tag<Item> o = a("oak_logs");
    public static final Tag<Item> p = a("birch_logs");
    public static final Tag<Item> q = a("acacia_logs");
    public static final Tag<Item> r = a("jungle_logs");
    public static final Tag<Item> s = a("spruce_logs");
    public static final Tag<Item> t = a("banners");
    public static final Tag<Item> u = a("sand");
    public static final Tag<Item> v = a("stairs");
    public static final Tag<Item> w = a("slabs");
    public static final Tag<Item> x = a("anvil");
    public static final Tag<Item> y = a("rails");
    public static final Tag<Item> z = a("live_coral_blocks");
    public static final Tag<Item> A = a("dead_coral_blocks");
    public static final Tag<Item> B = a("coral_blocks");
    public static final Tag<Item> C = a("corals");
    public static final Tag<Item> D = a("coral_fans");
    public static final Tag<Item> E = a("leaves");
    public static final Tag<Item> F = a("boats");
    public static final Tag<Item> G = a("fishes");

    public static void a(Tags<Item> tags) {
        TagsItem.H = tags;
        ++TagsItem.I;
    }

    public static Tags<Item> a() {
        return TagsItem.H;
    }

    private static Tag<Item> a(String s) {
        return new TagsItem.a(new MinecraftKey(s));
    }

    public static class a extends Tag<Item> {

        private int a = -1;
        private Tag<Item> b;

        public a(MinecraftKey minecraftkey) {
            super(minecraftkey);
        }

        public boolean a(Item item) {
            if (this.a != TagsItem.I) {
                this.b = TagsItem.H.b(this.c());
                this.a = TagsItem.I;
            }

            return this.b.isTagged(item);
        }

        public Collection<Item> a() {
            if (this.a != TagsItem.I) {
                this.b = TagsItem.H.b(this.c());
                this.a = TagsItem.I;
            }

            return this.b.a();
        }

        public Collection<Tag.b<Item>> b() {
            if (this.a != TagsItem.I) {
                this.b = TagsItem.H.b(this.c());
                this.a = TagsItem.I;
            }

            return this.b.b();
        }
    }
}
