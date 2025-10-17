package utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class PDFUtils {

    public static String encodePdfToBase64(File pdfFile) throws IOException {
        byte[] bytes = Files.readAllBytes(pdfFile.toPath());
        return Base64.getEncoder().encodeToString(bytes);
    }
}
