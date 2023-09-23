cmake_minimum_required(VERSION 3.16)

project(Calculator VERSION 0.1 LANGUAGES CXX)

set(APP "app${PROJECT_NAME}")

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

find_package(Qt6 6.4 REQUIRED COMPONENTS Quick)

qt_standard_project_setup(REQUIRES 6.4)

qt_add_executable(${APP}
    qCalculatorMain.cpp
    qCalculatorLogic.hpp qCalculatorLogic.cpp
    calculator/Calculator.hpp  calculator/Calculator.cpp
)

qt_add_qml_module(${APP}
    URI ${PROJECT_NAME}
    VERSION 1.0
    QML_FILES
        ui/CalculatorMain.qml
        ui/CalculatorButton.qml
        ui/CalculatorScreen.qml
)

set_target_properties(${APP} PROPERTIES
    MACOSX_BUNDLE_BUNDLE_VERSION ${PROJECT_VERSION}
    MACOSX_BUNDLE_SHORT_VERSION_STRING ${PROJECT_VERSION_MAJOR}.${PROJECT_VERSION_MINOR}
    MACOSX_BUNDLE TRUE
    WIN32_EXECUTABLE TRUE
)

target_link_libraries(${APP}
    PRIVATE Qt6::Quick
)

include(GNUInstallDirs)
install(TARGETS ${APP}
    BUNDLE DESTINATION .
    LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}
    RUNTIME DESTINATION ${CMAKE_INSTALL_BINDIR}
)
