package com.shopping.merchant.catalogue.controller;

import com.shopping.merchant.catalogue.entity.Merchant;
import com.shopping.merchant.catalogue.repository.PaginationAndSortingRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class PaginationController {
    @Autowired
    PaginationAndSortingRepository paginationAndSortingRepository;
    @Operation(summary = "This is to fetch all data at once in json format")
    @ApiResponse(responseCode = "204",
            description = "Database is empty")
    @ApiResponse(responseCode = "200",
            description = "Data retrieved successfully")
    @ApiResponse(responseCode = "500",
            description = "Unknown error occurred")
    @GetMapping("/merchant")
    public ResponseEntity<?> getAllMerchantsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "merchantId,asc") String[] sort
    ) {
        try {
            List<Merchant> merchants = new ArrayList<>();
            Page<Merchant> merchantPage;
            Pageable pageable = PageRequest.of(page, size, Sort.by(getDirection(sort)));
            merchantPage = paginationAndSortingRepository.findAll(pageable);
            merchants = merchantPage.getContent();
            LinkedHashMap<String, Object> response = new LinkedHashMap<>();
            response.put("status", "Success");
            response.put("currentPage", merchantPage.getNumber());
            response.put("totalItems", merchantPage.getTotalElements());
            response.put("totalPages", merchantPage.getTotalPages());
            response.put("data", merchants);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<Order> getDirection(String[] sort) {
        Sort.Direction dire = sort[1].contains("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Order order = new Order(dire, sort[0]);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        return orders;
    }

}
