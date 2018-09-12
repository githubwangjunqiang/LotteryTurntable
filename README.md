# LotteryTurntable


Android -小强-本人竭力打造的第一个开源项目 欢迎star 请勿商用 维护中 欢迎star 有bug 请提交lssues  

此控件是一个 抽奖转盘 因项目中用到 开源出来 供大家娱乐 嘿嘿

本库 还包含自己写的很多 自定义view loading 等 感兴趣 可以试试玩一玩 有兴趣交流的话加我QQ 980766134

当前最新版本号为  1.0.6

用法：
  1 添加仓库
  
	allprojects {
	
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
  2 添加依赖   
  
 	 dependencies {
  
          //CODE 修改为最新版本号 即可
	        implementation 'com.github.githubwangjunqiang:LotteryTurntable:vCODE'
	}
	
	
3 布局中使用

 	<FrameLayout
        	android:layout_width="match_parent"
        	android:layout_height="wrap_content">

        	<com.xiaoqiang.lottery.lottery.luckView
            	android:id="@+id/luckview"
            	android:layout_width="200dp"
            	android:layout_height="200dp"
            	android:layout_gravity="center"
		    	app:lv_bordercolor="@color/colorAccent"
		    	app:lv_isUnColor="true"
		    	app:lv_isUnSize="true"
		    	app:lv_luckDataColor="@color/colorAccent"
		    	app:lv_rotationSpeed="50"
		    	app:lv_viewbackcolor="@color/colorAccent"/>
			
		<!--中间指针 图片自己随意-->
		
        	<ImageView
            	android:onClick="doClicl"
            	android:layout_width="wrap_content"
            	android:layout_height="wrap_content"
            	android:layout_gravity="center"
            	android:src="@mipmap/ic_launcher" />
    </FrameLayout>
		

4 属性作用

		app:lv_bordercolor -》是指外边框颜色 默认外边框宽度是getPaddingLeft（） 如果没有设置 会有默认的宽度 如果不想有边框设置白色即可
		
		app:lv_isUnColor="true" -》中奖结果 是否高亮显示
		
		app:lv_isUnSize="true"  -》中奖结果 是否大小突出改变
		
		app:lv_luckDataColor  -》中奖结果 可以高亮显示的话 设置高亮的颜色 
		
		app:lv_rotationSpeed="50" -》 设置旋转的速度

		app:lv_viewbackcolor   ->  设置背景颜色
		
		
5 代码示例 

    第一步  初始化
	  //初始化 转盘
        IluckView view = findViewById(R.id.luckview);

        //给转盘填充奖品模块 实体类 LuckData
        for (int i = 0; i < 6; i++) {
            //创建奖品实体类 本库依赖此实体类
            LuckData data = new LuckData();

            //设置奖品背景色
            data.setBackColor(R.color.colorAccent);

            //设置奖品图片 可以不用填
            data.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

            //设置图片显示的宽度 百分比 例如控件半径的5分之一
            data.setBitmepWidth(5);

            //设置奖品名字
            data.setName("我是奖品");

            //设置奖品文字颜色
            data.setTextColor(R.color.colorAccent);

            //设置奖品文字大小 单位px
            data.setTextSize(12);

            //设置奖品唯一id 用来制定奖品位置
            data.setId(4);

	    //填入奖品
            view.addLuckData(data);
        }
	
     第二步  开始旋转
     
     	// 让转盘转起来
        view.startLuck();
	
     第三步  设置停止旋转 并指定奖品结果
     
     	 //设置停止 传入奖品结果 参数是您指定的奖品 就是您传入的奖品任意一个
        view.stopLuck(mLuckData.get(0));
        //也可以重载 添加回调
        view.stopLuck(data, new ICallback() {
                 @Override
                 public void luckStart(double mSpeed) {
                        //TODO 旋转开始
                 }

                 @Override
                 public void luckEnd(LuckData data) {
                        //TODO 旋转结束 可以提示 用户得将了 实体类-》data
                 }
        });
        /**
         * 也可以单独添加回调
         */
         view.setLuckCallback(new ICallback() {
                 @Override
                 public void luckStart(double mSpeed) {
                             //TODO 旋转开始
                 }

                 @Override
                 public void luckEnd(LuckData data) {
                             //TODO 旋转结束 可以提示 用户得将了 实体类-》data
                 }
         });
	
	
	
6 各种配置
 
 	//可动态配置各参数
 		view.setBorderColor(R.color.colorAccent);
        	view.setLuckDataColor(R.color.colorAccent);
        	view.setLuckisUnColor(true);
        	view.setLuckisUnSize(true);
        	view.setViewBackColor(int color);

 
 就是这么简单 完成了 抽奖转盘 有什么问题可以提交 我会及时修改bug
 
 注意事项
 
 	 本库使用SurfaceView 来实现转盘绘制 如有其他冲突 请您酌情考虑 
	 本库的默认中奖位置设置在中心位置正上方 未来可能会扩展出 设置位置 您在设置图片时 指针方向设置在中心上方就可以了



      添加了 loadingView

 历程

     1.0.6：修复转盘停止回调 重复调用的问题
	 1.0.5：添加转盘停止回调
     	

		
		
		
		
