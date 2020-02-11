package com.rodzyn.car.service;

import com.rodzyn.car.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

    List<Car> getCars();
    Optional<Car> getCarById(Long id);
    List<Car> getCarByColor(String color);
    List<Car> getCarByMark(String mark);
    boolean addCar(Car car);
    Optional<Car> modCar(Car car);
    boolean removeCar(Car car);
}
