package observer;

import model.K2600128_User;

public class K2600128_UserObserver implements K2600128_Observer {

    private K2600128_User user;

    public K2600128_UserObserver(K2600128_User user) {
        this.user = user;
    }

    @Override
    public void update(String message) {
        System.out.println("[NOTIFICATION for " + user.getName() + "]: " + message);
    }

    @Override
    public K2600128_User getUser() {
        return user;
    }
}
