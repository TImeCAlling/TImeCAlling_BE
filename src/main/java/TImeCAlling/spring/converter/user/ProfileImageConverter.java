package TImeCAlling.spring.converter.user;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.S3Handler;
import TImeCAlling.spring.domain.ProfileImage;
import TImeCAlling.spring.domain.User;

import java.net.MalformedURLException;
import java.net.URL;

public class ProfileImageConverter {

    public static ProfileImage toProfileImage(User user, String imageUrl) {

        String fileName;

        try {
            URL url = new URL(imageUrl);
            String path = url.getPath();
            fileName = path.substring(path.lastIndexOf("/") + 1);

        } catch (MalformedURLException e) {
            throw new S3Handler(ErrorStatus.INVALID_URL);
        }

        return ProfileImage.builder()
                .fileName(fileName)
                .fileUrl(imageUrl)
                .user(user)
                .build();
    }
}
