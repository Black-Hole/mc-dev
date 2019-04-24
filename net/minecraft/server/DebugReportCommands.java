package net.minecraft.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.brigadier.tree.CommandNode;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.nio.file.Paths;
import java.util.UUID;

public class DebugReportCommands implements DebugReportProvider {

    private static final Gson b = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private final DebugReportGenerator c;

    public DebugReportCommands(DebugReportGenerator debugreportgenerator) {
        this.c = debugreportgenerator;
    }

    @Override
    public void a(HashCache hashcache) throws IOException {
        YggdrasilAuthenticationService yggdrasilauthenticationservice = new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString());
        MinecraftSessionService minecraftsessionservice = yggdrasilauthenticationservice.createMinecraftSessionService();
        GameProfileRepository gameprofilerepository = yggdrasilauthenticationservice.createProfileRepository();
        File file = new File(this.c.b().toFile(), "tmp");
        UserCache usercache = new UserCache(gameprofilerepository, new File(file, MinecraftServer.b.getName()));
        DedicatedServerSettings dedicatedserversettings = new DedicatedServerSettings(Paths.get("server.properties"));
        DedicatedServer dedicatedserver = new DedicatedServer(file, dedicatedserversettings, DataConverterRegistry.a(), yggdrasilauthenticationservice, minecraftsessionservice, gameprofilerepository, usercache, WorldLoadListenerLogger::new, dedicatedserversettings.getProperties().levelName);
        java.nio.file.Path java_nio_file_path = this.c.b().resolve("reports/commands.json");
        com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> com_mojang_brigadier_commanddispatcher = dedicatedserver.getCommandDispatcher().a();

        DebugReportProvider.a(DebugReportCommands.b, hashcache, ArgumentRegistry.a(com_mojang_brigadier_commanddispatcher, (CommandNode) com_mojang_brigadier_commanddispatcher.getRoot()), java_nio_file_path);
    }

    @Override
    public String a() {
        return "Command Syntax";
    }
}
