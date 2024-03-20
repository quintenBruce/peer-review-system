    package com.example.PaperReview.repositories;

    import com.example.PaperReview.models.Organization;
    import com.example.PaperReview.models.User;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    public class UserRepository {
        private static final Map<String, User> userMap = new HashMap<>();

        private UserRepository() {
            // private constructor to prevent instantiation
        }

        public static User putUser(User user) {
            userMap.put(user.getUsername(), user);
            return user;
        }

        public static User getUser(String username) {
            return userMap.get(username);
        }

        public static void deleteUser(String username) {
            userMap.remove(username);
        }

        public static boolean isUserExists(String username) {
            return userMap.containsKey(username);
        }

        public static List<User> getUsers(List<String> usernames) {
            List<User> userList = new ArrayList<>();
            for (String username : usernames) {
                if (isUserExists(username)) {
                    userList.add(userMap.get(username));
                }
            }
            return userList;
        }
        public static List<User> getUsersByOrganization(Organization organization) {
            List<User> usersInOrganization = new ArrayList<>();
            for (User user : userMap.values()) {
                if (user.getOrganization() != null && user.getOrganization().equals(organization)) {
                    usersInOrganization.add(user);
                }
            }
            return usersInOrganization;
        }


    }

