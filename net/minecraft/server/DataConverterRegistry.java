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
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterGuardian()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterSkeleton()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterZombieType()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterHorse()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.BLOCK_ENTITY, (IDataConverter) (new DataConverterTileEntity()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterEntity()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ITEM_INSTANCE, (IDataConverter) (new DataConverterBanner()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ITEM_INSTANCE, (IDataConverter) (new DataConverterPotionWater()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterShulker()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ITEM_INSTANCE, (IDataConverter) (new DataConverterShulkerBoxItem()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.BLOCK_ENTITY, (IDataConverter) (new DataConverterShulkerBoxBlock()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.OPTIONS, (IDataConverter) (new DataConverterLang()));
    }

    public static DataConverterManager a() {
        DataConverterManager dataconvertermanager = new DataConverterManager(819);

        WorldData.a(dataconvertermanager);
        EntityHuman.b(dataconvertermanager);
        ChunkRegionLoader.a(dataconvertermanager);
        ItemStack.a(dataconvertermanager);
        DefinedStructure.a(dataconvertermanager);
        Entity.a(dataconvertermanager);
        EntityArmorStand.b(dataconvertermanager);
        EntityArrow.b(dataconvertermanager);
        EntityBat.b(dataconvertermanager);
        EntityBlaze.b(dataconvertermanager);
        EntityCaveSpider.b(dataconvertermanager);
        EntityChicken.b(dataconvertermanager);
        EntityCow.b(dataconvertermanager);
        EntityCreeper.b(dataconvertermanager);
        EntityHorseDonkey.b(dataconvertermanager);
        EntityDragonFireball.b(dataconvertermanager);
        EntityGuardianElder.b(dataconvertermanager);
        EntityEnderDragon.b(dataconvertermanager);
        EntityEnderman.b(dataconvertermanager);
        EntityEndermite.b(dataconvertermanager);
        EntityEvoker.b(dataconvertermanager);
        EntityFallingBlock.b(dataconvertermanager);
        EntityFireworks.b(dataconvertermanager);
        EntityGhast.b(dataconvertermanager);
        EntityGiantZombie.b(dataconvertermanager);
        EntityGuardian.c(dataconvertermanager);
        EntityHorse.b(dataconvertermanager);
        EntityZombieHusk.b(dataconvertermanager);
        EntityItem.b(dataconvertermanager);
        EntityItemFrame.b(dataconvertermanager);
        EntityLargeFireball.b(dataconvertermanager);
        EntityMagmaCube.b(dataconvertermanager);
        EntityMinecartChest.b(dataconvertermanager);
        EntityMinecartCommandBlock.b(dataconvertermanager);
        EntityMinecartFurnace.b(dataconvertermanager);
        EntityMinecartHopper.b(dataconvertermanager);
        EntityMinecartRideable.b(dataconvertermanager);
        EntityMinecartMobSpawner.b(dataconvertermanager);
        EntityMinecartTNT.b(dataconvertermanager);
        EntityHorseMule.b(dataconvertermanager);
        EntityMushroomCow.c(dataconvertermanager);
        EntityOcelot.b(dataconvertermanager);
        EntityPig.b(dataconvertermanager);
        EntityPigZombie.b(dataconvertermanager);
        EntityRabbit.b(dataconvertermanager);
        EntitySheep.b(dataconvertermanager);
        EntityShulker.b(dataconvertermanager);
        EntitySilverfish.b(dataconvertermanager);
        EntitySkeleton.b(dataconvertermanager);
        EntityHorseSkeleton.b(dataconvertermanager);
        EntitySlime.c(dataconvertermanager);
        EntitySmallFireball.b(dataconvertermanager);
        EntitySnowman.b(dataconvertermanager);
        EntitySnowball.b(dataconvertermanager);
        EntitySpectralArrow.c(dataconvertermanager);
        EntitySpider.c(dataconvertermanager);
        EntitySquid.b(dataconvertermanager);
        EntitySkeletonStray.b(dataconvertermanager);
        EntityEgg.b(dataconvertermanager);
        EntityEnderPearl.b(dataconvertermanager);
        EntityThrownExpBottle.b(dataconvertermanager);
        EntityPotion.b(dataconvertermanager);
        EntityTippedArrow.c(dataconvertermanager);
        EntityVex.b(dataconvertermanager);
        EntityVillager.b(dataconvertermanager);
        EntityIronGolem.b(dataconvertermanager);
        EntityVindicator.b(dataconvertermanager);
        EntityWitch.b(dataconvertermanager);
        EntityWither.b(dataconvertermanager);
        EntitySkeletonWither.b(dataconvertermanager);
        EntityWitherSkull.b(dataconvertermanager);
        EntityWolf.b(dataconvertermanager);
        EntityZombie.c(dataconvertermanager);
        EntityHorseZombie.b(dataconvertermanager);
        EntityZombieVillager.b(dataconvertermanager);
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
        TileEntityShulkerBox.a(dataconvertermanager);
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
