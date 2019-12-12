package com.example.wen20174950.gongju;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.wen20174950.R;

public class kapianshitu extends FrameLayout {

    private int shuzi = 0;
    private ImageView tupian;
    private int[] tupianshuzu = new int[4100];
    private LayoutParams lp;

    public kapianshitu(Context yujing) {
        super(yujing);
        fangtupian();
        tupian = new ImageView(getContext());
        lp = new LayoutParams(-1, -1);
        lp.setMargins(20, 20, 0, 0);
        addView(tupian, lp);
        shezhishuzi(0);
    }

    public void fangtupian() {
        tupianshuzu[0] = R.drawable.wu;
        tupianshuzu[2] = R.drawable.ha;
        tupianshuzu[4] = R.drawable.ka;
        tupianshuzu[8] = R.drawable.nn;
        tupianshuzu[16] = R.drawable.ya;
        tupianshuzu[32] = R.drawable.sa;
        tupianshuzu[64] = R.drawable.lasi;
        tupianshuzu[128] = R.drawable.en;
        tupianshuzu[256] = R.drawable.pia;
        tupianshuzu[512] = R.drawable.xuxu;
        tupianshuzu[1024] = R.drawable.zuihou;
        tupianshuzu[2048] = R.drawable.ba;
        tupianshuzu[4096]=R.drawable.fa;
    }

    public int dedaoshuzu() {
        return shuzi;
    }

    public void shezhishuzi(int shuzi) {
        this.shuzi = shuzi;
        tupian.setBackgroundResource(tupianshuzu[shuzi]);
    }

    public boolean dengyu(kapianshitu jianli) {
        return dedaoshuzu() == jianli.dedaoshuzu();
    }
}
