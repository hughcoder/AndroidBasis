#include <jni.h>
#include <string>
#include <stdio.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_hugh_ffmpeg_CCMainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */thisObj) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
