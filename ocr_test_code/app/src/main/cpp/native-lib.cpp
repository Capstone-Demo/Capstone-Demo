#include <jni.h>
#include <string>
#include <opencv2/opencv.hpp>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_myopencvndk1_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
//    std::string hello = "Hello from C++";
//    return env->NewStringUTF(hello.c_str());

    std::string cv_ver = "CV Version is" CV_VERSION;
    return env->NewStringUTF(cv_ver.c_str());
}