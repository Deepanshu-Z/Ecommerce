package com.ecommerce.ecom.Service;

import com.ecommerce.ecom.ExceptionHandler.ApiException;
import com.ecommerce.ecom.ExceptionHandler.ResourceNotFoundException;
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
    public AddressDTO createAddress(AddressDTO addressDTO){
        Address address = modelMapper.map(addressDTO, Address.class);

        User user = authUtil.loggedInUser();
        if (user == null) throw new ResourceNotFoundException("Please log in first");

        address.setUser(user);
        user.getAddresses().add(address);

        addressRepository.save(address);
        userRepository.save(user);

        return modelMapper.map(address, AddressDTO.class);
    }

    ///GET getUserSpecificAddress///////////
    public List<AddressDTO> getUserSpecificAddress(User user){
        List<Address> addressList = user.getAddresses();
        if(addressList.isEmpty()) throw new ResourceNotFoundException("Please add some address");

        List<AddressDTO> addressDTOS = addressList.stream()
                .map(item -> modelMapper.map(item, AddressDTO.class))
                .toList();

        return addressDTOS;
    }

    //////GET ALL ADDRESSES////////////
    public List<AddressDTO> getAllAddresses(){
        List<Address> addressList = addressRepository.findAll();
        if(addressList.isEmpty()) throw new ResourceNotFoundException("No addresses found");

        List<AddressDTO> addressDTOS = addressList.stream()
                .map(item -> modelMapper.map(item, AddressDTO.class))
                .toList();
        return addressDTOS;
    }

    ///////Update Address///////
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO){
       Address address = addressRepository.findById(addressId)
               .orElseThrow(() -> new ResourceNotFoundException("Address not found, please tell a valid address."));

       address.setBuildingName(addressDTO.getBuildingName());
       address.setCity(addressDTO.getCity());
       address.setCountry(addressDTO.getCountry());
       address.setPincode(addressDTO.getPincode());
       address.setState(addressDTO.getState());
       address.setStreet(addressDTO.getStreet());
       addressRepository.save(address);
       return modelMapper.map(address, AddressDTO.class);
    }

    ///////Delete Address///////
    public String deleteAddress(Long addressId){
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("No address found with such Id"));

        addressRepository.delete(address);

        return "Address with ID: " + addressId + " deleted successfully";
    }
}
