package TImeCAlling.spring.repository.user;

import TImeCAlling.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);
    Optional<User> findBySocialId(Long socialId);
    Optional<User> findByRefreshToken(String refreshToken);
}
