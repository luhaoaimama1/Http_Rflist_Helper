package com.zone.http_rflist_helper;

import android.content.Intent;
import android.view.View;

import com.zone.http2rflist.Config;
import com.zone.http2rflist.impl.enigne.ZhttpEngine;
import com.zone.http_rflist_helper.activity.BaseActvity;

public class MainActivity extends BaseActvity {

    static{
        new Config().setGlobalEngine(ZhttpEngine.class);
    }
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void findIDs() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.noPull_GloboTest:
                startActivity(new Intent(this, NetworkNoPull_Globlo_TestActivity.class));
                break;
            case R.id.pullGet:
                startActivity(new Intent(this, NetworkPull_TestActivity.class));
                break;

            default:
                break;
        }
    }
}
