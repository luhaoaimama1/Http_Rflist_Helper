# Http_Rflist_Helper

A can network request and pullview Association, which does not need to handle page pull down animation processing and data update problem and global switch network request Libraries 

#### [中文版文档](./README-cn.md)

### Demo need to change the network API  and Local file （upload use）

### Solved problems 
- [x] Global switch network request Library 
- [x] May be associated with pullview association comes after animation flip characteristics and pull-up and pull-down will be automatically processed and the data will be updated automatically 
- [x] Global settings limitcolumn = "offsetcolumn", limit = "offset" field 
- [x] Error detection.Setmsgcheckcallback config for global settings return value 

# Preview
![](./demo/demoo.gif)

# Easy use:
1.Global switch network request Library 

      new Config().setGlobalEngine(ZhttpEngine.class);

2.request for initialization
     
     engineGet=new GlobalEngine(this, handler);
		 engineGet.setStartPage(1);//Set start page
		//handlerTag hander  Plus your own processing.  If you do not deal with it does not need to add handlertag 
     engineGet.prepare(RequestParams.post(UrlPath).params(new NetworkParams().setParamsMap(params)).handlerTag(GET_TAG));

3.It is not associated with the time when you want to perform this operation 

	engineGet.start();

4.Association pullview will automatically execute
  
	googlePullView=new UltraPullView<String, Data>(swipe_container, rv, adapter, dataImg) {
			@Override
			public List<String> getAdapterData(Data entity) {
				return entity.getImgEntity().getImg();
			}
		};
		engineGet.relatePullView(googlePullView);
		
Advanced operation and skills please see demo; 