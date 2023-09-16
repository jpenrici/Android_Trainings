#include "calculator/Calculator.hpp"

#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_calculator_ui_CalculatorViewModel_cppCalculate(JNIEnv *env, jobject /* this */, jstring expression) {
    const char* str = env->GetStringUTFChars(expression, nullptr);
    std::string result = Calculator::calculator(str);
    return env->NewStringUTF(result.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_calculator_ui_CalculatorViewModel_cppError(JNIEnv *env, jobject /* this */) {
    std::string result = Calculator::ERROR;
    return env->NewStringUTF(result.c_str());
}