var websocket = null;

window.onload = function() { // URI = ws://localhost:8080/Hey/ws
    connect('ws://' + window.location.host + '/Hey/ws');
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
    websocket.onmessage = onMessage;
    websocket.onerror   = onError;
}

function onOpen(event) {
    console.log("Logged in!");
    doSend(username);
}

function onClose(event) {
    writeToHistory('WebSocket closed (code ' + event.code + ').');
}

function onMessage(message) { // print the received message
    console.log(message);
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