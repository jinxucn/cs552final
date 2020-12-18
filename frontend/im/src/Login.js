
import React, { useState, useEffect } from 'react';

import websocket from './websocket'
import { msgTypes, dispatcher } from './Dispatcher'

export default function Login({ setLoginId}) {

    const [userId, setUserId] = useState("");
    const [password, setPassword] = useState("");

    function sendAuthRequest(userId, password) {
        let obj = {
            type: msgTypes.authenticate,
            userId: userId,
            password: password
        };
        websocket.send(obj);
    }

    useEffect(() => {
        dispatcher.register(msgTypes.authenticate, (res) => {
            if (res.request === "accept") {
                setLoginId(document.querySelector('#login-id').value);
            }
            else
                alert("Login: Denied");
        })
    },[])

    return (
        <div id="login-wrap">
            <input
                id="login-id"
                className="login-input"
                type="text"
                placeholder="UserId"
                value={userId}
                onChange={(e)=>setUserId(e.target.value)}
            />
            <input
                className="login-input"
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e)=>setPassword(e.target.value)}
            />
            <button
                className="login-submit"
                onClick={(function () {
                    let t = null;
                    return function () { 
                        if (t)
                            clearTimeout(t);
                        t = setTimeout(
                            sendAuthRequest.bind(null, userId, password),
                            300
                        )
                    }

                })()}
            >Submit</button>

        </div>
    )
}