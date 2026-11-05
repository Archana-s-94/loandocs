package nz.archana.loandocs.repository;

import nz.archana.loandocs.model.GeneratedDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GeneratedDocumentRepository extends JpaRepository<GeneratedDocument, Long> {
    List<GeneratedDocument> findByCustomerId(Long customerId);
}
