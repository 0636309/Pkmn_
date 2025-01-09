package ru.mirea.kryukovakn.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mirea.kryukovakn.dao.CardDao;
import ru.mirea.kryukovakn.dao.StudentDao;
import ru.mirea.kryukovakn.entity.CardEntity;
import ru.mirea.kryukovakn.entity.StudentEntity;
import ru.mirea.kryukovakn.models.Card;
import ru.mirea.kryukovakn.models.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;



@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardDao cardDao;
    private final StudentDao studentDao;
    private final PokemonTCG pokemonTCG;

    @Override
    public Card getCardByName(String name){
        CardEntity cardEntity = cardDao.getCardByName(name);
        return Card.fromEntity(cardEntity);
    }


    @Override
    public Card getCardById(UUID id){
        CardEntity cardEntity = cardDao.getCardById(id);
        return Card.fromEntity(cardEntity);
    }


    @Override
    public Card getCardByOwnerFullName(String firstName, String surName, String familyName){
        CardEntity cardEntity = cardDao.getCardByOwnerFullName(firstName, surName, familyName);
        return Card.fromEntity(cardEntity);
    }

    @Override
    public Card saveCard(Card card) throws JsonProcessingException {
        Student student = card.getPokemonOwner();
        StudentEntity studentEntity = studentDao.getStudentByFullNameAndGroup(student.getFirstName(), student.getSurName(), student.getFamilyName(), student.getGroup());
        if (studentEntity == null) {
            throw new RuntimeException("Владелец не найден.");
        }

        Optional<CardEntity> statusCard = cardDao.getCardByNameAndNumber(card.getName(), card.getNumber());
        if (statusCard.isPresent()) {
            throw new RuntimeException("Карта с таким именем уже существует.");
        }

        CardEntity cardEntity = toEntity(card);
        CardEntity saveCard = cardDao.saveCard(cardEntity);
        return fromEntity(saveCard);
    }




    @Override
    public List<Card> getAllCards(){
        List<CardEntity> cardEntities = cardDao.getAllCards();
        return cardEntities.stream().map(Card::fromEntity).collect(Collectors.toList());
    }

    private CardEntity toEntity(Card card) {
        if (card == null) {
            return null;
        }

        CardEntity evolvesFromEntity = (card.getEvolvesFrom() != null) ? cardDao.getCardByName(card.getEvolvesFrom().getName()) : null;

        StudentEntity ownerEntity = null;
        if (card.getPokemonOwner() != null) {
            ownerEntity = Student.toEntity(card.getPokemonOwner());
            ownerEntity = studentDao.saveStudent(ownerEntity);
        }

        return CardEntity.builder()
                .stage(card.getPokemonStage())
                .name(card.getName())
                .hp(card.getHp())
                .pokemon_type(card.getPokemonType())
                .evolves_from(evolvesFromEntity)
                .skills(card.getSkills())
                .weakness_type(card.getWeaknessType())
                .resistance_type(card.getResistanceType())
                .retreat_cost(card.getRetreatCost())
                .game_set(card.getGameSet())
                .regulation_mark(card.getRegulationMark())
                .pokemonOwner(ownerEntity)
                .number(card.getNumber())
                .build();
    }

    private Card fromEntity(CardEntity entity) {

        return Card.builder()
                .pokemonStage(entity.getStage())
                .name(entity.getName())
                .hp(entity.getHp())
                .pokemonType(entity.getPokemon_type())
                .evolvesFrom(fromEntity(entity.getEvolves_from()))
                .skills(entity.getSkills())
                .weaknessType(entity.getWeakness_type())
                .resistanceType(entity.getResistance_type())
                .retreatCost(entity.getRetreat_cost())
                .gameSet(entity.getGame_set())
                .regulationMark(entity.getRegulation_mark())
                .pokemonOwner(Student.fromEntity(entity.getPokemonOwner()))
                .number(entity.getNumber())
                .build();
    }

}


