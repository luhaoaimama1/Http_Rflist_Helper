package com.zone.http2rflist;
import com.zone.http2rflist.callback.NetworkListener;
import com.zone.http2rflist.entity.HttpTypeNet;

import java.io.File;

/**
 * Created by Administrator on 2016/3/25.
 */
public class Net {
  public  final NetParams params;
  public final NetworkListener listener;
  public  final  String urlString;
  public final int handlerTag;
  public final Object cancelTag;
  public final HttpTypeNet httpTypeNet;

    private Net(Builder builder) {
        this.params = builder.params;
        this.listener = builder.listener;
        this.urlString = builder.urlString;
        this.handlerTag = builder.handlerTag;
        this.cancelTag = builder.cancelTag;
        this.httpTypeNet = builder.httpTypeNet;
        this.params.setHttpTypeNet(httpTypeNet);
    }
    //-----------------------------get------------------------
    public static Builder get(String url){
        return  new Net.Builder().get().url(url);
    }
    public static Builder get(String url,NetworkListener listener){
        return  new Net.Builder().get().url(url).listener(listener);
    }
    public static Builder get(String url,NetParams params){
        return  new Net.Builder().get().url(url).params(params);
    }
    public static Builder get(String url, NetParams params, NetworkListener listener){
        return  new Net.Builder().get().url(url).params(params).listener(listener);
    }
    //-----------------------------post------------------------
    public static Builder post(String url){
        return new Net.Builder().post().url(url);
    }
    public static Builder post(String url,NetParams params){
        return new Net.Builder().post().url(url).params(params);
    }
    public static Builder post(String url, NetParams params, NetworkListener listener){
        return new Net.Builder().post().url(url).params(params).listener(listener);
    }

    //-----------------------------post json------------------------
    public static Builder postJson(String url, NetParams params) {
        return new Net.Builder().postJson().url(url).params(params);
    }

    public static Builder postJson(String url, NetParams params, NetworkListener listener) {
        return new Net.Builder().postJson().url(url).params(params).listener(listener);
    }

    //-----------------------------head------------------------
    public static Builder head(String url){
        return new Net.Builder().head().url(url);
    }
    public static Builder head(String url, NetParams params){
        return new Net.Builder().head().url(url).params(params);
    }
    public static Builder head(String url, NetParams params, NetworkListener listener){
        return new Net.Builder().head().url(url).params(params).listener(listener);
    }
    //-----------------------------delete------------------------
    public static Builder delete(String url){
        return new Net.Builder().delete().url(url);
    }
    public static Builder delete(String url, NetParams params){
        return new Net.Builder().delete().url(url).params(params);
    }
    public static Builder delete(String url, NetParams params, NetworkListener listener){
        return new Net.Builder().delete().url(url).params(params).listener(listener);
    }
    //-----------------------------put------------------------
    public static Builder put(String url, NetParams params){
        return  new Net.Builder().put().url(url).params(params);
    }
    public static Builder put(String url, NetParams params, NetworkListener listener){
        return  new Net.Builder().put().url(url).params(params).listener(listener);
    }
    //-----------------------------patch------------------------
    public static Builder patch(String url, NetParams params){
        return  new Net.Builder().patch().url(url).params(params);
    }
    public static Builder patch(String url, NetParams params, NetworkListener listener){
        return  new Net.Builder().patch().url(url).params(params).listener(listener);
    }
    //-----------------------------downLoad------------------------
    public static Builder downLoad(String url,File target){
        return new Net.Builder().downLoad(target).url(url);
    }
    public static Builder downLoad(String url,File target, NetParams params){
        return new Net.Builder().downLoad(target).url(url).params(params);
    }
    public static Builder downLoad(String url, File target, NetParams params, NetworkListener listener){
        return new Net.Builder().downLoad(target).url(url).params(params).listener(listener);
    }
    public static Builder downLoad(String url,File target, NetworkListener listener){
        return new Net.Builder().downLoad(target).url(url).listener(listener);
    }


    public static class Builder {

        private NetParams params;
        private NetworkListener listener;
        private String urlString;
        private int handlerTag=-1;
        private HttpTypeNet httpTypeNet=HttpTypeNet.GET;
        private Object cancelTag;
        private File target;
        private boolean isPostJson;
        public Builder() {
        }

        public Builder url(String urlString) {
            this.urlString = urlString;
            return this;
        }

        public Builder listener(NetworkListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder handlerTag(int handlerTag) {
            this.handlerTag = handlerTag;
            return this;
        }
        public Builder cancelTag(Object cancelTag) {
            this.cancelTag = cancelTag;
            return this;
        }

        public Builder params(NetParams params) {
            this.params = params;
            return this;
        }
        public  Builder get(){
            httpTypeNet=HttpTypeNet.GET;
            return this;
        }
        public  Builder downLoad(File target){
            httpTypeNet=HttpTypeNet.GET;
            this.target=target;
            return this;
        }
        public  Builder head(){
            httpTypeNet=HttpTypeNet.HEAD;
            return this;
        }
        public  Builder delete(){
            httpTypeNet=HttpTypeNet.DELETE;
            return new Net.Builder();
        }
        public  Builder post(){
            httpTypeNet=HttpTypeNet.POST;
            return this;
        }

        public  Builder postJson(){
            httpTypeNet=HttpTypeNet.POST;
            isPostJson=true;
            return this;
        }

        public  Builder put(){
            httpTypeNet=HttpTypeNet.PUT;
            return this;
        }
        public  Builder patch(){
            httpTypeNet=HttpTypeNet.PATCH;
            return this;
        }

        public Net build() {
            if(params==null)
                params=new NetParams();
            params.isDownLoad(target);
            params.setPostJson(isPostJson);
            return new Net(this);
        }
    }
}
