package net.minecraft.server;

public class DemoPlayerInteractManager extends PlayerInteractManager {

    private boolean c;
    private boolean d;
    private int e;
    private int f;

    public DemoPlayerInteractManager(WorldServer worldserver) {
        super(worldserver);
    }

    @Override
    public void a() {
        super.a();
        ++this.f;
        long i = this.world.getTime();
        long j = i / 24000L + 1L;

        if (!this.c && this.f > 20) {
            this.c = true;
            this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 0.0F));
        }

        this.d = i > 120500L;
        if (this.d) {
            ++this.e;
        }

        if (i % 24000L == 500L) {
            if (j <= 6L) {
                if (j == 6L) {
                    this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 104.0F));
                } else {
                    this.player.sendMessage(new ChatMessage("demo.day." + j, new Object[0]));
                }
            }
        } else if (j == 1L) {
            if (i == 100L) {
                this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 101.0F));
            } else if (i == 175L) {
                this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 102.0F));
            } else if (i == 250L) {
                this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 103.0F));
            }
        } else if (j == 5L && i % 24000L == 22000L) {
            this.player.sendMessage(new ChatMessage("demo.day.warning", new Object[0]));
        }

    }

    private void f() {
        if (this.e > 100) {
            this.player.sendMessage(new ChatMessage("demo.reminder", new Object[0]));
            this.e = 0;
        }

    }

    @Override
    public void a(BlockPosition blockposition, EnumDirection enumdirection) {
        if (this.d) {
            this.f();
        } else {
            super.a(blockposition, enumdirection);
        }
    }

    @Override
    public void a(BlockPosition blockposition) {
        if (!this.d) {
            super.a(blockposition);
        }
    }

    @Override
    public boolean breakBlock(BlockPosition blockposition) {
        return this.d ? false : super.breakBlock(blockposition);
    }

    @Override
    public EnumInteractionResult a(EntityHuman entityhuman, World world, ItemStack itemstack, EnumHand enumhand) {
        if (this.d) {
            this.f();
            return EnumInteractionResult.PASS;
        } else {
            return super.a(entityhuman, world, itemstack, enumhand);
        }
    }

    @Override
    public EnumInteractionResult a(EntityHuman entityhuman, World world, ItemStack itemstack, EnumHand enumhand, MovingObjectPositionBlock movingobjectpositionblock) {
        if (this.d) {
            this.f();
            return EnumInteractionResult.PASS;
        } else {
            return super.a(entityhuman, world, itemstack, enumhand, movingobjectpositionblock);
        }
    }
}
