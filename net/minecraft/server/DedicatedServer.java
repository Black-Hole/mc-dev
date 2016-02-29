package net.minecraft.server;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Proxy;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DedicatedServer extends MinecraftServer implements IMinecraftServer {

    private static final Logger LOGGER = LogManager.getLogger();
    private final List<ServerCommand> serverCommandQueue = Collections.synchronizedList(Lists.newArrayList());
    private RemoteStatusListener m;
    public final RemoteControlCommandListener remoteControlCommandListener = new RemoteControlCommandListener(this);
    private RemoteControlListener o;
    public PropertyManager propertyManager;
    private EULA q;
    private boolean generateStructures;
    private WorldSettings.EnumGamemode s;
    private boolean t;

    public DedicatedServer(File file, DataConverterManager dataconvertermanager, YggdrasilAuthenticationService yggdrasilauthenticationservice, MinecraftSessionService minecraftsessionservice, GameProfileRepository gameprofilerepository, UserCache usercache) {
        super(file, Proxy.NO_PROXY, dataconvertermanager, yggdrasilauthenticationservice, minecraftsessionservice, gameprofilerepository, usercache);
        Thread thread = new Thread("Server Infinisleeper") {
            {
                this.setDaemon(true);
                this.start();
            }

            public void run() {
                while (true) {
                    try {
                        Thread.sleep(2147483647L);
                    } catch (InterruptedException interruptedexception) {
                        ;
                    }
                }
            }
        };
    }

    protected boolean init() throws IOException {
        Thread thread = new Thread("Server console handler") {
            public void run() {
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(System.in));

                String s;

                try {
                    while (!DedicatedServer.this.isStopped() && DedicatedServer.this.isRunning() && (s = bufferedreader.readLine()) != null) {
                        DedicatedServer.this.issueCommand(s, DedicatedServer.this);
                    }
                } catch (IOException ioexception) {
                    DedicatedServer.LOGGER.error("Exception handling console input", ioexception);
                }

            }
        };

        thread.setDaemon(true);
        thread.start();
        DedicatedServer.LOGGER.info("Starting minecraft server version 1.9");
        if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
            DedicatedServer.LOGGER.warn("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
        }

        DedicatedServer.LOGGER.info("Loading properties");
        this.propertyManager = new PropertyManager(new File("server.properties"));
        this.q = new EULA(new File("eula.txt"));
        if (!this.q.a()) {
            DedicatedServer.LOGGER.info("You need to agree to the EULA in order to run the server. Go to eula.txt for more info.");
            this.q.b();
            return false;
        } else {
            if (this.R()) {
                this.c("127.0.0.1");
            } else {
                this.setOnlineMode(this.propertyManager.getBoolean("online-mode", true));
                this.c(this.propertyManager.getString("server-ip", ""));
            }

            this.setSpawnAnimals(this.propertyManager.getBoolean("spawn-animals", true));
            this.setSpawnNPCs(this.propertyManager.getBoolean("spawn-npcs", true));
            this.setPVP(this.propertyManager.getBoolean("pvp", true));
            this.setAllowFlight(this.propertyManager.getBoolean("allow-flight", false));
            this.setResourcePack(this.propertyManager.getString("resource-pack", ""), this.aK());
            this.setMotd(this.propertyManager.getString("motd", "A Minecraft Server"));
            this.setForceGamemode(this.propertyManager.getBoolean("force-gamemode", false));
            this.setIdleTimeout(this.propertyManager.getInt("player-idle-timeout", 0));
            if (this.propertyManager.getInt("difficulty", 1) < 0) {
                this.propertyManager.setProperty("difficulty", Integer.valueOf(0));
            } else if (this.propertyManager.getInt("difficulty", 1) > 3) {
                this.propertyManager.setProperty("difficulty", Integer.valueOf(3));
            }

            this.generateStructures = this.propertyManager.getBoolean("generate-structures", true);
            int i = this.propertyManager.getInt("gamemode", WorldSettings.EnumGamemode.SURVIVAL.getId());

            this.s = WorldSettings.a(i);
            DedicatedServer.LOGGER.info("Default game type: " + this.s);
            InetAddress inetaddress = null;

            if (!this.getServerIp().isEmpty()) {
                inetaddress = InetAddress.getByName(this.getServerIp());
            }

            if (this.P() < 0) {
                this.setPort(this.propertyManager.getInt("server-port", 25565));
            }

            DedicatedServer.LOGGER.info("Generating keypair");
            this.a(MinecraftEncryption.b());
            DedicatedServer.LOGGER.info("Starting Minecraft server on " + (this.getServerIp().isEmpty() ? "*" : this.getServerIp()) + ":" + this.P());

            try {
                this.am().a(inetaddress, this.P());
            } catch (IOException ioexception) {
                DedicatedServer.LOGGER.warn("**** FAILED TO BIND TO PORT!");
                DedicatedServer.LOGGER.warn("The exception was: {}", new Object[] { ioexception.toString()});
                DedicatedServer.LOGGER.warn("Perhaps a server is already running on that port?");
                return false;
            }

            if (!this.getOnlineMode()) {
                DedicatedServer.LOGGER.warn("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
                DedicatedServer.LOGGER.warn("The server will make no attempt to authenticate usernames. Beware.");
                DedicatedServer.LOGGER.warn("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
                DedicatedServer.LOGGER.warn("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
            }

            if (this.aO()) {
                this.getUserCache().c();
            }

            if (!NameReferencingFileConverter.a(this.propertyManager)) {
                return false;
            } else {
                this.a((PlayerList) (new DedicatedPlayerList(this)));
                long j = System.nanoTime();

                if (this.S() == null) {
                    this.setWorld(this.propertyManager.getString("level-name", "world"));
                }

                String s = this.propertyManager.getString("level-seed", "");
                String s1 = this.propertyManager.getString("level-type", "DEFAULT");
                String s2 = this.propertyManager.getString("generator-settings", "");
                long k = (new Random()).nextLong();

                if (!s.isEmpty()) {
                    try {
                        long l = Long.parseLong(s);

                        if (l != 0L) {
                            k = l;
                        }
                    } catch (NumberFormatException numberformatexception) {
                        k = (long) s.hashCode();
                    }
                }

                WorldType worldtype = WorldType.getType(s1);

                if (worldtype == null) {
                    worldtype = WorldType.NORMAL;
                }

                this.ax();
                this.getEnableCommandBlock();
                this.q();
                this.getSnooperEnabled();
                this.aF();
                this.c(this.propertyManager.getInt("max-build-height", 256));
                this.c((this.getMaxBuildHeight() + 8) / 16 * 16);
                this.c(MathHelper.clamp(this.getMaxBuildHeight(), 64, 256));
                this.propertyManager.setProperty("max-build-height", Integer.valueOf(this.getMaxBuildHeight()));
                TileEntitySkull.a(this.getUserCache());
                TileEntitySkull.a(this.ay());
                UserCache.a(this.getOnlineMode());
                DedicatedServer.LOGGER.info("Preparing level \"" + this.S() + "\"");
                this.a(this.S(), this.S(), k, worldtype, s2);
                long i1 = System.nanoTime() - j;
                String s3 = String.format("%.3fs", new Object[] { Double.valueOf((double) i1 / 1.0E9D)});

                DedicatedServer.LOGGER.info("Done (" + s3 + ")! For help, type \"help\" or \"?\"");
                if (this.propertyManager.getBoolean("enable-query", false)) {
                    DedicatedServer.LOGGER.info("Starting GS4 status listener");
                    this.m = new RemoteStatusListener(this);
                    this.m.a();
                }

                if (this.propertyManager.getBoolean("enable-rcon", false)) {
                    DedicatedServer.LOGGER.info("Starting remote control listener");
                    this.o = new RemoteControlListener(this);
                    this.o.a();
                }

                if (this.aP() > 0L) {
                    Thread thread1 = new Thread(new ThreadWatchdog(this));

                    thread1.setName("Server Watchdog");
                    thread1.setDaemon(true);
                    thread1.start();
                }

                return true;
            }
        }
    }

    public String aK() {
        String s = "";

        if (this.propertyManager.a("resource-pack-hash") && !this.propertyManager.a("resource-pack-sha1")) {
            DedicatedServer.LOGGER.warn("ressource-pack-hash is depricated. Please use ressource-pack-sha1 instead.");
            s = this.propertyManager.getString("resource-pack-hash", "");
            this.propertyManager.getString("resource-pack-sha1", s);
            this.propertyManager.b("resource-pack-hash");
        }

        if (this.propertyManager.a("resource-pack-hash") && this.propertyManager.a("resource-pack-sha1")) {
            DedicatedServer.LOGGER.warn("ressource-pack-hash is depricated and found along side resource-pack-sha1. resource-pack-hash will be ignored.");
        }

        s = this.propertyManager.getString("resource-pack-sha1", "");
        if (!s.equals("") && !s.matches("^[a-f0-9]{40}$")) {
            DedicatedServer.LOGGER.warn("Invalid sha1 for ressource-pack-sha1");
        }

        if (!this.propertyManager.getString("resource-pack", "").equals("") && s.equals("")) {
            DedicatedServer.LOGGER.warn("You specified a resource pack without providing a sha1 hash. Pack will be updated on the client only if you change the name of the pack.");
        }

        return s;
    }

    public void setGamemode(WorldSettings.EnumGamemode worldsettings_enumgamemode) {
        super.setGamemode(worldsettings_enumgamemode);
        this.s = worldsettings_enumgamemode;
    }

    public boolean getGenerateStructures() {
        return this.generateStructures;
    }

    public WorldSettings.EnumGamemode getGamemode() {
        return this.s;
    }

    public EnumDifficulty getDifficulty() {
        return EnumDifficulty.getById(this.propertyManager.getInt("difficulty", EnumDifficulty.NORMAL.a()));
    }

    public boolean isHardcore() {
        return this.propertyManager.getBoolean("hardcore", false);
    }

    protected void a(CrashReport crashreport) {}

    public CrashReport b(CrashReport crashreport) {
        crashreport = super.b(crashreport);
        crashreport.g().a("Is Modded", new Callable() {
            public String a() throws Exception {
                String s = DedicatedServer.this.getServerModName();

                return !s.equals("vanilla") ? "Definitely; Server brand changed to \'" + s + "\'" : "Unknown (can\'t tell)";
            }

            public Object call() throws Exception {
                return this.a();
            }
        });
        crashreport.g().a("Type", new Callable() {
            public String a() throws Exception {
                return "Dedicated Server (map_server.txt)";
            }

            public Object call() throws Exception {
                return this.a();
            }
        });
        return crashreport;
    }

    protected void B() {
        System.exit(0);
    }

    protected void D() {
        super.D();
        this.aL();
    }

    public boolean getAllowNether() {
        return this.propertyManager.getBoolean("allow-nether", true);
    }

    public boolean getSpawnMonsters() {
        return this.propertyManager.getBoolean("spawn-monsters", true);
    }

    public void a(MojangStatisticsGenerator mojangstatisticsgenerator) {
        mojangstatisticsgenerator.a("whitelist_enabled", Boolean.valueOf(this.aM().getHasWhitelist()));
        mojangstatisticsgenerator.a("whitelist_count", Integer.valueOf(this.aM().getWhitelisted().length));
        super.a(mojangstatisticsgenerator);
    }

    public boolean getSnooperEnabled() {
        return this.propertyManager.getBoolean("snooper-enabled", true);
    }

    public void issueCommand(String s, ICommandListener icommandlistener) {
        this.serverCommandQueue.add(new ServerCommand(s, icommandlistener));
    }

    public void aL() {
        while (!this.serverCommandQueue.isEmpty()) {
            ServerCommand servercommand = (ServerCommand) this.serverCommandQueue.remove(0);

            this.getCommandHandler().a(servercommand.source, servercommand.command);
        }

    }

    public boolean aa() {
        return true;
    }

    public boolean ae() {
        return this.propertyManager.getBoolean("use-native-transport", true);
    }

    public DedicatedPlayerList aM() {
        return (DedicatedPlayerList) super.getPlayerList();
    }

    public int a(String s, int i) {
        return this.propertyManager.getInt(s, i);
    }

    public String a(String s, String s1) {
        return this.propertyManager.getString(s, s1);
    }

    public boolean a(String s, boolean flag) {
        return this.propertyManager.getBoolean(s, flag);
    }

    public void a(String s, Object object) {
        this.propertyManager.setProperty(s, object);
    }

    public void a() {
        this.propertyManager.savePropertiesFile();
    }

    public String b() {
        File file = this.propertyManager.c();

        return file != null ? file.getAbsolutePath() : "No settings file";
    }

    public String d_() {
        return this.getServerIp();
    }

    public int e_() {
        return this.P();
    }

    public String f_() {
        return this.getMotd();
    }

    public void aN() {
        ServerGUI.a(this);
        this.t = true;
    }

    public boolean ao() {
        return this.t;
    }

    public String a(WorldSettings.EnumGamemode worldsettings_enumgamemode, boolean flag) {
        return "";
    }

    public boolean getEnableCommandBlock() {
        return this.propertyManager.getBoolean("enable-command-block", false);
    }

    public int getSpawnProtection() {
        return this.propertyManager.getInt("spawn-protection", super.getSpawnProtection());
    }

    public boolean a(World world, BlockPosition blockposition, EntityHuman entityhuman) {
        if (world.worldProvider.getDimensionManager().getDimensionID() != 0) {
            return false;
        } else if (this.aM().getOPs().isEmpty()) {
            return false;
        } else if (this.aM().isOp(entityhuman.getProfile())) {
            return false;
        } else if (this.getSpawnProtection() <= 0) {
            return false;
        } else {
            BlockPosition blockposition1 = world.getSpawn();
            int i = MathHelper.a(blockposition.getX() - blockposition1.getX());
            int j = MathHelper.a(blockposition.getZ() - blockposition1.getZ());
            int k = Math.max(i, j);

            return k <= this.getSpawnProtection();
        }
    }

    public int q() {
        return this.propertyManager.getInt("op-permission-level", 4);
    }

    public void setIdleTimeout(int i) {
        super.setIdleTimeout(i);
        this.propertyManager.setProperty("player-idle-timeout", Integer.valueOf(i));
        this.a();
    }

    public boolean r() {
        return this.propertyManager.getBoolean("broadcast-rcon-to-ops", true);
    }

    public boolean s() {
        return this.propertyManager.getBoolean("broadcast-console-to-ops", true);
    }

    public boolean ax() {
        return this.propertyManager.getBoolean("announce-player-achievements", true);
    }

    public int aD() {
        int i = this.propertyManager.getInt("max-world-size", super.aD());

        if (i < 1) {
            i = 1;
        } else if (i > super.aD()) {
            i = super.aD();
        }

        return i;
    }

    public int aF() {
        return this.propertyManager.getInt("network-compression-threshold", super.aF());
    }

    protected boolean aO() {
        boolean flag = false;

        int i;

        for (i = 0; !flag && i <= 2; ++i) {
            if (i > 0) {
                DedicatedServer.LOGGER.warn("Encountered a problem while converting the user banlist, retrying in a few seconds");
                this.aR();
            }

            flag = NameReferencingFileConverter.a((MinecraftServer) this);
        }

        boolean flag1 = false;

        for (i = 0; !flag1 && i <= 2; ++i) {
            if (i > 0) {
                DedicatedServer.LOGGER.warn("Encountered a problem while converting the ip banlist, retrying in a few seconds");
                this.aR();
            }

            flag1 = NameReferencingFileConverter.b((MinecraftServer) this);
        }

        boolean flag2 = false;

        for (i = 0; !flag2 && i <= 2; ++i) {
            if (i > 0) {
                DedicatedServer.LOGGER.warn("Encountered a problem while converting the op list, retrying in a few seconds");
                this.aR();
            }

            flag2 = NameReferencingFileConverter.c((MinecraftServer) this);
        }

        boolean flag3 = false;

        for (i = 0; !flag3 && i <= 2; ++i) {
            if (i > 0) {
                DedicatedServer.LOGGER.warn("Encountered a problem while converting the whitelist, retrying in a few seconds");
                this.aR();
            }

            flag3 = NameReferencingFileConverter.d((MinecraftServer) this);
        }

        boolean flag4 = false;

        for (i = 0; !flag4 && i <= 2; ++i) {
            if (i > 0) {
                DedicatedServer.LOGGER.warn("Encountered a problem while converting the player save files, retrying in a few seconds");
                this.aR();
            }

            flag4 = NameReferencingFileConverter.a(this, this.propertyManager);
        }

        return flag || flag1 || flag2 || flag3 || flag4;
    }

    private void aR() {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException interruptedexception) {
            ;
        }
    }

    public long aP() {
        return this.propertyManager.getLong("max-tick-time", TimeUnit.MINUTES.toMillis(1L));
    }

    public String getPlugins() {
        return "";
    }

    public String executeRemoteCommand(String s) {
        this.remoteControlCommandListener.clearMessages();
        this.b.a(this.remoteControlCommandListener, s);
        return this.remoteControlCommandListener.getMessages();
    }

    public PlayerList getPlayerList() {
        return this.aM();
    }
}
