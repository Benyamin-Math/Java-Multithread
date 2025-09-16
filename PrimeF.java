package com.deitel.ch17;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.*;

public class PrimeF {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("لطفا عدد n را وارد کنید: ");
        int n = scanner.nextInt();

        System.out.print("لطفا تعداد تردها را وارد کنید: ");
        int m = scanner.nextInt();

        scanner.close();

        ExecutorService executor = Executors.newFixedThreadPool(m);
        Future<?>[] futures = new Future[m];

        for (int i = 0; i < m; i++) {
            final int threadId = i;
            final int start = (n * i / m) + 1;
            final int end = (n * (i + 1)) / m;

            // ارسال task به executor و دریافت Future
            futures[i] = executor.submit(() -> {
                try {
                    findPrimesInRange(start, end, threadId);
                } catch (IOException e) {
                    System.err.println("خطا در ترد " + threadId + ": " + e.getMessage());
                }
            });
        }

        // منتظر ماندن برای پایان تمام tasks
        for (int i = 0; i < m; i++) {
            try {
                futures[i].get(); // منتظر ماندن برای پایان این task
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("خطا در اجرای ترد " + i + ": " + e.getMessage());
            }
        }

        executor.shutdown(); // خاموش کردن executor

        System.out.println("پردازش تمام شد! نتایج در فایل‌های prime_thread_X.txt ذخیره شدند.");
    }


    private static boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number == 2) return true;
        if (number % 2 == 0) return false;

        for (int i = 3; i <= Math.sqrt(number); i += 2) {
            if (number % i == 0) return false;
        }
        return true;
    }

    private static void findPrimesInRange(int start, int end, int threadId) throws IOException {
        String fileName = "prime_thread_" + threadId + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("اعداد اول بین " + start + " و " + end + ":\n");
            writer.write("================================\n");

            int count = 0;
            for (int i = start; i <= end; i++) {
                if (isPrime(i)) {
                    writer.write(i + "\n");
                    count++;
                }
            }

            writer.write("================================\n");
            writer.write("تعداد اعداد اول پیدا شده: " + count + "\n");
        }

        System.out.println("ترد " + threadId + " کار خود را به پایان رساند. بازه: " + start + " تا " + end);
    }
}