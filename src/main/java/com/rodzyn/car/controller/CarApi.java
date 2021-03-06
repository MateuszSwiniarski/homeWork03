package com.rodzyn.car.controller;

        import com.rodzyn.car.model.Car;
        import com.rodzyn.car.service.CarService;
        import com.rodzyn.car.service.CarServiceImp;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.hateoas.CollectionModel;
        import org.springframework.hateoas.EntityModel;
        import org.springframework.hateoas.Link;
        import org.springframework.hateoas.RepresentationModel;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.MediaType;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import java.util.List;
        import java.util.Optional;


        import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/cars")
public class CarApi extends RepresentationModel<Car> {

    private CarService carService;

    @Autowired
    public CarApi(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CollectionModel<Car>> getCars(){
        List<Car> cars = carService.getCars();
        cars.forEach(car -> car.add(linkTo(CarApi.class).slash(car.getId()).withSelfRel()));
        Link link = linkTo(CarApi.class).withSelfRel();
        CollectionModel<Car> carEntityModel = new CollectionModel(cars, link);
        return new ResponseEntity(carEntityModel, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<Car>> getCarById(@PathVariable long id) {
        Link link = linkTo(CarApi.class).slash(id).withSelfRel();
        Optional<Car> carById = carService.getCarById(id);
        if (carById.isPresent()) {
            EntityModel<Car> carEntityModel = new EntityModel<>(carById.get(), link);
            return new ResponseEntity<>(carEntityModel, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/color", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CollectionModel<Car>> getCarByColor(@RequestParam String color){
        List<Car> carByColor = carService.getCarByColor(color);
        carByColor.forEach(car -> car.add(linkTo(CarApi.class).slash(car.getId()).withSelfRel()));
        Link link = linkTo(CarApi.class).withSelfRel();
        CollectionModel<Car> carEntityModel = new CollectionModel(carByColor, link);
        if(carByColor.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(carEntityModel, HttpStatus.OK);
    }

    @GetMapping(value = "/mark/{mark}", produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CollectionModel<Car>> getCarByMark(@PathVariable String mark){
        List<Car> carByMark = carService.getCarByMark(mark);
        carByMark.forEach(car -> car.add(linkTo(CarApi.class).slash(car.getId()).withSelfRel()));
        Link link = linkTo(CarApi.class).withSelfRel();
        CollectionModel<Car> carEntityModel = new CollectionModel(carByMark, link);
        if(carByMark.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(carEntityModel, HttpStatus.OK);
    }

    @PostMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Car> addCar(@RequestBody Car car){
        boolean add = carService.addCar(car);
        if(add){
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Car> modCar(@RequestBody Car newCar){
        Optional<Car> car = carService.modCar(newCar);
        if(car.isPresent()){
            carService.getCars().remove(car.get());
            carService.addCar(newCar);
            return new ResponseEntity<>(newCar, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Car> removeCar(@PathVariable long id){
        Optional<Car> first = carService.getCars().stream().filter(car -> car.getId() == id).findFirst();
        if(first.isPresent()){
            carService.removeCar(first.get());
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}