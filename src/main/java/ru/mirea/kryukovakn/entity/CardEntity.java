package ru.mirea.kryukovakn.entity;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.mirea.kryukovakn.models.AttackSkill;
import ru.mirea.kryukovakn.models.EnergyType;
import ru.mirea.kryukovakn.models.PokemonStage;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private int hp;

    @Enumerated(EnumType.STRING)
    private EnergyType pokemon_type;

    @ManyToOne
    @JoinColumn(name = "evolves_from_id")
    private CardEntity evolves_from;

    @Enumerated(EnumType.STRING)
    private PokemonStage stage;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "attack_skills")
    @JsonDeserialize(using = SkillDeserializer.class)
    private List<AttackSkill> skills;

    @Enumerated(EnumType.STRING)
    private EnergyType weakness_type;

    @Enumerated(EnumType.STRING)
    private EnergyType resistance_type;

    private String retreat_cost;

    private String game_set;

    private char regulation_mark;

    @Column(name = "card_number")
    private String number;



    @ManyToOne
    @JoinColumn(name = "pokemon_owner_id")
    private StudentEntity pokemonOwner;

}
