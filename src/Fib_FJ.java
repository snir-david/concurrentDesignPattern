import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Fib_FJ extends RecursiveTask<Integer> {
    int num;
    public Fib_FJ(int num){
        this.num = num;
    }
    @Override
    public Integer compute(){
        if(num<=1)
            return num;
        Fib_FJ fib1 = new Fib_FJ(num-1);
        fib1.fork();
        Fib_FJ fib2 = new Fib_FJ(num-2);
        return fib2.compute() + fib1.join();
    }

    public static void main(String[] args) {
        Fib_FJ fib = new Fib_FJ(45);
        ForkJoinPool pool = new ForkJoinPool();
        System.out.println(pool.invoke(fib));
    }
}
