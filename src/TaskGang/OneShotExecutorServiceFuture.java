package TaskGang;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OneShotExecutorServiceFuture extends SearchTaskGangCommon{

    protected List<Future<SearchResults>> mResultFutures;

    protected OneShotExecutorServiceFuture(String[] wordsToFind, String[][] stringsToSearch) {

        super(wordsToFind,
                stringsToSearch);

        setExecutor(Executors.newCachedThreadPool());
    }

    protected void initiateTaskGang(int inputSize) {

        mResultFutures =
                new ArrayList<>(inputSize
                                * mWordsToFind.length);

        for (final String inputData : getInput())
            processInput(inputData);

        processFutureResults(mResultFutures);
    }

    protected boolean processInput(String inputData) {
        ExecutorService executorService =
                (ExecutorService) getExecutor();

        for (final String word : mWordsToFind) {

            Future<SearchResults> resultFuture = executorService
                    .submit(() -> searchForWord(word, inputData));
            mResultFutures.add(resultFuture);
        }
        return true;
    }

    protected void processFutureResults
            (List<Future<SearchResults>> mResultFutures) {
        for (Future<SearchResults> resultFuture : mResultFutures) {
            try {
                resultFuture.get().print();
            } catch (Exception e) {
                System.out.println("get() exception");
            }
        }
    }
}
