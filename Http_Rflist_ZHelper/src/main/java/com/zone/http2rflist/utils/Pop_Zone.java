package com.zone.http2rflist.utils;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

public abstract class Pop_Zone extends PopupWindow {
	protected Activity activity;
	protected View mMenuView;
	private int layoutid,dismissViewId,showAtLocationViewId;
	private boolean bgVisibility=true;
	private int bgColor=0xb0000000;
	private Mode mode=null;
	public enum Mode{
		Fill,Wrap;
	}
	/**
	 * 
	 * @param layoutid
	 * @param mode 
	 * @param dismissViewId Pop layout in the control viewrect ID outside the scope of the dissming can be clicked
	 */
	public void setPopContentView(int layoutid,Mode mode,int dismissViewId){
		this.layoutid=layoutid;
		this.dismissViewId=dismissViewId;
		if(mode==null)
			throw new NullPointerException("setMode may be null!");
		this.mode=mode;
	}
	/**
	 * Just call show ()
	 * The default color is light black
	 * No setlocation view showatlocationviewid is incoming layout 
	 * @param activity  In that activity pop up pop
	 */
	public Pop_Zone(Activity activity) {
		this(activity,-1);
	}
	/**
	 * Just call show ()
	 * The default color is light black
	 * @param activity  In that activity pop up pop
	 * @param showAtLocationViewId
	 */
	public Pop_Zone(Activity activity, int showAtLocationViewId) {
		super(activity);
		this.activity = activity;
		this.showAtLocationViewId=showAtLocationViewId;
	}
	/**
	 * Dynamic call initpop (r.layout.alert_dialog, r.id.pop_layout);
	 * @param layout Load custom layout
	 * @param dismissViewId You can click on the rect dissming outside that control ID
	 * -1 It means that this function is not required
	 */
	private void initPop(int layout, final int dismissViewId) {
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(layout, null);
		// Set view selectpicpopupwindow
		this.setContentView(mMenuView);
		switch (mode) {
		case Fill:
			// Set the width of the selectpicpopupwindow pop-up window
			this.setWidth(LayoutParams.FILL_PARENT);
			// Set selectpicpopupwindow pop-up form high
			this.setHeight(LayoutParams.FILL_PARENT);
			break;
		case Wrap:
			// Set the width of the selectpicpopupwindow pop-up window
			this.setWidth(LayoutParams.WRAP_CONTENT);
			// Set selectpicpopupwindow pop-up form high
			this.setHeight(LayoutParams.WRAP_CONTENT);
			bgVisibility=false;
			break;

		default:
			break;
		}
		// Set selectpicpopupwindow pop up form to click
		this.setTouchable(true);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		if (bgVisibility) {
			// Set selectpicpopupwindow pop-up form animation effect this.setanimationstyle (r.style.animbottom);
			// The colordrawable color is a semi transparent
			ColorDrawable dw = new ColorDrawable(bgColor);
			// Set the background of the selectpicpopupwindow pop-up form
			setBackgroundDrawable(dw);
			// Set the location of layout to display in popupwindow
		}else{
			//This will allow the pop to return to the key dimiss
			setBackgroundDrawable(new BitmapDrawable()); 
		}
		if(dismissViewId!=-1){
			// Mmenuview add ontouchlistener monitor to determine the location of the touch screen
			// if the selection box outside the destruction of the pop-up box
			mMenuView.setOnTouchListener(new OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					
					if(event.getAction() == MotionEvent.ACTION_DOWN){
						int x = (int) event.getX();
						int y = (int) event.getY();
						View view=mMenuView.findViewById(dismissViewId);
							int left=view.getLeft();
							int right=view.getRight();
							int top=view.getTop();
							int bottom=view.getBottom();
							Rect rect=new Rect(left,top,right,bottom);
							if(!rect.contains(x, y)){
								dismiss();
							}
					}
					return true;
				}
			});
		}
	}
	/**
	 * This is the norm.
	 */
	public void show(){
		//Prevent every time from initialization to improve performance with respect to me
		if (mMenuView==null)
			initPop(layoutid, dismissViewId);
		findView(mMenuView);
		initData();
		setListener();
		View view = null;
		if(showAtLocationViewId!=-1)
			view = activity.findViewById(showAtLocationViewId);
		else
			view= ((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0);;
		setLocation(view);
	}
	/**
	 * Find pop within the control through the mmenuview in the parent class
	 * For example: tv_pop_cancel=(TextView) mMenuView.findViewById(R.id.tv_pop_cancel);
	 * @param mMenuView 
	 */
	protected abstract  void findView(View mMenuView) ;
	protected abstract  void initData() ;
	protected abstract  void setListener() ;
	/**
	 * Can also add animation this.setanimationstyle (r.style.popselectpicanimation);
	 * For example:this.showAtLocation(context.findViewById(R.id.main), Gravity.BOTTOM	| Gravity.CENTER_HORIZONTAL, 0, 0);
	 * And you can change the other settings for pop
	 * @param view 
	 */
	protected abstract void setLocation(View view);
	
	
	
	/**
	 * Example: the constructor calls the super below to default visibility for true
	 * setBgVisibility(false);
	 * @param bgVisibility
	 */
	protected void setBgVisibility(boolean bgVisibility) {
		this.bgVisibility=bgVisibility;
	}
	/**
	 * With the same set of links to the default visibility of the color can be true
	 * {@link #setBgVisibility}
	 * @param bgColor
	 */
	protected void setBgColor(int bgColor) {
		this.bgColor=bgColor;
	}
}
