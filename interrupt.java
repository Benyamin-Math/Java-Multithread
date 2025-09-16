import java.util.concurrent.*;

 class RestaurantPriceChecker {

    public static void main(String[] args) {
        // ایجاد یک مدیر (ExecutorService) با ۳ کارگر (Thread)
        ExecutorService executor = Executors.newFixedThreadPool(3);

        System.out.println("در حال دریافت قیمت از رستوران‌ها...");

        // ارسال درخواست به سه رستوران مختلف
        Future<Integer> future1 = executor.submit(() -> {
            System.out.println("در حال تماس با رستوران A...");
            Thread.sleep(2000); // 2 ثانیه زمان برای پاسخ
            return 25000; // قیمت رستوران A
        });

        Future<Integer> future2 = executor.submit(() -> {
            System.out.println("در حال تماس با رستوران B...");
            Thread.sleep(3000); // 3 ثانیه زمان برای پاسخ
            return 23000; // قیمت رستوران B
        });

        Future<Integer> future3 = executor.submit(() -> {
            System.out.println("در حال تماس با رستوران C...");
            Thread.sleep(1000); // 1 ثانیه زمان برای پاسخ
            return 27000; // قیمت رستوران C
        });

        try {
            // منتظر ماندن برای تمام نتایج
            int priceA = future1.get(); // منتظر رستوران A می‌ماند
            int priceB = future2.get(); // منتظر رستوران B می‌ماند
            int priceC = future3.get(); // منتظر رستوران C می‌ماند

            System.out.println("\nقیمت‌ها دریافت شد:");
            System.out.println("رستوران A: " + priceA + " تومان");
            System.out.println("رستوران B: " + priceB + " تومان");
            System.out.println("رستوران C: " + priceC + " تومان");

            // پیدا کردن بهترین قیمت
            int bestPrice = Math.min(Math.min(priceA, priceB), priceC);
            System.out.println("\nبهترین قیمت: " + bestPrice + " تومان");

        } catch (Exception e) {
            System.out.println("خطا در دریافت قیمت!");
        } finally {
            // بستن Executor
            executor.shutdown();
        }
    }
}