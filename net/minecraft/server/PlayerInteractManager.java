package net.minecraft.server;

public class PlayerInteractManager {

    public World world;
    public EntityPlayer player;
    private EnumGamemode gamemode;
    private boolean d;
    private int lastDigTick;
    private BlockPosition f;
    private int currentTick;
    private boolean h;
    private BlockPosition i;
    private int j;
    private int k;

    public PlayerInteractManager(World world) {
        this.gamemode = EnumGamemode.NOT_SET;
        this.f = BlockPosition.ZERO;
        this.i = BlockPosition.ZERO;
        this.k = -1;
        this.world = world;
    }

    public void setGameMode(EnumGamemode enumgamemode) {
        this.gamemode = enumgamemode;
        enumgamemode.a(this.player.abilities);
        this.player.updateAbilities();
        this.player.server.getPlayerList().sendAll(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.UPDATE_GAME_MODE, new EntityPlayer[] { this.player}));
    }

    public EnumGamemode getGameMode() {
        return this.gamemode;
    }

    public boolean c() {
        return this.gamemode.e();
    }

    public boolean isCreative() {
        return this.gamemode.d();
    }

    public void b(EnumGamemode enumgamemode) {
        if (this.gamemode == EnumGamemode.NOT_SET) {
            this.gamemode = enumgamemode;
        }

        this.setGameMode(this.gamemode);
    }

    public void a() {
        ++this.currentTick;
        float f;
        int i;

        if (this.h) {
            int j = this.currentTick - this.j;
            Block block = this.world.getType(this.i).getBlock();

            if (block.getMaterial() == Material.AIR) {
                this.h = false;
            } else {
                f = block.getDamage(this.player, this.player.world, this.i) * (float) (j + 1);
                i = (int) (f * 10.0F);
                if (i != this.k) {
                    this.world.c(this.player.getId(), this.i, i);
                    this.k = i;
                }

                if (f >= 1.0F) {
                    this.h = false;
                    this.breakBlock(this.i);
                }
            }
        } else if (this.d) {
            Block block1 = this.world.getType(this.f).getBlock();

            if (block1.getMaterial() == Material.AIR) {
                this.world.c(this.player.getId(), this.f, -1);
                this.k = -1;
                this.d = false;
            } else {
                int k = this.currentTick - this.lastDigTick;

                f = block1.getDamage(this.player, this.player.world, this.i) * (float) (k + 1);
                i = (int) (f * 10.0F);
                if (i != this.k) {
                    this.world.c(this.player.getId(), this.f, i);
                    this.k = i;
                }
            }
        }

    }

    public void a(BlockPosition blockposition, EnumDirection enumdirection) {
        if (this.isCreative()) {
            if (!this.world.douseFire((EntityHuman) null, blockposition, enumdirection)) {
                this.breakBlock(blockposition);
            }

        } else {
            Block block = this.world.getType(blockposition).getBlock();

            if (this.gamemode.c()) {
                if (this.gamemode == EnumGamemode.SPECTATOR) {
                    return;
                }

                if (!this.player.cm()) {
                    ItemStack itemstack = this.player.bY();

                    if (itemstack == null) {
                        return;
                    }

                    if (!itemstack.c(block)) {
                        return;
                    }
                }
            }

            this.world.douseFire((EntityHuman) null, blockposition, enumdirection);
            this.lastDigTick = this.currentTick;
            float f = 1.0F;

            if (block.getMaterial() != Material.AIR) {
                block.attack(this.world, blockposition, this.player);
                f = block.getDamage(this.player, this.player.world, blockposition);
            }

            if (block.getMaterial() != Material.AIR && f >= 1.0F) {
                this.breakBlock(blockposition);
            } else {
                this.d = true;
                this.f = blockposition;
                int i = (int) (f * 10.0F);

                this.world.c(this.player.getId(), blockposition, i);
                this.k = i;
            }

        }
    }

    public void a(BlockPosition blockposition) {
        if (blockposition.equals(this.f)) {
            int i = this.currentTick - this.lastDigTick;
            Block block = this.world.getType(blockposition).getBlock();

            if (block.getMaterial() != Material.AIR) {
                float f = block.getDamage(this.player, this.player.world, blockposition) * (float) (i + 1);

                if (f >= 0.7F) {
                    this.d = false;
                    this.world.c(this.player.getId(), blockposition, -1);
                    this.breakBlock(blockposition);
                } else if (!this.h) {
                    this.d = false;
                    this.h = true;
                    this.i = blockposition;
                    this.j = this.lastDigTick;
                }
            }
        }

    }

    public void e() {
        this.d = false;
        this.world.c(this.player.getId(), this.f, -1);
    }

    private boolean c(BlockPosition blockposition) {
        IBlockData iblockdata = this.world.getType(blockposition);

        iblockdata.getBlock().a(this.world, blockposition, iblockdata, (EntityHuman) this.player);
        boolean flag = this.world.setAir(blockposition);

        if (flag) {
            iblockdata.getBlock().postBreak(this.world, blockposition, iblockdata);
        }

        return flag;
    }

    public boolean breakBlock(BlockPosition blockposition) {
        if (this.gamemode.d() && this.player.bz() != null && this.player.bz().getItem() instanceof ItemSword) {
            return false;
        } else {
            IBlockData iblockdata = this.world.getType(blockposition);
            TileEntity tileentity = this.world.getTileEntity(blockposition);

            if (this.gamemode.c()) {
                if (this.gamemode == EnumGamemode.SPECTATOR) {
                    return false;
                }

                if (!this.player.cm()) {
                    ItemStack itemstack = this.player.bY();

                    if (itemstack == null) {
                        return false;
                    }

                    if (!itemstack.c(iblockdata.getBlock())) {
                        return false;
                    }
                }
            }

            this.world.a(this.player, 2001, blockposition, Block.getCombinedId(iblockdata));
            boolean flag = this.c(blockposition);

            if (this.isCreative()) {
                this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, blockposition));
            } else {
                ItemStack itemstack1 = this.player.bY();
                boolean flag1 = this.player.b(iblockdata.getBlock());

                if (itemstack1 != null) {
                    itemstack1.a(this.world, iblockdata.getBlock(), blockposition, this.player);
                    if (itemstack1.count == 0) {
                        this.player.bZ();
                    }
                }

                if (flag && flag1) {
                    iblockdata.getBlock().a(this.world, this.player, blockposition, iblockdata, tileentity);
                }
            }

            return flag;
        }
    }

    public boolean useItem(EntityHuman entityhuman, World world, ItemStack itemstack) {
        if (this.gamemode == EnumGamemode.SPECTATOR) {
            return false;
        } else {
            int i = itemstack.count;
            int j = itemstack.getData();
            ItemStack itemstack1 = itemstack.a(world, entityhuman);

            if (itemstack1 == itemstack && (itemstack1 == null || itemstack1.count == i && itemstack1.l() <= 0 && itemstack1.getData() == j)) {
                return false;
            } else {
                entityhuman.inventory.items[entityhuman.inventory.itemInHandIndex] = itemstack1;
                if (this.isCreative()) {
                    itemstack1.count = i;
                    if (itemstack1.e()) {
                        itemstack1.setData(j);
                    }
                }

                if (itemstack1.count == 0) {
                    entityhuman.inventory.items[entityhuman.inventory.itemInHandIndex] = null;
                }

                if (!entityhuman.bR()) {
                    ((EntityPlayer) entityhuman).updateInventory(entityhuman.defaultContainer);
                }

                return true;
            }
        }
    }

    public boolean interact(EntityHuman entityhuman, World world, ItemStack itemstack, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2) {
        if (this.gamemode == EnumGamemode.SPECTATOR) {
            TileEntity tileentity = world.getTileEntity(blockposition);

            if (tileentity instanceof ITileInventory) {
                Block block = world.getType(blockposition).getBlock();
                ITileInventory itileinventory = (ITileInventory) tileentity;

                if (itileinventory instanceof TileEntityChest && block instanceof BlockChest) {
                    itileinventory = ((BlockChest) block).d(world, blockposition);
                }

                if (itileinventory != null) {
                    entityhuman.openContainer(itileinventory);
                    return true;
                }
            } else if (tileentity instanceof IInventory) {
                entityhuman.openContainer((IInventory) tileentity);
                return true;
            }

            return false;
        } else {
            if (!entityhuman.isSneaking() || entityhuman.bz() == null) {
                IBlockData iblockdata = world.getType(blockposition);

                if (iblockdata.getBlock().interact(world, blockposition, iblockdata, entityhuman, enumdirection, f, f1, f2)) {
                    return true;
                }
            }

            if (itemstack == null) {
                return false;
            } else if (this.isCreative()) {
                int i = itemstack.getData();
                int j = itemstack.count;
                boolean flag = itemstack.placeItem(entityhuman, world, blockposition, enumdirection, f, f1, f2);

                itemstack.setData(i);
                itemstack.count = j;
                return flag;
            } else {
                return itemstack.placeItem(entityhuman, world, blockposition, enumdirection, f, f1, f2);
            }
        }
    }

    public void a(WorldServer worldserver) {
        this.world = worldserver;
    }
}
