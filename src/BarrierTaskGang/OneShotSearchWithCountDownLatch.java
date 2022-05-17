package BarrierTaskGang;

import java.util.concurrent.CountDownLatch;

public class OneShotSearchWithCountDownLatch extends SearchTaskGangCommon {
    OneShotSearchWithCountDownLatch(String[] wordsToFind, String[][] stringsToSearch) {
        super(wordsToFind, stringsToSearch);
    }

    protected void initiateHook(int size) {
        BarrierTaskGangTest.printDebugging
                ("@@@ Started cycle 1 with "
                 + size
                 + " Thread"
                 + (size == 1 ? "" : "s")
                 + " @@@");
        mExitBarrier = new CountDownLatch(size);
    }

    @Override
    protected void taskDone(int index) throws IndexOutOfBoundsException {
        mExitBarrier.countDown();
    }
}
