#ifndef QCALCULATOR_LOGIC_HPP_
#define QCALCULATOR_LOGIC_HPP_

#include <QObject>

#include "calculator/Calculator.hpp"


class CalculatorLogic : public QObject {

    Q_OBJECT
    Q_PROPERTY(QString result READ result WRITE calculate NOTIFY notify)
    Q_PROPERTY(QString error READ error NOTIFY notify)

public:

    explicit CalculatorLogic(QObject *qParent = nullptr) noexcept : QObject(qParent) {}
    CalculatorLogic(const CalculatorLogic &) = delete;
    CalculatorLogic(CalculatorLogic &&) = delete;
    auto operator=(const CalculatorLogic &) -> CalculatorLogic & = delete;
    auto operator=(CalculatorLogic &&) -> CalculatorLogic & = delete;

    ~CalculatorLogic() noexcept override = default;

    auto result() -> QString
    {
        return currentResult;
    }

    auto error() -> QString
    {
        return Calculator::ERROR;
    }

signals:
    void notify();

public slots:

    void calculate(QString expression)
    {
        currentResult = QString().fromStdString(Calculator::calculator(expression.toStdString()));
        emit notify();
    }

protected:
    QString currentResult{};
};

#endif
