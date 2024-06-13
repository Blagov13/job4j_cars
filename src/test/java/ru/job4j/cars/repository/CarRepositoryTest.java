package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class CarRepositoryTest {
    private SessionFactory sf;
    private CrudRepository crudRepository;
    private CarRepository carRepository;
    private CarEngineRepository engineRepository;

    @BeforeEach
    public void setUp() {
        sf = new Configuration().configure().buildSessionFactory();
        crudRepository = new CrudRepository(sf);
        carRepository = new CarRepository(crudRepository);
        engineRepository = new CarEngineRepository(crudRepository);
        CarEngineSize engineSize = new CarEngineSize();
        engineSize.setSize(12);
        CarFuelType fuelType = new CarFuelType();
        fuelType.setName("Petrol");
        CarEngine engine = new CarEngine();
        engine.setCarFuelType(fuelType);
        engine.setCarEngineSize(engineSize);
        engineRepository.save(engine);
    }

    @AfterEach
    public void tearDown() {
        crudRepository.run(session -> {
            session.createQuery("DELETE FROM Car").executeUpdate();
            session.createQuery("DELETE FROM CarEngine").executeUpdate();
        });
        sf.close();
    }

    @Test
    public void whenAddNewCarThenRepositoryHasSameCar() {
        CarEngine engine = engineRepository.findAll().get(0);
        Car car = new Car();
        car.setCarEngine(engine);
        carRepository.save(car);
        Optional<Car> result = carRepository.findById(car.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(car);
    }

    @Test
    public void whenReplaceCarThenReturnTrue() {
        CarEngine engine = engineRepository.findAll().get(0);
        Car car = new Car();
        car.setCarEngine(engine);
        carRepository.save(car);
        car.setCarEngine(engine);
        boolean isUpdated = carRepository.update(car);
        assertThat(isUpdated).isTrue();
        Optional<Car> result = carRepository.findById(car.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getCarEngine()).isEqualTo(engine);
    }

    @Test
    public void whenDeleteCarThenRepositoryDoesNotHaveSameCar() {
        CarEngine engine = engineRepository.findAll().get(0);
        Car car = new Car();
        car.setCarEngine(engine);
        carRepository.save(car);
        boolean isDeleted = carRepository.delete(car.getId());
        assertThat(isDeleted).isTrue();
        Optional<Car> result = carRepository.findById(car.getId());
        assertThat(result).isNotPresent();
    }

    @Test
    public void whenFindAllThenReturnAllCars() {
        CarEngine engine = engineRepository.findAll().get(0);
        Car car1 = new Car();
        car1.setCarEngine(engine);
        carRepository.save(car1);
        Car car2 = new Car();
        car2.setCarEngine(engine);
        carRepository.save(car2);
        List<Car> cars = carRepository.findAll();
        assertThat(cars).hasSize(2);
    }

    @Test
    public void whenFindByIdThenReturnCorrectCar() {
        CarEngine engine = engineRepository.findAll().get(0);
        Car car = new Car();
        car.setCarEngine(engine);
        carRepository.save(car);
        Optional<Car> result = carRepository.findById(car.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(car);
    }
}