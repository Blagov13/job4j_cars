package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class CarRepositoryTest {
    private SessionFactory sf;
    private CrudRepository crudRepository;
    private CarRepository carRepository;
    private EngineRepository engineRepository;

    @BeforeEach
    public void setUp() {
        sf = new Configuration().configure().buildSessionFactory();
        crudRepository = new CrudRepository(sf);
        carRepository = new CarRepository(crudRepository);
        engineRepository = new EngineRepository(crudRepository);
        Engine engine = new Engine();
        engine.setName("V8");
        engineRepository.save(engine);
    }

    @AfterEach
    public void tearDown() {
        crudRepository.run(session -> {
            session.createQuery("DELETE FROM Car").executeUpdate();
            session.createQuery("DELETE FROM Engine").executeUpdate();
        });
        sf.close();
    }

    @Test
    public void whenAddNewCarThenRepositoryHasSameCar() {
        Engine engine = engineRepository.findAll().get(0);
        Car car = new Car();
        car.setName("test1");
        car.setEngine(engine);
        carRepository.save(car);
        Optional<Car> result = carRepository.findById(car.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(car);
    }

    @Test
    public void whenReplaceCarThenReturnTrue() {
        Engine engine = engineRepository.findAll().get(0);
        Car car = new Car();
        car.setName("test1");
        car.setEngine(engine);
        carRepository.save(car);
        car.setName("new name");
        boolean isUpdated = carRepository.update(car);
        assertThat(isUpdated).isTrue();
        Optional<Car> result = carRepository.findById(car.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("new name");
    }

    @Test
    public void whenDeleteCarThenRepositoryDoesNotHaveSameCar() {
        Engine engine = engineRepository.findAll().get(0);
        Car car = new Car();
        car.setName("test1");
        car.setEngine(engine);
        carRepository.save(car);
        boolean isDeleted = carRepository.delete(car.getId());
        assertThat(isDeleted).isTrue();
        Optional<Car> result = carRepository.findById(car.getId());
        assertThat(result).isNotPresent();
    }

    @Test
    public void whenFindAllThenReturnAllCars() {
        Engine engine = engineRepository.findAll().get(0);
        Car car1 = new Car();
        car1.setName("test1");
        car1.setEngine(engine);
        carRepository.save(car1);
        Car car2 = new Car();
        car2.setName("test2");
        car2.setEngine(engine);
        carRepository.save(car2);
        List<Car> cars = carRepository.findAll();
        assertThat(cars).hasSize(2);
    }

    @Test
    public void whenFindByIdThenReturnCorrectCar() {
        Engine engine = engineRepository.findAll().get(0);
        Car car = new Car();
        car.setName("test1");
        car.setEngine(engine);
        carRepository.save(car);
        Optional<Car> result = carRepository.findById(car.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(car);
    }
}