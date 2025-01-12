package TImeCAlling.spring.service.user;


import TImeCAlling.spring.domain.User;

public interface UserQueryService {
    User findOne(Long id);
}
