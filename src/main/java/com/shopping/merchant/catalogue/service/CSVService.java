package com.shopping.merchant.catalogue.service;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.shopping.merchant.catalogue.entity.Merchant;
import com.shopping.merchant.catalogue.helper.CSVHelper;
import com.shopping.merchant.catalogue.repository.MerchantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVService {
    @Autowired
    MerchantRepo repository;
    public void save(MultipartFile file) {
        try {
            List<Merchant> merchantList = CSVHelper.csvToMerchants(file.getInputStream());
            repository.saveAll(merchantList);
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