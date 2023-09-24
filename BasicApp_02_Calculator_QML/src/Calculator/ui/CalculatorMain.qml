import QtQuick
import QtQuick.Window


Window {
    width : 2 * 64
    height: 4 * 64
    visible: true
    color: "#cacaca"
    title: qsTr("Calculator")

    CalculatorScreen {
        id: caluculatorScreen
        width: parent.width
        height: parent.height

        textHeight  : height / 10
        buttonWidth : width  /  4
        buttonHeight: height /  8
    }
}
