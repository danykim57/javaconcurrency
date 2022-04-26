package ThreadJoinTest;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ThreadJoinTest {

    public static boolean diagnosticEnabled = true;

    private final static String[] mOneShotInputStrings =
            {"xreo", "xfao", "xmiomio", "xlao", "xtiotio", "xsoosoo", "xdoo", "xdoodoo"};

    private static String[] mWordList =
            {"do", "re", "mi", "fa", "sol", "la", "ti", "do",};

    static public class SearchOneShotThreadJoin {
        private volatile List<String> mInput;

        final String[] mWordsToFind;

        private List<Thread> mWorkerThreads;

        public SearchOneShotThreadJoin(String[] wordsToFind, String[] inputStrings) {
            mWordsToFind = wordsToFind;
            mInput = Arrays.asList(inputStrings);

            mWorkerThreads = new LinkedList<Thread>();

            for (int i = 0; i < mInput.size(); ++i) {
                Thread t = new Thread(makeTask(i));

                mWorkerThreads.add(t);

                t.start();
            }

            //Barrier sync
            for (Thread thread : mWorkerThreads)
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    printDebugging("join() interrupted");
                }
        }

        private Runnable makeTask(final int index) {
            return new Runnable() {
                @Override
                public void run() {
                    String element = mInput.get(index);

                    if (processInput(element) == false)
                        return;
                }
            };
        }

        private boolean processInput(String inputData) {
            for (String word : mWordsToFind)
                for (int i = inputData.indexOf(word, 0);
                     i != -1;
                     i = inputData.indexOf(word, i + word.length()))
                    processResult("in thread "
                            + Thread.currentThread().getId()
                            + " "
                            + word
                            + " was found at offset "
                            + i
                            + " in string "
                            + inputData);
            return true;
        }

        private void processResult(String results) {
            printDebugging(results);
        }
    }

        static void printDebugging(String output) {
            if (diagnosticEnabled)
                System.out.println(output);
        }

    public static void main(String[] args) {
        printDebugging("Starting ThreadJoinTest");
        new SearchOneShotThreadJoin(mWordList, mOneShotInputStrings);

        printDebugging("Ending JOIN");

        printDebugging("Ending ThreadJoinTest");
    }
}
