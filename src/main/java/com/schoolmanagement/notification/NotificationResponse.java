package com.schoolmanagement.notification;

import java.time.Instant;

public record NotificationResponse(
        Long id,
        Long studentId,
        String studentName,
        String recipientPhone,
        String message,
        String status,
        String providerReference,
        int attempts,
        String lastError,
        Instant createdAt,
        Instant sentAt
) {
    public static NotificationResponse from(Notification n) {
        return new NotificationResponse(
                n.getId(),
                n.getStudent().getId(),
                n.getStudent().getFirstName() + " " + n.getStudent().getLastName(),
                n.getRecipientPhone(),
                n.getMessage(),
                n.getStatus().name(),
                n.getProviderReference(),
                n.getAttempts(),
                n.getLastError(),
                n.getCreatedAt(),
                n.getSentAt()
        );
    }
}