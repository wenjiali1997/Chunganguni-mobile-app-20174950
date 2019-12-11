package com.example.wen20174950;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class huanyingjiemian extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle baocunshilizhuangtai) {
        super.onCreate(baocunshilizhuangtai);
        //去掉顶部标题
        getSupportActionBar().hide();
        //去掉最上面时间、电量等
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.huanyingjiemian);


        //界面跳转
        tiaoguo();
    }

    //5秒后跳转到主界面
    protected void tiaoguo(){
        final Intent yitu=new Intent(huanyingjiemian.this, youxidongzuo.class);
        Timer jishiqi=new Timer();
        TimerTask jishiqirenwu=new TimerTask() {
            @Override
            public void run() {
                startActivity(yitu);
                finish();
            }
        };
        jishiqi.schedule(jishiqirenwu,1000*4);
    }

}
