package nz.archana.loandocs.dto;

import nz.archana.loandocs.model.GeneratedDocument;
import java.time.LocalDateTime;

public record DocumentResponse(
        Long id,
        Long templateId,
        Long customerId,
        String customerName,
        String content,
        String status,
        LocalDateTime createdAt) {

    public static DocumentResponse from(GeneratedDocument d) {
        return new DocumentResponse(
                d.getId(),
                d.getTemplate().getId(),
                d.getCustomer().getId(),
                d.getCustomer().getName(),
                d.getContent(),
                d.getStatus(),
                d.getCreatedAt());
    }
}
