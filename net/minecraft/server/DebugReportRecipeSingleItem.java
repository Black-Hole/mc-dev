package net.minecraft.server;

import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class DebugReportRecipeSingleItem {

    private final Item a;
    private final RecipeItemStack b;
    private final int c;
    private final Advancement.SerializedAdvancement d = Advancement.SerializedAdvancement.a();
    private String e;
    private final RecipeSerializer<?> f;

    public DebugReportRecipeSingleItem(RecipeSerializer<?> recipeserializer, RecipeItemStack recipeitemstack, IMaterial imaterial, int i) {
        this.f = recipeserializer;
        this.a = imaterial.getItem();
        this.b = recipeitemstack;
        this.c = i;
    }

    public static DebugReportRecipeSingleItem a(RecipeItemStack recipeitemstack, IMaterial imaterial) {
        return new DebugReportRecipeSingleItem(RecipeSerializer.s, recipeitemstack, imaterial, 1);
    }

    public static DebugReportRecipeSingleItem a(RecipeItemStack recipeitemstack, IMaterial imaterial, int i) {
        return new DebugReportRecipeSingleItem(RecipeSerializer.s, recipeitemstack, imaterial, i);
    }

    public DebugReportRecipeSingleItem a(String s, CriterionInstance criterioninstance) {
        this.d.a(s, criterioninstance);
        return this;
    }

    public void a(Consumer<DebugReportRecipeData> consumer, String s) {
        MinecraftKey minecraftkey = IRegistry.ITEM.getKey(this.a);

        if ((new MinecraftKey(s)).equals(minecraftkey)) {
            throw new IllegalStateException("Single Item Recipe " + s + " should remove its 'save' argument");
        } else {
            this.a(consumer, new MinecraftKey(s));
        }
    }

    public void a(Consumer<DebugReportRecipeData> consumer, MinecraftKey minecraftkey) {
        this.a(minecraftkey);
        this.d.a(new MinecraftKey("recipes/root")).a("has_the_recipe", (CriterionInstance) (new CriterionTriggerRecipeUnlocked.b(minecraftkey))).a(AdvancementRewards.a.c(minecraftkey)).a(AdvancementRequirements.OR);
        consumer.accept(new DebugReportRecipeSingleItem.a(minecraftkey, this.f, this.e == null ? "" : this.e, this.b, this.a, this.c, this.d, new MinecraftKey(minecraftkey.b(), "recipes/" + this.a.p().c() + "/" + minecraftkey.getKey())));
    }

    private void a(MinecraftKey minecraftkey) {
        if (this.d.c().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + minecraftkey);
        }
    }

    public static class a implements DebugReportRecipeData {

        private final MinecraftKey a;
        private final String b;
        private final RecipeItemStack c;
        private final Item d;
        private final int e;
        private final Advancement.SerializedAdvancement f;
        private final MinecraftKey g;
        private final RecipeSerializer<?> h;

        public a(MinecraftKey minecraftkey, RecipeSerializer<?> recipeserializer, String s, RecipeItemStack recipeitemstack, Item item, int i, Advancement.SerializedAdvancement advancement_serializedadvancement, MinecraftKey minecraftkey1) {
            this.a = minecraftkey;
            this.h = recipeserializer;
            this.b = s;
            this.c = recipeitemstack;
            this.d = item;
            this.e = i;
            this.f = advancement_serializedadvancement;
            this.g = minecraftkey1;
        }

        @Override
        public void a(JsonObject jsonobject) {
            if (!this.b.isEmpty()) {
                jsonobject.addProperty("group", this.b);
            }

            jsonobject.add("ingredient", this.c.c());
            jsonobject.addProperty("result", IRegistry.ITEM.getKey(this.d).toString());
            jsonobject.addProperty("count", this.e);
        }

        @Override
        public MinecraftKey b() {
            return this.a;
        }

        @Override
        public RecipeSerializer<?> c() {
            return this.h;
        }

        @Nullable
        @Override
        public JsonObject d() {
            return this.f.b();
        }

        @Nullable
        @Override
        public MinecraftKey e() {
            return this.g;
        }
    }
}
