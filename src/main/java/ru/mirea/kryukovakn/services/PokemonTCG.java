package ru.mirea.kryukovakn.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PokemonTCG {

    private final RestTemplate restTemplate;

    public String getCardImageByName(String name) throws JsonProcessingException {
        String POKEMON_TCG_API_URL = "https://api.pokemontcg.io/v2/cards?q=name:" + name;
        String jsonResponse = restTemplate.getForObject(POKEMON_TCG_API_URL, String.class);
        if (jsonResponse == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonResponse);
        JsonNode data = root.get("data");
        if (data == null || data.isEmpty()) {
            return null;
        }
        JsonNode firstCard = data.get(0);
        JsonNode images = firstCard.get("images");
        if (images == null) {
            return null;
        }
        String imageUrl = images.has("large") ? images.get("large").asText() : null;
        if (imageUrl == null && images.has("small")) {
            imageUrl = images.get("small").asText();
        }

        return imageUrl;
    }



}
