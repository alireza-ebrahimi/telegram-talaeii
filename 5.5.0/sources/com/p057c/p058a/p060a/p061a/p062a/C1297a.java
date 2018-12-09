package com.p057c.p058a.p060a.p061a.p062a;

import com.p054b.p055a.C1288c;
import com.p054b.p055a.C1291f;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@C1302g(a = {5}, b = 64)
/* renamed from: com.c.a.a.a.a.a */
public class C1297a extends C1296b {
    /* renamed from: c */
    public static Map<Integer, Integer> f3878c = new HashMap();
    /* renamed from: d */
    public static Map<Integer, String> f3879d = new HashMap();
    /* renamed from: A */
    public boolean f3880A;
    /* renamed from: B */
    public boolean f3881B;
    /* renamed from: C */
    public boolean f3882C;
    /* renamed from: D */
    public int f3883D;
    /* renamed from: E */
    public boolean f3884E;
    /* renamed from: F */
    public int f3885F;
    /* renamed from: G */
    public int f3886G;
    /* renamed from: H */
    public int f3887H;
    /* renamed from: I */
    public int f3888I;
    /* renamed from: J */
    public int f3889J;
    /* renamed from: K */
    public int f3890K;
    /* renamed from: L */
    public int f3891L;
    /* renamed from: M */
    public int f3892M;
    /* renamed from: N */
    public int f3893N;
    /* renamed from: O */
    public int f3894O;
    /* renamed from: P */
    public int f3895P;
    /* renamed from: Q */
    public int f3896Q;
    /* renamed from: R */
    public int f3897R;
    /* renamed from: S */
    public int f3898S;
    /* renamed from: T */
    public boolean f3899T;
    /* renamed from: a */
    byte[] f3900a;
    /* renamed from: b */
    public C1294a f3901b;
    /* renamed from: e */
    public int f3902e;
    /* renamed from: f */
    public int f3903f;
    /* renamed from: g */
    public int f3904g;
    /* renamed from: h */
    public int f3905h;
    /* renamed from: i */
    public int f3906i;
    /* renamed from: j */
    public boolean f3907j;
    /* renamed from: k */
    public boolean f3908k;
    /* renamed from: l */
    public int f3909l;
    /* renamed from: m */
    public int f3910m;
    /* renamed from: n */
    public int f3911n;
    /* renamed from: o */
    public int f3912o;
    /* renamed from: p */
    public int f3913p;
    /* renamed from: q */
    public int f3914q;
    /* renamed from: r */
    public int f3915r;
    /* renamed from: s */
    public int f3916s;
    /* renamed from: t */
    public int f3917t;
    /* renamed from: u */
    public int f3918u;
    /* renamed from: v */
    public int f3919v;
    /* renamed from: w */
    public int f3920w;
    /* renamed from: x */
    public int f3921x;
    /* renamed from: y */
    public int f3922y;
    /* renamed from: z */
    public int f3923z;

    /* renamed from: com.c.a.a.a.a.a$a */
    public class C1294a {
        /* renamed from: a */
        public boolean f3851a;
        /* renamed from: b */
        public boolean f3852b;
        /* renamed from: c */
        public boolean f3853c;
        /* renamed from: d */
        public boolean f3854d;
        /* renamed from: e */
        public boolean f3855e;
        /* renamed from: f */
        public boolean f3856f;
        /* renamed from: g */
        public boolean f3857g;
        /* renamed from: h */
        final /* synthetic */ C1297a f3858h;

        public C1294a(C1297a c1297a, int i, C1298c c1298c) {
            this.f3858h = c1297a;
            this.f3851a = c1298c.m6714a();
            this.f3852b = c1298c.m6714a();
            this.f3853c = c1298c.m6714a();
            this.f3854d = c1298c.m6714a();
            this.f3855e = c1298c.m6714a();
            if (this.f3855e) {
                this.f3856f = c1298c.m6714a();
                this.f3857g = c1298c.m6714a();
                m6693a(i, c1298c);
            }
            while (c1298c.m6713a(4) != 0) {
                int a;
                int a2 = c1298c.m6713a(4);
                if (a2 == 15) {
                    a = c1298c.m6713a(8);
                    int i2 = a;
                    a = a2 + a;
                    a2 = i2;
                } else {
                    a = a2;
                    a2 = 0;
                }
                if (a2 == 255) {
                    a += c1298c.m6713a(16);
                }
                for (a2 = 0; a2 < a; a2++) {
                    c1298c.m6713a(8);
                }
            }
        }

        /* renamed from: a */
        public void m6693a(int i, C1298c c1298c) {
            int i2;
            int i3 = 0;
            switch (i) {
                case 1:
                case 2:
                    i2 = 1;
                    break;
                case 3:
                    i2 = 2;
                    break;
                case 4:
                case 5:
                case 6:
                    i2 = 3;
                    break;
                case 7:
                    i2 = 4;
                    break;
                default:
                    i2 = 0;
                    break;
            }
            while (i3 < i2) {
                C1295b c1295b = new C1295b(this.f3858h, c1298c);
                i3++;
            }
        }
    }

    /* renamed from: com.c.a.a.a.a.a$b */
    public class C1295b {
        /* renamed from: a */
        public boolean f3859a;
        /* renamed from: b */
        public int f3860b;
        /* renamed from: c */
        public int f3861c;
        /* renamed from: d */
        public int f3862d;
        /* renamed from: e */
        public int f3863e;
        /* renamed from: f */
        public boolean f3864f;
        /* renamed from: g */
        public boolean f3865g;
        /* renamed from: h */
        public int f3866h;
        /* renamed from: i */
        public boolean f3867i;
        /* renamed from: j */
        public int f3868j;
        /* renamed from: k */
        public int f3869k;
        /* renamed from: l */
        public int f3870l;
        /* renamed from: m */
        public boolean f3871m;
        /* renamed from: n */
        public boolean f3872n;
        /* renamed from: o */
        final /* synthetic */ C1297a f3873o;

        public C1295b(C1297a c1297a, C1298c c1298c) {
            this.f3873o = c1297a;
            this.f3859a = c1298c.m6714a();
            this.f3860b = c1298c.m6713a(4);
            this.f3861c = c1298c.m6713a(4);
            this.f3862d = c1298c.m6713a(3);
            this.f3863e = c1298c.m6713a(2);
            this.f3864f = c1298c.m6714a();
            this.f3865g = c1298c.m6714a();
            if (this.f3864f) {
                this.f3866h = c1298c.m6713a(2);
                this.f3867i = c1298c.m6714a();
                this.f3868j = c1298c.m6713a(2);
            }
            if (this.f3865g) {
                this.f3869k = c1298c.m6713a(2);
                this.f3870l = c1298c.m6713a(2);
                this.f3871m = c1298c.m6714a();
            }
            this.f3872n = c1298c.m6714a();
        }
    }

    static {
        f3878c.put(Integer.valueOf(0), Integer.valueOf(96000));
        f3878c.put(Integer.valueOf(1), Integer.valueOf(88200));
        f3878c.put(Integer.valueOf(2), Integer.valueOf(64000));
        f3878c.put(Integer.valueOf(3), Integer.valueOf(48000));
        f3878c.put(Integer.valueOf(4), Integer.valueOf(44100));
        f3878c.put(Integer.valueOf(5), Integer.valueOf(32000));
        f3878c.put(Integer.valueOf(6), Integer.valueOf(24000));
        f3878c.put(Integer.valueOf(7), Integer.valueOf(22050));
        f3878c.put(Integer.valueOf(8), Integer.valueOf(16000));
        f3878c.put(Integer.valueOf(9), Integer.valueOf(12000));
        f3878c.put(Integer.valueOf(10), Integer.valueOf(11025));
        f3878c.put(Integer.valueOf(11), Integer.valueOf(8000));
        f3879d.put(Integer.valueOf(1), "AAC main");
        f3879d.put(Integer.valueOf(2), "AAC LC");
        f3879d.put(Integer.valueOf(3), "AAC SSR");
        f3879d.put(Integer.valueOf(4), "AAC LTP");
        f3879d.put(Integer.valueOf(5), "SBR");
        f3879d.put(Integer.valueOf(6), "AAC Scalable");
        f3879d.put(Integer.valueOf(7), "TwinVQ");
        f3879d.put(Integer.valueOf(8), "CELP");
        f3879d.put(Integer.valueOf(9), "HVXC");
        f3879d.put(Integer.valueOf(10), "(reserved)");
        f3879d.put(Integer.valueOf(11), "(reserved)");
        f3879d.put(Integer.valueOf(12), "TTSI");
        f3879d.put(Integer.valueOf(13), "Main synthetic");
        f3879d.put(Integer.valueOf(14), "Wavetable synthesis");
        f3879d.put(Integer.valueOf(15), "General MIDI");
        f3879d.put(Integer.valueOf(16), "Algorithmic Synthesis and Audio FX");
        f3879d.put(Integer.valueOf(17), "ER AAC LC");
        f3879d.put(Integer.valueOf(18), "(reserved)");
        f3879d.put(Integer.valueOf(19), "ER AAC LTP");
        f3879d.put(Integer.valueOf(20), "ER AAC Scalable");
        f3879d.put(Integer.valueOf(21), "ER TwinVQ");
        f3879d.put(Integer.valueOf(22), "ER BSAC");
        f3879d.put(Integer.valueOf(23), "ER AAC LD");
        f3879d.put(Integer.valueOf(24), "ER CELP");
        f3879d.put(Integer.valueOf(25), "ER HVXC");
        f3879d.put(Integer.valueOf(26), "ER HILN");
        f3879d.put(Integer.valueOf(27), "ER Parametric");
        f3879d.put(Integer.valueOf(28), "SSC");
        f3879d.put(Integer.valueOf(29), "PS");
        f3879d.put(Integer.valueOf(30), "MPEG Surround");
        f3879d.put(Integer.valueOf(31), "(escape)");
        f3879d.put(Integer.valueOf(32), "Layer-1");
        f3879d.put(Integer.valueOf(33), "Layer-2");
        f3879d.put(Integer.valueOf(34), "Layer-3");
        f3879d.put(Integer.valueOf(35), "DST");
        f3879d.put(Integer.valueOf(36), "ALS");
        f3879d.put(Integer.valueOf(37), "SLS");
        f3879d.put(Integer.valueOf(38), "SLS non-core");
        f3879d.put(Integer.valueOf(39), "ER AAC ELD");
        f3879d.put(Integer.valueOf(40), "SMR Simple");
        f3879d.put(Integer.valueOf(41), "SMR Main");
    }

    /* renamed from: a */
    private int m6699a(C1298c c1298c) {
        int a = c1298c.m6713a(5);
        return a == 31 ? c1298c.m6713a(6) + 32 : a;
    }

    /* renamed from: a */
    private void m6700a(int i, int i2, int i3, C1298c c1298c) {
        this.f3917t = c1298c.m6713a(1);
        this.f3918u = c1298c.m6713a(1);
        if (this.f3918u == 1) {
            this.f3919v = c1298c.m6713a(14);
        }
        this.f3920w = c1298c.m6713a(1);
        if (i2 == 0) {
            throw new UnsupportedOperationException("can't parse program_config_element yet");
        }
        if (i3 == 6 || i3 == 20) {
            this.f3921x = c1298c.m6713a(3);
        }
        if (this.f3920w == 1) {
            if (i3 == 22) {
                this.f3922y = c1298c.m6713a(5);
                this.f3923z = c1298c.m6713a(11);
            }
            if (i3 == 17 || i3 == 19 || i3 == 20 || i3 == 23) {
                this.f3880A = c1298c.m6714a();
                this.f3881B = c1298c.m6714a();
                this.f3882C = c1298c.m6714a();
            }
            this.f3883D = c1298c.m6713a(1);
        }
        this.f3884E = true;
    }

    /* renamed from: b */
    private void m6701b(int i, int i2, int i3, C1298c c1298c) {
        this.f3885F = c1298c.m6713a(1);
        if (this.f3885F == 1) {
            m6702c(i, i2, i3, c1298c);
        } else {
            m6706f(i, i2, i3, c1298c);
        }
    }

    /* renamed from: c */
    private void m6702c(int i, int i2, int i3, C1298c c1298c) {
        this.f3886G = c1298c.m6713a(2);
        if (this.f3886G != 1) {
            m6703d(i, i2, i3, c1298c);
        }
        if (this.f3886G != 0) {
            m6704e(i, i2, i3, c1298c);
        }
        this.f3887H = c1298c.m6713a(1);
        this.f3899T = true;
    }

    /* renamed from: d */
    private void m6703d(int i, int i2, int i3, C1298c c1298c) {
        this.f3888I = c1298c.m6713a(1);
        this.f3889J = c1298c.m6713a(2);
        this.f3890K = c1298c.m6713a(1);
        if (this.f3890K == 1) {
            this.f3891L = c1298c.m6713a(1);
        }
    }

    /* renamed from: e */
    private void m6704e(int i, int i2, int i3, C1298c c1298c) {
        this.f3892M = c1298c.m6713a(1);
        this.f3893N = c1298c.m6713a(8);
        this.f3894O = c1298c.m6713a(4);
        this.f3895P = c1298c.m6713a(12);
        this.f3896Q = c1298c.m6713a(2);
    }

    /* renamed from: f */
    private int m6705f() {
        return 0;
    }

    /* renamed from: f */
    private void m6706f(int i, int i2, int i3, C1298c c1298c) {
        this.f3897R = c1298c.m6713a(1);
        if (this.f3897R == 1) {
            this.f3898S = c1298c.m6713a(2);
        }
    }

    /* renamed from: a */
    public int m6707a() {
        if (this.f3902e == 2) {
            return 4 + m6705f();
        }
        throw new UnsupportedOperationException("can't serialize that yet");
    }

    /* renamed from: a */
    public void m6708a(int i) {
        this.f3902e = i;
    }

    /* renamed from: a */
    public void mo1120a(ByteBuffer byteBuffer) {
        ByteBuffer slice = byteBuffer.slice();
        slice.limit(this.V);
        byteBuffer.position(byteBuffer.position() + this.V);
        this.f3900a = new byte[this.V];
        slice.get(this.f3900a);
        slice.rewind();
        C1298c c1298c = new C1298c(slice);
        this.f3902e = m6699a(c1298c);
        this.f3903f = c1298c.m6713a(4);
        if (this.f3903f == 15) {
            this.f3904g = c1298c.m6713a(24);
        }
        this.f3905h = c1298c.m6713a(4);
        if (this.f3902e == 5 || this.f3902e == 29) {
            this.f3906i = 5;
            this.f3907j = true;
            if (this.f3902e == 29) {
                this.f3908k = true;
            }
            this.f3909l = c1298c.m6713a(4);
            if (this.f3909l == 15) {
                this.f3910m = c1298c.m6713a(24);
            }
            this.f3902e = m6699a(c1298c);
            if (this.f3902e == 22) {
                this.f3911n = c1298c.m6713a(4);
            }
        } else {
            this.f3906i = 0;
        }
        switch (this.f3902e) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 6:
            case 7:
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                m6700a(this.f3903f, this.f3905h, this.f3902e, c1298c);
                break;
            case 8:
                throw new UnsupportedOperationException("can't parse CelpSpecificConfig yet");
            case 9:
                throw new UnsupportedOperationException("can't parse HvxcSpecificConfig yet");
            case 12:
                throw new UnsupportedOperationException("can't parse TTSSpecificConfig yet");
            case 13:
            case 14:
            case 15:
            case 16:
                throw new UnsupportedOperationException("can't parse StructuredAudioSpecificConfig yet");
            case 24:
                throw new UnsupportedOperationException("can't parse ErrorResilientCelpSpecificConfig yet");
            case 25:
                throw new UnsupportedOperationException("can't parse ErrorResilientHvxcSpecificConfig yet");
            case 26:
            case 27:
                m6701b(this.f3903f, this.f3905h, this.f3902e, c1298c);
                break;
            case 28:
                throw new UnsupportedOperationException("can't parse SSCSpecificConfig yet");
            case 30:
                this.f3912o = c1298c.m6713a(1);
                throw new UnsupportedOperationException("can't parse SpatialSpecificConfig yet");
            case 32:
            case 33:
            case 34:
                throw new UnsupportedOperationException("can't parse MPEG_1_2_SpecificConfig yet");
            case 35:
                throw new UnsupportedOperationException("can't parse DSTSpecificConfig yet");
            case 36:
                this.f3913p = c1298c.m6713a(5);
                throw new UnsupportedOperationException("can't parse ALSSpecificConfig yet");
            case 37:
            case 38:
                throw new UnsupportedOperationException("can't parse SLSSpecificConfig yet");
            case 39:
                this.f3901b = new C1294a(this, this.f3905h, c1298c);
                break;
            case 40:
            case 41:
                throw new UnsupportedOperationException("can't parse SymbolicMusicSpecificConfig yet");
        }
        switch (this.f3902e) {
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 39:
                this.f3914q = c1298c.m6713a(2);
                if (this.f3914q == 2 || this.f3914q == 3) {
                    throw new UnsupportedOperationException("can't parse ErrorProtectionSpecificConfig yet");
                } else if (this.f3914q == 3) {
                    this.f3915r = c1298c.m6713a(1);
                    if (this.f3915r == 0) {
                        throw new RuntimeException("not implemented");
                    }
                }
                break;
        }
        if (this.f3906i != 5 && c1298c.m6715b() >= 16) {
            this.f3916s = c1298c.m6713a(11);
            if (this.f3916s == 695) {
                this.f3906i = m6699a(c1298c);
                if (this.f3906i == 5) {
                    this.f3907j = c1298c.m6714a();
                    if (this.f3907j) {
                        this.f3909l = c1298c.m6713a(4);
                        if (this.f3909l == 15) {
                            this.f3910m = c1298c.m6713a(24);
                        }
                        if (c1298c.m6715b() >= 12) {
                            this.f3916s = c1298c.m6713a(11);
                            if (this.f3916s == 1352) {
                                this.f3908k = c1298c.m6714a();
                            }
                        }
                    }
                }
                if (this.f3906i == 22) {
                    this.f3907j = c1298c.m6714a();
                    if (this.f3907j) {
                        this.f3909l = c1298c.m6713a(4);
                        if (this.f3909l == 15) {
                            this.f3910m = c1298c.m6713a(24);
                        }
                    }
                    this.f3911n = c1298c.m6713a(4);
                }
            }
        }
    }

    /* renamed from: b */
    public ByteBuffer m6710b() {
        ByteBuffer allocate = ByteBuffer.allocate(m6707a());
        C1291f.m6687c(allocate, 5);
        C1291f.m6687c(allocate, m6707a() - 2);
        C1299d c1299d = new C1299d(allocate);
        c1299d.m6716a(this.f3902e, 5);
        c1299d.m6716a(this.f3903f, 4);
        if (this.f3903f == 15) {
            throw new UnsupportedOperationException("can't serialize that yet");
        }
        c1299d.m6716a(this.f3905h, 4);
        return allocate;
    }

    /* renamed from: b */
    public void m6711b(int i) {
        this.f3903f = i;
    }

    /* renamed from: c */
    public void m6712c(int i) {
        this.f3905h = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        C1297a c1297a = (C1297a) obj;
        return this.f3881B != c1297a.f3881B ? false : this.f3880A != c1297a.f3880A ? false : this.f3882C != c1297a.f3882C ? false : this.f3902e != c1297a.f3902e ? false : this.f3905h != c1297a.f3905h ? false : this.f3919v != c1297a.f3919v ? false : this.f3918u != c1297a.f3918u ? false : this.f3915r != c1297a.f3915r ? false : this.f3914q != c1297a.f3914q ? false : this.f3890K != c1297a.f3890K ? false : this.f3906i != c1297a.f3906i ? false : this.f3911n != c1297a.f3911n ? false : this.f3920w != c1297a.f3920w ? false : this.f3883D != c1297a.f3883D ? false : this.f3910m != c1297a.f3910m ? false : this.f3909l != c1297a.f3909l ? false : this.f3913p != c1297a.f3913p ? false : this.f3917t != c1297a.f3917t ? false : this.f3884E != c1297a.f3884E ? false : this.f3896Q != c1297a.f3896Q ? false : this.f3897R != c1297a.f3897R ? false : this.f3898S != c1297a.f3898S ? false : this.f3895P != c1297a.f3895P ? false : this.f3893N != c1297a.f3893N ? false : this.f3892M != c1297a.f3892M ? false : this.f3894O != c1297a.f3894O ? false : this.f3889J != c1297a.f3889J ? false : this.f3888I != c1297a.f3888I ? false : this.f3885F != c1297a.f3885F ? false : this.f3921x != c1297a.f3921x ? false : this.f3923z != c1297a.f3923z ? false : this.f3922y != c1297a.f3922y ? false : this.f3887H != c1297a.f3887H ? false : this.f3886G != c1297a.f3886G ? false : this.f3899T != c1297a.f3899T ? false : this.f3908k != c1297a.f3908k ? false : this.f3912o != c1297a.f3912o ? false : this.f3904g != c1297a.f3904g ? false : this.f3903f != c1297a.f3903f ? false : this.f3907j != c1297a.f3907j ? false : this.f3916s != c1297a.f3916s ? false : this.f3891L != c1297a.f3891L ? false : Arrays.equals(this.f3900a, c1297a.f3900a);
    }

    public int hashCode() {
        int i = 1;
        int hashCode = ((((((((((((((((((((((((((((((this.f3884E ? 1 : 0) + (((((this.f3882C ? 1 : 0) + (((this.f3881B ? 1 : 0) + (((this.f3880A ? 1 : 0) + (((((((((((((((((((((((((((((((((this.f3908k ? 1 : 0) + (((this.f3907j ? 1 : 0) + ((((((((((((this.f3900a != null ? Arrays.hashCode(this.f3900a) : 0) * 31) + this.f3902e) * 31) + this.f3903f) * 31) + this.f3904g) * 31) + this.f3905h) * 31) + this.f3906i) * 31)) * 31)) * 31) + this.f3909l) * 31) + this.f3910m) * 31) + this.f3911n) * 31) + this.f3912o) * 31) + this.f3913p) * 31) + this.f3914q) * 31) + this.f3915r) * 31) + this.f3916s) * 31) + this.f3917t) * 31) + this.f3918u) * 31) + this.f3919v) * 31) + this.f3920w) * 31) + this.f3921x) * 31) + this.f3922y) * 31) + this.f3923z) * 31)) * 31)) * 31)) * 31) + this.f3883D) * 31)) * 31) + this.f3885F) * 31) + this.f3886G) * 31) + this.f3887H) * 31) + this.f3888I) * 31) + this.f3889J) * 31) + this.f3890K) * 31) + this.f3891L) * 31) + this.f3892M) * 31) + this.f3893N) * 31) + this.f3894O) * 31) + this.f3895P) * 31) + this.f3896Q) * 31) + this.f3897R) * 31) + this.f3898S) * 31;
        if (!this.f3899T) {
            i = 0;
        }
        return hashCode + i;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AudioSpecificConfig");
        stringBuilder.append("{configBytes=").append(C1288c.m6663a(this.f3900a));
        stringBuilder.append(", audioObjectType=").append(this.f3902e).append(" (").append((String) f3879d.get(Integer.valueOf(this.f3902e))).append(")");
        stringBuilder.append(", samplingFrequencyIndex=").append(this.f3903f).append(" (").append(f3878c.get(Integer.valueOf(this.f3903f))).append(")");
        stringBuilder.append(", samplingFrequency=").append(this.f3904g);
        stringBuilder.append(", channelConfiguration=").append(this.f3905h);
        if (this.f3906i > 0) {
            stringBuilder.append(", extensionAudioObjectType=").append(this.f3906i).append(" (").append((String) f3879d.get(Integer.valueOf(this.f3906i))).append(")");
            stringBuilder.append(", sbrPresentFlag=").append(this.f3907j);
            stringBuilder.append(", psPresentFlag=").append(this.f3908k);
            stringBuilder.append(", extensionSamplingFrequencyIndex=").append(this.f3909l).append(" (").append(f3878c.get(Integer.valueOf(this.f3909l))).append(")");
            stringBuilder.append(", extensionSamplingFrequency=").append(this.f3910m);
            stringBuilder.append(", extensionChannelConfiguration=").append(this.f3911n);
        }
        stringBuilder.append(", syncExtensionType=").append(this.f3916s);
        if (this.f3884E) {
            stringBuilder.append(", frameLengthFlag=").append(this.f3917t);
            stringBuilder.append(", dependsOnCoreCoder=").append(this.f3918u);
            stringBuilder.append(", coreCoderDelay=").append(this.f3919v);
            stringBuilder.append(", extensionFlag=").append(this.f3920w);
            stringBuilder.append(", layerNr=").append(this.f3921x);
            stringBuilder.append(", numOfSubFrame=").append(this.f3922y);
            stringBuilder.append(", layer_length=").append(this.f3923z);
            stringBuilder.append(", aacSectionDataResilienceFlag=").append(this.f3880A);
            stringBuilder.append(", aacScalefactorDataResilienceFlag=").append(this.f3881B);
            stringBuilder.append(", aacSpectralDataResilienceFlag=").append(this.f3882C);
            stringBuilder.append(", extensionFlag3=").append(this.f3883D);
        }
        if (this.f3899T) {
            stringBuilder.append(", isBaseLayer=").append(this.f3885F);
            stringBuilder.append(", paraMode=").append(this.f3886G);
            stringBuilder.append(", paraExtensionFlag=").append(this.f3887H);
            stringBuilder.append(", hvxcVarMode=").append(this.f3888I);
            stringBuilder.append(", hvxcRateMode=").append(this.f3889J);
            stringBuilder.append(", erHvxcExtensionFlag=").append(this.f3890K);
            stringBuilder.append(", var_ScalableFlag=").append(this.f3891L);
            stringBuilder.append(", hilnQuantMode=").append(this.f3892M);
            stringBuilder.append(", hilnMaxNumLine=").append(this.f3893N);
            stringBuilder.append(", hilnSampleRateCode=").append(this.f3894O);
            stringBuilder.append(", hilnFrameLength=").append(this.f3895P);
            stringBuilder.append(", hilnContMode=").append(this.f3896Q);
            stringBuilder.append(", hilnEnhaLayer=").append(this.f3897R);
            stringBuilder.append(", hilnEnhaQuantMode=").append(this.f3898S);
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
