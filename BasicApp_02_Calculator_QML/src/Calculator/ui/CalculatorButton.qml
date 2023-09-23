import QtQuick


Rectangle {
    property string symbol: "()"
    property color backgroundColor: "#000000"
    property color textColor: "#FFFFFF"
    property string fontFamily: "Arial"
    property int size: 96

    id: button
    width: size
    height: size
    color: backgroundColor
    border.color: "#00000000"
    radius: 15

    signal clicked(string label)

    Text {
        id: label
        width: parent.width
        height: parent.height
        color: textColor
        text: qsTr(symbol)
        horizontalAlignment: Text.AlignHCenter
        verticalAlignment: Text.AlignVCenter
        font.family: fontFamily
        font.pointSize: size / symbol.length * 0.5
    }

    SequentialAnimation {
        id: buttonAnimation
        PropertyAnimation {
            target: button
            property: "color"
            to: "white"
            duration: 100
        }
        PropertyAnimation {
            target: button
            property: "color"
            to: backgroundColor
            duration: 100
        }
    }

    MouseArea {
        id: mouseArea
        width: parent.width
        height: parent.height

        onClicked: {
            button.clicked(symbol);
            buttonAnimation.running = true;
            //console.log(symbol + " clicked");
        }
    }
}
