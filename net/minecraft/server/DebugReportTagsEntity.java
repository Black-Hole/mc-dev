package net.minecraft.server;

public class DebugReportTagsEntity extends DebugReportTags<EntityTypes<?>> {

    public DebugReportTagsEntity(DebugReportGenerator debugreportgenerator) {
        super(debugreportgenerator, IRegistry.ENTITY_TYPE);
    }

    @Override
    protected void b() {
        this.a(TagsEntity.SKELETONS).a((Object[])(EntityTypes.SKELETON, EntityTypes.STRAY, EntityTypes.WITHER_SKELETON));
        this.a(TagsEntity.RADIERS).a((Object[])(EntityTypes.EVOKER, EntityTypes.PILLAGER, EntityTypes.RAVAGER, EntityTypes.VINDICATOR, EntityTypes.ILLUSIONER, EntityTypes.WITCH));
    }

    @Override
    protected java.nio.file.Path a(MinecraftKey minecraftkey) {
        return this.b.b().resolve("data/" + minecraftkey.b() + "/tags/entity_types/" + minecraftkey.getKey() + ".json");
    }

    @Override
    public String a() {
        return "Entity Type Tags";
    }

    @Override
    protected void a(Tags<EntityTypes<?>> tags) {
        TagsEntity.a(tags);
    }
}
