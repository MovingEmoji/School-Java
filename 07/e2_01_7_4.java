//Naoya Iida
public class e2_01_7_4 {
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
        for (int i = 0; i < 10000; i++) {
            int tmp = sx; 
            tmp++;        
            sx = tmp;     
        }
        System.out.println(Thread.currentThread().getName() + ": " + sx);
    }
}
    
/*
thread2: 12979
thread3: 13653
thread1: 10564

thread1: 12322
thread3: 25543
thread2: 18860
 */