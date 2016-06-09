package net.minecraft.server;

public enum EnumSkeletonType {

    NORMAL("Skeleton", LootTables.al), WITHER("WitherSkeleton", LootTables.am), STRAY("Stray", LootTables.an);

    private final ChatMessage d;
    private final MinecraftKey e;

    private EnumSkeletonType(String s, MinecraftKey minecraftkey) {
        this.d = new ChatMessage("entity." + s + ".name", new Object[0]);
        this.e = minecraftkey;
    }

    public int a() {
        return this.ordinal();
    }

    public static EnumSkeletonType a(int i) {
        return values()[i];
    }

    public MinecraftKey c() {
        return this.e;
    }

    public SoundEffect d() {
        switch (EnumSkeletonType.SyntheticClass_1.a[this.ordinal()]) {
        case 1:
            return SoundEffects.ha;

        case 2:
            return SoundEffects.gu;

        default:
            return SoundEffects.ft;
        }
    }

    public SoundEffect e() {
        switch (EnumSkeletonType.SyntheticClass_1.a[this.ordinal()]) {
        case 1:
            return SoundEffects.hc;

        case 2:
            return SoundEffects.gw;

        default:
            return SoundEffects.fy;
        }
    }

    public SoundEffect f() {
        switch (EnumSkeletonType.SyntheticClass_1.a[this.ordinal()]) {
        case 1:
            return SoundEffects.hb;

        case 2:
            return SoundEffects.gv;

        default:
            return SoundEffects.fu;
        }
    }

    public SoundEffect g() {
        switch (EnumSkeletonType.SyntheticClass_1.a[this.ordinal()]) {
        case 1:
            return SoundEffects.hd;

        case 2:
            return SoundEffects.gx;

        default:
            return SoundEffects.fA;
        }
    }

    static class SyntheticClass_1 {

        static final int[] a = new int[EnumSkeletonType.values().length];

        static {
            try {
                EnumSkeletonType.SyntheticClass_1.a[EnumSkeletonType.WITHER.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                EnumSkeletonType.SyntheticClass_1.a[EnumSkeletonType.STRAY.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

        }
    }
}
