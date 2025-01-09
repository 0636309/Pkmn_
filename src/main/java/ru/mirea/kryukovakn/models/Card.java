package ru.mirea.kryukovakn.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mirea.kryukovakn.entity.CardEntity;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card implements Serializable {

    private PokemonStage pokemonStage;
    private String name;
    private int hp;
    private EnergyType pokemonType;
    private Card evolvesFrom;
    private List<AttackSkill> skills;
    private EnergyType weaknessType;
    private EnergyType resistanceType;
    private String retreatCost;
    private String gameSet;
    private char regulationMark;
    private Student pokemonOwner;
    public static final long serialVersionUID = 1L;
    public String number;
    public String image;

    public Card(PokemonStage pokemonStage, String name, int hp, EnergyType pokemonType, Card evolvesFrom, List<AttackSkill> skills, EnergyType weaknessType, EnergyType resistanceType, String retreatCost, String gameSet, String number, char regulationMark, Student pokemonOwner) {
        this.pokemonStage = pokemonStage;
        this.name = name;
        this.hp = hp;
        this.pokemonType = pokemonType;
        this.evolvesFrom = evolvesFrom;
        this.resistanceType = resistanceType;
        this.weaknessType = weaknessType;
        this.gameSet = gameSet;
        this.skills = skills;
        this.retreatCost = retreatCost;
        this.regulationMark = regulationMark;
        this.pokemonOwner = pokemonOwner;
        this.number = number;
    }

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder();

        result.append("\u001b[38;5;183mКарта покемона\u001b[38;5;15m").append("\n")
                .append("Стадия: ").append(pokemonStage).append("\n")
                .append("Имя покемона: ").append(name).append("\n")
                .append("ХП: ").append(hp).append("\n")
                .append("Тип покемона: ").append(pokemonType).append("\n")
                .append("Способности атак: ").append(skills).append("\n")
                .append("Тип слабости: ").append(weaknessType != null ? weaknessType : "-").append("\n")
                .append("Тип сопротивления: ").append(resistanceType != null ? resistanceType : "-").append("\n")
                .append("Цена побега: ").append(retreatCost).append("\n")
                .append("Название сета: ").append(gameSet).append("\n")
                .append("Номер карты в сете: ").append(number).append("\n")
                .append("Отметка легальности: ").append(regulationMark).append("\n")
                .append(pokemonOwner != null ? "Владелец карты: " + pokemonOwner + "\n" : "");

        if (evolvesFrom != null) {
            result.append("\n")
                    .append("\033[4;37mПредшественник:\033[0m").append("\n")
                    .append(evolvesFrom.toString());
        } else {
            result.append("Не имеет предка.\n");
        }

        return result.toString();

    }

    public static Card fromEntity(CardEntity cardEntity){
        return Card.builder()
                .name(cardEntity.getName())
                .pokemonStage(cardEntity.getStage())
                .hp(cardEntity.getHp())
                .pokemonType(cardEntity.getPokemon_type())
                .evolvesFrom(cardEntity.getEvolves_from() != null ? Card.fromEntity(cardEntity.getEvolves_from()) : null)
                .skills(cardEntity.getSkills())
                .weaknessType(cardEntity.getWeakness_type())
                .resistanceType(cardEntity.getResistance_type() != null ? cardEntity.getResistance_type() : null)
                .retreatCost(cardEntity.getRetreat_cost())
                .gameSet(cardEntity.getGame_set())
                .regulationMark(cardEntity.getRegulation_mark())
                .pokemonOwner(cardEntity.getPokemonOwner() != null ? Student.fromEntity(cardEntity.getPokemonOwner()) : null)
                .number(cardEntity.getNumber())
                .build();
    }





}
