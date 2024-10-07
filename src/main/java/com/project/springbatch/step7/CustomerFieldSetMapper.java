//package com.project.springbatch.step7;
//
//import com.project.springbatch.domain.Customer;
//import org.springframework.batch.item.file.mapping.FieldSetMapper;
//import org.springframework.batch.item.file.transform.FieldSet;
//import org.springframework.validation.BindException;
//
//public class CustomerFieldSetMapper implements FieldSetMapper<Customer> {
//
//    @Override
//    public Customer mapFieldSet(FieldSet fs) throws BindException {
//
//        if (fs == null) return  null;
//
//        Customer customer = new Customer();
////        customer.setName(fs.readString(0));
////        customer.setAge(fs.readInt(1));
////        customer.setYear(fs.readString(2));
////        customer.setFirst_name(fs.readString("name"));
////        customer.set(fs.readInt("age"));
////        customer.setYear(fs.readString("year"));
//
//        return customer;
//    }
//}
