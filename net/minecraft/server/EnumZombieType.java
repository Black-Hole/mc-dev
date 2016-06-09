package net.minecraft.server;

public enum EnumZombieType {

    NORMAL("Zombie", false), VILLAGER_FARMER("Zombie", true), VILLAGER_LIBRARIAN("Zombie", true), VILLAGER_PRIEST("Zombie", true), VILLAGER_SMITH("Zombie", true), VILLAGER_BUTCHER("Zombie", true), HUSK("Husk", false);

    private boolean h;
    private final ChatMessage i;

    private EnumZombieType(String s, boolean flag) {
        this.h = flag;
        this.i = new ChatMessage("entity." + s + ".name", new Object[0]);
    }

    public int a() {
        return this.ordinal();
    }

    public boolean b() {
        return this.h;
    }

    public int c() {
        return this.h ? this.a() - 1 : 0;
    }

    public static EnumZombieType a(int i) {
        return values()[i];
    }

    public static EnumZombieType b(int i) {
        return i >= 0 && i < 5 ? a(i + 1) : EnumZombieType.VILLAGER_FARMER;
    }

    public ChatMessage d() {
        return this.i;
    }

    public boolean e() {
        return this != EnumZombieType.HUSK;
    }

    public SoundEffect f() {
        switch (EnumZombieType.SyntheticClass_1.a[this.ordinal()]) {
        case 1:
            return SoundEffects.cI;

        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
            return SoundEffects.hP;

        default:
            return SoundEffects.hA;
        }
    }

    public SoundEffect g() {
        switch (EnumZombieType.SyntheticClass_1.a[this.ordinal()]) {
        case 1:
            return SoundEffects.cK;

        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
            return SoundEffects.hT;

        default:
            return SoundEffects.hI;
        }
    }

    public SoundEffect h() {
        switch (EnumZombieType.SyntheticClass_1.a[this.ordinal()]) {
        case 1:
            return SoundEffects.cJ;

        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
            return SoundEffects.hS;

        default:
            return SoundEffects.hE;
        }
    }

    public SoundEffect i() {
        switch (EnumZombieType.SyntheticClass_1.a[this.ordinal()]) {
        case 1:
            return SoundEffects.cL;

        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
            return SoundEffects.hU;

        default:
            return SoundEffects.hO;
        }
    }

    static class SyntheticClass_1 {

        static final int[] a = new int[EnumZombieType.values().length];

        static {
            try {
                EnumZombieType.SyntheticClass_1.a[EnumZombieType.HUSK.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                EnumZombieType.SyntheticClass_1.a[EnumZombieType.VILLAGER_FARMER.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                EnumZombieType.SyntheticClass_1.a[EnumZombieType.VILLAGER_LIBRARIAN.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                EnumZombieType.SyntheticClass_1.a[EnumZombieType.VILLAGER_PRIEST.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                EnumZombieType.SyntheticClass_1.a[EnumZombieType.VILLAGER_SMITH.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                EnumZombieType.SyntheticClass_1.a[EnumZombieType.VILLAGER_BUTCHER.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

        }
    }
}
