package com.example.ApiProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    //Dosya yükleme
    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            ResponseEntity<String> response = fileService.uploadFile(file);
            return response;
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Dosya yükleme hatası: " + e.getMessage());
        }
    }

    // Dosya Listeleme
    @GetMapping("/list")
    public ResponseEntity<List<FileEntity>> listFiles() {
        List<FileEntity> files = fileService.listFiles();
        return ResponseEntity.ok(files);
    }

    // Listelenen dosyanın detayları
    @GetMapping("/details/{fileName,fileExtension,filePath,fileSize}")
    public ResponseEntity<FileEntity> getFileDetails(@PathVariable String fileName,String fileExtension,String filePath,Long fileSize) {

            FileEntity fileDetails = fileService.getFileDetails(fileName,fileExtension,filePath,fileSize);
            return ResponseEntity.ok(fileDetails);

    }

    // Byte array
    @GetMapping("/content/{fileName,fileExtension,filePath,fileSize}")
    public ResponseEntity<byte[]> getFileContent(@PathVariable String fileName,String fileExtension,String filePath,Long fileSize) {

            byte[] fileContent = fileService.getFileContent(fileName,fileExtension,filePath,fileSize);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);

    }

    // Yüklü dosya güncelleme (Boyut hariç)
    @PutMapping("/update/{fileName,fileExtension,filePath}")
    public ResponseEntity<String> updateFile(@PathVariable String fileName,String fileExtension,String filePath, @RequestParam("file") MultipartFile file) {

            ResponseEntity<String> response = fileService.updateFile(fileName,fileExtension,filePath,file);
            return response;

    }

    // Yüklü dosya silme
    @DeleteMapping("/delete/{fileName,fileExtension,filePath,fileSize}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName,String fileExtension,String filePath,Long fileSize) {

            ResponseEntity<String> response = fileService.deleteFile(fileName,fileExtension,filePath,fileSize);
            return response;

    }
}

