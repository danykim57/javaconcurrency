package BarrierTaskGang;

import java.util.concurrent.CyclicBarrier;

public class CyclicSearchWithCyclicBarrier extends SearchTaskGangCommonCyclic{

    protected CyclicBarrier mCyclicBarrier;

    CyclicSearchWithCyclicBarrier(String[] wordsToFind, String[][] stringsToSearch) {
        super(wordsToFind, stringsToSearch);
    }

    @Override
    protected void taskDone(int index) throws IndexOutOfBoundsException {
        try {
            mCyclicBarrier.await();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void initiateHook(int size) {
        mCyclicBarrier = new CyclicBarrier
                (size,
                () -> {
            setInput(getNextInput());
            if (getInput() != null)
                BarrierTaskGangTest.printDebugging
                        ("@@@ Started cycle "
                         + currentCycle()
                        + " @@@");
                });
        BarrierTaskGangTest.printDebugging(
                "@@@ Started cycle 1 with "
                + size
                + " Thread"
                + (size == 1 ? "" : "s")
                + " @@@");
        );
    }

}
