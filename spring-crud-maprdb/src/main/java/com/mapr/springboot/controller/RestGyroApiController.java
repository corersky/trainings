package com.mapr.springboot.controller;

import com.mapr.springboot.model.Esp32Gyro;
import com.mapr.springboot.model.User;
import com.mapr.springboot.service.Esp32GyroService;
import com.mapr.springboot.service.UserService;
import com.mapr.springboot.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestGyroApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestGyroApiController.class);

	@Autowired
	Esp32GyroService esp32GyroService; //Service which will do all data retrieval/manipulation work


	@RequestMapping(value = "/gyro", method = RequestMethod.POST)
	public ResponseEntity<?> createGyro(@RequestBody Esp32Gyro gyro) {
		logger.info("Creating Gyro : {}", gyro);
		esp32GyroService.saveGyro(gyro);
		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(ucBuilder.path("/api/gyro/{id}").buildAndExpand(gyro.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/gyro/delete", method = RequestMethod.GET)
	public ResponseEntity<?> delete() {
		logger.info("Delete all Gyro");
		esp32GyroService.deleteAll();
		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(ucBuilder.path("/api/gyro/{id}").buildAndExpand(gyro.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

}