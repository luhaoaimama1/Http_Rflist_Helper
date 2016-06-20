package com.zone.http_rflist_helper;

import android.os.Message;
import android.view.View;

import com.zone.http2rflist.Config;
import com.zone.http2rflist.GlobalEngine;
import com.zone.http2rflist.NetworkParams;
import com.zone.http2rflist.RequestParams;
import com.zone.http2rflist.callback.NetworkListener;
import com.zone.http2rflist.callback.SimpleNetworkListener;
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

	private GlobalEngine engineDown;

	@Override
	public void setContentView() {
		setContentView(R.layout.a_network_nopull);
		params.put("name", "疯子");

		
		engineGet=new GlobalEngine(this, handler);
		engineGet.setShowDialog(true);
		engineGet.sendFake(RequestParams.get(UrlPath)
				.params(new NetworkParams().setParamsMap(params)).handlerTag(GET_TAG).build());
		
		enginePost=new GlobalEngine(this, handler);
//		enginePost.sendPost(UrlPath, new RequestParamsNet().setParamsMap(sendFake), POST_TAG,null);
		enginePost.sendFake(RequestParams.post(UrlPath)
				.params(new NetworkParams().setParamsMap(params)).handlerTag(POST_TAG));

		
		File f = new File(FileUtils.getFile(""), "高达 - 00.mp3");
		File f2 = new File(FileUtils.getFile("DCIM", "Camera"), "20150619_091758.jpg");
		fileMap.put("upload", f);
		fileMap.put("upload2", f2);
		engineFile=new GlobalEngine(this, handler,true);
		engineFile.sendFake(RequestParams.post(UrlPath).params(new NetworkParams().setParamsMap(params).setFileMap(fileMap)).listener(new NetworkListener() {
			@Override
			public void onStart() {

			}

			@Override
			public void onCancelled() {

			}

			@Override
			public void onLoading(long total, long current, long networkSpeed, boolean isDone) {
				System.out.println("isDone:"+ isDone);
				System.out.println("进度："+(current*100/total));
			}

			@Override
			public void onSuccess(String msg, SuccessType type) {

			}

			@Override
			public void onFailure(String msg) {
				System.out.println(msg);

			}
		}).handlerTag(FILE_TAG));
		engineDown=new GlobalEngine(this);
		engineDown.sendFake(RequestParams.downLoad("http://down.360safe.com/360/inst.exe",FileUtils.getFile("","360.exe")).listener(new SimpleNetworkListener(){
			@Override
			public void onLoading(long total, long current, long networkSpeed, boolean isDone) {
				super.onLoading(total, current, networkSpeed, isDone);
				System.out.println(" progress" + ((int) (current * 100 / total)) + "  \t networkSpeed:" + networkSpeed +
						"  \t total:" + total + " \t current:" + current + " \t isDone:" + isDone + "");
			}
		}));
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
		case R.id.downLoad:
			engineDown.start();
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
