package domain.services;

import domain.model.User;
import java.util.*;

public class SocialMediaService implements ISocialMediaService {
    public HashMap<User, HashMap<User, Integer>> tempDb;

    public SocialMediaService(HashMap<User, HashMap<User, Integer>> tempDb) {
        this.tempDb = tempDb;
    }

    @Override
    public void requestConnection(User user, User targetUser) {
        if (user.connectionReq.contains(targetUser)) {
            System.out.println("You have already sent a request to this user");
            return;
        }
        if (user.connections.contains(targetUser)) {
            System.out.println("You are already connected to this user");
            return;
        }
        targetUser.connectionReq.add(user);
    }

    @Override
    public LinkedList<User> getConnections(User user) {
        return user.connections;
    }

    @Override
    public LinkedList<User> getRequestConnections(User user) {
        return user.connectionReq;
    }

    @Override
    public void acceptRequestConnection(User user, User targetUser) {
        user.connections.add(targetUser);
        targetUser.connections.add(user);
        user.connectionReq.remove(targetUser);
        this.tempDb.get(user).put(targetUser, 1);
        this.tempDb.get(targetUser).put(user, 1);
    }

    @Override
    public void sendMessage(User user, User targetUser, String message) {
        if (user.connections.contains(targetUser)) {
            if (targetUser.inbox.containsKey(user)) {
                targetUser.inbox.get(user).add(message);
            } else {
                LinkedList<String> messages = new LinkedList<>();
                messages.add(message);
                targetUser.inbox.put(user, messages);
            }
        } else {
            System.out.println("You are not connected to this user");
        }
    }

    @Override
    public LinkedList<User> getInboxSenders(User user) {
        LinkedList<User> senders = new LinkedList<>();
        for (User sender : user.inbox.keySet()) {
            senders.add(sender);
        }
        return senders;
    }

    @Override
    public LinkedList<String> readMessage(User user, User targetUser) {
        if (user.inbox.containsKey(targetUser)) {
            return user.inbox.get(targetUser);
        } else {
            return null;
        }
    }

    @Override
    public LinkedList<User> getFriendRecommendations(User user) {
        Map<User, Integer> recConnections = new HashMap<>();
        int maxDegree = 2;
        LinkedList<User> queue = new LinkedList<>();
        queue.add(user);
        user.numberValue = 0;

        while (!queue.isEmpty()) {
            User currentFirst = queue.removeFirst();
            if (currentFirst.numberValue > maxDegree)
                break;
            if (currentFirst.isVisited)
                continue;
            currentFirst.isVisited = true;
            LinkedList<User> allConnections = currentFirst.connections;
            if (allConnections == null)
                continue;
            for (User connection : allConnections) {
                if (!connection.isVisited) {
                    queue.add(connection);
                    if (currentFirst.numberValue + 1 < connection.numberValue)
                        connection.numberValue = currentFirst.numberValue + 1;
                }
                if (connection.numberValue <= maxDegree && !user.connections.contains(connection)) {
                    if (recConnections.containsKey(connection)) {
                        recConnections.put(connection, recConnections.get(connection) + 1);
                    } else {
                        recConnections.put(connection, 1);
                    }
                }
            }
        }

        Comparator<User> valueComparator = Comparator.comparing(recConnections::get);

        LinkedList<User> connectionList = new LinkedList<>(recConnections.keySet());
        connectionList.remove(user);
        Collections.sort(connectionList, valueComparator.reversed());
        for (User u : this.tempDb.keySet()) {
            u.reset();
        }

        return connectionList;
    }

    @Override
    public String findUser(User user, User targetUser) {
        String message = "";
        // check is user is connected to targetUser
        message += (user.connections.contains(targetUser)) ? "You are connected to " + targetUser.name + "\n"
                : "You are not connected to " + targetUser.name + "\n";
        // check mutual
        LinkedList<User> mutualConnections = findMutual(user, targetUser);
        if (mutualConnections.size() > 0) {
            String mutualConnectionsName = "";
            for (int i = 1; i <= mutualConnections.size(); i++) {
                mutualConnectionsName += i + ". " + mutualConnections.get(i - 1).name + "\n";
            }
            message += "You have " + mutualConnections.size() + " mutual connections: \n"
                    + mutualConnectionsName;
        } else {
            message += "Your connections have no mutual with him/her/they/them/it";
        }
        return message;
    }

    @Override
    public LinkedList<User> findMutual(User user, User otherUser) {
        LinkedList<User> mutualConnections = new LinkedList<>();
        for (User connection : user.connections) {
            if (connection.connections.contains(otherUser)) {
                mutualConnections.add(connection);
            }
        }
        return mutualConnections;
    }

    @Override
    public int findErdosNumber(User user, User otherUser) {
        Queue<User> queue = new LinkedList<>();
        queue.add(user);
        user.numberValue = 0;

        while (!queue.isEmpty()) {
            User currentUser = queue.poll();

            if (currentUser.isVisited)
                continue;

            currentUser.isVisited = true;

            for (User connection : currentUser.connections) {
                if (!connection.isVisited) {
                    int newNumberValue = currentUser.numberValue + 1;
                    if (newNumberValue < connection.numberValue) {
                        connection.numberValue = newNumberValue;
                    }
                    queue.add(connection);
                }
            }
        }

        for (User u : this.tempDb.keySet()) {
            u.reset();
        }

        return otherUser.numberValue;
    }

    @Override
    public User getUserByName(String name) {
        for (User user : tempDb.keySet()) {
            if (user.name.equals(name))
                return user;
        }
        return null;
    }

    @Override
    public User login(String name) {
        for (User user : tempDb.keySet()) {
            if (user.name.equals(name))
                return user;
        }
        return null;
    }

    @Override
    public User register(String name) {
        for (User user : tempDb.keySet()) {
            if (user.name.equals(name))
                return null;
        }
        User newUser = new User(name);
        tempDb.put(newUser, new HashMap<User, Integer>());
        return newUser;
    }
}
