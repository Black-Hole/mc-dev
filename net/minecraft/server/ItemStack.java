package net.minecraft.server;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.text.DecimalFormat;
import java.util.Random;

public final class ItemStack {

    public static final DecimalFormat a = new DecimalFormat("#.##");
    public int count;
    public int c;
    private Item item;
    private NBTTagCompound tag;
    private int damage;
    private EntityItemFrame g;
    private Block h;
    private boolean i;
    private Block j;
    private boolean k;

    public ItemStack(Block block) {
        this(block, 1);
    }

    public ItemStack(Block block, int i) {
        this(block, i, 0);
    }

    public ItemStack(Block block, int i, int j) {
        this(Item.getItemOf(block), i, j);
    }

    public ItemStack(Item item) {
        this(item, 1);
    }

    public ItemStack(Item item, int i) {
        this(item, i, 0);
    }

    public ItemStack(Item item, int i, int j) {
        this.h = null;
        this.i = false;
        this.j = null;
        this.k = false;
        this.item = item;
        this.count = i;
        this.damage = j;
        if (this.damage < 0) {
            this.damage = 0;
        }

    }

    public static ItemStack createStack(NBTTagCompound nbttagcompound) {
        ItemStack itemstack = new ItemStack();

        itemstack.c(nbttagcompound);
        return itemstack.getItem() != null ? itemstack : null;
    }

    private ItemStack() {
        this.h = null;
        this.i = false;
        this.j = null;
        this.k = false;
    }

    public ItemStack cloneAndSubtract(int i) {
        i = Math.min(i, this.count);
        ItemStack itemstack = new ItemStack(this.item, i, this.damage);

        if (this.tag != null) {
            itemstack.tag = (NBTTagCompound) this.tag.clone();
        }

        this.count -= i;
        return itemstack;
    }

    public Item getItem() {
        return this.item;
    }

    public EnumInteractionResult placeItem(EntityHuman entityhuman, World world, BlockPosition blockposition, EnumHand enumhand, EnumDirection enumdirection, float f, float f1, float f2) {
        EnumInteractionResult enuminteractionresult = this.getItem().a(this, entityhuman, world, blockposition, enumhand, enumdirection, f, f1, f2);

        if (enuminteractionresult == EnumInteractionResult.SUCCESS) {
            entityhuman.b(StatisticList.b(this.item));
        }

        return enuminteractionresult;
    }

    public float a(IBlockData iblockdata) {
        return this.getItem().getDestroySpeed(this, iblockdata);
    }

    public InteractionResultWrapper<ItemStack> a(World world, EntityHuman entityhuman, EnumHand enumhand) {
        return this.getItem().a(this, world, entityhuman, enumhand);
    }

    public ItemStack a(World world, EntityLiving entityliving) {
        return this.getItem().a(this, world, entityliving);
    }

    public NBTTagCompound save(NBTTagCompound nbttagcompound) {
        MinecraftKey minecraftkey = (MinecraftKey) Item.REGISTRY.b(this.item);

        nbttagcompound.setString("id", minecraftkey == null ? "minecraft:air" : minecraftkey.toString());
        nbttagcompound.setByte("Count", (byte) this.count);
        nbttagcompound.setShort("Damage", (short) this.damage);
        if (this.tag != null) {
            nbttagcompound.set("tag", this.tag);
        }

        return nbttagcompound;
    }

    public void c(NBTTagCompound nbttagcompound) {
        this.item = Item.d(nbttagcompound.getString("id"));
        this.count = nbttagcompound.getByte("Count");
        this.damage = nbttagcompound.getShort("Damage");
        if (this.damage < 0) {
            this.damage = 0;
        }

        if (nbttagcompound.hasKeyOfType("tag", 10)) {
            this.tag = nbttagcompound.getCompound("tag");
            if (this.item != null) {
                this.item.a(this.tag);
            }
        }

    }

    public int getMaxStackSize() {
        return this.getItem().getMaxStackSize();
    }

    public boolean isStackable() {
        return this.getMaxStackSize() > 1 && (!this.e() || !this.g());
    }

    public boolean e() {
        return this.item == null ? false : (this.item.getMaxDurability() <= 0 ? false : !this.hasTag() || !this.getTag().getBoolean("Unbreakable"));
    }

    public boolean usesData() {
        return this.item.k();
    }

    public boolean g() {
        return this.e() && this.damage > 0;
    }

    public int h() {
        return this.damage;
    }

    public int getData() {
        return this.damage;
    }

    public void setData(int i) {
        this.damage = i;
        if (this.damage < 0) {
            this.damage = 0;
        }

    }

    public int j() {
        return this.item == null ? 0 : this.item.getMaxDurability();
    }

    public boolean isDamaged(int i, Random random) {
        if (!this.e()) {
            return false;
        } else {
            if (i > 0) {
                int j = EnchantmentManager.getEnchantmentLevel(Enchantments.DURABILITY, this);
                int k = 0;

                for (int l = 0; j > 0 && l < i; ++l) {
                    if (EnchantmentDurability.a(this, j, random)) {
                        ++k;
                    }
                }

                i -= k;
                if (i <= 0) {
                    return false;
                }
            }

            this.damage += i;
            return this.damage > this.j();
        }
    }

    public void damage(int i, EntityLiving entityliving) {
        if (!(entityliving instanceof EntityHuman) || !((EntityHuman) entityliving).abilities.canInstantlyBuild) {
            if (this.e()) {
                if (this.isDamaged(i, entityliving.getRandom())) {
                    entityliving.b(this);
                    --this.count;
                    if (entityliving instanceof EntityHuman) {
                        EntityHuman entityhuman = (EntityHuman) entityliving;

                        entityhuman.b(StatisticList.c(this.item));
                    }

                    if (this.count < 0) {
                        this.count = 0;
                    }

                    this.damage = 0;
                }

            }
        }
    }

    public void a(EntityLiving entityliving, EntityHuman entityhuman) {
        boolean flag = this.item.a(this, entityliving, entityhuman);

        if (flag) {
            entityhuman.b(StatisticList.b(this.item));
        }

    }

    public void a(World world, IBlockData iblockdata, BlockPosition blockposition, EntityHuman entityhuman) {
        boolean flag = this.item.a(this, world, iblockdata, blockposition, entityhuman);

        if (flag) {
            entityhuman.b(StatisticList.b(this.item));
        }

    }

    public boolean b(IBlockData iblockdata) {
        return this.item.canDestroySpecialBlock(iblockdata);
    }

    public boolean a(EntityHuman entityhuman, EntityLiving entityliving, EnumHand enumhand) {
        return this.item.a(this, entityhuman, entityliving, enumhand);
    }

    public ItemStack cloneItemStack() {
        ItemStack itemstack = new ItemStack(this.item, this.count, this.damage);

        if (this.tag != null) {
            itemstack.tag = (NBTTagCompound) this.tag.clone();
        }

        return itemstack;
    }

    public static boolean equals(ItemStack itemstack, ItemStack itemstack1) {
        return itemstack == null && itemstack1 == null ? true : (itemstack != null && itemstack1 != null ? (itemstack.tag == null && itemstack1.tag != null ? false : itemstack.tag == null || itemstack.tag.equals(itemstack1.tag)) : false);
    }

    public static boolean matches(ItemStack itemstack, ItemStack itemstack1) {
        return itemstack == null && itemstack1 == null ? true : (itemstack != null && itemstack1 != null ? itemstack.e(itemstack1) : false);
    }

    private boolean e(ItemStack itemstack) {
        return this.count != itemstack.count ? false : (this.item != itemstack.item ? false : (this.damage != itemstack.damage ? false : (this.tag == null && itemstack.tag != null ? false : this.tag == null || this.tag.equals(itemstack.tag))));
    }

    public static boolean c(ItemStack itemstack, ItemStack itemstack1) {
        return itemstack == itemstack1 ? true : (itemstack != null && itemstack1 != null ? itemstack.doMaterialsMatch(itemstack1) : false);
    }

    public static boolean d(ItemStack itemstack, ItemStack itemstack1) {
        return itemstack == itemstack1 ? true : (itemstack != null && itemstack1 != null ? itemstack.b(itemstack1) : false);
    }

    public boolean doMaterialsMatch(ItemStack itemstack) {
        return itemstack != null && this.item == itemstack.item && this.damage == itemstack.damage;
    }

    public boolean b(ItemStack itemstack) {
        return !this.e() ? this.doMaterialsMatch(itemstack) : itemstack != null && this.item == itemstack.item;
    }

    public String a() {
        return this.item.f_(this);
    }

    public static ItemStack c(ItemStack itemstack) {
        return itemstack == null ? null : itemstack.cloneItemStack();
    }

    public String toString() {
        return this.count + "x" + this.item.getName() + "@" + this.damage;
    }

    public void a(World world, Entity entity, int i, boolean flag) {
        if (this.c > 0) {
            --this.c;
        }

        if (this.item != null) {
            this.item.a(this, world, entity, i, flag);
        }

    }

    public void a(World world, EntityHuman entityhuman, int i) {
        entityhuman.a(StatisticList.a(this.item), i);
        this.item.b(this, world, entityhuman);
    }

    public int l() {
        return this.getItem().e(this);
    }

    public EnumAnimation m() {
        return this.getItem().f(this);
    }

    public void a(World world, EntityLiving entityliving, int i) {
        this.getItem().a(this, world, entityliving, i);
    }

    public boolean hasTag() {
        return this.tag != null;
    }

    public NBTTagCompound getTag() {
        return this.tag;
    }

    public NBTTagCompound a(String s, boolean flag) {
        if (this.tag != null && this.tag.hasKeyOfType(s, 10)) {
            return this.tag.getCompound(s);
        } else if (flag) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();

            this.a(s, (NBTBase) nbttagcompound);
            return nbttagcompound;
        } else {
            return null;
        }
    }

    public NBTTagList getEnchantments() {
        return this.tag == null ? null : this.tag.getList("ench", 10);
    }

    public void setTag(NBTTagCompound nbttagcompound) {
        this.tag = nbttagcompound;
    }

    public String getName() {
        String s = this.getItem().a(this);

        if (this.tag != null && this.tag.hasKeyOfType("display", 10)) {
            NBTTagCompound nbttagcompound = this.tag.getCompound("display");

            if (nbttagcompound.hasKeyOfType("Name", 8)) {
                s = nbttagcompound.getString("Name");
            }
        }

        return s;
    }

    public ItemStack c(String s) {
        if (this.tag == null) {
            this.tag = new NBTTagCompound();
        }

        if (!this.tag.hasKeyOfType("display", 10)) {
            this.tag.set("display", new NBTTagCompound());
        }

        this.tag.getCompound("display").setString("Name", s);
        return this;
    }

    public void r() {
        if (this.tag != null) {
            if (this.tag.hasKeyOfType("display", 10)) {
                NBTTagCompound nbttagcompound = this.tag.getCompound("display");

                nbttagcompound.remove("Name");
                if (nbttagcompound.isEmpty()) {
                    this.tag.remove("display");
                    if (this.tag.isEmpty()) {
                        this.setTag((NBTTagCompound) null);
                    }
                }

            }
        }
    }

    public boolean hasName() {
        return this.tag == null ? false : (!this.tag.hasKeyOfType("display", 10) ? false : this.tag.getCompound("display").hasKeyOfType("Name", 8));
    }

    public EnumItemRarity u() {
        return this.getItem().g(this);
    }

    public boolean v() {
        return !this.getItem().g_(this) ? false : !this.hasEnchantments();
    }

    public void addEnchantment(Enchantment enchantment, int i) {
        if (this.tag == null) {
            this.setTag(new NBTTagCompound());
        }

        if (!this.tag.hasKeyOfType("ench", 9)) {
            this.tag.set("ench", new NBTTagList());
        }

        NBTTagList nbttaglist = this.tag.getList("ench", 10);
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        nbttagcompound.setShort("id", (short) Enchantment.getId(enchantment));
        nbttagcompound.setShort("lvl", (short) ((byte) i));
        nbttaglist.add(nbttagcompound);
    }

    public boolean hasEnchantments() {
        return this.tag != null && this.tag.hasKeyOfType("ench", 9);
    }

    public void a(String s, NBTBase nbtbase) {
        if (this.tag == null) {
            this.setTag(new NBTTagCompound());
        }

        this.tag.set(s, nbtbase);
    }

    public boolean x() {
        return this.getItem().s();
    }

    public boolean y() {
        return this.g != null;
    }

    public void a(EntityItemFrame entityitemframe) {
        this.g = entityitemframe;
    }

    public EntityItemFrame z() {
        return this.g;
    }

    public int getRepairCost() {
        return this.hasTag() && this.tag.hasKeyOfType("RepairCost", 3) ? this.tag.getInt("RepairCost") : 0;
    }

    public void setRepairCost(int i) {
        if (!this.hasTag()) {
            this.tag = new NBTTagCompound();
        }

        this.tag.setInt("RepairCost", i);
    }

    public Multimap<String, AttributeModifier> a(EnumItemSlot enumitemslot) {
        Object object;

        if (this.hasTag() && this.tag.hasKeyOfType("AttributeModifiers", 9)) {
            object = HashMultimap.create();
            NBTTagList nbttaglist = this.tag.getList("AttributeModifiers", 10);

            for (int i = 0; i < nbttaglist.size(); ++i) {
                NBTTagCompound nbttagcompound = nbttaglist.get(i);
                AttributeModifier attributemodifier = GenericAttributes.a(nbttagcompound);

                if (attributemodifier != null && (!nbttagcompound.hasKeyOfType("Slot", 8) || nbttagcompound.getString("Slot").equals(enumitemslot.d())) && attributemodifier.a().getLeastSignificantBits() != 0L && attributemodifier.a().getMostSignificantBits() != 0L) {
                    ((Multimap) object).put(nbttagcompound.getString("AttributeName"), attributemodifier);
                }
            }
        } else {
            object = this.getItem().a(enumitemslot);
        }

        return (Multimap) object;
    }

    public void a(String s, AttributeModifier attributemodifier, EnumItemSlot enumitemslot) {
        if (this.tag == null) {
            this.tag = new NBTTagCompound();
        }

        if (!this.tag.hasKeyOfType("AttributeModifiers", 9)) {
            this.tag.set("AttributeModifiers", new NBTTagList());
        }

        NBTTagList nbttaglist = this.tag.getList("AttributeModifiers", 10);
        NBTTagCompound nbttagcompound = GenericAttributes.a(attributemodifier);

        nbttagcompound.setString("AttributeName", s);
        if (enumitemslot != null) {
            nbttagcompound.setString("Slot", enumitemslot.d());
        }

        nbttaglist.add(nbttagcompound);
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public IChatBaseComponent B() {
        ChatComponentText chatcomponenttext = new ChatComponentText(this.getName());

        if (this.hasName()) {
            chatcomponenttext.getChatModifier().setItalic(Boolean.valueOf(true));
        }

        IChatBaseComponent ichatbasecomponent = (new ChatComponentText("[")).addSibling(chatcomponenttext).a("]");

        if (this.item != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();

            this.save(nbttagcompound);
            ichatbasecomponent.getChatModifier().setChatHoverable(new ChatHoverable(ChatHoverable.EnumHoverAction.SHOW_ITEM, new ChatComponentText(nbttagcompound.toString())));
            ichatbasecomponent.getChatModifier().setColor(this.u().e);
        }

        return ichatbasecomponent;
    }

    public boolean a(Block block) {
        if (block == this.h) {
            return this.i;
        } else {
            this.h = block;
            if (this.hasTag() && this.tag.hasKeyOfType("CanDestroy", 9)) {
                NBTTagList nbttaglist = this.tag.getList("CanDestroy", 8);

                for (int i = 0; i < nbttaglist.size(); ++i) {
                    Block block1 = Block.getByName(nbttaglist.getString(i));

                    if (block1 == block) {
                        this.i = true;
                        return true;
                    }
                }
            }

            this.i = false;
            return false;
        }
    }

    public boolean b(Block block) {
        if (block == this.j) {
            return this.k;
        } else {
            this.j = block;
            if (this.hasTag() && this.tag.hasKeyOfType("CanPlaceOn", 9)) {
                NBTTagList nbttaglist = this.tag.getList("CanPlaceOn", 8);

                for (int i = 0; i < nbttaglist.size(); ++i) {
                    Block block1 = Block.getByName(nbttaglist.getString(i));

                    if (block1 == block) {
                        this.k = true;
                        return true;
                    }
                }
            }

            this.k = false;
            return false;
        }
    }
}
