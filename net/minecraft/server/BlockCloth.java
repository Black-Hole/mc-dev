package net.minecraft.server;

public class BlockCloth extends Block {

    public static final BlockStateEnum<EnumColor> COLOR = BlockStateEnum.of("color", EnumColor.class);

    public BlockCloth(Material material) {
        super(material);
        this.x(this.blockStateList.getBlockData().set(BlockCloth.COLOR, EnumColor.WHITE));
        this.a(CreativeModeTab.b);
    }

    public int getDropData(IBlockData iblockdata) {
        return ((EnumColor) iblockdata.get(BlockCloth.COLOR)).getColorIndex();
    }

    public MaterialMapColor c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return MaterialMapColor.a((EnumColor) iblockdata.get(BlockCloth.COLOR));
    }

    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockCloth.COLOR, EnumColor.fromColorIndex(i));
    }

    public int toLegacyData(IBlockData iblockdata) {
        return ((EnumColor) iblockdata.get(BlockCloth.COLOR)).getColorIndex();
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockCloth.COLOR});
    }
}
