package net.minecraft.server;

import java.util.Arrays;

public class TileEntityBrewingStand extends TileEntityContainer implements ITickable, IWorldInventory {

    private static final int[] a = new int[] { 3};
    private static final int[] f = new int[] { 0, 1, 2, 3};
    private static final int[] g = new int[] { 0, 1, 2, 4};
    private ItemStack[] items = new ItemStack[5];
    private int brewTime;
    private boolean[] j;
    private Item k;
    private String l;
    private int m;

    public TileEntityBrewingStand() {}

    public String getName() {
        return this.hasCustomName() ? this.l : "container.brewing";
    }

    public boolean hasCustomName() {
        return this.l != null && !this.l.isEmpty();
    }

    public void a(String s) {
        this.l = s;
    }

    public int getSize() {
        return this.items.length;
    }

    public void c() {
        if (this.m <= 0 && this.items[4] != null && this.items[4].getItem() == Items.BLAZE_POWDER) {
            this.m = 20;
            --this.items[4].count;
            if (this.items[4].count <= 0) {
                this.items[4] = null;
            }

            this.update();
        }

        boolean flag = this.n();
        boolean flag1 = this.brewTime > 0;

        if (flag1) {
            --this.brewTime;
            boolean flag2 = this.brewTime == 0;

            if (flag2 && flag) {
                this.o();
                this.update();
            } else if (!flag) {
                this.brewTime = 0;
                this.update();
            } else if (this.k != this.items[3].getItem()) {
                this.brewTime = 0;
                this.update();
            }
        } else if (flag && this.m > 0) {
            --this.m;
            this.brewTime = 400;
            this.k = this.items[3].getItem();
            this.update();
        }

        if (!this.world.isClientSide) {
            boolean[] aboolean = this.m();

            if (!Arrays.equals(aboolean, this.j)) {
                this.j = aboolean;
                IBlockData iblockdata = this.world.getType(this.getPosition());

                if (!(iblockdata.getBlock() instanceof BlockBrewingStand)) {
                    return;
                }

                for (int i = 0; i < BlockBrewingStand.HAS_BOTTLE.length; ++i) {
                    iblockdata = iblockdata.set(BlockBrewingStand.HAS_BOTTLE[i], Boolean.valueOf(aboolean[i]));
                }

                this.world.setTypeAndData(this.position, iblockdata, 2);
            }
        }

    }

    public boolean[] m() {
        boolean[] aboolean = new boolean[3];

        for (int i = 0; i < 3; ++i) {
            if (this.items[i] != null) {
                aboolean[i] = true;
            }
        }

        return aboolean;
    }

    private boolean n() {
        if (this.items[3] != null && this.items[3].count > 0) {
            ItemStack itemstack = this.items[3];

            if (!PotionBrewer.a(itemstack)) {
                return false;
            } else {
                for (int i = 0; i < 3; ++i) {
                    ItemStack itemstack1 = this.items[i];

                    if (itemstack1 != null && PotionBrewer.a(itemstack1, itemstack)) {
                        return true;
                    }
                }

                return false;
            }
        } else {
            return false;
        }
    }

    private void o() {
        ItemStack itemstack = this.items[3];

        for (int i = 0; i < 3; ++i) {
            this.items[i] = PotionBrewer.d(itemstack, this.items[i]);
        }

        --itemstack.count;
        BlockPosition blockposition = this.getPosition();

        if (itemstack.getItem().r()) {
            ItemStack itemstack1 = new ItemStack(itemstack.getItem().q());

            if (itemstack.count <= 0) {
                itemstack = itemstack1;
            } else {
                InventoryUtils.dropItem(this.world, (double) blockposition.getX(), (double) blockposition.getY(), (double) blockposition.getZ(), itemstack1);
            }
        }

        if (itemstack.count <= 0) {
            itemstack = null;
        }

        this.items[3] = itemstack;
        this.world.triggerEffect(1035, blockposition, 0);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.getList("Items", 10);

        this.items = new ItemStack[this.getSize()];

        for (int i = 0; i < nbttaglist.size(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.items.length) {
                this.items[b0] = ItemStack.createStack(nbttagcompound1);
            }
        }

        this.brewTime = nbttagcompound.getShort("BrewTime");
        if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
            this.l = nbttagcompound.getString("CustomName");
        }

        this.m = nbttagcompound.getByte("Fuel");
    }

    public void save(NBTTagCompound nbttagcompound) {
        super.save(nbttagcompound);
        nbttagcompound.setShort("BrewTime", (short) this.brewTime);
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
        if (this.hasCustomName()) {
            nbttagcompound.setString("CustomName", this.l);
        }

        nbttagcompound.setByte("Fuel", (byte) this.m);
    }

    public ItemStack getItem(int i) {
        return i >= 0 && i < this.items.length ? this.items[i] : null;
    }

    public ItemStack splitStack(int i, int j) {
        return ContainerUtil.a(this.items, i, j);
    }

    public ItemStack splitWithoutUpdate(int i) {
        return ContainerUtil.a(this.items, i);
    }

    public void setItem(int i, ItemStack itemstack) {
        if (i >= 0 && i < this.items.length) {
            this.items[i] = itemstack;
        }

    }

    public int getMaxStackSize() {
        return 64;
    }

    public boolean a(EntityHuman entityhuman) {
        return this.world.getTileEntity(this.position) != this ? false : entityhuman.e((double) this.position.getX() + 0.5D, (double) this.position.getY() + 0.5D, (double) this.position.getZ() + 0.5D) <= 64.0D;
    }

    public void startOpen(EntityHuman entityhuman) {}

    public void closeContainer(EntityHuman entityhuman) {}

    public boolean b(int i, ItemStack itemstack) {
        if (i == 3) {
            return PotionBrewer.a(itemstack);
        } else {
            Item item = itemstack.getItem();

            return i == 4 ? item == Items.BLAZE_POWDER : item == Items.POTION || item == Items.SPLASH_POTION || item == Items.LINGERING_POTION || item == Items.GLASS_BOTTLE;
        }
    }

    public int[] getSlotsForFace(EnumDirection enumdirection) {
        return enumdirection == EnumDirection.UP ? TileEntityBrewingStand.a : (enumdirection == EnumDirection.DOWN ? TileEntityBrewingStand.f : TileEntityBrewingStand.g);
    }

    public boolean canPlaceItemThroughFace(int i, ItemStack itemstack, EnumDirection enumdirection) {
        return this.b(i, itemstack);
    }

    public boolean canTakeItemThroughFace(int i, ItemStack itemstack, EnumDirection enumdirection) {
        return i == 3 ? itemstack.getItem() == Items.GLASS_BOTTLE : true;
    }

    public String getContainerName() {
        return "minecraft:brewing_stand";
    }

    public Container createContainer(PlayerInventory playerinventory, EntityHuman entityhuman) {
        return new ContainerBrewingStand(playerinventory, this);
    }

    public int getProperty(int i) {
        switch (i) {
        case 0:
            return this.brewTime;

        case 1:
            return this.m;

        default:
            return 0;
        }
    }

    public void setProperty(int i, int j) {
        switch (i) {
        case 0:
            this.brewTime = j;
            break;

        case 1:
            this.m = j;
        }

    }

    public int g() {
        return 2;
    }

    public void l() {
        Arrays.fill(this.items, (Object) null);
    }
}
