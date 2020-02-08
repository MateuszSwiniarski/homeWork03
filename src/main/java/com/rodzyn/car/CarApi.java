package com.rodzyn.car;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/cars")
public class CarApi {

    private List<Car> carList;

    public CarApi() {
        this.carList = new ArrayList<>();
        carList.add(new Car(1L, "audi", "A5", "black"));
        carList.add(new Car(2L, "prosche", "911", "red"));
        carList.add(new Car(3L, "bmw", "i8", "red"));
    }

    @GetMapping
    public ResponseEntity<Car> getCars(){
        return new ResponseEntity(carList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id){
        Optional<Car> first = carList.stream().filter(car -> car.getId() == id).findFirst();
        if(first.isPresent()){
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/color")
    public ResponseEntity<Car> getCarByColor(@RequestParam String color){
        List<Car> colorCar = carList.stream().filter(car -> car.getColor().equals(color)).collect(Collectors.toList());
        if(colorCar.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(colorCar, HttpStatus.OK);
    }

    @GetMapping("/mark/{mark}")
    public ResponseEntity<Car> getCarByMark(@PathVariable String mark){
        List<Car> colorCar = carList.stream().filter(car -> car.getMark().equals(mark)).collect(Collectors.toList());
        if(colorCar.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(colorCar, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@RequestBody Car car){
        boolean add = carList.add(car);
        if(add){
            return new ResponseEntity(add, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping()
    public ResponseEntity<Car> modCar(@RequestBody Car newCar){
        Optional<Car> first = carList.stream().filter(car -> car.getId() == newCar.getId()).findFirst();
        if(first.isPresent()){
            carList.remove(first.get());
            carList.add(newCar);
            return new ResponseEntity<>(newCar, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Car> removeCar(@PathVariable long id){
        Optional<Car> first = carList.stream().filter(car -> car.getId() == id).findFirst();
        if(first.isPresent()){
            carList.remove(first.get());
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
