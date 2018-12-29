package com.yfy.beem.clientv3.crypto;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.Assert.*;

/**
 * Tests for {@link CryptoUtils}
 * */
public class CryptoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(CryptoUtilsTest.class);
    private static final String TEST_STRING = "TestString 12 345 67890 abcdefg-hijklmno pqrstuvq xyz[]{}'./ ,;`~ABCDEF GHIJKLMNOP QRSTUVWXYZ-_=+";


    @Test
    public void testGenerateKeyPairGenerator() {
        assertNotNull(CryptoUtils.generateKeyPair());
    }

    @Test
    public void testGenerateKeyPair() {
        KeyPair keyPair = CryptoUtils.generateKeyPair();
        assertNotNull(keyPair);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        assertNotNull(publicKey);
        assertNotNull(privateKey);
    }

    @Test
    public void testEncryptDecrypt() throws InvalidKeyException {
        KeyPair keyPair = CryptoUtils.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] encryptedBytes = CryptoUtils.encrypt(publicKey, TEST_STRING.getBytes());
        byte[] decryptedBytes = CryptoUtils.decrypt(privateKey, encryptedBytes);
        assertEquals(TEST_STRING, new String(decryptedBytes));
    }
}
