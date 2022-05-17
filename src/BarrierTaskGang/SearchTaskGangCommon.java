package BarrierTaskGang;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class SearchTaskGangCommon extends TaskGang<String> {

    protected final String[] mWordsToFind;

    private final Iterator<String[]> mInputIterator;

    protected CountDownLatch mExitBarrier = null;

    protected SearchTaskGangCommon(String[] wordsToFind, String[][] stringsToSearch) {
        mWordsToFind = wordsToFind;

        mInputIterator = Arrays.asList(stringsToSearch).iterator();
    }

    @Override
    protected List<String> getNextInput() {
        if (!mInputIterator.hasNext()) return null;

        else {
            incrementCycle();
            return Arrays.asList(mInputIterator.next());
        }
    }

    @Override
    protected void initiateTaskGang(int size) {
        initiateHook(size);

        for (int i = 0; i < size; ++i)
            new Thread(makeTask(i)).start();
    }

    protected Runnable makeTask(final int index) {
        return new Runnable() {
            public void run() {
                do {
                    try {
                        String element = getInput().get(index);

                        if (processInput(element)) taskDone(index);
                        else return;
                    } catch (IndexOutOfBoundsException e) {
                        return;
                    }
                } while (advanceTaskToNextCycle());
            }
        };
    }

    @Override
    protected boolean processInput(String inputData) {
        for (String word : mWordsToFind) {
            SearchResults results = searchForWord(word, inputData);

            synchronized (System.out) {
                results.print();
            }
        }
        return true;
    }

    protected SearchResults searchForWord(String word, String inputData) {

        SearchResults results =
                new SearchResults(Thread.currentThread().getId(),
                                currentCycle(),
                                word,
                                inputData);

        for (int i = inputData.indexOf(word, 0);
             i != -1;
             i = inputData.indexOf(word, i + word.length())) {
            results.add(i);
        }
        return results;
    }

    @Override
    protected void awaitTasksDone() {
        try {
            mExitBarrier.await();
        } catch (InterruptedException e) {
        }
    }
}
