package com.itheima.reggie.repository;

import com.itheima.reggie.entity.AddressBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface AddressBookRepository extends JpaRepository<AddressBook, Long> {
    // Find addresses by user ID
    List<AddressBook> findByUserId(Long userId);

    // Find the default address for a user
    @Query("SELECT a FROM AddressBook a WHERE a.userId = :userId AND a.isDefault = 1")
    Optional<AddressBook> findDefaultAddressByUserId(Long userId);

    // Clear default address flag for a user
    @Modifying
    @Transactional
    @Query("UPDATE AddressBook a SET a.isDefault = 0 WHERE a.userId = :userId")
    void clearDefaultAddressForUser(Long userId);

    Page<AddressBook> findAll(@NotNull Pageable pageable);

}
