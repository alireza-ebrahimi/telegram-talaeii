package android.support.v7.widget;

class ba {
    /* renamed from: a */
    private int f2977a = 0;
    /* renamed from: b */
    private int f2978b = 0;
    /* renamed from: c */
    private int f2979c = Integer.MIN_VALUE;
    /* renamed from: d */
    private int f2980d = Integer.MIN_VALUE;
    /* renamed from: e */
    private int f2981e = 0;
    /* renamed from: f */
    private int f2982f = 0;
    /* renamed from: g */
    private boolean f2983g = false;
    /* renamed from: h */
    private boolean f2984h = false;

    ba() {
    }

    /* renamed from: a */
    public int m5600a() {
        return this.f2977a;
    }

    /* renamed from: a */
    public void m5601a(int i, int i2) {
        this.f2979c = i;
        this.f2980d = i2;
        this.f2984h = true;
        if (this.f2983g) {
            if (i2 != Integer.MIN_VALUE) {
                this.f2977a = i2;
            }
            if (i != Integer.MIN_VALUE) {
                this.f2978b = i;
                return;
            }
            return;
        }
        if (i != Integer.MIN_VALUE) {
            this.f2977a = i;
        }
        if (i2 != Integer.MIN_VALUE) {
            this.f2978b = i2;
        }
    }

    /* renamed from: a */
    public void m5602a(boolean z) {
        if (z != this.f2983g) {
            this.f2983g = z;
            if (!this.f2984h) {
                this.f2977a = this.f2981e;
                this.f2978b = this.f2982f;
            } else if (z) {
                this.f2977a = this.f2980d != Integer.MIN_VALUE ? this.f2980d : this.f2981e;
                this.f2978b = this.f2979c != Integer.MIN_VALUE ? this.f2979c : this.f2982f;
            } else {
                this.f2977a = this.f2979c != Integer.MIN_VALUE ? this.f2979c : this.f2981e;
                this.f2978b = this.f2980d != Integer.MIN_VALUE ? this.f2980d : this.f2982f;
            }
        }
    }

    /* renamed from: b */
    public int m5603b() {
        return this.f2978b;
    }

    /* renamed from: b */
    public void m5604b(int i, int i2) {
        this.f2984h = false;
        if (i != Integer.MIN_VALUE) {
            this.f2981e = i;
            this.f2977a = i;
        }
        if (i2 != Integer.MIN_VALUE) {
            this.f2982f = i2;
            this.f2978b = i2;
        }
    }

    /* renamed from: c */
    public int m5605c() {
        return this.f2983g ? this.f2978b : this.f2977a;
    }

    /* renamed from: d */
    public int m5606d() {
        return this.f2983g ? this.f2977a : this.f2978b;
    }
}
