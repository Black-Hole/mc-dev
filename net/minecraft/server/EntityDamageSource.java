package net.minecraft.server;

import javax.annotation.Nullable;

public class EntityDamageSource extends DamageSource {

    protected Entity t;
    private boolean u;

    public EntityDamageSource(String s, Entity entity) {
        super(s);
        this.t = entity;
    }

    public EntityDamageSource w() {
        this.u = true;
        return this;
    }

    public boolean x() {
        return this.u;
    }

    @Nullable
    public Entity getEntity() {
        return this.t;
    }

    public IChatBaseComponent getLocalizedDeathMessage(EntityLiving entityliving) {
        ItemStack itemstack = this.t instanceof EntityLiving ? ((EntityLiving) this.t).getItemInMainHand() : null;
        String s = "death.attack." + this.translationIndex;
        String s1 = s + ".item";

        return itemstack != null && itemstack.hasName() && LocaleI18n.c(s1) ? new ChatMessage(s1, new Object[] { entityliving.getScoreboardDisplayName(), this.t.getScoreboardDisplayName(), itemstack.B()}) : new ChatMessage(s, new Object[] { entityliving.getScoreboardDisplayName(), this.t.getScoreboardDisplayName()});
    }

    public boolean r() {
        return this.t != null && this.t instanceof EntityLiving && !(this.t instanceof EntityHuman);
    }

    @Nullable
    public Vec3D v() {
        return new Vec3D(this.t.locX, this.t.locY, this.t.locZ);
    }
}
