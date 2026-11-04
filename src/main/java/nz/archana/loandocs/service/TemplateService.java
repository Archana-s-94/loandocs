package nz.archana.loandocs.service;

import nz.archana.loandocs.model.Template;
import nz.archana.loandocs.repository.TemplateRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TemplateService {

    private final TemplateRepository repository;

    public TemplateService(TemplateRepository repository) {
        this.repository = repository;
    }

    public Template create(Template template) {
        return repository.save(template);
    }

    public List<Template> findAll() {
        return repository.findAll();
    }
}
