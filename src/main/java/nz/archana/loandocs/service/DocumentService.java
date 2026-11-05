package nz.archana.loandocs.service;

import nz.archana.loandocs.dto.GenerateRequest;
import nz.archana.loandocs.exception.ResourceNotFoundException;
import nz.archana.loandocs.model.Customer;
import nz.archana.loandocs.model.GeneratedDocument;
import nz.archana.loandocs.model.Template;
import nz.archana.loandocs.repository.CustomerRepository;
import nz.archana.loandocs.repository.GeneratedDocumentRepository;
import nz.archana.loandocs.repository.TemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DocumentService {

    private final TemplateRepository templateRepository;
    private final CustomerRepository customerRepository;
    private final GeneratedDocumentRepository documentRepository;

    public DocumentService(TemplateRepository templateRepository,
                           CustomerRepository customerRepository,
                           GeneratedDocumentRepository documentRepository) {
        this.templateRepository = templateRepository;
        this.customerRepository = customerRepository;
        this.documentRepository = documentRepository;
    }

    @Transactional
    public GeneratedDocument generate(GenerateRequest request) {
        Template template = templateRepository.findById(request.templateId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Template not found: " + request.templateId()));

        String content = PlaceholderFiller.fill(template.getContent(), request.data());

        Customer customer = customerRepository.findByEmail(request.customerEmail())
                .orElseGet(() -> customerRepository.save(
                        new Customer(request.customerName(), request.customerEmail())));

        return documentRepository.save(new GeneratedDocument(template, customer, content));
    }

    @Transactional(readOnly = true)
    public GeneratedDocument get(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<GeneratedDocument> findByCustomer(Long customerId) {
        return documentRepository.findByCustomerId(customerId);
    }
}
