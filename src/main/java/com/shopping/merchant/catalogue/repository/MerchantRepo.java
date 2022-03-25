package com.shopping.merchant.catalogue.repository;

import com.shopping.merchant.catalogue.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantRepo extends JpaRepository<Merchant,String> {

    Optional<Merchant> findByMerchantId(Long merchantId);
}
