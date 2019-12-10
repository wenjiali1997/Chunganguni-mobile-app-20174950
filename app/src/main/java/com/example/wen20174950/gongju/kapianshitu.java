package com.example.wen20174950.gongju;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.wen20174950.R;

public class kapianshitu extends FrameLayout {

    private int num = 0;
    // 只显示数字的话可以用Textview,要是用图片的话要用imview
    private ImageView pic;
    private int[] picArray = new int[4100];
    private LayoutParams lp;

    public kapianshitu(Context context) {
        super(context);
        putPic();
        pic = new ImageView(getContext());
        lp = new LayoutParams(-1, -1);// -1,-1就是填充完父类容器的意思
        lp.setMargins(20, 20, 0, 0);// 用来设置边框很管用
        addView(pic, lp);// 把imageView加到CardView上
        setNum(0);
    }

    //把数字逻辑实现的2048转化为图片逻辑，只需要把数字定位数组序数，数字对应图片，并保持一一对应关系
    public void putPic() {
        picArray[0] = R.drawable.wu;
        picArray[2] = R.drawable.ha;
        picArray[4] = R.drawable.ka;
        picArray[8] = R.drawable.nn;
        picArray[16] = R.drawable.ya;
        picArray[32] = R.drawable.sa;
        picArray[64] = R.drawable.lasi;
        picArray[128] = R.drawable.en;
        picArray[256] = R.drawable.pia;
        picArray[512] = R.drawable.xuxu;
        picArray[1024] = R.drawable.zuihou;
        picArray[2048] = R.drawable.ba;
        picArray[4096]=R.drawable.fa;
    }

    // 数字:数字相当于图片id
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        //将显示的数字改为图片
        pic.setBackgroundResource(picArray[num]);
    }

    // 判断数字是否相同
    public boolean equals(kapianshitu cv) {
        return getNum() == cv.getNum();
    }
}
