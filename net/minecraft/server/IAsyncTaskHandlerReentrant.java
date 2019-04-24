package net.minecraft.server;

public abstract class IAsyncTaskHandlerReentrant<R extends Runnable> extends IAsyncTaskHandler<R> {

    private int b;

    public IAsyncTaskHandlerReentrant(String s) {
        super(s);
    }

    @Override
    protected boolean isNotMainThread() {
        return this.bg() || super.isNotMainThread();
    }

    protected boolean bg() {
        return this.b != 0;
    }

    @Override
    protected void h(R r0) {
        ++this.b;

        try {
            super.h(r0);
        } finally {
            --this.b;
        }

    }
}
