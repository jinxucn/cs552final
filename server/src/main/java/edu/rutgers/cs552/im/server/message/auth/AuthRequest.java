package edu.rutgers.cs552.im.server.message.auth;

import edu.rutgers.cs552.im.common.dispatcher.Message;

/**
 * 用户认证请求
 */
public class AuthRequest implements Message {

    public static final String TYPE = "AUTH_REQUEST";

    /**
     * 认证 Token
     */
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public AuthRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @Override
    public String toString() {
        return "AuthRequest{" +
                "accessToken='" + accessToken + '\'' +
                '}';
    }

}