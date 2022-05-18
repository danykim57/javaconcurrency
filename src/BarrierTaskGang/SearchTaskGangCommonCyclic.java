package BarrierTaskGang;

import java.util.concurrent.CountDownLatch;

public class SearchTaskGangCommonCyclic extends SearchTaskGangCommon {

    protected SearchTaskGangCommonCyclic(String[] wordsToFind, String[][] stringsToSearch) {
        super (wordsToFind, stringsToSearch);

        mExitBarrier = new CountDownLatch(1);
    }

    @Override
    protected boolean advanceTaskToNextCycle() {
        if (getInput() == null) {
            mExitBarrier.countDown();
            return false;
        } else
            return true;
    }
}
