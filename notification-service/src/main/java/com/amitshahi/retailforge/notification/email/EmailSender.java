package com.amitshahi.retailforge.notification.email;

public interface EmailSender {

    /**
     * Sends an email.
     *
     * Throws an unchecked exception if sending fails.
     * Implementations must never silently swallow failures.
     */
    void send(EmailMessage message);
}
