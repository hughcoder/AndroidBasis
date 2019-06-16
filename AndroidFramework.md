# Android Framework

### 1.请介绍一下 NDK？

快速开发C、 C++的动态库，并自动将so和应用一起打包成 APK

### 2.如何加载 ndk 库？如何在 jni 中注册 native 函数，有几种注册方式?【猎豹移动】
### 3.Android 进程分类？
前台进程、可视进程、服务进程、缓存进程。

前台进程（foreground process）：需要用户当前正在进行的操作。一般满足以下条件： 
1. 屏幕顶层运行Activity（处于onResume()状态），用户正与之交互 
2. 有BroadcastReceiver正在执行代码 
3. 有Service在其回调方法（onCreate()、onStart()、onDestroy()）中正在执行代码 
这种进程较少，一般来作为最后的手段来回收内存

可视进程（visible process）：做用户当前意识到的工作。一般满足以下条件： 
1. 屏幕上显示Activity，但不可操作（处于onPause()状态） 
2. 有service通过调用Service.startForeground()，作为一个前台服务运行 
3. 含有用户意识到的特定的服务，如动态壁纸、输入法等 
这些进程很重要，一般不会杀死，除非这样做可以使得所有前台进程存活。

服务进程（service process）：含有以startService()方法启动的service。虽然该进程用户不直接可见，但是它们一般做一些用户关注的事情（如数据的上传与下载）。 
这些进程一般不会杀死，除非系统内存不足以保持前台进程和可视进程的运行。 
对于长时间运行的service（如30分钟以上），系统会考虑将之降级为缓存进程，避免长时间运行导致内存泄漏或其他问题，占用过多RAM以至于系统无法分配充足资源给缓存进程。

缓存/后台进程（cached/background process）:一般来说包含以下条件： 
1. 包含多个Activity实例，但是都不可见（处于onStop()且已返回）。 
系统如有内存需要，可随意杀死。

### 4.谈谈对进程共享和线程安全的认识？
进程共享涉及到两个进程数据的交互，在数据交互过程，若出现需要按一定顺序更改数据时，这时候就出现线程安全的概念了，确保数据按一定的流程进行更改，避免出现数据紊乱等操作，所以线程安全当中 我们就引入了锁的概念，比如syncnized和viotlate

### 5.谈谈对多进程开发的理解以及多进程应用场景？

* 前言

Android利用重要性层次结构，就是将最重要的保留，杀掉不重要的进程，在系统内存不足的时候，有些模块成为较低等级的进程会被杀掉，但是，在业务场景当中，我们又不希望该业务模块被强制关掉，比如后台音乐播放，我们可以尝试下如何提高优先级

Android的Low Memory Killer基于Linux的OOM机制，Low Memory Killer会根据进程的adj级别以及所占的内存，来决定是否杀掉该进程，adj越大，占用内存越多，进程越容易被杀掉。

关于adj的分级，我们可以参考ProcessList.java，这里的常量定义了ADJ的分级。通过手机的查看，在内存达到某些程度时，低值的adj会被杀掉？

* 什么情况需要使用多进程
比如拿音乐播放为例，有多种方案为例
A. 在Activity中直接播放音乐。

B. 启动后台Service，播放音乐。

C. 启动前台Service，播放音乐。

D. 在新的进程中，启动后台Service，播放音乐。

E. 在新的进程中，启动前台Service，播放音乐。

通过比较，我们可以看到E的adj最高，保活的成功率最高

多进程还有一种非常有用的场景，就是多模块应用。比如我做的应用大而全，里面肯定会有很多模块，假如有地图模块、大图浏览、自定义WebView等等（这些都是吃内存大户），一个成熟的应用一定是多模块化的。首先多进程开发能为应用解决了OOM问题，因为Android对内存的限制是针对于进程的，所以，当我们需要加载大图之类的操作，可以在新的进程中去执行，避免主进程OOM。而且假如图片浏览进程打开了一个过大的图片，java heap 申请内存失败，该进程崩溃并不影响我主进程的使用。

### 6.什么是协程？

协程是一种按序写异步代码的方式。
协程基于暂停函数的想法：那些函数被调用之后可以终止（程序）执行，一旦完成他们自己的任务之后又可以让他（程序）继续执行。

### 7.逻辑地址与物理地址，为什么使用逻辑地址？

物理地址，CPU地址总线传来的地址，由硬件电路控制（现在这些硬件是可编程的了）其具体含义。

### 8.Android 为每个应用程序分配的内存大小是多少？

```
private void getMemoryInfo() {
    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
    manager.getMemoryInfo(info);  
    Log.e("Memory","系统总内存:"+(info.totalMem / (1024*1024))+"M");
    Log.e("Memory","系统剩余内存:"+(info.availMem / (1024*1024))+"M");
    Log.e("Memory","系统是否处于低内存运行："+info.lowMemory );
    Log.e("Memory","系统剩余内存低于"+( info.threshold  / (1024*1024))+"M时为低内存运行");
}
```

### 9.进程保活的方式？

https://segmentfault.com/a/1190000006251859


### 10.系统启动流程是什么？

### 11.一个应用程序安装到手机上的过程发生了什么？
### 12.App 启动流程，从点击桌面开始（Activity 启动流程）？
### 13.什么是 AIDL？解决了什么问题？如何使用？
### 14.Binder 机制及工作原理？
### 15.App 中唤醒其他进程的实现方式？
### 16.Activity、Window、View 三者的关系与区别？
### 17.ApplicationContext 和 ActivityContext 的区别？
### 18.ActivityThread，ActivityManagerService，WindowManagerService 的工作原理？
### 19.PackageManagerService 的工作原理？
### 20.PowerManagerService 的工作原理？
### 21.权限管理系统（底层的权限是如何进行 grant 的）？
### 22.操作系统中进程和线程有什么区别？系统在什么情况下会在用户态和内核态中切换？【猎豹移动】
### 23.如果一个 App 里面有多个进程存在，请列举你所知道的全部 IPC 方法。
