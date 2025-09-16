// Thread برای پخش موسیقی
class MusicPlayer implements Runnable {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("موسیقی در حال پخش...");
            try { Thread.sleep(1000); } catch (Exception e) {}
        }
    }
}

// Thread برای شمارش
class Counter implements Runnable {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("شمارش: " + i);
            try { Thread.sleep(10000); } catch (Exception e) {}
        }
    }
}

 class Main2 {
    public static void main(String[] args) {
        // ساخت دو Thread
        Thread musicThread = new Thread(new MusicPlayer());
        Thread counterThread = new Thread(new Counter());

        // شروع اجرای همزمان
        musicThread.start();
        counterThread.start();
    }
}