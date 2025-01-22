package TImeCAlling.spring.converter.user;

import TImeCAlling.spring.domain.ProfileImage;
import TImeCAlling.spring.domain.User;

public class ProfileImageConverter {

    public static ProfileImage toProfileImage(User user, String profileUrl) {
        Long userId = user.getId();
        String fileName = userId + "_profile.jpg";

        return ProfileImage.builder()
                .fileName(fileName)
                .fileUrl(profileUrl)
                .user(user)
                .build();
    }
}
