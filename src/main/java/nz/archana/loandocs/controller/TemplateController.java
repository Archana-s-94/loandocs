package nz.archana.loandocs.controller;

import jakarta.validation.Valid;
import nz.archana.loandocs.model.Template;
import nz.archana.loandocs.service.TemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/templates")
public class TemplateController {

    private final TemplateService service;

    public TemplateController(TemplateService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Template create(@Valid @RequestBody Template template) {
        return service.create(template);
    }

    @GetMapping
    public List<Template> list() {
        return service.findAll();
    }
}
