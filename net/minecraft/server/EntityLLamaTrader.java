package net.minecraft.server;

import java.util.EnumSet;
import javax.annotation.Nullable;

public class EntityLLamaTrader extends EntityLlama {

    private int bJ;

    public EntityLLamaTrader(EntityTypes<? extends EntityLLamaTrader> entitytypes, World world) {
        super(entitytypes, world);
    }

    @Override
    protected EntityLlama eF() {
        return (EntityLlama) EntityTypes.TRADER_LLAMA.a(this.world);
    }

    @Override
    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("DespawnDelay", this.bJ);
    }

    @Override
    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.hasKeyOfType("DespawnDelay", 99)) {
            this.bJ = nbttagcompound.getInt("DespawnDelay");
        }

    }

    @Override
    protected void initPathfinder() {
        super.initPathfinder();
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 2.0D));
        this.targetSelector.a(1, new EntityLLamaTrader.a(this));
    }

    public void v(int i) {
        this.bJ = i;
    }

    @Override
    protected void g(EntityHuman entityhuman) {
        Entity entity = this.getLeashHolder();

        if (!(entity instanceof EntityVillagerTrader)) {
            super.g(entityhuman);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.bJ > 0 && --this.bJ == 0 && this.getLeashHolder() instanceof EntityVillagerTrader) {
            EntityVillagerTrader entityvillagertrader = (EntityVillagerTrader) this.getLeashHolder();
            int i = entityvillagertrader.eg();

            if (i - 1 > 0) {
                this.bJ = i - 1;
            } else {
                this.die();
            }
        }

    }

    @Nullable
    @Override
    public GroupDataEntity prepare(GeneratorAccess generatoraccess, DifficultyDamageScaler difficultydamagescaler, EnumMobSpawn enummobspawn, @Nullable GroupDataEntity groupdataentity, @Nullable NBTTagCompound nbttagcompound) {
        GroupDataEntity groupdataentity1 = super.prepare(generatoraccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);

        if (enummobspawn == EnumMobSpawn.EVENT) {
            this.setAgeRaw(0);
        }

        return groupdataentity1;
    }

    public class a extends PathfinderGoalTarget {

        private final EntityLlama b;
        private EntityLiving c;
        private int d;

        public a(EntityLlama entityllama) {
            super(entityllama, false);
            this.b = entityllama;
            this.a(EnumSet.of(PathfinderGoal.Type.TARGET));
        }

        @Override
        public boolean a() {
            if (!this.b.isLeashed()) {
                return false;
            } else {
                Entity entity = this.b.getLeashHolder();

                if (!(entity instanceof EntityVillagerTrader)) {
                    return false;
                } else {
                    EntityVillagerTrader entityvillagertrader = (EntityVillagerTrader) entity;

                    this.c = entityvillagertrader.getLastDamager();
                    int i = entityvillagertrader.cs();

                    return i != this.d && this.a(this.c, PathfinderTargetCondition.a);
                }
            }
        }

        @Override
        public void c() {
            this.e.setGoalTarget(this.c);
            Entity entity = this.b.getLeashHolder();

            if (entity instanceof EntityVillagerTrader) {
                this.d = ((EntityVillagerTrader) entity).cs();
            }

            super.c();
        }
    }
}
