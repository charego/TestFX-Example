package org.testfx.playground.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.testfx.playground.model.Player;
import org.testfx.playground.model.Team;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class Service {

	private final ObjectMapper mapper = new ObjectMapper();

	private final TypeReference<List<Player>> playerList = new TypeReference<List<Player>>() {};

	private final TypeReference<List<Team>> teamList = new TypeReference<List<Team>>() {};

	public List<Player> loadMorePlayers() {
		return loadFile("/data/players_more.json", playerList);
	}

	public List<Team> loadMoreTeams() {
		return loadFile("/data/teams_more.json", teamList);
	}

	public List<Player> loadPlayers() {
		return loadFile("/data/players.json", playerList);
	}

	public List<Team> loadTeams() {
		return loadFile("/data/teams.json", teamList);
	}

	private <T> T loadFile(String filePath, TypeReference<T> typeReference) {
		try {
			final InputStream is = getClass().getResourceAsStream(filePath);
			return mapper.readValue(is, typeReference);
		} catch (IOException e) {
			throw new RuntimeException("Failed to parse file: " + filePath, e);
		}
	}

}
