package com.hospital.registration.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.hospital.registration.entity.Doctor;
import com.hospital.registration.mapper.DoctorMapper;
import com.hospital.registration.service.DoctorService;

@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements DoctorService {
}
