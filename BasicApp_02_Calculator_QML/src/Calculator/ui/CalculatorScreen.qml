import QtQuick
import CalculatorLogic


Column  {
    id: calculator

    property int buttonSize: 56
    property string colorNumber:  "#2E2E2E"
    property string colorOperator:"#5F9BC8"
    property string colorOthers:  "#818181"
    property string fontFamily:   "Arial"

    width: parent.width
    height: parent.height

    TextEdit {
        id: displayState
        width: parent.width
        height: 32
        color: "#0000ff"
        text: "Welcome!"
        font.family: fontFamily
        font.pointSize: 16
        horizontalAlignment: Text.AlignLeft
        verticalAlignment: Text.AlignVCenter
        readOnly: true
        textMargin: 5
    }

    TextEdit {
        id: displayResult
        width: parent.width
        height: 48
        text: ""
        font.family: fontFamily
        font.pointSize: 24
        horizontalAlignment: Text.AlignRight
        verticalAlignment: Text.AlignVCenter
        readOnly: true
        textMargin: 5
    }

    TextEdit {
        id: displayExpression
        width: parent.width
        height: 56
        text: ""
        font.family: fontFamily
        font.pixelSize: 28
        horizontalAlignment: Text.AlignRight
        verticalAlignment: Text.AlignVCenter
        readOnly: true
        textMargin: 5
    }

    Grid {
        rows: 5
        columns: 4
        spacing: 6
        padding: 4
        topPadding: 8

        CalculatorButton {
            symbol: "("
            size: buttonSize
            backgroundColor: colorNumber
            onClicked: displayExpression.text += " ( "
        }

        CalculatorButton {
            symbol: ")"
            size: buttonSize
            backgroundColor: colorNumber
            onClicked: displayExpression.text += " ) "
        }

        CalculatorButton {
            symbol: "CE"
            size: buttonSize
            backgroundColor: colorOthers
            onClicked: {
                displayState.text = "";
                let str = displayExpression.text;
                if (str[str.length - 1] === " ") {
                    str = str.substring(0, str.length - 2);
                }
                displayExpression.text = str.substring(0, str.length - 1);
            }
        }

        CalculatorButton {
            symbol: "AC"
            size: buttonSize
            backgroundColor: colorOthers
            onClicked: {
                displayExpression.text = "";
                displayResult.text = "";
                displayState.text = "";
            }
        }

        CalculatorButton {
            symbol: "7"
            size: buttonSize
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: "8"
            size: buttonSize
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: "9"
            size: buttonSize
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: "/"
            size: buttonSize
            backgroundColor: colorOperator
            onClicked: displayExpression.text += " / "
        }

        CalculatorButton {
            symbol: "4"
            size: buttonSize
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: "5"
            size: buttonSize
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: "6"
            size: buttonSize
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: "x"
            size: buttonSize
            backgroundColor: colorOperator
            onClicked: displayExpression.text += " * "
        }

        CalculatorButton {
            symbol: "1"
            size: buttonSize
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: "2"
            size: buttonSize
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: "3"
            size: buttonSize
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: "-"
            size: buttonSize
            backgroundColor: colorOperator
            onClicked: displayExpression.text += " - "
        }

        CalculatorButton {
            symbol: "0"
            size: buttonSize
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: ","
            size: buttonSize
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: "="
            size: buttonSize
            backgroundColor: colorOperator
            onClicked: {
                // Prepare
                let expression = displayExpression.text;
                let lastResult = displayResult.text;
                let newExpression = lastResult + expression;
                if (lastResult) {
                    if (!expression.startsWith(" +") && !expression.startsWith(" -")
                        && !expression.startsWith(" *") && !expression.startsWith(" /")
                    ) {
                        newExpression += "?"
                    }
                }
                // Calculate
                calculatorLogic.calculate(newExpression);
                let result = calculatorLogic.result;
                // Update
                if (result === calculatorLogic.error) {
                    displayState.color = "#FF0000";
                    displayState.text = "Error!";
                } else {
                    displayExpression.text = "";
                    displayResult.text = result;
                    displayState.text = "";
                }
                // Log
                console.log("App Calculator Expression: ", newExpression, " Result: ", result);
            }
        }

        CalculatorButton {
            symbol: "+"
            size: buttonSize
            backgroundColor: colorOperator
            onClicked: displayExpression.text += " + "
        }
    }
}
