package net.minecraft.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class EntityVillager extends EntityVillagerAbstract implements ReputationHandler, VillagerDataHolder {

    private static final DataWatcherObject<VillagerData> bC = DataWatcher.a(EntityVillager.class, DataWatcherRegistry.q);
    public static final Map<Item, Integer> bA = ImmutableMap.of(Items.BREAD, 4, Items.POTATO, 1, Items.CARROT, 1, Items.BEETROOT, 1);
    private static final Set<Item> bD = ImmutableSet.of(Items.BREAD, Items.POTATO, Items.CARROT, Items.WHEAT, Items.WHEAT_SEEDS, Items.BEETROOT, new Item[]{Items.BEETROOT_SEEDS});
    private int bE;
    private boolean bF;
    @Nullable
    private EntityHuman bG;
    private byte bI;
    private final Reputation bJ;
    private long bK;
    private long bL;
    private int bM;
    private long bN;
    private static final ImmutableList<MemoryModuleType<?>> bO = ImmutableList.of(MemoryModuleType.HOME, MemoryModuleType.JOB_SITE, MemoryModuleType.MEETING_POINT, MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.VISIBLE_VILLAGER_BABIES, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.BREED_TARGET, new MemoryModuleType[]{MemoryModuleType.PATH, MemoryModuleType.INTERACTABLE_DOORS, MemoryModuleType.NEAREST_BED, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_HOSTILE, MemoryModuleType.SECONDARY_JOB_SITE, MemoryModuleType.HIDING_PLACE, MemoryModuleType.HEARD_BELL_TIME, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.LAST_SLEPT, MemoryModuleType.LAST_WORKED_AT_POI, MemoryModuleType.GOLEM_LAST_SEEN_TIME});
    private static final ImmutableList<SensorType<? extends Sensor<? super EntityVillager>>> bP = ImmutableList.of(SensorType.b, SensorType.c, SensorType.d, SensorType.e, SensorType.f, SensorType.g, SensorType.h, SensorType.i, SensorType.j);
    public static final Map<MemoryModuleType<GlobalPos>, BiPredicate<EntityVillager, VillagePlaceType>> bB = ImmutableMap.of(MemoryModuleType.HOME, (entityvillager, villageplacetype) -> {
        return villageplacetype == VillagePlaceType.q;
    }, MemoryModuleType.JOB_SITE, (entityvillager, villageplacetype) -> {
        return entityvillager.getVillagerData().getProfession().b() == villageplacetype;
    }, MemoryModuleType.MEETING_POINT, (entityvillager, villageplacetype) -> {
        return villageplacetype == VillagePlaceType.r;
    });

    public EntityVillager(EntityTypes<? extends EntityVillager> entitytypes, World world) {
        this(entitytypes, world, VillagerType.PLAINS);
    }

    public EntityVillager(EntityTypes<? extends EntityVillager> entitytypes, World world, VillagerType villagertype) {
        super(entitytypes, world);
        this.bJ = new Reputation();
        ((Navigation) this.getNavigation()).a(true);
        this.getNavigation().d(true);
        this.setCanPickupLoot(true);
        this.setVillagerData(this.getVillagerData().withType(villagertype).withProfession(VillagerProfession.NONE));
        this.br = this.a(new Dynamic(DynamicOpsNBT.a, new NBTTagCompound()));
    }

    @Override
    public BehaviorController<EntityVillager> getBehaviorController() {
        return super.getBehaviorController();
    }

    @Override
    protected BehaviorController<?> a(Dynamic<?> dynamic) {
        BehaviorController<EntityVillager> behaviorcontroller = new BehaviorController<>(EntityVillager.bO, EntityVillager.bP, dynamic);

        this.a(behaviorcontroller);
        return behaviorcontroller;
    }

    public void a(WorldServer worldserver) {
        BehaviorController<EntityVillager> behaviorcontroller = this.getBehaviorController();

        behaviorcontroller.b(worldserver, this);
        this.br = behaviorcontroller.f();
        this.a(this.getBehaviorController());
    }

    private void a(BehaviorController<EntityVillager> behaviorcontroller) {
        VillagerProfession villagerprofession = this.getVillagerData().getProfession();
        float f = (float) this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue();

        if (this.isBaby()) {
            behaviorcontroller.setSchedule(Schedule.VILLAGER_BABY);
            behaviorcontroller.a(Activity.PLAY, Behaviors.a(f));
        } else {
            behaviorcontroller.setSchedule(Schedule.VILLAGER_DEFAULT);
            behaviorcontroller.a(Activity.WORK, Behaviors.b(villagerprofession, f), (Set) ImmutableSet.of(Pair.of(MemoryModuleType.JOB_SITE, MemoryStatus.VALUE_PRESENT)));
        }

        behaviorcontroller.a(Activity.CORE, Behaviors.a(villagerprofession, f));
        behaviorcontroller.a(Activity.MEET, Behaviors.d(villagerprofession, f), (Set) ImmutableSet.of(Pair.of(MemoryModuleType.MEETING_POINT, MemoryStatus.VALUE_PRESENT)));
        behaviorcontroller.a(Activity.REST, Behaviors.c(villagerprofession, f));
        behaviorcontroller.a(Activity.IDLE, Behaviors.e(villagerprofession, f));
        behaviorcontroller.a(Activity.PANIC, Behaviors.f(villagerprofession, f));
        behaviorcontroller.a(Activity.PRE_RAID, Behaviors.g(villagerprofession, f));
        behaviorcontroller.a(Activity.RAID, Behaviors.h(villagerprofession, f));
        behaviorcontroller.a(Activity.HIDE, Behaviors.i(villagerprofession, f));
        behaviorcontroller.a((Set) ImmutableSet.of(Activity.CORE));
        behaviorcontroller.b(Activity.IDLE);
        behaviorcontroller.a(Activity.IDLE);
        behaviorcontroller.a(this.world.getDayTime(), this.world.getTime());
    }

    @Override
    protected void l() {
        super.l();
        if (this.world instanceof WorldServer) {
            this.a((WorldServer) this.world);
        }

    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.5D);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(48.0D);
    }

    @Override
    protected void mobTick() {
        this.world.getMethodProfiler().enter("brain");
        this.getBehaviorController().a((WorldServer) this.world, (EntityLiving) this);
        this.world.getMethodProfiler().exit();
        if (!this.dZ() && this.bE > 0) {
            --this.bE;
            if (this.bE <= 0) {
                if (this.bF) {
                    this.populateTrades();
                    this.bF = false;
                }

                this.addEffect(new MobEffect(MobEffects.REGENERATION, 200, 0));
            }
        }

        if (this.bG != null && this.world instanceof WorldServer) {
            ((WorldServer) this.world).a(ReputationEvent.e, (Entity) this.bG, (ReputationHandler) this);
            this.world.broadcastEntityEffect(this, (byte) 14);
            this.bG = null;
        }

        if (!this.isNoAI() && this.random.nextInt(100) == 0) {
            Raid raid = ((WorldServer) this.world).c_(new BlockPosition(this));

            if (raid != null && raid.v() && !raid.a()) {
                this.world.broadcastEntityEffect(this, (byte) 42);
            }
        }

        if (!this.br.getMemory(MemoryModuleType.JOB_SITE).isPresent() && this.dZ()) {
            this.ee();
        }

        super.mobTick();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.dW() > 0) {
            this.r(this.dW() - 1);
        }

        this.eC();
    }

    @Override
    public boolean a(EntityHuman entityhuman, EnumHand enumhand) {
        ItemStack itemstack = entityhuman.b(enumhand);
        boolean flag = itemstack.getItem() == Items.NAME_TAG;

        if (flag) {
            itemstack.a(entityhuman, (EntityLiving) this, enumhand);
            return true;
        } else if (itemstack.getItem() != Items.VILLAGER_SPAWN_EGG && this.isAlive() && !this.dZ() && !this.isSleeping()) {
            if (this.isBaby()) {
                this.ev();
                return super.a(entityhuman, enumhand);
            } else {
                boolean flag1 = this.getOffers().isEmpty();

                if (enumhand == EnumHand.MAIN_HAND) {
                    if (flag1 && !this.world.isClientSide) {
                        this.ev();
                    }

                    entityhuman.a(StatisticList.TALKED_TO_VILLAGER);
                }

                if (flag1) {
                    return super.a(entityhuman, enumhand);
                } else {
                    if (!this.world.isClientSide && !this.trades.isEmpty()) {
                        this.g(entityhuman);
                    }

                    return true;
                }
            }
        } else {
            return super.a(entityhuman, enumhand);
        }
    }

    private void ev() {
        this.r(40);
        if (!this.world.e()) {
            this.a(SoundEffects.ENTITY_VILLAGER_NO, this.getSoundVolume(), this.cV());
        }

    }

    private void g(EntityHuman entityhuman) {
        this.h(entityhuman);
        this.setTradingPlayer(entityhuman);
        this.openTrade(entityhuman, this.getScoreboardDisplayName(), this.getVillagerData().getLevel());
    }

    @Override
    public void setTradingPlayer(@Nullable EntityHuman entityhuman) {
        boolean flag = this.getTrader() != null && entityhuman == null;

        super.setTradingPlayer(entityhuman);
        if (flag) {
            this.ee();
        }

    }

    @Override
    protected void ee() {
        super.ee();
        this.ew();
    }

    private void ew() {
        Iterator iterator = this.getOffers().iterator();

        while (iterator.hasNext()) {
            MerchantRecipe merchantrecipe = (MerchantRecipe) iterator.next();

            merchantrecipe.setSpecialPrice();
        }

    }

    @Override
    public boolean ej() {
        return true;
    }

    public void ek() {
        Iterator iterator = this.getOffers().iterator();

        while (iterator.hasNext()) {
            MerchantRecipe merchantrecipe = (MerchantRecipe) iterator.next();

            merchantrecipe.e();
            merchantrecipe.resetUses();
        }

        if (this.getVillagerData().getProfession() == VillagerProfession.FARMER) {
            this.eB();
        }

        this.bN = this.world.getDayTime() % 24000L;
    }

    private void h(EntityHuman entityhuman) {
        int i = this.f(entityhuman);

        if (i != 0) {
            Iterator iterator = this.getOffers().iterator();

            while (iterator.hasNext()) {
                MerchantRecipe merchantrecipe = (MerchantRecipe) iterator.next();

                merchantrecipe.increaseSpecialPrice(-MathHelper.d((float) i * merchantrecipe.getPriceMultiplier()));
            }
        }

        if (entityhuman.hasEffect(MobEffects.HERO_OF_THE_VILLAGE)) {
            MobEffect mobeffect = entityhuman.getEffect(MobEffects.HERO_OF_THE_VILLAGE);
            int j = mobeffect.getAmplifier();
            Iterator iterator1 = this.getOffers().iterator();

            while (iterator1.hasNext()) {
                MerchantRecipe merchantrecipe1 = (MerchantRecipe) iterator1.next();
                double d0 = 0.3D + 0.0625D * (double) j;
                int k = (int) Math.floor(d0 * (double) merchantrecipe1.a().getCount());

                merchantrecipe1.increaseSpecialPrice(-Math.max(k, 1));
            }
        }

    }

    @Override
    protected void initDatawatcher() {
        super.initDatawatcher();
        this.datawatcher.register(EntityVillager.bC, new VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, 1));
    }

    @Override
    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.set("VillagerData", (NBTBase) this.getVillagerData().a(DynamicOpsNBT.a));
        nbttagcompound.setByte("FoodLevel", this.bI);
        nbttagcompound.set("Gossips", (NBTBase) this.bJ.a((DynamicOps) DynamicOpsNBT.a).getValue());
        nbttagcompound.setInt("Xp", this.bM);
        nbttagcompound.setLong("LastRestock", this.bN);
        nbttagcompound.setLong("LastGossipDecay", this.bL);
    }

    @Override
    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.hasKeyOfType("VillagerData", 10)) {
            this.setVillagerData(new VillagerData(new Dynamic(DynamicOpsNBT.a, nbttagcompound.get("VillagerData"))));
        }

        if (nbttagcompound.hasKeyOfType("Offers", 10)) {
            this.trades = new MerchantRecipeList(nbttagcompound.getCompound("Offers"));
        }

        if (nbttagcompound.hasKeyOfType("FoodLevel", 1)) {
            this.bI = nbttagcompound.getByte("FoodLevel");
        }

        NBTTagList nbttaglist = nbttagcompound.getList("Gossips", 10);

        this.bJ.a(new Dynamic(DynamicOpsNBT.a, nbttaglist));
        if (nbttagcompound.hasKeyOfType("Xp", 3)) {
            this.bM = nbttagcompound.getInt("Xp");
        }

        this.bN = nbttagcompound.getLong("LastRestock");
        this.bL = nbttagcompound.getLong("LastGossipDecay");
        this.setCanPickupLoot(true);
        this.a((WorldServer) this.world);
    }

    @Override
    public boolean isTypeNotPersistent(double d0) {
        return false;
    }

    @Nullable
    @Override
    protected SoundEffect getSoundAmbient() {
        return this.isSleeping() ? null : (this.dZ() ? SoundEffects.ENTITY_VILLAGER_TRADE : SoundEffects.ENTITY_VILLAGER_AMBIENT);
    }

    @Override
    protected SoundEffect getSoundHurt(DamageSource damagesource) {
        return SoundEffects.ENTITY_VILLAGER_HURT;
    }

    @Override
    protected SoundEffect getSoundDeath() {
        return SoundEffects.ENTITY_VILLAGER_DEATH;
    }

    public void el() {
        SoundEffect soundeffect = this.getVillagerData().getProfession().b().d();

        if (soundeffect != null) {
            this.a(soundeffect, this.getSoundVolume(), this.cV());
        }

    }

    public void setVillagerData(VillagerData villagerdata) {
        VillagerData villagerdata1 = this.getVillagerData();

        if (villagerdata1.getProfession() != villagerdata.getProfession()) {
            this.trades = null;
        }

        this.datawatcher.set(EntityVillager.bC, villagerdata);
    }

    @Override
    public VillagerData getVillagerData() {
        return (VillagerData) this.datawatcher.get(EntityVillager.bC);
    }

    @Override
    protected void b(MerchantRecipe merchantrecipe) {
        int i = 3 + this.random.nextInt(4);

        this.bM += merchantrecipe.getXp();
        this.bG = this.getTrader();
        if (this.ey()) {
            this.bE = 40;
            this.bF = true;
            i += 5;
        }

        if (merchantrecipe.isRewardExp()) {
            this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY + 0.5D, this.locZ, i));
        }

    }

    @Override
    public void setLastDamager(@Nullable EntityLiving entityliving) {
        if (entityliving != null && this.world instanceof WorldServer) {
            ((WorldServer) this.world).a(ReputationEvent.c, (Entity) entityliving, (ReputationHandler) this);
            if (this.isAlive() && entityliving instanceof EntityHuman) {
                this.world.broadcastEntityEffect(this, (byte) 13);
            }
        }

        super.setLastDamager(entityliving);
    }

    @Override
    public void die(DamageSource damagesource) {
        Entity entity = damagesource.getEntity();

        if (entity != null) {
            this.a(entity);
        }

        this.a(MemoryModuleType.HOME);
        this.a(MemoryModuleType.JOB_SITE);
        this.a(MemoryModuleType.MEETING_POINT);
        super.die(damagesource);
    }

    private void a(Entity entity) {
        if (this.world instanceof WorldServer) {
            Optional<List<EntityLiving>> optional = this.br.getMemory(MemoryModuleType.VISIBLE_MOBS);

            if (optional.isPresent()) {
                WorldServer worldserver = (WorldServer) this.world;

                ((List) optional.get()).stream().filter((entityliving) -> {
                    return entityliving instanceof ReputationHandler;
                }).forEach((entityliving) -> {
                    worldserver.a(ReputationEvent.d, entity, (ReputationHandler) entityliving);
                });
            }
        }
    }

    public void a(MemoryModuleType<GlobalPos> memorymoduletype) {
        if (this.world instanceof WorldServer) {
            MinecraftServer minecraftserver = ((WorldServer) this.world).getMinecraftServer();

            this.br.getMemory(memorymoduletype).ifPresent((globalpos) -> {
                WorldServer worldserver = minecraftserver.getWorldServer(globalpos.getDimensionManager());
                VillagePlace villageplace = worldserver.B();
                Optional<VillagePlaceType> optional = villageplace.c(globalpos.getBlockPosition());
                BiPredicate<EntityVillager, VillagePlaceType> bipredicate = (BiPredicate) EntityVillager.bB.get(memorymoduletype);

                if (optional.isPresent() && bipredicate.test(this, optional.get())) {
                    villageplace.b(globalpos.getBlockPosition());
                    PacketDebug.c(worldserver, globalpos.getBlockPosition());
                }

            });
        }
    }

    public boolean canBreed() {
        return this.bI + this.eA() >= 12 && this.getAge() == 0;
    }

    private boolean ex() {
        return this.bI < 12;
    }

    public void eo() {
        if (this.ex() && this.eA() != 0) {
            for (int i = 0; i < this.getInventory().getSize(); ++i) {
                ItemStack itemstack = this.getInventory().getItem(i);

                if (!itemstack.isEmpty()) {
                    Integer integer = (Integer) EntityVillager.bA.get(itemstack.getItem());

                    if (integer != null) {
                        int j = itemstack.getCount();

                        for (int k = j; k > 0; --k) {
                            this.bI = (byte) (this.bI + integer);
                            this.getInventory().splitStack(i, 1);
                            if (!this.ex()) {
                                return;
                            }
                        }
                    }
                }
            }

        }
    }

    public int f(EntityHuman entityhuman) {
        return this.bJ.a(entityhuman.getUniqueID(), (reputationtype) -> {
            return true;
        });
    }

    private void u(int i) {
        this.bI = (byte) (this.bI - i);
    }

    public void ep() {
        this.eo();
        this.u(12);
    }

    public void b(MerchantRecipeList merchantrecipelist) {
        this.trades = merchantrecipelist;
    }

    private boolean ey() {
        int i = this.getVillagerData().getLevel();

        return VillagerData.d(i) && this.bM >= VillagerData.c(i);
    }

    public void populateTrades() {
        this.setVillagerData(this.getVillagerData().withLevel(this.getVillagerData().getLevel() + 1));
        this.ei();
    }

    @Override
    public IChatBaseComponent getScoreboardDisplayName() {
        ScoreboardTeamBase scoreboardteambase = this.getScoreboardTeam();
        IChatBaseComponent ichatbasecomponent = this.getCustomName();

        if (ichatbasecomponent != null) {
            return ScoreboardTeam.a(scoreboardteambase, ichatbasecomponent).a((chatmodifier) -> {
                chatmodifier.setChatHoverable(this.bK()).setInsertion(this.getUniqueIDString());
            });
        } else {
            VillagerProfession villagerprofession = this.getVillagerData().getProfession();
            IChatBaseComponent ichatbasecomponent1 = (new ChatMessage(this.getEntityType().e() + '.' + IRegistry.VILLAGER_PROFESSION.getKey(villagerprofession).getKey(), new Object[0])).a((chatmodifier) -> {
                chatmodifier.setChatHoverable(this.bK()).setInsertion(this.getUniqueIDString());
            });

            if (scoreboardteambase != null) {
                ichatbasecomponent1.a(scoreboardteambase.getColor());
            }

            return ichatbasecomponent1;
        }
    }

    @Nullable
    @Override
    public GroupDataEntity prepare(GeneratorAccess generatoraccess, DifficultyDamageScaler difficultydamagescaler, EnumMobSpawn enummobspawn, @Nullable GroupDataEntity groupdataentity, @Nullable NBTTagCompound nbttagcompound) {
        if (enummobspawn == EnumMobSpawn.BREEDING) {
            this.setVillagerData(this.getVillagerData().withProfession(VillagerProfession.NONE));
        }

        if (enummobspawn == EnumMobSpawn.COMMAND || enummobspawn == EnumMobSpawn.SPAWN_EGG || enummobspawn == EnumMobSpawn.SPAWNER) {
            this.setVillagerData(this.getVillagerData().withType(VillagerType.a(generatoraccess.getBiome(new BlockPosition(this)))));
        }

        return super.prepare(generatoraccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
    }

    @Override
    public EntityVillager createChild(EntityAgeable entityageable) {
        double d0 = this.random.nextDouble();
        VillagerType villagertype;

        if (d0 < 0.5D) {
            villagertype = VillagerType.a(this.world.getBiome(new BlockPosition(this)));
        } else if (d0 < 0.75D) {
            villagertype = this.getVillagerData().getType();
        } else {
            villagertype = ((EntityVillager) entityageable).getVillagerData().getType();
        }

        EntityVillager entityvillager = new EntityVillager(EntityTypes.VILLAGER, this.world, villagertype);

        entityvillager.prepare(this.world, this.world.getDamageScaler(new BlockPosition(entityvillager)), EnumMobSpawn.BREEDING, (GroupDataEntity) null, (NBTTagCompound) null);
        return entityvillager;
    }

    @Override
    public void onLightningStrike(EntityLightning entitylightning) {
        EntityWitch entitywitch = (EntityWitch) EntityTypes.WITCH.a(this.world);

        entitywitch.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
        entitywitch.prepare(this.world, this.world.getDamageScaler(new BlockPosition(entitywitch)), EnumMobSpawn.CONVERSION, (GroupDataEntity) null, (NBTTagCompound) null);
        entitywitch.setNoAI(this.isNoAI());
        if (this.hasCustomName()) {
            entitywitch.setCustomName(this.getCustomName());
            entitywitch.setCustomNameVisible(this.getCustomNameVisible());
        }

        this.world.addEntity(entitywitch);
        this.die();
    }

    @Override
    protected void a(EntityItem entityitem) {
        ItemStack itemstack = entityitem.getItemStack();
        Item item = itemstack.getItem();
        VillagerProfession villagerprofession = this.getVillagerData().getProfession();

        if (EntityVillager.bD.contains(item) || villagerprofession.c().contains(item)) {
            InventorySubcontainer inventorysubcontainer = this.getInventory();
            int i = inventorysubcontainer.a(item);

            if (i == 256) {
                return;
            }

            if (i > 256) {
                inventorysubcontainer.a(item, i - 256);
                return;
            }

            this.receive(entityitem, itemstack.getCount());
            ItemStack itemstack1 = inventorysubcontainer.a(itemstack);

            if (itemstack1.isEmpty()) {
                entityitem.die();
            } else {
                itemstack.setCount(itemstack1.getCount());
            }
        }

    }

    public boolean eq() {
        return this.eA() >= 24;
    }

    public boolean er() {
        return this.eA() < 12;
    }

    private int eA() {
        InventorySubcontainer inventorysubcontainer = this.getInventory();

        return EntityVillager.bA.entrySet().stream().mapToInt((entry) -> {
            return inventorysubcontainer.a((Item) entry.getKey()) * (Integer) entry.getValue();
        }).sum();
    }

    private void eB() {
        InventorySubcontainer inventorysubcontainer = this.getInventory();
        int i = inventorysubcontainer.a(Items.WHEAT);
        int j = i / 3;

        if (j != 0) {
            int k = j * 3;

            inventorysubcontainer.a(Items.WHEAT, k);
            ItemStack itemstack = inventorysubcontainer.a(new ItemStack(Items.BREAD, j));

            if (!itemstack.isEmpty()) {
                this.a(itemstack, 0.5F);
            }

        }
    }

    public boolean es() {
        InventorySubcontainer inventorysubcontainer = this.getInventory();

        return inventorysubcontainer.a((Set) ImmutableSet.of(Items.WHEAT_SEEDS, Items.POTATO, Items.CARROT, Items.BEETROOT_SEEDS));
    }

    @Override
    protected void ei() {
        VillagerData villagerdata = this.getVillagerData();
        Int2ObjectMap<VillagerTrades.IMerchantRecipeOption[]> int2objectmap = (Int2ObjectMap) VillagerTrades.a.get(villagerdata.getProfession());

        if (int2objectmap != null && !int2objectmap.isEmpty()) {
            VillagerTrades.IMerchantRecipeOption[] avillagertrades_imerchantrecipeoption = (VillagerTrades.IMerchantRecipeOption[]) int2objectmap.get(villagerdata.getLevel());

            if (avillagertrades_imerchantrecipeoption != null) {
                MerchantRecipeList merchantrecipelist = this.getOffers();

                this.a(merchantrecipelist, avillagertrades_imerchantrecipeoption, 2);
            }
        }
    }

    public void a(EntityVillager entityvillager, long i) {
        if ((i < this.bK || i >= this.bK + 1200L) && (i < entityvillager.bK || i >= entityvillager.bK + 1200L)) {
            this.bJ.a(entityvillager.bJ, this.random, 10);
            this.bK = i;
            entityvillager.bK = i;
            this.a(i, 5);
        }
    }

    private void eC() {
        long i = this.world.getTime();

        if (this.bL == 0L) {
            this.bL = i;
        } else if (i >= this.bL + 24000L) {
            this.bJ.b();
            this.bL = i;
        }
    }

    public void a(long i, int j) {
        if (this.a(i)) {
            AxisAlignedBB axisalignedbb = this.getBoundingBox().grow(10.0D, 10.0D, 10.0D);
            List<EntityVillager> list = this.world.a(EntityVillager.class, axisalignedbb);
            List<EntityVillager> list1 = (List) list.stream().filter((entityvillager) -> {
                return entityvillager.a(i);
            }).limit(5L).collect(Collectors.toList());

            if (list1.size() >= j) {
                EntityIronGolem entityirongolem = this.eD();

                if (entityirongolem != null) {
                    list.forEach((entityvillager) -> {
                        entityvillager.b(i);
                    });
                }
            }
        }
    }

    private void b(long i) {
        this.br.setMemory(MemoryModuleType.GOLEM_LAST_SEEN_TIME, (Object) i);
    }

    private boolean c(long i) {
        Optional<Long> optional = this.br.getMemory(MemoryModuleType.GOLEM_LAST_SEEN_TIME);

        if (!optional.isPresent()) {
            return false;
        } else {
            Long olong = (Long) optional.get();

            return i - olong <= 600L;
        }
    }

    public boolean a(long i) {
        VillagerData villagerdata = this.getVillagerData();

        return villagerdata.getProfession() != VillagerProfession.NONE && villagerdata.getProfession() != VillagerProfession.NITWIT ? (!this.d(this.world.getTime()) ? false : !this.c(i)) : false;
    }

    @Nullable
    private EntityIronGolem eD() {
        BlockPosition blockposition = new BlockPosition(this);
        int i = 0;

        while (i < 10) {
            double d0 = (double) (this.world.random.nextInt(16) - 8);
            double d1 = (double) (this.world.random.nextInt(16) - 8);
            double d2 = 6.0D;
            int j = 0;

            while (true) {
                if (j >= -12) {
                    BlockPosition blockposition1 = blockposition.a(d0, d2 + (double) j, d1);

                    if (!this.world.getType(blockposition1).isAir() && !this.world.getType(blockposition1).getMaterial().isLiquid() || !this.world.getType(blockposition1.down()).getMaterial().f()) {
                        --j;
                        continue;
                    }

                    d2 += (double) j;
                }

                BlockPosition blockposition2 = blockposition.a(d0, d2, d1);
                EntityIronGolem entityirongolem = (EntityIronGolem) EntityTypes.IRON_GOLEM.b(this.world, (NBTTagCompound) null, (IChatBaseComponent) null, (EntityHuman) null, blockposition2, EnumMobSpawn.MOB_SUMMONED, false, false);

                if (entityirongolem != null) {
                    if (entityirongolem.a((GeneratorAccess) this.world, EnumMobSpawn.MOB_SUMMONED) && entityirongolem.a((IWorldReader) this.world)) {
                        this.world.addEntity(entityirongolem);
                        return entityirongolem;
                    }

                    entityirongolem.die();
                }

                ++i;
                break;
            }
        }

        return null;
    }

    @Override
    public void a(ReputationEvent reputationevent, Entity entity) {
        if (reputationevent == ReputationEvent.a) {
            this.bJ.a(entity.getUniqueID(), ReputationType.MAJOR_POSITIVE, 25);
        } else if (reputationevent == ReputationEvent.e) {
            this.bJ.a(entity.getUniqueID(), ReputationType.TRADING, 2);
        } else if (reputationevent == ReputationEvent.c) {
            this.bJ.a(entity.getUniqueID(), ReputationType.MINOR_NEGATIVE, 25);
        } else if (reputationevent == ReputationEvent.d) {
            this.bJ.a(entity.getUniqueID(), ReputationType.MAJOR_NEGATIVE, 25);
        }

    }

    @Override
    public int getExperience() {
        return this.bM;
    }

    public void setExperience(int i) {
        this.bM = i;
    }

    public long et() {
        return this.bN;
    }

    @Override
    protected void K() {
        super.K();
        PacketDebug.a(this);
    }

    @Override
    public void e(BlockPosition blockposition) {
        super.e(blockposition);
        this.br.setMemory(MemoryModuleType.LAST_SLEPT, (Object) MinecraftSerializableLong.a(this.world.getTime()));
    }

    private boolean d(long i) {
        Optional<MinecraftSerializableLong> optional = this.br.getMemory(MemoryModuleType.LAST_SLEPT);
        Optional<MinecraftSerializableLong> optional1 = this.br.getMemory(MemoryModuleType.LAST_WORKED_AT_POI);

        return optional.isPresent() && optional1.isPresent() ? i - ((MinecraftSerializableLong) optional.get()).a() < 24000L && i - ((MinecraftSerializableLong) optional1.get()).a() < 36000L : false;
    }
}
