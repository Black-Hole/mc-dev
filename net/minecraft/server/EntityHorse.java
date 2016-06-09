package net.minecraft.server;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;

public class EntityHorse extends EntityAnimal implements IInventoryListener, IJumpable {

    private static final Predicate<Entity> bD = new Predicate() {
        public boolean a(@Nullable Entity entity) {
            return entity instanceof EntityHorse && ((EntityHorse) entity).dt();
        }

        public boolean apply(Object object) {
            return this.a((Entity) object);
        }
    };
    public static final IAttribute attributeJumpStrength = (new AttributeRanged((IAttribute) null, "horse.jumpStrength", 0.7D, 0.0D, 2.0D)).a("Jump Strength").a(true);
    private static final UUID bF = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
    private static final DataWatcherObject<Byte> bG = DataWatcher.a(EntityHorse.class, DataWatcherRegistry.a);
    private static final DataWatcherObject<Integer> bH = DataWatcher.a(EntityHorse.class, DataWatcherRegistry.b);
    private static final DataWatcherObject<Integer> bI = DataWatcher.a(EntityHorse.class, DataWatcherRegistry.b);
    private static final DataWatcherObject<Optional<UUID>> bJ = DataWatcher.a(EntityHorse.class, DataWatcherRegistry.m);
    private static final DataWatcherObject<Integer> bK = DataWatcher.a(EntityHorse.class, DataWatcherRegistry.b);
    private static final String[] bL = new String[] { "textures/entity/horse/horse_white.png", "textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png", "textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png", "textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png"};
    private static final String[] bM = new String[] { "hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb"};
    private static final String[] bN = new String[] { null, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png"};
    private static final String[] bO = new String[] { "", "wo_", "wmo", "wdo", "bdo"};
    private final PathfinderGoalHorseTrap bP = new PathfinderGoalHorseTrap(this);
    private int bQ;
    private int bR;
    private int bS;
    public int bx;
    public int by;
    protected boolean bz;
    public InventoryHorseChest inventoryChest;
    private boolean bU;
    protected int bB;
    protected float jumpPower;
    private boolean canSlide;
    private boolean bW;
    private int bX;
    private float bY;
    private float bZ;
    private float ca;
    private float cb;
    private float cc;
    private float cd;
    private int ce;
    private String cf;
    private final String[] cg = new String[3];

    public EntityHorse(World world) {
        super(world);
        this.setSize(1.3964844F, 1.6F);
        this.fireProof = false;
        this.setHasChest(false);
        this.P = 1.0F;
        this.loadChest();
    }

    protected void r() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.2D));
        this.goalSelector.a(1, new PathfinderGoalTame(this, 1.2D));
        this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
        this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.0D));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.7D));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
    }

    protected void i() {
        super.i();
        this.datawatcher.register(EntityHorse.bG, Byte.valueOf((byte) 0));
        this.datawatcher.register(EntityHorse.bH, Integer.valueOf(EnumHorseType.HORSE.k()));
        this.datawatcher.register(EntityHorse.bI, Integer.valueOf(0));
        this.datawatcher.register(EntityHorse.bJ, Optional.absent());
        this.datawatcher.register(EntityHorse.bK, Integer.valueOf(EnumHorseArmor.NONE.a()));
    }

    public void setType(EnumHorseType enumhorsetype) {
        this.datawatcher.set(EntityHorse.bH, Integer.valueOf(enumhorsetype.k()));
        this.dQ();
    }

    public EnumHorseType getType() {
        return EnumHorseType.a(((Integer) this.datawatcher.get(EntityHorse.bH)).intValue());
    }

    public void setVariant(int i) {
        this.datawatcher.set(EntityHorse.bI, Integer.valueOf(i));
        this.dQ();
    }

    public int getVariant() {
        return ((Integer) this.datawatcher.get(EntityHorse.bI)).intValue();
    }

    public String getName() {
        return this.hasCustomName() ? this.getCustomName() : this.getType().d().toPlainText();
    }

    private boolean o(int i) {
        return (((Byte) this.datawatcher.get(EntityHorse.bG)).byteValue() & i) != 0;
    }

    private void c(int i, boolean flag) {
        byte b0 = ((Byte) this.datawatcher.get(EntityHorse.bG)).byteValue();

        if (flag) {
            this.datawatcher.set(EntityHorse.bG, Byte.valueOf((byte) (b0 | i)));
        } else {
            this.datawatcher.set(EntityHorse.bG, Byte.valueOf((byte) (b0 & ~i)));
        }

    }

    public boolean dg() {
        return !this.isBaby();
    }

    public boolean isTamed() {
        return this.o(2);
    }

    public boolean di() {
        return this.dg();
    }

    @Nullable
    public UUID getOwnerUUID() {
        return (UUID) ((Optional) this.datawatcher.get(EntityHorse.bJ)).orNull();
    }

    public void setOwnerUUID(@Nullable UUID uuid) {
        this.datawatcher.set(EntityHorse.bJ, Optional.fromNullable(uuid));
    }

    public float dn() {
        return 0.5F;
    }

    public void a(boolean flag) {
        if (flag) {
            this.a(this.dn());
        } else {
            this.a(1.0F);
        }

    }

    public boolean do_() {
        return this.bz;
    }

    public void setTame(boolean flag) {
        this.c(2, flag);
    }

    public void q(boolean flag) {
        this.bz = flag;
    }

    public boolean a(EntityHuman entityhuman) {
        return !this.getType().h() && super.a(entityhuman);
    }

    protected void q(float f) {
        if (f > 6.0F && this.dr()) {
            this.v(false);
        }

    }

    public boolean hasChest() {
        return this.getType().f() && this.o(8);
    }

    public EnumHorseArmor dq() {
        return EnumHorseArmor.a(((Integer) this.datawatcher.get(EntityHorse.bK)).intValue());
    }

    public boolean dr() {
        return this.o(32);
    }

    public boolean ds() {
        return this.o(64);
    }

    public boolean dt() {
        return this.o(16);
    }

    public boolean hasReproduced() {
        return this.bU;
    }

    public void f(ItemStack itemstack) {
        EnumHorseArmor enumhorsearmor = EnumHorseArmor.a(itemstack);

        this.datawatcher.set(EntityHorse.bK, Integer.valueOf(enumhorsearmor.a()));
        this.dQ();
        if (!this.world.isClientSide) {
            this.getAttributeInstance(GenericAttributes.g).b(EntityHorse.bF);
            int i = enumhorsearmor.c();

            if (i != 0) {
                this.getAttributeInstance(GenericAttributes.g).b((new AttributeModifier(EntityHorse.bF, "Horse armor bonus", (double) i, 0)).a(false));
            }
        }

    }

    public void r(boolean flag) {
        this.c(16, flag);
    }

    public void setHasChest(boolean flag) {
        this.c(8, flag);
    }

    public void t(boolean flag) {
        this.bU = flag;
    }

    public void u(boolean flag) {
        this.c(4, flag);
    }

    public int getTemper() {
        return this.bB;
    }

    public void setTemper(int i) {
        this.bB = i;
    }

    public int n(int i) {
        int j = MathHelper.clamp(this.getTemper() + i, 0, this.getMaxDomestication());

        this.setTemper(j);
        return j;
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        Entity entity = damagesource.getEntity();

        return this.isVehicle() && entity != null && this.y(entity) ? false : super.damageEntity(damagesource, f);
    }

    public boolean isCollidable() {
        return !this.isVehicle();
    }

    public boolean dw() {
        int i = MathHelper.floor(this.locX);
        int j = MathHelper.floor(this.locZ);

        this.world.getBiome(new BlockPosition(i, 0, j));
        return true;
    }

    public void dx() {
        if (!this.world.isClientSide && this.hasChest()) {
            this.a(Item.getItemOf(Blocks.CHEST), 1);
            this.setHasChest(false);
        }
    }

    private void dM() {
        this.dT();
        if (!this.isSilent()) {
            this.world.a((EntityHuman) null, this.locX, this.locY, this.locZ, SoundEffects.cu, this.bC(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
        }

    }

    public void e(float f, float f1) {
        if (f > 1.0F) {
            this.a(SoundEffects.cy, 0.4F, 1.0F);
        }

        int i = MathHelper.f((f * 0.5F - 3.0F) * f1);

        if (i > 0) {
            this.damageEntity(DamageSource.FALL, (float) i);
            if (this.isVehicle()) {
                Iterator iterator = this.by().iterator();

                while (iterator.hasNext()) {
                    Entity entity = (Entity) iterator.next();

                    entity.damageEntity(DamageSource.FALL, (float) i);
                }
            }

            IBlockData iblockdata = this.world.getType(new BlockPosition(this.locX, this.locY - 0.2D - (double) this.lastYaw, this.locZ));
            Block block = iblockdata.getBlock();

            if (iblockdata.getMaterial() != Material.AIR && !this.isSilent()) {
                SoundEffectType soundeffecttype = block.w();

                this.world.a((EntityHuman) null, this.locX, this.locY, this.locZ, soundeffecttype.d(), this.bC(), soundeffecttype.a() * 0.5F, soundeffecttype.b() * 0.75F);
            }

        }
    }

    private int dN() {
        EnumHorseType enumhorsetype = this.getType();

        return this.hasChest() && enumhorsetype.f() ? 17 : 2;
    }

    public void loadChest() {
        InventoryHorseChest inventoryhorsechest = this.inventoryChest;

        this.inventoryChest = new InventoryHorseChest("HorseChest", this.dN());
        this.inventoryChest.a(this.getName());
        if (inventoryhorsechest != null) {
            inventoryhorsechest.b(this);
            int i = Math.min(inventoryhorsechest.getSize(), this.inventoryChest.getSize());

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = inventoryhorsechest.getItem(j);

                if (itemstack != null) {
                    this.inventoryChest.setItem(j, itemstack.cloneItemStack());
                }
            }
        }

        this.inventoryChest.a((IInventoryListener) this);
        this.dP();
    }

    private void dP() {
        if (!this.world.isClientSide) {
            this.u(this.inventoryChest.getItem(0) != null);
            if (this.getType().j()) {
                this.f(this.inventoryChest.getItem(1));
            }
        }

    }

    public void a(InventorySubcontainer inventorysubcontainer) {
        EnumHorseArmor enumhorsearmor = this.dq();
        boolean flag = this.dz();

        this.dP();
        if (this.ticksLived > 20) {
            if (enumhorsearmor == EnumHorseArmor.NONE && enumhorsearmor != this.dq()) {
                this.a(SoundEffects.cr, 0.5F, 1.0F);
            } else if (enumhorsearmor != this.dq()) {
                this.a(SoundEffects.cr, 0.5F, 1.0F);
            }

            if (!flag && this.dz()) {
                this.a(SoundEffects.cz, 0.5F, 1.0F);
            }
        }

    }

    public boolean cK() {
        this.dw();
        return super.cK();
    }

    protected EntityHorse a(Entity entity, double d0) {
        double d1 = Double.MAX_VALUE;
        Entity entity1 = null;
        List list = this.world.getEntities(entity, entity.getBoundingBox().a(d0, d0, d0), EntityHorse.bD);
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            Entity entity2 = (Entity) iterator.next();
            double d2 = entity2.e(entity.locX, entity.locY, entity.locZ);

            if (d2 < d1) {
                entity1 = entity2;
                d1 = d2;
            }
        }

        return (EntityHorse) entity1;
    }

    public double getJumpStrength() {
        return this.getAttributeInstance(EntityHorse.attributeJumpStrength).getValue();
    }

    protected SoundEffect bW() {
        this.dT();
        return this.getType().c();
    }

    protected SoundEffect bV() {
        this.dT();
        if (this.random.nextInt(3) == 0) {
            this.setStanding();
        }

        return this.getType().b();
    }

    public boolean dz() {
        return this.o(4);
    }

    protected SoundEffect G() {
        this.dT();
        if (this.random.nextInt(10) == 0 && !this.cj()) {
            this.setStanding();
        }

        return this.getType().a();
    }

    @Nullable
    protected SoundEffect dA() {
        this.dT();
        this.setStanding();
        EnumHorseType enumhorsetype = this.getType();

        return enumhorsetype.h() ? null : (enumhorsetype.g() ? SoundEffects.ay : SoundEffects.cq);
    }

    protected void a(BlockPosition blockposition, Block block) {
        SoundEffectType soundeffecttype = block.w();

        if (this.world.getType(blockposition.up()).getBlock() == Blocks.SNOW_LAYER) {
            soundeffecttype = Blocks.SNOW_LAYER.w();
        }

        if (!block.getBlockData().getMaterial().isLiquid()) {
            EnumHorseType enumhorsetype = this.getType();

            if (this.isVehicle() && !enumhorsetype.g()) {
                ++this.ce;
                if (this.ce > 5 && this.ce % 3 == 0) {
                    this.a(SoundEffects.cv, soundeffecttype.a() * 0.15F, soundeffecttype.b());
                    if (enumhorsetype == EnumHorseType.HORSE && this.random.nextInt(10) == 0) {
                        this.a(SoundEffects.cs, soundeffecttype.a() * 0.6F, soundeffecttype.b());
                    }
                } else if (this.ce <= 5) {
                    this.a(SoundEffects.cB, soundeffecttype.a() * 0.15F, soundeffecttype.b());
                }
            } else if (soundeffecttype == SoundEffectType.a) {
                this.a(SoundEffects.cB, soundeffecttype.a() * 0.15F, soundeffecttype.b());
            } else {
                this.a(SoundEffects.cA, soundeffecttype.a() * 0.15F, soundeffecttype.b());
            }
        }

    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeMap().b(EntityHorse.attributeJumpStrength);
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(53.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.22499999403953552D);
    }

    public int cO() {
        return 6;
    }

    public int getMaxDomestication() {
        return 100;
    }

    protected float ch() {
        return 0.8F;
    }

    public int C() {
        return 400;
    }

    private void dQ() {
        this.cf = null;
    }

    public void f(EntityHuman entityhuman) {
        if (!this.world.isClientSide && (!this.isVehicle() || this.w(entityhuman)) && this.isTamed()) {
            this.inventoryChest.a(this.getName());
            entityhuman.a(this, (IInventory) this.inventoryChest);
        }

    }

    public boolean a(EntityHuman entityhuman, EnumHand enumhand, @Nullable ItemStack itemstack) {
        if (itemstack != null && itemstack.getItem() == Items.SPAWN_EGG) {
            return super.a(entityhuman, enumhand, itemstack);
        } else if (!this.isTamed() && this.getType().h()) {
            return false;
        } else if (this.isTamed() && this.dg() && entityhuman.isSneaking()) {
            this.f(entityhuman);
            return true;
        } else if (this.di() && this.isVehicle()) {
            return super.a(entityhuman, enumhand, itemstack);
        } else {
            if (itemstack != null) {
                if (this.getType().j()) {
                    EnumHorseArmor enumhorsearmor = EnumHorseArmor.a(itemstack);

                    if (enumhorsearmor != EnumHorseArmor.NONE) {
                        if (!this.isTamed()) {
                            this.dJ();
                            return true;
                        }

                        this.f(entityhuman);
                        return true;
                    }
                }

                boolean flag = false;

                if (!this.getType().h()) {
                    float f = 0.0F;
                    short short0 = 0;
                    byte b0 = 0;

                    if (itemstack.getItem() == Items.WHEAT) {
                        f = 2.0F;
                        short0 = 20;
                        b0 = 3;
                    } else if (itemstack.getItem() == Items.SUGAR) {
                        f = 1.0F;
                        short0 = 30;
                        b0 = 3;
                    } else if (Block.asBlock(itemstack.getItem()) == Blocks.HAY_BLOCK) {
                        f = 20.0F;
                        short0 = 180;
                    } else if (itemstack.getItem() == Items.APPLE) {
                        f = 3.0F;
                        short0 = 60;
                        b0 = 3;
                    } else if (itemstack.getItem() == Items.GOLDEN_CARROT) {
                        f = 4.0F;
                        short0 = 60;
                        b0 = 5;
                        if (this.isTamed() && this.getAge() == 0) {
                            flag = true;
                            this.c(entityhuman);
                        }
                    } else if (itemstack.getItem() == Items.GOLDEN_APPLE) {
                        f = 10.0F;
                        short0 = 240;
                        b0 = 10;
                        if (this.isTamed() && this.getAge() == 0 && !this.isInLove()) {
                            flag = true;
                            this.c(entityhuman);
                        }
                    }

                    if (this.getHealth() < this.getMaxHealth() && f > 0.0F) {
                        this.heal(f);
                        flag = true;
                    }

                    if (!this.dg() && short0 > 0) {
                        if (!this.world.isClientSide) {
                            this.setAge(short0);
                        }

                        flag = true;
                    }

                    if (b0 > 0 && (flag || !this.isTamed()) && this.getTemper() < this.getMaxDomestication()) {
                        flag = true;
                        if (!this.world.isClientSide) {
                            this.n(b0);
                        }
                    }

                    if (flag) {
                        this.dM();
                    }
                }

                if (!this.isTamed() && !flag) {
                    if (itemstack.a(entityhuman, (EntityLiving) this, enumhand)) {
                        return true;
                    }

                    this.dJ();
                    return true;
                }

                if (!flag && this.getType().f() && !this.hasChest() && itemstack.getItem() == Item.getItemOf(Blocks.CHEST)) {
                    this.setHasChest(true);
                    this.a(SoundEffects.az, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                    flag = true;
                    this.loadChest();
                }

                if (!flag && this.di() && !this.dz() && itemstack.getItem() == Items.SADDLE) {
                    this.f(entityhuman);
                    return true;
                }

                if (flag) {
                    if (!entityhuman.abilities.canInstantlyBuild) {
                        --itemstack.count;
                    }

                    return true;
                }
            }

            if (this.di() && !this.isVehicle()) {
                if (itemstack != null && itemstack.a(entityhuman, (EntityLiving) this, enumhand)) {
                    return true;
                } else {
                    this.h(entityhuman);
                    return true;
                }
            } else {
                return super.a(entityhuman, enumhand, itemstack);
            }
        }
    }

    private void h(EntityHuman entityhuman) {
        entityhuman.yaw = this.yaw;
        entityhuman.pitch = this.pitch;
        this.v(false);
        this.w(false);
        if (!this.world.isClientSide) {
            entityhuman.startRiding(this);
        }

    }

    protected boolean cj() {
        return this.isVehicle() && this.dz() ? true : this.dr() || this.ds();
    }

    public boolean e(@Nullable ItemStack itemstack) {
        return false;
    }

    private void dS() {
        this.bx = 1;
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        if (!this.world.isClientSide) {
            this.dropChest();
        }

    }

    public void n() {
        if (this.random.nextInt(200) == 0) {
            this.dS();
        }

        super.n();
        if (!this.world.isClientSide) {
            if (this.random.nextInt(900) == 0 && this.deathTicks == 0) {
                this.heal(1.0F);
            }

            if (!this.dr() && !this.isVehicle() && this.random.nextInt(300) == 0 && this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(this.locY) - 1, MathHelper.floor(this.locZ))).getBlock() == Blocks.GRASS) {
                this.v(true);
            }

            if (this.dr() && ++this.bQ > 50) {
                this.bQ = 0;
                this.v(false);
            }

            if (this.dt() && !this.dg() && !this.dr()) {
                EntityHorse entityhorse = this.a(this, 16.0D);

                if (entityhorse != null && this.h((Entity) entityhorse) > 4.0D) {
                    this.navigation.a((Entity) entityhorse);
                }
            }

            if (this.dL() && this.bX++ >= 18000) {
                this.die();
            }
        }

    }

    public void m() {
        super.m();
        if (this.world.isClientSide && this.datawatcher.a()) {
            this.datawatcher.e();
            this.dQ();
        }

        if (this.bR > 0 && ++this.bR > 30) {
            this.bR = 0;
            this.c(128, false);
        }

        if (this.bA() && this.bS > 0 && ++this.bS > 20) {
            this.bS = 0;
            this.w(false);
        }

        if (this.bx > 0 && ++this.bx > 8) {
            this.bx = 0;
        }

        if (this.by > 0) {
            ++this.by;
            if (this.by > 300) {
                this.by = 0;
            }
        }

        this.bZ = this.bY;
        if (this.dr()) {
            this.bY += (1.0F - this.bY) * 0.4F + 0.05F;
            if (this.bY > 1.0F) {
                this.bY = 1.0F;
            }
        } else {
            this.bY += (0.0F - this.bY) * 0.4F - 0.05F;
            if (this.bY < 0.0F) {
                this.bY = 0.0F;
            }
        }

        this.cb = this.ca;
        if (this.ds()) {
            this.bY = 0.0F;
            this.bZ = this.bY;
            this.ca += (1.0F - this.ca) * 0.4F + 0.05F;
            if (this.ca > 1.0F) {
                this.ca = 1.0F;
            }
        } else {
            this.canSlide = false;
            this.ca += (0.8F * this.ca * this.ca * this.ca - this.ca) * 0.6F - 0.05F;
            if (this.ca < 0.0F) {
                this.ca = 0.0F;
            }
        }

        this.cd = this.cc;
        if (this.o(128)) {
            this.cc += (1.0F - this.cc) * 0.7F + 0.05F;
            if (this.cc > 1.0F) {
                this.cc = 1.0F;
            }
        } else {
            this.cc += (0.0F - this.cc) * 0.7F - 0.05F;
            if (this.cc < 0.0F) {
                this.cc = 0.0F;
            }
        }

    }

    private void dT() {
        if (!this.world.isClientSide) {
            this.bR = 1;
            this.c(128, true);
        }

    }

    private boolean dU() {
        return !this.isVehicle() && !this.isPassenger() && this.isTamed() && this.dg() && this.getType().i() && this.getHealth() >= this.getMaxHealth() && this.isInLove();
    }

    public void v(boolean flag) {
        this.c(32, flag);
    }

    public void w(boolean flag) {
        if (flag) {
            this.v(false);
        }

        this.c(64, flag);
    }

    private void setStanding() {
        if (this.bA()) {
            this.bS = 1;
            this.w(true);
        }

    }

    public void dJ() {
        this.setStanding();
        SoundEffect soundeffect = this.dA();

        if (soundeffect != null) {
            this.a(soundeffect, this.ch(), this.ci());
        }

    }

    public void dropChest() {
        this.a((Entity) this, this.inventoryChest);
        this.dx();
    }

    private void a(Entity entity, InventoryHorseChest inventoryhorsechest) {
        if (inventoryhorsechest != null && !this.world.isClientSide) {
            for (int i = 0; i < inventoryhorsechest.getSize(); ++i) {
                ItemStack itemstack = inventoryhorsechest.getItem(i);

                if (itemstack != null) {
                    this.a(itemstack, 0.0F);
                }
            }

        }
    }

    public boolean g(EntityHuman entityhuman) {
        this.setOwnerUUID(entityhuman.getUniqueID());
        this.setTame(true);
        return true;
    }

    public void g(float f, float f1) {
        if (this.isVehicle() && this.cP() && this.dz()) {
            EntityLiving entityliving = (EntityLiving) this.bw();

            this.yaw = entityliving.yaw;
            this.lastYaw = this.yaw;
            this.pitch = entityliving.pitch * 0.5F;
            this.setYawPitch(this.yaw, this.pitch);
            this.aO = this.yaw;
            this.aQ = this.aO;
            f = entityliving.bf * 0.5F;
            f1 = entityliving.bg;
            if (f1 <= 0.0F) {
                f1 *= 0.25F;
                this.ce = 0;
            }

            if (this.onGround && this.jumpPower == 0.0F && this.ds() && !this.canSlide) {
                f = 0.0F;
                f1 = 0.0F;
            }

            if (this.jumpPower > 0.0F && !this.do_() && this.onGround) {
                this.motY = this.getJumpStrength() * (double) this.jumpPower;
                if (this.hasEffect(MobEffects.JUMP)) {
                    this.motY += (double) ((float) (this.getEffect(MobEffects.JUMP).getAmplifier() + 1) * 0.1F);
                }

                this.q(true);
                this.impulse = true;
                if (f1 > 0.0F) {
                    float f2 = MathHelper.sin(this.yaw * 0.017453292F);
                    float f3 = MathHelper.cos(this.yaw * 0.017453292F);

                    this.motX += (double) (-0.4F * f2 * this.jumpPower);
                    this.motZ += (double) (0.4F * f3 * this.jumpPower);
                    this.a(SoundEffects.cx, 0.4F, 1.0F);
                }

                this.jumpPower = 0.0F;
            }

            this.aS = this.cp() * 0.1F;
            if (this.bA()) {
                this.l((float) this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue());
                super.g(f, f1);
            } else if (entityliving instanceof EntityHuman) {
                this.motX = 0.0D;
                this.motY = 0.0D;
                this.motZ = 0.0D;
            }

            if (this.onGround) {
                this.jumpPower = 0.0F;
                this.q(false);
            }

            this.aG = this.aH;
            double d0 = this.locX - this.lastX;
            double d1 = this.locZ - this.lastZ;
            float f4 = MathHelper.sqrt(d0 * d0 + d1 * d1) * 4.0F;

            if (f4 > 1.0F) {
                f4 = 1.0F;
            }

            this.aH += (f4 - this.aH) * 0.4F;
            this.aI += this.aH;
        } else {
            this.aS = 0.02F;
            super.g(f, f1);
        }
    }

    public static void b(DataConverterManager dataconvertermanager) {
        EntityInsentient.a(dataconvertermanager, "EntityHorse");
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("EntityHorse", new String[] { "Items"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItem("EntityHorse", new String[] { "ArmorItem", "SaddleItem"})));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setBoolean("EatingHaystack", this.dr());
        nbttagcompound.setBoolean("ChestedHorse", this.hasChest());
        nbttagcompound.setBoolean("HasReproduced", this.hasReproduced());
        nbttagcompound.setBoolean("Bred", this.dt());
        nbttagcompound.setInt("Type", this.getType().k());
        nbttagcompound.setInt("Variant", this.getVariant());
        nbttagcompound.setInt("Temper", this.getTemper());
        nbttagcompound.setBoolean("Tame", this.isTamed());
        nbttagcompound.setBoolean("SkeletonTrap", this.dL());
        nbttagcompound.setInt("SkeletonTrapTime", this.bX);
        if (this.getOwnerUUID() != null) {
            nbttagcompound.setString("OwnerUUID", this.getOwnerUUID().toString());
        }

        if (this.hasChest()) {
            NBTTagList nbttaglist = new NBTTagList();

            for (int i = 2; i < this.inventoryChest.getSize(); ++i) {
                ItemStack itemstack = this.inventoryChest.getItem(i);

                if (itemstack != null) {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();

                    nbttagcompound1.setByte("Slot", (byte) i);
                    itemstack.save(nbttagcompound1);
                    nbttaglist.add(nbttagcompound1);
                }
            }

            nbttagcompound.set("Items", nbttaglist);
        }

        if (this.inventoryChest.getItem(1) != null) {
            nbttagcompound.set("ArmorItem", this.inventoryChest.getItem(1).save(new NBTTagCompound()));
        }

        if (this.inventoryChest.getItem(0) != null) {
            nbttagcompound.set("SaddleItem", this.inventoryChest.getItem(0).save(new NBTTagCompound()));
        }

    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.v(nbttagcompound.getBoolean("EatingHaystack"));
        this.r(nbttagcompound.getBoolean("Bred"));
        this.setHasChest(nbttagcompound.getBoolean("ChestedHorse"));
        this.t(nbttagcompound.getBoolean("HasReproduced"));
        this.setType(EnumHorseType.a(nbttagcompound.getInt("Type")));
        this.setVariant(nbttagcompound.getInt("Variant"));
        this.setTemper(nbttagcompound.getInt("Temper"));
        this.setTame(nbttagcompound.getBoolean("Tame"));
        this.y(nbttagcompound.getBoolean("SkeletonTrap"));
        this.bX = nbttagcompound.getInt("SkeletonTrapTime");
        String s;

        if (nbttagcompound.hasKeyOfType("OwnerUUID", 8)) {
            s = nbttagcompound.getString("OwnerUUID");
        } else {
            String s1 = nbttagcompound.getString("Owner");

            s = NameReferencingFileConverter.a(this.h(), s1);
        }

        if (!s.isEmpty()) {
            this.setOwnerUUID(UUID.fromString(s));
        }

        AttributeInstance attributeinstance = this.getAttributeMap().a("Speed");

        if (attributeinstance != null) {
            this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(attributeinstance.b() * 0.25D);
        }

        if (this.hasChest()) {
            NBTTagList nbttaglist = nbttagcompound.getList("Items", 10);

            this.loadChest();

            for (int i = 0; i < nbttaglist.size(); ++i) {
                NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
                int j = nbttagcompound1.getByte("Slot") & 255;

                if (j >= 2 && j < this.inventoryChest.getSize()) {
                    this.inventoryChest.setItem(j, ItemStack.createStack(nbttagcompound1));
                }
            }
        }

        ItemStack itemstack;

        if (nbttagcompound.hasKeyOfType("ArmorItem", 10)) {
            itemstack = ItemStack.createStack(nbttagcompound.getCompound("ArmorItem"));
            if (itemstack != null && EnumHorseArmor.b(itemstack.getItem())) {
                this.inventoryChest.setItem(1, itemstack);
            }
        }

        if (nbttagcompound.hasKeyOfType("SaddleItem", 10)) {
            itemstack = ItemStack.createStack(nbttagcompound.getCompound("SaddleItem"));
            if (itemstack != null && itemstack.getItem() == Items.SADDLE) {
                this.inventoryChest.setItem(0, itemstack);
            }
        }

        this.dP();
    }

    public boolean mate(EntityAnimal entityanimal) {
        if (entityanimal == this) {
            return false;
        } else if (entityanimal.getClass() != this.getClass()) {
            return false;
        } else {
            EntityHorse entityhorse = (EntityHorse) entityanimal;

            if (this.dU() && entityhorse.dU()) {
                EnumHorseType enumhorsetype = this.getType();
                EnumHorseType enumhorsetype1 = entityhorse.getType();

                return enumhorsetype == enumhorsetype1 || enumhorsetype == EnumHorseType.HORSE && enumhorsetype1 == EnumHorseType.DONKEY || enumhorsetype == EnumHorseType.DONKEY && enumhorsetype1 == EnumHorseType.HORSE;
            } else {
                return false;
            }
        }
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        EntityHorse entityhorse = (EntityHorse) entityageable;
        EntityHorse entityhorse1 = new EntityHorse(this.world);
        EnumHorseType enumhorsetype = this.getType();
        EnumHorseType enumhorsetype1 = entityhorse.getType();
        EnumHorseType enumhorsetype2 = EnumHorseType.HORSE;

        if (enumhorsetype == enumhorsetype1) {
            enumhorsetype2 = enumhorsetype;
        } else if (enumhorsetype == EnumHorseType.HORSE && enumhorsetype1 == EnumHorseType.DONKEY || enumhorsetype == EnumHorseType.DONKEY && enumhorsetype1 == EnumHorseType.HORSE) {
            enumhorsetype2 = EnumHorseType.MULE;
        }

        if (enumhorsetype2 == EnumHorseType.HORSE) {
            int i = this.random.nextInt(9);
            int j;

            if (i < 4) {
                j = this.getVariant() & 255;
            } else if (i < 8) {
                j = entityhorse.getVariant() & 255;
            } else {
                j = this.random.nextInt(7);
            }

            int k = this.random.nextInt(5);

            if (k < 2) {
                j |= this.getVariant() & '\uff00';
            } else if (k < 4) {
                j |= entityhorse.getVariant() & '\uff00';
            } else {
                j |= this.random.nextInt(5) << 8 & '\uff00';
            }

            entityhorse1.setVariant(j);
        }

        entityhorse1.setType(enumhorsetype2);
        double d0 = this.getAttributeInstance(GenericAttributes.maxHealth).b() + entityageable.getAttributeInstance(GenericAttributes.maxHealth).b() + (double) this.dW();

        entityhorse1.getAttributeInstance(GenericAttributes.maxHealth).setValue(d0 / 3.0D);
        double d1 = this.getAttributeInstance(EntityHorse.attributeJumpStrength).b() + entityageable.getAttributeInstance(EntityHorse.attributeJumpStrength).b() + this.dX();

        entityhorse1.getAttributeInstance(EntityHorse.attributeJumpStrength).setValue(d1 / 3.0D);
        double d2 = this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).b() + entityageable.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).b() + this.dY();

        entityhorse1.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(d2 / 3.0D);
        return entityhorse1;
    }

    @Nullable
    public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, @Nullable GroupDataEntity groupdataentity) {
        Object object = super.prepare(difficultydamagescaler, groupdataentity);
        int i = 0;
        EnumHorseType enumhorsetype;

        if (object instanceof EntityHorse.a) {
            enumhorsetype = ((EntityHorse.a) object).a;
            i = ((EntityHorse.a) object).b & 255 | this.random.nextInt(5) << 8;
        } else {
            if (this.random.nextInt(10) == 0) {
                enumhorsetype = EnumHorseType.DONKEY;
            } else {
                int j = this.random.nextInt(7);
                int k = this.random.nextInt(5);

                enumhorsetype = EnumHorseType.HORSE;
                i = j | k << 8;
            }

            object = new EntityHorse.a(enumhorsetype, i);
        }

        this.setType(enumhorsetype);
        this.setVariant(i);
        if (this.random.nextInt(5) == 0) {
            this.setAgeRaw(-24000);
        }

        if (enumhorsetype.h()) {
            this.getAttributeInstance(GenericAttributes.maxHealth).setValue(15.0D);
            this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.20000000298023224D);
        } else {
            this.getAttributeInstance(GenericAttributes.maxHealth).setValue((double) this.dW());
            if (enumhorsetype == EnumHorseType.HORSE) {
                this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(this.dY());
            } else {
                this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.17499999701976776D);
            }
        }

        if (enumhorsetype.g()) {
            this.getAttributeInstance(EntityHorse.attributeJumpStrength).setValue(0.5D);
        } else {
            this.getAttributeInstance(EntityHorse.attributeJumpStrength).setValue(this.dX());
        }

        this.setHealth(this.getMaxHealth());
        return (GroupDataEntity) object;
    }

    public boolean cP() {
        Entity entity = this.bw();

        return entity instanceof EntityLiving;
    }

    public boolean b() {
        return this.dz();
    }

    public void b(int i) {
        this.canSlide = true;
        this.setStanding();
    }

    public void r_() {}

    public void k(Entity entity) {
        super.k(entity);
        if (entity instanceof EntityInsentient) {
            EntityInsentient entityinsentient = (EntityInsentient) entity;

            this.aO = entityinsentient.aO;
        }

        if (this.cb > 0.0F) {
            float f = MathHelper.sin(this.aO * 0.017453292F);
            float f1 = MathHelper.cos(this.aO * 0.017453292F);
            float f2 = 0.7F * this.cb;
            float f3 = 0.15F * this.cb;

            entity.setPosition(this.locX + (double) (f2 * f), this.locY + this.ay() + entity.ax() + (double) f3, this.locZ - (double) (f2 * f1));
            if (entity instanceof EntityLiving) {
                ((EntityLiving) entity).aO = this.aO;
            }
        }

    }

    public double ay() {
        double d0 = super.ay();

        if (this.getType() == EnumHorseType.SKELETON) {
            d0 -= 0.1875D;
        } else if (this.getType() == EnumHorseType.DONKEY) {
            d0 -= 0.25D;
        }

        return d0;
    }

    private float dW() {
        return 15.0F + (float) this.random.nextInt(8) + (float) this.random.nextInt(9);
    }

    private double dX() {
        return 0.4000000059604645D + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D;
    }

    private double dY() {
        return (0.44999998807907104D + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D) * 0.25D;
    }

    public boolean dL() {
        return this.bW;
    }

    public void y(boolean flag) {
        if (flag != this.bW) {
            this.bW = flag;
            if (flag) {
                this.goalSelector.a(1, this.bP);
            } else {
                this.goalSelector.a((PathfinderGoal) this.bP);
            }
        }

    }

    public boolean m_() {
        return false;
    }

    public float getHeadHeight() {
        return this.length;
    }

    public boolean c(int i, @Nullable ItemStack itemstack) {
        if (i == 499 && this.getType().f()) {
            if (itemstack == null && this.hasChest()) {
                this.setHasChest(false);
                this.loadChest();
                return true;
            }

            if (itemstack != null && itemstack.getItem() == Item.getItemOf(Blocks.CHEST) && !this.hasChest()) {
                this.setHasChest(true);
                this.loadChest();
                return true;
            }
        }

        int j = i - 400;

        if (j >= 0 && j < 2 && j < this.inventoryChest.getSize()) {
            if (j == 0 && itemstack != null && itemstack.getItem() != Items.SADDLE) {
                return false;
            } else if (j == 1 && (itemstack != null && !EnumHorseArmor.b(itemstack.getItem()) || !this.getType().j())) {
                return false;
            } else {
                this.inventoryChest.setItem(j, itemstack);
                this.dP();
                return true;
            }
        } else {
            int k = i - 500 + 2;

            if (k >= 2 && k < this.inventoryChest.getSize()) {
                this.inventoryChest.setItem(k, itemstack);
                return true;
            } else {
                return false;
            }
        }
    }

    @Nullable
    public Entity bw() {
        return this.bx().isEmpty() ? null : (Entity) this.bx().get(0);
    }

    public EnumMonsterType getMonsterType() {
        return this.getType().h() ? EnumMonsterType.UNDEAD : EnumMonsterType.UNDEFINED;
    }

    @Nullable
    protected MinecraftKey J() {
        return this.getType().l();
    }

    public static class a implements GroupDataEntity {

        public EnumHorseType a;
        public int b;

        public a(EnumHorseType enumhorsetype, int i) {
            this.a = enumhorsetype;
            this.b = i;
        }
    }
}
