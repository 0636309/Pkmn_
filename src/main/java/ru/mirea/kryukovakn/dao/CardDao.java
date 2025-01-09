package ru.mirea.kryukovakn.dao;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.mirea.kryukovakn.entity.CardEntity;
import ru.mirea.kryukovakn.repository.CardRepository;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CardDao {

    private final CardRepository cardRepository;

    @SneakyThrows
    public CardEntity getCardById(UUID id){
        return cardRepository.findById(id).orElseThrow(
                () -> new InstanceNotFoundException("Карта с ID: " + id + " не найдена.")
        );
    }



    @SneakyThrows
    public CardEntity getCardByName(String name){
        return cardRepository.findByName(name).orElseThrow(
                () -> new InstanceNotFoundException("Карта покемона с именем " + name + " не найдена.")
        );
    }


    @SneakyThrows
    public CardEntity getCardByOwnerFullName(String firstName, String surName, String familyName){
        return cardRepository.findByOwnerFullName(firstName, surName, familyName).orElseThrow(
                () -> new InstanceNotFoundException(String.format("Карта покемона с владельцем: %s %s %s не найдена.", firstName, surName, familyName))
        );
    }

    @SneakyThrows
    public List<CardEntity> getAllCards(){
        return cardRepository.findAll();
    }

    public Optional<CardEntity> getCardByNameAndNumber(String name, String number) {
        return cardRepository.findByNameAndNumber(name, number);
    }

    public CardEntity saveCard(CardEntity cardEntity) {
        return cardRepository.save(cardEntity);
    }

}
