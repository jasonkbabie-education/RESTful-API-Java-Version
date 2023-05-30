package com.amigoscode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {
    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args)
    {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    record NewRequestCustomer(
            String name,
            String email,
            Integer age
    ){}

    @PostMapping
    public void addCustomer(@RequestBody NewRequestCustomer request){
        Customer customer = new Customer();
        customer.setName(request.name);
        customer.setAge(request.age);
        customerRepository.save(customer);
    }

    @DeleteMapping("{customerID}")
    public void deleteCustomer(@PathVariable("customerID") Integer Id){
        customerRepository.deleteById(Id);
    }

    @PutMapping("{customerID}")
    public void editCustomer(
            @PathVariable("customerID") Integer Id,
            @RequestBody NewRequestCustomer request
    ){
        Customer customer = customerRepository.getReferenceById(Id);
        customer.setName(request.name);
        customer.setAge(request.age);
        customer.setEmail(request.email);
        customerRepository.save(customer);
    }
}