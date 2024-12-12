package com.springboot.ecom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.springboot.ecom.dto.CustomerShippingDetailsDto;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Customer;
import com.springboot.ecom.model.ShippingAddress;
import com.springboot.ecom.repository.CustomerRepository;
import com.springboot.ecom.repository.ShippingAddressRepository;
import com.springboot.ecom.service.CustomerService;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ShippingAddressRepository shippingAddressRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validate_ShouldReturnCustomer_WhenCustomerExists() throws ResourceNotFoundException {
        // Arrange
        int customerId = 1;
        Customer customer = new Customer();
        customer.setId(customerId);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // Act
        Customer result = customerService.validate(customerId);

        // Assert
        assertNotNull(result);
        assertEquals(customerId, result.getId());
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void validate_ShouldThrowException_WhenCustomerDoesNotExist() {
        // Arrange
        int customerId = 1;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> customerService.validate(customerId));
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void delete_ShouldDeleteCustomerById() {
        // Arrange
        int customerId = 1;

        // Act
        customerService.delete(customerId);

        // Assert
        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    void getCustomerByZipcode_ShouldReturnListOfCustomers() {
        // Arrange
        int zipcode = 12345;
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerRepository.getCustomerByZipcode(zipcode)).thenReturn(customers);

        // Act
        List<Customer> result = customerService.getCustomerByZipcode(zipcode);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(customerRepository, times(1)).getCustomerByZipcode(zipcode);
    }

    @Test
    void insert_ShouldSaveAndReturnCustomer() {
        // Arrange
        Customer customer = new Customer();
        when(customerRepository.save(customer)).thenReturn(customer);

        // Act
        Customer result = customerService.insert(customer);

        // Assert
        assertNotNull(result);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void getAllCustomers_ShouldReturnAllCustomers() {
        // Arrange
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(customers);

        // Act
        List<Customer> result = customerService.getAllCustomers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(customerRepository, times(1)).findAll();
    }


    @Test
    void getCustomerAndShippingDetailsByUsername_ShouldReturnNull_WhenNoDataExists() {
        // Arrange
        String username = "testuser";
        when(customerRepository.findCustomerDetailsByUsername(username)).thenReturn(Arrays.asList());

        // Act
        CustomerShippingDetailsDto result = customerService.getCustomerAndShippingDetailsByUsername(username);

        // Assert
        assertNull(result);
        verify(customerRepository, times(1)).findCustomerDetailsByUsername(username);
        verifyNoInteractions(shippingAddressRepository);
    }
}

