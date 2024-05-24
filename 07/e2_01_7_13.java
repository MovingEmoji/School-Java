import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class e2_01_7_13 {
    public static void main(String[] args) throws Exception {
        int threadCount = 4;
        ExecutorService service = Executors.newFixedThreadPool(threadCount);
        List<Future<Long>> futures = new ArrayList<>();
        long start = System.currentTimeMillis();
        int range = 250000;
        for (int i = 0; i < threadCount; i++) {
            futures.add(service.submit(new PrimeCalculator(range - 249999, range)));
            range += 250000;
        }
        long totalPrimes = 0;
        for (Future<Long> future : futures) {
            totalPrimes += future.get();
        }
        service.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("Total primes: " + totalPrimes);
        System.out.println("Time taken: " + (end - start) + " ms");
    }
}
class PrimeCalculator implements Callable<Long> {
    private int start;
    private int end;
    public PrimeCalculator(int start, int end) {
        this.start = start;
        this.end = end;
    }
    @Override
    public Long call() throws Exception {
        return calculatePrimes(start, end);
    }
    private static long calculatePrimes(int start, int end) {
        long count = 0;
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) 
                count++;
        }
        return count;
    }
    private static boolean isPrime(int number) {
    if (number <= 1) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}
/*
SingleThread
Total primes: 78498
Time taken: 196 ms

MultiThread
Total primes: 78498
Time taken: 76 ms
 */