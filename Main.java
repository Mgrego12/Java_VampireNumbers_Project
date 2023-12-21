
/*
Matt Gregorek Homework 3 CMSC 412
Java Threads , Vampire Numbers.
Main Thread (M) creates 2 worker Threads and starts
Main thread will join 2 workers at End of process to compute Total
Due Date: 11/8/2022

1260 is a Vampire Number, 21x60(fangs) = 1260
first thread will search for even
second will search for odds
[100.000 , 999.999]..
use Thread method, start() and join()
run() method to sort for the Vamp Numbers
display results to terminal. 

 */
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private static ArrayList vampireArray = new ArrayList();

    public static void main(String[] args) throws InterruptedException {
        Thread worker1 = new Thread(new Worker(0));
        Thread worker2 = new Thread(new Worker(1));
        worker1.start();
        worker2.start();
        worker1.join();
        worker2.join();
        System.out.println("The TOTAL number of Vampire numbers found is... " + vampireArray.size());
    }

    private static class Worker implements Runnable {
        private int i;

        public Worker(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            int numFound = 0;
            for (int num = 100000 + i; num < 1000000; num += 2) {
                if (isVampNum(num)) {
                    addVampNumber(num);
                    System.out.println((i == 0 ? "First worker" : "Second worker")
                            + " found: " + num);
                    numFound++;
                }
            }
            System.out.println((i == 0 ? "First worker" : "Second worker") +
                    " found " + numFound + " Vampire numbers");
        }

        private static boolean fangCheck(int num, int fang1, int fang2) {
            String numStr = String.valueOf(num);
            String fang1Str = String.valueOf(fang1);
            String fang2Str = String.valueOf(fang2);

            if (fang1Str.length() != numStr.length() / 2 || fang2Str.length() != numStr.length() / 2)
                return false;
            if (fang1Str.endsWith("0") && fang2Str.endsWith("0"))
                return false;

            char[] numDigits = numStr.toCharArray();
            char[] fangDigits = (fang1Str + fang2Str).toCharArray();
            Arrays.sort(numDigits);
            Arrays.sort(fangDigits);
            return Arrays.equals(numDigits, fangDigits);
        }

        private static boolean isVampNum(int num) {
            if (String.valueOf(num).length() % 2 != 0)
                return false;

            for (int fangOne = 2; fangOne <= Math.sqrt(num); fangOne++) {
                if (num % fangOne == 0) {
                    int fangTwo = num / fangOne;
                    if (fangCheck(num, fangOne, fangTwo)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private static synchronized void addVampNumber(int num) {
        vampireArray.add(num);
    }
}