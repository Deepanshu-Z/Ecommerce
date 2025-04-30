package com.ecommerce.ecom.Controller;

import com.ecommerce.ecom.Model.Address;
import com.ecommerce.ecom.Payload.AddressDTO;
import com.ecommerce.ecom.Service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AddressController {
    @Autowired
    AddressService addressService;

    //CREATE ADDRESS////////////////////////////////
    @PostMapping("/public/create/address")
    public ResponseEntity<String> createAddress(@Valid @RequestBody AddressDTO requestAddressDTO){
        String message = addressService.createAddress(requestAddressDTO);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
}
