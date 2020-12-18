

const msgTypes = {
    // error: -1,
    // heartbeat:0,
    authenticate: 1,
    friendList: 2,
    confirmSent: 3,
    receiveMessage: 4,
    confirmRead: 5
};

const handlers = {
    [msgTypes.authenticate]: null,
    [msgTypes.friendList]: null,
    [msgTypes.receiveMessage]: null,
    [msgTypes.confirmRead]: null

};

const dispatcher = {
    register: function (msgType, callback) {
        handlers[msgType] = callback;
    },
};


export {msgTypes,handlers,dispatcher}