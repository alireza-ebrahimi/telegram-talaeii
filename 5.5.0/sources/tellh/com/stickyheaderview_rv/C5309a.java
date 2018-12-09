package tellh.com.stickyheaderview_rv;

import java.util.EmptyStackException;
import java.util.LinkedList;

/* renamed from: tellh.com.stickyheaderview_rv.a */
public class C5309a<E> {
    /* renamed from: a */
    private LinkedList<E> f10215a = new LinkedList();

    /* renamed from: a */
    public E m14118a() {
        return this.f10215a.isEmpty() ? null : this.f10215a.removeLast();
    }

    /* renamed from: a */
    public E m14119a(E e) {
        this.f10215a.addLast(e);
        return e;
    }

    /* renamed from: b */
    public synchronized E m14120b() {
        if (this.f10215a.size() == 0) {
            throw new EmptyStackException();
        }
        return this.f10215a.peekLast();
    }

    /* renamed from: c */
    public boolean m14121c() {
        return this.f10215a.size() == 0;
    }
}
