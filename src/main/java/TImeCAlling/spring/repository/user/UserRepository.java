package TImeCAlling.spring.repository.user;

import TImeCAlling.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);
    Optional<User> findBySocialId(Long socialId);
    Optional<User> findByRefreshToken(String refreshToken);

    @Query("SELECT u.fcmToken FROM User u WHERE u.id = :userId")
    Optional<String> findFcmTokenByUserId(Long userId);
}
