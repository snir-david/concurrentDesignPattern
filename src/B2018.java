import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class B2018 {
    public interface Command {
        void doCommand();

        int getPriority();
    }

    HashMap<String, Command> commandMap;
    List<Command> dispatch;
    volatile boolean stop;
    List<Thread> threadsList;

    public B2018(HashMap<String, Command> map) {
        commandMap = map;
        dispatch = new ArrayList<>();
        stop = false;
    }

    public void submit(String command) {
        synchronized (this) {
            dispatch.add(commandMap.get(command));
        }
    }

    void stop() {
        stop = true;
        for(Thread t: threadsList){
            t.interrupt();
        }
    }

    void start(int numOfThreads) {
        dispatch.sort((o1, o2) -> o1.getPriority() - o2.getPriority());
        BlockingQueue<Command> queue = new LinkedBlockingQueue<>();
        threadsList = new ArrayList<>();
        for (Command command : dispatch)
            try {
                queue.put(command);
            } catch (Exception ignored) {
            }
        for (int i = 0; i < numOfThreads; i++) {
            threadsList.add(new Thread(() -> {
                while (!stop) {
                    try {
                        queue.take().doCommand();
                    } catch (Exception ignored) {
                    }
                }
                System.out.println("Thread Died!");
            }));
            threadsList.get(i).start();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        HashMap<String, Command> map = new HashMap<>();
        map.put("A", new Command() {
            @Override
            public void doCommand() {
                System.out.println("hello");

            }

            @Override
            public int getPriority() {
                return 1;
            }
        });
        map.put("B", new Command() {
            @Override
            public void doCommand() {
                System.out.println("world");

            }

            @Override
            public int getPriority() {
                return 2;
            }
        });
        B2018 sol = new B2018(map);
        sol.submit("B");
        sol.submit("A");
        sol.submit("A");
        sol.submit("B");
        sol.submit("A");
        sol.start(10);
        Thread.sleep(2000);
        sol.stop();
    }
}
