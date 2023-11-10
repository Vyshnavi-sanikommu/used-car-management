package com.tamuc.usedcarsmanagement.controller;

import com.tamuc.usedcarsmanagement.MongoDBController.MongoDBAtlasManager;
import com.tamuc.usedcarsmanagement.exceptions.ResourceNotFoundException;
import com.tamuc.usedcarsmanagement.usedcarsPojo.UsedCarsPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class UsedCarManagementController {

    @Autowired
    private MongoDBAtlasManager mongoDBAtlasManager;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView displayUsedCars() {
        ModelAndView modelAndView = new ModelAndView("/index.html");
        modelAndView.addObject("cars", mongoDBAtlasManager.getAllUsedCars());
        return modelAndView;
    }

    @RequestMapping(value = "/editDetails/{id}", method = RequestMethod.GET)
    public ModelAndView updateUsedCars(@PathVariable String id) throws ResourceNotFoundException {
        ModelAndView modelAndView = new ModelAndView("/edit.html");
        modelAndView.addObject("car", mongoDBAtlasManager.getUsedCarById(id));
        return modelAndView;
    }

    @RequestMapping(value = "/saveDetails", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseEntity<UsedCarsPojo>> saveUpdatedCarDetails(@RequestParam("carMake") String carMake, @RequestParam("carModel") String carModel, @RequestParam("carId") String id, @RequestParam("year") int year,
                                                                              @RequestParam("mileage") int mileage, @RequestParam("fuelType") String fuelType, @RequestParam("price") double price,
                                                                              @RequestParam("suspension") String suspension) throws ResourceNotFoundException {
        UsedCarsPojo usedCarsPojo = new UsedCarsPojo(carMake, carModel, fuelType, year, mileage, price, suspension);
        return ResponseEntity.ok(mongoDBAtlasManager.updateCarDetails(id, usedCarsPojo));
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView showForm() {
        ModelAndView modelAndView = new ModelAndView("/add.html");
        modelAndView.addObject("Opened Create new car form");
        return modelAndView;
    }

    @RequestMapping(value = "/createNewCar", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UsedCarsPojo> createNewCar(@RequestParam("carMake") String carMake, @RequestParam("carModel") String carModel, @RequestParam("fuelType") String fuelType,
                                                     @RequestParam("year") int year, @RequestParam("mileage") int mileage, @RequestParam("price") double price,
                                                     @RequestParam("suspension") String suspension) {
        System.out.println("inside");
        UsedCarsPojo usedCarsPojo = new UsedCarsPojo(carMake, carModel, fuelType, year, mileage, price, suspension);
        return ResponseEntity.ok(mongoDBAtlasManager.createUsedCar(usedCarsPojo).getBody());
    }

    @RequestMapping(value = "/openUpload", method = RequestMethod.GET)
    public ModelAndView showUploadForm() {
        ModelAndView modelAndView = new ModelAndView("/upload.html");
        modelAndView.addObject("Opened Create new car form");
        return modelAndView;
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ResponseEntity<ResponseEntity<String>> uploadFile(@RequestParam("file") MultipartFile file) throws ResourceNotFoundException {
        return ResponseEntity.ok(mongoDBAtlasManager.createUsedCarByUpload(file));
    }

    @RequestMapping(value = "/results", method = RequestMethod.GET)
    public ModelAndView searchCars(@RequestParam(value = "search-query", required = false) String search_value) throws ResourceNotFoundException {

        ModelAndView modelAndView = new ModelAndView("/search.html");
        List<UsedCarsPojo> results = null;
        if (!search_value.isEmpty()) {
            results = mongoDBAtlasManager.getUsedCarByModel(search_value);
            if (results.isEmpty()) {
                results = mongoDBAtlasManager.getUsedCarByMake(search_value);
            }
            return modelAndView.addObject("cars", results);
        } else {
            return modelAndView.addObject("cars", mongoDBAtlasManager.getAllUsedCars());
        }
    }
}
