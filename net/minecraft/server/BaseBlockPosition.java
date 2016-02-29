package net.minecraft.server;

import com.google.common.base.Objects;

public class BaseBlockPosition implements Comparable<BaseBlockPosition> {

    public static final BaseBlockPosition ZERO = new BaseBlockPosition(0, 0, 0);
    private final int a;
    private final int c;
    private final int d;

    public BaseBlockPosition(int i, int j, int k) {
        this.a = i;
        this.c = j;
        this.d = k;
    }

    public BaseBlockPosition(double d0, double d1, double d2) {
        this(MathHelper.floor(d0), MathHelper.floor(d1), MathHelper.floor(d2));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof BaseBlockPosition)) {
            return false;
        } else {
            BaseBlockPosition baseblockposition = (BaseBlockPosition) object;

            return this.getX() != baseblockposition.getX() ? false : (this.getY() != baseblockposition.getY() ? false : this.getZ() == baseblockposition.getZ());
        }
    }

    public int hashCode() {
        return (this.getY() + this.getZ() * 31) * 31 + this.getX();
    }

    public int i(BaseBlockPosition baseblockposition) {
        return this.getY() == baseblockposition.getY() ? (this.getZ() == baseblockposition.getZ() ? this.getX() - baseblockposition.getX() : this.getZ() - baseblockposition.getZ()) : this.getY() - baseblockposition.getY();
    }

    public int getX() {
        return this.a;
    }

    public int getY() {
        return this.c;
    }

    public int getZ() {
        return this.d;
    }

    public BaseBlockPosition d(BaseBlockPosition baseblockposition) {
        return new BaseBlockPosition(this.getY() * baseblockposition.getZ() - this.getZ() * baseblockposition.getY(), this.getZ() * baseblockposition.getX() - this.getX() * baseblockposition.getZ(), this.getX() * baseblockposition.getY() - this.getY() * baseblockposition.getX());
    }

    public double f(int i, int j, int k) {
        double d0 = (double) (this.getX() - i);
        double d1 = (double) (this.getY() - j);
        double d2 = (double) (this.getZ() - k);

        return Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public double distanceSquared(double d0, double d1, double d2) {
        double d3 = (double) this.getX() - d0;
        double d4 = (double) this.getY() - d1;
        double d5 = (double) this.getZ() - d2;

        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public double f(double d0, double d1, double d2) {
        double d3 = (double) this.getX() + 0.5D - d0;
        double d4 = (double) this.getY() + 0.5D - d1;
        double d5 = (double) this.getZ() + 0.5D - d2;

        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public double k(BaseBlockPosition baseblockposition) {
        return this.distanceSquared((double) baseblockposition.getX(), (double) baseblockposition.getY(), (double) baseblockposition.getZ());
    }

    public String toString() {
        return Objects.toStringHelper(this).add("x", this.getX()).add("y", this.getY()).add("z", this.getZ()).toString();
    }

    public int compareTo(Object object) {
        return this.i((BaseBlockPosition) object);
    }
}
