package com.itheima.reggie.controller;

import com.itheima.reggie.common._BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.AddressBook;
import com.itheima.reggie.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Address Book Management Controller (Hibernate Version)
 */
@Slf4j
@RestController
@RequestMapping("/api/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * Add a new address
     * @param addressBook AddressBook entity
     * @return Response with saved address
     */
    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook) {
        addressBook.setUserId(_BaseContext.getCurrentId());
        log.info("Saving address: {}", addressBook);
        AddressBook savedAddress = addressBookService.saveAddress(addressBook);
        return R.success(savedAddress);
    }

    /**
     * Get an address by ID
     * @param id Address ID
     * @return Response with found address
     */
    @GetMapping("/{id}")
    public R<AddressBook> get(@PathVariable Long id) {
        Optional<AddressBook> addressBook = addressBookService.getAddressById(id);
        return addressBook.map(R::success).orElseGet(() -> R.error("Address not found"));
    }

    /**
     * Set an address as default
     * @param addressBook AddressBook entity
     * @return Response with updated default address
     */
    @PutMapping("default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook) {
        log.info("Setting default address: {}", addressBook);
        AddressBook updatedAddress = addressBookService.setDefaultAddress(addressBook);
        return R.success(updatedAddress);
    }

    /**
     * Get the default address for the current user
     * @return Response with default address
     */
    @GetMapping("default")
    public R<AddressBook> getDefault() {
        Optional<AddressBook> defaultAddress = addressBookService.getDefaultAddressByUserId(_BaseContext.getCurrentId());
        return defaultAddress.map(R::success).orElseGet(() -> R.error("No default address set"));
    }

    /**
     * Get all addresses for a specific user
     * @return List of AddressBook entities
     */
    @GetMapping("/list")
    public R<List<AddressBook>> list() {
        List<AddressBook> addressBookList = addressBookService.getAllAddressesByUserId(_BaseContext.getCurrentId());
        return R.success(addressBookList);
    }

    /**
     * Update an address
     * @param addressBook AddressBook entity
     * @return Response with updated address
     */
    @PutMapping
    public R<AddressBook> update(@RequestBody AddressBook addressBook) {
        addressBook.setUserId(_BaseContext.getCurrentId());
        log.info("Updating address: {}", addressBook);
        AddressBook updatedAddress = addressBookService.saveAddress(addressBook);
        return R.success(updatedAddress);
    }

    /**
     * Delete an address by ID
     * @param id Address ID
     * @return Response message
     */
    @DeleteMapping
    public R<String> delete(@RequestParam("ids") Long id) {
        log.info("Deleting address with ID: {}", id);
        addressBookService.deleteAddress(id);
        return R.success("Address deleted successfully");
    }

    /**
     * Retrieves a paginated list of categories.
     *
     * @param page     The page number (starting from 1 on the frontend, but adjusted to start from 0 internally).
     * @param pageSize The number of records per page.
     * @return A response containing the paginated list of categories.
     */
    @GetMapping("/page")
    public R<Map<String, Object>> getCategoryPage(
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize) {

        // Call the service layer to retrieve paginated data
        Map<String, Object> pageInfo = addressBookService.getAddressBookPage(page, pageSize);
        return R.success(pageInfo);
    }
}
