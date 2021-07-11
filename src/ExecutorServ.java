import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServ {
    ExecutorService exe1 = Executors.newSingleThreadExecutor();
    ExecutorService exe2 = Executors.newCachedThreadPool();
    ExecutorService exe3 = Executors.newFixedThreadPool(5);
    ExecutorService exe4 = Executors.newWorkStealingPool();

    public static void main(String[] args) {
        ExecutorServ exeServ = new ExecutorServ();
//        for (int i = 0; i < 20; i++) {
//            int finalI = i;
//            exeServ.exe1.execute(() -> {
//                System.out.println("SingleThreadExecutor task num: " + finalI + " Thread num: " + Thread.currentThread().getId());
//            });
//        }
//        for (int i = 0; i < 20; i++) {
//            int finalI = i;
//            exeServ.exe2.execute(() -> {
//                System.out.println("CachedThreadPool task num: " + finalI + " Thread num: " + Thread.currentThread().getId());
//            });
//        }
//        for (int i = 0; i < 20; i++) {
//            int finalI = i;
//            exeServ.exe3.execute(() -> {
//                System.out.println("FixedThreadPool task num: " + finalI + " Thread num: " + Thread.currentThread().getId());
//            });
//        }
//        for (int i = 0; i < 20; i++) {
//            int finalI = i;
//            exeServ.exe4.execute(() -> {
//                System.out.println("WorkStealingPool task num: " + finalI + " Thread num: " + Thread.currentThread().getId());
//            });
//        }
    }
}