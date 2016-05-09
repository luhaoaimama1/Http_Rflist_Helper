package com.zone.http2rflist.base;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zone.http2rflist.utils.ExceptionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

//A listview that package a t
public abstract class BasePullView<T,K,M,E,A> {
	protected final Context context;
	public  T pullView;
	public  M adapter;
	public  List<E> data;
	public  Class<A> clazz;
	//This is the gson conversion.
	private  A entity;
	public K listView;
	public BaseNetworkEngine baseNetworkQuest;
//	public  OnRefresh2LoadMoreListener listener;
	public  BasePullView(Context context,T pullView,K listView,M adapter,List<E> data) {
		this.context=context;
		this.pullView=pullView;
		this.listView=listView;
		this.adapter=adapter;
		this.data=data;
		setType();
	}
	
	public  void relateBaseNetworkQuest(BaseNetworkEngine baseNetworkQuest){
		this.baseNetworkQuest=baseNetworkQuest;
	}
	//Get class by generic
	@SuppressWarnings("unchecked")
	private void setType(){
		Type superClass = getClass().getGenericSuperclass();
		Type[] types = ((ParameterizedType)superClass).getActualTypeArguments();
		this.clazz = (Class<A>)types[types.length-1]; 
	}
	public  boolean gsonParse(String msg){
		boolean resultIsRight= MsgErrorCheck.errorChecked(msg);
		if(!resultIsRight)
			return false;
		Gson g=new Gson();
		try {
			entity=g.fromJson(msg, clazz);
		} catch (JsonSyntaxException e) {
			ExceptionUtils.quiet(e);
			return false;
		}
		return true;
	};
	public void clearData(){
		if (entity != null&& getAdapterData(entity).size()!= 0)
				data.clear();
	}
	public void addAllData2Notify(){
		if (entity!=null) {
			if(getAdapterData(entity).size()==0){
				//todo something the matter ;
//				baseNetworkQuest.relateReturnEmptyData();
				baseNetworkQuest.isLastPage =true;
				lastPageRemoveOnLoadListener();
			}else {
				baseNetworkQuest.isLastPage =false;
				if(getAdapterData(entity).size()<baseNetworkQuest.getLimit()){
					//Cannot pull up operation
					baseNetworkQuest.isLastPage =true;
					lastPageRemoveOnLoadListener();
				}

				data.addAll(getAdapterData(entity));
				notifyDataSetChanged();
			}
		}
	}
	

	public abstract void onRefreshComplete();
	public abstract void onLoadMoreComplete();
	public abstract void lastPageRemoveOnLoadListener();
	public abstract void onLoadMoreFail();
	public abstract void notifyDataSetChanged();
	public abstract List<E> getAdapterData(A entity);

}
