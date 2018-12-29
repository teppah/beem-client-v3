package com.yfy.beem.clientv3.datamodel;

/**
 * Interface representing what is contained inside a {@link Message}, whether it be text, images, audio, etc.
 * */
public interface MessageContent {
    /**
     * Get a textual representation of the message contents.
     * */
    String getText();
}
