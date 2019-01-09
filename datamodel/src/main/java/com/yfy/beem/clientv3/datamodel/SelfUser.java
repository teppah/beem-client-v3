package com.yfy.beem.clientv3.datamodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Used to represent the local user, this class
 * extends the {@link User} with some extra info,
 * such as with the private key and such.
 */
public class SelfUser extends User {
    private final PrivateKey privateKey;
    private SelfUser(Long id, String name, PublicKey publicKey, PrivateKey privateKey, InetAddress ipAddress) {
        super(id, name, publicKey, ipAddress);
        this.privateKey = privateKey;
    }


    public static Builder selfBuilder() {
        return new SelfUser.Builder();
    }

    /**
     * Specific builder for the self user
     */
    public static class Builder {
        // logger
        private static final Logger log = LoggerFactory.getLogger(Builder.class);
        public Builder(){}

        private Long id;
        private String name;
        private PublicKey publicKey;
        private PrivateKey privateKey;
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

        public Builder publicKey(PublicKey key) {
            this.publicKey = key;
            return this;
        }

        public Builder privateKey(PrivateKey privateKey) {
            this.privateKey = privateKey;
            return this;
        }


        public SelfUser build() {
            if (publicKey == null) {
                throw new IllegalArgumentException("publicKey cannot be null");
            }
            if (id == null) {
                throw new IllegalArgumentException("id cannot be null");
            }
            SelfUser user = new SelfUser(id, name, publicKey, privateKey, ipAddress);
            log.debug("building new selfuser {}", user.toString());
            return user;
        }
    }
    /*---getters and setters---*/
    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }
}
