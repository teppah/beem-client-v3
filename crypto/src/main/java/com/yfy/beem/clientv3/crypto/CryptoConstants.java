package com.yfy.beem.clientv3.crypto;

/**
 * Constants for cryptography-related stuff
 * */
public final class CryptoConstants {
    // do not let anyone instantiate this class
    private CryptoConstants() {
        throw new UnsupportedOperationException("u can't");
    }

    public static final String ALGORITHM = "RSA";
    public static final int KEY_SIZE = 2048;
}
