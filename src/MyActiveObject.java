import java.util.concurrent.*;

public class MyActiveObject implements ActiveObject {
    private boolean stop = false;
    private String someData;
    private String otherData;
    private BlockingQueue<Runnable> dispatchedQueue = new LinkedBlockingQueue<>();

    public MyActiveObject() {
        new Thread(() -> {
            while (!stop) {
                try {
                    dispatchedQueue.take().run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Active Object is Dead!");
        }).start();
    }

    @Override
    public void doSomeMethod() throws InterruptedException {
        dispatchedQueue.put(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            someData = "This is Some Data!";
            System.out.println(someData);
        });

    }

    @Override
    public void doSomeOtherMethod() throws InterruptedException {
        dispatchedQueue.put(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            otherData = "This is Some Other Data!";
            System.out.println(otherData);
        });

    }

    public void stopQueue() throws InterruptedException {
        dispatchedQueue.put(() -> stop = true);

    }

    public static void main(String[] args) throws InterruptedException {
        MyActiveObject mao = new MyActiveObject();
        mao.doSomeMethod();
        System.out.println("print after setting up first task!");
        mao.doSomeOtherMethod();
        mao.stopQueue();
    }
}
