public class ThreadPool extends Thread {
    Runnable task;
    boolean term = false;

    public ThreadPool() {
        new Thread(() -> {
            this.run();
        }).start();
    }

    public void assignTask(Runnable r) {
        task = r;
        notifyPool();
    }

    @Override
    public void run() {
        while (!term) {
            if (task != null)
                task.run();
            try {
                suspendPool();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread Pool is Dead!");
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
        ThreadPool tp = new ThreadPool();
        tp.assignTask(() -> {
            System.out.println("New Task added!");
        });
        tp.assignTask(() -> {
            tp.terminatePool();
        });


    }
}
