package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFixTypes;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.JsonOps;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class WorldData {

    private String b;
    private int c;
    private boolean d;
    public static final EnumDifficulty a = EnumDifficulty.NORMAL;
    private long e;
    private WorldType f;
    private NBTTagCompound g;
    private int h;
    private int i;
    private int j;
    private long k;
    private long l;
    private long m;
    private long n;
    @Nullable
    private final DataFixer o;
    private final int p;
    private boolean q;
    private NBTTagCompound r;
    private int s;
    private String levelName;
    private int u;
    private int v;
    private boolean w;
    private int x;
    private boolean y;
    private int z;
    private EnumGamemode A;
    private boolean B;
    private boolean C;
    private boolean D;
    private boolean E;
    private EnumDifficulty F;
    private boolean G;
    private double H;
    private double I;
    private double J;
    private long K;
    private double L;
    private double M;
    private double N;
    private int O;
    private int P;
    private final Set<String> Q;
    private final Set<String> R;
    private final Map<DimensionManager, NBTTagCompound> S;
    private NBTTagCompound T;
    private final GameRules U;

    protected WorldData() {
        this.f = WorldType.NORMAL;
        this.g = new NBTTagCompound();
        this.J = 6.0E7D;
        this.M = 5.0D;
        this.N = 0.2D;
        this.O = 5;
        this.P = 15;
        this.Q = Sets.newHashSet();
        this.R = Sets.newLinkedHashSet();
        this.S = Maps.newEnumMap(DimensionManager.class);
        this.U = new GameRules();
        this.o = null;
        this.p = 1513;
        this.b(new NBTTagCompound());
    }

    public WorldData(NBTTagCompound nbttagcompound, DataFixer datafixer, int i, @Nullable NBTTagCompound nbttagcompound1) {
        this.f = WorldType.NORMAL;
        this.g = new NBTTagCompound();
        this.J = 6.0E7D;
        this.M = 5.0D;
        this.N = 0.2D;
        this.O = 5;
        this.P = 15;
        this.Q = Sets.newHashSet();
        this.R = Sets.newLinkedHashSet();
        this.S = Maps.newEnumMap(DimensionManager.class);
        this.U = new GameRules();
        this.o = datafixer;
        NBTTagCompound nbttagcompound2;

        if (nbttagcompound.hasKeyOfType("Version", 10)) {
            nbttagcompound2 = nbttagcompound.getCompound("Version");
            this.b = nbttagcompound2.getString("Name");
            this.c = nbttagcompound2.getInt("Id");
            this.d = nbttagcompound2.getBoolean("Snapshot");
        }

        this.e = nbttagcompound.getLong("RandomSeed");
        if (nbttagcompound.hasKeyOfType("generatorName", 8)) {
            String s = nbttagcompound.getString("generatorName");

            this.f = WorldType.getType(s);
            if (this.f == null) {
                this.f = WorldType.NORMAL;
            } else if (this.f.g()) {
                int j = 0;

                if (nbttagcompound.hasKeyOfType("generatorVersion", 99)) {
                    j = nbttagcompound.getInt("generatorVersion");
                }

                this.f = this.f.a(j);
            }

            this.b(nbttagcompound.getCompound("generatorOptions"));
        }

        this.A = EnumGamemode.getById(nbttagcompound.getInt("GameType"));
        if (nbttagcompound.hasKeyOfType("MapFeatures", 99)) {
            this.B = nbttagcompound.getBoolean("MapFeatures");
        } else {
            this.B = true;
        }

        this.h = nbttagcompound.getInt("SpawnX");
        this.i = nbttagcompound.getInt("SpawnY");
        this.j = nbttagcompound.getInt("SpawnZ");
        this.k = nbttagcompound.getLong("Time");
        if (nbttagcompound.hasKeyOfType("DayTime", 99)) {
            this.l = nbttagcompound.getLong("DayTime");
        } else {
            this.l = this.k;
        }

        this.m = nbttagcompound.getLong("LastPlayed");
        this.n = nbttagcompound.getLong("SizeOnDisk");
        this.levelName = nbttagcompound.getString("LevelName");
        this.u = nbttagcompound.getInt("version");
        this.v = nbttagcompound.getInt("clearWeatherTime");
        this.x = nbttagcompound.getInt("rainTime");
        this.w = nbttagcompound.getBoolean("raining");
        this.z = nbttagcompound.getInt("thunderTime");
        this.y = nbttagcompound.getBoolean("thundering");
        this.C = nbttagcompound.getBoolean("hardcore");
        if (nbttagcompound.hasKeyOfType("initialized", 99)) {
            this.E = nbttagcompound.getBoolean("initialized");
        } else {
            this.E = true;
        }

        if (nbttagcompound.hasKeyOfType("allowCommands", 99)) {
            this.D = nbttagcompound.getBoolean("allowCommands");
        } else {
            this.D = this.A == EnumGamemode.CREATIVE;
        }

        this.p = i;
        if (nbttagcompound1 != null) {
            this.r = nbttagcompound1;
        }

        if (nbttagcompound.hasKeyOfType("GameRules", 10)) {
            this.U.a(nbttagcompound.getCompound("GameRules"));
        }

        if (nbttagcompound.hasKeyOfType("Difficulty", 99)) {
            this.F = EnumDifficulty.getById(nbttagcompound.getByte("Difficulty"));
        }

        if (nbttagcompound.hasKeyOfType("DifficultyLocked", 1)) {
            this.G = nbttagcompound.getBoolean("DifficultyLocked");
        }

        if (nbttagcompound.hasKeyOfType("BorderCenterX", 99)) {
            this.H = nbttagcompound.getDouble("BorderCenterX");
        }

        if (nbttagcompound.hasKeyOfType("BorderCenterZ", 99)) {
            this.I = nbttagcompound.getDouble("BorderCenterZ");
        }

        if (nbttagcompound.hasKeyOfType("BorderSize", 99)) {
            this.J = nbttagcompound.getDouble("BorderSize");
        }

        if (nbttagcompound.hasKeyOfType("BorderSizeLerpTime", 99)) {
            this.K = nbttagcompound.getLong("BorderSizeLerpTime");
        }

        if (nbttagcompound.hasKeyOfType("BorderSizeLerpTarget", 99)) {
            this.L = nbttagcompound.getDouble("BorderSizeLerpTarget");
        }

        if (nbttagcompound.hasKeyOfType("BorderSafeZone", 99)) {
            this.M = nbttagcompound.getDouble("BorderSafeZone");
        }

        if (nbttagcompound.hasKeyOfType("BorderDamagePerBlock", 99)) {
            this.N = nbttagcompound.getDouble("BorderDamagePerBlock");
        }

        if (nbttagcompound.hasKeyOfType("BorderWarningBlocks", 99)) {
            this.O = nbttagcompound.getInt("BorderWarningBlocks");
        }

        if (nbttagcompound.hasKeyOfType("BorderWarningTime", 99)) {
            this.P = nbttagcompound.getInt("BorderWarningTime");
        }

        if (nbttagcompound.hasKeyOfType("DimensionData", 10)) {
            nbttagcompound2 = nbttagcompound.getCompound("DimensionData");
            Iterator iterator = nbttagcompound2.getKeys().iterator();

            while (iterator.hasNext()) {
                String s1 = (String) iterator.next();

                this.S.put(DimensionManager.a(Integer.parseInt(s1)), nbttagcompound2.getCompound(s1));
            }
        }

        if (nbttagcompound.hasKeyOfType("DataPacks", 10)) {
            nbttagcompound2 = nbttagcompound.getCompound("DataPacks");
            NBTTagList nbttaglist = nbttagcompound2.getList("Disabled", 8);

            for (int k = 0; k < nbttaglist.size(); ++k) {
                this.Q.add(nbttaglist.getString(k));
            }

            NBTTagList nbttaglist1 = nbttagcompound2.getList("Enabled", 8);

            for (int l = 0; l < nbttaglist1.size(); ++l) {
                this.R.add(nbttaglist1.getString(l));
            }
        }

        if (nbttagcompound.hasKeyOfType("CustomBossEvents", 10)) {
            this.T = nbttagcompound.getCompound("CustomBossEvents");
        }

    }

    public WorldData(WorldSettings worldsettings, String s) {
        this.f = WorldType.NORMAL;
        this.g = new NBTTagCompound();
        this.J = 6.0E7D;
        this.M = 5.0D;
        this.N = 0.2D;
        this.O = 5;
        this.P = 15;
        this.Q = Sets.newHashSet();
        this.R = Sets.newLinkedHashSet();
        this.S = Maps.newEnumMap(DimensionManager.class);
        this.U = new GameRules();
        this.o = null;
        this.p = 1513;
        this.a(worldsettings);
        this.levelName = s;
        this.F = WorldData.a;
        this.E = false;
    }

    public void a(WorldSettings worldsettings) {
        this.e = worldsettings.d();
        this.A = worldsettings.e();
        this.B = worldsettings.g();
        this.C = worldsettings.f();
        this.f = worldsettings.h();
        this.b((NBTTagCompound) Dynamic.convert(JsonOps.INSTANCE, DynamicOpsNBT.a, worldsettings.j()));
        this.D = worldsettings.i();
    }

    public NBTTagCompound a(@Nullable NBTTagCompound nbttagcompound) {
        this.Q();
        if (nbttagcompound == null) {
            nbttagcompound = this.r;
        }

        NBTTagCompound nbttagcompound1 = new NBTTagCompound();

        this.a(nbttagcompound1, nbttagcompound);
        return nbttagcompound1;
    }

    private void a(NBTTagCompound nbttagcompound, NBTTagCompound nbttagcompound1) {
        NBTTagCompound nbttagcompound2 = new NBTTagCompound();

        nbttagcompound2.setString("Name", "1.13-pre7");
        nbttagcompound2.setInt("Id", 1513);
        nbttagcompound2.setBoolean("Snapshot", true);
        nbttagcompound.set("Version", nbttagcompound2);
        nbttagcompound.setInt("DataVersion", 1513);
        nbttagcompound.setLong("RandomSeed", this.e);
        nbttagcompound.setString("generatorName", this.f.name());
        nbttagcompound.setInt("generatorVersion", this.f.getVersion());
        if (!this.g.isEmpty()) {
            nbttagcompound.set("generatorOptions", this.g);
        }

        nbttagcompound.setInt("GameType", this.A.getId());
        nbttagcompound.setBoolean("MapFeatures", this.B);
        nbttagcompound.setInt("SpawnX", this.h);
        nbttagcompound.setInt("SpawnY", this.i);
        nbttagcompound.setInt("SpawnZ", this.j);
        nbttagcompound.setLong("Time", this.k);
        nbttagcompound.setLong("DayTime", this.l);
        nbttagcompound.setLong("SizeOnDisk", this.n);
        nbttagcompound.setLong("LastPlayed", SystemUtils.d());
        nbttagcompound.setString("LevelName", this.levelName);
        nbttagcompound.setInt("version", this.u);
        nbttagcompound.setInt("clearWeatherTime", this.v);
        nbttagcompound.setInt("rainTime", this.x);
        nbttagcompound.setBoolean("raining", this.w);
        nbttagcompound.setInt("thunderTime", this.z);
        nbttagcompound.setBoolean("thundering", this.y);
        nbttagcompound.setBoolean("hardcore", this.C);
        nbttagcompound.setBoolean("allowCommands", this.D);
        nbttagcompound.setBoolean("initialized", this.E);
        nbttagcompound.setDouble("BorderCenterX", this.H);
        nbttagcompound.setDouble("BorderCenterZ", this.I);
        nbttagcompound.setDouble("BorderSize", this.J);
        nbttagcompound.setLong("BorderSizeLerpTime", this.K);
        nbttagcompound.setDouble("BorderSafeZone", this.M);
        nbttagcompound.setDouble("BorderDamagePerBlock", this.N);
        nbttagcompound.setDouble("BorderSizeLerpTarget", this.L);
        nbttagcompound.setDouble("BorderWarningBlocks", (double) this.O);
        nbttagcompound.setDouble("BorderWarningTime", (double) this.P);
        if (this.F != null) {
            nbttagcompound.setByte("Difficulty", (byte) this.F.a());
        }

        nbttagcompound.setBoolean("DifficultyLocked", this.G);
        nbttagcompound.set("GameRules", this.U.a());
        NBTTagCompound nbttagcompound3 = new NBTTagCompound();
        Iterator iterator = this.S.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();

            nbttagcompound3.set(String.valueOf(((DimensionManager) entry.getKey()).getDimensionID()), (NBTBase) entry.getValue());
        }

        nbttagcompound.set("DimensionData", nbttagcompound3);
        if (nbttagcompound1 != null) {
            nbttagcompound.set("Player", nbttagcompound1);
        }

        NBTTagCompound nbttagcompound4 = new NBTTagCompound();
        NBTTagList nbttaglist = new NBTTagList();
        Iterator iterator1 = this.R.iterator();

        while (iterator1.hasNext()) {
            String s = (String) iterator1.next();

            nbttaglist.add((NBTBase) (new NBTTagString(s)));
        }

        nbttagcompound4.set("Enabled", nbttaglist);
        NBTTagList nbttaglist1 = new NBTTagList();
        Iterator iterator2 = this.Q.iterator();

        while (iterator2.hasNext()) {
            String s1 = (String) iterator2.next();

            nbttaglist1.add((NBTBase) (new NBTTagString(s1)));
        }

        nbttagcompound4.set("Disabled", nbttaglist1);
        nbttagcompound.set("DataPacks", nbttagcompound4);
        if (this.T != null) {
            nbttagcompound.set("CustomBossEvents", this.T);
        }

    }

    public long getSeed() {
        return this.e;
    }

    public int b() {
        return this.h;
    }

    public int c() {
        return this.i;
    }

    public int d() {
        return this.j;
    }

    public long getTime() {
        return this.k;
    }

    public long getDayTime() {
        return this.l;
    }

    private void Q() {
        if (!this.q && this.r != null) {
            if (this.p < 1513) {
                if (this.o == null) {
                    throw new NullPointerException("Fixer Upper not set inside LevelData, and the player tag is not upgraded.");
                }

                this.r = GameProfileSerializer.a(this.o, DataFixTypes.PLAYER, this.r, this.p);
            }

            this.s = this.r.getInt("Dimension");
            this.q = true;
        }
    }

    public NBTTagCompound h() {
        this.Q();
        return this.r;
    }

    public void setTime(long i) {
        this.k = i;
    }

    public void setDayTime(long i) {
        this.l = i;
    }

    public void setSpawn(BlockPosition blockposition) {
        this.h = blockposition.getX();
        this.i = blockposition.getY();
        this.j = blockposition.getZ();
    }

    public String getName() {
        return this.levelName;
    }

    public void a(String s) {
        this.levelName = s;
    }

    public int k() {
        return this.u;
    }

    public void d(int i) {
        this.u = i;
    }

    public int z() {
        return this.v;
    }

    public void g(int i) {
        this.v = i;
    }

    public boolean isThundering() {
        return this.y;
    }

    public void setThundering(boolean flag) {
        this.y = flag;
    }

    public int getThunderDuration() {
        return this.z;
    }

    public void setThunderDuration(int i) {
        this.z = i;
    }

    public boolean hasStorm() {
        return this.w;
    }

    public void setStorm(boolean flag) {
        this.w = flag;
    }

    public int getWeatherDuration() {
        return this.x;
    }

    public void setWeatherDuration(int i) {
        this.x = i;
    }

    public EnumGamemode getGameType() {
        return this.A;
    }

    public boolean shouldGenerateMapFeatures() {
        return this.B;
    }

    public void f(boolean flag) {
        this.B = flag;
    }

    public void setGameType(EnumGamemode enumgamemode) {
        this.A = enumgamemode;
    }

    public boolean isHardcore() {
        return this.C;
    }

    public void g(boolean flag) {
        this.C = flag;
    }

    public WorldType getType() {
        return this.f;
    }

    public void a(WorldType worldtype) {
        this.f = worldtype;
    }

    public NBTTagCompound getGeneratorOptions() {
        return this.g;
    }

    public void b(NBTTagCompound nbttagcompound) {
        this.g = nbttagcompound;
    }

    public boolean u() {
        return this.D;
    }

    public void c(boolean flag) {
        this.D = flag;
    }

    public boolean v() {
        return this.E;
    }

    public void d(boolean flag) {
        this.E = flag;
    }

    public GameRules w() {
        return this.U;
    }

    public double B() {
        return this.H;
    }

    public double C() {
        return this.I;
    }

    public double D() {
        return this.J;
    }

    public void a(double d0) {
        this.J = d0;
    }

    public long E() {
        return this.K;
    }

    public void c(long i) {
        this.K = i;
    }

    public double F() {
        return this.L;
    }

    public void b(double d0) {
        this.L = d0;
    }

    public void c(double d0) {
        this.I = d0;
    }

    public void d(double d0) {
        this.H = d0;
    }

    public double G() {
        return this.M;
    }

    public void e(double d0) {
        this.M = d0;
    }

    public double H() {
        return this.N;
    }

    public void f(double d0) {
        this.N = d0;
    }

    public int I() {
        return this.O;
    }

    public int J() {
        return this.P;
    }

    public void h(int i) {
        this.O = i;
    }

    public void i(int i) {
        this.P = i;
    }

    public EnumDifficulty getDifficulty() {
        return this.F;
    }

    public void setDifficulty(EnumDifficulty enumdifficulty) {
        this.F = enumdifficulty;
    }

    public boolean isDifficultyLocked() {
        return this.G;
    }

    public void e(boolean flag) {
        this.G = flag;
    }

    public void a(CrashReportSystemDetails crashreportsystemdetails) {
        crashreportsystemdetails.a("Level seed", () -> {
            return String.valueOf(this.getSeed());
        });
        crashreportsystemdetails.a("Level generator", () -> {
            return String.format("ID %02d - %s, ver %d. Features enabled: %b", new Object[] { Integer.valueOf(this.f.h()), this.f.name(), Integer.valueOf(this.f.getVersion()), Boolean.valueOf(this.B)});
        });
        crashreportsystemdetails.a("Level generator options", () -> {
            return this.g.toString();
        });
        crashreportsystemdetails.a("Level spawn location", () -> {
            return CrashReportSystemDetails.a(this.h, this.i, this.j);
        });
        crashreportsystemdetails.a("Level time", () -> {
            return String.format("%d game time, %d day time", new Object[] { Long.valueOf(this.k), Long.valueOf(this.l)});
        });
        crashreportsystemdetails.a("Level dimension", () -> {
            return String.valueOf(this.s);
        });
        crashreportsystemdetails.a("Level storage version", () -> {
            String s = "Unknown?";

            try {
                switch (this.u) {
                case 19132:
                    s = "McRegion";
                    break;

                case 19133:
                    s = "Anvil";
                }
            } catch (Throwable throwable) {
                ;
            }

            return String.format("0x%05X - %s", new Object[] { Integer.valueOf(this.u), s});
        });
        crashreportsystemdetails.a("Level weather", () -> {
            return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", new Object[] { Integer.valueOf(this.x), Boolean.valueOf(this.w), Integer.valueOf(this.z), Boolean.valueOf(this.y)});
        });
        crashreportsystemdetails.a("Level game mode", () -> {
            return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", new Object[] { this.A.b(), Integer.valueOf(this.A.getId()), Boolean.valueOf(this.C), Boolean.valueOf(this.D)});
        });
    }

    public NBTTagCompound a(DimensionManager dimensionmanager) {
        NBTTagCompound nbttagcompound = (NBTTagCompound) this.S.get(dimensionmanager);

        return nbttagcompound == null ? new NBTTagCompound() : nbttagcompound;
    }

    public void a(DimensionManager dimensionmanager, NBTTagCompound nbttagcompound) {
        this.S.put(dimensionmanager, nbttagcompound);
    }

    public Set<String> N() {
        return this.Q;
    }

    public Set<String> O() {
        return this.R;
    }

    @Nullable
    public NBTTagCompound P() {
        return this.T;
    }

    public void c(@Nullable NBTTagCompound nbttagcompound) {
        this.T = nbttagcompound;
    }
}
