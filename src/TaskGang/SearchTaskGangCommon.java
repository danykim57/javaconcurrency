package TaskGang;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class SearchTaskGangCommon extends TaskGang<String>{

    protected final String[] mWordsToFind;

    private final Iterator<String[]> mInputIterator;

    protected SearchTaskGangCommon(String[] wordsToFind, String[][] stringsToSearch) {
        mWordsToFind = wordsToFind;

        mInputIterator = Arrays.asList(stringsToSearch).iterator();
    }

    @Override
    protected List<String> getNextInput() {
        if (mInputIterator.hasNext()) {
            incrementCycle();

            return Arrays.asList(mInputIterator.next());
        }
        else return null;
    }

    protected SearchResults searchForWord(String word, String inputData) {
        SearchResults results =
                new SearchResults(Thread.currentThread().getId(),
                        currentCycle(),
                        word,
                        inputData);
        for (int i = inputData.indexOf(word, 0);
             i != -1;
             i = inputData.indexOf(word, i + 1)) {


            results.add(i);
        }
        return results;
    }

    protected void awaitTasksDone() {
        if (getExecutor() instanceof ExecutorService) {
            ExecutorService executorService =
                    (ExecutorService) getExecutor();
            executorService.shutdown();
            try {
                executorService.awaitTermination(Long.MAX_VALUE,
                        TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
            }
        }
    }
}
