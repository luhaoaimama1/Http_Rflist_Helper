# Http_Rflist_Helper
一个可以把网络请求 和pullview关联，从而不需要处理 翻页 上拉下拉的动画处理 与 数据更新问题 并且可以全局切换网络请求库

### demo需要自己更改网络API 与 本地文件(上传用)

### 已解决的问题
- [x] 全局切换 网络请求库
- [x] 可以关联PullView 关联后自带翻页特性与 上拉和下拉的动画会自动处理 并且数据会自动更新
- [x] 全局设置  limitColumn ="limit", offsetColumn ="offset"字段
- [x] 全局设置返回值的错误检测 Config .setMsgCheckCallBack

### Jcenter

gradle

    compile 'com.zone:http-rflist-helper:1.0.3'

pom.xml

     <dependency>
       <groupId>com.zone</groupId>
       <artifactId>http-rflist-helper</artifactId>
       <version>1.0.3</version>
       <type>pom</type>
     </dependency>


# Preview
![](./demo/demoo.gif)

# Easy use:
1.全局切换网络请求库

      new Config().setGlobalEngine(ZhttpEngine.class);

2.初始化请求
     
     engineGet=new GlobalEngine(this, handler);//有三个构造器的 第三个是弹出 pop
	 //engineGet.setStartPage(1);//设置起始页
	 //handlerTag hander里处理加上自己的处理  如果不处理则不需要加handlerTag
     enginePost.sendFake(Net.post(UrlPath,new NetParams().setParamsMap(params)).handlerTag(POST_TAG));

3.未关联的时候想要执行需要此操作

	engineGet.start();

4.关联pullview  会自动执行
  
	googlePullView=new UltraPullView<String, Data>(swipe_container, rv, adapter, dataImg) {
    			@Override
    			public List<String> getAdapterData(Data entity) {
    				return entity.getImgEntity().getImg();
    			}
    		};
    		engineGet.relatePullView(googlePullView);
		
5.关联pullview 后想自己执行 第一页
	 
	 engineGet.firstPage();
高级操作与技巧请看demo;