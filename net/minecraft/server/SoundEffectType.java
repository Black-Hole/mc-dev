package net.minecraft.server;

public class SoundEffectType {

    public static final SoundEffectType a = new SoundEffectType(1.0F, 1.0F, SoundEffects.gX, SoundEffects.hf, SoundEffects.hc, SoundEffects.hb, SoundEffects.ha);
    public static final SoundEffectType b = new SoundEffectType(1.0F, 1.0F, SoundEffects.bZ, SoundEffects.cd, SoundEffects.cc, SoundEffects.cb, SoundEffects.ca);
    public static final SoundEffectType c = new SoundEffectType(1.0F, 1.0F, SoundEffects.bU, SoundEffects.bY, SoundEffects.bX, SoundEffects.bW, SoundEffects.bV);
    public static final SoundEffectType d = new SoundEffectType(1.0F, 1.0F, SoundEffects.fZ, SoundEffects.gh, SoundEffects.ge, SoundEffects.gd, SoundEffects.gc);
    public static final SoundEffectType e = new SoundEffectType(1.0F, 1.5F, SoundEffects.dn, SoundEffects.dt, SoundEffects.dq, SoundEffects.dp, SoundEffects.do);
    public static final SoundEffectType f = new SoundEffectType(1.0F, 1.0F, SoundEffects.bP, SoundEffects.bT, SoundEffects.bS, SoundEffects.bR, SoundEffects.bQ);
    public static final SoundEffectType g = new SoundEffectType(1.0F, 1.0F, SoundEffects.ag, SoundEffects.ak, SoundEffects.aj, SoundEffects.ai, SoundEffects.ah);
    public static final SoundEffectType h = new SoundEffectType(1.0F, 1.0F, SoundEffects.eG, SoundEffects.eK, SoundEffects.eJ, SoundEffects.eI, SoundEffects.eH);
    public static final SoundEffectType i = new SoundEffectType(1.0F, 1.0F, SoundEffects.fL, SoundEffects.fP, SoundEffects.fO, SoundEffects.fN, SoundEffects.fM);
    public static final SoundEffectType j = new SoundEffectType(1.0F, 1.0F, SoundEffects.cV, SoundEffects.cZ, SoundEffects.cY, SoundEffects.cX, SoundEffects.cW);
    public static final SoundEffectType k = new SoundEffectType(0.3F, 1.0F, SoundEffects.b, SoundEffects.h, SoundEffects.g, SoundEffects.e, SoundEffects.d);
    public static final SoundEffectType l = new SoundEffectType(1.0F, 1.0F, SoundEffects.fq, SoundEffects.fy, SoundEffects.fw, SoundEffects.ft, SoundEffects.fs);
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
