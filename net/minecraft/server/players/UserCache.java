package net.minecraft.server.players;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.ProfileLookupCallback;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.world.entity.player.EntityHuman;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserCache {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int GAMEPROFILES_MRU_LIMIT = 1000;
    private static final int GAMEPROFILES_EXPIRATION_MONTHS = 1;
    private static boolean usesAuthentication;
    private final Map<String, UserCache.UserCacheEntry> profilesByName = Maps.newConcurrentMap();
    private final Map<UUID, UserCache.UserCacheEntry> profilesByUUID = Maps.newConcurrentMap();
    private final Map<String, CompletableFuture<GameProfile>> requests = Maps.newConcurrentMap();
    private final GameProfileRepository profileRepository;
    private final Gson gson = (new GsonBuilder()).create();
    private final File file;
    private final AtomicLong operationCount = new AtomicLong();
    @Nullable
    private Executor executor;

    public UserCache(GameProfileRepository gameprofilerepository, File file) {
        this.profileRepository = gameprofilerepository;
        this.file = file;
        Lists.reverse(this.a()).forEach(this::a);
    }

    private void a(UserCache.UserCacheEntry usercache_usercacheentry) {
        GameProfile gameprofile = usercache_usercacheentry.a();

        usercache_usercacheentry.a(this.d());
        String s = gameprofile.getName();

        if (s != null) {
            this.profilesByName.put(s.toLowerCase(Locale.ROOT), usercache_usercacheentry);
        }

        UUID uuid = gameprofile.getId();

        if (uuid != null) {
            this.profilesByUUID.put(uuid, usercache_usercacheentry);
        }

    }

    @Nullable
    private static GameProfile a(GameProfileRepository gameprofilerepository, String s) {
        final AtomicReference<GameProfile> atomicreference = new AtomicReference();
        ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
            public void onProfileLookupSucceeded(GameProfile gameprofile) {
                atomicreference.set(gameprofile);
            }

            public void onProfileLookupFailed(GameProfile gameprofile, Exception exception) {
                atomicreference.set((Object) null);
            }
        };

        gameprofilerepository.findProfilesByNames(new String[]{s}, Agent.MINECRAFT, profilelookupcallback);
        GameProfile gameprofile = (GameProfile) atomicreference.get();

        if (!c() && gameprofile == null) {
            UUID uuid = EntityHuman.a(new GameProfile((UUID) null, s));

            gameprofile = new GameProfile(uuid, s);
        }

        return gameprofile;
    }

    public static void a(boolean flag) {
        UserCache.usesAuthentication = flag;
    }

    private static boolean c() {
        return UserCache.usesAuthentication;
    }

    public void a(GameProfile gameprofile) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.add(2, 1);
        Date date = calendar.getTime();
        UserCache.UserCacheEntry usercache_usercacheentry = new UserCache.UserCacheEntry(gameprofile, date);

        this.a(usercache_usercacheentry);
        this.b();
    }

    private long d() {
        return this.operationCount.incrementAndGet();
    }

    @Nullable
    public GameProfile getProfile(String s) {
        String s1 = s.toLowerCase(Locale.ROOT);
        UserCache.UserCacheEntry usercache_usercacheentry = (UserCache.UserCacheEntry) this.profilesByName.get(s1);
        boolean flag = false;

        if (usercache_usercacheentry != null && (new Date()).getTime() >= usercache_usercacheentry.expirationDate.getTime()) {
            this.profilesByUUID.remove(usercache_usercacheentry.a().getId());
            this.profilesByName.remove(usercache_usercacheentry.a().getName().toLowerCase(Locale.ROOT));
            flag = true;
            usercache_usercacheentry = null;
        }

        GameProfile gameprofile;

        if (usercache_usercacheentry != null) {
            usercache_usercacheentry.a(this.d());
            gameprofile = usercache_usercacheentry.a();
        } else {
            gameprofile = a(this.profileRepository, s1);
            if (gameprofile != null) {
                this.a(gameprofile);
                flag = false;
            }
        }

        if (flag) {
            this.b();
        }

        return gameprofile;
    }

    public void a(String s, Consumer<GameProfile> consumer) {
        if (this.executor == null) {
            throw new IllegalStateException("No executor");
        } else {
            CompletableFuture<GameProfile> completablefuture = (CompletableFuture) this.requests.get(s);

            if (completablefuture != null) {
                this.requests.put(s, completablefuture.whenCompleteAsync((gameprofile, throwable) -> {
                    consumer.accept(gameprofile);
                }, this.executor));
            } else {
                this.requests.put(s, CompletableFuture.supplyAsync(() -> {
                    return this.getProfile(s);
                }, SystemUtils.f()).whenCompleteAsync((gameprofile, throwable) -> {
                    this.requests.remove(s);
                }, this.executor).whenCompleteAsync((gameprofile, throwable) -> {
                    consumer.accept(gameprofile);
                }, this.executor));
            }

        }
    }

    @Nullable
    public GameProfile getProfile(UUID uuid) {
        UserCache.UserCacheEntry usercache_usercacheentry = (UserCache.UserCacheEntry) this.profilesByUUID.get(uuid);

        if (usercache_usercacheentry == null) {
            return null;
        } else {
            usercache_usercacheentry.a(this.d());
            return usercache_usercacheentry.a();
        }
    }

    public void a(Executor executor) {
        this.executor = executor;
    }

    private static DateFormat e() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    }

    public List<UserCache.UserCacheEntry> a() {
        ArrayList arraylist = Lists.newArrayList();

        try {
            BufferedReader bufferedreader = Files.newReader(this.file, StandardCharsets.UTF_8);

            label54:
            {
                ArrayList arraylist1;

                try {
                    JsonArray jsonarray = (JsonArray) this.gson.fromJson(bufferedreader, JsonArray.class);

                    if (jsonarray != null) {
                        DateFormat dateformat = e();

                        jsonarray.forEach((jsonelement) -> {
                            UserCache.UserCacheEntry usercache_usercacheentry = a(jsonelement, dateformat);

                            if (usercache_usercacheentry != null) {
                                arraylist.add(usercache_usercacheentry);
                            }

                        });
                        break label54;
                    }

                    arraylist1 = arraylist;
                } catch (Throwable throwable) {
                    if (bufferedreader != null) {
                        try {
                            bufferedreader.close();
                        } catch (Throwable throwable1) {
                            throwable.addSuppressed(throwable1);
                        }
                    }

                    throw throwable;
                }

                if (bufferedreader != null) {
                    bufferedreader.close();
                }

                return arraylist1;
            }

            if (bufferedreader != null) {
                bufferedreader.close();
            }
        } catch (FileNotFoundException filenotfoundexception) {
            ;
        } catch (JsonParseException | IOException ioexception) {
            UserCache.LOGGER.warn("Failed to load profile cache {}", this.file, ioexception);
        }

        return arraylist;
    }

    public void b() {
        JsonArray jsonarray = new JsonArray();
        DateFormat dateformat = e();

        this.a(1000).forEach((usercache_usercacheentry) -> {
            jsonarray.add(a(usercache_usercacheentry, dateformat));
        });
        String s = this.gson.toJson(jsonarray);

        try {
            BufferedWriter bufferedwriter = Files.newWriter(this.file, StandardCharsets.UTF_8);

            try {
                bufferedwriter.write(s);
            } catch (Throwable throwable) {
                if (bufferedwriter != null) {
                    try {
                        bufferedwriter.close();
                    } catch (Throwable throwable1) {
                        throwable.addSuppressed(throwable1);
                    }
                }

                throw throwable;
            }

            if (bufferedwriter != null) {
                bufferedwriter.close();
            }
        } catch (IOException ioexception) {
            ;
        }

    }

    private Stream<UserCache.UserCacheEntry> a(int i) {
        return ImmutableList.copyOf(this.profilesByUUID.values()).stream().sorted(Comparator.comparing(UserCache.UserCacheEntry::c).reversed()).limit((long) i);
    }

    private static JsonElement a(UserCache.UserCacheEntry usercache_usercacheentry, DateFormat dateformat) {
        JsonObject jsonobject = new JsonObject();

        jsonobject.addProperty("name", usercache_usercacheentry.a().getName());
        UUID uuid = usercache_usercacheentry.a().getId();

        jsonobject.addProperty("uuid", uuid == null ? "" : uuid.toString());
        jsonobject.addProperty("expiresOn", dateformat.format(usercache_usercacheentry.b()));
        return jsonobject;
    }

    @Nullable
    private static UserCache.UserCacheEntry a(JsonElement jsonelement, DateFormat dateformat) {
        if (jsonelement.isJsonObject()) {
            JsonObject jsonobject = jsonelement.getAsJsonObject();
            JsonElement jsonelement1 = jsonobject.get("name");
            JsonElement jsonelement2 = jsonobject.get("uuid");
            JsonElement jsonelement3 = jsonobject.get("expiresOn");

            if (jsonelement1 != null && jsonelement2 != null) {
                String s = jsonelement2.getAsString();
                String s1 = jsonelement1.getAsString();
                Date date = null;

                if (jsonelement3 != null) {
                    try {
                        date = dateformat.parse(jsonelement3.getAsString());
                    } catch (ParseException parseexception) {
                        ;
                    }
                }

                if (s1 != null && s != null && date != null) {
                    UUID uuid;

                    try {
                        uuid = UUID.fromString(s);
                    } catch (Throwable throwable) {
                        return null;
                    }

                    return new UserCache.UserCacheEntry(new GameProfile(uuid, s1), date);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private static class UserCacheEntry {

        private final GameProfile profile;
        final Date expirationDate;
        private volatile long lastAccess;

        UserCacheEntry(GameProfile gameprofile, Date date) {
            this.profile = gameprofile;
            this.expirationDate = date;
        }

        public GameProfile a() {
            return this.profile;
        }

        public Date b() {
            return this.expirationDate;
        }

        public void a(long i) {
            this.lastAccess = i;
        }

        public long c() {
            return this.lastAccess;
        }
    }
}
