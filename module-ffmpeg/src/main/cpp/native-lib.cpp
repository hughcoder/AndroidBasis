#include <jni.h>
#include <string>
#include <stdio.h>
#include <android/log.h>
#include <stdlib.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_hugh_ffmpeg_CCMainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */thisObj) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

//C/C++访问Java的成员
//访问非静态属性
//如果发生该错误：member reference type 'JNIEnv' (aka '_JNIEnv') is not a pointer; did you mean...
// 举个例子 C语言的写法      jfieldID  jfID = (*env)->GetFieldID(env, jcla, "mTestStr", "Ljava/lang/String;");
// C++的写法          jfieldID  jfID = env->GetFieldID(jcla, "mTestStr", "Ljava/lang/String;");

extern "C" JNIEXPORT void JNICALL
Java_com_hugh_ffmpeg_CCMainActivity_accessField(JNIEnv *env, jobject jobj) {
    //得到jclass
    jclass jcla = env->GetObjectClass(jobj);
    //得到jfieldID，最后一个参数是签名，String对应的签名是Ljava/lang/String;(注意最后的分号)
    jfieldID  jfID = env->GetFieldID(jcla, "mTestStr", "Ljava/lang/String;");
    //得到key属性的值jstring
    jstring jstr = (jstring)env->GetObjectField(jobj,jfID);
    //jstring转化为C中的char*
    const char* oriText = env->GetStringUTFChars(jstr, NULL);
    //拼接得到新的字符串text="ddd good"
    char text[20] = "ddd ";
    strcat(text, oriText);
    //C中的char*转化为JNI中的jstring
    jstring jstrMod = env->NewStringUTF(text);
    //修改key
    env->SetObjectField(jobj, jfID, jstrMod);
    //只要使用了GetStringUTFChars，就需要释放
    env->ReleaseStringUTFChars(jstr,oriText);
}

//访问静态属性
extern "C" JNIEXPORT void JNICALL
Java_com_hugh_ffmpeg_CCMainActivity_accessStaticField(JNIEnv *env, jobject jobj) {
    //得到jclass
    jclass jcla = env->GetObjectClass( jobj);
    //得到jfieldID
    jfieldID jfid = env->GetStaticFieldID(jcla, "mTestStaticCount", "I");
    //得到静态属性的值mTestStaticCount
    jint count = env->GetStaticIntField(jcla, jfid);
    //自增
    count++;
    //修改mTestStaticCount的值
    env->SetStaticIntField( jcla, jfid, count);
}

//访问方法
extern "C" JNIEXPORT void JNICALL
Java_com_hugh_ffmpeg_CCMainActivity_handleMethod(JNIEnv *env, jobject jobj) {
    //得到jclass
    jclass jcla = env->GetObjectClass(jobj);
    //得到jmethodID
    jmethodID jmid = env->GetMethodID(jcla, "getIntValue", "()I");
    //调用java方法获取返回值，第四个参数100表示传入到java方法中的值
    jint jRandom = env->CallIntMethod(jobj, jmid);
    //可以在Android Studio中Logcat显示，需要定义头文件#ffmpeg.include <android/log.h>
    __android_log_print(ANDROID_LOG_DEBUG, "system.out", "getIntValue：%ld", jRandom);
}

//访问静态String方法
extern "C" JNIEXPORT void JNICALL
Java_com_hugh_ffmpeg_CCMainActivity_handleStaticMethod(JNIEnv *env, jobject jobj) {
    //得到jclass
    jclass jcla = env->GetObjectClass(jobj);
    //得到jmethodID
    jmethodID jmid = env->GetStaticMethodID(jcla, "getStaticStr", "()Ljava/lang/String;");
    //调用java方法获取返回值，第四个参数100表示传入到java方法中的值
    jstring jstring1 = (jstring)env->CallStaticObjectMethod(jcla, jmid);
    //可以在Android Studio中Logcat显示，需要定义头文件#ffmpeg.include <android/log.h>
    __android_log_print(ANDROID_LOG_DEBUG, "system.out", "getStaticStr：%ld", jstring1);
}

//调用对象的方法
extern "C" JNIEXPORT void JNICALL
Java_com_hugh_ffmpeg_CCMainActivity_accessClassMethod(JNIEnv *env, jobject jobj) {
    //得到MainActivity对应的jclass
    jclass  jcla = env->GetObjectClass(jobj);
    //得到xiaoming对象属性对应的jfieldID
    jfieldID jfid = env->GetFieldID( jcla, "xiaoming", "Lcom/hugh/ffmpeg/jnizz/People;");   //这里必须是父类对象的签名，否则会报NoSuchFieldError，因为Java中是父类引用指向子类对象
    //得到xiaoming对象属性对应的jobject
    jobject animalObj = env->GetObjectField( jobj, jfid);
//    jclass animalCla =(*env)->GetObjectClass(env, animalObj); //这种方式，下面调用CallNonvirtualVoidMethod会执行子类的方法
    //找到xiaoming对象对应的jclass
    jclass animalCla = env->FindClass("com/hugh/ffmpeg/jnizz/People");   //如果这里写成子类的全类名，下面调用CallNonvirtualVoidMethod会执行子类的方法
    //得到getName对应的jmethodID
    jmethodID eatID = env->GetMethodID(animalCla, "getName", "()Ljava/lang/String;");
//    (*env)->CallVoidMethod(env, animalCla, eatID);      //这样调用会报错
    //调用对象的方法
    env->CallNonvirtualObjectMethod(animalObj, animalCla, eatID);     //输出父类的方法
}

 int commpare(const int* a, const int* b) {
     return ( * ( int * ) a - * ( int * ) b) ;
}


//传入数组
extern "C" JNIEXPORT void JNICALL
Java_com_hugh_ffmpeg_CCMainActivity_putArray(JNIEnv *env, jobject jobj, jintArray arr_) {
    //jintArray --> jint指针 --> C int 数组
    jint *arr = env->GetIntArrayElements( arr_, NULL);
    //数组的长度
    jint arrLength = env->GetArrayLength(arr_);
    //排序
//    qsort(arr, arrLength, sizeof(jint), commpare);

    //同步
    //0：Java数组进行更新，并且释放C/C++数组
    //JNI_ABORT：Java数组不进行更新，但是释放C/C++数组
    //JNI_COMMIT：Java数组进行更新，不释放C/C++数组(函数执行完后，数组还是会释放的)
    env->ReleaseIntArrayElements( arr_, arr, JNI_COMMIT);
}


//返回数组
extern "C" JNIEXPORT jintArray JNICALL
Java_com_hugh_ffmpeg_CCMainActivity_getArray(JNIEnv *env, jobject jobj, jint arrLength) {
    //创建一个指定大小的数组
    jintArray  array = env->NewIntArray( arrLength);
    jint* elementp = env->GetIntArrayElements(array, NULL);
    jint* startP = elementp;
    int i = 0;
    for (; startP < elementp + arrLength; startP++) {
        (*startP) = i;
        i++;
    }
    //同步，如果没有同步Java层打印出来的数组里面的各个值为0
    env->ReleaseIntArrayElements( array, elementp, 0);
    return array;
}

//全局引用
//共享(可以跨多个线程)，手动控制内存使用

//创建
jstring jstr;
extern "C" JNIEXPORT void JNICALL
Java_com_hugh_ffmpeg_CCMainActivity_createGlobalRef(JNIEnv *env, jobject instance) {
    jstring obj = env->NewStringUTF( "people");
    jstr =(jstring)env->NewGlobalRef(obj);
}

//获得
extern "C" JNIEXPORT jstring JNICALL
Java_com_hugh_ffmpeg_CCMainActivity_getGlobalRef(JNIEnv *env, jobject instance) {
    return jstr;
}

//释放
extern "C" JNIEXPORT void JNICALL
Java_com_hugh_ffmpeg_CCMainActivity_deleteGlobalRef(JNIEnv *env, jobject instance) {
    env->DeleteGlobalRef(jstr);
}

//C++静态变量
extern "C" JNIEXPORT void JNICALL
Java_com_hugh_ffmpeg_CCMainActivity_staticRef(JNIEnv *env, jobject jobj) {
    jclass jcla = env->GetObjectClass( jobj);
    //局部静态变量，作用域当然是函数中 static效果和android一样
    //在第一次调用函数的时候会初始化，函数结束，但是它的值还会存在内存当中（只有当程序结束了才会销毁），只会声明一次
    static jfieldID jfid = NULL;   //如果加了static修饰，下面就只有一个打印，如果没加，for循环执行了多少次就打印多少次，这里是10次
    if (jfid == NULL) {
        jfid = env->GetFieldID(jcla, "mTestStr", "Ljava/lang/String;");
        __android_log_print(ANDROID_LOG_DEBUG, "system.out", "加了static");
    }
}

extern "C" JNIEXPORT void JNICALL
Java_com_hugh_ffmpeg_CCMainActivity_NostaticRef(JNIEnv *env, jobject jobj) {
    jclass jcla = env->GetObjectClass( jobj);
     //这边如果不用static关键词
     jfieldID jfid = NULL;
    if (jfid == NULL) {
        jfid = env->GetFieldID(jcla, "mTestStr", "Ljava/lang/String;");
        __android_log_print(ANDROID_LOG_DEBUG, "system.out", "不加static");
    }
}