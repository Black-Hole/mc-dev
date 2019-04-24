package net.minecraft.server;

public class Potions {

    public static final PotionRegistry EMPTY = a("empty", new PotionRegistry(new MobEffect[0]));
    public static final PotionRegistry b = a("water", new PotionRegistry(new MobEffect[0]));
    public static final PotionRegistry c = a("mundane", new PotionRegistry(new MobEffect[0]));
    public static final PotionRegistry d = a("thick", new PotionRegistry(new MobEffect[0]));
    public static final PotionRegistry e = a("awkward", new PotionRegistry(new MobEffect[0]));
    public static final PotionRegistry f = a("night_vision", new PotionRegistry(new MobEffect[] { new MobEffect(MobEffects.NIGHT_VISION, 3600)}));
    public static final PotionRegistry g = a("long_night_vision", new PotionRegistry("night_vision", new MobEffect[] { new MobEffect(MobEffects.NIGHT_VISION, 9600)}));
    public static final PotionRegistry h = a("invisibility", new PotionRegistry(new MobEffect[] { new MobEffect(MobEffects.INVISIBILITY, 3600)}));
    public static final PotionRegistry i = a("long_invisibility", new PotionRegistry("invisibility", new MobEffect[] { new MobEffect(MobEffects.INVISIBILITY, 9600)}));
    public static final PotionRegistry j = a("leaping", new PotionRegistry(new MobEffect[] { new MobEffect(MobEffects.JUMP, 3600)}));
    public static final PotionRegistry k = a("long_leaping", new PotionRegistry("leaping", new MobEffect[] { new MobEffect(MobEffects.JUMP, 9600)}));
    public static final PotionRegistry l = a("strong_leaping", new PotionRegistry("leaping", new MobEffect[] { new MobEffect(MobEffects.JUMP, 1800, 1)}));
    public static final PotionRegistry m = a("fire_resistance", new PotionRegistry(new MobEffect[] { new MobEffect(MobEffects.FIRE_RESISTANCE, 3600)}));
    public static final PotionRegistry n = a("long_fire_resistance", new PotionRegistry("fire_resistance", new MobEffect[] { new MobEffect(MobEffects.FIRE_RESISTANCE, 9600)}));
    public static final PotionRegistry o = a("swiftness", new PotionRegistry(new MobEffect[] { new MobEffect(MobEffects.FASTER_MOVEMENT, 3600)}));
    public static final PotionRegistry p = a("long_swiftness", new PotionRegistry("swiftness", new MobEffect[] { new MobEffect(MobEffects.FASTER_MOVEMENT, 9600)}));
    public static final PotionRegistry q = a("strong_swiftness", new PotionRegistry("swiftness", new MobEffect[] { new MobEffect(MobEffects.FASTER_MOVEMENT, 1800, 1)}));
    public static final PotionRegistry r = a("slowness", new PotionRegistry(new MobEffect[] { new MobEffect(MobEffects.SLOWER_MOVEMENT, 1800)}));
    public static final PotionRegistry s = a("long_slowness", new PotionRegistry("slowness", new MobEffect[] { new MobEffect(MobEffects.SLOWER_MOVEMENT, 4800)}));
    public static final PotionRegistry t = a("strong_slowness", new PotionRegistry("slowness", new MobEffect[] { new MobEffect(MobEffects.SLOWER_MOVEMENT, 400, 3)}));
    public static final PotionRegistry u = a("turtle_master", new PotionRegistry("turtle_master", new MobEffect[] { new MobEffect(MobEffects.SLOWER_MOVEMENT, 400, 3), new MobEffect(MobEffects.RESISTANCE, 400, 2)}));
    public static final PotionRegistry v = a("long_turtle_master", new PotionRegistry("turtle_master", new MobEffect[] { new MobEffect(MobEffects.SLOWER_MOVEMENT, 800, 3), new MobEffect(MobEffects.RESISTANCE, 800, 2)}));
    public static final PotionRegistry w = a("strong_turtle_master", new PotionRegistry("turtle_master", new MobEffect[] { new MobEffect(MobEffects.SLOWER_MOVEMENT, 400, 5), new MobEffect(MobEffects.RESISTANCE, 400, 3)}));
    public static final PotionRegistry x = a("water_breathing", new PotionRegistry(new MobEffect[] { new MobEffect(MobEffects.WATER_BREATHING, 3600)}));
    public static final PotionRegistry y = a("long_water_breathing", new PotionRegistry("water_breathing", new MobEffect[] { new MobEffect(MobEffects.WATER_BREATHING, 9600)}));
    public static final PotionRegistry z = a("healing", new PotionRegistry(new MobEffect[] { new MobEffect(MobEffects.HEAL, 1)}));
    public static final PotionRegistry A = a("strong_healing", new PotionRegistry("healing", new MobEffect[] { new MobEffect(MobEffects.HEAL, 1, 1)}));
    public static final PotionRegistry B = a("harming", new PotionRegistry(new MobEffect[] { new MobEffect(MobEffects.HARM, 1)}));
    public static final PotionRegistry C = a("strong_harming", new PotionRegistry("harming", new MobEffect[] { new MobEffect(MobEffects.HARM, 1, 1)}));
    public static final PotionRegistry D = a("poison", new PotionRegistry(new MobEffect[] { new MobEffect(MobEffects.POISON, 900)}));
    public static final PotionRegistry E = a("long_poison", new PotionRegistry("poison", new MobEffect[] { new MobEffect(MobEffects.POISON, 1800)}));
    public static final PotionRegistry F = a("strong_poison", new PotionRegistry("poison", new MobEffect[] { new MobEffect(MobEffects.POISON, 432, 1)}));
    public static final PotionRegistry G = a("regeneration", new PotionRegistry(new MobEffect[] { new MobEffect(MobEffects.REGENERATION, 900)}));
    public static final PotionRegistry H = a("long_regeneration", new PotionRegistry("regeneration", new MobEffect[] { new MobEffect(MobEffects.REGENERATION, 1800)}));
    public static final PotionRegistry I = a("strong_regeneration", new PotionRegistry("regeneration", new MobEffect[] { new MobEffect(MobEffects.REGENERATION, 450, 1)}));
    public static final PotionRegistry J = a("strength", new PotionRegistry(new MobEffect[] { new MobEffect(MobEffects.INCREASE_DAMAGE, 3600)}));
    public static final PotionRegistry K = a("long_strength", new PotionRegistry("strength", new MobEffect[] { new MobEffect(MobEffects.INCREASE_DAMAGE, 9600)}));
    public static final PotionRegistry L = a("strong_strength", new PotionRegistry("strength", new MobEffect[] { new MobEffect(MobEffects.INCREASE_DAMAGE, 1800, 1)}));
    public static final PotionRegistry M = a("weakness", new PotionRegistry(new MobEffect[] { new MobEffect(MobEffects.WEAKNESS, 1800)}));
    public static final PotionRegistry N = a("long_weakness", new PotionRegistry("weakness", new MobEffect[] { new MobEffect(MobEffects.WEAKNESS, 4800)}));
    public static final PotionRegistry O = a("luck", new PotionRegistry("luck", new MobEffect[] { new MobEffect(MobEffects.LUCK, 6000)}));
    public static final PotionRegistry P = a("slow_falling", new PotionRegistry(new MobEffect[] { new MobEffect(MobEffects.SLOW_FALLING, 1800)}));
    public static final PotionRegistry Q = a("long_slow_falling", new PotionRegistry("slow_falling", new MobEffect[] { new MobEffect(MobEffects.SLOW_FALLING, 4800)}));

    private static PotionRegistry a(String s, PotionRegistry potionregistry) {
        return (PotionRegistry) IRegistry.a((IRegistry) IRegistry.POTION, s, (Object) potionregistry);
    }
}
