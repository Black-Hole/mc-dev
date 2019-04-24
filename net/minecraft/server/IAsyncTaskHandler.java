package net.minecraft.server;

import com.google.common.collect.Queues;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BooleanSupplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class IAsyncTaskHandler<R extends Runnable> implements Mailbox<R>, Executor {

    private final String b;
    private static final Logger c = LogManager.getLogger();
    private final Queue<R> d = Queues.newConcurrentLinkedQueue();
    private int e;

    protected IAsyncTaskHandler(String s) {
        this.b = s;
    }

    protected abstract R postToMainThread(Runnable runnable);

    protected abstract boolean c(R r0);

    public boolean isMainThread() {
        return Thread.currentThread() == this.ax();
    }

    protected abstract Thread ax();

    protected boolean isNotMainThread() {
        return !this.isMainThread();
    }

    @Override
    public String bd() {
        return this.b;
    }

    private CompletableFuture<Object> a(Runnable runnable) {
        return CompletableFuture.supplyAsync(() -> {
            runnable.run();
            return null;
        }, this);
    }

    public void f(Runnable runnable) {
        if (!this.isMainThread()) {
            this.a(runnable).join();
        } else {
            runnable.run();
        }

    }

    public void a(R r0) {
        this.d.add(r0);
        LockSupport.unpark(this.ax());
    }

    public void execute(Runnable runnable) {
        if (this.isNotMainThread()) {
            this.a(this.postToMainThread(runnable));
        } else {
            runnable.run();
        }

    }

    protected void bf() {
        while (this.p()) {
            ;
        }

    }

    protected boolean p() {
        R r0 = (Runnable) this.d.peek();

        if (r0 == null) {
            return false;
        } else if (this.e == 0 && !this.c(r0)) {
            return false;
        } else {
            this.h((Runnable) this.d.remove());
            return true;
        }
    }

    public void c(BooleanSupplier booleansupplier) {
        ++this.e;

        try {
            while (!booleansupplier.getAsBoolean()) {
                if (!this.p()) {
                    LockSupport.parkNanos("waiting for tasks", 1000L);
                }
            }
        } finally {
            --this.e;
        }

    }

    protected void h(R r0) {
        try {
            r0.run();
        } catch (Exception exception) {
            IAsyncTaskHandler.c.fatal("Error executing task on {}", this.bd(), exception);
        }

    }
}
