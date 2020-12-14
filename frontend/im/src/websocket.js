/*
 * @Author: Jin X
 * @Date: 2020-12-12 17:19:07
 * @LastEditTime: 2020-12-14 11:39:21
 */

import {handlers} from './Dispatcher'

if (!window.WebSocket)
    alert("Not support websocket");

const websocket = new WebSocket("ws://localhost:8080/ws");
// const websocket = {};

websocket.onopen = () => console.log("WebSocket: On");
websocket.onclose = () => console.log("WebSocket: Close");
websocket.onerror = () => console.log("WebSocket: Error");
window.onbeforeunload = () => websocket.close();

// websocket.send = (msg) => console.log("WebSocket send " + msg);

websocket.onmessage = function (res) {
    let resObj = JSON.parse(res);
    console.log(resObj);
    let type = resObj.type;
    if (type == -1) {
        // console.log("ERROR: " + resObj.reason);
        websocket.close(0, resObj.reason);
        return;
    } else if (type == 0) {
        console.log("Heartbeat");
        websocket.send(JSON.stringify({ type: 0 }));
        return;
    }
    handlers[type].call(null, resObj);
}


const o =  {
    send: (msg) => {
        websocket.send(JSON.stringify(msg));
    },
}



// for debugger:
// window.im = websocket;
// window.imRec = websocket.onmessage;
// window.handlers = handlers;

export default o;


