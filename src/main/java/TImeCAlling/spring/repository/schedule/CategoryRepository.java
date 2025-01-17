package TImeCAlling.spring.repository.schedule;

import TImeCAlling.spring.domain.Category;
import TImeCAlling.spring.domain.Schedule;
import TImeCAlling.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
