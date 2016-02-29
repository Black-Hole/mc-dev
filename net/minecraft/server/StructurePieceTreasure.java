package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class StructurePieceTreasure extends WeightedRandom.WeightedRandomChoice {

    private ItemStack b;
    private int c;
    private int d;

    public StructurePieceTreasure(Item item, int i, int j, int k, int l) {
        super(l);
        this.b = new ItemStack(item, 1, i);
        this.c = j;
        this.d = k;
    }

    public static void a(Random random, List<StructurePieceTreasure> list, TileEntityDispenser tileentitydispenser, int i) {
        for (int j = 0; j < i; ++j) {
            StructurePieceTreasure structurepiecetreasure = (StructurePieceTreasure) WeightedRandom.a(random, list);
            int k = structurepiecetreasure.c + random.nextInt(structurepiecetreasure.d - structurepiecetreasure.c + 1);

            if (structurepiecetreasure.b.getMaxStackSize() >= k) {
                ItemStack itemstack = structurepiecetreasure.b.cloneItemStack();

                itemstack.count = k;
                tileentitydispenser.setItem(random.nextInt(tileentitydispenser.getSize()), itemstack);
            } else {
                for (int l = 0; l < k; ++l) {
                    ItemStack itemstack1 = structurepiecetreasure.b.cloneItemStack();

                    itemstack1.count = 1;
                    tileentitydispenser.setItem(random.nextInt(tileentitydispenser.getSize()), itemstack1);
                }
            }
        }

    }
}
