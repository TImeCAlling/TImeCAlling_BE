package TImeCAlling.spring.service.user;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.UserHandler;
import TImeCAlling.spring.domain.User;
import TImeCAlling.spring.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;
    @Override
    public User findOne(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
    }
}
