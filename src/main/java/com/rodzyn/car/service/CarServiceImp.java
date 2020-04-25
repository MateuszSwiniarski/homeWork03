package com.rodzyn.car.service;

import com.rodzyn.car.model.Car;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImp implements CarService {

    public CarServiceImp(){
        carList.add(new Car(1L, "audi", "A5", "black"));
        carList.add(new Car(2L, "prosche", "911", "red"));
        carList.add(new Car(3L, "bmw", "i8", "red"));
    }

    private List<Car> carList = new ArrayList<>();

    @Override
    public List<Car> getCars() {
        return carList;
    }

    @Override
    public Optional<Car> getCarById(Long id) {
        Optional<Car> first = getCars().stream().filter(car -> car.getId() == id).findFirst();
        return first;
    }

    @Override
    public List<Car> getCarByColor(String color) {
        List<Car> colorCar = carList.stream().filter(car -> car.getColor().equals(color)).collect(Collectors.toList());
        return colorCar;
    }

    @Override
    public List<Car> getCarByMark(String mark) {
        List<Car> markCar = carList.stream().filter(car -> car.getMark().equals(mark)).collect(Collectors.toList());
        return markCar;
    }

    @Override
    public boolean addCar(Car car) {
        return carList.add(car);
    }

    @Override
    public Optional<Car> modCar(Car newCar) {
        Optional<Car> first = carList.stream().filter(car -> car.getId() == newCar.getId()).findFirst();
        return first;
    }

    @Override
    public boolean removeCar(Car car) {
        return carList.remove(car);
    }
}
