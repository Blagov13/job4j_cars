package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.CarEngine;
import ru.job4j.cars.model.CarEngineSize;
import ru.job4j.cars.model.CarFuelType;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class EngineRepositoryTest {
    private SessionFactory sf;
    private CrudRepository crudRepository;
    private CarEngineRepository engineRepository;

    @BeforeEach
    public void setUp() {
        sf = new Configuration().configure().buildSessionFactory();
        crudRepository = new CrudRepository(sf);
        engineRepository = new CarEngineRepository(crudRepository);
    }

    @AfterEach
    public void tearDown() {
        crudRepository.run(session -> session.createQuery("DELETE FROM CarEngine").executeUpdate());
        sf.close();
    }

    @Test
    public void whenSaveEngineThenRepositoryHasSameEngine() {
        CarEngine engine = new CarEngine();
        CarFuelType fuelType = new CarFuelType();
        fuelType.setName("Gasoline");
        CarEngineSize engineSize = new CarEngineSize();
        engineSize.setSize(2000);
        engine.setCarFuelType(fuelType);
        engine.setCarEngineSize(engineSize);
        engineRepository.save(engine);
        Optional<CarEngine> result = engineRepository.findById(engine.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(engine);
    }

    @Test
    public void whenUpdateEngineThenReturnTrue() {
        CarEngine engine = new CarEngine();
        CarFuelType fuelType = new CarFuelType();
        fuelType.setName("Gasoline");
        CarEngineSize engineSize = new CarEngineSize();
        engineSize.setSize(2000);
        engine.setCarFuelType(fuelType);
        engine.setCarEngineSize(engineSize);
        engineRepository.save(engine);
        CarEngineSize updatedSize = new CarEngineSize();
        updatedSize.setSize(2500);
        engine.setCarEngineSize(updatedSize);
        boolean isUpdated = engineRepository.update(engine);
        assertThat(isUpdated).isTrue();
        Optional<CarEngine> result = engineRepository.findById(engine.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getCarEngineSize().getSize()).isEqualTo(2500);
    }

    @Test
    public void whenDeleteEngineThenRepositoryDoesNotHaveSameEngine() {
        CarEngine engine = new CarEngine();
        CarFuelType fuelType = new CarFuelType();
        fuelType.setName("Gasoline");
        CarEngineSize engineSize = new CarEngineSize();
        engineSize.setSize(2000);
        engine.setCarFuelType(fuelType);
        engine.setCarEngineSize(engineSize);
        engineRepository.save(engine);
        boolean isDeleted = engineRepository.delete(engine.getId());
        assertThat(isDeleted).isTrue();
        Optional<CarEngine> result = engineRepository.findById(engine.getId());
        assertThat(result).isNotPresent();
    }

    @Test
    public void whenFindAllThenReturnAllEngines() {
        CarEngine engine1 = new CarEngine();
        CarFuelType fuelType1 = new CarFuelType();
        fuelType1.setName("Gasoline");
        CarEngineSize engineSize1 = new CarEngineSize();
        engineSize1.setSize(2000);
        engine1.setCarFuelType(fuelType1);
        engine1.setCarEngineSize(engineSize1);
        engineRepository.save(engine1);

        CarEngine engine2 = new CarEngine();
        CarFuelType fuelType2 = new CarFuelType();
        fuelType2.setName("Diesel");
        CarEngineSize engineSize2 = new CarEngineSize();
        engineSize2.setSize(2500);
        engine2.setCarFuelType(fuelType2);
        engine2.setCarEngineSize(engineSize2);
        engineRepository.save(engine2);

        List<CarEngine> engines = engineRepository.findAll();
        assertThat(engines).hasSize(2);
    }

    @Test
    public void whenFindByIdThenReturnCorrectEngine() {
        CarEngine engine = new CarEngine();
        CarFuelType fuelType = new CarFuelType();
        fuelType.setName("Gasoline");
        CarEngineSize engineSize = new CarEngineSize();
        engineSize.setSize(2000);
        engine.setCarFuelType(fuelType);
        engine.setCarEngineSize(engineSize);
        engineRepository.save(engine);
        Optional<CarEngine> result = engineRepository.findById(engine.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(engine);
    }
}