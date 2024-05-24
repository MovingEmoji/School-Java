//Naoya Iida
public class e2_01_7_6 {
    public static void main(String[] args) {
        MyRunnable r = new MyRunnable();
        new Thread(r, "thread1").start();
        new Thread(r, "thread2").start();
        new Thread(r, "thread3").start();
        try {
            Thread.sleep(1000); // ms
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ": " + r.getX());
    }
}
class MyRunnable implements Runnable {
    private int x = 0;
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++)
            incX();
    }
    private synchronized void incX() {x++;}
    public int getX() {return x;}
}
    
    
/*
main: 30000
 */