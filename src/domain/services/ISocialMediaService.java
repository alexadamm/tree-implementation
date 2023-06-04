package domain.services;
import domain.model.User;

import java.util.HashMap;
import java.util.LinkedList;
public interface ISocialMediaService {
    void requestConnection(User user, User targetUser);
    LinkedList<User> getConnections(User user);

    LinkedList<User> getRequestConnections(User user);

    void acceptRequestConnection(User user, User targetUser);

    void sendMessage(User user, User targetUser, String message);

    LinkedList<User> getInboxSenders(User user);

    LinkedList<String> readMessage(User user, User targetUser);

    LinkedList<User> getFriendRecommendations(User user);

    LinkedList<User> findMutual(User user, User otherUser);

    int findErdosNumber(User user, User otherUser);

    User getUserByName(String name);

    User login(String name);

    User register(String name);

}