package com.itheima.reggie.service.impl;

import com.itheima.reggie.entity.AddressBook;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.repository.AddressBookRepository;
import com.itheima.reggie.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of AddressBookService using Hibernate & JPA.
 */
@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookRepository addressBookRepository;

    /**
     * Save or update an address
     * @param addressBook AddressBook entity
     * @return Saved AddressBook entity
     */
    @Override
    public AddressBook saveAddress(AddressBook addressBook) {
        return addressBookRepository.save(addressBook);
    }

    /**
     * Get an address by ID
     * @param id Address ID
     * @return Optional of AddressBook
     */
    @Override
    public Optional<AddressBook> getAddressById(Long id) {
        return addressBookRepository.findById(id);
    }

    /**
     * Get all addresses for a specific user
     * @param userId User ID
     * @return List of AddressBook entities
     */
    @Override
    public List<AddressBook> getAllAddressesByUserId(Long userId) {
        return addressBookRepository.findByUserId(userId);
    }

    /**
     * Set default address for a user
     * @param addressBook AddressBook entity
     * @return Updated AddressBook entity
     */
    @Override
    public AddressBook setDefaultAddress(AddressBook addressBook) {
        Long userId = addressBook.getUserId();
        addressBookRepository.clearDefaultAddressForUser(userId); // Set all to non-default
        addressBook.setIsDefault(1);
        return addressBookRepository.save(addressBook);
    }

    /**
     * Get the default address for a specific user
     * @param userId User ID
     * @return Optional AddressBook entity
     */
    @Override
    public Optional<AddressBook> getDefaultAddressByUserId(Long userId) {
        return addressBookRepository.findDefaultAddressByUserId(userId);
    }

    /**
     * Delete an address by ID
     * @param id Address ID
     */
    @Override
    public void deleteAddress(Long id) {
        addressBookRepository.deleteById(id);
    }

    /**
     * Spring Data JPA Pagination Implementation (Compatible with MyBatis-Plus Format)
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Map<String, Object> getAddressBookPage(int page, int pageSize) {
        /**
         * Step 1: Adjust Page Number for Spring Data JPA
         * Spring Data JPA uses 0-based page indexing (first page is 0).
         * Frontend usually uses 1-based indexing (first page is 1).
         * We subtract 1 from 'page' to align with Spring Data JPA's expectations.
         */
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        /**
         * Step 2: Fetch Paginated Data from Database
         * 'findAll(Pageable pageable)' is a built-in method in JpaRepository.
         * It automatically generates the correct SQL query with 'LIMIT' and 'OFFSET' for pagination.
         * The 'Page<Category>' object contains all the required pagination metadata.
         */
        Page<AddressBook> categoryPage = addressBookRepository.findAll(pageable);

        /**
         * Step 3: Construct Response in MyBatis-Plus Compatible Format
         * The frontend was originally designed to work with MyBatis-Plus.
         * MyBatis-Plus uses 'records' instead of 'content', and expects 'current' instead of 'number'.
         */
        Map<String, Object> result = new HashMap<>();

        // 🚀 Key: Use "records" instead of "content" to match the frontend's expectations.
        result.put("records", categoryPage.getContent());

        // 📝 Total number of records in the entire dataset (across all pages).
        result.put("total", categoryPage.getTotalElements());

        // 📏 Page size (number of records per page).
        result.put("size", categoryPage.getSize());

        // 🔢 Current page number (converted to 1-based index).
        result.put("current", categoryPage.getNumber() + 1);

        /**
         * Step 4: Return the formatted response
         * This ensures that the frontend can continue to work without changes.
         */
        return result;
    }
}
