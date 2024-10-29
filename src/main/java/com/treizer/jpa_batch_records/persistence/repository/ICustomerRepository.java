package com.treizer.jpa_batch_records.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treizer.jpa_batch_records.persistence.entity.CustomerEntity;

import jakarta.transaction.Transactional;

// JpaRepository is more usefully when needs store massive data
@Repository
@Transactional
public interface ICustomerRepository extends JpaRepository<CustomerEntity, Long> {

}
