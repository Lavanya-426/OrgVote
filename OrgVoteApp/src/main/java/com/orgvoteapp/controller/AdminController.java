package com.orgvoteapp.controller;

import com.orgvoteapp.model.Candidate;
import com.orgvoteapp.model.Election;
import com.orgvoteapp.model.Voter;
import com.orgvoteapp.repository.CandidateRepository;
import com.orgvoteapp.repository.ElectionRepository;
import com.orgvoteapp.repository.UserRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired private UserRepository userRepo;
    @Autowired private CandidateRepository candRepo;
    @Autowired private ElectionRepository electionRepo;

    @PostMapping("/upload-users")
    public String uploadUsers(@RequestParam("file") MultipartFile file) throws Exception {
        String name = file.getOriginalFilename().toLowerCase();
        if(name.endsWith(".xlsx") || name.endsWith(".xls")) {
            try (XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream())) {
                var sheet = wb.getSheetAt(0);
                for (Row r : sheet) {
                    if (r.getRowNum()==0) continue;
                    var cell0 = r.getCell(0);
                    if(cell0==null) continue;
                    String username = cell0.getStringCellValue().trim();
                    String password = "pass";
                    String role = "USER";
                    var cell1 = r.getCell(1);
                    if(cell1!=null) password = cell1.getStringCellValue().trim();
                    var cell2 = r.getCell(2);
                    if(cell2!=null) role = cell2.getStringCellValue().trim().toUpperCase();
                    if(userRepo.findByUsername(username).isEmpty()){
                        userRepo.save(new Voter(username, password, role));
                    }
                }
            }
            return "users uploaded (xlsx)";
        } else {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] p = line.split(",");
                    String username = p[0].trim();
                    String password = p.length>1 ? p[1].trim() : "pass";
                    String role = p.length>2 ? p[2].trim().toUpperCase() : "USER";
                    if (userRepo.findByUsername(username).isEmpty()) {
                        userRepo.save(new Voter(username, password, role));
                    }
                }
            }
            return "users uploaded (csv)";
        }
    }

    @PostMapping("/upload-candidates")
    public String uploadCandidates(@RequestParam("file") MultipartFile file) throws Exception {
        String name = file.getOriginalFilename().toLowerCase();
        if(name.endsWith(".xlsx") || name.endsWith(".xls")) {
            try (XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream())) {
                var sheet = wb.getSheetAt(0);
                for (Row r : sheet) {
                    if (r.getRowNum()==0) continue;
                    var cell0 = r.getCell(0);
                    if(cell0==null) continue;
                    String cname = cell0.getStringCellValue().trim();
                    candRepo.save(new Candidate(cname));
                }
            }
            return "candidates uploaded (xlsx)";
        } else {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    candRepo.save(new Candidate(line.trim()));
                }
            }
            return "candidates uploaded (csv)";
        }
    }

    @PostMapping("/create-election")
    public Election createElection(@RequestBody Election e){
        e.setActive(false);
        return electionRepo.save(e);
    }

    @PostMapping("/start")
    public String startElection(){
        Election e = electionRepo.findTopByOrderByIdDesc();
        if(e==null) return "no election";
        e.setActive(true); electionRepo.save(e);
        return "started";
    }

    @PostMapping("/end")
    public String endElection(){
        Election e = electionRepo.findTopByOrderByIdDesc();
        if(e==null) return "no election";
        e.setActive(false); electionRepo.save(e);
        return "ended";
    }

    @GetMapping("/results")
    public Object results(){ return candRepo.findAll(); }
}
