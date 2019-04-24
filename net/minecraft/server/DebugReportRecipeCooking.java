package net.minecraft.server;

import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class DebugReportRecipeCooking {

    private final Item a;
    private final RecipeItemStack b;
    private final float c;
    private final int d;
    private final Advancement.SerializedAdvancement e = Advancement.SerializedAdvancement.a();
    private String f;
    private final RecipeSerializerCooking<?> g;

    private DebugReportRecipeCooking(IMaterial imaterial, RecipeItemStack recipeitemstack, float f, int i, RecipeSerializerCooking<?> recipeserializercooking) {
        this.a = imaterial.getItem();
        this.b = recipeitemstack;
        this.c = f;
        this.d = i;
        this.g = recipeserializercooking;
    }

    public static DebugReportRecipeCooking a(RecipeItemStack recipeitemstack, IMaterial imaterial, float f, int i, RecipeSerializerCooking<?> recipeserializercooking) {
        return new DebugReportRecipeCooking(imaterial, recipeitemstack, f, i, recipeserializercooking);
    }

    public static DebugReportRecipeCooking b(RecipeItemStack recipeitemstack, IMaterial imaterial, float f, int i) {
        return a(recipeitemstack, imaterial, f, i, RecipeSerializer.p);
    }

    public static DebugReportRecipeCooking c(RecipeItemStack recipeitemstack, IMaterial imaterial, float f, int i) {
        return a(recipeitemstack, imaterial, f, i, RecipeSerializer.o);
    }

    public DebugReportRecipeCooking a(String s, CriterionInstance criterioninstance) {
        this.e.a(s, criterioninstance);
        return this;
    }

    public void a(Consumer<DebugReportRecipeData> consumer) {
        this.a(consumer, IRegistry.ITEM.getKey(this.a));
    }

    public void a(Consumer<DebugReportRecipeData> consumer, String s) {
        MinecraftKey minecraftkey = IRegistry.ITEM.getKey(this.a);
        MinecraftKey minecraftkey1 = new MinecraftKey(s);

        if (minecraftkey1.equals(minecraftkey)) {
            throw new IllegalStateException("Recipe " + minecraftkey1 + " should remove its 'save' argument");
        } else {
            this.a(consumer, minecraftkey1);
        }
    }

    public void a(Consumer<DebugReportRecipeData> consumer, MinecraftKey minecraftkey) {
        this.a(minecraftkey);
        this.e.a(new MinecraftKey("recipes/root")).a("has_the_recipe", (CriterionInstance) (new CriterionTriggerRecipeUnlocked.b(minecraftkey))).a(AdvancementRewards.a.c(minecraftkey)).a(AdvancementRequirements.OR);
        consumer.accept(new DebugReportRecipeCooking.a(minecraftkey, this.f == null ? "" : this.f, this.b, this.a, this.c, this.d, this.e, new MinecraftKey(minecraftkey.b(), "recipes/" + this.a.p().c() + "/" + minecraftkey.getKey()), this.g));
    }

    private void a(MinecraftKey minecraftkey) {
        if (this.e.c().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + minecraftkey);
        }
    }

    public static class a implements DebugReportRecipeData {

        private final MinecraftKey a;
        private final String b;
        private final RecipeItemStack c;
        private final Item d;
        private final float e;
        private final int f;
        private final Advancement.SerializedAdvancement g;
        private final MinecraftKey h;
        private final RecipeSerializer<? extends RecipeCooking> i;

        public a(MinecraftKey minecraftkey, String s, RecipeItemStack recipeitemstack, Item item, float f, int i, Advancement.SerializedAdvancement advancement_serializedadvancement, MinecraftKey minecraftkey1, RecipeSerializer<? extends RecipeCooking> recipeserializer) {
            this.a = minecraftkey;
            this.b = s;
            this.c = recipeitemstack;
            this.d = item;
            this.e = f;
            this.f = i;
            this.g = advancement_serializedadvancement;
            this.h = minecraftkey1;
            this.i = recipeserializer;
        }

        @Override
        public void a(JsonObject jsonobject) {
            if (!this.b.isEmpty()) {
                jsonobject.addProperty("group", this.b);
            }

            jsonobject.add("ingredient", this.c.c());
            jsonobject.addProperty("result", IRegistry.ITEM.getKey(this.d).toString());
            jsonobject.addProperty("experience", this.e);
            jsonobject.addProperty("cookingtime", this.f);
        }

        @Override
        public RecipeSerializer<?> c() {
            return this.i;
        }

        @Override
        public MinecraftKey b() {
            return this.a;
        }

        @Nullable
        @Override
        public JsonObject d() {
            return this.g.b();
        }

        @Nullable
        @Override
        public MinecraftKey e() {
            return this.h;
        }
    }
}
