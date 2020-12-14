/*
 * @Author: Jin X
 * @Date: 2020-12-12 18:25:38
 * @LastEditTime: 2020-12-13 16:01:29
 */

import React from 'react';

export default function Message({ type, content }){
    return (
        <li className="message-wrap">
            <div className={["message","message-" + type].join(" ")}>{content}</div>
        </li>
    )
    // return (
    //     <li className={["message","message-" + type].join(" ")}>{content}</li>
    // )
}