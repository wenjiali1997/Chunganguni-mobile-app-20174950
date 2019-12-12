package com.example.wen20174950;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wen20174950.gongju.youxihuamian;
import com.example.wen20174950.gongju.MyDBHelper;

public class youxidongzuo extends AppCompatActivity {
    private LinearLayout xianxingbuju;
    private int defen = 0;
    private TextView tvdefen;
    private TextView tvzuigaodefen;
    private MyDBHelper wodeshujukuzhushou;
    private SQLiteDatabase shujukumingzi;
    private Cursor guangbiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.youxihuamian);

        xianxingbuju = (LinearLayout)findViewById(R.id.mylinearlaout);
        tvdefen = (TextView) findViewById(R.id.tvdefen);
        tvzuigaodefen = (TextView) findViewById(R.id.tvzuigaofen);

        wodeshujukuzhushou = new MyDBHelper(this);
        wodeshujukuzhushou.getReadableDatabase();
        shujukumingzi = wodeshujukuzhushou.getReadableDatabase();
        guangbiao = shujukumingzi.rawQuery("select * from s",null);

        if(guangbiao ==null || guangbiao.getCount()==0){
            shujukumingzi = wodeshujukuzhushou.getWritableDatabase();
            shujukumingzi.execSQL("insert into s values(1,0)");
            shujukumingzi.execSQL("insert into s values(2,0)");
            shujukumingzi.close();
        }

        youxihuamian.dedaoyouxijiazhi().chushihuayouxishitu();
        ImageButton huifu = (ImageButton) findViewById(R.id.imageButton3);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);

        int kuandu = wm.getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams lp;
        lp= xianxingbuju.getLayoutParams();
        lp.width=kuandu;
        lp.height=kuandu;
        xianxingbuju.setLayoutParams(lp);

        huifu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(dedaoyouxihuodong())
                        .setTitle("게임 포기함??")
                        .setMessage("게임 다시 시작할깡？")
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                youxihuamian.dedaoyouxijiazhi().kaishiyouxi();
                                qingchufenshu();
                            }
                        })
                        .create().show();
            }
        });
    }

    private youxidongzuo dedaoyouxihuodong(){
        return this;
    }

    public void xianshidefen() {
        tvdefen.setText(defen + "");
    }

    public void xianshizuigaofen(int s) {
        tvzuigaodefen.setText(s + "");
    }

    public void qingchufenshu() {
        defen = 0;
        xianshidefen();
    }

    public void tianjiafenshu(int s) {
        defen += s;
        xianshidefen();
        int zuidadefen = Math.max(defen, huodezuigaofen());
        baocunzuigaofen(zuidadefen);
        xianshizuigaofen(zuidadefen);
    }

    public int huodezuigaofen(){
        int zuigaofen=100;
        shujukumingzi = wodeshujukuzhushou.getReadableDatabase();
        guangbiao = shujukumingzi.rawQuery("select * from s where Id=2",null);
        while (guangbiao.moveToNext()){
            zuigaofen= guangbiao.getInt(guangbiao.getColumnIndex("Score"));
        }
        shujukumingzi.close();
        return zuigaofen;
    }

    public int degao1(){
        int shuzi=100;
        shujukumingzi = wodeshujukuzhushou.getReadableDatabase();
        guangbiao = shujukumingzi.rawQuery("select * from s where Id=1",null);
        while (guangbiao.moveToNext()){
            shuzi= guangbiao.getInt(guangbiao.getColumnIndex("Score"));
        }
        shujukumingzi.close();
        return shuzi;
    }

    public void shezhi1(){
        shujukumingzi = wodeshujukuzhushou.getReadableDatabase();
        shujukumingzi.execSQL("update s set Score = 10 where Id = 1");
        shujukumingzi.close();
    }

    public void baocunzuigaofen(int s){
        shujukumingzi = wodeshujukuzhushou.getReadableDatabase();
        shujukumingzi.execSQL("update s set Score = "+s+" where Id = 2");
        shujukumingzi.close();
    }

    public void chushifenshu(){
        defen = 0;
        xianshidefen();
        int zuigaofen= huodezuigaofen();
        xianshizuigaofen(zuigaofen);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
