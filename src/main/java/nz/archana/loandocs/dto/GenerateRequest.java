package nz.archana.loandocs.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record GenerateRequest(
        @NotNull Long templateId,
        @NotBlank String customerName,
        @NotBlank @Email String customerEmail,
        @NotNull Map<String, String> data) {
}