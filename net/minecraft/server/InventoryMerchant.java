package net.minecraft.server;

public class InventoryMerchant implements IInventory {

    private final IMerchant merchant;
    private ItemStack[] itemsInSlots = new ItemStack[3];
    private final EntityHuman player;
    private MerchantRecipe recipe;
    private int e;

    public InventoryMerchant(EntityHuman entityhuman, IMerchant imerchant) {
        this.player = entityhuman;
        this.merchant = imerchant;
    }

    public int getSize() {
        return this.itemsInSlots.length;
    }

    public ItemStack getItem(int i) {
        return this.itemsInSlots[i];
    }

    public ItemStack splitStack(int i, int j) {
        if (i == 2 && this.itemsInSlots[i] != null) {
            return ContainerUtil.a(this.itemsInSlots, i, this.itemsInSlots[i].count);
        } else {
            ItemStack itemstack = ContainerUtil.a(this.itemsInSlots, i, j);

            if (itemstack != null && this.e(i)) {
                this.h();
            }

            return itemstack;
        }
    }

    private boolean e(int i) {
        return i == 0 || i == 1;
    }

    public ItemStack splitWithoutUpdate(int i) {
        return ContainerUtil.a(this.itemsInSlots, i);
    }

    public void setItem(int i, ItemStack itemstack) {
        this.itemsInSlots[i] = itemstack;
        if (itemstack != null && itemstack.count > this.getMaxStackSize()) {
            itemstack.count = this.getMaxStackSize();
        }

        if (this.e(i)) {
            this.h();
        }

    }

    public String getName() {
        return "mob.villager";
    }

    public boolean hasCustomName() {
        return false;
    }

    public IChatBaseComponent getScoreboardDisplayName() {
        return (IChatBaseComponent) (this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatMessage(this.getName(), new Object[0]));
    }

    public int getMaxStackSize() {
        return 64;
    }

    public boolean a(EntityHuman entityhuman) {
        return this.merchant.t_() == entityhuman;
    }

    public void startOpen(EntityHuman entityhuman) {}

    public void closeContainer(EntityHuman entityhuman) {}

    public boolean b(int i, ItemStack itemstack) {
        return true;
    }

    public void update() {
        this.h();
    }

    public void h() {
        this.recipe = null;
        ItemStack itemstack = this.itemsInSlots[0];
        ItemStack itemstack1 = this.itemsInSlots[1];

        if (itemstack == null) {
            itemstack = itemstack1;
            itemstack1 = null;
        }

        if (itemstack == null) {
            this.setItem(2, (ItemStack) null);
        } else {
            MerchantRecipeList merchantrecipelist = this.merchant.getOffers(this.player);

            if (merchantrecipelist != null) {
                MerchantRecipe merchantrecipe = merchantrecipelist.a(itemstack, itemstack1, this.e);

                if (merchantrecipe != null && !merchantrecipe.h()) {
                    this.recipe = merchantrecipe;
                    this.setItem(2, merchantrecipe.getBuyItem3().cloneItemStack());
                } else if (itemstack1 != null) {
                    merchantrecipe = merchantrecipelist.a(itemstack1, itemstack, this.e);
                    if (merchantrecipe != null && !merchantrecipe.h()) {
                        this.recipe = merchantrecipe;
                        this.setItem(2, merchantrecipe.getBuyItem3().cloneItemStack());
                    } else {
                        this.setItem(2, (ItemStack) null);
                    }
                } else {
                    this.setItem(2, (ItemStack) null);
                }
            }
        }

        this.merchant.a(this.getItem(2));
    }

    public MerchantRecipe getRecipe() {
        return this.recipe;
    }

    public void d(int i) {
        this.e = i;
        this.h();
    }

    public int getProperty(int i) {
        return 0;
    }

    public void setProperty(int i, int j) {}

    public int g() {
        return 0;
    }

    public void l() {
        for (int i = 0; i < this.itemsInSlots.length; ++i) {
            this.itemsInSlots[i] = null;
        }

    }
}
