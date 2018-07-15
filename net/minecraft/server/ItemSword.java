package net.minecraft.server;

import com.google.common.collect.Multimap;

public class ItemSword extends ItemToolMaterial {

    private final float a;
    private final float b;

    public ItemSword(ToolMaterial toolmaterial, int i, float f, Item.Info item_info) {
        super(toolmaterial, item_info);
        this.b = f;
        this.a = (float) i + toolmaterial.c();
    }

    public float d() {
        return this.a;
    }

    public boolean a(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman) {
        return !entityhuman.u();
    }

    public float getDestroySpeed(ItemStack itemstack, IBlockData iblockdata) {
        Block block = iblockdata.getBlock();

        if (block == Blocks.COBWEB) {
            return 15.0F;
        } else {
            Material material = iblockdata.getMaterial();

            return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.CORAL && !iblockdata.a(TagsBlock.E) && material != Material.PUMPKIN ? 1.0F : 1.5F;
        }
    }

    public boolean a(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        itemstack.damage(1, entityliving1);
        return true;
    }

    public boolean a(ItemStack itemstack, World world, IBlockData iblockdata, BlockPosition blockposition, EntityLiving entityliving) {
        if (iblockdata.e(world, blockposition) != 0.0F) {
            itemstack.damage(2, entityliving);
        }

        return true;
    }

    public boolean canDestroySpecialBlock(IBlockData iblockdata) {
        return iblockdata.getBlock() == Blocks.COBWEB;
    }

    public Multimap<String, AttributeModifier> a(EnumItemSlot enumitemslot) {
        Multimap multimap = super.a(enumitemslot);

        if (enumitemslot == EnumItemSlot.MAINHAND) {
            multimap.put(GenericAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ItemSword.h, "Weapon modifier", (double) this.a, 0));
            multimap.put(GenericAttributes.g.getName(), new AttributeModifier(ItemSword.i, "Weapon modifier", (double) this.b, 0));
        }

        return multimap;
    }
}
