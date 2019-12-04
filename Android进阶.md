# Android进阶

## Camera2 使用

* CameraManager：摄像头管理器。这是一个全新的系统管理器，专门用于检测系统摄像头、打开系统摄像头。除此之外，调用CameraManager的getCameraCharacteristics(String)方法即可获取指定摄像头的相关特性。
* CameraCharacteristics：摄像头特性。该对象通过CameraManager来获取，用于描述特定摄像头所支持的各种特性。
* CameraDevice：代表系统摄像头。该类的功能类似于早期的Camera类。
* CameraCaptureSession：这是一个非常重要的API，当程序需要预览、拍照时，都需要先通过该类的实例创建Session。而且不管预览还是拍照，也都是由该对象的方法进行控制的，其中控制预览的方法为setRepeatingRequest()；控制拍照的方法为capture()。
* CameraRequest和CameraRequest.Builder：当程序调用setRepeatingRequest()方法进行预览时，或调用capture()方法进行拍照时，都需要传入CameraRequest参数。CameraRequest代表了一次捕获请求，用于描述捕获图片的各种参数设置，比如对焦模式、曝光模式……总之，程序需要对照片所做的各种控制，都通过CameraRequest参数进行设置。CameraRequest.Builder则负责生成CameraRequest对象。

