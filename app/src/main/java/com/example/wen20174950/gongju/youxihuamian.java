package com.example.wen20174950.gongju;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import com.example.wen20174950.youxidongzuo;

import java.util.ArrayList;
import java.util.List;

public class youxihuamian extends GridLayout {

    private youxidongzuo youxi;
    private static youxihuamian gshitu;
    private float qishiX, qishiY, jieshuX, jieshuY, guanbiX, guanbiY;
    private int hang = 4, lie = 4;
    private kapianshitu[][] kapianditu = new kapianshitu[10][10];
    private List<Point> kongfen = new ArrayList<Point>();

    public youxihuamian(Context yujing) {
        super(yujing);
        gshitu = this;
    }

    public youxihuamian(Context yujing, AttributeSet shuxing) {
        super(yujing, shuxing);
        gshitu = this;
    }

    public youxihuamian(Context yujing, AttributeSet shuxing, int deffengge) {
        super(yujing, shuxing, deffengge);
        gshitu = this;
    }

    public static youxihuamian dedaoyouxijiazhi() {
        return gshitu;
    }

    @Override
    protected void onSizeChanged(int d, int i, int laod, int laoi) {
        super.onSizeChanged(d, i, laod, laoi);
    }

    public void kaishiyouxi() {
        for (int d = 0; d < 4; d++) {
            for (int i = 0; i < 4; i++) {
                kapianditu[i][d].shezhishuzi(0);
            }
        }

        tianjiasuijishu();
        tianjiasuijishu();
        youxi.chushifenshu();
    }

    private void tianjiakapian() {
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();
        int kuandu=displayMetrics.widthPixels;
        int kapiankuandu=(kuandu-20)/4;
        kapianshitu c;

        for (int d = 0; d < 4; d++) {
            for (int i = 0; i < 4; i++) {
                c = new kapianshitu(getContext());
                c.shezhishuzi(0);
                addView(c,kapiankuandu,kapiankuandu);
                kapianditu[i][d] = c;
            }
        }
    }

    private void tianjiasuijishu() {
        kongfen.clear();
        for (int d = 0; d < 4; d++) {
            for (int i = 0; i < 4; i++) {
                if (kapianditu[i][d].dedaoshuzu() <= 0) {
                    kongfen.add(new Point(i, d));
                }
            }
        }

        Point c = kongfen.remove((int) (Math.random() * kongfen.size()));
        kapianditu[c.x][c.y].shezhishuzi(Math.random() > 0.1 ? 2 : 4);
    }

    public void chushihuayouxishitu() {
        youxi = (youxidongzuo) this.getContext();
        setColumnCount(4);
        tianjiakapian();
        kaishiyouxi();
        setBackgroundColor(0xffccccff);
        setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                int dongzuo = event.getAction();
                switch (dongzuo) {

                    case MotionEvent.ACTION_DOWN:
                        qishiX = event.getX();
                        qishiY = event.getY();
                        break;

                    case MotionEvent.ACTION_UP:
                        jieshuX = event.getX();
                        jieshuY = event.getY();
                        guanbiX = qishiX - jieshuX;
                        guanbiY = qishiY - jieshuY;

                        if (Math.abs(guanbiX) >= Math.abs(guanbiY)) {
                            if (guanbiX >= 5){
                                xiangzuoyidong();
                            }

                            else if (guanbiX < -5) {
                                xiangyouyidong();
                            }

                        } else {
                            if (guanbiY >= 5) {
                                xiangshangyidong();
                            }

                            else if (guanbiY < -5) {
                                xiangxiayidong();

                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void xiangzuoyidong() {
        boolean hebing = false;
        for (int d = 0; d < 4; d++) {

            for (int i = 0; i < 4; i++) {

                for (int d1 = i + 1; d1 < 4; d1++) {

                    if (kapianditu[d1][d].dedaoshuzu() > 0) {

                        if (kapianditu[i][d].dedaoshuzu() <= 0) {
                            kapianditu[i][d].shezhishuzi(kapianditu[d1][d].dedaoshuzu());
                            kapianditu[d1][d].shezhishuzi(0);
                            i--;
                            hebing = true;
                            break;

                        } else if (kapianditu[i][d].dengyu(kapianditu[d1][d])) {
                            kapianditu[i][d].shezhishuzi(kapianditu[i][d].dedaoshuzu() * 2);
                            kapianditu[d1][d].shezhishuzi(0);
                           youxi.tianjiafenshu(kapianditu[i][d].dedaoshuzu());
                            break;
                        }
                        break;
                    }
                }
            }
        }

        if (hebing) {
            tianjiasuijishu();
            jianchawancheng();
        }
    }

    private void xiangyouyidong() {
        boolean hebing = false;
        for (int d = 0; d < 4; d++) {
            for (int i = 4 - 1; i >= 0; i--) {

                for (int d1 = i - 1; d1 >= 0; d1--) {

                    if (kapianditu[d1][d].dedaoshuzu() > 0) {

                        if (kapianditu[i][d].dedaoshuzu() <= 0) {
                            kapianditu[i][d].shezhishuzi(kapianditu[d1][d].dedaoshuzu());
                            kapianditu[d1][d].shezhishuzi(0);
                            i++;
                            hebing = true;
                            break;

                        } else if (kapianditu[i][d].dengyu(kapianditu[d1][d])) {
                            kapianditu[i][d].shezhishuzi(kapianditu[i][d].dedaoshuzu() * 2);
                            kapianditu[d1][d].shezhishuzi(0);
                            youxi.tianjiafenshu(kapianditu[i][d].dedaoshuzu());
                            hebing = true;
                            break;
                        }
                        break;
                    }
                }
            }
        }
        if (hebing) {
            tianjiasuijishu();
            jianchawancheng();
        }

    }

    private void xiangshangyidong() {
        boolean hebing = false;
        for (int d = 0; d < lie; d++) {
            for (int i = 0; i < hang; i++) {

                for (int i1 = i + 1; i1 < hang; i1++) {

                    if (kapianditu[d][i1].dedaoshuzu() > 0) {

                        if (kapianditu[d][i].dedaoshuzu() <= 0) {
                            kapianditu[d][i].shezhishuzi(kapianditu[d][i1].dedaoshuzu());
                            kapianditu[d][i1].shezhishuzi(0);
                            i--;
                            hebing = true;
                            break;

                        } else if (kapianditu[d][i].dengyu(kapianditu[d][i1])) {
                            kapianditu[d][i].shezhishuzi(kapianditu[d][i].dedaoshuzu() * 2);
                            kapianditu[d][i1].shezhishuzi(0);
                            youxi.tianjiafenshu(kapianditu[d][i].dedaoshuzu());
                            hebing = true;
                            break;
                        }
                        break;
                    }
                }
            }
        }

        if (hebing) {
            tianjiasuijishu();
            jianchawancheng();
        }

    }

    private void xiangxiayidong() {
        boolean hebing = false;
        for (int d = 0; d < lie; d++) {
            for (int i = hang - 1; i >= 0; i--) {

                for (int i1 = i - 1; i1 >= 0; i1--) {

                    if (kapianditu[d][i1].dedaoshuzu() > 0) {

                        if (kapianditu[d][i].dedaoshuzu() <= 0) {
                            kapianditu[d][i].shezhishuzi(kapianditu[d][i1].dedaoshuzu());
                            kapianditu[d][i1].shezhishuzi(0);
                            i++;
                            hebing = true;
                            break;

                        } else if (kapianditu[d][i].dengyu(kapianditu[d][i1])) {
                            kapianditu[d][i].shezhishuzi(kapianditu[d][i].dedaoshuzu() * 2);
                            kapianditu[d][i1].shezhishuzi(0);
                            youxi.tianjiafenshu(kapianditu[d][i].dedaoshuzu());
                            hebing = true;
                            break;
                        }
                        break;
                    }
                }
            }

        }if (hebing) {
            tianjiasuijishu();
            jianchawancheng();
        }

    }

    private void jianchawancheng() {
        int yaoshi= youxi.degao1();
        boolean wancheng = true;
        for (int i = 0; i < 4; i++) {
            for (int d = 0; d < 4; d++) {


                if(kapianditu[d][i].dedaoshuzu() == 4096 && yaoshi!=0){
                    new AlertDialog.Builder(getContext())
                            .setTitle("와하하하하!")
                            .setMessage("승리")
                            .setPositiveButton("다시 시작", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    kaishiyouxi();
                                }
                            }).show();
                    break;
                }
            }
            break;
        }

        quanbu: for (int i = 0; i < 4; i++) {
            for (int d = 0; d < 4; d++) {

                if (kapianditu[d][i].dedaoshuzu() == 0
                        || (d > 0 && kapianditu[d][i].dengyu(kapianditu[d - 1][i]))
                        || (d < 3 && kapianditu[d][i].dengyu(kapianditu[d + 1][i]))
                        || (i > 0 && kapianditu[d][i].dengyu(kapianditu[d][i - 1]))
                        || (i < 3 && kapianditu[d][i].dengyu(kapianditu[d][i + 1]))) {
                    wancheng = false;
                    break quanbu;
                }
            }
        }

        if (wancheng) {
            new AlertDialog.Builder(getContext())
                    .setTitle("ㅠㅠㅠ")
                    .setMessage("게임 실패")
                    .setPositiveButton("다시 시작",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    kaishiyouxi();
                                }
                            }).show();
        }
    }

}
