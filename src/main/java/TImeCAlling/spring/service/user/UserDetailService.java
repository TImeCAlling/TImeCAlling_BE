package TImeCAlling.spring.service.user;

import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {

        User user = userRepository.findByNickname(nickname);
        if (user == null) {
            throw new UsernameNotFoundException("해당 유저가 없습니다.");
        }
        return user;
    }
}
