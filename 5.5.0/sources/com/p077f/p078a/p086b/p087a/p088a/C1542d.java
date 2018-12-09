package com.p077f.p078a.p086b.p087a.p088a;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: com.f.a.b.a.a.d */
public class C1542d<E> extends AbstractQueue<E> implements C1541a<E>, Serializable {
    /* renamed from: a */
    transient C1547c<E> f4677a;
    /* renamed from: b */
    transient C1547c<E> f4678b;
    /* renamed from: c */
    final ReentrantLock f4679c;
    /* renamed from: d */
    private transient int f4680d;
    /* renamed from: e */
    private final int f4681e;
    /* renamed from: f */
    private final Condition f4682f;
    /* renamed from: g */
    private final Condition f4683g;

    /* renamed from: com.f.a.b.a.a.d$a */
    private abstract class C1545a implements Iterator<E> {
        /* renamed from: a */
        C1547c<E> f4684a;
        /* renamed from: b */
        E f4685b;
        /* renamed from: c */
        final /* synthetic */ C1542d f4686c;
        /* renamed from: d */
        private C1547c<E> f4687d;

        C1545a(C1542d c1542d) {
            this.f4686c = c1542d;
            ReentrantLock reentrantLock = c1542d.f4679c;
            reentrantLock.lock();
            try {
                this.f4684a = mo1223a();
                this.f4685b = this.f4684a == null ? null : this.f4684a.f4689a;
            } finally {
                reentrantLock.unlock();
            }
        }

        /* renamed from: b */
        private C1547c<E> m7666b(C1547c<E> c1547c) {
            while (true) {
                C1547c<E> a = mo1224a(c1547c);
                if (a == null) {
                    return null;
                }
                if (a.f4689a != null) {
                    return a;
                }
                if (a == c1547c) {
                    return mo1223a();
                }
                c1547c = a;
            }
        }

        /* renamed from: a */
        abstract C1547c<E> mo1223a();

        /* renamed from: a */
        abstract C1547c<E> mo1224a(C1547c<E> c1547c);

        /* renamed from: b */
        void m7669b() {
            ReentrantLock reentrantLock = this.f4686c.f4679c;
            reentrantLock.lock();
            try {
                this.f4684a = m7666b(this.f4684a);
                this.f4685b = this.f4684a == null ? null : this.f4684a.f4689a;
            } finally {
                reentrantLock.unlock();
            }
        }

        public boolean hasNext() {
            return this.f4684a != null;
        }

        public E next() {
            if (this.f4684a == null) {
                throw new NoSuchElementException();
            }
            this.f4687d = this.f4684a;
            E e = this.f4685b;
            m7669b();
            return e;
        }

        public void remove() {
            C1547c c1547c = this.f4687d;
            if (c1547c == null) {
                throw new IllegalStateException();
            }
            this.f4687d = null;
            ReentrantLock reentrantLock = this.f4686c.f4679c;
            reentrantLock.lock();
            try {
                if (c1547c.f4689a != null) {
                    this.f4686c.m7655a(c1547c);
                }
                reentrantLock.unlock();
            } catch (Throwable th) {
                reentrantLock.unlock();
            }
        }
    }

    /* renamed from: com.f.a.b.a.a.d$b */
    private class C1546b extends C1545a {
        /* renamed from: d */
        final /* synthetic */ C1542d f4688d;

        private C1546b(C1542d c1542d) {
            this.f4688d = c1542d;
            super(c1542d);
        }

        /* renamed from: a */
        C1547c<E> mo1223a() {
            return this.f4688d.f4677a;
        }

        /* renamed from: a */
        C1547c<E> mo1224a(C1547c<E> c1547c) {
            return c1547c.f4691c;
        }
    }

    /* renamed from: com.f.a.b.a.a.d$c */
    static final class C1547c<E> {
        /* renamed from: a */
        E f4689a;
        /* renamed from: b */
        C1547c<E> f4690b;
        /* renamed from: c */
        C1547c<E> f4691c;

        C1547c(E e) {
            this.f4689a = e;
        }
    }

    public C1542d() {
        this(Integer.MAX_VALUE);
    }

    public C1542d(int i) {
        this.f4679c = new ReentrantLock();
        this.f4682f = this.f4679c.newCondition();
        this.f4683g = this.f4679c.newCondition();
        if (i <= 0) {
            throw new IllegalArgumentException();
        }
        this.f4681e = i;
    }

    /* renamed from: b */
    private boolean m7649b(C1547c<E> c1547c) {
        if (this.f4680d >= this.f4681e) {
            return false;
        }
        C1547c c1547c2 = this.f4677a;
        c1547c.f4691c = c1547c2;
        this.f4677a = c1547c;
        if (this.f4678b == null) {
            this.f4678b = c1547c;
        } else {
            c1547c2.f4690b = c1547c;
        }
        this.f4680d++;
        this.f4682f.signal();
        return true;
    }

    /* renamed from: c */
    private boolean m7650c(C1547c<E> c1547c) {
        if (this.f4680d >= this.f4681e) {
            return false;
        }
        C1547c c1547c2 = this.f4678b;
        c1547c.f4690b = c1547c2;
        this.f4678b = c1547c;
        if (this.f4677a == null) {
            this.f4677a = c1547c;
        } else {
            c1547c2.f4691c = c1547c;
        }
        this.f4680d++;
        this.f4682f.signal();
        return true;
    }

    /* renamed from: f */
    private E m7651f() {
        C1547c c1547c = this.f4677a;
        if (c1547c == null) {
            return null;
        }
        C1547c c1547c2 = c1547c.f4691c;
        E e = c1547c.f4689a;
        c1547c.f4689a = null;
        c1547c.f4691c = c1547c;
        this.f4677a = c1547c2;
        if (c1547c2 == null) {
            this.f4678b = null;
        } else {
            c1547c2.f4690b = null;
        }
        this.f4680d--;
        this.f4683g.signal();
        return e;
    }

    /* renamed from: g */
    private E m7652g() {
        C1547c c1547c = this.f4678b;
        if (c1547c == null) {
            return null;
        }
        C1547c c1547c2 = c1547c.f4690b;
        E e = c1547c.f4689a;
        c1547c.f4689a = null;
        c1547c.f4690b = c1547c;
        this.f4678b = c1547c2;
        if (c1547c2 == null) {
            this.f4677a = null;
        } else {
            c1547c2.f4691c = null;
        }
        this.f4680d--;
        this.f4683g.signal();
        return e;
    }

    /* renamed from: a */
    public E m7653a() {
        E b = m7658b();
        if (b != null) {
            return b;
        }
        throw new NoSuchElementException();
    }

    /* renamed from: a */
    public E m7654a(long j, TimeUnit timeUnit) {
        long toNanos = timeUnit.toNanos(j);
        ReentrantLock reentrantLock = this.f4679c;
        reentrantLock.lockInterruptibly();
        long j2 = toNanos;
        while (true) {
            try {
                E f = m7651f();
                if (f != null) {
                    reentrantLock.unlock();
                    return f;
                } else if (j2 <= 0) {
                    break;
                } else {
                    j2 = this.f4682f.awaitNanos(j2);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        return null;
    }

    /* renamed from: a */
    void m7655a(C1547c<E> c1547c) {
        C1547c c1547c2 = c1547c.f4690b;
        C1547c c1547c3 = c1547c.f4691c;
        if (c1547c2 == null) {
            m7651f();
        } else if (c1547c3 == null) {
            m7652g();
        } else {
            c1547c2.f4691c = c1547c3;
            c1547c3.f4690b = c1547c2;
            c1547c.f4689a = null;
            this.f4680d--;
            this.f4683g.signal();
        }
    }

    /* renamed from: a */
    public void m7656a(E e) {
        if (!m7661c((Object) e)) {
            throw new IllegalStateException("Deque full");
        }
    }

    /* renamed from: a */
    public boolean m7657a(E e, long j, TimeUnit timeUnit) {
        if (e == null) {
            throw new NullPointerException();
        }
        C1547c c1547c = new C1547c(e);
        long toNanos = timeUnit.toNanos(j);
        ReentrantLock reentrantLock = this.f4679c;
        reentrantLock.lockInterruptibly();
        while (!m7650c(c1547c)) {
            try {
                if (toNanos <= 0) {
                    return false;
                }
                toNanos = this.f4683g.awaitNanos(toNanos);
            } finally {
                reentrantLock.unlock();
            }
        }
        reentrantLock.unlock();
        return true;
    }

    public boolean add(E e) {
        m7656a((Object) e);
        return true;
    }

    /* renamed from: b */
    public E m7658b() {
        ReentrantLock reentrantLock = this.f4679c;
        reentrantLock.lock();
        try {
            E f = m7651f();
            return f;
        } finally {
            reentrantLock.unlock();
        }
    }

    /* renamed from: b */
    public boolean m7659b(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        C1547c c1547c = new C1547c(e);
        ReentrantLock reentrantLock = this.f4679c;
        reentrantLock.lock();
        try {
            boolean b = m7649b(c1547c);
            return b;
        } finally {
            reentrantLock.unlock();
        }
    }

    /* renamed from: c */
    public E m7660c() {
        E f;
        ReentrantLock reentrantLock = this.f4679c;
        reentrantLock.lock();
        while (true) {
            try {
                f = m7651f();
                if (f != null) {
                    break;
                }
                this.f4682f.await();
            } finally {
                reentrantLock.unlock();
            }
        }
        return f;
    }

    /* renamed from: c */
    public boolean m7661c(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        C1547c c1547c = new C1547c(e);
        ReentrantLock reentrantLock = this.f4679c;
        reentrantLock.lock();
        try {
            boolean c = m7650c(c1547c);
            return c;
        } finally {
            reentrantLock.unlock();
        }
    }

    public void clear() {
        ReentrantLock reentrantLock = this.f4679c;
        reentrantLock.lock();
        try {
            C1547c c1547c = this.f4677a;
            while (c1547c != null) {
                c1547c.f4689a = null;
                C1547c c1547c2 = c1547c.f4691c;
                c1547c.f4690b = null;
                c1547c.f4691c = null;
                c1547c = c1547c2;
            }
            this.f4678b = null;
            this.f4677a = null;
            this.f4680d = 0;
            this.f4683g.signalAll();
        } finally {
            reentrantLock.unlock();
        }
    }

    public boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        ReentrantLock reentrantLock = this.f4679c;
        reentrantLock.lock();
        try {
            for (C1547c c1547c = this.f4677a; c1547c != null; c1547c = c1547c.f4691c) {
                if (obj.equals(c1547c.f4689a)) {
                    return true;
                }
            }
            reentrantLock.unlock();
            return false;
        } finally {
            reentrantLock.unlock();
        }
    }

    /* renamed from: d */
    public E m7662d() {
        E e = m7664e();
        if (e != null) {
            return e;
        }
        throw new NoSuchElementException();
    }

    /* renamed from: d */
    public void m7663d(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        C1547c c1547c = new C1547c(e);
        ReentrantLock reentrantLock = this.f4679c;
        reentrantLock.lock();
        while (!m7650c(c1547c)) {
            try {
                this.f4683g.await();
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    public int drainTo(Collection<? super E> collection) {
        return drainTo(collection, Integer.MAX_VALUE);
    }

    public int drainTo(Collection<? super E> collection, int i) {
        if (collection == null) {
            throw new NullPointerException();
        } else if (collection == this) {
            throw new IllegalArgumentException();
        } else {
            ReentrantLock reentrantLock = this.f4679c;
            reentrantLock.lock();
            try {
                int min = Math.min(i, this.f4680d);
                for (int i2 = 0; i2 < min; i2++) {
                    collection.add(this.f4677a.f4689a);
                    m7651f();
                }
                return min;
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    /* renamed from: e */
    public E m7664e() {
        ReentrantLock reentrantLock = this.f4679c;
        reentrantLock.lock();
        try {
            E e = this.f4677a == null ? null : this.f4677a.f4689a;
            reentrantLock.unlock();
            return e;
        } catch (Throwable th) {
            reentrantLock.unlock();
        }
    }

    /* renamed from: e */
    public boolean m7665e(Object obj) {
        if (obj == null) {
            return false;
        }
        ReentrantLock reentrantLock = this.f4679c;
        reentrantLock.lock();
        try {
            for (C1547c c1547c = this.f4677a; c1547c != null; c1547c = c1547c.f4691c) {
                if (obj.equals(c1547c.f4689a)) {
                    m7655a(c1547c);
                    return true;
                }
            }
            reentrantLock.unlock();
            return false;
        } finally {
            reentrantLock.unlock();
        }
    }

    public E element() {
        return m7662d();
    }

    public Iterator<E> iterator() {
        return new C1546b();
    }

    public boolean offer(E e) {
        return m7661c((Object) e);
    }

    public boolean offer(E e, long j, TimeUnit timeUnit) {
        return m7657a(e, j, timeUnit);
    }

    public E peek() {
        return m7664e();
    }

    public E poll() {
        return m7658b();
    }

    public E poll(long j, TimeUnit timeUnit) {
        return m7654a(j, timeUnit);
    }

    public void put(E e) {
        m7663d(e);
    }

    public int remainingCapacity() {
        ReentrantLock reentrantLock = this.f4679c;
        reentrantLock.lock();
        try {
            int i = this.f4681e - this.f4680d;
            return i;
        } finally {
            reentrantLock.unlock();
        }
    }

    public E remove() {
        return m7653a();
    }

    public boolean remove(Object obj) {
        return m7665e(obj);
    }

    public int size() {
        ReentrantLock reentrantLock = this.f4679c;
        reentrantLock.lock();
        try {
            int i = this.f4680d;
            return i;
        } finally {
            reentrantLock.unlock();
        }
    }

    public E take() {
        return m7660c();
    }

    public Object[] toArray() {
        ReentrantLock reentrantLock = this.f4679c;
        reentrantLock.lock();
        try {
            Object[] objArr = new Object[this.f4680d];
            int i = 0;
            C1547c c1547c = this.f4677a;
            while (c1547c != null) {
                int i2 = i + 1;
                objArr[i] = c1547c.f4689a;
                c1547c = c1547c.f4691c;
                i = i2;
            }
            return objArr;
        } finally {
            reentrantLock.unlock();
        }
    }

    public <T> T[] toArray(T[] tArr) {
        ReentrantLock reentrantLock = this.f4679c;
        reentrantLock.lock();
        try {
            if (tArr.length < this.f4680d) {
                tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), this.f4680d);
            }
            int i = 0;
            C1547c c1547c = this.f4677a;
            while (c1547c != null) {
                int i2 = i + 1;
                tArr[i] = c1547c.f4689a;
                c1547c = c1547c.f4691c;
                i = i2;
            }
            if (tArr.length > i) {
                tArr[i] = null;
            }
            reentrantLock.unlock();
            return tArr;
        } catch (Throwable th) {
            reentrantLock.unlock();
        }
    }

    public String toString() {
        ReentrantLock reentrantLock = this.f4679c;
        reentrantLock.lock();
        try {
            String str;
            C1547c c1547c = this.f4677a;
            if (c1547c == null) {
                str = "[]";
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append('[');
                C1547c c1547c2 = c1547c;
                while (true) {
                    Object obj = c1547c2.f4689a;
                    if (obj == this) {
                        obj = "(this Collection)";
                    }
                    stringBuilder.append(obj);
                    c1547c = c1547c2.f4691c;
                    if (c1547c == null) {
                        break;
                    }
                    stringBuilder.append(',').append(' ');
                    c1547c2 = c1547c;
                }
                str = stringBuilder.append(']').toString();
                reentrantLock.unlock();
            }
            return str;
        } finally {
            reentrantLock.unlock();
        }
    }
}
