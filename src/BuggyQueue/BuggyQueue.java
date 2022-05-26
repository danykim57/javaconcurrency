package BuggyQueue;

import java.util.LinkedList;

public class BuggyQueue<E> implements BoundedQueue<E>{

    private LinkedList<E> mList;

    private final int mCapacity;

    public BuggyQueue() {
        this(Integer.MAX_VALUE);
    }

    public BuggyQueue(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException();

        mCapacity = capacity;
        mList = new LinkedList<>();
    }

    public E poll() {
        if (!isEmpty()) {
            return mList.remove(0);
        } else
            return null;
    }

    public boolean offer(E e) {
        if (!isFull()) {
            mList.add(e);
            return true;
        } else
            return false;
    }

    public boolean isEmpty() {
        return mList.size() == 0;
    }

    public boolean isFull() {
        return mList.size() == mCapacity;
    }

    public int size() {
        return mList.size();
    }

}
