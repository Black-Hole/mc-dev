package net.minecraft.server;

public class InventoryCrafting implements IInventory {

    private final ItemStack[] items;
    private final int b;
    private final int c;
    private final Container d;

    public InventoryCrafting(Container container, int i, int j) {
        int k = i * j;

        this.items = new ItemStack[k];
        this.d = container;
        this.b = i;
        this.c = j;
    }

    public int getSize() {
        return this.items.length;
    }

    public ItemStack getItem(int i) {
        return i >= this.getSize() ? null : this.items[i];
    }

    public ItemStack c(int i, int j) {
        return i >= 0 && i < this.b && j >= 0 && j <= this.c ? this.getItem(i + j * this.b) : null;
    }

    public String getName() {
        return "container.crafting";
    }

    public boolean hasCustomName() {
        return false;
    }

    public IChatBaseComponent getScoreboardDisplayName() {
        return (IChatBaseComponent) (this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatMessage(this.getName(), new Object[0]));
    }

    public ItemStack splitWithoutUpdate(int i) {
        return ContainerUtil.a(this.items, i);
    }

    public ItemStack splitStack(int i, int j) {
        ItemStack itemstack = ContainerUtil.a(this.items, i, j);

        if (itemstack != null) {
            this.d.a((IInventory) this);
        }

        return itemstack;
    }

    public void setItem(int i, ItemStack itemstack) {
        this.items[i] = itemstack;
        this.d.a((IInventory) this);
    }

    public int getMaxStackSize() {
        return 64;
    }

    public void update() {}

    public boolean a(EntityHuman entityhuman) {
        return true;
    }

    public void startOpen(EntityHuman entityhuman) {}

    public void closeContainer(EntityHuman entityhuman) {}

    public boolean b(int i, ItemStack itemstack) {
        return true;
    }

    public int getProperty(int i) {
        return 0;
    }

    public void setProperty(int i, int j) {}

    public int g() {
        return 0;
    }

    public void l() {
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i] = null;
        }

    }

    public int h() {
        return this.c;
    }

    public int i() {
        return this.b;
    }
}
