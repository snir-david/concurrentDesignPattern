import javax.swing.text.Style;
import java.util.Timer;
import java.util.TimerTask;

public class PingPongSync {
     static class Ping extends TimerTask{
         @Override
         public void run() {
             System.out.println("Ping");
         }
     }
    static class Pong extends TimerTask{
        @Override
        public void run() {
            System.out.println("Pong");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Ping ping = new Ping();
        Pong pong = new Pong();
        Timer t = new Timer();
        t.scheduleAtFixedRate(ping, 0, 1000);
        t.scheduleAtFixedRate(pong, 500, 1000);
        Thread.sleep(5000);
        ping.cancel();
        pong.cancel();
        t.cancel();
        System.out.println("Finish Ping Pong!");
    }

}
