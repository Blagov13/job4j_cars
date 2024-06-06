package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class OwnerRepositoryTest {
    private SessionFactory sf;
    private CrudRepository crudRepository;
    private OwnerRepository ownerRepository;

    @BeforeEach
    public void setUp() {
        sf = new Configuration().configure().buildSessionFactory();
        crudRepository = new CrudRepository(sf);
        ownerRepository = new OwnerRepository(crudRepository);
    }

    @AfterEach
    public void tearDown() {
        crudRepository.run(session -> session.createQuery("DELETE FROM Owner").executeUpdate());
        sf.close();
    }

    @Test
    public void whenSaveOwnerThenRepositoryHasSameOwner() {
        Owner owner = new Owner();
        owner.setName("John Doe");
        ownerRepository.save(owner);
        Optional<Owner> result = ownerRepository.findById(owner.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(owner);
    }

    @Test
    public void whenUpdateOwnerThenReturnTrue() {
        Owner owner = new Owner();
        owner.setName("John Doe");
        ownerRepository.save(owner);
        owner.setName("Jane Doe");
        boolean isUpdated = ownerRepository.update(owner);
        assertThat(isUpdated).isTrue();
        Optional<Owner> result = ownerRepository.findById(owner.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Jane Doe");
    }

    @Test
    public void whenDeleteOwnerThenRepositoryDoesNotHaveSameOwner() {
        Owner owner = new Owner();
        owner.setName("John Doe");
        ownerRepository.save(owner);
        boolean isDeleted = ownerRepository.delete(owner.getId());
        assertThat(isDeleted).isTrue();
        Optional<Owner> result = ownerRepository.findById(owner.getId());
        assertThat(result).isNotPresent();
    }

    @Test
    public void whenFindAllThenReturnAllOwners() {
        Owner owner1 = new Owner();
        owner1.setName("John Doe");
        ownerRepository.save(owner1);
        Owner owner2 = new Owner();
        owner2.setName("Jane Doe");
        ownerRepository.save(owner2);
        List<Owner> owners = ownerRepository.findAll();
        assertThat(owners).hasSize(2);
    }

    @Test
    public void whenFindByIdThenReturnCorrectOwner() {
        Owner owner = new Owner();
        owner.setName("John Doe");
        ownerRepository.save(owner);
        Optional<Owner> result = ownerRepository.findById(owner.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(owner);
    }
}