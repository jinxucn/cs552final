/*
 * @Author: Jin X
 * @Date: 2020-12-12 21:30:10
 * @LastEditTime: 2020-12-12 21:47:16
 */

const errorResponse = {
    type: -1,
    reason: "reason"//message
}

const heartBeatRequestAndResponse = {
    type: 0
}

const authenticateRequest = {
    type: 1,
    userId: 'userId',
    password: 'password'
}

const authenticateResponse = {
    type: 1,
    request: "accept"/"denied"
}

const friendListRequest = {
    type: 2,
    userId: 'userId'
}

const friendListResponse = {
    type: 2,
    friendList: ["friend1Id", "friend2Id", "friend3Id"]
}

const sendMsgRequest = {
    type: 3,
    userId: 'userId',
    friendId: 'friendId',
    message: 'Hello world'
}

const sendMsgResponse = {
    type: 3,
    from: "userId",
    message: 'Hello world'
}



//from sender to server
const sendMsgRequest = {
    type: 3,
    userId: 'userId',
    friendId: 'friendId',
    message: 'Hello world'
}

//from server to sender
const sendMsgResponse = {
    type: 3,
    status: "success"/"failure"
}

//from server to receiver
const forwardMsgRequest = {
    type: 4,
    from: "userId",
    message: 'Hello world'
}

//from receiver to server
const forwardMsgResponse = {
    type: 4,
    from: "userId",    //message sender id
    to: "userId"       //message receiver id
}

//from server to sender
const readMsgResponse = {
    type: 5,
    from: "userId",    //message sender id
    to: "userId"       //message receiver id
}