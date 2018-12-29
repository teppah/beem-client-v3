package com.yfy.beem.clientv3.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Utility class for cryptography-related functions
 */
@SuppressWarnings("null")
public final class CryptoUtils {
    private static final Logger log = LoggerFactory.getLogger(CryptoUtils.class);

    // == private fields ==
    private static final KeyPairGenerator gen = createKeyPairGenerator();
    private static final KeyFactory kf = createKeyFactory();

    private static KeyPairGenerator createKeyPairGenerator() {
        try {
            return KeyPairGenerator.getInstance(CryptoConstants.ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            log.error("ERROR INITIALIZING CryptoUtils: {} ; please check crypto configuration", e);
            return null;
        }
    }

    private static KeyFactory createKeyFactory() {
        try {
            return KeyFactory.getInstance(CryptoConstants.ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            log.error("ERROR INITIALIZING CryptoUtils: {} ; please check crypto configuration", e);
            return null;
        }
    }

    // do not let anyone instantiate this class
    private CryptoUtils() {
        throw new UnsupportedOperationException("what are you doing lol, you can't do this");
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
     * @return a newly generated {@link KeyPair}
     * */
    public static KeyPair generateKeyPair() {
        return gen.generateKeyPair();
    }

    /**
     * Returns a base64 encoded version of the key for convenience
     * */
    public static String keyToString(Key key) {
        byte[] bytes = key.getEncoded();
        return Base64.getEncoder()
                .encodeToString(bytes);
    }

    /**
     * Parses a {@link String} to a {@link PrivateKey}.
     * */
    public static PrivateKey stringToPrivateKey(String privateKeyContent) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
                    Base64.getDecoder().decode(privateKeyContent)
            );
            PrivateKey priv = kf.generatePrivate(keySpec);
            return priv;

        }  catch (InvalidKeySpecException e) {
            log.error("invalid key spec: {}", e);
        }
        return null;
    }

    /**
     * Parses a {@link String} to a {@link PublicKey}.
     * */

    public static PublicKey stringToPublicKey(String publicKeyContent) {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
                    Base64.getDecoder().decode(publicKeyContent)
            );
            PublicKey publicKey = kf.generatePublic(keySpec);
            return publicKey;

        }  catch (InvalidKeySpecException e) {
            log.error("invalid key spec: {}", e);
        }
        return null;
    }
}
