package com.yfy.beem.clientv3.datamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that represents a conversation between two {@link User}s,
 * the first one being self and the other one being the receiver.
 * */
public class Conversation {
    private final SelfUser self;
    private final User recipient;

    private final List<Message> messages;

    private Conversation(SelfUser self, User recipient) {
        this.self = self;
        this.recipient = recipient;
        this.messages = new ArrayList<>();
    }

    /**
     * Add a message to the conversation.
     * */
    public void addMessage(Message msg) {
        if (msg != null) {
            messages.add(msg);
        }
    }
    /**
     * Returns a read-only view of the messages of this conversation.
     */
    public List<Message> getAllMessages() {
        return Collections.unmodifiableList(messages);
    }

    public SelfUser getSelf() {
        return self;
    }

    public User getRecipient() {
        return recipient;
    }

    public static Conversation createNewConversation(SelfUser self, User recipient) {
        if (recipient instanceof SelfUser) {
            throw new IllegalArgumentException("recipient cannot be of SelfUser type");
        }
        return new Conversation(self, recipient);
    }
}
