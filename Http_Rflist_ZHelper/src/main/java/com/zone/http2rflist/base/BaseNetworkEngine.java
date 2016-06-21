package com.zone.http2rflist.base;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import com.google.gson.Gson;
import com.zone.http2rflist.Net;
import com.zone.http2rflist.callback.IBaseNetworkEngine;
import com.zone.http2rflist.utils.Pop_Zone;
import java.util.ArrayList;
import java.util.List;

//This is  handle network requests dialog and handler return information
public abstract class BaseNetworkEngine implements IBaseNetworkEngine {

	public enum DialogType{
		pop,dialog;
	}
	//default level
	private DialogType  dialogType=DialogType.pop;

	private static  String limitColumn ="limit", offsetColumn ="offset";
	protected Handler handler;
	private boolean isShowDialog =false;
	protected int limit=10, pageNumber=0, startPage=0;
	private Dialog dialog;
	private Pop_Zone popWindow;

	private BasePullView listView;
	//Outside of the class is also used in the class
	public final Context context;
	protected boolean isLastPage =false;
	//When the current page is to prevent processing and then flip flip parameters.
	private List<Integer> pageNumberhistory=new ArrayList<>();
	private Net request;

	public BaseNetworkEngine(Context context) {
		this(context,null,false);
	}
	public BaseNetworkEngine(Context context, Handler handler) {
		this(context,handler,false);
	}

	public BaseNetworkEngine(Context context, Handler handler, boolean isShowDialog) {
		this.context=context;
		this.handler= handler;
		this.isShowDialog = isShowDialog;
	}

	@Override
	public void setStartPage(int startPage) {
		this.startPage=startPage;
		pageNumber+=startPage;
	}

	@Override
	//his is already requested by the use of firstpage can be
	public  void firstPage(){
		turnPageExceptionChecked();
		pageNumber=startPage;
		start();
	};
	@Override
	public  void nextPage(){
		if (!isLastPage) {
			turnPageExceptionChecked();
			pageNumber++;
			start();
		}else{
			//Is the last page of the time
			listView.onLoadMoreComplete();
		}
	};
	@Override
	public  void turnPage(int number){
		turnPageExceptionChecked();
		pageNumber=number;
		start();
	};
	private void turnPageExceptionChecked(){
		if(listView==null)
			throw new IllegalStateException("please must be use method:relatePullView!");
	}
	@Override
	//Start task
	public void start(){
		execute(true);
	}
//	@Override
//	public  void sendFake(Net request){
//		this.request=request;
//		execute(false);
//	}

	@Override
	public void sendFake(Net.Builder request) {
		this.request=request.build();
		execute(false);
	}

	private void execute(boolean run){
		if (run&&request!=null) {
			showDialog();
			relateAddTurnPage();
			//TODO Get only when there is a cache memory cache local cache and then http
			ab_Send(request);
		}
	}
	@Override
	// Error and success both need to send a message but remember that there must be only one out.
	public void sendhandlerMsg(final String msg,final int handlerTag){
		if(handler==null)
			return ;
		//把dialog弄掉
		handler.post(new Runnable() {

			@Override
			public void run() {
				hideDialog();
			}
		});
		//Linkage list animation to get rid of data processing
		if(listView!=null&&pageNumberhistory.size()>0){
			//Handler is serial.
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					int number=pageNumberhistory.get(0);
					//data handle
					boolean parseOK=listView.gsonParse(msg);
					if (parseOK) {
						if(number==startPage){
							listView.clearData();
							//When the first page of data processing can flip
							isLastPage =false;
						}
						listView.addAllData2Notify();
					}
					//Animation out
					removeListAnimal(number, listView,parseOK);
					//Remove the nubmerhistory from the process.
					pageNumberhistory.remove(0);
					//Arg1 is the number of pages ar2 doesn't know that -1 was.
					if (handlerTag !=-1)
						handler.obtainMessage(handlerTag,number,-1,msg).sendToTarget();
				}
				//Animation out
				private void removeListAnimal(int number, BasePullView listView,boolean parseOK) {
					if(number==startPage)
						listView.onRefreshComplete();
					else{
						if (parseOK)
							listView.onLoadMoreComplete();
						else
							listView.onLoadMoreFail();
					}
				}
			});
		}else{
			//Not linked to send information to handler
			if (handlerTag ==-1)
				throw new IllegalStateException("handlerTag must be set!");
			handler.obtainMessage(handlerTag,msg).sendToTarget();
		}
	}
	@Override
	//The establishment of list linkage will add flip function
	public void relatePullView(BasePullView  listView ){
		this.listView=listView;
		listView.relateBaseNetworkQuest(this);
	}
	private void relateAddTurnPage(){
		if(listView!=null){
			request.params.put(limitColumn, limit + "");
			pageNumberhistory.add(pageNumber);
	        int offest = limit* pageNumber;
			request.params.put(offsetColumn, offest + "");
		}
	}
	protected abstract void ab_Send(Net request);

	//Set default dialog
	protected  abstract Dialog createDefaultDialog(Context context);
	//Set default popwindow
	protected  abstract Pop_Zone createDefaultPopWindow(Context context);
	@Override
	//set dialog
	public  void setDialog(Dialog dialog){
		this.dialog=dialog;
	};
	@Override
	//set popWindow
	public  void setPopWindow(Pop_Zone popWindow){
		this.popWindow=popWindow;
	};

	//Listview request is not displayed when dialog
	private void showDialog() {
		if (listView==null) {
			switch (dialogType) {
				case pop:
					if (popWindow == null&& isShowDialog)
						popWindow = createDefaultPopWindow(context);
					//No dialog will not burst out.
					if (popWindow != null&&!popWindow.isShowing())
						popWindow.show();
					break;
				case dialog:
					if (dialog == null&& isShowDialog)
						dialog = createDefaultDialog(context);
					//No dialog will not burst out.
					if (dialog != null&&dialog.isShowing())
						dialog.show();
					break;
			}
		}
	}
	private void hideDialog() {
		if (isShowDialog) {
			if (dialog != null&&dialog.isShowing())
				dialog.dismiss();
			if(popWindow!=null&&popWindow.isShowing())
				popWindow.dismiss();
		}
	}
	@Override
	public boolean isShowDialog() {
		return isShowDialog;
	}
	@Override
	public void setShowDialog(boolean showDialog) {
		this.isShowDialog = showDialog;
	}
	@Override
	public  <A> A gsonParseNoRelateList(String msg, Class<A> clazz){
		boolean resultIsRight= MsgErrorCheck.errorChecked(msg);
		if(!resultIsRight)
			return null;
		Gson g=new Gson();
		return 	g.fromJson(msg, clazz);
	};
	@Override
	public int getLimit() {
		return limit;
	}
	@Override
	public void setLimit(int limit) {
		this.limit = limit;
	}

	public static String getLimitColumn() {
		return limitColumn;
	}

	public static void setLimitColumn(String limitColumn) {
		BaseNetworkEngine.limitColumn = limitColumn;
	}

	public static String getOffsetColumn() {
		return offsetColumn;
	}

	public static void setOffsetColumn(String offsetColumn) {
		BaseNetworkEngine.offsetColumn = offsetColumn;
	}
}
