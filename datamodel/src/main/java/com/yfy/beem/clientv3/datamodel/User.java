package com.yfy.beem.clientv3.datamodel;

import java.net.InetAddress;
import java.security.Key;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Representation of an User
 * */
public class User {
    // == static final fields ==
    private static final int KEY_SIZE = 2048;

    private Long id;
    private String name;

    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    private InetAddress ipAddress;

    private User(Long id, String name, RSAPublicKey publicKey, RSAPrivateKey privateKey, InetAddress ipAddress) {
        this.id = id;
        this.name = name;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.ipAddress = ipAddress;
    }



    public static class Builder {


    }
}
