package net.minecraft.server;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import java.util.function.Supplier;

public class DataConverterSaddle extends DataConverterNamedEntity {

    public DataConverterSaddle(Schema schema, boolean flag) {
        super(schema, flag, "EntityHorseSaddleFix", DataConverterTypes.ENTITY, "EntityHorse");
    }

    protected Typed<?> a(Typed<?> typed) {
        OpticFinder opticfinder = DSL.fieldFinder("id", DSL.named(DataConverterTypes.q.typeName(), DSL.namespacedString()));
        Type type = this.getInputSchema().getTypeRaw(DataConverterTypes.ITEM_STACK);
        OpticFinder opticfinder1 = DSL.fieldFinder("SaddleItem", type);
        Optional optional = typed.getOptionalTyped(opticfinder1);
        Dynamic dynamic = (Dynamic) typed.get(DSL.remainderFinder());

        if (!optional.isPresent() && dynamic.getBoolean("Saddle")) {
            Typed typed1 = (Typed) type.pointTyped(typed.getOps()).orElseThrow(IllegalStateException::new);

            typed1 = typed1.set(opticfinder, Pair.of(DataConverterTypes.q.typeName(), "minecraft:saddle"));
            Dynamic dynamic1 = dynamic.emptyMap();

            dynamic1 = dynamic1.set("Count", dynamic1.createByte((byte) 1));
            dynamic1 = dynamic1.set("Damage", dynamic1.createShort((short) 0));
            typed1 = typed1.set(DSL.remainderFinder(), dynamic1);
            dynamic.remove("Saddle");
            return typed.set(opticfinder1, typed1).set(DSL.remainderFinder(), dynamic);
        } else {
            return typed;
        }
    }
}
