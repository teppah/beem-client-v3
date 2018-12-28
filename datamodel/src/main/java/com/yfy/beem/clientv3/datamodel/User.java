package com.yfy.beem.clientv3.datamodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Representation of an User
 */
public class User {
    private static final Logger log = LoggerFactory.getLogger(User.class);
    // == constants ==
    private static final int KEY_SIZE = 2048;
    private static final String ALGORITHM = "RSA";

    // == fields ==
    private Long id;
    private String name;

    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    private InetAddress ipAddress;

    private User(Long id, String name, PublicKey publicKey, PrivateKey privateKey, InetAddress ipAddress) {
        this.id = id;
        this.name = name;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.ipAddress = ipAddress;
        log.debug("created new user {}", this.toString());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", publicKey=" + publicKey +
                ", privateKey=" + privateKey +
                ", ipAddress=" + ipAddress +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private static final Logger log = LoggerFactory.getLogger(Builder.class);

        private Long id;
        private String name;
        private KeyPair keyPair;
        private InetAddress ipAddress;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder ipAddress(InetAddress address) {
            this.ipAddress = address;
            return this;
        }

        public Builder generateKeyPair() throws GeneralSecurityException {
            // create a key pair generator
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);

            keyPair = keyPairGenerator.genKeyPair();

            log.debug("generated keypair {}", keyPair);
            return this;
        }

        public User build() {
            if (keyPair == null) {
                throw new IllegalArgumentException("key pair cannot be null");
            }
            User user = new User(id, name, keyPair.getPublic(), keyPair.getPrivate(), ipAddress);
            log.debug("building new user {}", user);
            return user;
        }


    }
}
