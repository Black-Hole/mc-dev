package net.minecraft.server;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

public class ArgumentEntitySummon implements ArgumentType<MinecraftKey> {

    private static final Collection<String> b = Arrays.asList(new String[] { "minecraft:pig", "cow"});
    public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("entity.notFound", new Object[] { object});
    });

    public ArgumentEntitySummon() {}

    public static ArgumentEntitySummon a() {
        return new ArgumentEntitySummon();
    }

    public static MinecraftKey a(CommandContext<CommandListenerWrapper> commandcontext, String s) throws CommandSyntaxException {
        return a((MinecraftKey) commandcontext.getArgument(s, MinecraftKey.class));
    }

    private static final MinecraftKey a(MinecraftKey minecraftkey) throws CommandSyntaxException {
        EntityTypes entitytypes = (EntityTypes) EntityTypes.REGISTRY.get(minecraftkey);

        if (entitytypes != null && entitytypes.b()) {
            return minecraftkey;
        } else {
            throw ArgumentEntitySummon.a.create(minecraftkey);
        }
    }

    public MinecraftKey a(StringReader stringreader) throws CommandSyntaxException {
        return a(MinecraftKey.a(stringreader));
    }

    public Collection<String> getExamples() {
        return ArgumentEntitySummon.b;
    }

    public Object parse(StringReader stringreader) throws CommandSyntaxException {
        return this.a(stringreader);
    }
}
