package TaskGang;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OneShotExecutorCompletionService extends SearchTaskGangCommon {

    protected ExecutorCompletionService<SearchResults> mCompletionService;

    protected OneShotExecutorCompletionService(String [] wordsToFind,
                                               String[][] stringsToSearch) {
        super(wordsToFind, stringsToSearch);

        setExecutor(Executors.newCachedThreadPool());

        mCompletionService = new ExecutorCompletionService<>(getExecutor());
    }

    protected void initiateTaskGang(int inputSize) {

        for (int i = 0; i < inputSize; i++)
            getExecutor().execute(makeTask(i));
        concurrentlyProcessQueuedFutures();
    }

    protected boolean processInput(final String inputData) {
        for (final String word : mWordsToFind) {
            mCompletionService
                    .submit (() ->
                            searchForWord(word, inputData));
        }
        return true;
    }

    private void concurrentlyProcessQueuedFutures() {
        int count = getInput().size() * mWordsToFind.length;

        for (int i = 0; i < count; i++)
            try {
                Future<SearchResults> resultFuture =
                        mCompletionService.take();
                resultFuture.get().print();
            } catch (Exception e) {
                System.out.println("get() exception");
            }
    }
}
