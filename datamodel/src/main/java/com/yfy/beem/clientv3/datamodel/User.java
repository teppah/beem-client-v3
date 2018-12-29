package com.yfy.beem.clientv3.datamodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.security.*;
import java.util.Objects;

/**
 * Representation of an User with the appropriate keys.
 */
public class User {
    // logger
    private static final Logger log = LoggerFactory.getLogger(User.class);

    // == constants ==
    private static final int KEY_SIZE = 2048;
    private static final String ALGORITHM = "RSA";

    // == fields ==
    private final Long id;
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
        log.debug("publicKey format = ", publicKey.getFormat());
        log.debug("privateKey format = ", privateKey.getFormat());
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

    // equals for id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    // hash code for id
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link User}. This is the way to create a new user.
     * */
    public static class Builder {
        // logger
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

        public Builder generateKeyPair() {
            // create a key pair generator
            try {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
                keyPairGenerator.initialize(KEY_SIZE);

                keyPair = keyPairGenerator.genKeyPair();

                log.debug("generated keypair {}", keyPair);
                return this;
            } catch (NoSuchAlgorithmException e) {
                log.error("NoSuchAlgorithmException: '{}', please check spelling of algorithm", e);
                return null;
            }
        }

        public User build() {
            if (keyPair == null) {
                throw new IllegalArgumentException("keyPair cannot be null");
            }
            if (id == null) {
                throw new IllegalArgumentException("id cannot be null");
            }
            User user = new User(id, name, keyPair.getPublic(), keyPair.getPrivate(), ipAddress);
            log.debug("building new user {}", user);
            return user;
        }
    }

    /*
    * ===== getters and setters =====
    * */

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }
}
