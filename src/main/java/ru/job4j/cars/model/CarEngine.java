package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "engine")
@Data
public class CarEngine {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "fuel_type_id", foreignKey = @ForeignKey(name = "FUEL_TYPE_ID_FK"))
    private CarFuelType carFuelType;

    @ManyToOne
    @JoinColumn(name = "engine_size_id", foreignKey = @ForeignKey(name = "ENGINE_SIZE_ID_FK"))
    private CarEngineSize carEngineSize;
}
