# Android知识点的各种尝试 验证


## 生命周期理一理

### 1.生命周期

第一个Activity
                                     (正常情况走下一个Activity) -> 2onC ->2onStart->2onResume ->1onStop (这样确保能有一个在前台)
onC - onStart - onResume   -> onPause
                                     (异常情况横竖切换)->onSave->onstop->ondestroy->onCrete ->onRestrore->onStart->onResume


### 2tip
onStart、onResume ---- 是否可见这个角度
onPause、onStop ---- 是否位于前台这个角度


### 资源内存不足导致优先级的Activity被杀死
前台Activity ---正在和用户交互的Activity
可见但非前台Activity ----比如Activity弹了一个dialog，导致Activity可见但是位于后台无法和用户交互
后台Activity ----已经被暂停的Activity,比如执行onStop

### 防止Activity在旋转时被重新创建  需要 orientation(旋转) || screenSize(防止被重新创建)
