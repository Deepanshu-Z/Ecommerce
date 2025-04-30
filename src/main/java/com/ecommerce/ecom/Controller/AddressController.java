package com.ecommerce.ecom.Controller;

import com.ecommerce.ecom.Model.Address;
import com.ecommerce.ecom.Model.User;
import com.ecommerce.ecom.Payload.AddressDTO;
import com.ecommerce.ecom.Service.AddressService;
import com.ecommerce.ecom.Util.AuthUtil;
import com.sun.net.httpserver.HttpsConfigurator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {
    @Autowired
    AddressService addressService;
    @Autowired
    private AuthUtil authUtil;

    //CREATE ADDRESS////////////////////////////////
    @PostMapping("/public/create/address")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO requestAddressDTO){
        AddressDTO addressDTO = addressService.createAddress(requestAddressDTO);
        return new ResponseEntity<>(addressDTO, HttpStatus.CREATED);
    }

    /////FETCH USER SPECIFIC ADDRESSES//////////////
    @GetMapping("/public/user/addresses")
    public ResponseEntity<List<AddressDTO>> getUserSpecificAddress(){
        User user = authUtil.loggedInUser();
        List<AddressDTO> addressList = addressService.getUserSpecificAddress(user);
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }

    ///FETCH ALL ADDRESSES/////////]
    @GetMapping("/admin/user/addresses")
    public ResponseEntity<List<AddressDTO>> getAllAddresses(){
        List<AddressDTO> addressDTOList = addressService.getAllAddresses();
        return new ResponseEntity<>(addressDTOList, HttpStatus.OK);
    }
}
