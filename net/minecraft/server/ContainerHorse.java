package net.minecraft.server;

import javax.annotation.Nullable;

public class ContainerHorse extends Container {

    private final IInventory a;
    private final EntityHorse f;

    public ContainerHorse(IInventory iinventory, final IInventory iinventory1, final EntityHorse entityhorse, EntityHuman entityhuman) {
        this.a = iinventory1;
        this.f = entityhorse;
        boolean flag = true;

        iinventory1.startOpen(entityhuman);
        boolean flag1 = true;

        this.a(new Slot(iinventory1, 0, 8, 18) {
            public boolean isAllowed(@Nullable ItemStack itemstack) {
                return super.isAllowed(itemstack) && itemstack.getItem() == Items.SADDLE && !this.hasItem();
            }
        });
        this.a(new Slot(iinventory1, 1, 8, 36) {
            public boolean isAllowed(@Nullable ItemStack itemstack) {
                return super.isAllowed(itemstack) && entityhorse.getType().j() && EnumHorseArmor.b(itemstack.getItem());
            }
        });
        int i;
        int j;

        if (entityhorse.hasChest()) {
            for (i = 0; i < 3; ++i) {
                for (j = 0; j < 5; ++j) {
                    this.a(new Slot(iinventory1, 2 + j + i * 5, 80 + j * 18, 18 + i * 18));
                }
            }
        }

        for (i = 0; i < 3; ++i) {
            for (j = 0; j < 9; ++j) {
                this.a(new Slot(iinventory, j + i * 9 + 9, 8 + j * 18, 102 + i * 18 + -18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.a(new Slot(iinventory, i, 8 + i * 18, 142));
        }

    }

    public boolean a(EntityHuman entityhuman) {
        return this.a.a(entityhuman) && this.f.isAlive() && this.f.g((Entity) entityhuman) < 8.0F;
    }

    @Nullable
    public ItemStack b(EntityHuman entityhuman, int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.c.get(i);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();

            itemstack = itemstack1.cloneItemStack();
            if (i < this.a.getSize()) {
                if (!this.a(itemstack1, this.a.getSize(), this.c.size(), true)) {
                    return null;
                }
            } else if (this.getSlot(1).isAllowed(itemstack1) && !this.getSlot(1).hasItem()) {
                if (!this.a(itemstack1, 1, 2, false)) {
                    return null;
                }
            } else if (this.getSlot(0).isAllowed(itemstack1)) {
                if (!this.a(itemstack1, 0, 1, false)) {
                    return null;
                }
            } else if (this.a.getSize() <= 2 || !this.a(itemstack1, 2, this.a.getSize(), false)) {
                return null;
            }

            if (itemstack1.count == 0) {
                slot.set((ItemStack) null);
            } else {
                slot.f();
            }
        }

        return itemstack;
    }

    public void b(EntityHuman entityhuman) {
        super.b(entityhuman);
        this.a.closeContainer(entityhuman);
    }
}
