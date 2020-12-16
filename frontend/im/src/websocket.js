/*
 * @Author: Jin X
 * @Date: 2020-12-12 17:19:07
 * @LastEditTime: 2020-12-16 18:43:32
 */

import {handlers} from './Dispatcher'

if (!window.WebSocket)
    alert("Not support websocket");

const websocket = new WebSocket("ws://localhost:8080/ws");
// const websocket = {};


const resetHearBeat = (function () {
    let timer = 30;
    let flag;
    (function heartbeat() {
        if (!timer) {
            console.log("IMApp: lost heartbeat");
            websocket.close();
            clearTimeout(flag);
        }
        timer--;
        flag = setTimeout(heartbeat, 1000);
    })();
    return () => timer = 30;
})();

websocket.onopen = () => {
    console.log("WebSocket: On");
    (function () {
        let t = null;

    })
    websocket.send(JSON.stringify({ type: 0 }));
};

websocket.onclose = () => console.log("WebSocket: Close");
websocket.onerror = () => console.log("WebSocket: Error");
window.onbeforeunload = () => websocket.close();

// websocket.send = (msg) => console.log("WebSocket send " + msg);
function heartbeat() {

}

websocket.onmessage = function (res) {
    let resObj = JSON.parse(res);
    console.log(resObj);
    let type = resObj.type;
    if (type == -1) {
        // console.log("ERROR: " + resObj.reason);
        websocket.close(0, resObj.reason);
        return;
    } else if (type == 0) {
        console.log("IMApp: receive heartbeat");
        resetHearBeat();
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


