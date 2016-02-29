package net.minecraft.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DragonControllerManager {

    private static final Logger a = LogManager.getLogger();
    private final EntityEnderDragon b;
    private final IDragonController[] c = new IDragonController[DragonControllerPhase.c()];
    private IDragonController d;

    public DragonControllerManager(EntityEnderDragon entityenderdragon) {
        this.b = entityenderdragon;
        this.a(DragonControllerPhase.k);
    }

    public void a(DragonControllerPhase<?> dragoncontrollerphase) {
        if (this.d == null || dragoncontrollerphase != this.d.i()) {
            if (this.d != null) {
                this.d.e();
            }

            this.d = this.b(dragoncontrollerphase);
            if (!this.b.world.isClientSide) {
                this.b.getDataWatcher().set(EntityEnderDragon.a, Integer.valueOf(dragoncontrollerphase.b()));
            }

            DragonControllerManager.a.debug("Dragon is now in phase {} on the {}", new Object[] { dragoncontrollerphase, this.b.world.isClientSide ? "client" : "server"});
            this.d.d();
        }
    }

    public IDragonController a() {
        return this.d;
    }

    public <T extends IDragonController> T b(DragonControllerPhase<T> dragoncontrollerphase) {
        int i = dragoncontrollerphase.b();

        if (this.c[i] == null) {
            this.c[i] = dragoncontrollerphase.a(this.b);
        }

        return this.c[i];
    }
}
