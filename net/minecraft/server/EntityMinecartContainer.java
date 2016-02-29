package net.minecraft.server;

import java.util.Random;

public abstract class EntityMinecartContainer extends EntityMinecartAbstract implements ITileInventory, ILootable {

    private ItemStack[] items = new ItemStack[36];
    private boolean b = true;
    private MinecraftKey c;
    private long d;

    public EntityMinecartContainer(World world) {
        super(world);
    }

    public EntityMinecartContainer(World world, double d0, double d1, double d2) {
        super(world, d0, d1, d2);
    }

    public void a(DamageSource damagesource) {
        super.a(damagesource);
        if (this.world.getGameRules().getBoolean("doEntityDrops")) {
            InventoryUtils.dropEntity(this.world, this, this);
        }

    }

    public ItemStack getItem(int i) {
        this.f((EntityHuman) null);
        return this.items[i];
    }

    public ItemStack splitStack(int i, int j) {
        this.f((EntityHuman) null);
        return ContainerUtil.a(this.items, i, j);
    }

    public ItemStack splitWithoutUpdate(int i) {
        this.f((EntityHuman) null);
        if (this.items[i] != null) {
            ItemStack itemstack = this.items[i];

            this.items[i] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    public void setItem(int i, ItemStack itemstack) {
        this.f((EntityHuman) null);
        this.items[i] = itemstack;
        if (itemstack != null && itemstack.count > this.getMaxStackSize()) {
            itemstack.count = this.getMaxStackSize();
        }

    }

    public void update() {}

    public boolean a(EntityHuman entityhuman) {
        return this.dead ? false : entityhuman.h(this) <= 64.0D;
    }

    public void startOpen(EntityHuman entityhuman) {}

    public void closeContainer(EntityHuman entityhuman) {}

    public boolean b(int i, ItemStack itemstack) {
        return true;
    }

    public String getName() {
        return this.hasCustomName() ? this.getCustomName() : "container.minecart";
    }

    public int getMaxStackSize() {
        return 64;
    }

    public Entity c(int i) {
        this.b = false;
        return super.c(i);
    }

    public void die() {
        if (this.b) {
            InventoryUtils.dropEntity(this.world, this, this);
        }

        super.die();
    }

    public void b(boolean flag) {
        this.b = flag;
    }

    protected void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        if (this.c != null) {
            nbttagcompound.setString("LootTable", this.c.toString());
            if (this.d != 0L) {
                nbttagcompound.setLong("LootTableSeed", this.d);
            }
        } else {
            NBTTagList nbttaglist = new NBTTagList();

            for (int i = 0; i < this.items.length; ++i) {
                if (this.items[i] != null) {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();

                    nbttagcompound1.setByte("Slot", (byte) i);
                    this.items[i].save(nbttagcompound1);
                    nbttaglist.add(nbttagcompound1);
                }
            }

            nbttagcompound.set("Items", nbttaglist);
        }

    }

    protected void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.items = new ItemStack[this.getSize()];
        if (nbttagcompound.hasKeyOfType("LootTable", 8)) {
            this.c = new MinecraftKey(nbttagcompound.getString("LootTable"));
            this.d = nbttagcompound.getLong("LootTableSeed");
        } else {
            NBTTagList nbttaglist = nbttagcompound.getList("Items", 10);

            for (int i = 0; i < nbttaglist.size(); ++i) {
                NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
                int j = nbttagcompound1.getByte("Slot") & 255;

                if (j >= 0 && j < this.items.length) {
                    this.items[j] = ItemStack.createStack(nbttagcompound1);
                }
            }
        }

    }

    public boolean a(EntityHuman entityhuman, ItemStack itemstack, EnumHand enumhand) {
        if (!this.world.isClientSide) {
            entityhuman.openContainer(this);
        }

        return true;
    }

    protected void r() {
        float f = 0.98F;

        if (this.c == null) {
            int i = 15 - Container.b((IInventory) this);

            f += (float) i * 0.001F;
        }

        this.motX *= (double) f;
        this.motY *= 0.0D;
        this.motZ *= (double) f;
    }

    public int getProperty(int i) {
        return 0;
    }

    public void setProperty(int i, int j) {}

    public int g() {
        return 0;
    }

    public boolean x_() {
        return false;
    }

    public void a(ChestLock chestlock) {}

    public ChestLock y_() {
        return ChestLock.a;
    }

    public void f(EntityHuman entityhuman) {
        if (this.c != null) {
            LootTable loottable = this.world.ak().a(this.c);

            this.c = null;
            Random random;

            if (this.d == 0L) {
                random = new Random();
            } else {
                random = new Random(this.d);
            }

            LootTableInfo.a loottableinfo_a = new LootTableInfo.a((WorldServer) this.world);

            if (entityhuman != null) {
                loottableinfo_a.a(entityhuman.db());
            }

            loottable.a(this, random, loottableinfo_a.a());
        }

    }

    public void l() {
        this.f((EntityHuman) null);

        for (int i = 0; i < this.items.length; ++i) {
            this.items[i] = null;
        }

    }

    public void a(MinecraftKey minecraftkey, long i) {
        this.c = minecraftkey;
        this.d = i;
    }

    public MinecraftKey b() {
        return this.c;
    }
}
