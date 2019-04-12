# Android知识点的各种尝试 验证


## 生命周期理一理

### 1.生命周期

第一个Activity

* onCreate - onStart - onResume   -> onPause
* -> 一、(正常情况走下一个Activity) -> 2onC ->2onStart->2onResume ->1onStop   ---- (这样确保能有一个在前台)
* -> 二、(异常情况横竖切换)->onSaveInstanceState->onstop->ondestroy->onCrete ->onRestroreState->onStart->onResume


### 2tip
onStart、onResume ---- 是否可见这个角度
onPause、onStop ---- 是否位于前台这个角度


### 资源内存不足导致优先级的Activity被杀死
前台Activity ---正在和用户交互的Activity
可见但非前台Activity ----比如Activity弹了一个dialog，导致Activity可见但是位于后台无法和用户交互
后台Activity ----已经被暂停的Activity,比如执行onStop

### 防止Activity在旋转时被重新创建  需要 orientation(旋转) || screenSize(防止被重新创建)

## 启动模式

1.standard
2.singleTop
3.singleTask   -- 单实例模式
4.singleInstance



任务栈 ：  1、前台任务栈  2、后台任务栈


## IPC机制 binder理一理

### 1.多进程

```
     <activity android:name=".SecondActivity"
            android:label="@string/app_name"
            android:process=":remote"
            />
        <activity android:name=".ThirdActivity"
            android:label="@string/app_name"
            android:process=":com.hugh.remote"
            />

```
u0_a522   14878 762   1763400 62220 SyS_epoll_ 0000000000 S com.hugh.basis
u0_a522   15247 762   1760024 57208 SyS_epoll_ 0000000000 S com.hugh.basis:remote
u0_a522   15273 762   1775416 51876 SyS_epoll_ 0000000000 S com.hugh.basis:com.hugh.remote
进程名以 ':'开头的进程属于当前应用的私有进程

### 2.Parcelable  和  Serializable

区别 ：         平台  、开销  、使用 、效率 、维护

Serializable   java    大     简单  低   难       ------>需要考虑serialID 去序列化和反序列化

Parcelable     android  小    麻烦  高   简单

### 3.ICP跨进程的通信方式
1. Bundle(实现Parcelable序列化)  ---只能传输Bundle支持的数据类型，适合四大组件之间的通信
2. 文件读写（注意并发操作，不适合高并发操作）
3.AIDL        -------多AIDL 对 一Service需要用到binder池，主要原理多开一个接口 queryBinder去返回不同的Binder对象，处理不同的操作方法
4.Messanger(其实是对AIDL的封装)
5.ContentProvider(四大组件之一 ，主要提供数据源的CRUD操作)  ---也可以用于一对多的进程通信
6.socket（客户端与服务端监听同一端口） 适用网络数据交换


## 4.View 滑动相关

```
 1.               button3.scrollBy(300,200);
 
 2.               ObjectAnimator.ofFloat(button3,"translationX",0,100).setDuration(500).start();
 
 3.               ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) button3.getLayoutParams();
                params.width += 100;
                params.leftMargin += 100;
                button3.requestLayout();
```

### 4.1 MeasureSpec

### 首先理解MeasureSpec,是什么？
1. SpecMode ----测量模式
2. SpecSize ----测量大小

MeasureSpec 是一个32位的int型值，前2位代表测量模式，后30位代表测量大小,


#### 区分MeasureSpec 与layoutParams的关系？
我们不禁要想 MeasureSpec 和layoutParams到底是什么关系呢？具体看源码其实我们可以看到两者的对应关系，根据case： layoutParams来决定
不同的MeasureSpec，
测量模式有一下3种
1 unspecified  这个用于系统内部测量的，父View不对子View进行限制
2 Exactly 这种测量模式意思子类有具体的值，对应我们的 layoutParams match_parent 和具体数值dp
3 at-most 指父元素给定一定的大小，子元素不能超过这个大小，这个对应我们的 layoutParams wrap-content

有了测量模式和测量大小，就能测量出子View具体的大小了
这边知道了大致关系，下面我们就可以看到是怎么通过 layoutParams 得到 MeasureSpec 

#### 区分DecorView 和 普通view（只有DecorView外层直接是窗口）如何得到自身的MeasureSpec？
这里要做个区分 有一个比较特殊的View，他是没有父元素的
1. DecorView
DecorView是Android最外层的View，他本质是一个FrameLayout,他的MeasureSpec 是根据他自身 layoutParams和窗口的大小来决定的

2.其他View
其他View的 MeasureSpec 根据 '父容器的 MeasureSpec' 和 '自身的layoutParams' 来决定
看下面的代码

```
   public static int getChildMeasureSpec(int spec, int padding, int childDimension) {
        int specMode = MeasureSpec.getMode(spec);
        int specSize = MeasureSpec.getSize(spec);

        int size = Math.max(0, specSize - padding);

        int resultSize = 0;
        int resultMode = 0;

        switch (specMode) {
        // Parent has imposed an exact size on us
        case MeasureSpec.EXACTLY:
            if (childDimension >= 0) {
                resultSize = childDimension;
                resultMode = MeasureSpec.EXACTLY;
            } else if (childDimension == LayoutParams.MATCH_PARENT) {
                // Child wants to be our size. So be it.
                resultSize = size;
                resultMode = MeasureSpec.EXACTLY;
            } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                // Child wants to determine its own size. It can't be
                // bigger than us.
                resultSize = size;
                resultMode = MeasureSpec.AT_MOST;
            }
            break;
            ……
            }
```
首先根据 父元素的MeasureSpec 判断，在根据自身的 LayoutParams判断，得出测量模式 和测量大小

 
### 4.2View measure layout draw

#### measure过程 ,区分最小颗粒View和ViewGroup的测量过程
在整个测量过程当中，会用到我们上面理解的MeasureSpec
measure过程确定View的测量宽、高
measure过程也要分情况看，
1. 如果只有一个原始的View，那么通过 measure方法就完成其测量过程
View 会先调用 onMeasure()方法

```
  setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
```

接下来看下getDefaultSize(),它会将我们上次搞得 measureSpec的值拿过来
拿到测量模式和测量大小 mode 和 size

```
    public static int getDefaultSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
        case MeasureSpec.UNSPECIFIED:
            result = size;
            break;
        case MeasureSpec.AT_MOST:
        case MeasureSpec.EXACTLY:
            result = specSize;
            break;
        }
        return result;
    }

```

getSuggestedMinimumWidth() 是怎么传size的，size分别是宽、高，这里面就用到了我们在布局文件的 mMinwdith和背景图片（drawable）的大小


```
  protected int getSuggestedMinimumWidth() {
        return (mBackground == null) ? mMinWidth : max(mMinWidth, mBackground.getMinimumWidth());
    }
```

#### tip
我们可以看到View的宽高就是由specSize决定，我们在自定义View的时候需要重写onMeasure方法并设置wrap_content时的自身大小，否在在布局中适用
wrap_content就相当于适用 march_parent，这个根据之前的测量模式关系对应能得出结论，具体根据业务需要来控制大小，比如给一个内部的宽高比，通过
调用setMeasureDimension()来完成

2. 如果是一个ViewGroup，除了完成自己的测量过程外，还会遍历去调用所有子元素的measure方法

它提供了一个 measureChildren 方法

```
   protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
        final int size = mChildrenCount;
        final View[] children = mChildren;
        for (int i = 0; i < size; ++i) {
            final View child = children[i];
            if ((child.mViewFlags & VISIBILITY_MASK) != GONE) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            }
        }
    }
```

measureChild 做的事情主要是取出子元素的 layoutParams，然后在通过getChildMeasureSpec 来创建子元素的 MeasureSpec，接着将MeasureSpec
直接传递给View的 measure方法

//简单来说就是获取子元素的measureSpec 然后让子元素去测量自己，子元素自己去进入measure过程 

```
 protected void measureChild(View child, int parentWidthMeasureSpec,
            int parentHeightMeasureSpec) {
        final LayoutParams lp = child.getLayoutParams();

        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                mPaddingLeft + mPaddingRight, lp.width);
        final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
                mPaddingTop + mPaddingBottom, lp.height);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }
```

父元素的onMeasure过程都都有所差异 所以不像View能有一个统一的方法，因为每种的布局方式不同，比如linearLayout 和RelativeLayout
这里简要概括下linearLayout的测量情况

1. 子元素 measureSpec 去测量 子元素的大小
2. 子元素的宽高得到，父元素再去测量自己




#### 4.3tips
 一. 因为View的measure过程和Activity的生命周期方法不是同步执行的，因此无法保证Activity执行了onCreate、onStart、onResume 时某个View已经测量
 完毕了，如果View还没有测量完毕，那么获得的宽高就是0，下面有4中方法来解决这个问题。
 
 * 1 在Activity的 onWindowFocusChanged 里去获取
 
 ```
     @Override
     public void onWindowFocusChanged(boolean hasFocus) {
         super.onWindowFocusChanged(hasFocus);
         if (hasFocus) {
             int width = button.getMeasuredWidth();
             int height = button.getMeasuredHeight();
             Log.e("a", width + "");
             Log.e("a", height + "");
         }
     }
```

 * 2 view.post(runnable)
 
 ```
      button.post(new Runnable() {
            @Override
            public void run() {
                int width = button.getMeasuredWidth();
                int height = button.getMeasuredHeight();
                Log.e("b", width + "");
                Log.e("b", height + "");
            }
        });
```
 
 * 3 ViewTreeObserver
 
 ```
    ViewTreeObserver observer = button.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                button.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = button.getMeasuredWidth();
                int height = button.getMeasuredHeight();
                Log.e("c", width + "");
                Log.e("c", height + "");
            }
        });
```

* 4. 如果子View是 wrap-content 或者 具体数值的话

具体的数值

```
       int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(dip2px(this,100), View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(dip2px(this,100), View.MeasureSpec.EXACTLY);
        button.measure(widthMeasureSpec, heightMeasureSpec);
```
 
 wrap-content
 
 ```
  //如果是wrapContent的话
      //   makeMeasureSpec()  传入测量大小 ，测量模式
         int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 << 30) -1, View.MeasureSpec.AT_MOST);
         int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 << 30) -1, View.MeasureSpec.AT_MOST);
         button.measure(widthMeasureSpec, heightMeasureSpec);
```

#### layout过程

* layout的作用是ViewGroup用来确定子元素的位置 

ViewGroup提供的抽象方法 强制子类复写

```
  @Override
    protected abstract void onLayout(boolean changed,
            int l, int t, int r, int b);

```

这边一垂直的LinerLayout为例

1. 父元素先完成自己的定位后
2. 再遍历子元素的layout,子元素通过自己的layout方法完成自己的定位
3. 从上往下一层层传递下去，从上先摆好位置，先摆外面 在摆内部。




#### draw过程

* 1.绘制背景 background.draw(canvas)
* 2.绘制自己(onDraw)
* 3.绘制children(dispatchDraw)
* 4.绘制装饰(onDrawScrollBars)

View 绘制的过程是通过dispatchDraw来实现的，dispatchDraw会遍历调用所有元素的draw方法，draw的事件就一层层传递了下去。

#### tips
如果当前View不想绘制任何内容，可以使用 `setWillNotDraw` 来优化

#### 自定义View 自定义ViewGroup 参考CircleView 和 HorizontalScrollView


## 7Android动画相关

Android动画分为3种
1.View动画
2.帧动画
3.属性动画


### 7.1 View动画

* 平移动画
* 缩放动画
* 旋转动画
* 透明度动画





