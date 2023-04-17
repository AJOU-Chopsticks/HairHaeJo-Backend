package Chopsticks.HairHaeJoBackend.s3;

import Chopsticks.HairHaeJoBackend.service.S3UploadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class S3UploadServiceTest {

    @Autowired
    private S3UploadService s3UploadService;

    @Test
    public void testUpload() throws IOException {
        String path = "src/test/java/Chopsticks/HairHaeJoBackend/s3/test_image.png";

        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", "test_image.png",
            "image/png", new FileInputStream(path));

        String url = s3UploadService.upload(mockMultipartFile);

        System.out.println(url);

        assertNotNull(url);
    }

}
