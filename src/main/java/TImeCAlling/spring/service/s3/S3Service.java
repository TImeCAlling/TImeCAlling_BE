package TImeCAlling.spring.service.s3;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.handler.S3Handler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${spring.cloud.aws.s3.bucketName}")
    private String bucketName;

    public String uploadFile(MultipartFile file) {
        validateFile(file);

        String fileName = generateUniqueFileName(file.getOriginalFilename());
        ObjectMetadata metadata = new ObjectMetadata();

        try (InputStream inputStream = file.getInputStream()) {
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            // S3 업로드 요청 생성
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

        } catch (IOException e) {
            throw new S3Handler(ErrorStatus.FILE_NOT_UPLOADED);
        }

        // 업로드된 파일의 URL 반환
        return amazonS3.getUrl(bucketName, fileName).toString();
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new S3Handler(ErrorStatus.FILE_IS_EMPTY);
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new S3Handler(ErrorStatus.FILE_NOT_IMAGE);
        }
    }

    private String generateUniqueFileName(String originalFilename) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        return uniqueId + extension;
    }

    public void deleteImageFromS3(String imageAddress){
        String key = getKeyFromImageAddress(imageAddress);
        try{
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
        }catch (Exception e){
            throw new S3Handler(ErrorStatus.IO_EXCEPTION_ON_IMAGE_DELETE);
        }
    }

    private String getKeyFromImageAddress(String imageAddress){
        try{
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
            return decodingKey.substring(1); // 맨 앞의 '/' 제거
        }catch (MalformedURLException | UnsupportedEncodingException e){
            throw new S3Handler(ErrorStatus.IO_EXCEPTION_ON_IMAGE_DELETE);
        }
    }
}
