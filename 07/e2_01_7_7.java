//Naoya Iida
public class e2_01_7_7 {
    public static void main(String[] args) {
        MyData md = new MyData(0);
        MyRunnable r = new MyRunnable(md);
        new Thread(r, "thread1").start();
        new Thread(r, "thread2").start();
        new Thread(r, "thread3").start();
        try {
            Thread.sleep(1000); // ms
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ": " + md.getX());
    }
}

class MyData {
    private int x;
    public MyData(int x) {
        this.x = x;
    }
    public int getX() {return x;}
    public synchronized void incX() {x++;}
}

class MyRunnable implements Runnable {
    private MyData md;
    public MyRunnable(MyData md){
        this.md = md;
    }
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++)
            md.incX();
    }
}
    
/*
main: 30000
 */