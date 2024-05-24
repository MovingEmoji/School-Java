//Naoya Iida
public class e2_01_7_11 {
    public static void main(String[] args) {
        final Object res1 = new Object();
        final Object res2 = new Object();
        Thread th1 = new Thread(new ResLockerA(res1, res2), "thread1");
        Thread th2 = new Thread(new ResLockerB(res1, res2), "thread2");
        th1.start();
        th2.start();
    }
}
class ResLockerA implements Runnable {
    private Object res1;
    private Object res2;
    public ResLockerA(Object res1, Object res2) {
        this.res1 = res1;
        this.res2 = res2;
    }
    @Override
    public void run() {
        synchronized (res1) {
            System.out.println("[thread1] Locked resource 1");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            synchronized (res2) {
                System.out.println("[thread1] Locked resource 2");
            }
        }
    }
}
class ResLockerB implements Runnable {
    private Object res1;
    private Object res2;
    public ResLockerB(Object res1, Object res2) {
        this.res1 = res1;
        this.res2 = res2;
    }
    @Override
    public void run() {
        synchronized (res2) {
            System.out.println("[thread2] Locked resource 2");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            synchronized (res1) {
                System.out.println("[thread2] Locked resource 1");
            }
        }
    }
}
    
/*
200000
17 (ms)
 */