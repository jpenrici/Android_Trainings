cmake_minimum_required(VERSION 3.22.1)

project("calculatorLib")

add_library(${CMAKE_PROJECT_NAME} SHARED
    calculator/Calculator.hpp
    calculator/Calculator.cpp
    native-lib.cpp)

target_link_libraries(${CMAKE_PROJECT_NAME}
    android
    log)
