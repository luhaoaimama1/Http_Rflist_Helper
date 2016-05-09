package com.zone.http_rflist_helper;

import android.os.Message;
import android.view.View;

import com.zone.http2rflist.Config;
import com.zone.http2rflist.GlobalEngine;
import com.zone.http2rflist.NetworkParams;
import com.zone.http2rflist.RequestParams;
import com.zone.http2rflist.callback.NetworkListener;
import com.zone.http2rflist.entity.SuccessType;
import com.zone.http2rflist.impl.enigne.ZhttpEngine;
import com.zone.http_rflist_helper.activity.BaseActvity;
import com.zone.http_rflist_helper.utils.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

//TODO  listener有问题  null或者 有的时候不应该会有消息
public class NetworkNoPull_Globlo_TestActivity extends BaseActvity {
	final	String UrlPath = Constant.ADDRESS;
	private GlobalEngine engineGet,enginePost,engineFile;
	private static final int GET_TAG=1,POST_TAG=2,FILE_TAG=3;
	Map<String,String> params=new HashMap<String,String>();
	Map<String,File> fileMap=new HashMap<String,File>();
	static{
		new Config().setGlobalEngine(ZhttpEngine.class);
	}
	@Override
	public void setContentView() {
		setContentView(R.layout.a_network_nopull);
		params.put("name", "疯子");

		
		engineGet=new GlobalEngine(this, handler);
		engineGet.setShowDialog(true);
		engineGet.prepare(RequestParams.get(UrlPath)
				.params(new NetworkParams().setParamsMap(params)).handlerTag(GET_TAG).build());
		
		enginePost=new GlobalEngine(this, handler);
//		enginePost.sendPost(UrlPath, new RequestParamsNet().setParamsMap(prepare), POST_TAG,null);
		enginePost.prepare(RequestParams.post(UrlPath)
				.params(new NetworkParams().setParamsMap(params)).handlerTag(POST_TAG));

		
		File f = new File(FileUtils.getFile(""), "高达 - 00.mp3");
		File f2 = new File(FileUtils.getFile("DCIM", "Camera"), "20150619_091758.jpg");
		fileMap.put("upload", f);
		fileMap.put("upload2", f2);
		engineFile=new GlobalEngine(this, handler,true);
		engineFile.prepare(RequestParams.post(UrlPath).params(new NetworkParams().setParamsMap(params).setFileMap(fileMap)).listener(new NetworkListener() {
			@Override
			public void onStart() {

			}

			@Override
			public void onCancelled() {

			}

			@Override
			public void onLoading(long total, long current, long networkSpeed, boolean isUploading) {
				System.out.println("isUploading:"+isUploading);
				System.out.println("进度："+(current*100/total));
			}

			@Override
			public void onSuccess(String msg, SuccessType type) {

			}

			@Override
			public void onFailure(String msg) {

			}
		}).handlerTag(FILE_TAG));
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
		super.onClick(v);
		switch (v.getId()) {
		case R.id.client_get:
			engineGet.start();
			break;
		case R.id.client_post:
			enginePost.start();
			break;
		case R.id.client_upload:
			engineFile.start();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		engineGet.cancel();
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case GET_TAG:
			System.out.println("GET_TAG:"+(String)msg.obj);
			break;
		case POST_TAG:
			System.out.println("POST_TAG:"+(String)msg.obj);
			break;
		case FILE_TAG:
			System.out.println("FILE_TAG:"+(String)msg.obj);
			break;

		default:
			break;
		}
		return super.handleMessage(msg);
	}

}
