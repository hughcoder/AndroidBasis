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
### 6.什么是协程？
### 7.逻辑地址与物理地址，为什么使用逻辑地址？
### 8.Android 为每个应用程序分配的内存大小是多少？
### 9.进程保活的方式？
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
