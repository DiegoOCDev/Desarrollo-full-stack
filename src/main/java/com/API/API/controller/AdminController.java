package com.API.API.controller;

import com.API.API.model.Admin;
import com.API.API.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Admins")

class AdminController {
    @Autowired
    private AdminService adminService;
    @GetMapping("/GetAll")
    public List<Admin> getAdmins(){
        return adminService.getAllAdmins();
    }
    @PostMapping
    public Admin addAdmin(@RequestBody Admin admin){
        return adminService.addAdmin(admin);
    }
    @PutMapping("/{id}")
    public String updateAdmin(@RequestBody Admin admin, @PathVariable int id){
        return adminService.updateAdmin(id,admin);
    }
    @DeleteMapping("/{id}")
   public String deleteAdmin(@PathVariable int id){
        return adminService.deleteAdmin(id);
   }
   @GetMapping("/{id}")
   public Admin getAdmin( @PathVariable int id){
        return adminService.getAdmin(id);
   }
}
