package com.example.wen20174950.gongju;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.wen20174950.R;

public class kapianshitu extends FrameLayout {

    private int shuzi = 0;
    // 只显示数字的话可以用Textview,要是用图片的话要用imview
    private ImageView tupian;
    private int[] tupianshuzu = new int[4100];
    private LayoutParams lp;

    public kapianshitu(Context yujing) {
        super(yujing);
        fangtupian();
        tupian = new ImageView(getContext());
        lp = new LayoutParams(-1, -1);// -1,-1就是填充完父类容器的意思
        lp.setMargins(20, 20, 0, 0);// 用来设置边框很管用
        addView(tupian, lp);// 把imageView加到CardView上
        shezhishuzi(0);
    }

    //把数字逻辑实现的2048转化为图片逻辑，只需要把数字定位数组序数，数字对应图片，并保持一一对应关系
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

    // 数字:数字相当于图片id
    public int dedaoshuzu() {
        return shuzi;
    }

    public void shezhishuzi(int shuzi) {
        this.shuzi = shuzi;
        //将显示的数字改为图片
        tupian.setBackgroundResource(tupianshuzu[shuzi]);
    }

    // 判断数字是否相同
    public boolean dengyu(kapianshitu jianli) {
        return dedaoshuzu() == jianli.dedaoshuzu();
    }
}
