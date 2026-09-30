package com.ama.qa.util;

import java.time.LocalDateTime;

/**
 * Utility service to handle email notification dispatching for price alerts.
 */
public class EmailNotificationUtil {

    private boolean lastNotificationSent = false;
    private String lastNotificationLog = "";

    /**
     * Sends a price drop notification email.
     *
     * @param productName    Name of the monitored product
     * @param currentPrice   Current price observed on website
     * @param thresholdPrice Target price threshold set by user
     * @param recipientEmail Recipient email address
     * @return true if notification dispatched successfully
     */
    public boolean sendPriceDropAlert(String productName, double currentPrice, double thresholdPrice, String recipientEmail) {
        String subject = "PRICE DROP ALERT: " + productName + " is now ₹" + currentPrice + "!";
        String body = String.format(
            "Hello,\n\nGood news! The product '%s' has dropped below your target price threshold.\n\n" +
            "• Current Price: ₹%.2f\n" +
            "• Target Threshold: ₹%.2f\n" +
            "• Savings: ₹%.2f\n" +
            "• Timestamp: %s\n\n" +
            "Buy it now before stock runs out!",
            productName, currentPrice, thresholdPrice, (thresholdPrice - currentPrice), LocalDateTime.now()
        );

        return sendEmail(recipientEmail, subject, body);
    }

    /**
     * Core method to simulate/dispatch email notifications.
     */
    public boolean sendEmail(String toEmail, String subject, String body) {
        if (toEmail == null || toEmail.trim().isEmpty()) {
            System.err.println("Failed to send email: Recipient email is null or empty.");
            this.lastNotificationSent = false;
            return false;
        }

        // Simulate Email Dispatch & Log Details
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("================ EMAIL DISPATCH NOTIFICATION ================\n");
        logBuilder.append("To: ").append(toEmail).append("\n");
        logBuilder.append("Subject: ").append(subject).append("\n");
        logBuilder.append("Body:\n").append(body).append("\n");
        logBuilder.append("=============================================================");

        this.lastNotificationLog = logBuilder.toString();
        System.out.println(this.lastNotificationLog);
        this.lastNotificationSent = true;
        return true;
    }

    public boolean isLastNotificationSent() {
        return lastNotificationSent;
    }

    public String getLastNotificationLog() {
        return lastNotificationLog;
    }
}
