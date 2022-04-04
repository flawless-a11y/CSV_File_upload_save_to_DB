package com.shopping.merchant.catalogue.controller;

import com.shopping.merchant.catalogue.entity.Merchant;
import com.shopping.merchant.catalogue.pagination.PaginationAndSortingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PaginationController {
    @Autowired
    PaginationAndSortingRepository paginationAndSortingRepository;

    @GetMapping("/merchant")
    public ResponseEntity<?> getAllMerchantsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "merchantId,asc") String[] sort
    ) {
        try {
            List<Merchant> merchants = new ArrayList<>();
            Page<Merchant> merchantPage;
            Pageable pageable =  PageRequest.of(page,size, Sort.by(getDirection(sort)));
            merchantPage = paginationAndSortingRepository.findAll(pageable);
            merchants = merchantPage.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("status","Success");
            response.put("currentPage",merchantPage.getNumber());
            response.put("totalItems",merchantPage.getTotalElements());
            response.put("totalPages",merchantPage.getTotalPages());
            response.put("merchants",merchants);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private List<Order> getDirection(String[] sort){
    Sort.Direction dire = sort[1].contains("desc")?Sort.Direction.DESC:Sort.Direction.ASC;
    Order order = new Order(dire,sort[0]);
    List<Order> orders = new ArrayList<>();
    orders.add( order );
    return orders;
    }

}
