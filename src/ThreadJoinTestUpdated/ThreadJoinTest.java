package ThreadJoinTestUpdated;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ThreadJoinTest {

    private static final String sSHAKESPEARE_DATA_FILE =
            "completeWorksOfShakespeare.txt";

    private static final String sPHRASE_LIST_FILE =
            "phraseList.txt";

    private static final List<String> mInputList =
            TestDataFactory.getInput(sSHAKESPEARE_DATA_FILE, "@");

    private static final List<String> mPhrasesToFind =
            TestDataFactory.getPhraseList(sPHRASE_LIST_FILE);

    public static void main(String[] args) {
        System.out.println("Starting ThreadJoinTest");

        new SearchOneShotThreadJoin();

        System.out.println("Ending ThreadJoinTest");
    }

    static class SearchOneShotThreadJoin {
        SearchOneShotThreadJoin() {
            List<Thread> workerThreads = makeWorkerThreads(this::processInput);

            workerThreads.forEach(Thread::start);

            workerThreads.forEach(thread -> {
                try {
                    thread.join();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        List<Thread> makeWorkerThreads(Function<String, Void> task) {
            List<Thread> workerThreads = new ArrayList<Thread>();

            assert mInputList != null;

            mInputList.forEach
                    (input ->
                    workerThreads.add(new Thread(()
                    ->
                            task.apply(input))));
            return workerThreads;
        }

        private Void processInput(String input) {
            String title = getTitle(input);

            assert mPhrasesToFind != null;

            for (String phrase : mPhrasesToFind)

                for (int offset = input.indexOf(phrase);
                    offset != -1;
                    offset = input.indexOf(phrase, offset + phrase.length()))

                    System.out.println("in thread "
                                        + Thread.currentThread().getId()
                                        + " the phrase \""
                                        + phrase
                                        + "\" was found at character offset "
                                        + offset
                                        + " in \""
                                        + title
                                        + "\"");
            return null;
        }

        String getTitle(String input) {
            int endOfTitlePos = input.indexOf('\n');

            return input.substring(0, endOfTitlePos);
        }
    }
}
