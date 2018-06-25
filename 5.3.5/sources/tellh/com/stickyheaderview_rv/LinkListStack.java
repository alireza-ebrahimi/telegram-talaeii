package tellh.com.stickyheaderview_rv;

import java.util.EmptyStackException;
import java.util.LinkedList;

public class LinkListStack<E> {
    private LinkedList<E> list = new LinkedList();

    public E push(E item) {
        this.list.addLast(item);
        return item;
    }

    public E pop() {
        if (this.list.isEmpty()) {
            return null;
        }
        return this.list.removeLast();
    }

    public synchronized E peek() {
        if (this.list.size() == 0) {
            throw new EmptyStackException();
        }
        return this.list.peekLast();
    }

    public boolean isEmpty() {
        return this.list.size() == 0;
    }

    public void clear() {
        this.list.clear();
    }

    public boolean remove(E item) {
        return this.list.remove(item);
    }
}
