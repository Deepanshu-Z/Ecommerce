package com.ecommerce.ecom.Service;

import com.ecommerce.ecom.Model.User;
import com.ecommerce.ecom.Payload.AddressDTO;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO);

    List<AddressDTO> getUserSpecificAddress(User user);
    List<AddressDTO> getAllAddresses();

    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO);

    AddressDTO deleteAddress(Long addressId);
}
