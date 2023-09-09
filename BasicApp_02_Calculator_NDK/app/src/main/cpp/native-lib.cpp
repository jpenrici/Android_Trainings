#include "calculator/Calculator.hpp"

#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_calculator_MainActivity_cpp_1calculate(JNIEnv *env, jobject /* this */, jstring expression) {
    const char* str = env->GetStringUTFChars(expression, nullptr);
    std::string result = Calculator::calculator(str);
    return env->NewStringUTF(result.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_calculator_MainActivity_cpp_1error(JNIEnv *env, jobject /* this */) {
    std::string result = Calculator::ERROR;
    return env->NewStringUTF(result.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_calculator_MainActivity_helloJNI(JNIEnv *env, jobject /* this */) {
    std::string result = "JNI running ...";
    return env->NewStringUTF(result.c_str());
}