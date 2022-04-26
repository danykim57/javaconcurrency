package UserThreadInterrupted;

public class TestInterrupted {

    public static void main(String[] args) {
        System.out.println("Entering main()");

        final Boolean interruptThread = args.length == 0;

        GCDRunnable runnableCommand =
                new GCDRunnable();
        Thread thr = new Thread(runnableCommand);

        thr.start();

        try {
            if (interruptThread) {
                Thread.sleep(4000);

                System.out.println("interrupted thread "
                                + thr.getName());
                thr.interrupt();
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        System.out.println("Leaving main()");
    }
}
