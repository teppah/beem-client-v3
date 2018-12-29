package com.yfy.beem.clientv3.datamodel;

import java.time.LocalDateTime;

/**
 * Class that represents a single message.
 */
public class Message {
    private final MessageContent content;
    private final LocalDateTime timeCreated;
    private final User originatedFrom;

    public Message(MessageContent content, LocalDateTime timeCreated, User originatedFrom) {
        this.content = content;
        this.timeCreated = timeCreated;
        this.originatedFrom = originatedFrom;
    }

    public MessageContent getContent() {
        return content;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public User getOriginatedFrom() {
        return originatedFrom;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Static builder class for the {@link Message} class.
     */
    public static class Builder {
        private MessageContent messageContent;
        private User user;
        private LocalDateTime timeCreated;

        public Builder() {

        }

        public Builder byUser(User user) {
            this.user = user;
            return this;
        }

        public Builder dateTimeCreated(LocalDateTime timeCreated) {
            this.timeCreated = timeCreated;
            return this;
        }

        public Builder createdNow() {
            this.timeCreated = LocalDateTime.now();
            return this;
        }

        public Builder withTextContent(String textContent) {
            messageContent = new TextMessageContent(textContent);
            return this;
        }

        public Message build() {
            if (messageContent == null || user == null || timeCreated == null) {
                throw new UnsupportedOperationException("field cannot be null while creating user: messageContent = " + messageContent + ", user = " + user + ", timeCreated = " + timeCreated);
            }
            return new Message(messageContent, timeCreated, user);
        }

    }
}
