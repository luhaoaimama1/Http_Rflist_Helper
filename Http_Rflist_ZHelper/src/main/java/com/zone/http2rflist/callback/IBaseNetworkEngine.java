package com.zone.http2rflist.callback;

import android.app.Dialog;

import com.zone.http2rflist.base.BasePullView;
import com.zone.http2rflist.Net;
import com.zone.http2rflist.utils.Pop_Zone;

/**
 * Created by Administrator on 2016/4/7.
 */
public interface IBaseNetworkEngine {

    void setStartPage(int startPage);

    void firstPage();

    void nextPage();

    void turnPage(int number);

    void start();

//    void sendFake(Net request);

    void sendFake(Net.Builder request);

    void sendhandlerMsg(String msg, int handlerTag);

    void relatePullView(BasePullView listView);


    void setDialog(Dialog dialog);

    void setPopWindow(Pop_Zone popWindow);

    boolean isShowDialog();

    void setShowDialog(boolean showDialog);

    <A> A gsonParseNoRelateList(String msg, Class<A> clazz);

    void setLimit(int limit);

    int getLimit();

    void cancelTag(Object obj);

    void cancel();
    void addResetUrl(ResetUrlCallback callback);
    interface ResetUrlCallback {
        String resetQuestUrlByTurnPage(String url,
                                       String limitColumn, int limit,
                                       String offsetColumn, int offset, int pageNumber);
    }
}
