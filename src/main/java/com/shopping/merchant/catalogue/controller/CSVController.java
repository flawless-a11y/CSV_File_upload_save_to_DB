package com.shopping.merchant.catalogue.controller;

import com.shopping.merchant.catalogue.entity.Merchant;
import com.shopping.merchant.catalogue.message.ResponseMessage;
import com.shopping.merchant.catalogue.message.ResponseMessage1;
import com.shopping.merchant.catalogue.service.CSVService;
import com.shopping.merchant.catalogue.utility.FileToMultipart;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping
public class CSVController {

    @Autowired
    CSVService fileService;

    @Autowired
    FileToMultipart fileToMultipart;

    @Operation(summary = "This is to upload merchant data through csv file ")
    @ApiResponse(responseCode = "200",
            description = "File uploaded successfully and data is saved to db",
            content = {@Content(mediaType = "String")})
    @ApiResponse(responseCode = "400",
            description = "Please Upload a CSV file")
    @ApiResponse(responseCode = "417",
            description = "Could not upload the file")
    @PostMapping("/merchant/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("filepath") String filepath) throws IOException {
        File check = new File(filepath);
        String message = "";
        if (check.exists()) {
            MultipartFile file = fileToMultipart.ConvertFileToMultipart(filepath);

            int lastDot = filepath.lastIndexOf('.');
            String extension = filepath.substring(lastDot + 1);
            if (extension.equals("csv")) {
                try {
                    message = fileService.save(file);
                    String status = "";
                    if (message == "Data uploaded and saved successfully")
                        status = " Success";
                    else
                        status = "Failure";
                    message += " File :" + file.getOriginalFilename();

                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(status, message));
                } catch (Exception e) {
                    message = "Could not upload the file:" + file.getOriginalFilename() + "!";
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Failure", message));
                }
            }

            message = "Please upload a csv file!";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Failure", message));
        }
        message = "File Doesn't exist";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Failure", message));
    }

    @Operation(summary = "This is to fetch data of one or more merchant with specified merchants ids")
    @ApiResponse(responseCode = "204",
            description = "Database is empty")
    @ApiResponse(responseCode = "200",
            description = "Data retrieved successfully")
    @ApiResponse(responseCode = "500",
            description = "Unknown error occurred")
    @GetMapping("/merchant/{ids}")
    public ResponseEntity<?> getMerchantByIds(@PathVariable("ids") List<Integer> ids) {
        Collections.sort(ids);
        List<Merchant> merchants = new ArrayList<>();
        for (Integer id : ids) {
            try {
                Merchant merchant = fileService.getMerchantById(id);
                merchants.add(merchant);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Failure",
                        "Something has gone wrong on the website's server"));
            }
        }
        if (merchants.get(0) == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseMessage("Failure", "Requested Merchant Id does not exist"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage1("Success",
                "Data of the requested merchants is retrieved", merchants));
    }
}


