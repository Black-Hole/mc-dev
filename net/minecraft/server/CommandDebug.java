package net.minecraft.server;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommandDebug {

    private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ChatMessage("commands.debug.notRunning", new Object[0]));
    private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ChatMessage("commands.debug.alreadyRunning", new Object[0]));

    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> com_mojang_brigadier_commanddispatcher) {
        com_mojang_brigadier_commanddispatcher.register((LiteralArgumentBuilder) ((LiteralArgumentBuilder) ((LiteralArgumentBuilder) CommandDispatcher.a("debug").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(3);
        })).then(CommandDispatcher.a("start").executes((commandcontext) -> {
            return a((CommandListenerWrapper) commandcontext.getSource());
        }))).then(CommandDispatcher.a("stop").executes((commandcontext) -> {
            return b((CommandListenerWrapper) commandcontext.getSource());
        })));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper) throws CommandSyntaxException {
        MinecraftServer minecraftserver = commandlistenerwrapper.getServer();
        GameProfiler gameprofiler = minecraftserver.getMethodProfiler();

        if (gameprofiler.d().a()) {
            throw CommandDebug.b.create();
        } else {
            minecraftserver.aj();
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.debug.started", new Object[] { "Started the debug profiler. Type '/debug stop' to stop it."}), true);
            return 0;
        }
    }

    private static int b(CommandListenerWrapper commandlistenerwrapper) throws CommandSyntaxException {
        MinecraftServer minecraftserver = commandlistenerwrapper.getServer();
        GameProfiler gameprofiler = minecraftserver.getMethodProfiler();

        if (!gameprofiler.d().a()) {
            throw CommandDebug.a.create();
        } else {
            MethodProfilerResults methodprofilerresults = gameprofiler.d().b();
            File file = new File(minecraftserver.d("debug"), "profile-results-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + ".txt");

            methodprofilerresults.a(file);
            float f = (float) methodprofilerresults.f() / 1.0E9F;
            float f1 = (float) methodprofilerresults.g() / f;

            commandlistenerwrapper.sendMessage(new ChatMessage("commands.debug.stopped", new Object[] { String.format(Locale.ROOT, "%.2f", f), methodprofilerresults.g(), String.format("%.2f", f1)}), true);
            return MathHelper.d(f1);
        }
    }
}
