//package com.project.springbatch;
//
//import com.project.springbatch.domain.Customer;
//import com.project.springbatch.domain.CustomerRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//@Configuration
//public class Initializer {
//    private static final Logger logger = LoggerFactory.getLogger(Initializer.class);
//    private static final Random random = new Random();
//
//    @Bean
//    public CommandLineRunner initCustomers(CustomerRepository customerRepository) {
//        return args -> {
//            List<Customer> customers = new ArrayList<>();
//
//            for (int i = 1; i <= 100; i++) {
//                Customer customer = new Customer();
//                customer.setId((long) i);
//                customer.setFirst_name("user");
//                customer.setLast_name(String.valueOf(i));
//                customer.setBirthdate(generateRandomBirthdate().toString());
//
//                customers.add(customer);
//            }
//
//            customerRepository.saveAll(customers);
//        };
//    }
//
//    private LocalDate generateRandomBirthdate() {
//        int year = 1980 + random.nextInt(44);
//        int month = 1 + random.nextInt(12);
//        int day = 1 + random.nextInt(28);
//        return LocalDate.of(year, month, day);
//    }
//}