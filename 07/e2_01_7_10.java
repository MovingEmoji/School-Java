//Naoya Iida
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
public class e2_01_7_10 {
    public static void main(String[] args) throws InterruptedException{
        long start = System.currentTimeMillis();
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        Thread t1 = new Thread(new Producer(queue), "thread1");
        Thread t2 = new Thread(new Consumer(queue), "thread2");
        t1.start();
        t2.start();
        t1.join(); //thread1の終了まで待機
        t2.join(); //thread2の終了まで待機
        long end = System.currentTimeMillis();
        System.out.println(queue.size());
        System.out.println((end - start) + " (ms)");
    }
}
class Producer implements Runnable {
    private BlockingQueue<Integer> queue;
    public Producer(BlockingQueue<Integer> queue){
        this.queue = queue;
    }
    @Override
    public void run() {
        for (int i = 0; i < 200000; i++)
            queue.add(i); //データを生産
    }
}
class Consumer implements Runnable {
    private BlockingQueue<Integer> queue;
    public Consumer(BlockingQueue<Integer> queue){
        this.queue = queue;
    }
    private void doSomething(int x){}
    @Override
    public void run() {
        for (Integer x : queue)
            doSomething(x); //データを消費
    }
}
/*
200000
17 (ms)
 */