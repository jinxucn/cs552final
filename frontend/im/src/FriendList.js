/*
 * @Author: Jin X
 * @Date: 2020-12-12 20:27:41
 * @LastEditTime: 2020-12-17 20:29:42
 */

import React, { useState } from "react";

function Friend({friendId, isUnRead, setCurrentFriend }){

    return (
        <li className={"friend" + (isUnRead ? " friend-unread":"")}
            onClick={setCurrentFriend}
        >{friendId}
        </li>
    )

}

export default function FriendList({ friendList, setCurrentFriend, unRead, setRead, toggleToMsg}){


    function setCurrentFriendAndRead(friendId) {
        setCurrentFriend(friendId);
        setRead(friendId);
    }

    return (
        <ul className="friend-list"
            onClick={toggleToMsg}
        >{
            friendList.map((friend) => 
                <Friend key={friend}
                        friendId={friend}
                        isUnRead={unRead[friend]}
                        setCurrentFriend={setCurrentFriendAndRead.bind(null, friend)}
                />
            )
            }
            
        </ul>
    )
}