package p033b.p034a.p035a.p036a.p037a.p040c;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: b.a.a.a.a.c.c */
public class C1150c<E extends C1149b & C1154l & C1153i> extends PriorityBlockingQueue<E> {
    /* renamed from: a */
    final Queue<E> f3367a = new LinkedList();
    /* renamed from: b */
    private final ReentrantLock f3368b = new ReentrantLock();

    /* renamed from: a */
    public E m6132a() {
        return m6139b(0, null, null);
    }

    /* renamed from: a */
    E m6133a(int i, Long l, TimeUnit timeUnit) {
        switch (i) {
            case 0:
                return (C1149b) super.take();
            case 1:
                return (C1149b) super.peek();
            case 2:
                return (C1149b) super.poll();
            case 3:
                return (C1149b) super.poll(l.longValue(), timeUnit);
            default:
                return null;
        }
    }

    /* renamed from: a */
    public E m6134a(long j, TimeUnit timeUnit) {
        return m6139b(3, Long.valueOf(j), timeUnit);
    }

    /* renamed from: a */
    boolean m6135a(int i, E e) {
        try {
            this.f3368b.lock();
            if (i == 1) {
                super.remove(e);
            }
            boolean offer = this.f3367a.offer(e);
            return offer;
        } finally {
            this.f3368b.unlock();
        }
    }

    /* renamed from: a */
    boolean m6136a(E e) {
        return e.mo1031d();
    }

    /* renamed from: a */
    <T> T[] m6137a(T[] tArr, T[] tArr2) {
        int length = tArr.length;
        int length2 = tArr2.length;
        Object[] objArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), length + length2);
        System.arraycopy(tArr, 0, objArr, 0, length);
        System.arraycopy(tArr2, 0, objArr, length, length2);
        return objArr;
    }

    /* renamed from: b */
    public E m6138b() {
        E e = null;
        try {
            e = m6139b(1, null, null);
        } catch (InterruptedException e2) {
        }
        return e;
    }

    /* renamed from: b */
    E m6139b(int i, Long l, TimeUnit timeUnit) {
        C1149b a;
        while (true) {
            a = m6133a(i, l, timeUnit);
            if (a == null || m6136a(a)) {
                return a;
            }
            m6135a(i, a);
        }
        return a;
    }

    /* renamed from: c */
    public E m6140c() {
        E e = null;
        try {
            e = m6139b(2, null, null);
        } catch (InterruptedException e2) {
        }
        return e;
    }

    public void clear() {
        try {
            this.f3368b.lock();
            this.f3367a.clear();
            super.clear();
        } finally {
            this.f3368b.unlock();
        }
    }

    public boolean contains(Object obj) {
        try {
            this.f3368b.lock();
            boolean z = super.contains(obj) || this.f3367a.contains(obj);
            this.f3368b.unlock();
            return z;
        } catch (Throwable th) {
            this.f3368b.unlock();
        }
    }

    /* renamed from: d */
    public void m6141d() {
        try {
            this.f3368b.lock();
            Iterator it = this.f3367a.iterator();
            while (it.hasNext()) {
                C1149b c1149b = (C1149b) it.next();
                if (m6136a(c1149b)) {
                    super.offer(c1149b);
                    it.remove();
                }
            }
        } finally {
            this.f3368b.unlock();
        }
    }

    public int drainTo(Collection<? super E> collection) {
        try {
            this.f3368b.lock();
            int drainTo = super.drainTo(collection) + this.f3367a.size();
            while (!this.f3367a.isEmpty()) {
                collection.add(this.f3367a.poll());
            }
            return drainTo;
        } finally {
            this.f3368b.unlock();
        }
    }

    public int drainTo(Collection<? super E> collection, int i) {
        try {
            this.f3368b.lock();
            int drainTo = super.drainTo(collection, i);
            while (!this.f3367a.isEmpty() && drainTo <= i) {
                collection.add(this.f3367a.poll());
                drainTo++;
            }
            this.f3368b.unlock();
            return drainTo;
        } catch (Throwable th) {
            this.f3368b.unlock();
        }
    }

    public /* synthetic */ Object peek() {
        return m6138b();
    }

    public /* synthetic */ Object poll() {
        return m6140c();
    }

    public /* synthetic */ Object poll(long j, TimeUnit timeUnit) {
        return m6134a(j, timeUnit);
    }

    public boolean remove(Object obj) {
        try {
            this.f3368b.lock();
            boolean z = super.remove(obj) || this.f3367a.remove(obj);
            this.f3368b.unlock();
            return z;
        } catch (Throwable th) {
            this.f3368b.unlock();
        }
    }

    public boolean removeAll(Collection<?> collection) {
        try {
            this.f3368b.lock();
            boolean removeAll = super.removeAll(collection) | this.f3367a.removeAll(collection);
            return removeAll;
        } finally {
            this.f3368b.unlock();
        }
    }

    public int size() {
        try {
            this.f3368b.lock();
            int size = this.f3367a.size() + super.size();
            return size;
        } finally {
            this.f3368b.unlock();
        }
    }

    public /* synthetic */ Object take() {
        return m6132a();
    }

    public Object[] toArray() {
        try {
            this.f3368b.lock();
            Object[] a = m6137a(super.toArray(), this.f3367a.toArray());
            return a;
        } finally {
            this.f3368b.unlock();
        }
    }

    public <T> T[] toArray(T[] tArr) {
        try {
            this.f3368b.lock();
            T[] a = m6137a(super.toArray(tArr), this.f3367a.toArray(tArr));
            return a;
        } finally {
            this.f3368b.unlock();
        }
    }
}
