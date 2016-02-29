package net.minecraft.server;

public class DragonControllerHover extends AbstractDragonController {

    private Vec3D b;

    public DragonControllerHover(EntityEnderDragon entityenderdragon) {
        super(entityenderdragon);
    }

    public void c() {
        if (this.b == null) {
            this.b = new Vec3D(this.a.locX, this.a.locY, this.a.locZ);
        }

    }

    public boolean a() {
        return true;
    }

    public void d() {
        this.b = null;
    }

    public float f() {
        return 1.0F;
    }

    public Vec3D g() {
        return this.b;
    }

    public DragonControllerPhase<DragonControllerHover> i() {
        return DragonControllerPhase.k;
    }
}
