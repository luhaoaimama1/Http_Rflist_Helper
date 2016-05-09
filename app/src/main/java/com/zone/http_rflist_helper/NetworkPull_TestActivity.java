package com.zone.http_rflist_helper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.ListView;
import com.bumptech.glide.Glide;
import com.zone.adapter.QuickAdapter;
import com.zone.adapter.callback.Helper;
import com.zone.http2rflist.NetworkParams;
import com.zone.http2rflist.RequestParams;
import com.zone.http2rflist.impl.enigne.ZhttpEngine;
import com.zone.http_rflist_helper.pullview.UltraPullView;
import com.zone.http_rflist_helper.activity.BaseActvity;
import com.zone.http_rflist_helper.entity.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import in.srain.cube.views.ptr.PtrFrameLayout;


public class NetworkPull_TestActivity extends BaseActvity {
	final	String UrlPath = Constant.ADDRESS;
	private PtrFrameLayout swipe_container;
	private ListView rv;
	private ZhttpEngine engineGet;
	private static final int GET_TAG=1;
	Map<String,String> params=new HashMap<String,String>();
	private List<String> dataImg=new ArrayList<String>();
	private QuickAdapter<String> adapter;
	private UltraPullView<String, Data> googlePullView;

	@Override
	public void setContentView() {
		params.put("name", "xoxoxxoo");
		setContentView(R.layout.a_network_pull);
		engineGet=new ZhttpEngine(this, handler);
//		engineGet.setStartPage(1);//设置起始页
		engineGet.prepare(RequestParams.post(UrlPath).params(new NetworkParams().setParamsMap(params)).handlerTag(GET_TAG));

	}

	@Override
	public void findIDs() {
		swipe_container=(PtrFrameLayout)findViewById(R.id.swipe_container);
		rv=(ListView)findViewById(R.id.rv);
	}

	@Override
	public void initData() {

		adapter=new QuickAdapter<String>(this, dataImg) {
			@Override
			public void fillData(Helper helper, String item, boolean itemChanged, int layoutId) {
				ImageView id_num=(ImageView) helper.getView(R.id.id_num);
				Glide.with(NetworkPull_TestActivity.this).load(item)
						.placeholder(R.drawable.ic_stub).dontAnimate()
						.error(R.drawable.ic_error)
						.into(id_num);
			}

			@Override
			public int getItemLayoutId(String s, int position) {
				return  R.layout.item_network_pull;
			}
		};
		adapter.relatedList(rv);
		googlePullView=new UltraPullView<String, Data>(swipe_container, rv, adapter, dataImg) {
			@Override
			public List<String> getAdapterData(Data entity) {
				return entity.getImgEntity().getImg();
			}
		};
		engineGet.relatePullView(googlePullView);
	}

	@Override
	public void setListener() {
		
	}
	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case GET_TAG:
//			System.out.println("GET_TAG:"+msg.obj);
			System.err.println("handleMessage size:"+dataImg.size());
			break;

		default:
			break;
		}
		return super.handleMessage(msg);
	}

}
