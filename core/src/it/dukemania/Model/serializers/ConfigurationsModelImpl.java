package it.dukemania.Model.serializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.dukemania.Model.serializers.song.SongInfo;
import it.dukemania.Model.serializers.synthesizer.SynthInfo;
import it.dukemania.util.storage.Storage;

import java.io.IOException;
import java.util.List;

public class ConfigurationsModelImpl implements ConfigurationsModel {
    private final Storage storage;
    private final ObjectMapper mapper = new ObjectMapper();
    private static final String SONGS_CONFIGURATION_PATH = "configs/song_config.json";
    private static final String SYNTHESIZERS_CONFIGURATION_PATH = "configs/synthesizers_config.json";

    public ConfigurationsModelImpl(final Storage storage) {
        this.storage = storage;
    }

    @Override
    public List<SongInfo> readSongsConfiguration() throws IOException {
        String json = storage.readFileAsString(SONGS_CONFIGURATION_PATH);

        JavaType listSongType = mapper.constructType(new TypeReference<List<SongInfo>>() {
        });

        return mapper.readValue(json, listSongType);
    }

    @Override
    public void writeSongsConfiguration(final List<SongInfo> songs) throws IOException {
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(songs);
        storage.createFileIfNotExists(SONGS_CONFIGURATION_PATH);
        storage.writeStringOnFile(SONGS_CONFIGURATION_PATH, json);
    }

    @Override
    public List<SynthInfo> readSynthesizersConfiguration() throws IOException {
        String json = storage.readFileAsString(SYNTHESIZERS_CONFIGURATION_PATH);

        JavaType listSynthInfoType = mapper.constructType(new TypeReference<List<SynthInfo>>() {
        });

        return mapper.readValue(json, listSynthInfoType);
    }
}
