package net.minecraft.server;

import com.google.common.base.Predicates;
import java.util.List;

final class DispenseBehaviorArmor extends DispenseBehaviorItem {

    DispenseBehaviorArmor() {}

    protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
        BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.b(isourceblock.f()));
        int i = blockposition.getX();
        int j = blockposition.getY();
        int k = blockposition.getZ();
        AxisAlignedBB axisalignedbb = new AxisAlignedBB((double) i, (double) j, (double) k, (double) (i + 1), (double) (j + 1), (double) (k + 1));
        List list = isourceblock.i().a(EntityLiving.class, axisalignedbb, Predicates.and(IEntitySelector.d, new EntitySelectorEquipable(itemstack)));

        if (list.size() > 0) {
            EntityLiving entityliving = (EntityLiving) list.get(0);
            int l = entityliving instanceof EntityHuman ? 1 : 0;
            int i1 = EntityInsentient.c(itemstack);
            ItemStack itemstack1 = itemstack.cloneItemStack();

            itemstack1.count = 1;
            entityliving.setEquipment(i1 - l, itemstack1);
            if (entityliving instanceof EntityInsentient) {
                ((EntityInsentient) entityliving).a(i1, 2.0F);
            }

            --itemstack.count;
            return itemstack;
        } else {
            return super.b(isourceblock, itemstack);
        }
    }
}
