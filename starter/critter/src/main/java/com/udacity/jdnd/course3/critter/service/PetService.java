package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {


    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;


    public Pet save(Pet pet, Long ownerId) {
        Customer owner = customerRepository.getOne(ownerId);
        pet.setCustomer(owner);
        petRepository.save(pet);
        owner.getPets().add(pet);
        customerRepository.save(owner);
        return pet;
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Pet findById(Long id) {
        return petRepository.getOne(id);
    }

    public List<Pet> findByOwnerId(Long ownerId) {
        Customer owner = customerRepository.getOne(ownerId);
        return owner.getPets();
    }

    public List<Pet> findAllByIds(List<Long> ids) {
        return petRepository.findAllById(ids);
    }

}
