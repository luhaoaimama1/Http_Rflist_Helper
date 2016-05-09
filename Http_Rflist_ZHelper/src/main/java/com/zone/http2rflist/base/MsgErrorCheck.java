package com.zone.http2rflist.base;

public  class MsgErrorCheck {
	private static MsgCheckCallBack msgCheckCallBack;
	//Detect whether the data back on the network is in the correct format if not gson parse
	public static  boolean  errorChecked(String msg){
		if(msgCheckCallBack!=null)
			return msgCheckCallBack.errorChecked(msg);
		return true;
	};
	public static void setMsgCheckCallBack(MsgCheckCallBack msgCheckCallBack){
		MsgErrorCheck.msgCheckCallBack=msgCheckCallBack;
	};
	public interface MsgCheckCallBack{
		//It can send messages at the wrong time.
		boolean errorChecked(String msg);
	}
}
