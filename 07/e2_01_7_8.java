import java.util.ArrayList;
import java.util.List;
public class e2_01_7_8 {
    public static void main(String[] args) throws InterruptedException{
        long start = System.currentTimeMillis();
        List<Integer> l = new ArrayList<>();
        Thread t1 = new Thread(new Producer(l), "thread1");
        Thread t2 = new Thread(new Consumer(l), "thread2");
        t1.start();
        t2.start();
        t1.join(); //thread1の終了まで待機
        t2.join(); //thread2の終了まで待機
        long end = System.currentTimeMillis();
        System.out.println(l.size());
        System.out.println((end - start) + " (ms)");
    }
}
class Producer implements Runnable {
    private List<Integer> l;
    public Producer(List<Integer> l){
        this.l = l;
    }
    @Override
    public void run() {
        for (int i = 0; i < 200000; i++)
            l.add(i); //データを生産
    }
}
class Consumer implements Runnable {
    private List<Integer> l;
    public Consumer(List<Integer> l){
        this.l = l;
    }
    private void doSomething(int x){}
    @Override
    public void run() {
        for (Integer x : l)
            doSomething(x); //データを消費
    }
}
    