var websocket = null;
var notifN = 0;

window.onload = function() { // URI = ws://localhost:8080/Hey/ws
    connect('ws://10.16.2.109:8080/ws');
}

function connect(host) { // connect to the host websocket
    if ('WebSocket' in window)
        websocket = new WebSocket(host);
    else if ('MozWebSocket' in window)
        websocket = new MozWebSocket(host);
    else {
        writeToHistory('Get a real browser which supports WebSocket.');
        return;
    }

    websocket.onopen    = onOpen; // set the 4 event listeners below
    websocket.onclose   = onClose;
    websocket.onmessage = createNotification;
    websocket.onerror   = onError;
}

function onOpen(event) {
    console.log("Logged in!");
    doSend(username);
}

function onClose(event) {
    writeToHistory('WebSocket closed (code ' + event.code + ').');
}

function onError(event) {
    writeToHistory('WebSocket error.');
}

function doSend(message) {
    websocket.send(message); // send the message to the server
}

function writeToHistory(text) {
    console.log(text);
}

function createNotification(text) {
    var notifContainer = document.createElement("div");
    notifContainer.id = "notification" + notifN++;
    notifContainer.innerHTML = text.data;
    document.body.appendChild(notifContainer);
    var id = notifContainer.id;
    setTimeout(function () {
        var notification = document.getElementById(id);
        document.removeChild(notification);
    }, 2000);
}