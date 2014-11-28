package net.minecraft.server;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public abstract class EntityHuman extends EntityLiving {

    public PlayerInventory inventory = new PlayerInventory(this);
    private InventoryEnderChest enderChest = new InventoryEnderChest();
    public Container defaultContainer;
    public Container activeContainer;
    protected FoodMetaData foodData = new FoodMetaData();
    protected int bk;
    public float bl;
    public float bm;
    public int bn;
    public double bo;
    public double bp;
    public double bq;
    public double br;
    public double bs;
    public double bt;
    public boolean sleeping;
    public BlockPosition bv;
    public int sleepTicks;
    public float bw;
    public float bx;
    private BlockPosition c;
    private boolean d;
    private BlockPosition e;
    public PlayerAbilities abilities = new PlayerAbilities();
    public int expLevel;
    public int expTotal;
    public float exp;
    private int f;
    private ItemStack g;
    private int h;
    protected float bC = 0.1F;
    protected float bD = 0.02F;
    private int i;
    private final GameProfile bF;
    private boolean bG = false;
    public EntityFishingHook hookedFish;

    public EntityHuman(World world, GameProfile gameprofile) {
        super(world);
        this.uniqueID = a(gameprofile);
        this.bF = gameprofile;
        this.defaultContainer = new ContainerPlayer(this.inventory, !world.isStatic, this);
        this.activeContainer = this.defaultContainer;
        BlockPosition blockposition = world.getSpawn();

        this.setPositionRotation((double) blockposition.getX() + 0.5D, (double) (blockposition.getY() + 1), (double) blockposition.getZ() + 0.5D, 0.0F, 0.0F);
        this.aT = 180.0F;
        this.maxFireTicks = 20;
    }

    protected void aW() {
        super.aW();
        this.getAttributeMap().b(GenericAttributes.e).setValue(1.0D);
        this.getAttributeInstance(GenericAttributes.d).setValue(0.10000000149011612D);
    }

    protected void h() {
        super.h();
        this.datawatcher.a(16, Byte.valueOf((byte) 0));
        this.datawatcher.a(17, Float.valueOf(0.0F));
        this.datawatcher.a(18, Integer.valueOf(0));
        this.datawatcher.a(10, Byte.valueOf((byte) 0));
    }

    public boolean bR() {
        return this.g != null;
    }

    public void bT() {
        if (this.g != null) {
            this.g.b(this.world, this, this.h);
        }

        this.bU();
    }

    public void bU() {
        this.g = null;
        this.h = 0;
        if (!this.world.isStatic) {
            this.f(false);
        }

    }

    public boolean isBlocking() {
        return this.bR() && this.g.getItem().e(this.g) == EnumAnimation.BLOCK;
    }

    public void s_() {
        this.T = this.v();
        if (this.v()) {
            this.onGround = false;
        }

        if (this.g != null) {
            ItemStack itemstack = this.inventory.getItemInHand();

            if (itemstack == this.g) {
                if (this.h <= 25 && this.h % 4 == 0) {
                    this.b(itemstack, 5);
                }

                if (--this.h == 0 && !this.world.isStatic) {
                    this.s();
                }
            } else {
                this.bU();
            }
        }

        if (this.bn > 0) {
            --this.bn;
        }

        if (this.isSleeping()) {
            ++this.sleepTicks;
            if (this.sleepTicks > 100) {
                this.sleepTicks = 100;
            }

            if (!this.world.isStatic) {
                if (!this.p()) {
                    this.a(true, true, false);
                } else if (this.world.w()) {
                    this.a(false, true, true);
                }
            }
        } else if (this.sleepTicks > 0) {
            ++this.sleepTicks;
            if (this.sleepTicks >= 110) {
                this.sleepTicks = 0;
            }
        }

        super.s_();
        if (!this.world.isStatic && this.activeContainer != null && !this.activeContainer.a(this)) {
            this.closeInventory();
            this.activeContainer = this.defaultContainer;
        }

        if (this.isBurning() && this.abilities.isInvulnerable) {
            this.extinguish();
        }

        this.bo = this.br;
        this.bp = this.bs;
        this.bq = this.bt;
        double d0 = this.locX - this.br;
        double d1 = this.locY - this.bs;
        double d2 = this.locZ - this.bt;
        double d3 = 10.0D;

        if (d0 > d3) {
            this.bo = this.br = this.locX;
        }

        if (d2 > d3) {
            this.bq = this.bt = this.locZ;
        }

        if (d1 > d3) {
            this.bp = this.bs = this.locY;
        }

        if (d0 < -d3) {
            this.bo = this.br = this.locX;
        }

        if (d2 < -d3) {
            this.bq = this.bt = this.locZ;
        }

        if (d1 < -d3) {
            this.bp = this.bs = this.locY;
        }

        this.br += d0 * 0.25D;
        this.bt += d2 * 0.25D;
        this.bs += d1 * 0.25D;
        if (this.vehicle == null) {
            this.e = null;
        }

        if (!this.world.isStatic) {
            this.foodData.a(this);
            this.b(StatisticList.g);
            if (this.isAlive()) {
                this.b(StatisticList.h);
            }
        }

        int i = 29999999;
        double d4 = MathHelper.a(this.locX, -2.9999999E7D, 2.9999999E7D);
        double d5 = MathHelper.a(this.locZ, -2.9999999E7D, 2.9999999E7D);

        if (d4 != this.locX || d5 != this.locZ) {
            this.setPosition(d4, this.locY, d5);
        }

    }

    public int L() {
        return this.abilities.isInvulnerable ? 0 : 80;
    }

    protected String P() {
        return "game.player.swim";
    }

    protected String aa() {
        return "game.player.swim.splash";
    }

    public int ar() {
        return 10;
    }

    public void makeSound(String s, float f, float f1) {
        this.world.a(this, s, f, f1);
    }

    protected void b(ItemStack itemstack, int i) {
        if (itemstack.m() == EnumAnimation.DRINK) {
            this.makeSound("random.drink", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
        }

        if (itemstack.m() == EnumAnimation.EAT) {
            for (int j = 0; j < i; ++j) {
                Vec3D vec3d = new Vec3D(((double) this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);

                vec3d = vec3d.a(-this.pitch * 3.1415927F / 180.0F);
                vec3d = vec3d.b(-this.yaw * 3.1415927F / 180.0F);
                double d0 = (double) (-this.random.nextFloat()) * 0.6D - 0.3D;
                Vec3D vec3d1 = new Vec3D(((double) this.random.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);

                vec3d1 = vec3d1.a(-this.pitch * 3.1415927F / 180.0F);
                vec3d1 = vec3d1.b(-this.yaw * 3.1415927F / 180.0F);
                vec3d1 = vec3d1.add(this.locX, this.locY + (double) this.getHeadHeight(), this.locZ);
                if (itemstack.usesData()) {
                    this.world.addParticle(EnumParticle.ITEM_CRACK, vec3d1.a, vec3d1.b, vec3d1.c, vec3d.a, vec3d.b + 0.05D, vec3d.c, new int[] { Item.getId(itemstack.getItem()), itemstack.getData()});
                } else {
                    this.world.addParticle(EnumParticle.ITEM_CRACK, vec3d1.a, vec3d1.b, vec3d1.c, vec3d.a, vec3d.b + 0.05D, vec3d.c, new int[] { Item.getId(itemstack.getItem())});
                }
            }

            this.makeSound("random.eat", 0.5F + 0.5F * (float) this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        }

    }

    protected void s() {
        if (this.g != null) {
            this.b(this.g, 16);
            int i = this.g.count;
            ItemStack itemstack = this.g.b(this.world, this);

            if (itemstack != this.g || itemstack != null && itemstack.count != i) {
                this.inventory.items[this.inventory.itemInHandIndex] = itemstack;
                if (itemstack.count == 0) {
                    this.inventory.items[this.inventory.itemInHandIndex] = null;
                }
            }

            this.bU();
        }

    }

    protected boolean bC() {
        return this.getHealth() <= 0.0F || this.isSleeping();
    }

    public void closeInventory() {
        this.activeContainer = this.defaultContainer;
    }

    public void ak() {
        if (!this.world.isStatic && this.isSneaking()) {
            this.mount((Entity) null);
            this.setSneaking(false);
        } else {
            double d0 = this.locX;
            double d1 = this.locY;
            double d2 = this.locZ;
            float f = this.yaw;
            float f1 = this.pitch;

            super.ak();
            this.bl = this.bm;
            this.bm = 0.0F;
            this.l(this.locX - d0, this.locY - d1, this.locZ - d2);
            if (this.vehicle instanceof EntityPig) {
                this.pitch = f1;
                this.yaw = f;
                this.aG = ((EntityPig) this.vehicle).aG;
            }

        }
    }

    protected void doTick() {
        super.doTick();
        this.bw();
        this.aI = this.yaw;
    }

    public void m() {
        if (this.bk > 0) {
            --this.bk;
        }

        if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL && this.world.getGameRules().getBoolean("naturalRegeneration")) {
            if (this.getHealth() < this.getMaxHealth() && this.ticksLived % 20 == 0) {
                this.heal(1.0F);
            }

            if (this.foodData.c() && this.ticksLived % 10 == 0) {
                this.foodData.a(this.foodData.getFoodLevel() + 1);
            }
        }

        this.inventory.k();
        this.bl = this.bm;
        super.m();
        AttributeInstance attributeinstance = this.getAttributeInstance(GenericAttributes.d);

        if (!this.world.isStatic) {
            attributeinstance.setValue((double) this.abilities.b());
        }

        this.aK = this.bD;
        if (this.isSprinting()) {
            this.aK = (float) ((double) this.aK + (double) this.bD * 0.3D);
        }

        this.j((float) attributeinstance.getValue());
        float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
        float f1 = (float) (Math.atan(-this.motY * 0.20000000298023224D) * 15.0D);

        if (f > 0.1F) {
            f = 0.1F;
        }

        if (!this.onGround || this.getHealth() <= 0.0F) {
            f = 0.0F;
        }

        if (this.onGround || this.getHealth() <= 0.0F) {
            f1 = 0.0F;
        }

        this.bm += (f - this.bm) * 0.4F;
        this.aD += (f1 - this.aD) * 0.8F;
        if (this.getHealth() > 0.0F && !this.v()) {
            AxisAlignedBB axisalignedbb = null;

            if (this.vehicle != null && !this.vehicle.dead) {
                axisalignedbb = this.getBoundingBox().a(this.vehicle.getBoundingBox()).grow(1.0D, 0.0D, 1.0D);
            } else {
                axisalignedbb = this.getBoundingBox().grow(1.0D, 0.5D, 1.0D);
            }

            List list = this.world.getEntities(this, axisalignedbb);

            for (int i = 0; i < list.size(); ++i) {
                Entity entity = (Entity) list.get(i);

                if (!entity.dead) {
                    this.d(entity);
                }
            }
        }

    }

    private void d(Entity entity) {
        entity.d(this);
    }

    public int getScore() {
        return this.datawatcher.getInt(18);
    }

    public void setScore(int i) {
        this.datawatcher.watch(18, Integer.valueOf(i));
    }

    public void addScore(int i) {
        int j = this.getScore();

        this.datawatcher.watch(18, Integer.valueOf(j + i));
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        this.a(0.2F, 0.2F);
        this.setPosition(this.locX, this.locY, this.locZ);
        this.motY = 0.10000000149011612D;
        if (this.getName().equals("Notch")) {
            this.a(new ItemStack(Items.APPLE, 1), true, false);
        }

        if (!this.world.getGameRules().getBoolean("keepInventory")) {
            this.inventory.n();
        }

        if (damagesource != null) {
            this.motX = (double) (-MathHelper.cos((this.au + this.yaw) * 3.1415927F / 180.0F) * 0.1F);
            this.motZ = (double) (-MathHelper.sin((this.au + this.yaw) * 3.1415927F / 180.0F) * 0.1F);
        } else {
            this.motX = this.motZ = 0.0D;
        }

        this.b(StatisticList.y);
        this.a(StatisticList.h);
    }

    protected String bn() {
        return "game.player.hurt";
    }

    protected String bo() {
        return "game.player.die";
    }

    public void b(Entity entity, int i) {
        this.addScore(i);
        Collection collection = this.getScoreboard().getObjectivesForCriteria(IScoreboardCriteria.f);

        if (entity instanceof EntityHuman) {
            this.b(StatisticList.B);
            collection.addAll(this.getScoreboard().getObjectivesForCriteria(IScoreboardCriteria.e));
            collection.addAll(this.e(entity));
        } else {
            this.b(StatisticList.z);
        }

        Iterator iterator = collection.iterator();

        while (iterator.hasNext()) {
            ScoreboardObjective scoreboardobjective = (ScoreboardObjective) iterator.next();
            ScoreboardScore scoreboardscore = this.getScoreboard().getPlayerScoreForObjective(this.getName(), scoreboardobjective);

            scoreboardscore.incrementScore();
        }

    }

    private Collection e(Entity entity) {
        ScoreboardTeam scoreboardteam = this.getScoreboard().getPlayerTeam(this.getName());

        if (scoreboardteam != null) {
            int i = scoreboardteam.l().b();

            if (i >= 0 && i < IScoreboardCriteria.i.length) {
                Iterator iterator = this.getScoreboard().getObjectivesForCriteria(IScoreboardCriteria.i[i]).iterator();

                while (iterator.hasNext()) {
                    ScoreboardObjective scoreboardobjective = (ScoreboardObjective) iterator.next();
                    ScoreboardScore scoreboardscore = this.getScoreboard().getPlayerScoreForObjective(entity.getName(), scoreboardobjective);

                    scoreboardscore.incrementScore();
                }
            }
        }

        ScoreboardTeam scoreboardteam1 = this.getScoreboard().getPlayerTeam(entity.getName());

        if (scoreboardteam1 != null) {
            int j = scoreboardteam1.l().b();

            if (j >= 0 && j < IScoreboardCriteria.h.length) {
                return this.getScoreboard().getObjectivesForCriteria(IScoreboardCriteria.h[j]);
            }
        }

        return Lists.newArrayList();
    }

    public EntityItem a(boolean flag) {
        return this.a(this.inventory.splitStack(this.inventory.itemInHandIndex, flag && this.inventory.getItemInHand() != null ? this.inventory.getItemInHand().count : 1), false, true);
    }

    public EntityItem drop(ItemStack itemstack, boolean flag) {
        return this.a(itemstack, false, false);
    }

    public EntityItem a(ItemStack itemstack, boolean flag, boolean flag1) {
        if (itemstack == null) {
            return null;
        } else if (itemstack.count == 0) {
            return null;
        } else {
            double d0 = this.locY - 0.30000001192092896D + (double) this.getHeadHeight();
            EntityItem entityitem = new EntityItem(this.world, this.locX, d0, this.locZ, itemstack);

            entityitem.a(40);
            if (flag1) {
                entityitem.c(this.getName());
            }

            float f;
            float f1;

            if (flag) {
                f = this.random.nextFloat() * 0.5F;
                f1 = this.random.nextFloat() * 3.1415927F * 2.0F;
                entityitem.motX = (double) (-MathHelper.sin(f1) * f);
                entityitem.motZ = (double) (MathHelper.cos(f1) * f);
                entityitem.motY = 0.20000000298023224D;
            } else {
                f = 0.3F;
                entityitem.motX = (double) (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
                entityitem.motZ = (double) (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
                entityitem.motY = (double) (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F) * f + 0.1F);
                f1 = this.random.nextFloat() * 3.1415927F * 2.0F;
                f = 0.02F * this.random.nextFloat();
                entityitem.motX += Math.cos((double) f1) * (double) f;
                entityitem.motY += (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
                entityitem.motZ += Math.sin((double) f1) * (double) f;
            }

            this.a(entityitem);
            if (flag1) {
                this.b(StatisticList.v);
            }

            return entityitem;
        }
    }

    protected void a(EntityItem entityitem) {
        this.world.addEntity(entityitem);
    }

    public float a(Block block) {
        float f = this.inventory.a(block);

        if (f > 1.0F) {
            int i = EnchantmentManager.getDigSpeedEnchantmentLevel(this);
            ItemStack itemstack = this.inventory.getItemInHand();

            if (i > 0 && itemstack != null) {
                f += (float) (i * i + 1);
            }
        }

        if (this.hasEffect(MobEffectList.FASTER_DIG)) {
            f *= 1.0F + (float) (this.getEffect(MobEffectList.FASTER_DIG).getAmplifier() + 1) * 0.2F;
        }

        if (this.hasEffect(MobEffectList.SLOWER_DIG)) {
            float f1 = 1.0F;

            switch (this.getEffect(MobEffectList.SLOWER_DIG).getAmplifier()) {
            case 0:
                f1 = 0.3F;
                break;

            case 1:
                f1 = 0.09F;
                break;

            case 2:
                f1 = 0.0027F;
                break;

            case 3:
            default:
                f1 = 8.1E-4F;
            }

            f *= f1;
        }

        if (this.a(Material.WATER) && !EnchantmentManager.j(this)) {
            f /= 5.0F;
        }

        if (!this.onGround) {
            f /= 5.0F;
        }

        return f;
    }

    public boolean b(Block block) {
        return this.inventory.b(block);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.uniqueID = a(this.bF);
        NBTTagList nbttaglist = nbttagcompound.getList("Inventory", 10);

        this.inventory.b(nbttaglist);
        this.inventory.itemInHandIndex = nbttagcompound.getInt("SelectedItemSlot");
        this.sleeping = nbttagcompound.getBoolean("Sleeping");
        this.sleepTicks = nbttagcompound.getShort("SleepTimer");
        this.exp = nbttagcompound.getFloat("XpP");
        this.expLevel = nbttagcompound.getInt("XpLevel");
        this.expTotal = nbttagcompound.getInt("XpTotal");
        this.f = nbttagcompound.getInt("XpSeed");
        if (this.f == 0) {
            this.f = this.random.nextInt();
        }

        this.setScore(nbttagcompound.getInt("Score"));
        if (this.sleeping) {
            this.bv = new BlockPosition(this);
            this.a(true, true, false);
        }

        if (nbttagcompound.hasKeyOfType("SpawnX", 99) && nbttagcompound.hasKeyOfType("SpawnY", 99) && nbttagcompound.hasKeyOfType("SpawnZ", 99)) {
            this.c = new BlockPosition(nbttagcompound.getInt("SpawnX"), nbttagcompound.getInt("SpawnY"), nbttagcompound.getInt("SpawnZ"));
            this.d = nbttagcompound.getBoolean("SpawnForced");
        }

        this.foodData.a(nbttagcompound);
        this.abilities.b(nbttagcompound);
        if (nbttagcompound.hasKeyOfType("EnderItems", 9)) {
            NBTTagList nbttaglist1 = nbttagcompound.getList("EnderItems", 10);

            this.enderChest.a(nbttaglist1);
        }

    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.set("Inventory", this.inventory.a(new NBTTagList()));
        nbttagcompound.setInt("SelectedItemSlot", this.inventory.itemInHandIndex);
        nbttagcompound.setBoolean("Sleeping", this.sleeping);
        nbttagcompound.setShort("SleepTimer", (short) this.sleepTicks);
        nbttagcompound.setFloat("XpP", this.exp);
        nbttagcompound.setInt("XpLevel", this.expLevel);
        nbttagcompound.setInt("XpTotal", this.expTotal);
        nbttagcompound.setInt("XpSeed", this.f);
        nbttagcompound.setInt("Score", this.getScore());
        if (this.c != null) {
            nbttagcompound.setInt("SpawnX", this.c.getX());
            nbttagcompound.setInt("SpawnY", this.c.getY());
            nbttagcompound.setInt("SpawnZ", this.c.getZ());
            nbttagcompound.setBoolean("SpawnForced", this.d);
        }

        this.foodData.b(nbttagcompound);
        this.abilities.a(nbttagcompound);
        nbttagcompound.set("EnderItems", this.enderChest.h());
        ItemStack itemstack = this.inventory.getItemInHand();

        if (itemstack != null && itemstack.getItem() != null) {
            nbttagcompound.set("SelectedItem", itemstack.save(new NBTTagCompound()));
        }

    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        if (this.isInvulnerable(damagesource)) {
            return false;
        } else if (this.abilities.isInvulnerable && !damagesource.ignoresInvulnerability()) {
            return false;
        } else {
            this.aO = 0;
            if (this.getHealth() <= 0.0F) {
                return false;
            } else {
                if (this.isSleeping() && !this.world.isStatic) {
                    this.a(true, true, false);
                }

                if (damagesource.r()) {
                    if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
                        f = 0.0F;
                    }

                    if (this.world.getDifficulty() == EnumDifficulty.EASY) {
                        f = f / 2.0F + 1.0F;
                    }

                    if (this.world.getDifficulty() == EnumDifficulty.HARD) {
                        f = f * 3.0F / 2.0F;
                    }
                }

                if (f == 0.0F) {
                    return false;
                } else {
                    Entity entity = damagesource.getEntity();

                    if (entity instanceof EntityArrow && ((EntityArrow) entity).shooter != null) {
                        entity = ((EntityArrow) entity).shooter;
                    }

                    return super.damageEntity(damagesource, f);
                }
            }
        }
    }

    public boolean a(EntityHuman entityhuman) {
        ScoreboardTeamBase scoreboardteambase = this.getScoreboardTeam();
        ScoreboardTeamBase scoreboardteambase1 = entityhuman.getScoreboardTeam();

        return scoreboardteambase == null ? true : (!scoreboardteambase.isAlly(scoreboardteambase1) ? true : scoreboardteambase.allowFriendlyFire());
    }

    protected void damageArmor(float f) {
        this.inventory.a(f);
    }

    public int bq() {
        return this.inventory.m();
    }

    public float bX() {
        int i = 0;
        ItemStack[] aitemstack = this.inventory.armor;
        int j = aitemstack.length;

        for (int k = 0; k < j; ++k) {
            ItemStack itemstack = aitemstack[k];

            if (itemstack != null) {
                ++i;
            }
        }

        return (float) i / (float) this.inventory.armor.length;
    }

    protected void d(DamageSource damagesource, float f) {
        if (!this.isInvulnerable(damagesource)) {
            if (!damagesource.ignoresArmor() && this.isBlocking() && f > 0.0F) {
                f = (1.0F + f) * 0.5F;
            }

            f = this.applyArmorModifier(damagesource, f);
            f = this.applyMagicModifier(damagesource, f);
            float f1 = f;

            f = Math.max(f - this.getAbsorptionHearts(), 0.0F);
            this.setAbsorptionHearts(this.getAbsorptionHearts() - (f1 - f));
            if (f != 0.0F) {
                this.applyExhaustion(damagesource.getExhaustionCost());
                float f2 = this.getHealth();

                this.setHealth(this.getHealth() - f);
                this.br().a(damagesource, f2, f);
                if (f < 3.4028235E37F) {
                    this.a(StatisticList.x, Math.round(f * 10.0F));
                }

            }
        }
    }

    public void openSign(TileEntitySign tileentitysign) {}

    public void a(CommandBlockListenerAbstract commandblocklistenerabstract) {}

    public void openTrade(IMerchant imerchant) {}

    public void openContainer(IInventory iinventory) {}

    public void openHorseInventory(EntityHorse entityhorse, IInventory iinventory) {}

    public void openTileEntity(ITileEntityContainer itileentitycontainer) {}

    public void openBook(ItemStack itemstack) {}

    public boolean u(Entity entity) {
        if (this.v()) {
            if (entity instanceof IInventory) {
                this.openContainer((IInventory) entity);
            }

            return false;
        } else {
            ItemStack itemstack = this.bY();
            ItemStack itemstack1 = itemstack != null ? itemstack.cloneItemStack() : null;

            if (!entity.e(this)) {
                if (itemstack != null && entity instanceof EntityLiving) {
                    if (this.abilities.canInstantlyBuild) {
                        itemstack = itemstack1;
                    }

                    if (itemstack.a(this, (EntityLiving) entity)) {
                        if (itemstack.count <= 0 && !this.abilities.canInstantlyBuild) {
                            this.bZ();
                        }

                        return true;
                    }
                }

                return false;
            } else {
                if (itemstack != null && itemstack == this.bY()) {
                    if (itemstack.count <= 0 && !this.abilities.canInstantlyBuild) {
                        this.bZ();
                    } else if (itemstack.count < itemstack1.count && this.abilities.canInstantlyBuild) {
                        itemstack.count = itemstack1.count;
                    }
                }

                return true;
            }
        }
    }

    public ItemStack bY() {
        return this.inventory.getItemInHand();
    }

    public void bZ() {
        this.inventory.setItem(this.inventory.itemInHandIndex, (ItemStack) null);
    }

    public double am() {
        return -0.35D;
    }

    public void attack(Entity entity) {
        if (entity.aE()) {
            if (!entity.l(this)) {
                float f = (float) this.getAttributeInstance(GenericAttributes.e).getValue();
                byte b0 = 0;
                float f1 = 0.0F;

                if (entity instanceof EntityLiving) {
                    f1 = EnchantmentManager.a(this.bz(), ((EntityLiving) entity).getMonsterType());
                } else {
                    f1 = EnchantmentManager.a(this.bz(), EnumMonsterType.UNDEFINED);
                }

                int i = b0 + EnchantmentManager.a((EntityLiving) this);

                if (this.isSprinting()) {
                    ++i;
                }

                if (f > 0.0F || f1 > 0.0F) {
                    boolean flag = this.fallDistance > 0.0F && !this.onGround && !this.j_() && !this.V() && !this.hasEffect(MobEffectList.BLINDNESS) && this.vehicle == null && entity instanceof EntityLiving;

                    if (flag && f > 0.0F) {
                        f *= 1.5F;
                    }

                    f += f1;
                    boolean flag1 = false;
                    int j = EnchantmentManager.getFireAspectEnchantmentLevel(this);

                    if (entity instanceof EntityLiving && j > 0 && !entity.isBurning()) {
                        flag1 = true;
                        entity.setOnFire(1);
                    }

                    double d0 = entity.motX;
                    double d1 = entity.motY;
                    double d2 = entity.motZ;
                    boolean flag2 = entity.damageEntity(DamageSource.playerAttack(this), f);

                    if (flag2) {
                        if (i > 0) {
                            entity.g((double) (-MathHelper.sin(this.yaw * 3.1415927F / 180.0F) * (float) i * 0.5F), 0.1D, (double) (MathHelper.cos(this.yaw * 3.1415927F / 180.0F) * (float) i * 0.5F));
                            this.motX *= 0.6D;
                            this.motZ *= 0.6D;
                            this.setSprinting(false);
                        }

                        if (entity instanceof EntityPlayer && entity.velocityChanged) {
                            ((EntityPlayer) entity).playerConnection.sendPacket(new PacketPlayOutEntityVelocity(entity));
                            entity.velocityChanged = false;
                            entity.motX = d0;
                            entity.motY = d1;
                            entity.motZ = d2;
                        }

                        if (flag) {
                            this.b(entity);
                        }

                        if (f1 > 0.0F) {
                            this.c(entity);
                        }

                        if (f >= 18.0F) {
                            this.b((Statistic) AchievementList.F);
                        }

                        this.p(entity);
                        if (entity instanceof EntityLiving) {
                            EnchantmentManager.a((EntityLiving) entity, (Entity) this);
                        }

                        EnchantmentManager.b(this, entity);
                        ItemStack itemstack = this.bY();
                        Object object = entity;

                        if (entity instanceof EntityComplexPart) {
                            IComplex icomplex = ((EntityComplexPart) entity).owner;

                            if (icomplex instanceof EntityLiving) {
                                object = (EntityLiving) icomplex;
                            }
                        }

                        if (itemstack != null && object instanceof EntityLiving) {
                            itemstack.a((EntityLiving) object, this);
                            if (itemstack.count <= 0) {
                                this.bZ();
                            }
                        }

                        if (entity instanceof EntityLiving) {
                            this.a(StatisticList.w, Math.round(f * 10.0F));
                            if (j > 0) {
                                entity.setOnFire(j * 4);
                            }
                        }

                        this.applyExhaustion(0.3F);
                    } else if (flag1) {
                        entity.extinguish();
                    }
                }

            }
        }
    }

    public void b(Entity entity) {}

    public void c(Entity entity) {}

    public void die() {
        super.die();
        this.defaultContainer.b(this);
        if (this.activeContainer != null) {
            this.activeContainer.b(this);
        }

    }

    public boolean inBlock() {
        return !this.sleeping && super.inBlock();
    }

    public GameProfile getProfile() {
        return this.bF;
    }

    public EnumBedResult a(BlockPosition blockposition) {
        if (!this.world.isStatic) {
            if (this.isSleeping() || !this.isAlive()) {
                return EnumBedResult.OTHER_PROBLEM;
            }

            if (!this.world.worldProvider.d()) {
                return EnumBedResult.NOT_POSSIBLE_HERE;
            }

            if (this.world.w()) {
                return EnumBedResult.NOT_POSSIBLE_NOW;
            }

            if (Math.abs(this.locX - (double) blockposition.getX()) > 3.0D || Math.abs(this.locY - (double) blockposition.getY()) > 2.0D || Math.abs(this.locZ - (double) blockposition.getZ()) > 3.0D) {
                return EnumBedResult.TOO_FAR_AWAY;
            }

            double d0 = 8.0D;
            double d1 = 5.0D;
            List list = this.world.a(EntityMonster.class, new AxisAlignedBB((double) blockposition.getX() - d0, (double) blockposition.getY() - d1, (double) blockposition.getZ() - d0, (double) blockposition.getX() + d0, (double) blockposition.getY() + d1, (double) blockposition.getZ() + d0));

            if (!list.isEmpty()) {
                return EnumBedResult.NOT_SAFE;
            }
        }

        if (this.av()) {
            this.mount((Entity) null);
        }

        this.a(0.2F, 0.2F);
        if (this.world.isLoaded(blockposition)) {
            EnumDirection enumdirection = (EnumDirection) this.world.getType(blockposition).get(BlockDirectional.FACING);
            float f = 0.5F;
            float f1 = 0.5F;

            switch (SwitchHelperEntityHuman.a[enumdirection.ordinal()]) {
            case 1:
                f1 = 0.9F;
                break;

            case 2:
                f1 = 0.1F;
                break;

            case 3:
                f = 0.1F;
                break;

            case 4:
                f = 0.9F;
            }

            this.a(enumdirection);
            this.setPosition((double) ((float) blockposition.getX() + f), (double) ((float) blockposition.getY() + 0.6875F), (double) ((float) blockposition.getZ() + f1));
        } else {
            this.setPosition((double) ((float) blockposition.getX() + 0.5F), (double) ((float) blockposition.getY() + 0.6875F), (double) ((float) blockposition.getZ() + 0.5F));
        }

        this.sleeping = true;
        this.sleepTicks = 0;
        this.bv = blockposition;
        this.motX = this.motZ = this.motY = 0.0D;
        if (!this.world.isStatic) {
            this.world.everyoneSleeping();
        }

        return EnumBedResult.OK;
    }

    private void a(EnumDirection enumdirection) {
        this.bw = 0.0F;
        this.bx = 0.0F;
        switch (SwitchHelperEntityHuman.a[enumdirection.ordinal()]) {
        case 1:
            this.bx = -1.8F;
            break;

        case 2:
            this.bx = 1.8F;
            break;

        case 3:
            this.bw = 1.8F;
            break;

        case 4:
            this.bw = -1.8F;
        }

    }

    public void a(boolean flag, boolean flag1, boolean flag2) {
        this.a(0.6F, 1.8F);
        IBlockData iblockdata = this.world.getType(this.bv);

        if (this.bv != null && iblockdata.getBlock() == Blocks.BED) {
            this.world.setTypeAndData(this.bv, iblockdata.set(BlockBed.OCCUPIED, Boolean.valueOf(false)), 4);
            BlockPosition blockposition = BlockBed.a(this.world, this.bv, 0);

            if (blockposition == null) {
                blockposition = this.bv.up();
            }

            this.setPosition((double) ((float) blockposition.getX() + 0.5F), (double) ((float) blockposition.getY() + 0.1F), (double) ((float) blockposition.getZ() + 0.5F));
        }

        this.sleeping = false;
        if (!this.world.isStatic && flag1) {
            this.world.everyoneSleeping();
        }

        this.sleepTicks = flag ? 0 : 100;
        if (flag2) {
            this.setRespawnPosition(this.bv, false);
        }

    }

    private boolean p() {
        return this.world.getType(this.bv).getBlock() == Blocks.BED;
    }

    public static BlockPosition getBed(World world, BlockPosition blockposition, boolean flag) {
        if (world.getType(blockposition).getBlock() != Blocks.BED) {
            if (!flag) {
                return null;
            } else {
                Material material = world.getType(blockposition).getBlock().getMaterial();
                Material material1 = world.getType(blockposition.up()).getBlock().getMaterial();
                boolean flag1 = !material.isBuildable() && !material.isLiquid();
                boolean flag2 = !material1.isBuildable() && !material1.isLiquid();

                return flag1 && flag2 ? blockposition : null;
            }
        } else {
            return BlockBed.a(world, blockposition, 0);
        }
    }

    public boolean isSleeping() {
        return this.sleeping;
    }

    public boolean isDeeplySleeping() {
        return this.sleeping && this.sleepTicks >= 100;
    }

    public void b(IChatBaseComponent ichatbasecomponent) {}

    public BlockPosition getBed() {
        return this.c;
    }

    public boolean isRespawnForced() {
        return this.d;
    }

    public void setRespawnPosition(BlockPosition blockposition, boolean flag) {
        if (blockposition != null) {
            this.c = blockposition;
            this.d = flag;
        } else {
            this.c = null;
            this.d = false;
        }

    }

    public void b(Statistic statistic) {
        this.a(statistic, 1);
    }

    public void a(Statistic statistic, int i) {}

    public void a(Statistic statistic) {}

    public void bE() {
        super.bE();
        this.b(StatisticList.u);
        if (this.isSprinting()) {
            this.applyExhaustion(0.8F);
        } else {
            this.applyExhaustion(0.2F);
        }

    }

    public void g(float f, float f1) {
        double d0 = this.locX;
        double d1 = this.locY;
        double d2 = this.locZ;

        if (this.abilities.isFlying && this.vehicle == null) {
            double d3 = this.motY;
            float f2 = this.aK;

            this.aK = this.abilities.a() * (float) (this.isSprinting() ? 2 : 1);
            super.g(f, f1);
            this.motY = d3 * 0.6D;
            this.aK = f2;
        } else {
            super.g(f, f1);
        }

        this.checkMovement(this.locX - d0, this.locY - d1, this.locZ - d2);
    }

    public float bH() {
        return (float) this.getAttributeInstance(GenericAttributes.d).getValue();
    }

    public void checkMovement(double d0, double d1, double d2) {
        if (this.vehicle == null) {
            int i;

            if (this.a(Material.WATER)) {
                i = Math.round(MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2) * 100.0F);
                if (i > 0) {
                    this.a(StatisticList.p, i);
                    this.applyExhaustion(0.015F * (float) i * 0.01F);
                }
            } else if (this.V()) {
                i = Math.round(MathHelper.sqrt(d0 * d0 + d2 * d2) * 100.0F);
                if (i > 0) {
                    this.a(StatisticList.l, i);
                    this.applyExhaustion(0.015F * (float) i * 0.01F);
                }
            } else if (this.j_()) {
                if (d1 > 0.0D) {
                    this.a(StatisticList.n, (int) Math.round(d1 * 100.0D));
                }
            } else if (this.onGround) {
                i = Math.round(MathHelper.sqrt(d0 * d0 + d2 * d2) * 100.0F);
                if (i > 0) {
                    this.a(StatisticList.i, i);
                    if (this.isSprinting()) {
                        this.a(StatisticList.k, i);
                        this.applyExhaustion(0.099999994F * (float) i * 0.01F);
                    } else {
                        if (this.isSneaking()) {
                            this.a(StatisticList.j, i);
                        }

                        this.applyExhaustion(0.01F * (float) i * 0.01F);
                    }
                }
            } else {
                i = Math.round(MathHelper.sqrt(d0 * d0 + d2 * d2) * 100.0F);
                if (i > 25) {
                    this.a(StatisticList.o, i);
                }
            }

        }
    }

    private void l(double d0, double d1, double d2) {
        if (this.vehicle != null) {
            int i = Math.round(MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2) * 100.0F);

            if (i > 0) {
                if (this.vehicle instanceof EntityMinecartAbstract) {
                    this.a(StatisticList.q, i);
                    if (this.e == null) {
                        this.e = new BlockPosition(this);
                    } else if (this.e.c((double) MathHelper.floor(this.locX), (double) MathHelper.floor(this.locY), (double) MathHelper.floor(this.locZ)) >= 1000000.0D) {
                        this.b((Statistic) AchievementList.q);
                    }
                } else if (this.vehicle instanceof EntityBoat) {
                    this.a(StatisticList.r, i);
                } else if (this.vehicle instanceof EntityPig) {
                    this.a(StatisticList.s, i);
                } else if (this.vehicle instanceof EntityHorse) {
                    this.a(StatisticList.t, i);
                }
            }
        }

    }

    public void e(float f, float f1) {
        if (!this.abilities.canFly) {
            if (f >= 2.0F) {
                this.a(StatisticList.m, (int) Math.round((double) f * 100.0D));
            }

            super.e(f, f1);
        }
    }

    protected void X() {
        if (!this.v()) {
            super.X();
        }

    }

    protected String n(int i) {
        return i > 4 ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small";
    }

    public void a(EntityLiving entityliving) {
        if (entityliving instanceof IMonster) {
            this.b((Statistic) AchievementList.s);
        }

        MonsterEggInfo monsteregginfo = (MonsterEggInfo) EntityTypes.eggInfo.get(Integer.valueOf(EntityTypes.a(entityliving)));

        if (monsteregginfo != null) {
            this.b(monsteregginfo.killEntityStatistic);
        }

    }

    public void aB() {
        if (!this.abilities.isFlying) {
            super.aB();
        }

    }

    public ItemStack q(int i) {
        return this.inventory.e(i);
    }

    public void giveExp(int i) {
        this.addScore(i);
        int j = Integer.MAX_VALUE - this.expTotal;

        if (i > j) {
            i = j;
        }

        this.exp += (float) i / (float) this.getExpToLevel();

        for (this.expTotal += i; this.exp >= 1.0F; this.exp /= (float) this.getExpToLevel()) {
            this.exp = (this.exp - 1.0F) * (float) this.getExpToLevel();
            this.levelDown(1);
        }

    }

    public int ci() {
        return this.f;
    }

    public void b(int i) {
        this.expLevel -= i;
        if (this.expLevel < 0) {
            this.expLevel = 0;
            this.exp = 0.0F;
            this.expTotal = 0;
        }

        this.f = this.random.nextInt();
    }

    public void levelDown(int i) {
        this.expLevel += i;
        if (this.expLevel < 0) {
            this.expLevel = 0;
            this.exp = 0.0F;
            this.expTotal = 0;
        }

        if (i > 0 && this.expLevel % 5 == 0 && (float) this.i < (float) this.ticksLived - 100.0F) {
            float f = this.expLevel > 30 ? 1.0F : (float) this.expLevel / 30.0F;

            this.world.makeSound(this, "random.levelup", f * 0.75F, 1.0F);
            this.i = this.ticksLived;
        }

    }

    public int getExpToLevel() {
        return this.expLevel >= 30 ? 112 + (this.expLevel - 30) * 9 : (this.expLevel >= 15 ? 37 + (this.expLevel - 15) * 5 : 7 + this.expLevel * 2);
    }

    public void applyExhaustion(float f) {
        if (!this.abilities.isInvulnerable) {
            if (!this.world.isStatic) {
                this.foodData.a(f);
            }

        }
    }

    public FoodMetaData getFoodData() {
        return this.foodData;
    }

    public boolean j(boolean flag) {
        return (flag || this.foodData.c()) && !this.abilities.isInvulnerable;
    }

    public boolean cl() {
        return this.getHealth() > 0.0F && this.getHealth() < this.getMaxHealth();
    }

    public void a(ItemStack itemstack, int i) {
        if (itemstack != this.g) {
            this.g = itemstack;
            this.h = i;
            if (!this.world.isStatic) {
                this.f(true);
            }

        }
    }

    public boolean cm() {
        return this.abilities.mayBuild;
    }

    public boolean a(BlockPosition blockposition, EnumDirection enumdirection, ItemStack itemstack) {
        if (this.abilities.mayBuild) {
            return true;
        } else if (itemstack == null) {
            return false;
        } else {
            BlockPosition blockposition1 = blockposition.shift(enumdirection.opposite());
            Block block = this.world.getType(blockposition1).getBlock();

            return itemstack.d(block) || itemstack.x();
        }
    }

    protected int getExpValue(EntityHuman entityhuman) {
        if (this.world.getGameRules().getBoolean("keepInventory")) {
            return 0;
        } else {
            int i = this.expLevel * 7;

            return i > 100 ? 100 : i;
        }
    }

    protected boolean alwaysGivesExp() {
        return true;
    }

    public void copyTo(EntityHuman entityhuman, boolean flag) {
        if (flag) {
            this.inventory.b(entityhuman.inventory);
            this.setHealth(entityhuman.getHealth());
            this.foodData = entityhuman.foodData;
            this.expLevel = entityhuman.expLevel;
            this.expTotal = entityhuman.expTotal;
            this.exp = entityhuman.exp;
            this.setScore(entityhuman.getScore());
            this.an = entityhuman.an;
        } else if (this.world.getGameRules().getBoolean("keepInventory")) {
            this.inventory.b(entityhuman.inventory);
            this.expLevel = entityhuman.expLevel;
            this.expTotal = entityhuman.expTotal;
            this.exp = entityhuman.exp;
            this.setScore(entityhuman.getScore());
        }

        this.enderChest = entityhuman.enderChest;
        this.getDataWatcher().watch(10, Byte.valueOf(entityhuman.getDataWatcher().getByte(10)));
    }

    protected boolean r_() {
        return !this.abilities.isFlying;
    }

    public void updateAbilities() {}

    public void a(EnumGamemode enumgamemode) {}

    public String getName() {
        return this.bF.getName();
    }

    public InventoryEnderChest getEnderChest() {
        return this.enderChest;
    }

    public ItemStack getEquipment(int i) {
        return i == 0 ? this.inventory.getItemInHand() : this.inventory.armor[i - 1];
    }

    public ItemStack bz() {
        return this.inventory.getItemInHand();
    }

    public void setEquipment(int i, ItemStack itemstack) {
        this.inventory.armor[i] = itemstack;
    }

    public abstract boolean v();

    public ItemStack[] getEquipment() {
        return this.inventory.armor;
    }

    public boolean aK() {
        return !this.abilities.isFlying;
    }

    public Scoreboard getScoreboard() {
        return this.world.getScoreboard();
    }

    public ScoreboardTeamBase getScoreboardTeam() {
        return this.getScoreboard().getPlayerTeam(this.getName());
    }

    public IChatBaseComponent getScoreboardDisplayName() {
        ChatComponentText chatcomponenttext = new ChatComponentText(ScoreboardTeam.getPlayerDisplayName(this.getScoreboardTeam(), this.getName()));

        chatcomponenttext.getChatModifier().setChatClickable(new ChatClickable(EnumClickAction.SUGGEST_COMMAND, "/msg " + this.getName() + " "));
        chatcomponenttext.getChatModifier().setChatHoverable(this.aP());
        chatcomponenttext.getChatModifier().setInsertion(this.getName());
        return chatcomponenttext;
    }

    public float getHeadHeight() {
        float f = 1.62F;

        if (this.isSleeping()) {
            f = 0.2F;
        }

        if (this.isSneaking()) {
            f -= 0.08F;
        }

        return f;
    }

    public void setAbsorptionHearts(float f) {
        if (f < 0.0F) {
            f = 0.0F;
        }

        this.getDataWatcher().watch(17, Float.valueOf(f));
    }

    public float getAbsorptionHearts() {
        return this.getDataWatcher().getFloat(17);
    }

    public static UUID a(GameProfile gameprofile) {
        UUID uuid = gameprofile.getId();

        if (uuid == null) {
            uuid = b(gameprofile.getName());
        }

        return uuid;
    }

    public static UUID b(String s) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + s).getBytes(Charsets.UTF_8));
    }

    public boolean a(ChestLock chestlock) {
        if (chestlock.a()) {
            return true;
        } else {
            ItemStack itemstack = this.bY();

            return itemstack != null && itemstack.hasName() ? itemstack.getName().equals(chestlock.b()) : false;
        }
    }

    public boolean getSendCommandFeedback() {
        return MinecraftServer.getServer().worldServer[0].getGameRules().getBoolean("sendCommandFeedback");
    }

    public boolean d(int i, ItemStack itemstack) {
        if (i >= 0 && i < this.inventory.items.length) {
            this.inventory.setItem(i, itemstack);
            return true;
        } else {
            int j = i - 100;
            int k;

            if (j >= 0 && j < this.inventory.armor.length) {
                k = j + 1;
                if (itemstack != null && itemstack.getItem() != null) {
                    if (itemstack.getItem() instanceof ItemArmor) {
                        if (EntityInsentient.c(itemstack) != k) {
                            return false;
                        }
                    } else if (k != 4 || itemstack.getItem() != Items.SKULL && !(itemstack.getItem() instanceof ItemBlock)) {
                        return false;
                    }
                }

                this.inventory.setItem(j + this.inventory.items.length, itemstack);
                return true;
            } else {
                k = i - 200;
                if (k >= 0 && k < this.enderChest.getSize()) {
                    this.enderChest.setItem(k, itemstack);
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
}
