public class GuardedSuspension {
    boolean someCondition = false;
    int someDest = 0;

    synchronized void doSomethingIfCond() throws InterruptedException {
        while (!someCondition) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Condition is met!");
    }

    synchronized void changeDest(int x) {
        someDest += x;
        if (someDest > 100) {
            someCondition = true;
            notifyAll();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        GuardedSuspension gs = new GuardedSuspension();
        new Thread(() -> {
            try {
                gs.doSomethingIfCond();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        for (int i = 0; i < 15; i++) {
            gs.changeDest(10);
        }
    }
}
