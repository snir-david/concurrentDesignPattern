class bigObject {
    private Integer counter = 0;

    bigObject(String funcName) {
        counter++;
        System.out.println("Big Object CTOR called from " + funcName + "Counter : " + counter);
    }
}

public class DoubleChecked {
    private volatile bigObject someBigObject;

    private static class JVMStaticSol {
        private static final bigObject staticBigO = new bigObject("JVMStaticSol");
        private static bigObject init() {
            return staticBigO;
        }
    }


    public bigObject getBigObjectNotThreadSafe() throws InterruptedException {
        if (someBigObject == null) {
            Thread.sleep(1000);
            someBigObject = new bigObject("getDoubleCheckedObjectNotThreadSafe");
        }
        return someBigObject;
    }

    public bigObject getBigObjectThreadSafe() throws InterruptedException {
        bigObject DC = someBigObject;
        if (someBigObject == null) {
            synchronized (this) {
                DC = someBigObject;
                if (someBigObject == null) {
                    Thread.sleep(1000);
                    someBigObject = DC = new bigObject("getDoubleCheckedObjectThreadSafe");
                }
            }
        }
        return DC;
    }

    public bigObject getStaticObject() {
        this.someBigObject = JVMStaticSol.init();
        return someBigObject;
    }

    public static void main(String[] args) throws InterruptedException {
//        DoubleChecked dcNotSafe = new DoubleChecked();
//        DoubleChecked dcSafe = new DoubleChecked();
//        System.out.println("Not Thread Safe Locking");
//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> {
//                try {
//                    System.out.println("Big Object Address is - " + dcNotSafe.getBigObjectNotThreadSafe());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//        }
//        // Sleep for 4 seconds - let Not Safe Threads run
//        Thread.sleep(4000);
//        System.out.println("Thread Safe Locking");
//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> {
//                try {
//                    System.out.println("Big Object Address is - " + dcSafe.getBigObjectThreadSafe());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//        }
        DoubleChecked dcStat = new DoubleChecked();
        System.out.println("Big Object Address is - " + JVMStaticSol.staticBigO);
        dcStat.getStaticObject();
        System.out.println("Big Object Address is - " + JVMStaticSol.staticBigO);
        System.out.println("Big Object Address is - " + dcStat.someBigObject);
    }
}
