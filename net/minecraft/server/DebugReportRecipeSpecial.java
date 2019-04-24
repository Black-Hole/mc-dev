package net.minecraft.server;

import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class DebugReportRecipeSpecial {

    private final RecipeSerializerComplex<?> a;

    public DebugReportRecipeSpecial(RecipeSerializerComplex<?> recipeserializercomplex) {
        this.a = recipeserializercomplex;
    }

    public static DebugReportRecipeSpecial a(RecipeSerializerComplex<?> recipeserializercomplex) {
        return new DebugReportRecipeSpecial(recipeserializercomplex);
    }

    public void a(Consumer<DebugReportRecipeData> consumer, final String s) {
        consumer.accept(new DebugReportRecipeData() {
            @Override
            public void a(JsonObject jsonobject) {}

            @Override
            public RecipeSerializer<?> c() {
                return DebugReportRecipeSpecial.this.a;
            }

            @Override
            public MinecraftKey b() {
                return new MinecraftKey(s);
            }

            @Nullable
            @Override
            public JsonObject d() {
                return null;
            }

            @Override
            public MinecraftKey e() {
                return new MinecraftKey("");
            }
        });
    }
}
