//Naoya Iida
public class e2_01_7_5 {
    public static void main(String[] args) {
        Thread t1 = new Thread(new MyRunnable(), "thread1");
        Thread t2 = new Thread(new MyRunnable(), "thread2");
        Thread t3 = new Thread(new MyRunnable(), "thread3");
        t1.start();
        t2.start();
        t3.start();
    }
}
class MyRunnable implements Runnable {
    private static int sx = 0; //共有変数
    @Override
    public void run() {
        staticMethod();
    }
    public static synchronized void staticMethod() {
        for (int i = 0; i < 10000; i++) {
            int tmp = sx;
            tmp++;        
            sx = tmp; 
        }
        System.out.println(Thread.currentThread().getName() + ": " + sx);
    }
}
    
/*
thread1: 10000
thread3: 20000
thread2: 30000
 */