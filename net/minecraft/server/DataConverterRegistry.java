package net.minecraft.server;

public class DataConverterRegistry {

    private static void a(DataConverterManager dataconvertermanager) {
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterEquipment()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.BLOCK_ENTITY, (IDataConverter) (new DataConverterSignText()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ITEM_INSTANCE, (IDataConverter) (new DataConverterMaterialId()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ITEM_INSTANCE, (IDataConverter) (new DataConverterPotionId()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ITEM_INSTANCE, (IDataConverter) (new DataConverterSpawnEgg()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterMinecart()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.BLOCK_ENTITY, (IDataConverter) (new DataConverterMobSpawner()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterUUID()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterHealth()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterSaddle()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterHanging()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterDropChances()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterRiding()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterArmorStand()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ITEM_INSTANCE, (IDataConverter) (new DataConverterBook()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ITEM_INSTANCE, (IDataConverter) (new DataConverterCookedFish()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterZombie()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.OPTIONS, (IDataConverter) (new DataConverterVBO()));
    }

    public static DataConverterManager a() {
        DataConverterManager dataconvertermanager = new DataConverterManager(512);

        WorldData.a(dataconvertermanager);
        EntityHuman.a(dataconvertermanager);
        ChunkRegionLoader.a(dataconvertermanager);
        ItemStack.a(dataconvertermanager);
        EntityArmorStand.a(dataconvertermanager);
        EntityArrow.a(dataconvertermanager);
        EntityBat.b(dataconvertermanager);
        EntityBlaze.b(dataconvertermanager);
        EntityCaveSpider.b(dataconvertermanager);
        EntityChicken.b(dataconvertermanager);
        EntityCow.b(dataconvertermanager);
        EntityCreeper.b(dataconvertermanager);
        EntityDragonFireball.a(dataconvertermanager);
        EntityEnderDragon.b(dataconvertermanager);
        EntityEnderman.b(dataconvertermanager);
        EntityEndermite.b(dataconvertermanager);
        EntityFallingBlock.a(dataconvertermanager);
        EntityLargeFireball.a(dataconvertermanager);
        EntityFireworks.a(dataconvertermanager);
        EntityGhast.b(dataconvertermanager);
        EntityGiantZombie.b(dataconvertermanager);
        EntityGuardian.b(dataconvertermanager);
        EntityHorse.b(dataconvertermanager);
        EntityItem.a(dataconvertermanager);
        EntityItemFrame.a(dataconvertermanager);
        EntityMagmaCube.b(dataconvertermanager);
        EntityMinecartChest.a(dataconvertermanager);
        EntityMinecartCommandBlock.a(dataconvertermanager);
        EntityMinecartFurnace.a(dataconvertermanager);
        EntityMinecartHopper.a(dataconvertermanager);
        EntityMinecartRideable.a(dataconvertermanager);
        EntityMinecartMobSpawner.a(dataconvertermanager);
        EntityMinecartTNT.a(dataconvertermanager);
        EntityInsentient.a(dataconvertermanager);
        EntityMonster.c(dataconvertermanager);
        EntityMushroomCow.c(dataconvertermanager);
        EntityOcelot.b(dataconvertermanager);
        EntityPig.b(dataconvertermanager);
        EntityPigZombie.b(dataconvertermanager);
        EntityRabbit.b(dataconvertermanager);
        EntitySheep.b(dataconvertermanager);
        EntityShulker.b(dataconvertermanager);
        EntitySilverfish.b(dataconvertermanager);
        EntitySkeleton.b(dataconvertermanager);
        EntitySlime.c(dataconvertermanager);
        EntitySmallFireball.a(dataconvertermanager);
        EntitySnowman.b(dataconvertermanager);
        EntitySnowball.a(dataconvertermanager);
        EntitySpectralArrow.b(dataconvertermanager);
        EntitySpider.d(dataconvertermanager);
        EntitySquid.b(dataconvertermanager);
        EntityEgg.a(dataconvertermanager);
        EntityEnderPearl.a(dataconvertermanager);
        EntityThrownExpBottle.a(dataconvertermanager);
        EntityPotion.a(dataconvertermanager);
        EntityTippedArrow.b(dataconvertermanager);
        EntityVillager.b(dataconvertermanager);
        EntityIronGolem.b(dataconvertermanager);
        EntityWitch.b(dataconvertermanager);
        EntityWither.b(dataconvertermanager);
        EntityWitherSkull.a(dataconvertermanager);
        EntityWolf.b(dataconvertermanager);
        EntityZombie.d(dataconvertermanager);
        TileEntityPiston.a(dataconvertermanager);
        TileEntityFlowerPot.a(dataconvertermanager);
        TileEntityFurnace.a(dataconvertermanager);
        TileEntityChest.a(dataconvertermanager);
        TileEntityDispenser.a(dataconvertermanager);
        TileEntityDropper.b(dataconvertermanager);
        TileEntityBrewingStand.a(dataconvertermanager);
        TileEntityHopper.a(dataconvertermanager);
        BlockJukeBox.a(dataconvertermanager);
        TileEntityMobSpawner.a(dataconvertermanager);
        a(dataconvertermanager);
        return dataconvertermanager;
    }

    public static NBTTagCompound a(DataConverter dataconverter, NBTTagCompound nbttagcompound, int i, String s) {
        if (nbttagcompound.hasKeyOfType(s, 10)) {
            nbttagcompound.set(s, dataconverter.a(DataConverterTypes.ITEM_INSTANCE, nbttagcompound.getCompound(s), i));
        }

        return nbttagcompound;
    }

    public static NBTTagCompound b(DataConverter dataconverter, NBTTagCompound nbttagcompound, int i, String s) {
        if (nbttagcompound.hasKeyOfType(s, 9)) {
            NBTTagList nbttaglist = nbttagcompound.getList(s, 10);

            for (int j = 0; j < nbttaglist.size(); ++j) {
                nbttaglist.a(j, dataconverter.a(DataConverterTypes.ITEM_INSTANCE, nbttaglist.get(j), i));
            }
        }

        return nbttagcompound;
    }
}
