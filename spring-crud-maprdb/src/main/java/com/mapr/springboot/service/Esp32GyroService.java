package com.mapr.springboot.service;


import com.mapr.springboot.model.Esp32Gyro;
import com.mapr.springboot.model.User;

import java.util.List;

public interface Esp32GyroService {

	void saveGyro(Esp32Gyro gyro);
	void deleteAll();

}