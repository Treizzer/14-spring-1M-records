package com.treizer.jpa_batch_records.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class CustomerEntity {

    // SEQUENCE usually is more optimer than IDENTITY, because
    // doesn't use the database to create the numbers
    // To handle massive data use SEQUENCE
    // sequenceName is to the database
    // allocationSize means the initial number to autoincrement
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_sequence_generator")
    @SequenceGenerator(name = "my_sequence_generator", sequenceName = "my_sequence", allocationSize = 1)
    private Long id;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    private String address;

    private String email;

}
