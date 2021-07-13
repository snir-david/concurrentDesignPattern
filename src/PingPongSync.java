import javax.swing.text.Style;
import java.io.ObjectStreamConstants;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class PingPongSync {
    volatile Object pongLock = new Object();
    volatile Object pingLock = new Object();

    static class Ping extends TimerTask {
        Object pongLock, pingLock;

        public Ping(Object pingLock, Object pongLock) {
            this.pingLock = pingLock;
            this.pongLock = pongLock;
        }

        @Override
        public void run() {
//            System.out.println("Ping");
            while (true) {
                System.out.println("Ping");
                pongLock.notify();
                try {
                    pingLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Pong extends TimerTask {
        Object pongLock, pingLock;

        public Pong(Object pingLock, Object pongLock) {
            this.pingLock = pingLock;
            this.pongLock = pongLock;
        }

        @Override
        public void run() {
//            System.out.println("Pong");
            while (true) {
                System.out.println("Pong");
                pingLock.notify();
                try {
                    pongLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PingPongSync pps = new PingPongSync();
        Ping ping = new Ping(pps.pingLock, pps.pongLock);
        Pong pong = new Pong(pps.pingLock, pps.pongLock);
        Thread t1 = new Thread(() -> {
            ping.run();
        });
        Thread t2 = new Thread(() -> {
            pong.run();
        });
        t1.start();
        t2.start();
//        Timer t = new Timer();
//        t.scheduleAtFixedRate(ping, 0, 1000);
//        t.scheduleAtFixedRate(pong, 500, 1000);
//        Thread.sleep(5000);
//        ping.cancel();
//        pong.cancel();
//        t.cancel();
//        System.out.println("Finish Ping Pong!");
    }

}
