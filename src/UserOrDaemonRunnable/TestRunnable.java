package UserOrDaemonRunnable;

public class TestRunnable {
    public static void main(String[] args) {
        System.out.println("Entering main()");

        final Boolean daemonThread = args.length > 0;

        GCDRunnable runnableCommand = new GCDRunnable(daemonThread ? "daemon" :"user");

        Thread thr = new Thread(runnableCommand);

        if (daemonThread) thr.setDaemon(true);

        thr.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        System.out.println("Leaving main()");
    }
}
