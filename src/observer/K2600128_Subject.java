package observer;
public interface K2600128_Subject {
    void register(K2600128_Observer o);
    void unregister(K2600128_Observer o);
    void notifyObservers(String message);
}
