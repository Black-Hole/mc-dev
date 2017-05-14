package net.minecraft.server;

import javax.annotation.Nullable;

public class CommandListenerWrapper implements ICommandListener {

    private final ICommandListener a;

    public CommandListenerWrapper(ICommandListener icommandlistener) {
        this.a = icommandlistener;
    }

    public String getName() {
        return this.a.getName();
    }

    public IChatBaseComponent getScoreboardDisplayName() {
        return this.a.getScoreboardDisplayName();
    }

    public void sendMessage(IChatBaseComponent ichatbasecomponent) {
        this.a.sendMessage(ichatbasecomponent);
    }

    public boolean a(int i, String s) {
        return this.a.a(i, s);
    }

    public BlockPosition getChunkCoordinates() {
        return this.a.getChunkCoordinates();
    }

    public Vec3D d() {
        return this.a.d();
    }

    public World getWorld() {
        return this.a.getWorld();
    }

    @Nullable
    public Entity f() {
        return this.a.f();
    }

    public boolean getSendCommandFeedback() {
        return this.a.getSendCommandFeedback();
    }

    public void a(CommandObjectiveExecutor.EnumCommandResult commandobjectiveexecutor_enumcommandresult, int i) {
        this.a.a(commandobjectiveexecutor_enumcommandresult, i);
    }

    @Nullable
    public MinecraftServer C_() {
        return this.a.C_();
    }
}
