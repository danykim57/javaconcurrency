package BusySynchronizedQueue;

import java.util.LinkedList;

public class BusySynchronizedQueue<E> implements BoundedQueue<E>{
    private LinkedList<E> mList;

    private final int mCapacity;

    public BusySynchronizedQueue() {
        this(Integer.MAX_VALUE);
    }

    public BusySynchronizedQueue(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        mCapacity = capacity;
        mList = new LinkedList<>();
    }

    @Override
    public synchronized boolean isEmpty() {
        return mList.size() == 0;
    }

    @Override
    public synchronized boolean isFull() {
        return mList.size() == mCapacity;
    }

    @Override
    public synchronized int size() {
        return mList.size();
    }

    @Override
    public synchronized E poll() {
        return mList.poll();
    }

    @Override
    public synchronized boolean offer(E e) {
        if (!isFull()) {
            mList.add(e);
            return true;
        } else
            return false;
    }
}
