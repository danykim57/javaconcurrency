package BarrierTaskGang;

public class BarrierTaskGangTest {

    enum TestsToRun {
        COUNTDOWNLATCH,
        CYCLIC_BARRIER,
        PHASER
    }

    public static boolean diagnosticsEnabled = true;

    static void printDebugging(String output) {
        if (diagnosticsEnabled)
            System.out.println(output);
    }

    private final static String[] mWordList = {"do",
            "re",
            "mi",
            "fa",
            "so",
            "la",
            "ti",
            "do"};

    private final static String[][] mOneShotInputStrings = {
            {"xreo", "xfao", "xmiomio", "xlao", "xtiotio", "xsoosoo", "xdoo", "xdoodoo"}
    };

    private static Runnable makeTaskGang(String[] wordList,
                                         TestsToRun choice) {
        switch (choice) {
            case COUNTDOWNLATCH:
                return new OneShotSearchWithCountDownLatch(wordList, mOneShotInputStrings);

            case CYCLIC_BARRIER:
                return new CyclicSearchWithCyclicBarrier(wordList,
                        mFixedNumberOfInputStrings);
            case PHASER:
                return new CyclicSearchWithPhaser(wordList,
                        mVariableNumberOfInputStrings);
        }
        return null;
    }

    public static void main(String[] args) {
        printDebugging("Starting BarrierTaskGangTest");

        for (TestsToRun test : TestsToRun.values()) {
            printDebugging("Starting "
            + test);

            makeTaskGang(mWordList, test).run();

            printDebugging("Ending "
            + test);
        }
        printDebugging("Ending BarrierTaskGangTest");
    }


}
