package com.ecommerce.ecom.Repository;

import com.ecommerce.ecom.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
