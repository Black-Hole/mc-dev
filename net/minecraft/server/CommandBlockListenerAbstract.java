package net.minecraft.server;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

public abstract class CommandBlockListenerAbstract implements ICommandListener {

    private static final SimpleDateFormat a = new SimpleDateFormat("HH:mm:ss");
    private int b;
    private boolean c = true;
    private IChatBaseComponent d = null;
    public String e = "";
    private String f = "@";
    private final CommandObjectiveExecutor g = new CommandObjectiveExecutor();

    public CommandBlockListenerAbstract() {}

    public int j() {
        return this.b;
    }

    public IChatBaseComponent k() {
        return this.d;
    }

    public void a(NBTTagCompound nbttagcompound) {
        nbttagcompound.setString("Command", this.e);
        nbttagcompound.setInt("SuccessCount", this.b);
        nbttagcompound.setString("CustomName", this.f);
        nbttagcompound.setBoolean("TrackOutput", this.c);
        if (this.d != null && this.c) {
            nbttagcompound.setString("LastOutput", ChatSerializer.a(this.d));
        }

        this.g.b(nbttagcompound);
    }

    public void b(NBTTagCompound nbttagcompound) {
        this.e = nbttagcompound.getString("Command");
        this.b = nbttagcompound.getInt("SuccessCount");
        if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
            this.f = nbttagcompound.getString("CustomName");
        }

        if (nbttagcompound.hasKeyOfType("TrackOutput", 1)) {
            this.c = nbttagcompound.getBoolean("TrackOutput");
        }

        if (nbttagcompound.hasKeyOfType("LastOutput", 8) && this.c) {
            this.d = ChatSerializer.a(nbttagcompound.getString("LastOutput"));
        }

        this.g.a(nbttagcompound);
    }

    public boolean a(int i, String s) {
        return i <= 2;
    }

    public void setCommand(String s) {
        this.e = s;
        this.b = 0;
    }

    public String getCommand() {
        return this.e;
    }

    public void a(World world) {
        if (world.isStatic) {
            this.b = 0;
        }

        MinecraftServer minecraftserver = MinecraftServer.getServer();

        if (minecraftserver != null && minecraftserver.N() && minecraftserver.getEnableCommandBlock()) {
            ICommandHandler icommandhandler = minecraftserver.getCommandHandler();

            try {
                this.d = null;
                this.b = icommandhandler.a(this, this.e);
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.a(throwable, "Executing command block");
                CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Command to be executed");

                crashreportsystemdetails.a("Command", (Callable) (new CrashReportCommandBlockCommand(this)));
                crashreportsystemdetails.a("Name", (Callable) (new CrashReportCommandBlockName(this)));
                throw new ReportedException(crashreport);
            }
        } else {
            this.b = 0;
        }

    }

    public String getName() {
        return this.f;
    }

    public IChatBaseComponent getScoreboardDisplayName() {
        return new ChatComponentText(this.getName());
    }

    public void setName(String s) {
        this.f = s;
    }

    public void sendMessage(IChatBaseComponent ichatbasecomponent) {
        if (this.c && this.getWorld() != null && !this.getWorld().isStatic) {
            this.d = (new ChatComponentText("[" + CommandBlockListenerAbstract.a.format(new Date()) + "] ")).addSibling(ichatbasecomponent);
            this.h();
        }

    }

    public boolean getSendCommandFeedback() {
        MinecraftServer minecraftserver = MinecraftServer.getServer();

        return minecraftserver == null || !minecraftserver.N() || minecraftserver.worldServer[0].getGameRules().getBoolean("commandBlockOutput");
    }

    public void a(EnumCommandResult enumcommandresult, int i) {
        this.g.a(this, enumcommandresult, i);
    }

    public abstract void h();

    public void b(IChatBaseComponent ichatbasecomponent) {
        this.d = ichatbasecomponent;
    }

    public void a(boolean flag) {
        this.c = flag;
    }

    public boolean m() {
        return this.c;
    }

    public boolean a(EntityHuman entityhuman) {
        if (!entityhuman.abilities.canInstantlyBuild) {
            return false;
        } else {
            if (entityhuman.getWorld().isStatic) {
                entityhuman.a(this);
            }

            return true;
        }
    }

    public CommandObjectiveExecutor n() {
        return this.g;
    }
}
