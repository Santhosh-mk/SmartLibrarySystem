package observer;

import model.K2600128_User;

public class K2600128_NotificationService {

    // Notify user about reserved book availability
    public void notifyReservedBook(K2600128_User user, String bookTitle) {
        user.addNotification("Book '" + bookTitle + "' is now available for you!");
    }

    // Show all pending notifications when user logs in
    public void notifyUserOnLogin(K2600128_User user) {
        if (user.getNotifications().isEmpty()) {
            System.out.println("No new notifications.");
        } else {
            System.out.println("\n--- NOTIFICATIONS ---");
            for (String msg : user.getNotifications()) {
                System.out.println("[NOTIFICATION]: " + msg);
            }
            user.clearNotifications(); // clear after showing
        }
    }
}
