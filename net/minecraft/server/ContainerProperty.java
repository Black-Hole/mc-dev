package net.minecraft.server;

public abstract class ContainerProperty {

    private int a;

    public ContainerProperty() {}

    public static ContainerProperty a(final IContainerProperties icontainerproperties, final int i) {
        return new ContainerProperty() {
            @Override
            public int b() {
                return icontainerproperties.getProperty(i);
            }

            @Override
            public void a(int j) {
                icontainerproperties.setProperty(i, j);
            }
        };
    }

    public static ContainerProperty a(final int[] aint, final int i) {
        return new ContainerProperty() {
            @Override
            public int b() {
                return aint[i];
            }

            @Override
            public void a(int j) {
                aint[i] = j;
            }
        };
    }

    public static ContainerProperty a() {
        return new ContainerProperty() {
            private int a;

            @Override
            public int b() {
                return this.a;
            }

            @Override
            public void a(int i) {
                this.a = i;
            }
        };
    }

    public abstract int b();

    public abstract void a(int i);

    public boolean c() {
        int i = this.b();
        boolean flag = i != this.a;

        this.a = i;
        return flag;
    }
}
