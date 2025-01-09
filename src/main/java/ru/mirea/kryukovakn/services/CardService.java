package ru.mirea.kryukovakn.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.mirea.kryukovakn.entity.CardEntity;
import ru.mirea.kryukovakn.models.Card;
import ru.mirea.kryukovakn.models.Student;

import java.util.List;
import java.util.UUID;

public interface CardService {

    Card getCardByName(String name);

    Card getCardById(UUID id);

    Card getCardByOwnerFullName(String firstName, String surName, String familyName);

    Card saveCard(Card card) throws JsonProcessingException;

    List<Card> getAllCards();

}

