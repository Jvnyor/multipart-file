package com.Jvnyor.multipartfile.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Jvnyor.multipartfile.model.Employee;

@Service
public class EmployeeService {

	public void save(Employee employee) {
        saveFile(employee.getDocument());
        // save other employee data
    }

    private void saveFile(MultipartFile multipartFile) {
        try {
            saveToFilesystem(multipartFile);
        } catch (Exception e) {
            throw new RuntimeException("Unable to save file", e);
        }
    }

    private static void saveToFilesystem(MultipartFile multipartFile) throws IOException {
        String dir = Files.createTempDirectory("tmpDir").toFile().getAbsolutePath();
        File file = new File(dir + File.pathSeparator + multipartFile.getName());

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
        }
    }
}
