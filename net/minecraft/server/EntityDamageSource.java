package net.minecraft.server;

import javax.annotation.Nullable;

public class EntityDamageSource extends DamageSource {

    @Nullable
    protected Entity u;
    private boolean v;

    public EntityDamageSource(String s, @Nullable Entity entity) {
        super(s);
        this.u = entity;
    }

    public EntityDamageSource w() {
        this.v = true;
        return this;
    }

    public boolean x() {
        return this.v;
    }

    @Nullable
    public Entity getEntity() {
        return this.u;
    }

    public IChatBaseComponent getLocalizedDeathMessage(EntityLiving entityliving) {
        ItemStack itemstack = this.u instanceof EntityLiving ? ((EntityLiving) this.u).getItemInMainHand() : ItemStack.a;
        String s = "death.attack." + this.translationIndex;
        String s1 = s + ".item";

        return !itemstack.isEmpty() && itemstack.hasName() && LocaleI18n.c(s1) ? new ChatMessage(s1, new Object[] { entityliving.getScoreboardDisplayName(), this.u.getScoreboardDisplayName(), itemstack.C()}) : new ChatMessage(s, new Object[] { entityliving.getScoreboardDisplayName(), this.u.getScoreboardDisplayName()});
    }

    public boolean r() {
        return this.u != null && this.u instanceof EntityLiving && !(this.u instanceof EntityHuman);
    }

    @Nullable
    public Vec3D v() {
        return new Vec3D(this.u.locX, this.u.locY, this.u.locZ);
    }
}
