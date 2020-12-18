/*
 * @Author: Jin X
 * @Date: 2020-12-12 20:36:10
 * @LastEditTime: 2020-12-18 13:35:34
 */
import React, { useEffect, useState } from "react";

import FriendList from './FriendList'
import MessageList from './MessageList'

import websocket from './websocket'
import { msgTypes, dispatcher } from './Dispatcher'

export default function Container({ userId }){

    const [friendOrMsg, setFriendOrMsg] = useState(true);
    const [currentFriend, setCurrentFriend] = useState("");

    /**
     * @description: toggle whether display friend list or message list
     */
    function toggleFOM() {
        if (!friendOrMsg)
            setCurrentFriend("");
        setFriendOrMsg(!friendOrMsg);
    }

    /**
     * @description: send a friend list request at the first time Container rendered
     *               then update the friendList in callback
     */
    const [friendList, setFriendList] = useState([]);
    // const [friendList, setFriendList] = useState([
    //     "friend1",
    //     "friend2",
    //     "friend3",
    //     "friend4",
    //     "friend5",
    // ]);

    const [msgMap, setMsgMap] = useState({});

    /**
     * @description: control whether friend items have 'unread' class
     *               use a map which is not good, should be rewritten in the future
     */
    const [unRead, setUnRead] = useState({});
    function toggleUnRead(friendId) {
        if(currentFriend!=friendId)
            setUnRead((prevUnRead)=>({
                ...prevUnRead,
                [friendId]: true
            }))
        else {
            let obj = {
                type: 5,
                from: friendId,
                to: userId
            }
            websocket.send(obj);
        }

    }
    function sendReadReq(friendId) {
        setUnRead((prevUnRead) => {
            if (prevUnRead[friendId]){ 
                let obj = {
                    type: 5,
                    from: friendId, 
                    to: userId,
                }         
                websocket.send(obj);
            }
            return {
                ...prevUnRead,
                [friendId]: false,
            }

        })
    }

    const [msgConfirm, setMsgConfirm] = useState({})


    /**
     * @description: add a message to the message list 
     * @param {*} msg the message string
     * @param {*} isSent flag for sent or received, if sent, it should be send to current friend
     * @param {*} friendId if received, indicate from which friend
     */
    function addMsg(msg, isSent, friendId) {
        const friend = isSent ? currentFriend : friendId;
        setMsgMap((prevMsg) => {
            let newMsg = {};
            if (!prevMsg[friend])
                newMsg = {
                    ...prevMsg,
                    [friend]:[ {isSent, msg }]
                }
            else {
                let newList = [...prevMsg[friend]];
                newList.push({ isSent, msg });
                newMsg = {
                    ...prevMsg,
                    [friend]: newList
                }
            }
            return newMsg;
        })
   
    }

    function sendMsgReq(toUserId, fromUserId, message) {
        let obj = {
            type: 3,
            userId: fromUserId,
            friendId: toUserId,
            message: message
        };
        websocket.send(obj);
        let friendId = toUserId;
        setMsgConfirm((prevMsgConfirm) => {
            const temp = prevMsgConfirm[friendId] || [];
            let [confirmed = 0, sentConfirming = 0] = temp;
            return {
                ...prevMsgConfirm,
                [friendId]:[confirmed, sentConfirming+1]
            }

        })
        addMsg(message, true, friendId);
    }

    useEffect(() => {
        dispatcher.register(msgTypes.friendList,
            (res) =>setFriendList(res.friendList));
        dispatcher.register(msgTypes.confirmRead,
            (res) => {
                let friendId = res.to;
                setMsgConfirm((prevMsgConfirm) => {
                    let [confirmed, sentConfirming] = prevMsgConfirm[friendId];
                    return {
                        ...prevMsgConfirm,
                        [friendId]: [confirmed + sentConfirming, 0]
                    }
                })
            });
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
                    setRead={sendReadReq}
                    toggleToMsg={toggleFOM}
                  />
                : <MessageList
                    msgList={msgMap[currentFriend]}
                    confirmedReadNum={
                        msgConfirm[currentFriend]
                            ? msgConfirm[currentFriend][0]
                            : 0
                    }
                    friendId={currentFriend}
                    userId={userId}
                    sendMsgReq={sendMsgReq}
                    toggleToFriend={toggleFOM}
                   />
            }
        </div>
    )

}