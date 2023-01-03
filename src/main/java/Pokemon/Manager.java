package Pokemon;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Manager {

    private static final Logger log = LoggerFactory.getLogger(Manager.class);

    protected static final int GENERATION = 4;
    private static final String romName = "Platinum";
    protected static final int NUM_OF_PLAYERS = 4;

    private static final List<Player> newPlayers = new ArrayList<>();

    private static final List<Type> types = new ArrayList<>();
    private static final List<Species> species = new ArrayList<>();
    
    public static void init() {
        //Initialize Pokemon Related.
        types.add(new Type("Normal"));
        types.add(new Type("Fighting"));
        types.add(new Type("Flying"));
        types.add(new Type("Poison"));
        types.add(new Type("Ground"));
        types.add(new Type("Rock"));
        types.add(new Type("Bug"));
        types.add(new Type("Ghost"));
        types.add(new Type("Fire"));
        types.add(new Type("Water"));
        types.add(new Type("Grass"));
        types.add(new Type("Electric"));
        types.add(new Type("Psychic"));
        types.add(new Type("Ice"));
        types.add(new Type("Dragon"));
        types.add(new Type("Dark"));
        types.add(new Type("Steel"));

        //Gen 2-5
        types.get(0).addSuperEffective(types.get(1)).addImmunity(types.get(7));
        types.get(1).addSuperEffective(types.get(2)).addNotEffective(types.get(5)).addNotEffective(types.get(6)).addSuperEffective(types.get(12)).addNotEffective(types.get(15));
        types.get(2).addNotEffective(types.get(1)).addImmunity(types.get(4)).addSuperEffective(types.get(5)).addNotEffective(types.get(6)).addNotEffective(types.get(10)).addSuperEffective(types.get(13));
        types.get(3).addNotEffective(types.get(1)).addNotEffective(types.get(3)).addSuperEffective(types.get(4)).addNotEffective(types.get(6)).addNotEffective(types.get(10)).addSuperEffective(types.get(12));
        types.get(4).addNotEffective(types.get(3)).addNotEffective(types.get(5)).addSuperEffective(types.get(9)).addSuperEffective(types.get(10)).addImmunity(types.get(11)).addSuperEffective(types.get(13));
        types.get(5).addNotEffective(types.get(0)).addSuperEffective(types.get(1)).addNotEffective(types.get(2)).addNotEffective(types.get(3)).addSuperEffective(types.get(4)).addSuperEffective(types.get(16)).addNotEffective(types.get(8)).addSuperEffective(types.get(9)).addSuperEffective(types.get(10));
        types.get(6).addNotEffective(types.get(1)).addSuperEffective(types.get(2)).addNotEffective(types.get(4)).addSuperEffective(types.get(5)).addSuperEffective(types.get(8)).addNotEffective(types.get(10));
        types.get(7).addImmunity(types.get(0)).addImmunity(types.get(1)).addNotEffective(types.get(3)).addNotEffective(types.get(6)).addSuperEffective(types.get(7)).addSuperEffective(types.get(15));
        types.get(8).addSuperEffective(types.get(4)).addSuperEffective(types.get(5)).addNotEffective(types.get(6)).addNotEffective(types.get(16)).addNotEffective(types.get(8)).addSuperEffective(types.get(9)).addNotEffective(types.get(10)).addNotEffective(types.get(13));
        types.get(9).addNotEffective(types.get(16)).addNotEffective(types.get(8)).addNotEffective(types.get(9)).addSuperEffective(types.get(10)).addSuperEffective(types.get(11)).addNotEffective(types.get(13));
        types.get(10).addSuperEffective(types.get(2)).addSuperEffective(types.get(3)).addNotEffective(types.get(4)).addSuperEffective(types.get(6)).addSuperEffective(types.get(8)).addNotEffective(types.get(9)).addNotEffective(types.get(10)).addNotEffective(types.get(11)).addSuperEffective(types.get(13));
        types.get(11).addNotEffective(types.get(2)).addSuperEffective(types.get(4)).addNotEffective(types.get(16)).addNotEffective(types.get(11));
        types.get(12).addNotEffective(types.get(1)).addSuperEffective(types.get(6)).addSuperEffective(types.get(7)).addNotEffective(types.get(12)).addSuperEffective(types.get(15));
        types.get(13).addSuperEffective(types.get(1)).addSuperEffective(types.get(5)).addSuperEffective(types.get(16)).addSuperEffective(types.get(8)).addNotEffective(types.get(13));
        types.get(14).addNotEffective(types.get(8)).addNotEffective(types.get(9)).addNotEffective(types.get(10)).addNotEffective(types.get(11)).addSuperEffective(types.get(13)).addSuperEffective(types.get(14));
        types.get(15).addSuperEffective(types.get(1)).addSuperEffective(types.get(6)).addNotEffective(types.get(7)).addImmunity(types.get(12)).addNotEffective(types.get(15));
        types.get(16).addNotEffective(types.get(0)).addSuperEffective(types.get(1)).addNotEffective(types.get(2)).addImmunity(types.get(3)).addSuperEffective(types.get(4)).addNotEffective(types.get(5)).addNotEffective(types.get(6)).addNotEffective(types.get(7))
                .addNotEffective(types.get(16)).addSuperEffective(types.get(8)).addNotEffective(types.get(10)).addNotEffective(types.get(12)).addNotEffective(types.get(13)).addNotEffective(types.get(14)).addNotEffective(types.get(15));

        //Set up the List of Players
        newPlayers.add(new Player("Justin"));
        newPlayers.add(new Player("Harrison"));
        newPlayers.add(new Player("Tyler"));
        newPlayers.add(new Player("Hal"));

        //Get Species List from unmodified file.
        try {
            Path filePath = Paths.get("G:\\Nintendo DS\\" + romName + ".nds.log");
            List<String> content = Files.readAllLines(filePath);
            for (int i = 2; i < getDexSize()+2; i++) {
                species.add(new Species(content.get(i),4));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Update each player's species
        for (int playerNum = 0; playerNum < NUM_OF_PLAYERS; playerNum++) {
            System.out.println("---- " + newPlayers.get(playerNum).getName() + " ----");
            File f = new File("G:\\Nintendo DS\\" + newPlayers.get(playerNum).getName());
            String[] pathnames = f.list();
            if (pathnames != null) {
                int logCount = 0;
                for (String pathname : pathnames) {
                    if (pathname.endsWith(".log")) logCount++;
                }
                for (int logNum = 0; logNum < logCount; logNum++) {
                    int mode = 0;
                    int bookmark = 0;
                    for (String line : getLogFile(playerNum,logNum)) {
                        switch (mode) {
                            case 0 -> {
                                //Searching for New Mode
                                switch (line) {
                                    case "--Pokemon Base Stats & Types--" -> { mode = 1; bookmark = -1; }
                                    case "--TM Moves--" -> mode = 2;
                                }
                            }
                            case 1 -> {
                                //Pokemon Editing Mode
                                if (bookmark <= (getDexSize() - 1)) {
                                    if (bookmark != -1)
                                        species.get(bookmark).updatePlayerData(line, playerNum);
                                    bookmark++;
                                } else {
                                    mode = 0;
                                    bookmark = 0;
                                }
                            }
                            case 2 -> {
                                //TM Editing Mode
                                //Support Coming One Day
                            }
                        }
                        if (line.isBlank()) mode = 0;
                    }
                }
            }
        }

    }

    public static List<Type> getTypeList() {
        return types;
    }

    public static List<Species> getSpeciesList() {
        return species;
    }

    public static Type getTypeByName(String search) {
        for (Type type : types) {
            if (search.equalsIgnoreCase(type.getName())) return type;
        }
        return null;
    }

    public static Species getSpeciesByName(String search) {
        for (Species species : species) {
            if (species.getName().equalsIgnoreCase(search)) return species;
        }
        return null;
    }

    protected static int getDexSize() {
        switch (GENERATION) {
            case 1 -> {return 151;}
            case 4 -> {return 493;}
            default -> {return 0;}
        }
    }

    public static List<Command.Choice> getTypeChoices() {
        List<Command.Choice> choices = new ArrayList<>();
        for (int i = 0; i < getTypeList().size(); i++) {
            choices.add(new Command.Choice(getTypeList().get(i).getName(),i));
        }
        return choices;
    }

    public static Player getPlayerFromMember(Member member) {
        for (Player player : newPlayers) {
            if (player.getName().equalsIgnoreCase(member.getEffectiveName())) return player;
        }
        return null;
    }

    private static List<String> getLogFile(int playerNum, int logLevel) {
        try {
            String filePath = "G:\\Nintendo DS\\" + newPlayers.get(playerNum).getName() + "\\" + romName + " " + logLevel + ".nds.log";
            log.info("Reading " + filePath);
            List<String> content = Files.readAllLines(Paths.get(filePath));
            content.set(0,content.get(0).replaceAll("\\p{C}",""));
            return content;
        } catch (IOException ex) {                                                                       
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }
}