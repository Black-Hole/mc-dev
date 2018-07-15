package net.minecraft.server;

import java.util.function.Consumer;

public class ScoreboardObjective {

    private final Scoreboard a;
    private final String b;
    private final IScoreboardCriteria c;
    public String displayName;
    private IScoreboardCriteria.EnumScoreboardHealthDisplay e;

    public ScoreboardObjective(Scoreboard scoreboard, String s, IScoreboardCriteria iscoreboardcriteria, String s1) {
        this.a = scoreboard;
        this.b = s;
        this.c = iscoreboardcriteria;
        this.displayName = s1;
        this.e = iscoreboardcriteria.e();
    }

    public String getName() {
        return this.b;
    }

    public IScoreboardCriteria getCriteria() {
        return this.c;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public IChatBaseComponent e() {
        return ChatComponentUtils.a((IChatBaseComponent) (new ChatComponentText(this.displayName))).a((chatmodifier) -> {
            chatmodifier.setChatHoverable(new ChatHoverable(ChatHoverable.EnumHoverAction.SHOW_TEXT, new ChatComponentText(this.getName())));
        });
    }

    public void setDisplayName(String s) {
        this.displayName = s;
        this.a.handleObjectiveChanged(this);
    }

    public IScoreboardCriteria.EnumScoreboardHealthDisplay f() {
        return this.e;
    }
}
