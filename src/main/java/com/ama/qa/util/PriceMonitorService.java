package com.ama.qa.util;

/**
 * Service that monitors price changes for products and triggers email notifications
 * when the price falls below or equals a specified threshold.
 */
public class PriceMonitorService {

    private final EmailNotificationUtil emailNotificationUtil;

    public PriceMonitorService() {
        this.emailNotificationUtil = new EmailNotificationUtil();
    }

    public PriceMonitorService(EmailNotificationUtil emailNotificationUtil) {
        this.emailNotificationUtil = emailNotificationUtil;
    }

    /**
     * Checks current product price against specified threshold.
     * If current price <= thresholdPrice, triggers email notification alert.
     *
     * @param productName    Name of product
     * @param currentPrice   Current price found during automation
     * @param thresholdPrice Target price threshold
     * @param recipientEmail Notification email destination
     * @return true if price drop condition was met and notification sent, false otherwise
     */
    public boolean checkAndNotify(String productName, double currentPrice, double thresholdPrice, String recipientEmail) {
        System.out.println(String.format("Monitoring Product: '%s' | Current Price: ₹%.2f | Target Threshold: ₹%.2f",
                productName, currentPrice, thresholdPrice));

        if (currentPrice <= thresholdPrice) {
            System.out.println("ALERT: Product price ₹" + currentPrice + " has dropped below/equal to threshold ₹" + thresholdPrice + "!");
            return emailNotificationUtil.sendPriceDropAlert(productName, currentPrice, thresholdPrice, recipientEmail);
        } else {
            System.out.println("NO ALERT: Current price ₹" + currentPrice + " is still above threshold ₹" + thresholdPrice + ".");
            return false;
        }
    }

    public EmailNotificationUtil getEmailNotificationUtil() {
        return emailNotificationUtil;
    }
}
