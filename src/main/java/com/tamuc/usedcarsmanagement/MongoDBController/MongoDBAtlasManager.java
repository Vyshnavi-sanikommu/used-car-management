package com.tamuc.usedcarsmanagement.MongoDBController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tamuc.usedcarsmanagement.database_seq_service.DatabaseSeq_Generator;
import com.tamuc.usedcarsmanagement.exceptions.ResourceNotFoundException;
import com.tamuc.usedcarsmanagement.repository.UsedCarsRepository;
import com.tamuc.usedcarsmanagement.usedcarsPojo.UsedCarsPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/v1")
public class MongoDBAtlasManager {

    @Autowired
    private UsedCarsRepository usedCarsRepository;
    @Autowired
    private DatabaseSeq_Generator databaseSeqGenerator;
    @GetMapping("/usedcars")
    public List<UsedCarsPojo> getAllUsedCars(){
        return usedCarsRepository.findAll();
    }
    @GetMapping("/usedcars/{carModel}")
    public List<UsedCarsPojo> getUsedCarByModel(@PathVariable String carModel)
            throws ResourceNotFoundException {
        List<UsedCarsPojo> usedCar = usedCarsRepository.findByCarModel(carModel)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with this Model :: " + carModel));
        return ResponseEntity.ok().body(usedCar).getBody();
    }
    @GetMapping("/usedcars/{carMake}")
    public List<UsedCarsPojo> getUsedCarByMake(@PathVariable String carMake)
            throws ResourceNotFoundException {
        List<UsedCarsPojo> usedCar = usedCarsRepository.findByCarMake(carMake)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with this Make :: " + carMake));
        return ResponseEntity.ok().body(usedCar).getBody();
    }
    @GetMapping("/usedcarData/{id}")
    public UsedCarsPojo getUsedCarById(@PathVariable String id)
            throws ResourceNotFoundException {
        UsedCarsPojo usedCar = usedCarsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found for this Id :: " + id));
        return ResponseEntity.ok().body(usedCar).getBody();
    }
   @PostMapping("/usedcars")
   public ResponseEntity<UsedCarsPojo> createUsedCar(@RequestBody UsedCarsPojo usedCars) {
       usedCars.setId(databaseSeqGenerator.generateSeq(UsedCarsPojo.SEQUENCE_NAME));
       final UsedCarsPojo carDetails = usedCarsRepository.save(usedCars);
       return ResponseEntity.ok(carDetails);
   }

    @PutMapping("/usedcars/{id}")
    public ResponseEntity<UsedCarsPojo> updateCarDetails(@PathVariable(value = "id") String documentId,
                                           @RequestBody UsedCarsPojo usedCarDetails) throws ResourceNotFoundException {
        UsedCarsPojo usedCar = usedCarsRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found for this id :: " + documentId));

        usedCar.setCarModel(usedCarDetails.getCarModel());
        usedCar.setMileage(usedCarDetails.getMileage());
        usedCar.setYear(usedCarDetails.getYear());
        usedCar.setPrice(usedCarDetails.getPrice());
        usedCar.setSuspension(usedCarDetails.getSuspension());
        usedCar.setFuelType(usedCarDetails.getFuelType());

        final UsedCarsPojo updatedCarDetails = usedCarsRepository.save(usedCar);
        return ResponseEntity.ok(updatedCarDetails);
    }

    @DeleteMapping("/usedcars/{id}")
    public Map< String, Boolean > deleteCar(@PathVariable(value = "id") String documentId)
            throws ResourceNotFoundException {
        System.out.println(documentId);
        UsedCarsPojo usedCar = usedCarsRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found for this id :: " + documentId));

        usedCarsRepository.delete(usedCar);
        Map < String, Boolean > response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    @PostMapping("/usedcars/upload")
    public ResponseEntity<String> createUsedCarByUpload(MultipartFile file) {
        if (!file.getContentType().equals("application/json")) {
            return ResponseEntity.badRequest().body("File is not a JSON file");
        }
        try {
            // Parse the JSON data into a Java object
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode json = objectMapper.readTree(file.getInputStream());
            if(json.isArray()){
                UsedCarsPojo[] myObject = objectMapper.readValue(file.getInputStream(), UsedCarsPojo[].class);
                for (UsedCarsPojo pojo: myObject){
                    pojo.setId(databaseSeqGenerator.generateSeq(UsedCarsPojo.SEQUENCE_NAME));
                    usedCarsRepository.save(pojo);
                }
            }
            else{
                UsedCarsPojo myObject = objectMapper.readValue(file.getInputStream(), UsedCarsPojo.class);
                myObject.setId(databaseSeqGenerator.generateSeq(UsedCarsPojo.SEQUENCE_NAME));
                usedCarsRepository.save(myObject);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("JSON file uploaded and processed successfully");
    }
}
