package com.shopping.merchant.catalogue.helper;

import com.shopping.merchant.catalogue.entity.Merchant;
import com.shopping.merchant.catalogue.validator.MerchantTableValidator;
import javafx.util.Pair;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class CSVHelper {


    public static String TYPE = "text/csv";

    static String[] HEADERs = {"Merchant_Id", "Merchant_Name", "GSTIN", "PAN", "Address", "Account_Number", "Phone_Number",
            "Created", "Modified"};

    /*public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
}*/

    public static Pair<List<Merchant>, String> csvToMerchants(InputStream is) {
        String message = "";
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<Merchant> merchants = new ArrayList<Merchant>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            Integer count = 1;
            for (CSVRecord csvRecord : csvRecords) {
                Merchant merchant = new Merchant(
                        csvRecord.get("Merchant_Name"),
                        csvRecord.get("GSTIN"),
                        csvRecord.get("PAN"),
                        csvRecord.get("Address"),
                        Long.parseLong(csvRecord.get("Account_Number")),
                        Long.parseLong(csvRecord.get("Phone_Number"))
                );
                message = MerchantTableValidator.validity(merchant);
                if(message == "") {
                    count++;
                    merchants.add(merchant);
                }else {
                    return new Pair<List<Merchant>,String>(merchants,count.toString()+". "+message);
                }
            }
            return  new Pair<List<Merchant>,String>(merchants,message);
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}