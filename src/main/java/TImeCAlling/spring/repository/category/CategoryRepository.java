package TImeCAlling.spring.repository.category;

import TImeCAlling.spring.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByMemberId(Long id);
}
