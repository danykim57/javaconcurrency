package UserOrDaemonRunnable;

import static java.lang.Math.abs;

import java.util.Random;

public class GCDRunnable
        extends Random
        implements Runnable {

    final private String threadType;
    private final int MAX_ITERATIONS = 100000000;

    public GCDRunnable(String threadType) {
        this.threadType = threadType;
    }

    private int computeGCD(int number1,
                           int number2) {
        if (number2 == 0)
            return number1;
        return computeGCD(number2,
                number1 % number2);
    }

    public void run() {
        final String threadString =
                " with "
                        + threadType
                        + " thread id "
                        + Thread.currentThread();

        System.out.println("Entering run()"
                + threadString);

        try {
            // Iterate for the give number of times.
            for (int i = 0; i < MAX_ITERATIONS; ++i) {
                // Generate two random numbers (nextInt() obtained
                // from Random superclass).
                int number1 = abs(nextInt());
                int number2 = abs(nextInt());

                // Print results every 10 million iterations.
                if ((i % 10000000) == 0)
                    System.out.println("In run()"
                            + threadString
                            + " the GCD of "
                            + number1
                            + " and "
                            + number2
                            + " is "
                            + computeGCD(number1,
                            number2));
            }
        }
        finally {
            System.out.println("Leaving run() "
                    + threadString);
        }
    }
}