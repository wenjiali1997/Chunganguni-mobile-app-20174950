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
    private int hang = 4, lie = 4;// 行row对应y，列colunm对应x,默认开始都为4
    private kapianshitu[][] kapianditu = new kapianshitu[10][10];// 用一个二维数组来存
    private List<Point> kongfen = new ArrayList<Point>();// 链表方便增加删除
    // 在xml中能够访问则要添加构造方法
    // 以防万一三个构造方法都要写:对应参分别为上下文，属性，样式
    public youxihuamian(Context yujing) {
        super(yujing);
        //initGameView();
        gshitu = this;
    }

    public youxihuamian(Context yujing, AttributeSet shuxing) {
        super(yujing, shuxing);
//        initGameView();
        gshitu = this;
    }

    public youxihuamian(Context yujing, AttributeSet shuxing, int deffengge) {
        super(yujing, shuxing, deffengge);
//        initGameView();
        gshitu = this;
    }

    public static youxihuamian dedaoyouxijiazhi() {
        return gshitu;
    }

    // 由于手机可能不同，我们需要动态地获取卡片的宽高，所以要重写下面这个方法获取当前布局的宽高，
    // 为了让手机不会因倒过来改变宽高，要去mainifest里配置
    // 只会在手机里第一次运行的时候执行，之后不会改变
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
        //获取屏幕宽度
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();
        int kuandu=displayMetrics.widthPixels;
        int kapiankuandu=(kuandu-20)/4;
        kapianshitu c;
        //cw = cardWidth;
        for (int d = 0; d < 4; d++) {
            for (int i = 0; i < 4; i++) {
                c = new kapianshitu(getContext());
                // 先都初始画0号图片
                c.shezhishuzi(0);
                addView(c,kapiankuandu,kapiankuandu);
                // 把所有的卡片都记录下来
                kapianditu[i][d] = c;
            }
        }
    }

    // 添加随机数的时候要先遍历
    private void tianjiasuijishu() {
        kongfen.clear();
        for (int d = 0; d < 4; d++) {
            for (int i = 0; i < 4; i++) {
                if (kapianditu[i][d].dedaoshuzu() <= 0) {
                    kongfen.add(new Point(i, d));// 把空位给emptypoints链表
                }
            }
        }
        // 随机把emptyPoints中的一个赋值，生成2的概率为9,4为1
        Point k = kongfen.remove((int) (Math.random() * kongfen.size()));
        // 2号图片和4号图片
        kapianditu[k.x][k.y].shezhishuzi(Math.random() > 0.1 ? 2 : 4);
    }

    public void initGameView() {
        youxi = (youxidongzuo) this.getContext();
        setColumnCount(4);// 设置表格为4列
        tianjiakapian();// 把参数传过去
        kaishiyouxi();
        setBackgroundColor(0xffccccff);
        setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int dongzuo = event.getAction();// 获取触屏的动作
                switch (dongzuo) {
                    // 按下获取起始点
                    case MotionEvent.ACTION_DOWN:
                        qishiX = event.getX();
                        qishiY = event.getY();
                        break;
                    // 松开获取终止点，通过比较位移来判断滑动方向
                    // 要处理一下滑动偏的，看offx和offy哪个绝对值大就按照哪个来
                    case MotionEvent.ACTION_UP:
                        jieshuX = event.getX();
                        jieshuY = event.getY();
                        guanbiX = qishiX - jieshuX;
                        guanbiY = qishiY - jieshuY;
                        //判断 offx 与 offy 的绝对值大小
                        if (Math.abs(guanbiX) >= Math.abs(guanbiY)) {
                            if (guanbiX >= 5){
                                xiangzuoyidong();
                                //System.out.println("left");
                            }
                            else if (guanbiX < -5) {
                                xiangyouyidong();
                                //System.out.println("right");
                            }
                        } else {
                            if (guanbiY >= 5) {
                                xiangshangyidong();
                                //System.out.println("up");
                            }
                            else if (guanbiY < -5) {
                                xiangxiayidong();
                                //System.out.println("down");
                            }
                        }
                        break;
                }
                // !!!要改为true，否则ACTION_UP不会执行
                return true;
            }
        });
    }

    private void xiangzuoyidong() {
        boolean hebing = false;
        for (int d = 0; d < 4; d++) {
            for (int i = 0; i < 4; i++) {
                // 遍历当前位置的右边，如果有数字，如果当前位置没有数字，则合并到当前位置
                for (int d1 = i + 1; d1 < 4; d1++) {
                    // 每个右边的位置只判断执行一次
                    if (kapianditu[d1][d].dedaoshuzu() > 0) {

                        if (kapianditu[i][d].dedaoshuzu() <= 0) {
                            kapianditu[i][d].shezhishuzi(kapianditu[d1][d].dedaoshuzu());
                            kapianditu[d1][d].shezhishuzi(0);

                            i--;// 填补空位后，还要再次判断有没相同的可以合并的
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
                // 遍历当前位置的右边，如果有数字，如果当前位置没有数字，则合并到当前位置
                for (int d1 = i - 1; d1 >= 0; d1--) {
                    // 每个右边的位置只判断执行一次
                    if (kapianditu[d1][d].dedaoshuzu() > 0) {

                        if (kapianditu[i][d].dedaoshuzu() <= 0) {
                            kapianditu[i][d].shezhishuzi(kapianditu[d1][d].dedaoshuzu());
                            kapianditu[d1][d].shezhishuzi(0);
                            i++;// 填补空位后，还要再次判断有没相同的可以合并的
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
                // 遍历当前位置的右边，如果有数字，如果当前位置没有数字，则合并到当前位置
                for (int i1 = i + 1; i1 < hang; i1++) {
                    // 每个右边的位置只判断执行一次
                    if (kapianditu[d][i1].dedaoshuzu() > 0) {

                        if (kapianditu[d][i].dedaoshuzu() <= 0) {
                            kapianditu[d][i].shezhishuzi(kapianditu[d][i1].dedaoshuzu());
                            kapianditu[d][i1].shezhishuzi(0);
                            i--;// 填补空位后，还要再次判断有没相同的可以合并的
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
                // 遍历当前位置的右边，如果有数字，如果当前位置没有数字，则合并到当前位置
                for (int i1 = i - 1; i1 >= 0; i1--) {
                    // 每个右边的位置只判断执行一次
                    if (kapianditu[d][i1].dedaoshuzu() > 0) {

                        if (kapianditu[d][i].dedaoshuzu() <= 0) {
                            kapianditu[d][i].shezhishuzi(kapianditu[d][i1].dedaoshuzu());
                            kapianditu[d][i1].shezhishuzi(0);
                            i++;// 填补空位后，还要再次判断有没相同的可以合并的
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

     //判断结束
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
                // 如果还有空位，或者四个方向上还有相同的
                if (kapianditu[d][i].dedaoshuzu() == 0
                        || (d > 0 && kapianditu[d][i].dengyu(kapianditu[d - 1][i]))
                        || (d < 3 && kapianditu[d][i].dengyu(kapianditu[d + 1][i]))
                        || (i > 0 && kapianditu[d][i].dengyu(kapianditu[d][i - 1]))
                        || (i < 3 && kapianditu[d][i].dengyu(kapianditu[d][i + 1]))) {

                    wancheng = false;
                    break quanbu;// 如果出现这种情况，跳出双重循环，只写一个break只能跳出当前循环
                }
            }
        }
        if (wancheng) {
            new AlertDialog.Builder(getContext())
                    .setTitle("ㅠㅠㅠ")
                    .setMessage("게임 실폐")
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
