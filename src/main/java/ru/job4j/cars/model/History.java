package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import java.security.Timestamp;

@Entity
@Table(name = "history")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;
    private Timestamp startAt;
    private  Timestamp endAt;
    @ManyToOne
    @JoinColumn(name = "history_owner_id")
    private HistoryOwner historyOwner;
}
