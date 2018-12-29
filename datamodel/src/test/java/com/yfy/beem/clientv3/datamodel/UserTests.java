package com.yfy.beem.clientv3.datamodel;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.Random;

/**
 * Tests for User
 * */
public class UserTests {
    private Random r;

    @Before
    public void setUp() {
        r = new Random();
    }


    @Test
    public void builderCreateNewUserTest() throws Exception {
        final String name = "USER-Test123564";
        final Long id = r.nextLong();
        final InetAddress ipAddr = InetAddress.getByName("1.0.0.1");
        User user = User.builder()
                .generateKeyPair()
                .name(name)
                .id(id)
                .ipAddress(ipAddr)
                .build();
        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(ipAddr, user.getIpAddress());
        assertNotNull(user.getPublicKey());
        assertNotNull(user.getPrivateKey());
    }

    @Test(expected = IllegalArgumentException.class)
    public void builderNoKeyGenerationTest() {
        User user = User.builder().build();
    }

}
