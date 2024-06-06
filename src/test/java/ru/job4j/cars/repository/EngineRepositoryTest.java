package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class EngineRepositoryTest {
    private SessionFactory sf;
    private CrudRepository crudRepository;
    private EngineRepository engineRepository;

    @BeforeEach
    public void setUp() {
        sf = new Configuration().configure().buildSessionFactory();
        crudRepository = new CrudRepository(sf);
        engineRepository = new EngineRepository(crudRepository);
    }

    @AfterEach
    public void tearDown() {
        crudRepository.run(session -> session.createQuery("DELETE FROM Engine").executeUpdate());
        sf.close();
    }

    @Test
    public void whenSaveEngineThenRepositoryHasSameEngine() {
        Engine engine = new Engine();
        engine.setName("V8");
        engineRepository.save(engine);
        Optional<Engine> result = engineRepository.findById(engine.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(engine);
    }

    @Test
    public void whenUpdateEngineThenReturnTrue() {
        Engine engine = new Engine();
        engine.setName("V8");
        engineRepository.save(engine);
        engine.setName("V12");
        boolean isUpdated = engineRepository.update(engine);
        assertThat(isUpdated).isTrue();
        Optional<Engine> result = engineRepository.findById(engine.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("V12");
    }

    @Test
    public void whenDeleteEngineThenRepositoryDoesNotHaveSameEngine() {
        Engine engine = new Engine();
        engine.setName("V8");
        engineRepository.save(engine);
        boolean isDeleted = engineRepository.delete(engine.getId());
        assertThat(isDeleted).isTrue();
        Optional<Engine> result = engineRepository.findById(engine.getId());
        assertThat(result).isNotPresent();
    }

    @Test
    public void whenFindAllThenReturnAllEngines() {
        Engine engine1 = new Engine();
        engine1.setName("V8");
        engineRepository.save(engine1);
        Engine engine2 = new Engine();
        engine2.setName("V12");
        engineRepository.save(engine2);
        List<Engine> engines = engineRepository.findAll();
        assertThat(engines).hasSize(2);
    }

    @Test
    public void whenFindByIdThenReturnCorrectEngine() {
        Engine engine = new Engine();
        engine.setName("V8");
        engineRepository.save(engine);
        Optional<Engine> result = engineRepository.findById(engine.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(engine);
    }
}