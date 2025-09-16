interface PaymentProcessor {
    void processPayment(double amount);
}

class PayPalProcessor implements PaymentProcessor {
    public void processPayment(double amount) {
        System.out.println("Paying " + amount + " with PayPal");
    }
}

class StripeProcessor implements PaymentProcessor {
    public void processPayment(double amount) {
        System.out.println("Paying " + amount + " with Stripe");
    }
}

class ZarinpalProcessor implements PaymentProcessor {
    public void processPayment(double amount) {
        System.out.println("Paying " + amount + " with Zarinpal");
    }
}

class OrderService {
    public void processOrder(double amount, PaymentProcessor processor) {
        // فقط می‌گوید: "پردازش کن!" بدون اینکه بداند چگونه!
        processor.processPayment(amount);
    }
}

public class Main {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();

        // پرداخت با PayPal
        orderService.processOrder(100.0, new PayPalProcessor());

        // پرداخت با Stripe
        orderService.processOrder(200.0, new StripeProcessor());

        // فردا اگر درگاه جدید "IDPay" اضافه شد:
        class IDPayProcessor implements PaymentProcessor {
            public void processPayment(double amount) {
                System.out.println("Paying " + amount + " with IDPay");
            }
        }

        // بدون هیچ تغییری در OrderService می‌توانیم از آن استفاده کنیم!
        orderService.processOrder(300.0, new IDPayProcessor());
    }
}