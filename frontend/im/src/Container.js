/*
 * @Author: Jin X
 * @Date: 2020-12-12 20:36:10
 * @LastEditTime: 2020-12-13 20:19:40
 */
import React, { useEffect, useState } from "react";

import FriendList from './FriendList'
import MessageList from './MessageList'

import websocket from './websocket'
import { msgTypes, dispatcher } from './Dispatcher'

export default function Container({ userId }){
    
    const [friendOrMsg, setFriendOrMsg] = useState(true);
    const [currentFriend, setCurrentFriend] = useState("");
    const [friendList, setFriendList] = useState([]);
    // const [friendList, setFriendList] = useState([
    //     "friend1",
    //     "friend2",
    //     "friend3",
    //     "friend4",
    //     "friend5",
    // ]);

    const [msgMap, setMsgMap] = useState({});
    // const [msgMap, setMsgMap] = useState({
    //     "friend1": [
    //         {'sent':"sent 123124124132434543"},
    //         {'sent':"sent 53425dlfgjlsdkfglfgkjsdfg"},
    //         {'received':"received kldfsgjo20flsfkdfsgfsd"},
    //         {'sent':"sent asdfq4523049ewrj09web"},
    //         {'sent':"sent skflskadf3"},
    //     ]
    // });

    const [unRead, setUnRead] = useState({});

    function toggleUnRead(friendId) {
        if(currentFriend!=friendId)
            setUnRead((prevUnRead)=>({
                ...prevUnRead,
                [friendId]: true
            }))
    }

    function toggleFOM() {
        if (!friendOrMsg)
            setCurrentFriend("");
        setFriendOrMsg(!friendOrMsg);
    }


    function addMsg(msg, isSent, friendId) {
        const flag = isSent ? "sent" : "received";
        const friend = isSent ? currentFriend : friendId;
        setMsgMap((prevMsg) => {
            let newMsg = {};
            if (!prevMsg[friend])
                newMsg = {
                    ...prevMsg,
                    [friend]:[{[flag]: msg }]
                }
            else {
                let newList = [...prevMsg[friend]];
                newList.push({ [flag]: msg });
                newMsg = {
                    ...prevMsg,
                    [friend]: newList
                }
            }
            return newMsg;
        })
    }

    useEffect(() => {
        dispatcher.register(msgTypes.friendList,
            (res) =>setFriendList(res.friendList));
        let obj = {
            type: msgTypes.friendList,
            userId: userId
        }
        websocket.send(obj);
    },[])

    useEffect(() => {
        dispatcher.register(msgTypes.receiveMessage,
            (res) => {
                toggleUnRead(res.from);
                addMsg(res.message, false, res.from);
            });

    },[currentFriend])

    return (
        <div className="container">
            {friendOrMsg
                ? <FriendList
                    friendList={friendList}
                    setCurrentFriend={setCurrentFriend}
                    unRead={unRead}
                    setUnRead={setUnRead}
                    toggleToMsg={toggleFOM}
                  />
                : <MessageList
                    msgList={msgMap[currentFriend]}
                    friendId={currentFriend}
                    userId={userId}
                    addMsg={addMsg}
                    toggleToFriend={toggleFOM}
                   />
            }
        </div>
    )

}