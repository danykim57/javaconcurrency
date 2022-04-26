package UserOrDaemonThread;

public class TestThread {

    public static void main(String[] args) {
        System.out.println("Entering main()");

        final Boolean daemonThread = args.length > 0;

        UserOrDaemonThread thr =
                new UserOrDaemonThread(daemonThread);
        thr.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        System.out.println("Levaing main()");
    }
}
