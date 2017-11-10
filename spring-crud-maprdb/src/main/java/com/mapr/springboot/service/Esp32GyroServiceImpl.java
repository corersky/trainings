package com.mapr.springboot.service;

import com.mapr.springboot.model.Esp32Gyro;
import com.mapr.springboot.repositories.GyroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service("gyroServie")
public class Esp32GyroServiceImpl implements Esp32GyroService {
    @Autowired
    private GyroRepository gyroRepository;
    @Override
    public void saveGyro(Esp32Gyro gyro) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        gyro.setId(sdf.format(new Date()));
        gyroRepository.save(gyro);
    }

    @Override
    public void deleteAll() {
        gyroRepository.deleteAll();
    }
}
