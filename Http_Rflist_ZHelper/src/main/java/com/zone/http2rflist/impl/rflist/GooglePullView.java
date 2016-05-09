package com.zone.http2rflist.impl.rflist;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.zone.http2rflist.base.BasePullView;
import com.zone.http2rflist.utils.LogUtils;

import java.util.List;

/**
 * todo There is a problem on the load when the template is put on it
 */
public abstract class GooglePullView<E, A> extends BasePullView<SwipeRefreshLayout,ListView, BaseAdapter,E, A> {
	boolean loadMoreOk=true;
	public GooglePullView(Context context,SwipeRefreshLayout pullView, ListView listView, BaseAdapter adapter, List<E> data) {
		super(context,pullView, listView,adapter, data);
		pullViewSetListener();
	}

	private void pullViewSetListener() {
		pullView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				baseNetworkQuest.firstPage();
			}
		});

		listView.setOnScrollListener(new OnScrollListener() {
			int scrollState;

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				this.scrollState = scrollState;
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
								 int visibleItemCount, int totalItemCount) {
				if (scrollState != OnScrollListener.SCROLL_STATE_IDLE) {
					if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
						if (loadMoreOk) {
							LogUtils.d("Loading...");
							baseNetworkQuest.nextPage();
							loadMoreOk = false;
						}
					}
				}
			}
		});
	}

	@Override
	public void onRefreshComplete() {
		pullView.setRefreshing(false);
	}

	@Override
	public void onLoadMoreComplete() {
		loadMoreOk=true;
	}

	@Override
	public void notifyDataSetChanged() {
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onLoadMoreFail() {
	}
	@Override
	public void lastPageRemoveOnLoadListener() {
	}
}
