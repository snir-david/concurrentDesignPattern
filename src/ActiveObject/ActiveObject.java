package ActiveObject;

public interface ActiveObject {
    void doSomeMethod() throws InterruptedException;
    void doSomeOtherMethod() throws InterruptedException;
    void stopQueue() throws InterruptedException;

}
