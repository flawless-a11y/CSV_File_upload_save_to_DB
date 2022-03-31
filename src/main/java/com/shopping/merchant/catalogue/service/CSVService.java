package com.shopping.merchant.catalogue.service;

import com.shopping.merchant.catalogue.entity.Merchant;
import com.shopping.merchant.catalogue.helper.CSVHelper;
import com.shopping.merchant.catalogue.repository.MerchantRepo;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CSVService {
    @Autowired
    MerchantRepo repository;
    public String save(MultipartFile file) {
        try {
            Pair<List<Merchant> , String> data = CSVHelper.csvToMerchants(file.getInputStream());
            if(data.getValue().length()==0){
            repository.saveAll(data.getKey());
            return "Data uploaded and saved successfully";
            }
            else {
                return "There is some discrepancy in the file data . Check the reported data location "+data.getValue();
            }
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }
    public List<Merchant> getAllMerchants() {
        return repository.findAll();
    }

    public Optional<Merchant> getMerchantById(Long Id){
        return repository.findByMerchantId(Id);
    }
}