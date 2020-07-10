#include <jni.h>

extern "C" {
#include <libavcodec/avcodec.h>
#include <libavformat/avformat.h>
#include <libavfilter/avfilter.h>
#include <libavcodec/jni.h>
}


/**
 * 拿到 ffmpeg 当前版本
 * @return
 */
const char *getFFmpegVer() {
    return av_version_info();
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_hugh_ffmpegtest_MainActivity_getFFmpegVersion(JNIEnv *env, jclass type) {
    return env->NewStringUTF(getFFmpegVer());
}
