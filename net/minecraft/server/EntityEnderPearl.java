package net.minecraft.server;

public class EntityEnderPearl extends EntityProjectile {

    public EntityEnderPearl(World world, EntityLiving entityliving) {
        super(world, entityliving);
    }

    protected void a(MovingObjectPosition movingobjectposition) {
        EntityLiving entityliving = this.getShooter();

        if (movingobjectposition.entity != null) {
            movingobjectposition.entity.damageEntity(DamageSource.projectile(this, entityliving), 0.0F);
        }

        for (int i = 0; i < 32; ++i) {
            this.world.addParticle(EnumParticle.PORTAL, this.locX, this.locY + this.random.nextDouble() * 2.0D, this.locZ, this.random.nextGaussian(), 0.0D, this.random.nextGaussian(), new int[0]);
        }

        if (!this.world.isStatic) {
            if (entityliving instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer) entityliving;

                if (entityplayer.playerConnection.a().g() && entityplayer.world == this.world && !entityplayer.isSleeping()) {
                    if (this.random.nextFloat() < 0.05F && this.world.getGameRules().getBoolean("doMobSpawning")) {
                        EntityEndermite entityendermite = new EntityEndermite(this.world);

                        entityendermite.a(true);
                        entityendermite.setPositionRotation(entityliving.locX, entityliving.locY, entityliving.locZ, entityliving.yaw, entityliving.pitch);
                        this.world.addEntity(entityendermite);
                    }

                    if (entityliving.av()) {
                        entityliving.mount((Entity) null);
                    }

                    entityliving.enderTeleportTo(this.locX, this.locY, this.locZ);
                    entityliving.fallDistance = 0.0F;
                    entityliving.damageEntity(DamageSource.FALL, 5.0F);
                }
            }

            this.die();
        }

    }

    public void s_() {
        EntityLiving entityliving = this.getShooter();

        if (entityliving != null && entityliving instanceof EntityHuman && !entityliving.isAlive()) {
            this.die();
        } else {
            super.s_();
        }

    }
}
