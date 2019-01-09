package com.yfy.beem.clientv3.datamodel;

import com.yfy.beem.clientv3.crypto.CryptoConstants;
import com.yfy.beem.clientv3.crypto.CryptoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.security.*;
import java.util.Objects;

/**
 * Representation of an User with the appropriate keys.
 */
public class User implements Comparable<User> {
    // logger
    private static final Logger log = LoggerFactory.getLogger(User.class);

    // == fields ==
    private final Long id;
    private String name;

    private final PublicKey publicKey;

    private InetAddress ipAddress;

    protected User(Long id, String name, PublicKey publicKey, InetAddress ipAddress) {
        this.id = id;
        this.name = name;
        this.publicKey = publicKey;
        this.ipAddress = ipAddress;
        log.debug("created new user {}", this.toString());
        log.debug("publicKey format = ", publicKey.getFormat());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", publicKey=..." +
                ", ipAddress=" + ipAddress +
                '}';
    }

    @Override
    public int compareTo(User o) {
        return this.id.compareTo(o.id);
    }

    // equals for id
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }



    // hash code for id
    @Override
    public final int hashCode() {
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
        private PublicKey publicKey;
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


        public User build() {
            if (publicKey == null) {
                throw new IllegalArgumentException("publicKey cannot be null");
            }
            if (id == null) {
                throw new IllegalArgumentException("id cannot be null");
            }
            User user = new User(id, name, publicKey, ipAddress);
            log.debug("building new user {}", user.toString());
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

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }
}
