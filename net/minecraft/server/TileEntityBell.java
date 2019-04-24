package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

public class TileEntityBell extends TileEntity implements ITickable {

    private long g;
    public int a;
    public boolean b;
    public EnumDirection c;
    private List<EntityLiving> h;
    private boolean i;
    private int j;

    public TileEntityBell() {
        super(TileEntityTypes.BELL);
    }

    @Override
    public boolean setProperty(int i, int j) {
        if (i == 1) {
            this.c();
            this.j = 0;
            this.c = EnumDirection.fromType1(j);
            this.a = 0;
            this.b = true;
            return true;
        } else {
            return super.setProperty(i, j);
        }
    }

    @Override
    public void tick() {
        if (this.b) {
            ++this.a;
        }

        BlockPosition blockposition = this.getPosition();

        if (this.a >= 50) {
            this.b = false;
            this.a = 0;
        }

        if (this.a >= 5 && this.j == 0) {
            this.a(this.world, blockposition);
        }

        if (this.i) {
            if (this.j < 40) {
                ++this.j;
            } else {
                this.i = false;
                this.b(this.world, blockposition);
            }
        }

    }

    public void a(EnumDirection enumdirection) {
        BlockPosition blockposition = this.getPosition();

        this.c = enumdirection;
        if (this.b) {
            this.a = 0;
        } else {
            this.b = true;
        }

        this.world.playBlockAction(blockposition, this.getBlock().getBlock(), 1, enumdirection.a());
    }

    private void c() {
        BlockPosition blockposition = this.getPosition();

        if (this.world.getTime() > this.g + 60L || this.h == null) {
            this.g = this.world.getTime();
            AxisAlignedBB axisalignedbb = (new AxisAlignedBB(blockposition)).g(48.0D);

            this.h = this.world.a(EntityLiving.class, axisalignedbb);
        }

        if (!this.world.isClientSide) {
            Iterator iterator = this.h.iterator();

            while (iterator.hasNext()) {
                EntityLiving entityliving = (EntityLiving) iterator.next();

                if (entityliving.isAlive() && !entityliving.dead && blockposition.a((IPosition) entityliving.ch(), 32.0D)) {
                    entityliving.getBehaviorController().a(MemoryModuleType.HEARD_BELL_TIME, (Object) this.world.getTime());
                }
            }
        }

    }

    private void a(World world, BlockPosition blockposition) {
        Iterator iterator = this.h.iterator();

        while (iterator.hasNext()) {
            EntityLiving entityliving = (EntityLiving) iterator.next();

            if (entityliving.isAlive() && !entityliving.dead && blockposition.a((IPosition) entityliving.ch(), 32.0D) && entityliving.getEntityType().a(TagsEntity.RADIERS)) {
                this.i = true;
            }
        }

        if (this.i) {
            world.a((EntityHuman) null, blockposition, SoundEffects.BLOCK_BELL_RESONATE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }

    }

    private void b(World world, BlockPosition blockposition) {
        int i = 16700985;
        int j = (int) this.h.stream().filter((entityliving) -> {
            return blockposition.a((IPosition) entityliving.ch(), 32.0D);
        }).count();
        Iterator iterator = this.h.iterator();

        while (iterator.hasNext()) {
            EntityLiving entityliving = (EntityLiving) iterator.next();

            if (entityliving.isAlive() && !entityliving.dead && blockposition.a((IPosition) entityliving.ch(), 32.0D) && entityliving.getEntityType().a(TagsEntity.RADIERS)) {
                if (!world.isClientSide) {
                    entityliving.addEffect(new MobEffect(MobEffects.GLOWING, 60));
                } else {
                    float f = 1.0F;
                    float f1 = MathHelper.sqrt((entityliving.locX - (double) blockposition.getX()) * (entityliving.locX - (double) blockposition.getX()) + (entityliving.locZ - (double) blockposition.getZ()) * (entityliving.locZ - (double) blockposition.getZ()));
                    double d0 = (double) ((float) blockposition.getX() + 0.5F) + (double) (1.0F / f1) * (entityliving.locX - (double) blockposition.getX());
                    double d1 = (double) ((float) blockposition.getZ() + 0.5F) + (double) (1.0F / f1) * (entityliving.locZ - (double) blockposition.getZ());
                    int k = MathHelper.clamp((j - 21) / -2, 3, 15);

                    for (int l = 0; l < k; ++l) {
                        i += 5;
                        double d2 = (double) (i >> 16 & 255) / 255.0D;
                        double d3 = (double) (i >> 8 & 255) / 255.0D;
                        double d4 = (double) (i & 255) / 255.0D;

                        world.addParticle(Particles.ENTITY_EFFECT, d0, (double) ((float) blockposition.getY() + 0.5F), d1, d2, d3, d4);
                    }
                }
            }
        }

    }
}
