package TaskGang;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

public abstract class TaskGang<E> implements Runnable {

    private volatile List<E> mInput = null;

    private Executor mExecutor = null;

    private final AtomicLong mCurrentCycle = new AtomicLong(0);

    protected List<E> getInput() {
        return mInput;
    }

    protected List<E> setInput(List<E> input) {
        return mInput = input;
    }

    protected Executor getmExecutor() {
        return mExecutor;
    }

    protected void setExecutor(Executor executor) {
        mExecutor = executor;
    }

    protected long incrementCycle() {
        return mCurrentCycle.incrementAndGet();
    }

    protected long currentCycle() {
        return mCurrentCycle.get();
    }

    protected  abstract List<E> getNextInput();

    protected void initiateHook(int inputSize) {

    }

    protected abstract void initiateTaskGang(int inputSize);

    protected boolean advanceTaskToNextCycle() {
        return false;
    }

    protected abstract void awaitTasksDone();

    protected void taskDone(int index) throws IndexOutOfBoundsException {

    }

    protected abstract boolean processInput(E inputData);

    @Override
    public void run() {
        setInput(getNextInput());

        initiateTaskGang(getInput().size());

        awaitTasksDone();
    }

    protected Runnable makeTask(final int index) {
        return new Runnable() {
            public void run() {
                try {
                    E element = getInput().get(index);

                    if (processInput(element))

                        taskDone(index);

                    else
                        return;
                } catch (IndexOutOfBoundsException e) {
                    return;
                }
            }
        };
    }
}
