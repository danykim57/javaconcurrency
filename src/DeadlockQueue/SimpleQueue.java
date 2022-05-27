package DeadlockQueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class SimpleQueue<E> implements BlockingQueue<E> {

    private final List<E> mList = new ArrayList<E>();

    public boolean isEmpty() {
        return mList.size() == 0;
    }

    public void put(E msg) throws InterruptedException {
        mList.add(msg);
    }

    public int size() {
        return mList.size();
    }

    public boolean add(E e) {
        return false;
    }

    public boolean offer(E e) {
        return false;
    }

    public boolean offer(E e, long timeout, TimeUnit unit) {
        try {
            put(e);
        }
        catch (InterruptedException ex) {

        }
        return true;
    }

    public E take() throws InterruptedException {
        return mList.remove(0);
    }

    public E poll() {
        return null;
    }

    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        return take();
    }

    public int remainingCapacity() {
        return 0;
    }

    public boolean remove(Object o) {
        return false;
    }

    public boolean contains(Object o) {
        return false;
    }

    public int drainTo(Collection<? super E> c) {
        return 0;
    }

    public int drainTo(Collection<? super E> c, int maxElements) {
        return 0;
    }

    public E remove() {
        return null;
    }

    public E element() {
        return null;
    }

    public E peek() {
        return null;
    }

    public Iterator<E> iterator() {
        return null;
    }

    public <T> T[] toArray(T[] array) {
        return null;
    }

    public Object[] toArray() {
        return null;
    }

    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    public boolean addAll(Collection<? extends E> collection) {
        return false;
    }

    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    public void clear() {}
}
