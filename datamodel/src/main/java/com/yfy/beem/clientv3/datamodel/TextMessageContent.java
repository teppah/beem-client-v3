package com.yfy.beem.clientv3.datamodel;

/**
 * Implementation of {@link MessageContent} that represents a message containing text.
 * */
public class TextMessageContent implements MessageContent {
    private final String content;

    public TextMessageContent(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }
}
