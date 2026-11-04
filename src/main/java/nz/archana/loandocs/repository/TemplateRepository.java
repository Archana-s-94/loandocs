package nz.archana.loandocs.repository;

import nz.archana.loandocs.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long> {
}