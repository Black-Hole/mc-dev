package net.minecraft.server;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

public class TagsBlock {

    private static Tags<Block> J = new Tags((minecraftkey) -> {
        return false;
    }, (minecraftkey) -> {
        return null;
    }, "", false, "");
    private static int K;
    public static final Tag<Block> a = a("wool");
    public static final Tag<Block> b = a("planks");
    public static final Tag<Block> c = a("stone_bricks");
    public static final Tag<Block> d = a("wooden_buttons");
    public static final Tag<Block> e = a("buttons");
    public static final Tag<Block> f = a("carpets");
    public static final Tag<Block> g = a("wooden_doors");
    public static final Tag<Block> h = a("wooden_stairs");
    public static final Tag<Block> i = a("wooden_slabs");
    public static final Tag<Block> j = a("wooden_pressure_plates");
    public static final Tag<Block> k = a("doors");
    public static final Tag<Block> l = a("saplings");
    public static final Tag<Block> m = a("logs");
    public static final Tag<Block> n = a("dark_oak_logs");
    public static final Tag<Block> o = a("oak_logs");
    public static final Tag<Block> p = a("birch_logs");
    public static final Tag<Block> q = a("acacia_logs");
    public static final Tag<Block> r = a("jungle_logs");
    public static final Tag<Block> s = a("spruce_logs");
    public static final Tag<Block> t = a("banners");
    public static final Tag<Block> u = a("sand");
    public static final Tag<Block> v = a("stairs");
    public static final Tag<Block> w = a("slabs");
    public static final Tag<Block> x = a("anvil");
    public static final Tag<Block> y = a("rails");
    public static final Tag<Block> z = a("live_coral_blocks");
    public static final Tag<Block> A = a("dead_coral_blocks");
    public static final Tag<Block> B = a("coral_blocks");
    public static final Tag<Block> C = a("corals");
    public static final Tag<Block> D = a("coral_fans");
    public static final Tag<Block> E = a("leaves");
    public static final Tag<Block> F = a("flower_pots");
    public static final Tag<Block> G = a("enderman_holdable");
    public static final Tag<Block> H = a("ice");
    public static final Tag<Block> I = a("valid_spawn");

    public static void a(Tags<Block> tags) {
        TagsBlock.J = tags;
        ++TagsBlock.K;
    }

    public static Tags<Block> a() {
        return TagsBlock.J;
    }

    private static Tag<Block> a(String s) {
        return new TagsBlock.a(new MinecraftKey(s));
    }

    static class a extends Tag<Block> {

        private int a = -1;
        private Tag<Block> b;

        public a(MinecraftKey minecraftkey) {
            super(minecraftkey);
        }

        public boolean a(Block block) {
            if (this.a != TagsBlock.K) {
                this.b = TagsBlock.J.b(this.c());
                this.a = TagsBlock.K;
            }

            return this.b.isTagged(block);
        }

        public Collection<Block> a() {
            if (this.a != TagsBlock.K) {
                this.b = TagsBlock.J.b(this.c());
                this.a = TagsBlock.K;
            }

            return this.b.a();
        }

        public Collection<Tag.b<Block>> b() {
            if (this.a != TagsBlock.K) {
                this.b = TagsBlock.J.b(this.c());
                this.a = TagsBlock.K;
            }

            return this.b.b();
        }
    }
}
