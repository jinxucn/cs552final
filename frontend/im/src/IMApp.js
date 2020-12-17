/*
 * @Author: Jin X
 * @Date: 2020-12-12 16:55:41
 * @LastEditTime: 2020-12-17 20:36:58
 */

import React, { useState } from "react";

import Container from './Container'
import Login from './Login'

export default function IMApp() {
    
    const [userId, setUserId] = useState("");


    return (
        <div className="IMapp">
            {userId==""
                ? <Login setLoginId={setUserId}/>
                : <Container userId={userId} />
            }
        </div>
            
    )
}