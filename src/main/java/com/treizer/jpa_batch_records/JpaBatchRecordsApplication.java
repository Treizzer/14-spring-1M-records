package com.treizer.jpa_batch_records;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.stream.StreamSupport;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.monitorjbl.xlsx.StreamingReader;
import com.treizer.jpa_batch_records.persistence.entity.CustomerEntity;
import com.treizer.jpa_batch_records.persistence.repository.ICustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class JpaBatchRecordsApplication implements CommandLineRunner {

    private final ICustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(JpaBatchRecordsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        long startTimeRead = System.currentTimeMillis();
        log.info("-> Reading File");
        InputStream is = new FileInputStream("../customers.xlsx");
        Workbook workbook = StreamingReader.builder()
                .rowCacheSize(50000) // number of rows to keep in memory (defaults to 10)
                .bufferSize(131072) // buffer size to use when reading InputStream to file (defaults to 1024)
                .open(is); // InputStream or File for XLSX file (required)

        List<CustomerEntity> customers = StreamSupport.stream(workbook.spliterator(), false)
                .flatMap(sheet -> StreamSupport.stream(sheet.spliterator(), false))
                .skip(1)
                .map(row -> {
                    CustomerEntity customer = new CustomerEntity();
                    // Double id = row.getCell(0).getNumericCellValue();
                    // customer.setId(Long.parseLong(row.getCell(0).getStringCellValue()));
                    customer.setId((long) row.getCell(0).getNumericCellValue());
                    customer.setName(row.getCell(1).getStringCellValue());
                    customer.setLastName(row.getCell(2).getStringCellValue());
                    customer.setAddress(row.getCell(3).getStringCellValue());
                    customer.setEmail(row.getCell(4).getStringCellValue());
                    return customer;
                })
                .toList();
        long endTimeRead = System.currentTimeMillis();

        log.info("-> Lectura finalizada, tiempo: " + (endTimeRead - startTimeRead) + " ms.");

        log.info("-> Writing File");
        long startTimeWrite = System.currentTimeMillis();
        this.customerRepository.saveAll(customers);
        long endTimeWrite = System.currentTimeMillis();
        log.info("-> Escritura finalizada, tiempo: " + (endTimeWrite - startTimeWrite) + " ms.");
    }

    // @Bean
    // CommandLineRunner init() {
    // return args -> {
    // long startTimeRead = System.currentTimeMillis();
    // log.info("-> Reading File");
    // InputStream is = new FileInputStream("../customers.xlsx");
    // Workbook workbook = StreamingReader.builder()
    // .rowCacheSize(50000) // number of rows to keep in memory (defaults to 10)
    // .bufferSize(131072) // buffer size to use when reading InputStream to file
    // (defaults to 1024)
    // .open(is); // InputStream or File for XLSX file (required)

    // List<CustomerEntity> customers = StreamSupport.stream(workbook.spliterator(),
    // false)
    // .flatMap(sheet -> StreamSupport.stream(sheet.spliterator(), false))
    // .skip(1)
    // .map(row -> {
    // CustomerEntity customer = new CustomerEntity();
    // // Double id = row.getCell(0).getNumericCellValue();
    // // customer.setId(Long.parseLong(row.getCell(0).getStringCellValue()));
    // customer.setId((long) row.getCell(0).getNumericCellValue());
    // customer.setName(row.getCell(1).getStringCellValue());
    // customer.setLastName(row.getCell(2).getStringCellValue());
    // customer.setAddress(row.getCell(3).getStringCellValue());
    // customer.setEmail(row.getCell(4).getStringCellValue());
    // return customer;
    // })
    // .toList();
    // long endTimeRead = System.currentTimeMillis();

    // log.info("-> Lectura finalizada, tiempo: " + (endTimeRead - startTimeRead) +
    // " ms.");

    // log.info("-> Writing File");
    // long startTimeWrite = System.currentTimeMillis();
    // this.customerRepository.saveAll(customers);
    // long endTimeWrite = System.currentTimeMillis();
    // log.info("-> Escritura finalizada, tiempo: " + (endTimeWrite -
    // startTimeWrite) + " ms.");
    // };
    // }

}
