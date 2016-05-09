# Http_Rflist_Helper
一个可以把网络请求 和pullview关联 导致 翻页后不需要写 数据更新  上啦或者下拉动画完成
### 已解决的问题
- [x] 全局切换 网络请求库
- [x] 可以关联PullView 这样翻页操作会自带 上拉 和下拉的动画会自动处理 并且数据会更新
- [x] 全局设置  limitColumn ="limit", offsetColumn ="offset"字段
- [x] 全局设置返回值的错误检测 Config .setMsgCheckCallBack

# Usage

# Preview
![](./demo/demo.gif)
# Easy use:
1.初始化请求
     
     engineGet=new ZhttpEngine(this, handler);
		 engineGet.setStartPage(1);//设置起始页
		//handlerTag hander里处理加上自己的处理
     engineGet.prepare(RequestParams.post(UrlPath).params(new NetworkParams().setParamsMap(params)).handlerTag(GET_TAG));
     
2.关联pullview  完事~
  
	googlePullView=new UltraPullView<String, Data>(swipe_container, rv, adapter, dataImg) {
			@Override
			public List<String> getAdapterData(Data entity) {
				return entity.getImgEntity().getImg();
			}
		};
		engineGet.relatePullView(googlePullView);

3.全局切换网络请求库
    new Config().setGlobalEngine(ZhttpEngine.class);

