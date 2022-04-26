package UserOrDaemonExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class TestExecutor {

    public static void main(String[] args) {
        System.out.println("Entering main()");

        final Boolean daemonThread = args.length > 0;

        GCDRunnable runnableCommand =
                new GCDRunnable(daemonThread ? "daemon" : "user");
        final int POOL_SIZE = 2;

        final ThreadFactory threadFactory =
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thr = new Thread(r);
                        if (daemonThread)
                            thr.setDaemon(true);
                        return thr;
                    }
                };

        final Executor executor =
                Executors.newFixedThreadPool(POOL_SIZE,
                        threadFactory);
        for (int i = 0; i < POOL_SIZE; i++)
            executor.execute(runnableCommand);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        System.out.println("Leaving main()");
    }
}
