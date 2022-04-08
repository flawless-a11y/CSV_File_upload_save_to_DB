package com.shopping.merchant.catalogue.utility;

import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


@Component
public class FileToMultipart {

    public MultipartFile ConvertFileToMultipart(String FILE_PATH) throws IOException {


        File file = new File(FILE_PATH);
        if (file.exists()) {
            int lastDot = FILE_PATH.lastIndexOf('.');
            String extension = FILE_PATH.substring(lastDot + 1);
            String contentType = "text/" + extension;
            FileInputStream input = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), contentType,
                    IOUtils.toByteArray(input));
            return multipartFile;
        }
        return null;

    }
}