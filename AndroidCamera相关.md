# Android-Camera

#### Camera.getParameters()

获取相机的访问权限后，您可以使用 Camera.getParameters() 方法获取有关相机功能的详细信息，还可以检查返回的 Camera.Parameters 对象，以获取受支持的功能。当使用 API 级别 9 或更高级别时，请使用 Camera.getCameraInfo() 确定设备的摄像头是前置还是后置，以及图像的屏幕方向。

#### onPreviewFrame
称为显示预览帧。此回调是在事件线程camero .open(int)被调用时调用的。

如果使用ImageFormat。YV12格式，请参阅相机中的方程式。参数#setPreviewFormat，用于在预览回调缓冲区中安排像素数据。

#### FaceDetector

创建一个FaceDetector，配置与大小的图像到
进行分析，并找出能探测到的最大面数。
一旦对象被构造，这些参数就不能被改变。
注意图像的宽度必须是均匀的。