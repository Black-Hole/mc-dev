package net.minecraft.server;

import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public abstract class EntityFishSchool extends EntityFish {

    private EntityFishSchool b;
    private int c = 1;

    public EntityFishSchool(EntityTypes<? extends EntityFishSchool> entitytypes, World world) {
        super(entitytypes, world);
    }

    @Override
    protected void initPathfinder() {
        super.initPathfinder();
        this.goalSelector.a(5, new PathfinderGoalFishSchool(this));
    }

    @Override
    public int dD() {
        return this.dY();
    }

    public int dY() {
        return super.dD();
    }

    @Override
    protected boolean dW() {
        return !this.dZ();
    }

    public boolean dZ() {
        return this.b != null && this.b.isAlive();
    }

    public EntityFishSchool a(EntityFishSchool entityfishschool) {
        this.b = entityfishschool;
        entityfishschool.ef();
        return entityfishschool;
    }

    public void ea() {
        this.b.eg();
        this.b = null;
    }

    private void ef() {
        ++this.c;
    }

    private void eg() {
        --this.c;
    }

    public boolean eb() {
        return this.ec() && this.c < this.dY();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.ec() && this.world.random.nextInt(200) == 1) {
            List<EntityFish> list = this.world.a(this.getClass(), this.getBoundingBox().grow(8.0D, 8.0D, 8.0D));

            if (list.size() <= 1) {
                this.c = 1;
            }
        }

    }

    public boolean ec() {
        return this.c > 1;
    }

    public boolean ed() {
        return this.h((Entity) this.b) <= 121.0D;
    }

    public void ee() {
        if (this.dZ()) {
            this.getNavigation().a((Entity) this.b, 1.0D);
        }

    }

    public void a(Stream<EntityFishSchool> stream) {
        stream.limit((long) (this.dY() - this.c)).filter((entityfishschool) -> {
            return entityfishschool != this;
        }).forEach((entityfishschool) -> {
            entityfishschool.a(this);
        });
    }

    @Nullable
    @Override
    public GroupDataEntity prepare(GeneratorAccess generatoraccess, DifficultyDamageScaler difficultydamagescaler, EnumMobSpawn enummobspawn, @Nullable GroupDataEntity groupdataentity, @Nullable NBTTagCompound nbttagcompound) {
        super.prepare(generatoraccess, difficultydamagescaler, enummobspawn, (GroupDataEntity) groupdataentity, nbttagcompound);
        if (groupdataentity == null) {
            groupdataentity = new EntityFishSchool.a(this);
        } else {
            this.a(((EntityFishSchool.a) groupdataentity).a);
        }

        return (GroupDataEntity) groupdataentity;
    }

    public static class a implements GroupDataEntity {

        public final EntityFishSchool a;

        public a(EntityFishSchool entityfishschool) {
            this.a = entityfishschool;
        }
    }
}
