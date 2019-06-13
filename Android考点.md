# Android知识
### 四大组件是什么？
Activity Service BoradCast ContentProvider
### Activity 的生命周期？
 这个就不用再说了吧
### Activity 之间的通信方式？
* Intent
* 静态变量
* 全局变量 及Application
* Android系统剪切板
* 本地化存储方式(SharedPreference、SQLite、File)
* Andorid组件（Broadcast）
* EventBus


### Activity 各种情况下的生命周期？

横竖切换 ---根据screenSize的值

跳转到下一个Actitity

### 横竖屏切换时 Activity 的生命周期
稍微记得不同版本生命周期不一样 （6.0--7.0--8.0）
这边要看configChanges的设置
设置android:configChanges="orientation|keyboardHidden|screenSize"  则都不会调用Activity的其他生命周期方法，只会调用onConfigurationChanged方法。

如果设置了 orientation screenSize 生命周期
竖(横)屏启动：onCreate -->onStart-->onResume

* 切换横(竖)屏：onConfigurationChanged   （Android 6.0 Android 7.0 Android 8.0）

如果没设置
* 正常页面切换 -> onPause ->onSaveInstanceState ->onStop ->onDestroy -> onCreate ->onStart -> onRestoreInstanceState ->onResume


### 前台切换到后台，然后再回到前台时 Activity 的生命周期
onPause -> onSaveInstanceState -> onStop -> onRestart -> onstart -> onResume

### 弹出 Dialog 的时候按 Home 键时 Activity 的生命周期

onPause ->onSaveInstanceState(回调确实有) ->onStop ->onRestart ->onStart->onResume

### 两个 Activity 之间跳转时的生命周期
当前Activity -> onPause ->2onCreate->2onStart->2onResume ->1onStop
确保有一个在前台页面
### 下拉状态栏时 Activity 的生命周期
小米6.0 不会变化。
### Activity 与 Fragment 之间生命周期比较？
### Activity 的四种 LaunchMode（启动模式）的区别？

* 1.standard 标准模式，这也是系统默认启动模式

* 2.singleTop 
栈顶复用模式：如果新Activity已经位于任务栈的栈顶，那么此Activity不会被重新创建，但是会调用 onPause->onNewIntent->onResume 
应用场景：登录页面、推送通知栏

* 3.singleTask   
栈内复用模式：这是一种单实例模式，只要Activity在一个栈中存在，那么多次启动此Activity都不会重新创建实例，若存在此Activity会把栈前的Activity销毁
例：对于一个任务栈，存在ADBC，在重新启动D，则相应栈会变成AD
应用场景：主页面（Fragment的containerActivity）、WebView页面、扫一扫页面、电商中：购物界面，确认订单界面，付款界面
 
* 4.singleInstance
单实例模式，，它除了具有SingleTask模式的所有特性外，还加强了一点，具有此种模式的Activity只能单独位于一个任务栈中，换句话说，当A启动后，
系统会为它新建一个新的任务栈，然后A独自在这个新的任务栈中。
应用场景：系统Launcher、锁屏键、来电显示等系统应用

任务栈 用TaskAffinity，默认任务栈的名字为应用的包名

### Activity 状态保存与恢复？
异常状况下会调用
对应的onSaveInstanceState() 和onRestoreInstanceState（）

* 什么时候调用onSaveInstanceState()
1.屏幕旋转重建会调用onSaveInstanceState()
2.启动另一个activity: 当前activity在离开前会调用onSaveInstanceState()
3.按Home键的情形和启动另一个activity一样, 当前activity在离开前会onSaveInstanceState()

* 什么时候调用onRestoreInstanceState()
1.屏幕旋转重建会调用onRestoreInstanceState()
2.启动另一个activity，返回时如果因为被系统杀死需要重建, 则会从onCreate()重新开始生命周期, 调用onRestoreInstanceState()
3.按Home键的情形和启动另一个activity一样，用户再次点击应用图标返回时, 如果重建发生, 则会调用onCreate()和onRestoreInstanceState()
（注：2、3其实都是Activity异常销毁）


### Activity 和 Fragment 之间怎么通信， Fragment 和 Fragment 怎么通信？
具体参考FragmentActivity、NameFragment、NameContentFragment3个类
1.使用接口
2.直接在一个Fragment中调用另外一个Fragment中的方法
3.使用广播
4.Fragment直接调用Activity中的public方法

### Service 的生命周期？

* 使用context.startService() 启动Service
其生命周期为context.startService() ->onCreate()- >onStart()->Service running-->(如果调用context.stopService() )->onDestroy() ->Service shut down

* 对于bindService()启动Service会经历
context.bindService()->onCreate()->onBind()->Service running-->onUnbind() -> onDestroy() ->Service stop

### Service 的启动方式？

* 1. startService 
通过startService启动后，service会一直无限期运行下去，只有外部调用了stopService()或stopSelf()方法时，该Service才会停止运行并销毁。
多次startService不会重复执行onCreate回调，但每次都会执行onStartCommand回调。

2.bindService()
bindService启动服务特点：
1.bindService启动的服务和调用者之间是典型的client-server模式。调用者是client，service则是server端。service只有一个，但绑定到service上面的client可以有一个或很多个。这里所提到的client指的是组件，比如某个Activity。
2.client可以通过IBinder接口获取Service实例，从而实现在client端直接调用Service中的方法以实现灵活交互，这在通过startService方法启动中是无法实现的。
3.bindService启动服务的生命周期与其绑定的client息息相关。当client销毁时，client会自动与Service解除绑定。当然，client也可以明确调用Context的unbindService()方法与Service解除绑定。当没有任何client与Service绑定时，Service会自行销毁。




### Service 与 IntentService 的区别?
### Service 和 Activity 之间的通信方式？
### 对 ContentProvider 的理解？
### ContentProvider、ContentResolver、ContentObserver 之间的关系？
### 对 BroadcastReceiver 的了解？
### 广播的分类？使用方式和场景？
### 动态广播和静态广播有什么区别？
### AlertDialog、popupWindow、Activity 之间的区别？
### Application 和 Activity 的 Context 之间的区别？
### Android 属性动画特性？
### 请列举 Android 中常见的布局（Layout）类型，并简述其用法，以及排版效率。【猎豹移动】 LinearLayout、RelativeLayout、FrameLayout 的特性对比及使用场景？
### 对 SurfaceView 的了解？
### Serializable 和 Parcelable 的区别？
### Android 中数据存储方式有哪些？
### 屏幕适配的处理技巧都有哪些?
### Android 各个版本 API 的区别？
### 动态权限适配方案，权限组的概念？
### 为什么不能在子线程更新 UI？
### ListView 图片加载错乱的原理和解决方案？
### 对 RecycleView 的了解？
### Recycleview 和 ListView 的区别？
### RecycleView 实现原理？
### Android Manifest 的作用与理解？
### 多线程在 Android 中的使用？
### 区别 Animation 和 Animator 的用法，概述实现原理？【猎豹移动】
