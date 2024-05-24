//Naoya Iida
public class e2_01_7_3 {
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
    @Override
    public void run() {
        int x = 0; //ローカル変数は各スレッド毎に割り当てられる
        for (int i = 0; i < 10000; i++) {
            x++;
        }
        System.out.println(Thread.currentThread().getName() + ": " + x);
    }
}
/*
thread2: 10000
thread1: 10000
thread3: 10000
 */