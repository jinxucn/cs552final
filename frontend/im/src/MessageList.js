/*
 * @Author: Jin X
 * @Date: 2020-12-12 18:25:38
 * @LastEditTime: 2020-12-17 20:31:10
 */
import React, { useState, useEffect } from 'react';


const MsgInput = ({ onEnter }) => {
    
    const [inputContent, setInputContent] = useState("");

    return (
        <input
            className="message-input"
            placeholder="Send on Enter"
            onChange={(e) => setInputContent(e.target.value)}
            value={inputContent}
            onKeyDown={(e) => {
                if (e.keyCode === 13 && e.target.value) {
                    onEnter(e.target.value);
                    setInputContent("");
                }
                
            }}
        />
    )
}

function Message({ type, content }){
    return (
        <li className="message-wrap">
            <div className={"message "+type}>{content}</div>
        </li>
    )
}


export default function MessageList({ msgList, confirmedReadNum,  friendId,  userId, sendMsgReq,toggleToFriend}) {
    
    

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
                    let type = null;
                    if (!msg.isSent)
                        type = "message-received";
                    else {
                        type = "message-sent";
                        if (confirmedReadNum <= 0)
                            type += " message-unread"
                        else
                            type += " message-read"
                        confirmedReadNum--;
                    }
                    let content = msg.msg;
                    return(
                    <Message
                        key={""+friendId+i}
                        type={type}
                        content={content}
                    />)
                })
            }</ul>
            <MsgInput onEnter={(val)=>sendMsgReq(friendId, userId,val)} />
        </div>
    )


}