package TImeCAlling.spring.service.user;

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
    public UserDetails loadUserByUsername(String nickname) {

        return userRepository.findByNickname(nickname).orElseThrow(
                () -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));
    }

    public UserDetails loadUserByUserId(Long id) throws UsernameNotFoundException {

        return userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("유저를 찾을 수 없습니다. userId: " + id));
    }
}