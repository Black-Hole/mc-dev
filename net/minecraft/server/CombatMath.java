package net.minecraft.server;

public class CombatMath {

    public static float a(float f, float f1) {
        float f2 = MathHelper.a(f1 - f * 0.5F, f1 * 0.2F, 20.0F);
        float f3 = f * (1.0F - f2 / 25.0F);

        return f3;
    }

    public static float b(float f, float f1) {
        float f2 = MathHelper.a(f1, 0.0F, 20.0F);

        return f * (1.0F - f2 / 25.0F);
    }
}
