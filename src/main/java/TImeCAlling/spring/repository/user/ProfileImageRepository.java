package TImeCAlling.spring.repository.user;

import TImeCAlling.spring.domain.ProfileImage;
import TImeCAlling.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

    ProfileImage findByUser(User user);
}
