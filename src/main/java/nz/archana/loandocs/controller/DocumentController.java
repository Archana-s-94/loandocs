package nz.archana.loandocs.controller;

import jakarta.validation.Valid;
import nz.archana.loandocs.dto.DocumentResponse;
import nz.archana.loandocs.dto.GenerateRequest;
import nz.archana.loandocs.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService service;

    public DocumentController(DocumentService service) {
        this.service = service;
    }

    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentResponse generate(@Valid @RequestBody GenerateRequest request) {
        return DocumentResponse.from(service.generate(request));
    }

    @GetMapping("/{id}")
    public DocumentResponse get(@PathVariable Long id) {
        return DocumentResponse.from(service.get(id));
    }

    @GetMapping
    public List<DocumentResponse> byCustomer(@RequestParam Long customerId) {
        return service.findByCustomer(customerId).stream()
                .map(DocumentResponse::from)
                .toList();
    }
}
