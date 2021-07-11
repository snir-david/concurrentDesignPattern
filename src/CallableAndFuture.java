import java.util.concurrent.*;

class myCallable1 implements Callable<String>{
    @Override
    public String call() throws Exception {
        Thread.sleep(5000);
        return "Callable 1 Return";
    }
}

class myCallable2 implements Callable<String>{
    @Override
    public String call() throws Exception {
        return "Callable 2 Return";
    }
}

public class CallableAndFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService exe = Executors.newFixedThreadPool(5);
        Future<String> future1 =  exe.submit(new myCallable1());
        Future<String> future2 =  exe.submit(new myCallable2());
        System.out.println("Print in between");
        String s2 = future2.get();
        System.out.println(s2);
        String s1 = future1.get();
        s2 = future2.get();
        System.out.println(s1);
        System.out.println(s2);
        exe.shutdownNow();
    }
}

