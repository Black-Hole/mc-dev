package net.minecraft.server;

public class EnchantmentOxygen extends Enchantment {

    public EnchantmentOxygen(Enchantment.Rarity enchantment_rarity, EnumItemSlot... aenumitemslot) {
        super(enchantment_rarity, EnchantmentSlotType.ARMOR_HEAD, aenumitemslot);
    }

    @Override
    public int a(int i) {
        return 10 * i;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
