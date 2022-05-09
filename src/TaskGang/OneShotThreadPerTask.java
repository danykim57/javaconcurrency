package TaskGang;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

public class OneShotThreadPerTask extends SearchTaskGangCommon{

    private final List<Thread> mWorkerThreads;

    public OneShotThreadPerTask(String[] wordsToFind, String[][] stringsToSearch) {

        super(wordsToFind, stringsToSearch);

        mWorkerThreads = new LinkedList<>();
    }

    protected void initiateTaskGang(int inputSize) {
        if (getExecutor() == null)
            setExecutor(r -> {
                Thread thread = new Thread(r);
                mWorkerThreads.add(thread);
                thread.start();
            });
        for (int i = 0; i < inputSize; ++i)
            getExecutor().execute(makeTask(i));
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

    protected void awaitTasksDone() {
        for (Thread thread : mWorkerThreads)
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("awaitTasksDone interrupted");
            }
    }
}
