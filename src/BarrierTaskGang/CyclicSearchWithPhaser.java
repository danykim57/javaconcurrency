package BarrierTaskGang;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;

public class CyclicSearchWithPhaser extends SearchTaskGangCommonCyclic{

    protected Phaser mPhaser;

    volatile int mReconfiguration;

    volatile CyclicBarrier mReconfigurationCyclicBarrier;

    CyclicSearchWithPhaser(String[] wordsToFind,
                           String[][] stringsToSearch) {
        super(wordsToFind,
                stringsToSearch);
        mReconfiguration = 0;

        mPhaser = new Phaser() {
            @Override
            public boolean onAdvance(int phase,
                                     int registeredParties) {
                int prevSize = getInput().size();

                setInput(getNextInput());

                if (getInput() == null || registeredParties == 0)
                    return true;
                else {
                    int newSize = getInput().size();

                    mReconfiguration = newSize - prevSize;

                    if (mReconfiguration == 0)
                        BarrierTaskGangTest.printDebugging("@@@ Started cycle "
                                                            + mCurrentCycle.get()
                                                            + " with same # of Threads ("
                                                            + newSize
                                                            + ") @@@");

                    else {
                        BarrierTaskGangTest.printDebugging("@@@ Started cycle "
                                + mCurrentCycle.get()
                                + " with "
                                + newSize
                                + " vs "
                                + prevSize
                                + " Threads @@@");
                        mReconfigurationCyclicBarrier = new CyclicBarrier(
                                prevSize,
                                new Runnable() {
                                    public void run() {
                                        if (prevSize < newSize)
                                            for (int i = prevSize; i < newSize; i++)
                                                new Thread(makeTask(i)).start();
                                        mReconfiguration = 0;
                                    }
                                });
                    }
                    return false;
                }
            }
        };
    }

    @Override
    protected Runnable makeTask(final int index) {
        mPhaser.register();
        return super.makeTask(index);
    }

    @Override
    protected void initiateHook(int size) {
        BarrierTaskGangTest.printDebugging(
                "@@@ Started cycle 1 with "
                + size
                + " Thread"
                + (size == 1 ? "" : "s")
                + "@@@");
    }

    @Override
    protected void taskDone(int index) throws IndexOutOfBoundsException {
        boolean throwException = false;
        try {
            mPhaser.arriveAndAwaitAdvance();

            if (mReconfiguration != 0) {
                try {
                    mReconfigurationCyclicBarrier.await();

                    if (index >= getInput().size()) {
                        mPhaser.arriveAndDeregister();

                        throwException = true;
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        if (throwException)
            throw new IndexOutOfBoundsException();
    }
}
