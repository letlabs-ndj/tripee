package com.lameute.expedition_service.service;

import com.lameute.expedition_service.exceptions.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    @Value("${upload.path}")
    private String imageDirName;

    private Path uploadPath;

    /*stores uploaded file image to local server */
    public void storeFile(MultipartFile file) {
        // Get the original filename of the uploaded image file
        String fileName = file.getOriginalFilename();

        try {
            // Create a path to the storage directory with the unique filename
            Path path = this.uploadPath.resolve(fileName);

            // Copy the file input stream to the specified path, overwriting any existing file
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {
            throw new StorageException("Could not store file " + fileName, ex);
        }
    }

    /*Loads a file as a Resource object */
    public Resource loadFileAsResource(String fileName) {
        try {
            // Construct the file path by combining the image directory name and the filename.
            Path path = Paths.get(imageDirName).resolve(fileName);

            // Create a UrlResource object from the file path.
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) { // Check if the resource exists and is readable.
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file:  " + fileName, e);
        }
    }

	public void init() {
		try {
            this.uploadPath = Paths.get(imageDirName);
			Files.createDirectories(uploadPath);
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}

