/*
 * @Author: Jin X
 * @Date: 2020-12-12 20:27:41
 * @LastEditTime: 2020-12-13 19:32:34
 */

import React, { useState } from "react";

import Friend from './Friend'


export default function FriendList({ friendList, setCurrentFriend, unRead, setUnRead, toggleToMsg}){

    function setRead(friendId) {
        setUnRead({
            ...unRead,
            [friendId]: false
        })
    }

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