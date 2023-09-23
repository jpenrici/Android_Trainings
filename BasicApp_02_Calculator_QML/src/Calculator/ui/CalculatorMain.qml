import QtQuick
import QtQuick.Window


Window {
    width: 250
    height: 480
    visible: true
    color: "#cacaca"
    title: qsTr("Calculator")

    CalculatorScreen {
        width: parent.width
        height: parent.height
    }
}
