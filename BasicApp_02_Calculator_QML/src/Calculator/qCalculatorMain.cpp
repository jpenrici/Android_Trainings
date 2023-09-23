#include <QGuiApplication>
#include <QQmlApplicationEngine>
#include <QQmlContext>

#include "qCalculatorLogic.hpp"


auto main(int argc, char *argv[]) -> int
{
    QGuiApplication app(argc, argv);

    QQmlApplicationEngine engine;
    const QUrl url(u"qrc:/Calculator/ui/CalculatorMain.qml"_qs);
    QObject::connect(&engine, &QQmlApplicationEngine::objectCreationFailed,
    &app, []() {
        QCoreApplication::exit(-1);
    },
    Qt::QueuedConnection);

    qmlRegisterType<CalculatorLogic>("CalculatorLogic", 1, 0, "CalculatorLogic");
    engine.rootContext()->setContextProperty("calculatorLogic", new CalculatorLogic);
    engine.load(url);

    return app.exec();
}
