package com.example.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientOutputModel {
    List<Donor> donors;
    List<Doctor> doctors;
    Patient patient;
}
