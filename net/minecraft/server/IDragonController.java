package net.minecraft.server;

public interface IDragonController {

    boolean a();

    void b();

    void c();

    void a(EntityEnderCrystal entityendercrystal, BlockPosition blockposition, DamageSource damagesource, EntityHuman entityhuman);

    void d();

    void e();

    float f();

    float h();

    DragonControllerPhase<? extends IDragonController> i();

    Vec3D g();

    float a(EntityComplexPart entitycomplexpart, DamageSource damagesource, float f);
}
