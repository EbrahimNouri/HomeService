package ir.maktab.homeservice.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

@Component
public class FileUtil {

    public static byte[] imageConverter(java.io.File file) {
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] imageByte = fileInputStream.readAllBytes();
                fileInputStream.close();
                return imageByte;
            } catch (IOException e) {
                return null;
            }
        } else
            return null;
    }

    public static File fileWriter(Path path, byte[] bytes) {
        try (FileOutputStream fos = new FileOutputStream(path.toFile())) {
            fos.write(bytes);
            return new File(path.toUri());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
