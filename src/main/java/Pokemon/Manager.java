package Pokemon;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Manager {

    protected static final int GENERATION = 4;
    private static final String romName = "Platinum";
    private static final HashMap<Long,Player> players = new HashMap<>();

    private static final List<Type> types = new ArrayList<>();
    
    public static void init() {
        //Initialize Pokemon Related.
        types.add(new Type("Normal"));
        types.add(new Type("Fighting"));
        types.add(new Type("Flying"));
        types.add(new Type("POISON"));
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

        players.put(263492434317541386L,new Player("Tyler","Platinum 3.nds.log"));
        players.put(286307112072511490L,new Player("Harrison","Platinum 3.nds.log"));
        players.put(262982533157879810L,new Player("Justin","Platinum 3.nds.log"));
        Player hal = new Player("Hal","Platinum 2.nds.log");
        players.put(203470379795087360L,hal);
        players.put(1051864624435314688L,hal);
    }

    public static List<Type> getTypes() {
        return types;
    }

    public static Type getTypeByName(String name) {
        for (Type type : types) {
            if (name.equalsIgnoreCase(type.getName())) return type;
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
        for (int i = 0; i < getTypes().size(); i++) {
            choices.add(new Command.Choice(getTypes().get(i).getName(),i));
        }
        return choices;
    }

    public static Player getPlayerFromMember(Member member) {
        return players.get(member.getIdLong());
    }

    public static Pokemon getPokemonFromPlayer(String search, Player player) {
        for (Pokemon pokemon : player.getPokedex()) {
            if (pokemon.getName().equalsIgnoreCase(search)) {
                return pokemon;
            }
        }
        return null;
    }
    
}