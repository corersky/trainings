package com.mapr.springboot.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

public class Esp32Gyro implements Serializable{

	private String id;

	private double temperature;
	private double rawX;
	private double rawY;
	private double rawZ;
	private double normX;
	private double normY;
	private double normZ;

	public Esp32Gyro() {
	}

	public Esp32Gyro(String id, double temperature, double rawX, double rawY, double rawZ, double normX, double normY, double normZ) {
		this.id = id;
		this.temperature = temperature;
		this.rawX = rawX;
		this.rawY = rawY;
		this.rawZ = rawZ;
		this.normX = normX;
		this.normY = normY;
		this.normZ = normZ;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getRawX() {
		return rawX;
	}

	public void setRawX(double rawX) {
		this.rawX = rawX;
	}

	public double getRawY() {
		return rawY;
	}

	public void setRawY(double rawY) {
		this.rawY = rawY;
	}

	public double getRawZ() {
		return rawZ;
	}

	public void setRawZ(double rawZ) {
		this.rawZ = rawZ;
	}

	public double getNormX() {
		return normX;
	}

	public void setNormX(double normX) {
		this.normX = normX;
	}

	public double getNormY() {
		return normY;
	}

	public void setNormY(double normY) {
		this.normY = normY;
	}

	public double getNormZ() {
		return normZ;
	}

	public void setNormZ(double normZ) {
		this.normZ = normZ;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Esp32Gyro esp32Gyro = (Esp32Gyro) o;

		if (Double.compare(esp32Gyro.temperature, temperature) != 0) return false;
		if (Double.compare(esp32Gyro.rawX, rawX) != 0) return false;
		if (Double.compare(esp32Gyro.rawY, rawY) != 0) return false;
		if (Double.compare(esp32Gyro.rawZ, rawZ) != 0) return false;
		if (Double.compare(esp32Gyro.normX, normX) != 0) return false;
		if (Double.compare(esp32Gyro.normY, normY) != 0) return false;
		if (Double.compare(esp32Gyro.normZ, normZ) != 0) return false;
		return id != null ? id.equals(esp32Gyro.id) : esp32Gyro.id == null;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = id != null ? id.hashCode() : 0;
		temp = Double.doubleToLongBits(temperature);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(rawX);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(rawY);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(rawZ);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(normX);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(normY);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(normZ);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}
