package com.shopping.merchant.catalogue.repository;

import com.shopping.merchant.catalogue.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepo extends JpaRepository<Merchant, String> {

    Merchant findByMerchantId(Long merchantId);
}
