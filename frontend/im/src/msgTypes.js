/*
 * @Author: Jin X
 * @Date: 2020-12-12 21:30:10
 * @LastEditTime: 2020-12-12 21:47:16
 */

const errorResponse = {
    type: -1,
    reason: "reason"
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