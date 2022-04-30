package TaskGang;

public class TaskGangTest {
     enum TestsToRun {
         ONESHOT_THREAD_PER_TASK,
         ONESHOT_EXECUTOR_SERVICE,
         ONESHOT_EXECUTOR_SERVICE_FUTURE,
         ONESHOT_EXECUTOR_COMPLETION_SERVICE,
     }

     private final static String[] mWordList = { "do",
                                               "re",
                                               "mi",
                                               "fa",
                                               "so",
                                               "la",
                                               "ti",
                                               "do",};

     private final static String[][] mOneShotInputStrings = {
             {"xreo", "xfao", "xmiomio", "xlao", "xtiotio", "xsoosoo", "xdoo", "xdoodoo"}
     };

     public static boolean diagnosticsEnabled = true;

     private static void printDebugging(String output) {
         if (diagnosticsEnabled)
             System.out.println(output);
     }

     private static Runnable makeTaskGang(String[] wordList, TestsToRun choice) {

         switch (choice) {
             case ONESHOT_THREAD_PER_TASK:
                 return new OneShotThreadPerTask(wordList, mOneShotInputStrings);

             case ONESHOT_EXECUTOR_SERVICE:
                 return new OneShotExecutorService(wordList, mOneShotInputStrings);

             case ONESHOT_EXECUTOR_SERVICE_FUTURE:
                 return new OneShotExecutorServiceFuture(wordList, mOneShotInputStrings);

             case ONESHOT_EXECUTOR_COMPLETION_SERVICE:
                 return new OneShotExecutorCompletionService(wordList, mOneShotInputStrings);
         }
         return null;
     }

    public static void main(String[] args) {
        printDebugging("Starting TaskGangTest");

        for (TestsToRun test : TestsToRun.values()) {
            printDebugging("Starting " + test);
            makeTaskGang(mWordList, test).run();
            printDebugging("Ending " + test);
        }

        printDebugging("Ending TaskGangTest");
    }
}
