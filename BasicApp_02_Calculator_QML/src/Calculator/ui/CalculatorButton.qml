import QtQuick


Rectangle {
    property string symbol        : "#"
    property string fontFamily    : "Arial"
    property color backgroundColor: "#000000"
    property color foregroundColor: "#FFFFFF"

    id: button
    width:  64
    height: 64
    radius: 32
    color:  backgroundColor
    border.color: "#00000000"

    signal clicked(string label)

    Text {
        id: label
        width:  parent.width
        height: parent.height
        text:   qsTr(symbol)
        color:  foregroundColor
        horizontalAlignment: Text.AlignHCenter
        verticalAlignment:   Text.AlignVCenter
        font.family: fontFamily
        font.pointSize: (width > height ? height : width) * 0.5
    }

    SequentialAnimation {
        id: buttonAnimation
        PropertyAnimation {
            target: button
            property: "color"
            to: foregroundColor
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
        width:  parent.width
        height: parent.height

        onClicked: {
            button.clicked(symbol);
            buttonAnimation.running = true;
            //console.log(symbol + " clicked");
        }
    }
}
