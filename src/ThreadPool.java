import java.util.ArrayList;
import java.util.List;

public class ThreadPool extends Thread {
    boolean term = false;
    List<Thread> tp = new ArrayList<>();
    List<Runnable> tasks = new ArrayList<>();


    public ThreadPool(Integer num) {
        for (int i = 0; i < num; i++)
            tp.add(new Thread(() -> {
                this.run();
            }));
        for (int i = 0; i < num; i++)
            tp.get(i).start();
    }

    public synchronized void assignTask(Runnable r) {
        tasks.add(r);
        notifyPool();
    }

    @Override
    public void run() {
        while (!term) {
            synchronized (this) {
                if (!tasks.isEmpty()) {
                    tasks.get(0).run();
                    tasks.remove(0);
                    System.out.println("Thread " + Thread.currentThread().getId() + " got the task done!");
                }
                try {
                    suspendPool();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Thread Pool is Dead!");
        tp.remove(this);
    }

    synchronized void notifyPool() {
        notify();
    }

    synchronized void suspendPool() throws InterruptedException {
        wait();
    }

    private void terminatePool() {
        this.term = true;
    }

    public static void main(String[] args) {
        ThreadPool tp = new ThreadPool(5);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            tp.assignTask(() -> {
                System.out.println("New " + Integer.toString(finalI) + " Task added!");
            });
        }
        tp.assignTask(()->{
            tp.terminatePool();
        });
        while (!tp.tasks.isEmpty() && !tp.tp.isEmpty()){
            tp.notifyPool();
        }

    }
}
