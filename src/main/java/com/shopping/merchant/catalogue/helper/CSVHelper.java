package com.shopping.merchant.catalogue.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.shopping.merchant.catalogue.entity.Merchant;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = { "Merchant_Id", "Merchant_Name", "GSTIN", "PAN","Address","Account_Number","Phone_Number",
            "Created","Modified"};
    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }
    public static List<Merchant> csvToMerchants(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<Merchant> merchants = new ArrayList<Merchant>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                Merchant merchant = new Merchant(
                        //Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("Merchant_Name"),
                        csvRecord.get("GSTIN"),
                        csvRecord.get("PAN"),
                        Long.parseLong(csvRecord.get("Account_Number")),
                        Long.parseLong(csvRecord.get("Phone_Number"))
                );
                merchants.add(merchant);
            }
            return merchants;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}