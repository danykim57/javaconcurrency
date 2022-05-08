package TaskGang;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class OneShotExecutorService extends SearchTaskGangCommon {

    protected CountDownLatch mExitBarrier = null;

    private BlockingQueue<SearchResults> mResultsQueue =
            new LinkedBlockingQueue<>();

    protected final int MAX_THREADS = 4;

    public OneShotExecutorService(String[] wordsToFind,
                                  String[][] stringsToSearch) {
        super(wordsToFind,
                stringsToSearch);
    }

    @Override
    protected void initiateHook(int inputSize) {
        System.out.println("@@@ starting cycle "
                            + currentCycle()
                            + " with "
                            + inputSize
                            + " tasks@@@");
        mExitBarrier = new CountDownLatch(inputSize);

        if (getExecutor() == null)
            setExecutor (Executors.newFixedThreadPool(MAX_THREADS));
    }

    protected void initiateTaskGang(int inputSize) {
        initiateHook(inputSize);

        List<Callable<Object>> workerCollection =
                new ArrayList<>(inputSize);

        for (int i = 0; i < inputSize; ++i)
            workerCollection.add(Executors.callable(makeTask(i)));

        try {
            ExecutorService executorService =
                    (ExecutorService) getExecutor();
            executorService.invokeAll(workerCollection);
        } catch (InterruptedException e) {
            System.out.println("invokeAll() interrupted");
        }
    }

    @Override
    protected boolean processInput(String inputData) {
        for (String word : mWordsToFind)
            queueResults(searchForWord(word, inputData));
        return true;
    }

    @Override
    protected void taskDone(int index) throws IndexOutOfBoundsException {
        mExitBarrier.countDown();
    }

    protected void queueResults(SearchResults results) {
        getQueue().add(results);
    }

    @Override
    protected void awaitTasksDone() {
        do {
            processQueueResults(getInput().size()
                                * mWordsToFind.length);

            try {
                mExitBarrier.await();
            } catch (InterruptedException e) {
                System.out.println("await() interrupted");
            }
        } while (advanceTaskToNextCycle());

        super.awaitTasksDone();
    }

    protected BlockingQueue<SearchResults> getQueue() {
        return mResultsQueue;
    }
    protected BlockingQueue<SearchResults> setQueue(BlockingQueue<SearchResults> q) {
        BlockingQueue<SearchResults> old = mResultsQueue;
        mResultsQueue = q;
        return old;
    }

    protected void processQueueResults(final int resultCount) {
        Runnable processQueuedResultsRunnable = () -> {
            try {
                for (int i = 0; i < resultCount; i++)
                    getQueue().take().print();
            } catch (InterruptedException e) {
                System.out.println("run() interrupted");
            }
        };

        Thread t = new Thread(processQueuedResultsRunnable);
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            System.out.println("processQueuedResults() interrupted");
        }
    }
}
