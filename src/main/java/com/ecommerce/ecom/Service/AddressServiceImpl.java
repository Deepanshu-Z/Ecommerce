package com.ecommerce.ecom.Service;

import com.ecommerce.ecom.ExceptionHandler.ApiException;
import com.ecommerce.ecom.Model.Address;
import com.ecommerce.ecom.Model.User;
import com.ecommerce.ecom.Payload.AddressDTO;
import com.ecommerce.ecom.Repository.AddressRepository;
import com.ecommerce.ecom.Repository.UserRepository;
import com.ecommerce.ecom.Util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AuthUtil authUtil;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;

    /// //////////Create ADDRESS////////////////
    public String createAddress(AddressDTO addressDTO){
        Address address = modelMapper.map(addressDTO, Address.class);

        User user = authUtil.loggedInUser();
        if (user == null) throw new ApiException("Please log in first");

        address.setUser(user);
        user.getAddresses().add(address);

        userRepository.save(user);

        return "Address Successfully added";
    }
}
