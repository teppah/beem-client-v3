package com.yfy.beem.clientv3.util;

import com.yfy.beem.clientv3.apiaccess.UserApiAccessor;
import com.yfy.beem.clientv3.datamodel.Conversation;
import com.yfy.beem.clientv3.datamodel.Message;
import com.yfy.beem.clientv3.datamodel.SelfUser;
import com.yfy.beem.clientv3.datamodel.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Wrapper object that helps interfacing between api-access and the ui module. Contains
 * methods that wrap lists inside Observable stuff.
 */
@Component
public class MessagingServiceHelper {
    private static final Logger log = LoggerFactory.getLogger(MessagingServiceHelper.class);

    private UserApiAccessor userApiAccessor;
    private ConfigurableApplicationContext ctx;

    private ObservableList<User> currentUsers;
    private ObservableList<Conversation> conversations;

    // the user info for the current session
    private User self;

    public MessagingServiceHelper(UserApiAccessor userApiAccessor, ConfigurableApplicationContext ctx) {
        this.userApiAccessor = userApiAccessor;
        this.conversations = FXCollections.observableArrayList();
        this.ctx = ctx;
        currentUsers = FXCollections.observableArrayList(
                userApiAccessor.getAllUsers()
        );
//        initializeConversations();
    }

    public void initializeConversations() {
        for (int i = 0; i < currentUsers.size(); i++) {
            User u = currentUsers.get(i);
            Conversation c = Conversation.createNewConversation(
                    ctx.getBean(SelfUser.class),
                    u
            );
            c.addMessage(Message.builder().byUser(u).createdNow().withTextContent("Test").build());
            c.addMessage(Message.builder().byUser(ctx.getBean(SelfUser.class)).createdNow().withTextContent("Test").build());
            conversations.add(c);
        }
    }

    public ObservableList<User> getObservableCurrentUsers() {
        update();
        return currentUsers;
    }

    public void update() {
        currentUsers.clear();
        currentUsers.addAll(userApiAccessor.getAllUsers());
    }

    public boolean setCurrentUser(User user) {
        if (this.self == null) {
            this.self = user;
            log.info("set current user to {}", user);
            return true;
        } else {
            return false;
        }
    }

    public User getCurrentUser() {
        log.debug("returning current user {}", self);
        return self;
    }

    public ObservableList<Conversation> getAllConversations() {
        return FXCollections.unmodifiableObservableList(conversations);
    }

    public Optional<Conversation> getConversation(User recipient) {
        List<Conversation> conv = conversations.stream()
                .filter(conversation -> conversation.getRecipient().equals(recipient))
                .collect(Collectors.toList());
        if (conv.size() == 1) {
            log.info("returning conv {}", conv.get(0));
            return Optional.of(conv.get(0));
        } else {
            log.info("no convs found for {}" + recipient);
            return Optional.empty();
        }
    }

}
