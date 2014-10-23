package com.itechart.repository;

import com.itechart.model.Address;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Margarita on 19.10.2014.
 */
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("select a from Address a")
    public List<Address> readAddressList(Pageable pageable);

}
