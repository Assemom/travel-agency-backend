package com.travel.management.controller;

import com.travel.management.model.Package;
import com.travel.management.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PackageController {
    private final PackageService packageService;
    @Autowired
    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @GetMapping("/packages") // to all the roles
    public List<Package> getAllPackages(){
        return packageService.getAllPackages();
    }

    @GetMapping("/packages/{packageId}")  // to all users
    public Package getPackageById(@PathVariable Long packageId){
        return packageService.getPackageById(packageId);
    }
    @PostMapping("/packages") //to manager and admin
    public Package addPackage(@RequestBody Package P){
        try {
            return packageService.addPackage(P);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/packages/{packageId}") //to manager and admin
    public ResponseEntity<String> updatePackage(@PathVariable int packageId , @RequestBody Package p){
        try {
            Package currPackage = packageService.addPackage(p);
        } catch (IOException e) {
            return new ResponseEntity<>("cannot be updated", HttpStatus.BAD_REQUEST);
        }
        if (p != null){
            return new ResponseEntity<>("updated successfully" , HttpStatus.OK);
        }else{
            return new ResponseEntity<>("failed to update" , HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/packages/{packageId}") //to admin only
    public void deletePackageById(@PathVariable Long packageId){
        packageService.deleteById(packageId);
    }
}
