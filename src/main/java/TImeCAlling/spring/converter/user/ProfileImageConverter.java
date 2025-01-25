package TImeCAlling.spring.converter.user;

import TImeCAlling.spring.domain.ProfileImage;
import TImeCAlling.spring.domain.User;

public class ProfileImageConverter {

    public static ProfileImage toProfileImage(User user, String imageUrl, String fileName) {

        return ProfileImage.builder()
                .fileName(fileName)
                .fileUrl(imageUrl)
                .user(user)
                .build();
    }
}
