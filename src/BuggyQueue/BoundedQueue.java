package BuggyQueue;

public interface BoundedQueue<E> {

    default void put(E e)
            throws InterruptedException {
    }

    default E take()
            throws InterruptedException {
        return null;
    }

    default E poll() {
        return null;
    }

    default boolean offer(E e) {
        return false;
    }

    boolean isEmpty();

    boolean isFull();

    int size();
}
