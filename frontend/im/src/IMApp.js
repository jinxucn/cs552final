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