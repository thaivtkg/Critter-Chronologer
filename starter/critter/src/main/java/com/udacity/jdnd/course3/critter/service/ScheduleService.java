package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.ScheduleRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.EmployeRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

   @Autowired
   private CustomerRepository customerRepository;

   @Autowired
   private EmployeRepository employeRepository;

   @Autowired
   private PetRepository petRepository;

    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForEmployee(long employeeId) {
        Employee employee = employeRepository.getOne(employeeId);



        return scheduleRepository.findAllByEmployeesContains(employee);
    }

    public List<Schedule> getScheduleForPet(long petId) {
        Pet pet = petRepository.getOne(petId);
        return scheduleRepository.findAllByPetsContains(pet);
    }


    public List<Schedule> findScheduleByCustomerId(Long ownerId) {

        // find owner by owner Id:
        Customer customer = customerRepository.getOne(ownerId);

        // return list of pets of a customer
        // find owner by ownerId, then retrieve a list of pet of that owner
        // then, find in schedule table in a joined table with pet table
        return scheduleRepository.findAllByPetsIn(customer.getPets());
    }
}
