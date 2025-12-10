package observer;

import model.K2600128_User;

public interface K2600128_Observer {
    void update(String message);
    K2600128_User getUser();
}
