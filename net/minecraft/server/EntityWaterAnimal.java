package net.minecraft.server;

public abstract class EntityWaterAnimal extends EntityCreature {

    protected EntityWaterAnimal(EntityTypes<? extends EntityWaterAnimal> entitytypes, World world) {
        super(entitytypes, world);
    }

    @Override
    public boolean cl() {
        return true;
    }

    @Override
    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.e;
    }

    @Override
    protected boolean a(GeneratorAccess generatoraccess, EnumMobSpawn enummobspawn, BlockPosition blockposition) {
        return generatoraccess.getFluid(blockposition).a(TagsFluid.WATER);
    }

    @Override
    public boolean a(IWorldReader iworldreader) {
        return iworldreader.i(this);
    }

    @Override
    public int A() {
        return 120;
    }

    @Override
    public boolean isTypeNotPersistent(double d0) {
        return true;
    }

    @Override
    protected int getExpValue(EntityHuman entityhuman) {
        return 1 + this.world.random.nextInt(3);
    }

    protected void a(int i) {
        if (this.isAlive() && !this.au()) {
            this.setAirTicks(i - 1);
            if (this.getAirTicks() == -20) {
                this.setAirTicks(0);
                this.damageEntity(DamageSource.DROWN, 2.0F);
            }
        } else {
            this.setAirTicks(300);
        }

    }

    @Override
    public void entityBaseTick() {
        int i = this.getAirTicks();

        super.entityBaseTick();
        this.a(i);
    }

    @Override
    public boolean bD() {
        return false;
    }

    @Override
    public boolean a(EntityHuman entityhuman) {
        return false;
    }
}
