package net.minecraft.server;

import com.mojang.authlib.GameProfile;
import java.io.PrintStream;
import java.util.Random;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DispenserRegistry {

    public static final PrintStream a = System.out;
    private static boolean b = false;
    private static final Logger c = LogManager.getLogger();

    public static boolean a() {
        return DispenserRegistry.b;
    }

    static void b() {
        BlockDispenser.REGISTRY.a(Items.ARROW, new DispenseBehaviorProjectile() {
            protected IProjectile a(World world, IPosition iposition, ItemStack itemstack) {
                EntityTippedArrow entitytippedarrow = new EntityTippedArrow(world, iposition.getX(), iposition.getY(), iposition.getZ());

                entitytippedarrow.fromPlayer = EntityArrow.PickupStatus.ALLOWED;
                return entitytippedarrow;
            }
        });
        BlockDispenser.REGISTRY.a(Items.TIPPED_ARROW, new DispenseBehaviorProjectile() {
            protected IProjectile a(World world, IPosition iposition, ItemStack itemstack) {
                EntityTippedArrow entitytippedarrow = new EntityTippedArrow(world, iposition.getX(), iposition.getY(), iposition.getZ());

                entitytippedarrow.a(itemstack);
                entitytippedarrow.fromPlayer = EntityArrow.PickupStatus.ALLOWED;
                return entitytippedarrow;
            }
        });
        BlockDispenser.REGISTRY.a(Items.SPECTRAL_ARROW, new DispenseBehaviorProjectile() {
            protected IProjectile a(World world, IPosition iposition, ItemStack itemstack) {
                EntitySpectralArrow entityspectralarrow = new EntitySpectralArrow(world, iposition.getX(), iposition.getY(), iposition.getZ());

                entityspectralarrow.fromPlayer = EntityArrow.PickupStatus.ALLOWED;
                return entityspectralarrow;
            }
        });
        BlockDispenser.REGISTRY.a(Items.EGG, new DispenseBehaviorProjectile() {
            protected IProjectile a(World world, IPosition iposition, ItemStack itemstack) {
                return new EntityEgg(world, iposition.getX(), iposition.getY(), iposition.getZ());
            }
        });
        BlockDispenser.REGISTRY.a(Items.SNOWBALL, new DispenseBehaviorProjectile() {
            protected IProjectile a(World world, IPosition iposition, ItemStack itemstack) {
                return new EntitySnowball(world, iposition.getX(), iposition.getY(), iposition.getZ());
            }
        });
        BlockDispenser.REGISTRY.a(Items.EXPERIENCE_BOTTLE, new DispenseBehaviorProjectile() {
            protected IProjectile a(World world, IPosition iposition, ItemStack itemstack) {
                return new EntityThrownExpBottle(world, iposition.getX(), iposition.getY(), iposition.getZ());
            }

            protected float a() {
                return super.a() * 0.5F;
            }

            protected float getPower() {
                return super.getPower() * 1.25F;
            }
        });
        BlockDispenser.REGISTRY.a(Items.SPLASH_POTION, new IDispenseBehavior() {
            public ItemStack a(ISourceBlock isourceblock, final ItemStack itemstack) {
                return (new DispenseBehaviorProjectile() {
                    protected IProjectile a(World world, IPosition iposition, ItemStack itemstack) {
                        return new EntityPotion(world, iposition.getX(), iposition.getY(), iposition.getZ(), itemstack1.cloneItemStack());
                    }

                    protected float a() {
                        return super.a() * 0.5F;
                    }

                    protected float getPower() {
                        return super.getPower() * 1.25F;
                    }
                }).a(isourceblock, itemstack);
            }
        });
        BlockDispenser.REGISTRY.a(Items.LINGERING_POTION, new IDispenseBehavior() {
            public ItemStack a(ISourceBlock isourceblock, final ItemStack itemstack) {
                return (new DispenseBehaviorProjectile() {
                    protected IProjectile a(World world, IPosition iposition, ItemStack itemstack) {
                        return new EntityPotion(world, iposition.getX(), iposition.getY(), iposition.getZ(), itemstack1.cloneItemStack());
                    }

                    protected float a() {
                        return super.a() * 0.5F;
                    }

                    protected float getPower() {
                        return super.getPower() * 1.25F;
                    }
                }).a(isourceblock, itemstack);
            }
        });
        BlockDispenser.REGISTRY.a(Items.SPAWN_EGG, new DispenseBehaviorItem() {
            public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
                EnumDirection enumdirection = BlockDispenser.e(isourceblock.f());
                double d0 = isourceblock.getX() + (double) enumdirection.getAdjacentX();
                double d1 = (double) ((float) isourceblock.getBlockPosition().getY() + 0.2F);
                double d2 = isourceblock.getZ() + (double) enumdirection.getAdjacentZ();
                Entity entity = ItemMonsterEgg.a(isourceblock.getWorld(), ItemMonsterEgg.h(itemstack), d0, d1, d2);

                if (entity instanceof EntityLiving && itemstack.hasName()) {
                    entity.setCustomName(itemstack.getName());
                }

                ItemMonsterEgg.a(isourceblock.getWorld(), (EntityHuman) null, itemstack, entity);
                itemstack.cloneAndSubtract(1);
                return itemstack;
            }
        });
        BlockDispenser.REGISTRY.a(Items.FIREWORKS, new DispenseBehaviorItem() {
            public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
                EnumDirection enumdirection = BlockDispenser.e(isourceblock.f());
                double d0 = isourceblock.getX() + (double) enumdirection.getAdjacentX();
                double d1 = (double) ((float) isourceblock.getBlockPosition().getY() + 0.2F);
                double d2 = isourceblock.getZ() + (double) enumdirection.getAdjacentZ();
                EntityFireworks entityfireworks = new EntityFireworks(isourceblock.getWorld(), d0, d1, d2, itemstack);

                isourceblock.getWorld().addEntity(entityfireworks);
                itemstack.cloneAndSubtract(1);
                return itemstack;
            }

            protected void a(ISourceBlock isourceblock) {
                isourceblock.getWorld().triggerEffect(1004, isourceblock.getBlockPosition(), 0);
            }
        });
        BlockDispenser.REGISTRY.a(Items.FIRE_CHARGE, new DispenseBehaviorItem() {
            public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
                EnumDirection enumdirection = BlockDispenser.e(isourceblock.f());
                IPosition iposition = BlockDispenser.a(isourceblock);
                double d0 = iposition.getX() + (double) ((float) enumdirection.getAdjacentX() * 0.3F);
                double d1 = iposition.getY() + (double) ((float) enumdirection.getAdjacentY() * 0.3F);
                double d2 = iposition.getZ() + (double) ((float) enumdirection.getAdjacentZ() * 0.3F);
                World world = isourceblock.getWorld();
                Random random = world.random;
                double d3 = random.nextGaussian() * 0.05D + (double) enumdirection.getAdjacentX();
                double d4 = random.nextGaussian() * 0.05D + (double) enumdirection.getAdjacentY();
                double d5 = random.nextGaussian() * 0.05D + (double) enumdirection.getAdjacentZ();

                world.addEntity(new EntitySmallFireball(world, d0, d1, d2, d3, d4, d5));
                itemstack.cloneAndSubtract(1);
                return itemstack;
            }

            protected void a(ISourceBlock isourceblock) {
                isourceblock.getWorld().triggerEffect(1018, isourceblock.getBlockPosition(), 0);
            }
        });
        BlockDispenser.REGISTRY.a(Items.aG, new DispenserRegistry.a(EntityBoat.EnumBoatType.OAK));
        BlockDispenser.REGISTRY.a(Items.aH, new DispenserRegistry.a(EntityBoat.EnumBoatType.SPRUCE));
        BlockDispenser.REGISTRY.a(Items.aI, new DispenserRegistry.a(EntityBoat.EnumBoatType.BIRCH));
        BlockDispenser.REGISTRY.a(Items.aJ, new DispenserRegistry.a(EntityBoat.EnumBoatType.JUNGLE));
        BlockDispenser.REGISTRY.a(Items.aL, new DispenserRegistry.a(EntityBoat.EnumBoatType.DARK_OAK));
        BlockDispenser.REGISTRY.a(Items.aK, new DispenserRegistry.a(EntityBoat.EnumBoatType.ACACIA));
        DispenseBehaviorItem dispensebehavioritem = new DispenseBehaviorItem() {
            private final DispenseBehaviorItem b = new DispenseBehaviorItem();

            public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
                ItemBucket itembucket = (ItemBucket) itemstack.getItem();
                BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.e(isourceblock.f()));

                if (itembucket.a((EntityHuman) null, isourceblock.getWorld(), blockposition)) {
                    itemstack.setItem(Items.BUCKET);
                    itemstack.count = 1;
                    return itemstack;
                } else {
                    return this.b.a(isourceblock, itemstack);
                }
            }
        };

        BlockDispenser.REGISTRY.a(Items.LAVA_BUCKET, dispensebehavioritem);
        BlockDispenser.REGISTRY.a(Items.WATER_BUCKET, dispensebehavioritem);
        BlockDispenser.REGISTRY.a(Items.BUCKET, new DispenseBehaviorItem() {
            private final DispenseBehaviorItem b = new DispenseBehaviorItem();

            public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
                World world = isourceblock.getWorld();
                BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.e(isourceblock.f()));
                IBlockData iblockdata = world.getType(blockposition);
                Block block = iblockdata.getBlock();
                Material material = iblockdata.getMaterial();
                Item item;

                if (Material.WATER.equals(material) && block instanceof BlockFluids && ((Integer) iblockdata.get(BlockFluids.LEVEL)).intValue() == 0) {
                    item = Items.WATER_BUCKET;
                } else {
                    if (!Material.LAVA.equals(material) || !(block instanceof BlockFluids) || ((Integer) iblockdata.get(BlockFluids.LEVEL)).intValue() != 0) {
                        return super.b(isourceblock, itemstack);
                    }

                    item = Items.LAVA_BUCKET;
                }

                world.setAir(blockposition);
                if (--itemstack.count == 0) {
                    itemstack.setItem(item);
                    itemstack.count = 1;
                } else if (((TileEntityDispenser) isourceblock.getTileEntity()).addItem(new ItemStack(item)) < 0) {
                    this.b.a(isourceblock, new ItemStack(item));
                }

                return itemstack;
            }
        });
        BlockDispenser.REGISTRY.a(Items.FLINT_AND_STEEL, new DispenseBehaviorItem() {
            private boolean b = true;

            protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
                World world = isourceblock.getWorld();
                BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.e(isourceblock.f()));

                if (world.isEmpty(blockposition)) {
                    world.setTypeUpdate(blockposition, Blocks.FIRE.getBlockData());
                    if (itemstack.isDamaged(1, world.random)) {
                        itemstack.count = 0;
                    }
                } else if (world.getType(blockposition).getBlock() == Blocks.TNT) {
                    Blocks.TNT.postBreak(world, blockposition, Blocks.TNT.getBlockData().set(BlockTNT.EXPLODE, Boolean.valueOf(true)));
                    world.setAir(blockposition);
                } else {
                    this.b = false;
                }

                return itemstack;
            }

            protected void a(ISourceBlock isourceblock) {
                if (this.b) {
                    isourceblock.getWorld().triggerEffect(1000, isourceblock.getBlockPosition(), 0);
                } else {
                    isourceblock.getWorld().triggerEffect(1001, isourceblock.getBlockPosition(), 0);
                }

            }
        });
        BlockDispenser.REGISTRY.a(Items.DYE, new DispenseBehaviorItem() {
            private boolean b = true;

            protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
                if (EnumColor.WHITE == EnumColor.fromInvColorIndex(itemstack.getData())) {
                    World world = isourceblock.getWorld();
                    BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.e(isourceblock.f()));

                    if (ItemDye.a(itemstack, world, blockposition)) {
                        if (!world.isClientSide) {
                            world.triggerEffect(2005, blockposition, 0);
                        }
                    } else {
                        this.b = false;
                    }

                    return itemstack;
                } else {
                    return super.b(isourceblock, itemstack);
                }
            }

            protected void a(ISourceBlock isourceblock) {
                if (this.b) {
                    isourceblock.getWorld().triggerEffect(1000, isourceblock.getBlockPosition(), 0);
                } else {
                    isourceblock.getWorld().triggerEffect(1001, isourceblock.getBlockPosition(), 0);
                }

            }
        });
        BlockDispenser.REGISTRY.a(Item.getItemOf(Blocks.TNT), new DispenseBehaviorItem() {
            protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
                World world = isourceblock.getWorld();
                BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.e(isourceblock.f()));
                EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, (double) blockposition.getX() + 0.5D, (double) blockposition.getY(), (double) blockposition.getZ() + 0.5D, (EntityLiving) null);

                world.addEntity(entitytntprimed);
                world.a((EntityHuman) null, entitytntprimed.locX, entitytntprimed.locY, entitytntprimed.locZ, SoundEffects.gj, SoundCategory.BLOCKS, 1.0F, 1.0F);
                --itemstack.count;
                return itemstack;
            }
        });
        BlockDispenser.REGISTRY.a(Items.SKULL, new DispenseBehaviorItem() {
            private boolean b = true;

            protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
                World world = isourceblock.getWorld();
                EnumDirection enumdirection = BlockDispenser.e(isourceblock.f());
                BlockPosition blockposition = isourceblock.getBlockPosition().shift(enumdirection);
                BlockSkull blockskull = Blocks.SKULL;

                if (world.isEmpty(blockposition) && blockskull.b(world, blockposition, itemstack)) {
                    if (!world.isClientSide) {
                        world.setTypeAndData(blockposition, blockskull.getBlockData().set(BlockSkull.FACING, EnumDirection.UP), 3);
                        TileEntity tileentity = world.getTileEntity(blockposition);

                        if (tileentity instanceof TileEntitySkull) {
                            if (itemstack.getData() == 3) {
                                GameProfile gameprofile = null;

                                if (itemstack.hasTag()) {
                                    NBTTagCompound nbttagcompound = itemstack.getTag();

                                    if (nbttagcompound.hasKeyOfType("SkullOwner", 10)) {
                                        gameprofile = GameProfileSerializer.deserialize(nbttagcompound.getCompound("SkullOwner"));
                                    } else if (nbttagcompound.hasKeyOfType("SkullOwner", 8)) {
                                        String s = nbttagcompound.getString("SkullOwner");

                                        if (!UtilColor.b(s)) {
                                            gameprofile = new GameProfile((UUID) null, s);
                                        }
                                    }
                                }

                                ((TileEntitySkull) tileentity).setGameProfile(gameprofile);
                            } else {
                                ((TileEntitySkull) tileentity).setSkullType(itemstack.getData());
                            }

                            ((TileEntitySkull) tileentity).setRotation(enumdirection.opposite().get2DRotationValue() * 4);
                            Blocks.SKULL.a(world, blockposition, (TileEntitySkull) tileentity);
                        }

                        --itemstack.count;
                    }
                } else if (ItemArmor.a(isourceblock, itemstack) == null) {
                    this.b = false;
                }

                return itemstack;
            }

            protected void a(ISourceBlock isourceblock) {
                if (this.b) {
                    isourceblock.getWorld().triggerEffect(1000, isourceblock.getBlockPosition(), 0);
                } else {
                    isourceblock.getWorld().triggerEffect(1001, isourceblock.getBlockPosition(), 0);
                }

            }
        });
        BlockDispenser.REGISTRY.a(Item.getItemOf(Blocks.PUMPKIN), new DispenseBehaviorItem() {
            private boolean b = true;

            protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
                World world = isourceblock.getWorld();
                BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.e(isourceblock.f()));
                BlockPumpkin blockpumpkin = (BlockPumpkin) Blocks.PUMPKIN;

                if (world.isEmpty(blockposition) && blockpumpkin.b(world, blockposition)) {
                    if (!world.isClientSide) {
                        world.setTypeAndData(blockposition, blockpumpkin.getBlockData(), 3);
                    }

                    --itemstack.count;
                } else {
                    ItemStack itemstack1 = ItemArmor.a(isourceblock, itemstack);

                    if (itemstack1 == null) {
                        this.b = false;
                    }
                }

                return itemstack;
            }

            protected void a(ISourceBlock isourceblock) {
                if (this.b) {
                    isourceblock.getWorld().triggerEffect(1000, isourceblock.getBlockPosition(), 0);
                } else {
                    isourceblock.getWorld().triggerEffect(1001, isourceblock.getBlockPosition(), 0);
                }

            }
        });
    }

    public static void c() {
        if (!DispenserRegistry.b) {
            DispenserRegistry.b = true;
            if (DispenserRegistry.c.isDebugEnabled()) {
                d();
            }

            SoundEffect.b();
            Block.x();
            BlockFire.e();
            MobEffectList.k();
            Enchantment.f();
            Item.t();
            PotionRegistry.b();
            PotionBrewer.a();
            StatisticList.a();
            BiomeBase.q();
            b();
        }
    }

    private static void d() {
        System.setErr(new RedirectStream("STDERR", System.err));
        System.setOut(new RedirectStream("STDOUT", DispenserRegistry.a));
    }

    public static class a extends DispenseBehaviorItem {

        private final DispenseBehaviorItem b = new DispenseBehaviorItem();
        private final EntityBoat.EnumBoatType c;

        public a(EntityBoat.EnumBoatType entityboat_enumboattype) {
            this.c = entityboat_enumboattype;
        }

        public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
            EnumDirection enumdirection = BlockDispenser.e(isourceblock.f());
            World world = isourceblock.getWorld();
            double d0 = isourceblock.getX() + (double) ((float) enumdirection.getAdjacentX() * 1.125F);
            double d1 = isourceblock.getY() + (double) ((float) enumdirection.getAdjacentY() * 1.125F);
            double d2 = isourceblock.getZ() + (double) ((float) enumdirection.getAdjacentZ() * 1.125F);
            BlockPosition blockposition = isourceblock.getBlockPosition().shift(enumdirection);
            Material material = world.getType(blockposition).getMaterial();
            double d3;

            if (Material.WATER.equals(material)) {
                d3 = 1.0D;
            } else {
                if (!Material.AIR.equals(material) || !Material.WATER.equals(world.getType(blockposition.down()).getMaterial())) {
                    return this.b.a(isourceblock, itemstack);
                }

                d3 = 0.0D;
            }

            EntityBoat entityboat = new EntityBoat(world, d0, d1 + d3, d2);

            entityboat.a(this.c);
            entityboat.yaw = enumdirection.opposite().l();
            world.addEntity(entityboat);
            itemstack.cloneAndSubtract(1);
            return itemstack;
        }

        protected void a(ISourceBlock isourceblock) {
            isourceblock.getWorld().triggerEffect(1000, isourceblock.getBlockPosition(), 0);
        }
    }
}
