package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();
        convertToPet(petDTO);

        return convertToDto(petService.save(pet,petDTO.getOwnerId()));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertToDto(petService.findById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets =petService.findAll();
        return pets.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.findByOwnerId(ownerId);

        return pets.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public Pet convertToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }

    public PetDTO convertToDto(Pet pet) {
        PetDTO petDto = new PetDTO();
        BeanUtils.copyProperties(pet, petDto);
        petDto.setOwnerId(pet.getCustomer().getId());
        return petDto;
    }

    @Autowired
    public void setPetService(PetService petService) {
        this.petService = petService;
    }
}
