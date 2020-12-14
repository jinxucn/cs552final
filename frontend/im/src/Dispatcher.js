/*
 * @Author: Jin X
 * @Date: 2020-12-12 17:29:41
 * @LastEditTime: 2020-12-12 22:17:49
 */

const msgTypes = {
    // error: -1,
    // heartbeat:0,
    authenticate: 1,
    friendList: 2,
    receiveMessage: 3
};

const handlers = {
    [msgTypes.authenticate]: null,
    [msgTypes.friendList]: null,
    [msgTypes.receiveMessage]: null,

};

const dispatcher = {
    register: function (msgType, callback) {
        handlers[msgType] = callback;
    },
};


export {msgTypes,handlers,dispatcher}