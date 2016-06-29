package com.zone.http_rflist_helper;
import android.os.Message;
import android.widget.ListView;

import com.zone.adapter.Helper.HolderSymbol;
import com.zone.adapter.QuickAdapter;
import com.zone.adapter.callback.Helper;
import com.zone.http2rflist.GlobalEngine;
import com.zone.http2rflist.Net;
import com.zone.http2rflist.callback.IBaseNetworkEngine;
import com.zone.http2rflist.impl.enigne.ZhttpEngine;
import com.zone.http_rflist_helper.entity.MeiZiData;
import com.zone.http_rflist_helper.pullview.UltraPullView;
import com.zone.http_rflist_helper.activity.BaseActvity;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;


public class ListTestActivity extends BaseActvity {
	private PtrFrameLayout swipe_container;
	private ListView rv;
	private GlobalEngine engineGet;
	private static final int GET_TAG=1;
//	Map<String,String> params=new HashMap<String,String>();
	private List<MeiZiData.ResultsBean> dataImg=new ArrayList<>();
	private QuickAdapter<MeiZiData.ResultsBean> adapter;
	private UltraPullView<MeiZiData.ResultsBean, MeiZiData> googlePullView;

	@Override
	public void setContentView() {
//		params.put("name", "xoxoxxoo");
		setContentView(R.layout.a_network_pull);
		engineGet=new GlobalEngine(this, handler);
//		engineGet.setStartPage(1);//设置起始页
//		engineGet.sendFake(Net.post(UrlPath).params(new NetworkParams().setParamsMap(params)).handlerTag(GET_TAG));
		engineGet.sendFake(Net.get(Constant.MeiziApi).handlerTag(GET_TAG));
		engineGet.addResetUrl(new IBaseNetworkEngine.ResetUrlCallback() {
			@Override
			public String resetQuestUrlByTurnPage(String url, String limitColumn, int limit, String offsetColumn, int offset, int pageNumber) {
//				http://gank.io/api/data/福利/10/1
				System.out.println( "访问的页url:"+url+"/"+limit+"/"+pageNumber);
				return url+"/"+limit+"/"+pageNumber;
			}
		});
//		engineGet.firstPage();//第一页  start()真正的执行 这里边有 start()方法
	}

	@Override
	public void findIDs() {
		swipe_container=(PtrFrameLayout)findViewById(R.id.swipe_container);
		rv=(ListView)findViewById(R.id.rv);
	}

	@Override
	public void initData() {

		adapter=new QuickAdapter<MeiZiData.ResultsBean>(this, dataImg) {

			@Override
			public void fillData(Helper<MeiZiData.ResultsBean> helper, MeiZiData.ResultsBean item, boolean itemChanged, int layoutId) {
				HolderSymbol temp = new HolderSymbol();
				temp.setPlaceholderRes(R.drawable.ic_stub);
				temp.setErrorRes(R.drawable.ic_error);
				helper.setImageUrl(R.id.id_num,item.getUrl(),temp);
			}

			@Override
			public int getItemLayoutId(MeiZiData.ResultsBean meiZiData, int position) {
				return R.layout.item_network_pull;
			}
		};
		adapter.relatedList(rv);
		googlePullView=new UltraPullView<MeiZiData.ResultsBean, MeiZiData>(swipe_container, rv) {
			@Override
			public List<MeiZiData.ResultsBean> getAdapterData(MeiZiData entity) {
				return entity.getResults();
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
