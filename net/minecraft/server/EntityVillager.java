package net.minecraft.server;

import java.util.Iterator;
import java.util.Random;

public class EntityVillager extends EntityAgeable implements IMerchant, NPC {

    private static final DataWatcherObject<Integer> bw = DataWatcher.a(EntityVillager.class, DataWatcherRegistry.b);
    private int profession;
    private boolean by;
    private boolean bz;
    Village village;
    private EntityHuman tradingPlayer;
    private MerchantRecipeList trades;
    private int bC;
    private boolean bD;
    private boolean bE;
    public int riches;
    private String bG;
    private int bH;
    private int bI;
    private boolean bJ;
    private boolean bK;
    public InventorySubcontainer inventory;
    private static final EntityVillager.IMerchantRecipeOption[][][][] bM = new EntityVillager.IMerchantRecipeOption[][][][] { { { { new EntityVillager.MerchantRecipeOptionBuy(Items.WHEAT, new EntityVillager.MerchantOptionRandomRange(18, 22)), new EntityVillager.MerchantRecipeOptionBuy(Items.POTATO, new EntityVillager.MerchantOptionRandomRange(15, 19)), new EntityVillager.MerchantRecipeOptionBuy(Items.CARROT, new EntityVillager.MerchantOptionRandomRange(15, 19)), new EntityVillager.MerchantRecipeOptionSell(Items.BREAD, new EntityVillager.MerchantOptionRandomRange(-4, -2))}, { new EntityVillager.MerchantRecipeOptionBuy(Item.getItemOf(Blocks.PUMPKIN), new EntityVillager.MerchantOptionRandomRange(8, 13)), new EntityVillager.MerchantRecipeOptionSell(Items.PUMPKIN_PIE, new EntityVillager.MerchantOptionRandomRange(-3, -2))}, { new EntityVillager.MerchantRecipeOptionBuy(Item.getItemOf(Blocks.MELON_BLOCK), new EntityVillager.MerchantOptionRandomRange(7, 12)), new EntityVillager.MerchantRecipeOptionSell(Items.APPLE, new EntityVillager.MerchantOptionRandomRange(-5, -7))}, { new EntityVillager.MerchantRecipeOptionSell(Items.COOKIE, new EntityVillager.MerchantOptionRandomRange(-6, -10)), new EntityVillager.MerchantRecipeOptionSell(Items.CAKE, new EntityVillager.MerchantOptionRandomRange(1, 1))}}, { { new EntityVillager.MerchantRecipeOptionBuy(Items.STRING, new EntityVillager.MerchantOptionRandomRange(15, 20)), new EntityVillager.MerchantRecipeOptionBuy(Items.COAL, new EntityVillager.MerchantOptionRandomRange(16, 24)), new EntityVillager.MerchantRecipeOptionProcess(Items.FISH, new EntityVillager.MerchantOptionRandomRange(6, 6), Items.COOKED_FISH, new EntityVillager.MerchantOptionRandomRange(6, 6))}, { new EntityVillager.MerchantRecipeOptionEnchant(Items.FISHING_ROD, new EntityVillager.MerchantOptionRandomRange(7, 8))}}, { { new EntityVillager.MerchantRecipeOptionBuy(Item.getItemOf(Blocks.WOOL), new EntityVillager.MerchantOptionRandomRange(16, 22)), new EntityVillager.MerchantRecipeOptionSell(Items.SHEARS, new EntityVillager.MerchantOptionRandomRange(3, 4))}, { new EntityVillager.MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL)), new EntityVillager.MerchantOptionRandomRange(1, 2)), new EntityVillager.MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 1), new EntityVillager.MerchantOptionRandomRange(1, 2)), new EntityVillager.MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 2), new EntityVillager.MerchantOptionRandomRange(1, 2)), new EntityVillager.MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 3), new EntityVillager.MerchantOptionRandomRange(1, 2)), new EntityVillager.MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 4), new EntityVillager.MerchantOptionRandomRange(1, 2)), new EntityVillager.MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 5), new EntityVillager.MerchantOptionRandomRange(1, 2)), new EntityVillager.MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 6), new EntityVillager.MerchantOptionRandomRange(1, 2)), new EntityVillager.MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 7), new EntityVillager.MerchantOptionRandomRange(1, 2)), new EntityVillager.MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 8), new EntityVillager.MerchantOptionRandomRange(1, 2)), new EntityVillager.MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 9), new EntityVillager.MerchantOptionRandomRange(1, 2)), new EntityVillager.MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 10), new EntityVillager.MerchantOptionRandomRange(1, 2)), new EntityVillager.MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 11), new EntityVillager.MerchantOptionRandomRange(1, 2)), new EntityVillager.MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 12), new EntityVillager.MerchantOptionRandomRange(1, 2)), new EntityVillager.MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 13), new EntityVillager.MerchantOptionRandomRange(1, 2)), new EntityVillager.MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 14), new EntityVillager.MerchantOptionRandomRange(1, 2)), new EntityVillager.MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 15), new EntityVillager.MerchantOptionRandomRange(1, 2))}}, { { new EntityVillager.MerchantRecipeOptionBuy(Items.STRING, new EntityVillager.MerchantOptionRandomRange(15, 20)), new EntityVillager.MerchantRecipeOptionSell(Items.ARROW, new EntityVillager.MerchantOptionRandomRange(-12, -8))}, { new EntityVillager.MerchantRecipeOptionSell(Items.BOW, new EntityVillager.MerchantOptionRandomRange(2, 3)), new EntityVillager.MerchantRecipeOptionProcess(Item.getItemOf(Blocks.GRAVEL), new EntityVillager.MerchantOptionRandomRange(10, 10), Items.FLINT, new EntityVillager.MerchantOptionRandomRange(6, 10))}}}, { { { new EntityVillager.MerchantRecipeOptionBuy(Items.PAPER, new EntityVillager.MerchantOptionRandomRange(24, 36)), new EntityVillager.MerchantRecipeOptionBook()}, { new EntityVillager.MerchantRecipeOptionBuy(Items.BOOK, new EntityVillager.MerchantOptionRandomRange(8, 10)), new EntityVillager.MerchantRecipeOptionSell(Items.COMPASS, new EntityVillager.MerchantOptionRandomRange(10, 12)), new EntityVillager.MerchantRecipeOptionSell(Item.getItemOf(Blocks.BOOKSHELF), new EntityVillager.MerchantOptionRandomRange(3, 4))}, { new EntityVillager.MerchantRecipeOptionBuy(Items.WRITTEN_BOOK, new EntityVillager.MerchantOptionRandomRange(2, 2)), new EntityVillager.MerchantRecipeOptionSell(Items.CLOCK, new EntityVillager.MerchantOptionRandomRange(10, 12)), new EntityVillager.MerchantRecipeOptionSell(Item.getItemOf(Blocks.GLASS), new EntityVillager.MerchantOptionRandomRange(-5, -3))}, { new EntityVillager.MerchantRecipeOptionBook()}, { new EntityVillager.MerchantRecipeOptionBook()}, { new EntityVillager.MerchantRecipeOptionSell(Items.NAME_TAG, new EntityVillager.MerchantOptionRandomRange(20, 22))}}}, { { { new EntityVillager.MerchantRecipeOptionBuy(Items.ROTTEN_FLESH, new EntityVillager.MerchantOptionRandomRange(36, 40)), new EntityVillager.MerchantRecipeOptionBuy(Items.GOLD_INGOT, new EntityVillager.MerchantOptionRandomRange(8, 10))}, { new EntityVillager.MerchantRecipeOptionSell(Items.REDSTONE, new EntityVillager.MerchantOptionRandomRange(-4, -1)), new EntityVillager.MerchantRecipeOptionSell(new ItemStack(Items.DYE, 1, EnumColor.BLUE.getInvColorIndex()), new EntityVillager.MerchantOptionRandomRange(-2, -1))}, { new EntityVillager.MerchantRecipeOptionSell(Items.ENDER_PEARL, new EntityVillager.MerchantOptionRandomRange(4, 7)), new EntityVillager.MerchantRecipeOptionSell(Item.getItemOf(Blocks.GLOWSTONE), new EntityVillager.MerchantOptionRandomRange(-3, -1))}, { new EntityVillager.MerchantRecipeOptionSell(Items.EXPERIENCE_BOTTLE, new EntityVillager.MerchantOptionRandomRange(3, 11))}}}, { { { new EntityVillager.MerchantRecipeOptionBuy(Items.COAL, new EntityVillager.MerchantOptionRandomRange(16, 24)), new EntityVillager.MerchantRecipeOptionSell(Items.IRON_HELMET, new EntityVillager.MerchantOptionRandomRange(4, 6))}, { new EntityVillager.MerchantRecipeOptionBuy(Items.IRON_INGOT, new EntityVillager.MerchantOptionRandomRange(7, 9)), new EntityVillager.MerchantRecipeOptionSell(Items.IRON_CHESTPLATE, new EntityVillager.MerchantOptionRandomRange(10, 14))}, { new EntityVillager.MerchantRecipeOptionBuy(Items.DIAMOND, new EntityVillager.MerchantOptionRandomRange(3, 4)), new EntityVillager.MerchantRecipeOptionEnchant(Items.DIAMOND_CHESTPLATE, new EntityVillager.MerchantOptionRandomRange(16, 19))}, { new EntityVillager.MerchantRecipeOptionSell(Items.CHAINMAIL_BOOTS, new EntityVillager.MerchantOptionRandomRange(5, 7)), new EntityVillager.MerchantRecipeOptionSell(Items.CHAINMAIL_LEGGINGS, new EntityVillager.MerchantOptionRandomRange(9, 11)), new EntityVillager.MerchantRecipeOptionSell(Items.CHAINMAIL_HELMET, new EntityVillager.MerchantOptionRandomRange(5, 7)), new EntityVillager.MerchantRecipeOptionSell(Items.CHAINMAIL_CHESTPLATE, new EntityVillager.MerchantOptionRandomRange(11, 15))}}, { { new EntityVillager.MerchantRecipeOptionBuy(Items.COAL, new EntityVillager.MerchantOptionRandomRange(16, 24)), new EntityVillager.MerchantRecipeOptionSell(Items.IRON_AXE, new EntityVillager.MerchantOptionRandomRange(6, 8))}, { new EntityVillager.MerchantRecipeOptionBuy(Items.IRON_INGOT, new EntityVillager.MerchantOptionRandomRange(7, 9)), new EntityVillager.MerchantRecipeOptionEnchant(Items.IRON_SWORD, new EntityVillager.MerchantOptionRandomRange(9, 10))}, { new EntityVillager.MerchantRecipeOptionBuy(Items.DIAMOND, new EntityVillager.MerchantOptionRandomRange(3, 4)), new EntityVillager.MerchantRecipeOptionEnchant(Items.DIAMOND_SWORD, new EntityVillager.MerchantOptionRandomRange(12, 15)), new EntityVillager.MerchantRecipeOptionEnchant(Items.DIAMOND_AXE, new EntityVillager.MerchantOptionRandomRange(9, 12))}}, { { new EntityVillager.MerchantRecipeOptionBuy(Items.COAL, new EntityVillager.MerchantOptionRandomRange(16, 24)), new EntityVillager.MerchantRecipeOptionEnchant(Items.IRON_SHOVEL, new EntityVillager.MerchantOptionRandomRange(5, 7))}, { new EntityVillager.MerchantRecipeOptionBuy(Items.IRON_INGOT, new EntityVillager.MerchantOptionRandomRange(7, 9)), new EntityVillager.MerchantRecipeOptionEnchant(Items.IRON_PICKAXE, new EntityVillager.MerchantOptionRandomRange(9, 11))}, { new EntityVillager.MerchantRecipeOptionBuy(Items.DIAMOND, new EntityVillager.MerchantOptionRandomRange(3, 4)), new EntityVillager.MerchantRecipeOptionEnchant(Items.DIAMOND_PICKAXE, new EntityVillager.MerchantOptionRandomRange(12, 15))}}}, { { { new EntityVillager.MerchantRecipeOptionBuy(Items.PORKCHOP, new EntityVillager.MerchantOptionRandomRange(14, 18)), new EntityVillager.MerchantRecipeOptionBuy(Items.CHICKEN, new EntityVillager.MerchantOptionRandomRange(14, 18))}, { new EntityVillager.MerchantRecipeOptionBuy(Items.COAL, new EntityVillager.MerchantOptionRandomRange(16, 24)), new EntityVillager.MerchantRecipeOptionSell(Items.COOKED_PORKCHOP, new EntityVillager.MerchantOptionRandomRange(-7, -5)), new EntityVillager.MerchantRecipeOptionSell(Items.COOKED_CHICKEN, new EntityVillager.MerchantOptionRandomRange(-8, -6))}}, { { new EntityVillager.MerchantRecipeOptionBuy(Items.LEATHER, new EntityVillager.MerchantOptionRandomRange(9, 12)), new EntityVillager.MerchantRecipeOptionSell(Items.LEATHER_LEGGINGS, new EntityVillager.MerchantOptionRandomRange(2, 4))}, { new EntityVillager.MerchantRecipeOptionEnchant(Items.LEATHER_CHESTPLATE, new EntityVillager.MerchantOptionRandomRange(7, 12))}, { new EntityVillager.MerchantRecipeOptionSell(Items.SADDLE, new EntityVillager.MerchantOptionRandomRange(8, 10))}}}};

    public EntityVillager(World world) {
        this(world, 0);
    }

    public EntityVillager(World world, int i) {
        super(world);
        this.inventory = new InventorySubcontainer("Items", false, 8);
        this.setProfession(i);
        this.setSize(0.6F, 1.95F);
        ((Navigation) this.getNavigation()).a(true);
        this.l(true);
    }

    protected void r() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalAvoidTarget(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
        this.goalSelector.a(1, new PathfinderGoalTradeWithPlayer(this));
        this.goalSelector.a(1, new PathfinderGoalLookAtTradingPlayer(this));
        this.goalSelector.a(2, new PathfinderGoalMoveIndoors(this));
        this.goalSelector.a(3, new PathfinderGoalRestrictOpenDoor(this));
        this.goalSelector.a(4, new PathfinderGoalOpenDoor(this, true));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 0.6D));
        this.goalSelector.a(6, new PathfinderGoalMakeLove(this));
        this.goalSelector.a(7, new PathfinderGoalTakeFlower(this));
        this.goalSelector.a(9, new PathfinderGoalInteract(this, EntityHuman.class, 3.0F, 1.0F));
        this.goalSelector.a(9, new PathfinderGoalInteractVillagers(this));
        this.goalSelector.a(9, new PathfinderGoalRandomStroll(this, 0.6D));
        this.goalSelector.a(10, new PathfinderGoalLookAtPlayer(this, EntityInsentient.class, 8.0F));
    }

    private void dj() {
        if (!this.bK) {
            this.bK = true;
            if (this.isBaby()) {
                this.goalSelector.a(8, new PathfinderGoalPlay(this, 0.32D));
            } else if (this.getProfession() == 0) {
                this.goalSelector.a(6, new PathfinderGoalVillagerFarm(this, 0.6D));
            }

        }
    }

    protected void o() {
        if (this.getProfession() == 0) {
            this.goalSelector.a(8, new PathfinderGoalVillagerFarm(this, 0.6D));
        }

        super.o();
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.5D);
    }

    protected void M() {
        if (--this.profession <= 0) {
            BlockPosition blockposition = new BlockPosition(this);

            this.world.ai().a(blockposition);
            this.profession = 70 + this.random.nextInt(50);
            this.village = this.world.ai().getClosestVillage(blockposition, 32);
            if (this.village == null) {
                this.cX();
            } else {
                BlockPosition blockposition1 = this.village.a();

                this.a(blockposition1, this.village.b());
                if (this.bJ) {
                    this.bJ = false;
                    this.village.b(5);
                }
            }
        }

        if (!this.dc() && this.bC > 0) {
            --this.bC;
            if (this.bC <= 0) {
                if (this.bD) {
                    Iterator iterator = this.trades.iterator();

                    while (iterator.hasNext()) {
                        MerchantRecipe merchantrecipe = (MerchantRecipe) iterator.next();

                        if (merchantrecipe.h()) {
                            merchantrecipe.a(this.random.nextInt(6) + this.random.nextInt(6) + 2);
                        }
                    }

                    this.dk();
                    this.bD = false;
                    if (this.village != null && this.bG != null) {
                        this.world.broadcastEntityEffect(this, (byte) 14);
                        this.village.a(this.bG, 1);
                    }
                }

                this.addEffect(new MobEffect(MobEffects.REGENERATION, 200, 0));
            }
        }

        super.M();
    }

    public boolean a(EntityHuman entityhuman, EnumHand enumhand, ItemStack itemstack) {
        boolean flag = itemstack != null && itemstack.getItem() == Items.SPAWN_EGG;

        if (!flag && this.isAlive() && !this.dc() && !this.isBaby()) {
            if (!this.world.isClientSide && (this.trades == null || !this.trades.isEmpty())) {
                this.setTradingPlayer(entityhuman);
                entityhuman.openTrade(this);
            }

            entityhuman.b(StatisticList.H);
            return true;
        } else {
            return super.a(entityhuman, enumhand, itemstack);
        }
    }

    protected void i() {
        super.i();
        this.datawatcher.register(EntityVillager.bw, Integer.valueOf(0));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("Profession", this.getProfession());
        nbttagcompound.setInt("Riches", this.riches);
        nbttagcompound.setInt("Career", this.bH);
        nbttagcompound.setInt("CareerLevel", this.bI);
        nbttagcompound.setBoolean("Willing", this.bE);
        if (this.trades != null) {
            nbttagcompound.set("Offers", this.trades.a());
        }

        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.inventory.getSize(); ++i) {
            ItemStack itemstack = this.inventory.getItem(i);

            if (itemstack != null) {
                nbttaglist.add(itemstack.save(new NBTTagCompound()));
            }
        }

        nbttagcompound.set("Inventory", nbttaglist);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setProfession(nbttagcompound.getInt("Profession"));
        this.riches = nbttagcompound.getInt("Riches");
        this.bH = nbttagcompound.getInt("Career");
        this.bI = nbttagcompound.getInt("CareerLevel");
        this.bE = nbttagcompound.getBoolean("Willing");
        if (nbttagcompound.hasKeyOfType("Offers", 10)) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Offers");

            this.trades = new MerchantRecipeList(nbttagcompound1);
        }

        NBTTagList nbttaglist = nbttagcompound.getList("Inventory", 10);

        for (int i = 0; i < nbttaglist.size(); ++i) {
            ItemStack itemstack = ItemStack.createStack(nbttaglist.get(i));

            if (itemstack != null) {
                this.inventory.a(itemstack);
            }
        }

        this.l(true);
        this.dj();
    }

    protected boolean isTypeNotPersistent() {
        return false;
    }

    protected SoundEffect G() {
        return this.dc() ? SoundEffects.gt : SoundEffects.gp;
    }

    protected SoundEffect bR() {
        return SoundEffects.gr;
    }

    protected SoundEffect bS() {
        return SoundEffects.gq;
    }

    public void setProfession(int i) {
        this.datawatcher.set(EntityVillager.bw, Integer.valueOf(i));
    }

    public int getProfession() {
        return Math.max(((Integer) this.datawatcher.get(EntityVillager.bw)).intValue() % 5, 0);
    }

    public boolean da() {
        return this.by;
    }

    public void o(boolean flag) {
        this.by = flag;
    }

    public void p(boolean flag) {
        this.bz = flag;
    }

    public boolean db() {
        return this.bz;
    }

    public void a(EntityLiving entityliving) {
        super.a(entityliving);
        if (this.village != null && entityliving != null) {
            this.village.a(entityliving);
            if (entityliving instanceof EntityHuman) {
                byte b0 = -1;

                if (this.isBaby()) {
                    b0 = -3;
                }

                this.village.a(entityliving.getName(), b0);
                if (this.isAlive()) {
                    this.world.broadcastEntityEffect(this, (byte) 13);
                }
            }
        }

    }

    public void die(DamageSource damagesource) {
        if (this.village != null) {
            Entity entity = damagesource.getEntity();

            if (entity != null) {
                if (entity instanceof EntityHuman) {
                    this.village.a(entity.getName(), -2);
                } else if (entity instanceof IMonster) {
                    this.village.h();
                }
            } else {
                EntityHuman entityhuman = this.world.findNearbyPlayer(this, 16.0D);

                if (entityhuman != null) {
                    this.village.h();
                }
            }
        }

        super.die(damagesource);
    }

    public void setTradingPlayer(EntityHuman entityhuman) {
        this.tradingPlayer = entityhuman;
    }

    public EntityHuman t_() {
        return this.tradingPlayer;
    }

    public boolean dc() {
        return this.tradingPlayer != null;
    }

    public boolean q(boolean flag) {
        if (!this.bE && flag && this.df()) {
            boolean flag1 = false;

            for (int i = 0; i < this.inventory.getSize(); ++i) {
                ItemStack itemstack = this.inventory.getItem(i);

                if (itemstack != null) {
                    if (itemstack.getItem() == Items.BREAD && itemstack.count >= 3) {
                        flag1 = true;
                        this.inventory.splitStack(i, 3);
                    } else if ((itemstack.getItem() == Items.POTATO || itemstack.getItem() == Items.CARROT) && itemstack.count >= 12) {
                        flag1 = true;
                        this.inventory.splitStack(i, 12);
                    }
                }

                if (flag1) {
                    this.world.broadcastEntityEffect(this, (byte) 18);
                    this.bE = true;
                    break;
                }
            }
        }

        return this.bE;
    }

    public void r(boolean flag) {
        this.bE = flag;
    }

    public void a(MerchantRecipe merchantrecipe) {
        merchantrecipe.g();
        this.a_ = -this.C();
        this.a(SoundEffects.gu, this.cd(), this.ce());
        int i = 3 + this.random.nextInt(4);

        if (merchantrecipe.e() == 1 || this.random.nextInt(5) == 0) {
            this.bC = 40;
            this.bD = true;
            this.bE = true;
            if (this.tradingPlayer != null) {
                this.bG = this.tradingPlayer.getName();
            } else {
                this.bG = null;
            }

            i += 5;
        }

        if (merchantrecipe.getBuyItem1().getItem() == Items.EMERALD) {
            this.riches += merchantrecipe.getBuyItem1().count;
        }

        if (merchantrecipe.j()) {
            this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY + 0.5D, this.locZ, i));
        }

    }

    public void a(ItemStack itemstack) {
        if (!this.world.isClientSide && this.a_ > -this.C() + 20) {
            this.a_ = -this.C();
            if (itemstack != null) {
                this.a(SoundEffects.gu, this.cd(), this.ce());
            } else {
                this.a(SoundEffects.gs, this.cd(), this.ce());
            }
        }

    }

    public MerchantRecipeList getOffers(EntityHuman entityhuman) {
        if (this.trades == null) {
            this.dk();
        }

        return this.trades;
    }

    private void dk() {
        EntityVillager.IMerchantRecipeOption[][][] aentityvillager_imerchantrecipeoption = EntityVillager.bM[this.getProfession()];

        if (this.bH != 0 && this.bI != 0) {
            ++this.bI;
        } else {
            this.bH = this.random.nextInt(aentityvillager_imerchantrecipeoption.length) + 1;
            this.bI = 1;
        }

        if (this.trades == null) {
            this.trades = new MerchantRecipeList();
        }

        int i = this.bH - 1;
        int j = this.bI - 1;
        EntityVillager.IMerchantRecipeOption[][] aentityvillager_imerchantrecipeoption1 = aentityvillager_imerchantrecipeoption[i];

        if (j >= 0 && j < aentityvillager_imerchantrecipeoption1.length) {
            EntityVillager.IMerchantRecipeOption[] aentityvillager_imerchantrecipeoption2 = aentityvillager_imerchantrecipeoption1[j];
            EntityVillager.IMerchantRecipeOption[] aentityvillager_imerchantrecipeoption3 = aentityvillager_imerchantrecipeoption2;
            int k = aentityvillager_imerchantrecipeoption2.length;

            for (int l = 0; l < k; ++l) {
                EntityVillager.IMerchantRecipeOption entityvillager_imerchantrecipeoption = aentityvillager_imerchantrecipeoption3[l];

                entityvillager_imerchantrecipeoption.a(this.trades, this.random);
            }
        }

    }

    public IChatBaseComponent getScoreboardDisplayName() {
        ScoreboardTeamBase scoreboardteambase = this.aO();
        String s = this.getCustomName();

        if (s != null && !s.isEmpty()) {
            ChatComponentText chatcomponenttext = new ChatComponentText(ScoreboardTeam.getPlayerDisplayName(scoreboardteambase, s));

            chatcomponenttext.getChatModifier().setChatHoverable(this.bk());
            chatcomponenttext.getChatModifier().setInsertion(this.getUniqueID().toString());
            return chatcomponenttext;
        } else {
            if (this.trades == null) {
                this.dk();
            }

            String s1 = null;

            switch (this.getProfession()) {
            case 0:
                if (this.bH == 1) {
                    s1 = "farmer";
                } else if (this.bH == 2) {
                    s1 = "fisherman";
                } else if (this.bH == 3) {
                    s1 = "shepherd";
                } else if (this.bH == 4) {
                    s1 = "fletcher";
                }
                break;

            case 1:
                s1 = "librarian";
                break;

            case 2:
                s1 = "cleric";
                break;

            case 3:
                if (this.bH == 1) {
                    s1 = "armor";
                } else if (this.bH == 2) {
                    s1 = "weapon";
                } else if (this.bH == 3) {
                    s1 = "tool";
                }
                break;

            case 4:
                if (this.bH == 1) {
                    s1 = "butcher";
                } else if (this.bH == 2) {
                    s1 = "leather";
                }
            }

            if (s1 != null) {
                ChatMessage chatmessage = new ChatMessage("entity.Villager." + s1, new Object[0]);

                chatmessage.getChatModifier().setChatHoverable(this.bk());
                chatmessage.getChatModifier().setInsertion(this.getUniqueID().toString());
                if (scoreboardteambase != null) {
                    chatmessage.getChatModifier().setColor(scoreboardteambase.m());
                }

                return chatmessage;
            } else {
                return super.getScoreboardDisplayName();
            }
        }
    }

    public float getHeadHeight() {
        return this.isBaby() ? 0.81F : 1.62F;
    }

    public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
        groupdataentity = super.prepare(difficultydamagescaler, groupdataentity);
        this.setProfession(this.world.random.nextInt(5));
        this.dj();
        return groupdataentity;
    }

    public void dd() {
        this.bJ = true;
    }

    public EntityVillager b(EntityAgeable entityageable) {
        EntityVillager entityvillager = new EntityVillager(this.world);

        entityvillager.prepare(this.world.D(new BlockPosition(entityvillager)), (GroupDataEntity) null);
        return entityvillager;
    }

    public boolean a(EntityHuman entityhuman) {
        return false;
    }

    public void onLightningStrike(EntityLightning entitylightning) {
        if (!this.world.isClientSide && !this.dead) {
            EntityWitch entitywitch = new EntityWitch(this.world);

            entitywitch.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
            entitywitch.prepare(this.world.D(new BlockPosition(entitywitch)), (GroupDataEntity) null);
            entitywitch.m(this.cR());
            if (this.hasCustomName()) {
                entitywitch.setCustomName(this.getCustomName());
                entitywitch.setCustomNameVisible(this.getCustomNameVisible());
            }

            this.world.addEntity(entitywitch);
            this.die();
        }
    }

    public InventorySubcontainer de() {
        return this.inventory;
    }

    protected void a(EntityItem entityitem) {
        ItemStack itemstack = entityitem.getItemStack();
        Item item = itemstack.getItem();

        if (this.a(item)) {
            ItemStack itemstack1 = this.inventory.a(itemstack);

            if (itemstack1 == null) {
                entityitem.die();
            } else {
                itemstack.count = itemstack1.count;
            }
        }

    }

    private boolean a(Item item) {
        return item == Items.BREAD || item == Items.POTATO || item == Items.CARROT || item == Items.WHEAT || item == Items.WHEAT_SEEDS || item == Items.BEETROOT || item == Items.BEETROOT_SEEDS;
    }

    public boolean df() {
        return this.m(1);
    }

    public boolean dg() {
        return this.m(2);
    }

    public boolean dh() {
        boolean flag = this.getProfession() == 0;

        return flag ? !this.m(5) : !this.m(1);
    }

    private boolean m(int i) {
        boolean flag = this.getProfession() == 0;

        for (int j = 0; j < this.inventory.getSize(); ++j) {
            ItemStack itemstack = this.inventory.getItem(j);

            if (itemstack != null) {
                if (itemstack.getItem() == Items.BREAD && itemstack.count >= 3 * i || itemstack.getItem() == Items.POTATO && itemstack.count >= 12 * i || itemstack.getItem() == Items.CARROT && itemstack.count >= 12 * i || itemstack.getItem() == Items.BEETROOT && itemstack.count >= 12 * i) {
                    return true;
                }

                if (flag && itemstack.getItem() == Items.WHEAT && itemstack.count >= 9 * i) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean di() {
        for (int i = 0; i < this.inventory.getSize(); ++i) {
            ItemStack itemstack = this.inventory.getItem(i);

            if (itemstack != null && (itemstack.getItem() == Items.WHEAT_SEEDS || itemstack.getItem() == Items.POTATO || itemstack.getItem() == Items.CARROT || itemstack.getItem() == Items.BEETROOT_SEEDS)) {
                return true;
            }
        }

        return false;
    }

    public boolean c(int i, ItemStack itemstack) {
        if (super.c(i, itemstack)) {
            return true;
        } else {
            int j = i - 300;

            if (j >= 0 && j < this.inventory.getSize()) {
                this.inventory.setItem(j, itemstack);
                return true;
            } else {
                return false;
            }
        }
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.b(entityageable);
    }

    static class MerchantRecipeOptionProcess implements EntityVillager.IMerchantRecipeOption {

        public ItemStack a;
        public EntityVillager.MerchantOptionRandomRange b;
        public ItemStack c;
        public EntityVillager.MerchantOptionRandomRange d;

        public MerchantRecipeOptionProcess(Item item, EntityVillager.MerchantOptionRandomRange entityvillager_merchantoptionrandomrange, Item item1, EntityVillager.MerchantOptionRandomRange entityvillager_merchantoptionrandomrange1) {
            this.a = new ItemStack(item);
            this.b = entityvillager_merchantoptionrandomrange;
            this.c = new ItemStack(item1);
            this.d = entityvillager_merchantoptionrandomrange1;
        }

        public void a(MerchantRecipeList merchantrecipelist, Random random) {
            int i = 1;

            if (this.b != null) {
                i = this.b.a(random);
            }

            int j = 1;

            if (this.d != null) {
                j = this.d.a(random);
            }

            merchantrecipelist.add(new MerchantRecipe(new ItemStack(this.a.getItem(), i, this.a.getData()), new ItemStack(Items.EMERALD), new ItemStack(this.c.getItem(), j, this.c.getData())));
        }
    }

    static class MerchantRecipeOptionBook implements EntityVillager.IMerchantRecipeOption {

        public MerchantRecipeOptionBook() {}

        public void a(MerchantRecipeList merchantrecipelist, Random random) {
            Enchantment enchantment = (Enchantment) Enchantment.enchantments.a(random);
            int i = MathHelper.nextInt(random, enchantment.getStartLevel(), enchantment.getMaxLevel());
            ItemStack itemstack = Items.ENCHANTED_BOOK.a(new WeightedRandomEnchant(enchantment, i));
            int j = 2 + random.nextInt(5 + i * 10) + 3 * i;

            if (enchantment.e()) {
                j *= 2;
            }

            if (j > 64) {
                j = 64;
            }

            merchantrecipelist.add(new MerchantRecipe(new ItemStack(Items.BOOK), new ItemStack(Items.EMERALD, j), itemstack));
        }
    }

    static class MerchantRecipeOptionEnchant implements EntityVillager.IMerchantRecipeOption {

        public ItemStack a;
        public EntityVillager.MerchantOptionRandomRange b;

        public MerchantRecipeOptionEnchant(Item item, EntityVillager.MerchantOptionRandomRange entityvillager_merchantoptionrandomrange) {
            this.a = new ItemStack(item);
            this.b = entityvillager_merchantoptionrandomrange;
        }

        public void a(MerchantRecipeList merchantrecipelist, Random random) {
            int i = 1;

            if (this.b != null) {
                i = this.b.a(random);
            }

            ItemStack itemstack = new ItemStack(Items.EMERALD, i, 0);
            ItemStack itemstack1 = new ItemStack(this.a.getItem(), 1, this.a.getData());

            itemstack1 = EnchantmentManager.a(random, itemstack1, 5 + random.nextInt(15), false);
            merchantrecipelist.add(new MerchantRecipe(itemstack, itemstack1));
        }
    }

    static class MerchantRecipeOptionSell implements EntityVillager.IMerchantRecipeOption {

        public ItemStack a;
        public EntityVillager.MerchantOptionRandomRange b;

        public MerchantRecipeOptionSell(Item item, EntityVillager.MerchantOptionRandomRange entityvillager_merchantoptionrandomrange) {
            this.a = new ItemStack(item);
            this.b = entityvillager_merchantoptionrandomrange;
        }

        public MerchantRecipeOptionSell(ItemStack itemstack, EntityVillager.MerchantOptionRandomRange entityvillager_merchantoptionrandomrange) {
            this.a = itemstack;
            this.b = entityvillager_merchantoptionrandomrange;
        }

        public void a(MerchantRecipeList merchantrecipelist, Random random) {
            int i = 1;

            if (this.b != null) {
                i = this.b.a(random);
            }

            ItemStack itemstack;
            ItemStack itemstack1;

            if (i < 0) {
                itemstack = new ItemStack(Items.EMERALD);
                itemstack1 = new ItemStack(this.a.getItem(), -i, this.a.getData());
            } else {
                itemstack = new ItemStack(Items.EMERALD, i, 0);
                itemstack1 = new ItemStack(this.a.getItem(), 1, this.a.getData());
            }

            merchantrecipelist.add(new MerchantRecipe(itemstack, itemstack1));
        }
    }

    static class MerchantRecipeOptionBuy implements EntityVillager.IMerchantRecipeOption {

        public Item a;
        public EntityVillager.MerchantOptionRandomRange b;

        public MerchantRecipeOptionBuy(Item item, EntityVillager.MerchantOptionRandomRange entityvillager_merchantoptionrandomrange) {
            this.a = item;
            this.b = entityvillager_merchantoptionrandomrange;
        }

        public void a(MerchantRecipeList merchantrecipelist, Random random) {
            int i = 1;

            if (this.b != null) {
                i = this.b.a(random);
            }

            merchantrecipelist.add(new MerchantRecipe(new ItemStack(this.a, i, 0), Items.EMERALD));
        }
    }

    interface IMerchantRecipeOption {

        void a(MerchantRecipeList merchantrecipelist, Random random);
    }

    static class MerchantOptionRandomRange extends Tuple<Integer, Integer> {

        public MerchantOptionRandomRange(int i, int j) {
            super(Integer.valueOf(i), Integer.valueOf(j));
        }

        public int a(Random random) {
            return ((Integer) this.a()).intValue() >= ((Integer) this.b()).intValue() ? ((Integer) this.a()).intValue() : ((Integer) this.a()).intValue() + random.nextInt(((Integer) this.b()).intValue() - ((Integer) this.a()).intValue() + 1);
        }
    }
}
