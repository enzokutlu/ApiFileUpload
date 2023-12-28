package com.example.ApiProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    private static final String UPLOAD_DIR = "uploads"; // Yüklenen dosyanın yükleneceği yer
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("png", "jpeg", "jpg", "docx", "pdf", "xlsx"); // Sınırlanacak uzantılar
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // Max dosya boyutu

    public ResponseEntity<String> uploadFile(MultipartFile file) throws IOException {

            validateFile(file);

            // Kaydedilecek dosya için sunucuda File dosyası oluşturma
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            // Yüklenen dosya tanımlama
            String fileName = file.getOriginalFilename();
            String filePath = UPLOAD_DIR + File.separator + fileName;

            file.transferTo(new File(filePath));

            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(fileName);
            fileEntity.setFileExtension(getFileExtension(fileName));
            fileEntity.setFilePath(filePath);
            fileEntity.setFileSize(file.getSize());

            fileRepository.save(fileEntity);

            return ResponseEntity.ok("Dosya yükleme başarılı.");

    }

    public List<FileEntity> listFiles() {
        return fileRepository.findAll();
    }

    private void validateFile(MultipartFile file) {
        // Max Boyut Belirleme ve hatası
        if (file.getSize() > MAX_FILE_SIZE) {
            System.out.println("Dosya boyutu 5 MB'dan büyük olamaz.");
        }

        // Uzantı kontrol ve hatası
        String fileExtension = getFileExtension(file.getOriginalFilename());
        if (!ALLOWED_EXTENSIONS.contains(fileExtension.toLowerCase())) {
            System.out.println("Desteklenmeyen dosya uzantısı.");
        }
    }
    // Dosya türünü noktadan sonrasını ayıklama ( Database için )
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    public FileEntity getFileDetails(String fileName,String fileExtension,String filePath, MultipartFile file) {

        return null;
    }

    public ResponseEntity<String> updateFile(String fileName,String fileExtension,String filePath, MultipartFile file) {

        return null;
    }

    public ResponseEntity<String> deleteFile(String fileName,String fileExtension,String filePath,Long fileSize) {

        return null;
    }

    public FileEntity getFileDetails(String fileName, String fileExtension, String filePath, Long fileSize) {

        return null;
    }

    public byte[] getFileContent(String fileName, String fileExtension, String filePath, Long fileSize) {

        return new byte[0];
    }


}

