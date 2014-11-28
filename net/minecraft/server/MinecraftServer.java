package net.minecraft.server;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.Proxy;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class MinecraftServer implements ICommandListener, Runnable, IAsyncTaskHandler, IMojangStatistics {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final File a = new File("usercache.json");
    private static MinecraftServer k;
    public Convertable convertable;
    private final MojangStatisticsGenerator m = new MojangStatisticsGenerator("server", this, ax());
    public File universe;
    private final List o = Lists.newArrayList();
    private final ICommandHandler p;
    public final MethodProfiler methodProfiler = new MethodProfiler();
    private final ServerConnection q;
    private final ServerPing r = new ServerPing();
    private final Random s = new Random();
    private String serverIp;
    private int u = -1;
    public WorldServer[] worldServer;
    private PlayerList v;
    private boolean isRunning = true;
    private boolean isStopped;
    private int ticks;
    protected final Proxy d;
    public String e;
    public int f;
    private boolean onlineMode;
    private boolean spawnAnimals;
    private boolean spawnNPCs;
    private boolean pvpMode;
    private boolean allowFlight;
    private String motd;
    private int F;
    private int G = 0;
    public final long[] g = new long[100];
    public long[][] h;
    private KeyPair H;
    private String I;
    private String J;
    private boolean demoMode;
    private boolean M;
    private boolean N;
    private String O = "";
    private String P = "";
    private boolean Q;
    private long R;
    private String S;
    private boolean T;
    private boolean U;
    private final YggdrasilAuthenticationService V;
    private final MinecraftSessionService W;
    private long X = 0L;
    private final GameProfileRepository Y;
    private final UserCache Z;
    protected final Queue i = Queues.newArrayDeque();
    private Thread serverThread;
    private long ab = ax();

    public MinecraftServer(File file, Proxy proxy, File file1) {
        this.d = proxy;
        MinecraftServer.k = this;
        this.universe = file;
        this.q = new ServerConnection(this);
        this.Z = new UserCache(this, file1);
        this.p = this.h();
        this.convertable = new WorldLoaderServer(file);
        this.V = new YggdrasilAuthenticationService(proxy, UUID.randomUUID().toString());
        this.W = this.V.createMinecraftSessionService();
        this.Y = this.V.createProfileRepository();
    }

    protected CommandDispatcher h() {
        return new CommandDispatcher();
    }

    protected abstract boolean init();

    protected void a(String s) {
        if (this.getConvertable().isConvertable(s)) {
            MinecraftServer.LOGGER.info("Converting map!");
            this.b("menu.convertingLevel");
            this.getConvertable().convert(s, new ConvertProgressUpdater(this));
        }

    }

    protected synchronized void b(String s) {
        this.S = s;
    }

    protected void a(String s, String s1, long i, WorldType worldtype, String s2) {
        this.a(s);
        this.b("menu.loadingLevel");
        this.worldServer = new WorldServer[3];
        this.h = new long[this.worldServer.length][100];
        IDataManager idatamanager = this.convertable.a(s, true);

        this.a(this.T(), idatamanager);
        WorldData worlddata = idatamanager.getWorldData();
        WorldSettings worldsettings;

        if (worlddata == null) {
            if (this.W()) {
                worldsettings = DemoWorldServer.a;
            } else {
                worldsettings = new WorldSettings(i, this.getGamemode(), this.getGenerateStructures(), this.isHardcore(), worldtype);
                worldsettings.setGeneratorSettings(s2);
                if (this.M) {
                    worldsettings.a();
                }
            }

            worlddata = new WorldData(worldsettings, s1);
        } else {
            worlddata.a(s1);
            worldsettings = new WorldSettings(worlddata);
        }

        for (int j = 0; j < this.worldServer.length; ++j) {
            byte b0 = 0;

            if (j == 1) {
                b0 = -1;
            }

            if (j == 2) {
                b0 = 1;
            }

            if (j == 0) {
                if (this.W()) {
                    this.worldServer[j] = (WorldServer) (new DemoWorldServer(this, idatamanager, worlddata, b0, this.methodProfiler)).b();
                } else {
                    this.worldServer[j] = (WorldServer) (new WorldServer(this, idatamanager, worlddata, b0, this.methodProfiler)).b();
                }

                this.worldServer[j].a(worldsettings);
            } else {
                this.worldServer[j] = (WorldServer) (new SecondaryWorldServer(this, idatamanager, b0, this.worldServer[0], this.methodProfiler)).b();
            }

            this.worldServer[j].addIWorldAccess(new WorldManager(this, this.worldServer[j]));
            if (!this.S()) {
                this.worldServer[j].getWorldData().setGameType(this.getGamemode());
            }
        }

        this.v.setPlayerFileData(this.worldServer);
        this.a(this.getDifficulty());
        this.k();
    }

    protected void k() {
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = true;
        int i = 0;

        this.b("menu.generatingTerrain");
        byte b0 = 0;

        MinecraftServer.LOGGER.info("Preparing start region for level " + b0);
        WorldServer worldserver = this.worldServer[b0];
        BlockPosition blockposition = worldserver.getSpawn();
        long j = ax();

        for (int k = -192; k <= 192 && this.isRunning(); k += 16) {
            for (int l = -192; l <= 192 && this.isRunning(); l += 16) {
                long i1 = ax();

                if (i1 - j > 1000L) {
                    this.a_("Preparing spawn area", i * 100 / 625);
                    j = i1;
                }

                ++i;
                worldserver.chunkProviderServer.getChunkAt(blockposition.getX() + k >> 4, blockposition.getZ() + l >> 4);
            }
        }

        this.q();
    }

    protected void a(String s, IDataManager idatamanager) {
        File file = new File(idatamanager.getDirectory(), "resources.zip");

        if (file.isFile()) {
            this.setResourcePack("level://" + s + "/" + file.getName(), "");
        }

    }

    public abstract boolean getGenerateStructures();

    public abstract EnumGamemode getGamemode();

    public abstract EnumDifficulty getDifficulty();

    public abstract boolean isHardcore();

    public abstract int p();

    protected void a_(String s, int i) {
        this.e = s;
        this.f = i;
        MinecraftServer.LOGGER.info(s + ": " + i + "%");
    }

    protected void q() {
        this.e = null;
        this.f = 0;
    }

    protected void saveChunks(boolean flag) {
        if (!this.N) {
            WorldServer[] aworldserver = this.worldServer;
            int i = aworldserver.length;

            for (int j = 0; j < i; ++j) {
                WorldServer worldserver = aworldserver[j];

                if (worldserver != null) {
                    if (!flag) {
                        MinecraftServer.LOGGER.info("Saving chunks for level \'" + worldserver.getWorldData().getName() + "\'/" + worldserver.worldProvider.getName());
                    }

                    try {
                        worldserver.save(true, (IProgressUpdate) null);
                    } catch (ExceptionWorldConflict exceptionworldconflict) {
                        MinecraftServer.LOGGER.warn(exceptionworldconflict.getMessage());
                    }
                }
            }

        }
    }

    public void stop() {
        if (!this.N) {
            MinecraftServer.LOGGER.info("Stopping server");
            if (this.ao() != null) {
                this.ao().b();
            }

            if (this.v != null) {
                MinecraftServer.LOGGER.info("Saving players");
                this.v.savePlayers();
                this.v.v();
            }

            if (this.worldServer != null) {
                MinecraftServer.LOGGER.info("Saving worlds");
                this.saveChunks(false);

                for (int i = 0; i < this.worldServer.length; ++i) {
                    WorldServer worldserver = this.worldServer[i];

                    worldserver.saveLevel();
                }
            }

            if (this.m.d()) {
                this.m.e();
            }

        }
    }

    public String getServerIp() {
        return this.serverIp;
    }

    public void c(String s) {
        this.serverIp = s;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void safeShutdown() {
        this.isRunning = false;
    }

    public void run() {
        try {
            if (this.init()) {
                this.ab = ax();
                long i = 0L;

                this.r.setMOTD(new ChatComponentText(this.motd));
                this.r.setServerInfo(new ServerPingServerData("1.8", 47));
                this.a(this.r);

                while (this.isRunning) {
                    long j = ax();
                    long k = j - this.ab;

                    if (k > 2000L && this.ab - this.R >= 15000L) {
                        MinecraftServer.LOGGER.warn("Can\'t keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", new Object[] { Long.valueOf(k), Long.valueOf(k / 50L)});
                        k = 2000L;
                        this.R = this.ab;
                    }

                    if (k < 0L) {
                        MinecraftServer.LOGGER.warn("Time ran backwards! Did the system time change?");
                        k = 0L;
                    }

                    i += k;
                    this.ab = j;
                    if (this.worldServer[0].everyoneDeeplySleeping()) {
                        this.y();
                        i = 0L;
                    } else {
                        while (i > 50L) {
                            i -= 50L;
                            this.y();
                        }
                    }

                    Thread.sleep(Math.max(1L, 50L - i));
                    this.Q = true;
                }
            } else {
                this.a((CrashReport) null);
            }
        } catch (Throwable throwable) {
            MinecraftServer.LOGGER.error("Encountered an unexpected exception", throwable);
            CrashReport crashreport = null;

            if (throwable instanceof ReportedException) {
                crashreport = this.b(((ReportedException) throwable).a());
            } else {
                crashreport = this.b(new CrashReport("Exception in server tick loop", throwable));
            }

            File file = new File(new File(this.w(), "crash-reports"), "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-server.txt");

            if (crashreport.a(file)) {
                MinecraftServer.LOGGER.error("This crash report has been saved to: " + file.getAbsolutePath());
            } else {
                MinecraftServer.LOGGER.error("We were unable to save this crash report to disk.");
            }

            this.a(crashreport);
        } finally {
            try {
                this.stop();
                this.isStopped = true;
            } catch (Throwable throwable1) {
                MinecraftServer.LOGGER.error("Exception stopping the server", throwable1);
            } finally {
                this.x();
            }

        }

    }

    private void a(ServerPing serverping) {
        File file = this.d("server-icon.png");

        if (file.isFile()) {
            ByteBuf bytebuf = Unpooled.buffer();

            try {
                BufferedImage bufferedimage = ImageIO.read(file);

                Validate.validState(bufferedimage.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
                Validate.validState(bufferedimage.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
                ImageIO.write(bufferedimage, "PNG", new ByteBufOutputStream(bytebuf));
                ByteBuf bytebuf1 = Base64.encode(bytebuf);

                serverping.setFavicon("data:image/png;base64," + bytebuf1.toString(Charsets.UTF_8));
            } catch (Exception exception) {
                MinecraftServer.LOGGER.error("Couldn\'t load server icon", exception);
            } finally {
                bytebuf.release();
            }
        }

    }

    public File w() {
        return new File(".");
    }

    protected void a(CrashReport crashreport) {}

    protected void x() {}

    protected void y() {
        long i = System.nanoTime();

        ++this.ticks;
        if (this.T) {
            this.T = false;
            this.methodProfiler.a = true;
            this.methodProfiler.a();
        }

        this.methodProfiler.a("root");
        this.z();
        if (i - this.X >= 5000000000L) {
            this.X = i;
            this.r.setPlayerSample(new ServerPingPlayerSample(this.H(), this.G()));
            GameProfile[] agameprofile = new GameProfile[Math.min(this.G(), 12)];
            int j = MathHelper.nextInt(this.s, 0, this.G() - agameprofile.length);

            for (int k = 0; k < agameprofile.length; ++k) {
                agameprofile[k] = ((EntityPlayer) this.v.players.get(j + k)).getProfile();
            }

            Collections.shuffle(Arrays.asList(agameprofile));
            this.r.b().a(agameprofile);
        }

        if (this.ticks % 900 == 0) {
            this.methodProfiler.a("save");
            this.v.savePlayers();
            this.saveChunks(true);
            this.methodProfiler.b();
        }

        this.methodProfiler.a("tallying");
        this.g[this.ticks % 100] = System.nanoTime() - i;
        this.methodProfiler.b();
        this.methodProfiler.a("snooper");
        if (!this.m.d() && this.ticks > 100) {
            this.m.a();
        }

        if (this.ticks % 6000 == 0) {
            this.m.b();
        }

        this.methodProfiler.b();
        this.methodProfiler.b();
    }

    public void z() {
        this.methodProfiler.a("jobs");
        Queue queue = this.i;

        synchronized (this.i) {
            while (!this.i.isEmpty()) {
                try {
                    ((FutureTask) this.i.poll()).run();
                } catch (Throwable throwable) {
                    MinecraftServer.LOGGER.fatal(throwable);
                }
            }
        }

        this.methodProfiler.c("levels");

        int i;

        for (i = 0; i < this.worldServer.length; ++i) {
            long j = System.nanoTime();

            if (i == 0 || this.getAllowNether()) {
                WorldServer worldserver = this.worldServer[i];

                this.methodProfiler.a(worldserver.getWorldData().getName());
                if (this.ticks % 20 == 0) {
                    this.methodProfiler.a("timeSync");
                    this.v.a(new PacketPlayOutUpdateTime(worldserver.getTime(), worldserver.getDayTime(), worldserver.getGameRules().getBoolean("doDaylightCycle")), worldserver.worldProvider.getDimension());
                    this.methodProfiler.b();
                }

                this.methodProfiler.a("tick");

                CrashReport crashreport;

                try {
                    worldserver.doTick();
                } catch (Throwable throwable1) {
                    crashreport = CrashReport.a(throwable1, "Exception ticking world");
                    worldserver.a(crashreport);
                    throw new ReportedException(crashreport);
                }

                try {
                    worldserver.tickEntities();
                } catch (Throwable throwable2) {
                    crashreport = CrashReport.a(throwable2, "Exception ticking world entities");
                    worldserver.a(crashreport);
                    throw new ReportedException(crashreport);
                }

                this.methodProfiler.b();
                this.methodProfiler.a("tracker");
                worldserver.getTracker().updatePlayers();
                this.methodProfiler.b();
                this.methodProfiler.b();
            }

            this.h[i][this.ticks % 100] = System.nanoTime() - j;
        }

        this.methodProfiler.c("connection");
        this.ao().c();
        this.methodProfiler.c("players");
        this.v.tick();
        this.methodProfiler.c("tickables");

        for (i = 0; i < this.o.size(); ++i) {
            ((IUpdatePlayerListBox) this.o.get(i)).c();
        }

        this.methodProfiler.b();
    }

    public boolean getAllowNether() {
        return true;
    }

    public void a(IUpdatePlayerListBox iupdateplayerlistbox) {
        this.o.add(iupdateplayerlistbox);
    }

    public static void main(String[] astring) {
        DispenserRegistry.c();

        try {
            boolean flag = true;
            String s = null;
            String s1 = ".";
            String s2 = null;
            boolean flag1 = false;
            boolean flag2 = false;
            int i = -1;

            for (int j = 0; j < astring.length; ++j) {
                String s3 = astring[j];
                String s4 = j == astring.length - 1 ? null : astring[j + 1];
                boolean flag3 = false;

                if (!s3.equals("nogui") && !s3.equals("--nogui")) {
                    if (s3.equals("--port") && s4 != null) {
                        flag3 = true;

                        try {
                            i = Integer.parseInt(s4);
                        } catch (NumberFormatException numberformatexception) {
                            ;
                        }
                    } else if (s3.equals("--singleplayer") && s4 != null) {
                        flag3 = true;
                        s = s4;
                    } else if (s3.equals("--universe") && s4 != null) {
                        flag3 = true;
                        s1 = s4;
                    } else if (s3.equals("--world") && s4 != null) {
                        flag3 = true;
                        s2 = s4;
                    } else if (s3.equals("--demo")) {
                        flag1 = true;
                    } else if (s3.equals("--bonusChest")) {
                        flag2 = true;
                    }
                } else {
                    flag = false;
                }

                if (flag3) {
                    ++j;
                }
            }

            DedicatedServer dedicatedserver = new DedicatedServer(new File(s1));

            if (s != null) {
                dedicatedserver.j(s);
            }

            if (s2 != null) {
                dedicatedserver.setWorld(s2);
            }

            if (i >= 0) {
                dedicatedserver.setPort(i);
            }

            if (flag1) {
                dedicatedserver.b(true);
            }

            if (flag2) {
                dedicatedserver.c(true);
            }

            if (flag && !GraphicsEnvironment.isHeadless()) {
                dedicatedserver.aO();
            }

            dedicatedserver.B();
            Runtime.getRuntime().addShutdownHook(new ThreadShutdown("Server Shutdown Thread", dedicatedserver));
        } catch (Exception exception) {
            MinecraftServer.LOGGER.fatal("Failed to start the minecraft server", exception);
        }

    }

    public void B() {
        this.serverThread = new Thread(this, "Server thread");
        this.serverThread.start();
    }

    public File d(String s) {
        return new File(this.w(), s);
    }

    public void info(String s) {
        MinecraftServer.LOGGER.info(s);
    }

    public void warning(String s) {
        MinecraftServer.LOGGER.warn(s);
    }

    public WorldServer getWorldServer(int i) {
        return i == -1 ? this.worldServer[1] : (i == 1 ? this.worldServer[2] : this.worldServer[0]);
    }

    public String C() {
        return this.serverIp;
    }

    public int D() {
        return this.u;
    }

    public String E() {
        return this.motd;
    }

    public String getVersion() {
        return "1.8";
    }

    public int G() {
        return this.v.getPlayerCount();
    }

    public int H() {
        return this.v.getMaxPlayers();
    }

    public String[] getPlayers() {
        return this.v.g();
    }

    public GameProfile[] J() {
        return this.v.h();
    }

    public String getPlugins() {
        return "";
    }

    public String executeRemoteCommand(String s) {
        RemoteControlCommandListener.getInstance().i();
        this.p.a(RemoteControlCommandListener.getInstance(), s);
        return RemoteControlCommandListener.getInstance().j();
    }

    public boolean isDebugging() {
        return false;
    }

    public void h(String s) {
        MinecraftServer.LOGGER.error(s);
    }

    public void i(String s) {
        if (this.isDebugging()) {
            MinecraftServer.LOGGER.info(s);
        }

    }

    public String getServerModName() {
        return "vanilla";
    }

    public CrashReport b(CrashReport crashreport) {
        crashreport.g().a("Profiler Position", (Callable) (new CrashReportProfilerPosition(this)));
        if (this.v != null) {
            crashreport.g().a("Player Count", (Callable) (new CrashReportPlayerCount(this)));
        }

        return crashreport;
    }

    public List tabCompleteCommand(ICommandListener icommandlistener, String s, BlockPosition blockposition) {
        ArrayList arraylist = Lists.newArrayList();

        if (s.startsWith("/")) {
            s = s.substring(1);
            boolean flag = !s.contains(" ");
            List list = this.p.a(icommandlistener, s, blockposition);

            if (list != null) {
                Iterator iterator = list.iterator();

                while (iterator.hasNext()) {
                    String s1 = (String) iterator.next();

                    if (flag) {
                        arraylist.add("/" + s1);
                    } else {
                        arraylist.add(s1);
                    }
                }
            }

            return arraylist;
        } else {
            String[] astring = s.split(" ", -1);
            String s2 = astring[astring.length - 1];
            String[] astring1 = this.v.g();
            int i = astring1.length;

            for (int j = 0; j < i; ++j) {
                String s3 = astring1[j];

                if (CommandAbstract.a(s2, s3)) {
                    arraylist.add(s3);
                }
            }

            return arraylist;
        }
    }

    public static MinecraftServer getServer() {
        return MinecraftServer.k;
    }

    public boolean N() {
        return this.universe != null;
    }

    public String getName() {
        return "Server";
    }

    public void sendMessage(IChatBaseComponent ichatbasecomponent) {
        MinecraftServer.LOGGER.info(ichatbasecomponent.c());
    }

    public boolean a(int i, String s) {
        return true;
    }

    public ICommandHandler getCommandHandler() {
        return this.p;
    }

    public KeyPair P() {
        return this.H;
    }

    public int Q() {
        return this.u;
    }

    public void setPort(int i) {
        this.u = i;
    }

    public String R() {
        return this.I;
    }

    public void j(String s) {
        this.I = s;
    }

    public boolean S() {
        return this.I != null;
    }

    public String T() {
        return this.J;
    }

    public void setWorld(String s) {
        this.J = s;
    }

    public void a(KeyPair keypair) {
        this.H = keypair;
    }

    public void a(EnumDifficulty enumdifficulty) {
        for (int i = 0; i < this.worldServer.length; ++i) {
            WorldServer worldserver = this.worldServer[i];

            if (worldserver != null) {
                if (worldserver.getWorldData().isHardcore()) {
                    worldserver.getWorldData().setDifficulty(EnumDifficulty.HARD);
                    worldserver.setSpawnFlags(true, true);
                } else if (this.S()) {
                    worldserver.getWorldData().setDifficulty(enumdifficulty);
                    worldserver.setSpawnFlags(worldserver.getDifficulty() != EnumDifficulty.PEACEFUL, true);
                } else {
                    worldserver.getWorldData().setDifficulty(enumdifficulty);
                    worldserver.setSpawnFlags(this.getSpawnMonsters(), this.spawnAnimals);
                }
            }
        }

    }

    protected boolean getSpawnMonsters() {
        return true;
    }

    public boolean W() {
        return this.demoMode;
    }

    public void b(boolean flag) {
        this.demoMode = flag;
    }

    public void c(boolean flag) {
        this.M = flag;
    }

    public Convertable getConvertable() {
        return this.convertable;
    }

    public void Z() {
        this.N = true;
        this.getConvertable().d();

        for (int i = 0; i < this.worldServer.length; ++i) {
            WorldServer worldserver = this.worldServer[i];

            if (worldserver != null) {
                worldserver.saveLevel();
            }
        }

        this.getConvertable().e(this.worldServer[0].getDataManager().g());
        this.safeShutdown();
    }

    public String getResourcePack() {
        return this.O;
    }

    public String getResourcePackHash() {
        return this.P;
    }

    public void setResourcePack(String s, String s1) {
        this.O = s;
        this.P = s1;
    }

    public void a(MojangStatisticsGenerator mojangstatisticsgenerator) {
        mojangstatisticsgenerator.a("whitelist_enabled", Boolean.valueOf(false));
        mojangstatisticsgenerator.a("whitelist_count", Integer.valueOf(0));
        if (this.v != null) {
            mojangstatisticsgenerator.a("players_current", Integer.valueOf(this.G()));
            mojangstatisticsgenerator.a("players_max", Integer.valueOf(this.H()));
            mojangstatisticsgenerator.a("players_seen", Integer.valueOf(this.v.getSeenPlayers().length));
        }

        mojangstatisticsgenerator.a("uses_auth", Boolean.valueOf(this.onlineMode));
        mojangstatisticsgenerator.a("gui_state", this.aq() ? "enabled" : "disabled");
        mojangstatisticsgenerator.a("run_time", Long.valueOf((ax() - mojangstatisticsgenerator.g()) / 60L * 1000L));
        mojangstatisticsgenerator.a("avg_tick_ms", Integer.valueOf((int) (MathHelper.a(this.g) * 1.0E-6D)));
        int i = 0;

        if (this.worldServer != null) {
            for (int j = 0; j < this.worldServer.length; ++j) {
                if (this.worldServer[j] != null) {
                    WorldServer worldserver = this.worldServer[j];
                    WorldData worlddata = worldserver.getWorldData();

                    mojangstatisticsgenerator.a("world[" + i + "][dimension]", Integer.valueOf(worldserver.worldProvider.getDimension()));
                    mojangstatisticsgenerator.a("world[" + i + "][mode]", worlddata.getGameType());
                    mojangstatisticsgenerator.a("world[" + i + "][difficulty]", worldserver.getDifficulty());
                    mojangstatisticsgenerator.a("world[" + i + "][hardcore]", Boolean.valueOf(worlddata.isHardcore()));
                    mojangstatisticsgenerator.a("world[" + i + "][generator_name]", worlddata.getType().name());
                    mojangstatisticsgenerator.a("world[" + i + "][generator_version]", Integer.valueOf(worlddata.getType().getVersion()));
                    mojangstatisticsgenerator.a("world[" + i + "][height]", Integer.valueOf(this.F));
                    mojangstatisticsgenerator.a("world[" + i + "][chunks_loaded]", Integer.valueOf(worldserver.N().getLoadedChunks()));
                    ++i;
                }
            }
        }

        mojangstatisticsgenerator.a("worlds", Integer.valueOf(i));
    }

    public void b(MojangStatisticsGenerator mojangstatisticsgenerator) {
        mojangstatisticsgenerator.b("singleplayer", Boolean.valueOf(this.S()));
        mojangstatisticsgenerator.b("server_brand", this.getServerModName());
        mojangstatisticsgenerator.b("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
        mojangstatisticsgenerator.b("dedicated", Boolean.valueOf(this.ad()));
    }

    public boolean getSnooperEnabled() {
        return true;
    }

    public abstract boolean ad();

    public boolean getOnlineMode() {
        return this.onlineMode;
    }

    public void setOnlineMode(boolean flag) {
        this.onlineMode = flag;
    }

    public boolean getSpawnAnimals() {
        return this.spawnAnimals;
    }

    public void setSpawnAnimals(boolean flag) {
        this.spawnAnimals = flag;
    }

    public boolean getSpawnNPCs() {
        return this.spawnNPCs;
    }

    public void setSpawnNPCs(boolean flag) {
        this.spawnNPCs = flag;
    }

    public boolean getPVP() {
        return this.pvpMode;
    }

    public void setPVP(boolean flag) {
        this.pvpMode = flag;
    }

    public boolean getAllowFlight() {
        return this.allowFlight;
    }

    public void setAllowFlight(boolean flag) {
        this.allowFlight = flag;
    }

    public abstract boolean getEnableCommandBlock();

    public String getMotd() {
        return this.motd;
    }

    public void setMotd(String s) {
        this.motd = s;
    }

    public int getMaxBuildHeight() {
        return this.F;
    }

    public void c(int i) {
        this.F = i;
    }

    public boolean isStopped() {
        return this.isStopped;
    }

    public PlayerList getPlayerList() {
        return this.v;
    }

    public void a(PlayerList playerlist) {
        this.v = playerlist;
    }

    public void setGamemode(EnumGamemode enumgamemode) {
        for (int i = 0; i < this.worldServer.length; ++i) {
            getServer().worldServer[i].getWorldData().setGameType(enumgamemode);
        }

    }

    public ServerConnection ao() {
        return this.q;
    }

    public boolean aq() {
        return false;
    }

    public abstract String a(EnumGamemode enumgamemode, boolean flag);

    public int ar() {
        return this.ticks;
    }

    public void as() {
        this.T = true;
    }

    public BlockPosition getChunkCoordinates() {
        return BlockPosition.ZERO;
    }

    public Vec3D d() {
        return new Vec3D(0.0D, 0.0D, 0.0D);
    }

    public World getWorld() {
        return this.worldServer[0];
    }

    public Entity f() {
        return null;
    }

    public int getSpawnProtection() {
        return 16;
    }

    public boolean a(World world, BlockPosition blockposition, EntityHuman entityhuman) {
        return false;
    }

    public void setForceGamemode(boolean flag) {
        this.U = flag;
    }

    public boolean getForceGamemode() {
        return this.U;
    }

    public Proxy aw() {
        return this.d;
    }

    public static long ax() {
        return System.currentTimeMillis();
    }

    public int getIdleTimeout() {
        return this.G;
    }

    public void setIdleTimeout(int i) {
        this.G = i;
    }

    public IChatBaseComponent getScoreboardDisplayName() {
        return new ChatComponentText(this.getName());
    }

    public boolean az() {
        return true;
    }

    public MinecraftSessionService aB() {
        return this.W;
    }

    public GameProfileRepository getGameProfileRepository() {
        return this.Y;
    }

    public UserCache getUserCache() {
        return this.Z;
    }

    public ServerPing aE() {
        return this.r;
    }

    public void aF() {
        this.X = 0L;
    }

    public Entity a(UUID uuid) {
        WorldServer[] aworldserver = this.worldServer;
        int i = aworldserver.length;

        for (int j = 0; j < i; ++j) {
            WorldServer worldserver = aworldserver[j];

            if (worldserver != null) {
                Entity entity = worldserver.getEntity(uuid);

                if (entity != null) {
                    return entity;
                }
            }
        }

        return null;
    }

    public boolean getSendCommandFeedback() {
        return getServer().worldServer[0].getGameRules().getBoolean("sendCommandFeedback");
    }

    public void a(EnumCommandResult enumcommandresult, int i) {}

    public int aG() {
        return 29999984;
    }

    public ListenableFuture a(Callable callable) {
        Validate.notNull(callable);
        if (!this.isMainThread()) {
            ListenableFutureTask listenablefuturetask = ListenableFutureTask.create(callable);
            Queue queue = this.i;

            synchronized (this.i) {
                this.i.add(listenablefuturetask);
                return listenablefuturetask;
            }
        } else {
            try {
                return Futures.immediateFuture(callable.call());
            } catch (Exception exception) {
                return Futures.immediateFailedCheckedFuture(exception);
            }
        }
    }

    public ListenableFuture postToMainThread(Runnable runnable) {
        Validate.notNull(runnable);
        return this.a(Executors.callable(runnable));
    }

    public boolean isMainThread() {
        return Thread.currentThread() == this.serverThread;
    }

    public int aI() {
        return 256;
    }

    public long aJ() {
        return this.ab;
    }

    public Thread aK() {
        return this.serverThread;
    }

    public static Logger getLogger() {
        return MinecraftServer.LOGGER;
    }

    public static PlayerList a(MinecraftServer minecraftserver) {
        return minecraftserver.v;
    }
}
