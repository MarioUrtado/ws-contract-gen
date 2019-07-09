package cl.entel.tde.integration.soa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import cl.entel.tde.integration.soa.model.UploadFileResponse;
import cl.entel.tde.integration.soa.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/files")
public class GeneratorController {

    @Autowired
    private FileStorageService fileStorageService;


    @RequestMapping(value = "/down", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(@RequestParam(value = "file", required = true) String param) throws IOException {

        // ...
        File file = new File(param);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        String contentType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }

    @RequestMapping(value = "/up", method = RequestMethod.POST)
    public UploadFileResponse up(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }




}
