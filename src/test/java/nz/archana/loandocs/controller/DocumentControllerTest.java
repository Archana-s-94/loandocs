package nz.archana.loandocs.controller;

import nz.archana.loandocs.exception.MissingPlaceholderException;
import nz.archana.loandocs.exception.ResourceNotFoundException;
import nz.archana.loandocs.model.Customer;
import nz.archana.loandocs.model.GeneratedDocument;
import nz.archana.loandocs.model.Template;
import nz.archana.loandocs.service.DocumentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DocumentController.class)
class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DocumentService service;

    private static final String VALID_BODY = """
            {"templateId": 1, "customerName": "Jane Smith", "customerEmail": "jane@example.com",
             "data": {"customerName": "Jane Smith", "amount": "$250,000"}}
            """;

    @Test
    void generate_returns201WithFilledContent() throws Exception {
        GeneratedDocument doc = new GeneratedDocument(
                new Template("Loan approval", "Dear {{customerName}}, your loan of {{amount}} is approved."),
                new Customer("Jane Smith", "jane@example.com"),
                "Dear Jane Smith, your loan of $250,000 is approved.");
        when(service.generate(any())).thenReturn(doc);

        mockMvc.perform(post("/documents/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value("Dear Jane Smith, your loan of $250,000 is approved."))
                .andExpect(jsonPath("$.status").value("GENERATED"));
    }

    @Test
    void generate_returns400WhenPlaceholderMissing() throws Exception {
        when(service.generate(any())).thenThrow(new MissingPlaceholderException(Set.of("amount")));

        mockMvc.perform(post("/documents/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(containsString("amount")));
    }

    @Test
    void generate_returns404WhenTemplateUnknown() throws Exception {
        when(service.generate(any())).thenThrow(new ResourceNotFoundException("Template not found: 999"));

        mockMvc.perform(post("/documents/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Template not found: 999"));
    }

    @Test
    void generate_returns400ForInvalidEmail() throws Exception {
        String badEmail = """
                {"templateId": 1, "customerName": "Jane", "customerEmail": "not-an-email",
                 "data": {"amount": "1"}}
                """;

        mockMvc.perform(post("/documents/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badEmail))
                .andExpect(status().isBadRequest());
    }
}
