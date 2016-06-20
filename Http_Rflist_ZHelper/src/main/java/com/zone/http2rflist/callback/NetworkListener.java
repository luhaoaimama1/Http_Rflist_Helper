package com.zone.http2rflist.callback;

import com.zone.http2rflist.entity.SuccessType;

public interface NetworkListener {
    void onStart();

    //Success and failure at the end of the walk
    void onCancelled();

    void onLoading(long total, long current,long networkSpeed,boolean isDone);

    void onSuccess(String msg,SuccessType type);

    void onFailure(String msg);


}
