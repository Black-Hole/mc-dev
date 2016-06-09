package net.minecraft.server;

public class SoundEffectType {

    public static final SoundEffectType a = new SoundEffectType(1.0F, 1.0F, SoundEffects.hr, SoundEffects.hz, SoundEffects.hw, SoundEffects.hv, SoundEffects.hu);
    public static final SoundEffectType b = new SoundEffectType(1.0F, 1.0F, SoundEffects.cb, SoundEffects.cf, SoundEffects.ce, SoundEffects.cd, SoundEffects.cc);
    public static final SoundEffectType c = new SoundEffectType(1.0F, 1.0F, SoundEffects.bW, SoundEffects.ca, SoundEffects.bZ, SoundEffects.bY, SoundEffects.bX);
    public static final SoundEffectType d = new SoundEffectType(1.0F, 1.0F, SoundEffects.gl, SoundEffects.gt, SoundEffects.gq, SoundEffects.gp, SoundEffects.go);
    public static final SoundEffectType e = new SoundEffectType(1.0F, 1.5F, SoundEffects.dt, SoundEffects.dz, SoundEffects.dw, SoundEffects.dv, SoundEffects.du);
    public static final SoundEffectType f = new SoundEffectType(1.0F, 1.0F, SoundEffects.bR, SoundEffects.bV, SoundEffects.bU, SoundEffects.bT, SoundEffects.bS);
    public static final SoundEffectType g = new SoundEffectType(1.0F, 1.0F, SoundEffects.ag, SoundEffects.ak, SoundEffects.aj, SoundEffects.ai, SoundEffects.ah);
    public static final SoundEffectType h = new SoundEffectType(1.0F, 1.0F, SoundEffects.eS, SoundEffects.eW, SoundEffects.eV, SoundEffects.eU, SoundEffects.eT);
    public static final SoundEffectType i = new SoundEffectType(1.0F, 1.0F, SoundEffects.fX, SoundEffects.gb, SoundEffects.ga, SoundEffects.fZ, SoundEffects.fY);
    public static final SoundEffectType j = new SoundEffectType(1.0F, 1.0F, SoundEffects.db, SoundEffects.df, SoundEffects.de, SoundEffects.dd, SoundEffects.dc);
    public static final SoundEffectType k = new SoundEffectType(0.3F, 1.0F, SoundEffects.b, SoundEffects.h, SoundEffects.g, SoundEffects.e, SoundEffects.d);
    public static final SoundEffectType l = new SoundEffectType(1.0F, 1.0F, SoundEffects.fC, SoundEffects.fK, SoundEffects.fI, SoundEffects.fF, SoundEffects.fE);
    public final float m;
    public final float n;
    private final SoundEffect o;
    private final SoundEffect p;
    private final SoundEffect q;
    private final SoundEffect r;
    private final SoundEffect s;

    public SoundEffectType(float f, float f1, SoundEffect soundeffect, SoundEffect soundeffect1, SoundEffect soundeffect2, SoundEffect soundeffect3, SoundEffect soundeffect4) {
        this.m = f;
        this.n = f1;
        this.o = soundeffect;
        this.p = soundeffect1;
        this.q = soundeffect2;
        this.r = soundeffect3;
        this.s = soundeffect4;
    }

    public float a() {
        return this.m;
    }

    public float b() {
        return this.n;
    }

    public SoundEffect d() {
        return this.p;
    }

    public SoundEffect e() {
        return this.q;
    }

    public SoundEffect g() {
        return this.s;
    }
}
