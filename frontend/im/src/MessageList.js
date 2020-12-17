/*
 * @Author: Jin X
 * @Date: 2020-12-12 18:25:38
 * @LastEditTime: 2020-12-13 20:09:39
 */
import React, { useState, useEffect } from 'react';

import Message from './Message';

import websocket from './websocket'
import { dispatcher } from './Dispatcher'



export default function MessageList({ msgList,  friendId,  userId, addMsg,toggleToFriend}) {

    const [inputContent, setInputContent] = useState("");

    function sendMsgReq(toUserId, fromUserId, message) {
        let obj = {
            type: 3,
            userId: fromUserId,
            friendId: toUserId,
            message: message
        };
        websocket.send(obj);
        addMsg(message, true, friendId);
    }
    useEffect(() => {
            let ele = document.querySelector(".message-list");
            ele.scrollTop = ele.scrollHeight;
    })
    return (
        <div>
            <button
                className="content-toggle"
                onClick={toggleToFriend}
            >Back</button>
            <h2
                className="message-friend"
            >{friendId}</h2>
            <ul className="message-list">{
                msgList && msgList.map((msg,i)=> {
                    let type = msg["sent"] ? "sent" : "received";
                    let content = msg[type];
                    return(
                    <Message
                        key={""+friendId+i}
                        type={type}
                        content={content}
                    />)
                })
            }</ul>
            <input
                className="message-input"
                placeholder="Send on Enter"
                onChange={(e) => setInputContent(e.target.value)}
                value={inputContent}
                onKeyDown={(e) => {
                    if (e.keyCode === 13 && e.target.value) {
                        sendMsgReq(friendId, userId, e.target.value)
                        setInputContent("");
                    }

                }}
            />
        </div>
    )


}