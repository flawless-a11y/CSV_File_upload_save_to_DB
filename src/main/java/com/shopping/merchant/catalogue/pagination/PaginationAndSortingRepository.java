package com.shopping.merchant.catalogue.pagination;

import com.shopping.merchant.catalogue.entity.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaginationAndSortingRepository extends JpaRepository<Merchant,String> {
    Page<Merchant> findByGstin(String gstin, Pageable pageable);
    Page<Merchant> findAllByMerchantNameContaining(String name , Pageable pageable);
    Page<Merchant> findByPanContaining(String pan , Pageable pageable);
    Page<Merchant> findByMerchantId(Long merchantId , Pageable pageable);


 }
