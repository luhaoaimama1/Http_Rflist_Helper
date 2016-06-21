package com.zone.http_rflist_helper;
import android.os.Message;
import android.view.View;
import com.zone.http2rflist.NetParams;
import com.zone.http2rflist.Net;
import com.zone.http2rflist.impl.enigne.ZhttpEngine;
import com.zone.http_rflist_helper.activity.BaseActvity;
import com.zone.http_rflist_helper.utils.FileUtils;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
//TODO  listener有问题  null或者 有的时候不应该会有消息
public class NetworkNoPull_TestActivity extends BaseActvity {
	final	String UrlPath = Constant.ADDRESS;
	private ZhttpEngine engineGet,enginePost,engineFile;
	private static final int GET_TAG=1,POST_TAG=2,FILE_TAG=3;
	Map<String,String> params=new HashMap<String,String>();
	Map<String,File> fileMap=new HashMap<String,File>();
	@Override
	public void setContentView() {
		setContentView(R.layout.a_network_nopull);

		params.put("name", "干啥");
		//get传不了汉字
		engineGet=new ZhttpEngine(this, handler);
		engineGet.setShowDialog(true);
//		engineGet.sendFake(Net.get(UrlPath).params(new NetworkParams().setParamsMap(params)).handlerTag(GET_TAG));
		engineGet.sendFake(Net.get(UrlPath,new NetParams().setParamsMap(params)).handlerTag(GET_TAG));

		enginePost=new ZhttpEngine(this, handler);
//		enginePost.sendFake(Net.post(UrlPath).params( new NetworkParams().setParamsMap(params).setFileMap(fileMap)).handlerTag(POST_TAG));
		enginePost.sendFake(Net.post(UrlPath, new NetParams().setParamsMap(params).setFileMap(fileMap)).handlerTag(POST_TAG));

		File f = new File(FileUtils.getFile(""), "高达 - 00.mp3");
		File f2 = new File(FileUtils.getFile("DCIM", "Camera"), "20150619_091758.jpg");
		fileMap.put("upload", f);
		fileMap.put("upload2", f2);
		engineFile=new ZhttpEngine(this, handler,true);
//		engineFile.sendFake(Net.post(UrlPath).params(new NetworkParams().setParamsMap(params).setFileMap(fileMap)).handlerTag(FILE_TAG));
		engineFile.sendFake(Net.post(UrlPath,new NetParams().setParamsMap(params).setFileMap(fileMap)).handlerTag(FILE_TAG));
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
		engineFile.cancel();
//		engineFile.cancelTag();
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
