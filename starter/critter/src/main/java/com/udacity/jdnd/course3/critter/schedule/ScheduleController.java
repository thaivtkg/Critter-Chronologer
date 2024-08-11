package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertToSchedule(scheduleDTO);
        return convertToScheduleDTO(scheduleService.save(schedule));
    }

    public Schedule convertToSchedule( ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());
        List<Employee> employees = employeeService.findAllEmployeesById(scheduleDTO.getEmployeeIds());
        schedule.setEmployees(employees);
        List<Pet> pets = petService.findAllByIds(scheduleDTO.getPetIds());
        schedule.setPets(pets);
        return schedule;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.findAll();
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOs.add(convertToScheduleDTO(schedule));
        }
        return scheduleDTOs;
    }

    public ScheduleDTO convertToScheduleDTO( Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        return scheduleDTO;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> petSchedules = scheduleService.getScheduleForPet(petId);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule schedule : petSchedules) {
            scheduleDTOs.add(convertToScheduleDTO(schedule));
        }
        return scheduleDTOs;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> employeeSchedules = scheduleService.getScheduleForEmployee(employeeId);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule employeeSchedule : employeeSchedules) {
            scheduleDTOs.add(convertToScheduleDTO(employeeSchedule));
        }
        return scheduleDTOs;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> customerSchedules = scheduleService.findScheduleByCustomerId(customerId);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule customerSchedule : customerSchedules) {
            scheduleDTOs.add(convertToScheduleDTO(customerSchedule));
        }
        return scheduleDTOs;
    }
}
