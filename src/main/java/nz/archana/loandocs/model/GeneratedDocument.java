package nz.archana.loandocs.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "generated_documents")
public class GeneratedDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "template_id")
    private Template template;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String status = "GENERATED";

    private LocalDateTime createdAt = LocalDateTime.now();

    protected GeneratedDocument() {
    }

    public GeneratedDocument(Template template, Customer customer, String content) {
        this.template = template;
        this.customer = customer;
        this.content = content;
    }

    public Long getId() { return id; }
    public Template getTemplate() { return template; }
    public Customer getCustomer() { return customer; }
    public String getContent() { return content; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}