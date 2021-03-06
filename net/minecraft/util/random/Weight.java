package net.minecraft.util.random;

import com.mojang.serialization.Codec;
import net.minecraft.SharedConstants;
import net.minecraft.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Weight {

    public static final Codec<Weight> CODEC = Codec.INT.xmap(Weight::a, Weight::a);
    private static final Weight ONE = new Weight(1);
    private static final Logger LOGGER = LogManager.getLogger();
    private final int value;

    private Weight(int i) {
        this.value = i;
    }

    public static Weight a(int i) {
        if (i == 1) {
            return Weight.ONE;
        } else {
            b(i);
            return new Weight(i);
        }
    }

    public int a() {
        return this.value;
    }

    private static void b(int i) {
        if (i < 0) {
            throw (IllegalArgumentException) SystemUtils.c((Throwable) (new IllegalArgumentException("Weight should be >= 0")));
        } else {
            if (i == 0 && SharedConstants.IS_RUNNING_IN_IDE) {
                Weight.LOGGER.warn("Found 0 weight, make sure this is intentional!");
            }

        }
    }

    public String toString() {
        return Integer.toString(this.value);
    }

    public int hashCode() {
        return Integer.hashCode(this.value);
    }

    public boolean equals(Object object) {
        return this == object ? true : object instanceof Weight && this.value == ((Weight) object).value;
    }
}
