package com.travel.management.service;

import com.travel.management.model.Package;
import com.travel.management.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PackageService {
    private final PackageRepository packageRepository;
    @Autowired
    public PackageService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public List<Package> getAllPackages(){
        return packageRepository.findAll();
    }
    public Package getPackageById(Long id){
        return packageRepository.findById(id).orElse(null);
    }
    public Package addPackage(Package p) throws IOException {
        return packageRepository.save(p);
    }
    public void deleteById(Long id){
        packageRepository.deleteById(id);
    }


}
