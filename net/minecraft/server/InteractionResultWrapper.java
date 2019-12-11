package net.minecraft.server;

public class InteractionResultWrapper<T> {

    private final EnumInteractionResult a;
    private final T b;

    public InteractionResultWrapper(EnumInteractionResult enuminteractionresult, T t0) {
        this.a = enuminteractionresult;
        this.b = t0;
    }

    public EnumInteractionResult a() {
        return this.a;
    }

    public T b() {
        return this.b;
    }

    public static <T> InteractionResultWrapper<T> a(T t0) {
        return new InteractionResultWrapper<>(EnumInteractionResult.SUCCESS, t0);
    }

    public static <T> InteractionResultWrapper<T> b(T t0) {
        return new InteractionResultWrapper<>(EnumInteractionResult.CONSUME, t0);
    }

    public static <T> InteractionResultWrapper<T> c(T t0) {
        return new InteractionResultWrapper<>(EnumInteractionResult.PASS, t0);
    }

    public static <T> InteractionResultWrapper<T> d(T t0) {
        return new InteractionResultWrapper<>(EnumInteractionResult.FAIL, t0);
    }
}
