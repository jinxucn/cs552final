/*
 * @Author: Jin X
 * @Date: 2020-12-12 18:25:22
 * @LastEditTime: 2020-12-13 20:02:51
 */
import React, { useState } from "react";

export default function Friend({friendId, isUnRead, setCurrentFriend }){

    return (
        <li className={"friend" + (isUnRead ? " friend-unread":"")}
            onClick={setCurrentFriend}
        >{friendId}
        </li>
    )

}