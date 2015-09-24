package com.lenovo.smstest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class MainActivity extends AppCompatActivity {
    Button mBtnBindPhone;
    String APPKEY="aa976339f409";
    String APPSECRET="a7ab0948232fd5924a7e7cd238289e63";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化smssdk
        SMSSDK.initSDK(this,APPKEY,APPSECRET);
        mBtnBindPhone= (Button) findViewById(R.id.btn_bind_phone);
        mBtnBindPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册手机号
                RegisterPage registerPage=new RegisterPage();
                //注册回调事件
                registerPage.setRegisterCallback(new EventHandler() {
                    @Override
                    public void afterEvent(int event, int result, Object data) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                            String countyr = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");
                            submitUserInfo(countyr, phone);
                        }
                    }
                });
                registerPage.show(MainActivity.this);
            }
        });
    }

    //提交用户信息
    public void submitUserInfo(String country,String phone){
        Random r=new Random();
        String uid=Math.abs(r.nextInt())+"";
        String nickName="lws";
        SMSSDK.submitUserInfo(uid,nickName,null,country,phone);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
