/*
 * @Author: Jin X
 * @Date: 2020-12-13 14:43:51
 * @LastEditTime: 2020-12-13 16:18:45
 */

imRec(JSON.stringify({type:1,request:"accept"}))

imRec(JSON.stringify({
    type: 2,
    friendList: [
        "friend1",
        "friend2",
        "friend3",
        "friend4",
        "friend5",
    ]
}))

imRec(JSON.stringify({
    type: 3,
    from: "friend1",
    message: "This is a message" + Math.floor(Math.random() * 100)
}))