package net.minecraft.server;

public enum EnumHorseType {

    HORSE("EntityHorse", "horse_white", SoundEffects.cp, SoundEffects.cw, SoundEffects.ct, LootTables.F), DONKEY("Donkey", "donkey", SoundEffects.ax, SoundEffects.aB, SoundEffects.aA, LootTables.F), MULE("Mule", "mule", SoundEffects.dD, SoundEffects.dF, SoundEffects.dE, LootTables.F), ZOMBIE("ZombieHorse", "horse_zombie", SoundEffects.hF, SoundEffects.hH, SoundEffects.hG, LootTables.G), SKELETON("SkeletonHorse", "horse_skeleton", SoundEffects.fv, SoundEffects.fx, SoundEffects.fw, LootTables.H);

    private final ChatMessage f;
    private final MinecraftKey g;
    private final SoundEffect h;
    private final SoundEffect i;
    private final SoundEffect j;
    private final MinecraftKey k;

    private EnumHorseType(String s, String s1, SoundEffect soundeffect, SoundEffect soundeffect1, SoundEffect soundeffect2, MinecraftKey minecraftkey) {
        this.f = new ChatMessage("entity." + s + ".name", new Object[0]);
        this.g = new MinecraftKey("textures/entity/horse/" + s1 + ".png");
        this.h = soundeffect1;
        this.i = soundeffect;
        this.j = soundeffect2;
        this.k = minecraftkey;
    }

    public SoundEffect a() {
        return this.i;
    }

    public SoundEffect b() {
        return this.h;
    }

    public SoundEffect c() {
        return this.j;
    }

    public ChatMessage d() {
        return this.f;
    }

    public boolean f() {
        return this == EnumHorseType.DONKEY || this == EnumHorseType.MULE;
    }

    public boolean g() {
        return this == EnumHorseType.DONKEY || this == EnumHorseType.MULE;
    }

    public boolean h() {
        return this == EnumHorseType.ZOMBIE || this == EnumHorseType.SKELETON;
    }

    public boolean i() {
        return !this.h() && this != EnumHorseType.MULE;
    }

    public boolean j() {
        return this == EnumHorseType.HORSE;
    }

    public int k() {
        return this.ordinal();
    }

    public static EnumHorseType a(int i) {
        return values()[i];
    }

    public MinecraftKey l() {
        return this.k;
    }
}
