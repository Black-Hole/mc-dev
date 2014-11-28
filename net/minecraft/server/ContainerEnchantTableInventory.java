package net.minecraft.server;

class ContainerEnchantTableInventory extends InventorySubcontainer {

    final ContainerEnchantTable enchantTable;

    ContainerEnchantTableInventory(ContainerEnchantTable containerenchanttable, String s, boolean flag, int i) {
        super(s, flag, i);
        this.enchantTable = containerenchanttable;
    }

    public int getMaxStackSize() {
        return 64;
    }

    public void update() {
        super.update();
        this.enchantTable.a((IInventory) this);
    }
}
