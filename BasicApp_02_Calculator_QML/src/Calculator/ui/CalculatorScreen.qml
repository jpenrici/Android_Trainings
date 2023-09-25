import QtQuick

import CalculatorLogic


Column  {
    id: calculator

    property real textHeight     : 64
    property real buttonWidth    : 64
    property real buttonHeight   : 64
    property string colorNumber  : "#2E2E2E"
    property string colorOperator: "#5F9BC8"
    property string colorOthers  : "#818181"
    property string fontFamily   :  "Arial"

    width : 4 * 64
    height: 8 * 64

    TextEdit {
        id: displayState
        width: parent.width
        height: textHeight
        color: "#0000ff"
        text: qsTr("Welcome!")
        font.family: fontFamily
        font.pointSize: textHeight / 3
        horizontalAlignment: Text.AlignLeft
        verticalAlignment: Text.AlignVCenter
        readOnly: true
        textMargin: 5
    }

    TextEdit {
        id: displayResult
        width: parent.width
        height: textHeight
        text: qsTr("")
        font.family: fontFamily
        font.pointSize: textHeight
        horizontalAlignment: Text.AlignRight
        verticalAlignment: Text.AlignVCenter
        readOnly: true
        textMargin: 5
    }

    TextEdit {
        id: displayExpression
        width: parent.width
        height: textHeight
        text: qsTr("")
        font.family: fontFamily
        font.pointSize: textHeight
        horizontalAlignment: Text.AlignRight
        verticalAlignment: Text.AlignVCenter
        readOnly: true
        textMargin: 5
    }

    Grid {
        id: keys
        rows   : 5
        columns: 4
        width: parent.width

        CalculatorButton {
            symbol: "("
            width: buttonWidth
            height: buttonHeight
            backgroundColor: colorNumber
            onClicked: displayExpression.text += " ( "
        }

        CalculatorButton {
            symbol: ")"
            width: buttonWidth
            height: buttonHeight
            backgroundColor: colorNumber
            onClicked: displayExpression.text += " ) "
        }

        CalculatorButton {
            symbol: "CE"
            width: buttonWidth
            height: buttonHeight
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
            width: buttonWidth
            height: buttonHeight
            backgroundColor: colorOthers
            onClicked: {
                displayExpression.text = "";
                displayResult.text = "";
                displayState.text = "";
            }
        }

        CalculatorButton {
            symbol: "7"
            width: buttonWidth
            height: buttonHeight
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: "8"
            width: buttonWidth
            height: buttonHeight
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: "9"
            width: buttonWidth
            height: buttonHeight
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: "/"
            width: buttonWidth
            height: buttonHeight
            backgroundColor: colorOperator
            onClicked: displayExpression.text += " / "
        }

        CalculatorButton {
            symbol: "4"
            width: buttonWidth
            height: buttonHeight
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: "5"
            width: buttonWidth
            height: buttonHeight
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: "6"
            width: buttonWidth
            height: buttonHeight
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: "x"
            width: buttonWidth
            height: buttonHeight
            backgroundColor: colorOperator
            onClicked: displayExpression.text += " * "
        }

        CalculatorButton {
            symbol: "1"
            width: buttonWidth
            height: buttonHeight
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: "2"
            width: buttonWidth
            height: buttonHeight
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: "3"
            width: buttonWidth
            height: buttonHeight
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: "-"
            width: buttonWidth
            height: buttonHeight
            backgroundColor: colorOperator
            onClicked: displayExpression.text += " - "
        }

        CalculatorButton {
            symbol: "0"
            width: buttonWidth
            height: buttonHeight
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: ","
            width: buttonWidth
            height: buttonHeight
            backgroundColor: colorNumber
            onClicked: (label) => displayExpression.text += label
        }

        CalculatorButton {
            symbol: "="
            width: buttonWidth
            height: buttonHeight
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
            width: buttonWidth
            height: buttonHeight
            backgroundColor: colorOperator
            onClicked: displayExpression.text += " + "
        }
    }
}
