package com.udacity.jdnd.course3.critter.schedule;


import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Employee> employees;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Pet> pets;
    private LocalDate date;

    @ElementCollection
    private Set<EmployeeSkill> activities;

}
