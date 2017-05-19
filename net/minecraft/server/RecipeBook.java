package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.SortedSet;

public class RecipeBook {

    protected final List<IRecipe> a = Lists.newArrayList();
    protected final SortedSet<IRecipe> b = Sets.newTreeSet();
    protected boolean c;
    protected boolean d;

    public RecipeBook() {}

    public SortedSet<IRecipe> a() {
        return this.b;
    }

    public void a(RecipeBook recipebook) {
        this.b.clear();
        this.a.clear();
        this.b.addAll(recipebook.a());
        this.a.addAll(recipebook.b());
    }

    public List<IRecipe> b() {
        return this.a;
    }

    public boolean a(IRecipe irecipe) {
        return irecipe.c() ? false : this.b.add(irecipe);
    }

    public void b(IRecipe irecipe) {
        this.b.remove(irecipe);
        this.a.remove(irecipe);
    }

    public void d(IRecipe irecipe) {
        this.a.remove(irecipe);
    }

    public void a(boolean flag) {
        this.c = flag;
    }

    public void b(boolean flag) {
        this.d = flag;
    }

    public boolean f(IRecipe irecipe) {
        return this.b.contains(irecipe);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object instanceof RecipeBook) {
            RecipeBook recipebook = (RecipeBook) object;

            return this.c != recipebook.c ? false : (this.d != recipebook.d ? false : (!this.a.equals(recipebook.a) ? false : this.b.equals(recipebook.b)));
        } else {
            return false;
        }
    }

    public int hashCode() {
        int i = this.a.hashCode();

        i = 31 * i + this.b.hashCode();
        i = 31 * i + (this.c ? 1 : 0);
        i = 31 * i + (this.d ? 1 : 0);
        return i;
    }
}
