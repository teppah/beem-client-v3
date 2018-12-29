package com.yfy.beem.clientv3.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

/**
 * Utility class for cryptography-related functions
 */
@SuppressWarnings("null")
public final class CryptoUtils {
    private static final Logger log = LoggerFactory.getLogger(CryptoUtils.class);

    // == private fields ==
    private static final KeyPairGenerator gen = createKeyPairGenerator();

    private static KeyPairGenerator createKeyPairGenerator() {
        try {
            return KeyPairGenerator.getInstance(CryptoConstants.ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            log.error("ERROR INITIALIZING CryptoUtils: {} ; please check crypto configuration", e);
            return null;
        }
    }

    // do not let anyone instantiate this class
    private CryptoUtils() {
        throw new UnsupportedOperationException("what are you doing lol");
    }

    /**
     * Decodes a byte array with the specified {@link PrivateKey}.
     *
     * @param privateKey     the {@link PrivateKey} to use
     * @param encryptedBytes the byte array to decode
     * @return the decoded byte array
     * @throws InvalidKeyException if the key is invalid
     */
    public static byte[] decrypt(PrivateKey privateKey, byte[] encryptedBytes)
            throws InvalidKeyException {
        return doAction(Cipher.DECRYPT_MODE, privateKey, encryptedBytes);
    }

    /**
     * Encodes a byte array with the specified {@link PublicKey}.
     *
     * @param publicKey      the {@link PublicKey} to use
     * @param bytesToEncrypt the byte array to decode
     * @return the encoded byte array
     * @throws InvalidKeyException if the key is invalid
     */

    public static byte[] encrypt(PublicKey publicKey, byte[] bytesToEncrypt)
            throws InvalidKeyException {
        return doAction(Cipher.ENCRYPT_MODE, publicKey, bytesToEncrypt);
    }

    private static byte[] doAction(int mode, Key key, byte[] bytes)
            throws InvalidKeyException {
        try {
            Cipher cipher = Cipher.getInstance(CryptoConstants.ALGORITHM);
            cipher.init(mode, key);
            return cipher.doFinal(bytes);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException |
                IllegalBlockSizeException |
                BadPaddingException e) {
            log.error("Configuration-related error: '{}', please check cryptography configuration", e);
            return null;
        }
    }

    /**
     * Generates a keypair.
     * */
    public static KeyPair generateKeyPair() {
        return gen.generateKeyPair();
    }

}
