package net.minecraft.server;

import java.util.Random;

public class DataConverterZombie implements IDataConverter {

    private static final Random a = new Random();

    public DataConverterZombie() {}

    public int a() {
        return 502;
    }

    public NBTTagCompound a(NBTTagCompound nbttagcompound) {
        if ("Zombie".equals(nbttagcompound.getString("id")) && nbttagcompound.getBoolean("IsVillager")) {
            if (!nbttagcompound.hasKeyOfType("ZombieType", 99)) {
                EnumZombieType enumzombietype = null;

                if (nbttagcompound.hasKeyOfType("VillagerProfession", 99)) {
                    try {
                        enumzombietype = EnumZombieType.a(nbttagcompound.getInt("VillagerProfession") + 1);
                    } catch (RuntimeException runtimeexception) {
                        ;
                    }
                }

                if (enumzombietype == null) {
                    enumzombietype = EnumZombieType.a(DataConverterZombie.a.nextInt(5) + 1);
                }

                nbttagcompound.setInt("ZombieType", enumzombietype.a());
            }

            nbttagcompound.remove("IsVillager");
        }

        return nbttagcompound;
    }
}
