package com.shopping.merchant.catalogue.controller;

import com.shopping.merchant.catalogue.entity.Merchant;
import com.shopping.merchant.catalogue.helper.CSVHelper;
import com.shopping.merchant.catalogue.message.ResponseMessage;
import com.shopping.merchant.catalogue.service.CSVService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping
public class CSVController {

    @Autowired
    CSVService fileService;


    @PostMapping("/merchant/upload")
    @Operation(summary = "This is to upload merchant data through csv file ")
    @ApiResponse(responseCode = "200" ,
            description = "File uploaded successfully and data is saved to db",
            content = {@Content(mediaType = "application/csv")})
    @ApiResponse(responseCode = "400",
            description ="Please Upload a CSV file")
    @ApiResponse(responseCode = "417",
            description ="Could not upload the file")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (CSVHelper.hasCSVFormat(file)) {
            try {
                message = fileService.save(file);
                if(message.length()==2)
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/merchants")
    @Operation(summary = "This is to fetch all data at once in json format")
    @ApiResponse(responseCode = "204",
                 description = "Database is empty")
    @ApiResponse(responseCode = "200",
                 description = "Data retrieved successfully" )
    @ApiResponse(responseCode = "500",
                 description = "An error occurred")

    public ResponseEntity<List<Merchant>> getAllMerchants() {
        try {
            List<Merchant> merchants = fileService.getAllMerchants();
            if (merchants.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(merchants, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@GetMapping("/merchants")
    @Operation(summary = "This is to fetch all data at once in json format")
    @ApiResponse(responseCode = "204",
            description = "Database is empty")
    @ApiResponse(responseCode = "200",
            description = "Data retrieved successfully" )
    @ApiResponse(responseCode = "500",
            description = "An error occurred")

    public ResponseEntity<Map<String,Object>> getAllMerchants(
            @RequestParam(required = false ) String name ,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        try {
            List<Merchant> merchants = fileService.getAllMerchants();
            Pageable paging = PageRequest.of(page,size);
            Page<Merchant> pageMerch;
            if(name==null)
                pageMerch =
            if (merchants.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(merchants, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @GetMapping("/merchant/{id}")
    @Operation(summary = "This is to fetch data of one merchant related to a merchant_id")
    @ApiResponse(responseCode = "204",
            description = "Database is empty")
    @ApiResponse(responseCode = "200",
            description = "Data retrieved successfully" )
    @ApiResponse(responseCode = "500",
            description = "An error occurred")
    public ResponseEntity<Optional<Merchant>> getMerchantById(@PathVariable("id") Long Id){
        try {
            Optional<Merchant> merchants = fileService.getMerchantById(Id);
            if (merchants.isPresent()) {
                return new ResponseEntity<>(merchants, HttpStatus.OK);
            }
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}