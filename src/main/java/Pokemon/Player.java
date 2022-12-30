package Pokemon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String name;

    private final List<Species> pokedex = new ArrayList<>();

    public Player(String name, String filename) {
        //Add Player Name
        this.name = name;

        //Fill Dex from Log File
        try {
            Path filePath = Paths.get("G:\\Nintendo DS\\" + name + "\\" + filename);
            List<String> content = Files.readAllLines(filePath);
            for (int i = 2; i < Manager.getDexSize()+2; i++) {
                pokedex.add(new Species(content.get(i)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Species> getPokedex() {
        return pokedex;
    }
}
