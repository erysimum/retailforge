package com.amitshahi.retailforge.notification.email;

import java.util.Objects;

/**
 * Provider-neutral email representation.
 *
 * idempotencyKey is carried for providers that support idempotency,
 * such as Resend.
 *
 * SMTP does not support a standard idempotency-key mechanism.
 */
public record EmailMessage(String to, String subject, String body, String idempotencyKey) {

    public EmailMessage {
        Objects.requireNonNull(to, "to must not be null");
        Objects.requireNonNull(subject, "subject must not be null");
        Objects.requireNonNull(body, "body must not be null");
        Objects.requireNonNull(idempotencyKey, "idempotencyKey must not be null");
    }
}
