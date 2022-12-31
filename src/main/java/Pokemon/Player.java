package Pokemon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String name;

    private final List<Pokemon> pokemon = new ArrayList<>();

    public Player(String name, String filename) {
        //Add Player Name
        this.name = name;

        //Fill Dex from Log File
        try {
            Path filePath = Paths.get("G:\\Nintendo DS\\" + name + "\\" + filename);
            List<String> content = Files.readAllLines(filePath);
            for (int i = 2; i < Manager.getDexSize()+2; i++) {
                //pokedex.add(new Species(content.get(i)),0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void loadPokemonFromFile() {
        try {
            Path filePath = Paths.get("pokemon\\" + name + ".pkmn");
            List<String> content = Files.readAllLines(filePath);
            Pokemon temp = new Pokemon(content.get(0));
            for (int i = 1; i < content.size(); i++) {
                if (content.get(i).startsWith("IVs:")) {
                    temp.setIVs(content.get(i));
                } else if (content.get(i).startsWith("EVs:")) {
                    temp.setEVs(content.get(i));
                } else if (content.get(i).startsWith("Ability:")) {
                    temp.setAbility(content.get(i));
                } else if (content.get(i).startsWith("Level:")) {
                    temp.setLevel(content.get(i));
                } else if (content.get(i).startsWith("- ")) {
                    //Move
                } else {
                    if (content.get(i).contains("Nature")) {
                        temp.setNature(content.get(i));
                    } else {
                        if (!content.get(i).isEmpty()) {
                            pokemon.add(temp);
                            temp = new Pokemon(content.get(i));
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Pokemon p : pokemon) {
            System.out.println(p);
        }
    }

    public String getName() {
        return name;
    }
}
