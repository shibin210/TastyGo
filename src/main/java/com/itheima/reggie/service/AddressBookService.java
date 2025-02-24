package com.itheima.reggie.service;

import com.itheima.reggie.entity.AddressBook;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Address Book Service Interface (Hibernate Version)
 */
public interface AddressBookService {
    AddressBook saveAddress(AddressBook addressBook);
    Optional<AddressBook> getAddressById(Long id);
    List<AddressBook> getAllAddressesByUserId(Long userId);
    Optional<AddressBook> getDefaultAddressByUserId(Long userId);
    AddressBook setDefaultAddress(AddressBook addressBook);
    void deleteAddress(Long id);

    Map<String, Object> getAddressBookPage(int page, int size);

}
