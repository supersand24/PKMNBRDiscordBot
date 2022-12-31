package Pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pokemon {

    enum Gender{MALE,FEMALE,GENDERLESS}

    String speciesName;
    String nickname;
    Gender gender = Gender.GENDERLESS;
    Boolean shiny = false;
    int level = 1;
    int iv_hp = 0;
    int iv_attack = 0;
    int iv_defense = 0;
    int iv_specialDefense = 0;
    int iv_specialAttack = 0;
    int iv_speed = 0;
    int ev_hp = 0;
    int ev_attack = 0;
    int ev_defense = 0;
    int ev_specialDefense = 0;
    int ev_specialAttack = 0;
    int ev_speed = 0;
    String nature;
    String ability;
    String heldItem;
    List<String> moves = new ArrayList<>();

    public Pokemon(String showdownInput) {
        String[] f = showdownInput.split("\\(");
        for (String s : f) {
            System.out.print(s + " | ");
        }
        System.out.print("\n");
        nickname = f[0].trim();
        if (f.length > 1) {
            speciesName = f[1].split("\\)")[0].trim();
            if (f.length > 2) {
                switch (f[2].substring(0, 1)) {
                    case "M" -> gender = Gender.MALE;
                    case "F" -> gender = Gender.FEMALE;
                    default -> gender = Gender.GENDERLESS;
                }
            }
        }
        System.out.print(nickname + " | " + speciesName + " | ");
        System.out.print("\n");
    }

    protected void setIVs(String showdownInput) {
        for (String iv : showdownInput.substring("IVs:".length()).split("/")) {
            if (iv.contains("HP"))
                iv_hp = Integer.parseInt(iv.trim().split("\\s+")[0]);
            else if (iv.contains("Atk"))
                iv_attack = Integer.parseInt(iv.trim().split("\\s+")[0]);
            else if (iv.contains("Def"))
                iv_defense = Integer.parseInt(iv.trim().split("\\s+")[0]);
            else if (iv.contains("SpA"))
                iv_specialAttack = Integer.parseInt(iv.trim().split("\\s+")[0]);
            else if (iv.contains("SpD"))
                iv_specialDefense = Integer.parseInt(iv.trim().split("\\s+")[0]);
            else if (iv.contains("Spe"))
                iv_speed = Integer.parseInt(iv.trim().split("\\s+")[0]);
        }
    }

    protected void setEVs(String showdownInput) {
        for (String ev : showdownInput.substring("EVs:".length()).split("/")) {
            if (ev.contains("HP"))
                ev_hp = Integer.parseInt(ev.trim().split("\\s+")[0]);
            else if (ev.contains("Atk"))
                ev_attack = Integer.parseInt(ev.trim().split("\\s+")[0]);
            else if (ev.contains("Def"))
                ev_defense = Integer.parseInt(ev.trim().split("\\s+")[0]);
            else if (ev.contains("SpA"))
                ev_specialAttack = Integer.parseInt(ev.trim().split("\\s+")[0]);
            else if (ev.contains("SpD"))
                ev_specialDefense = Integer.parseInt(ev.trim().split("\\s+")[0]);
            else if (ev.contains("Spe"))
                ev_speed = Integer.parseInt(ev.trim().split("\\s+")[0]);
        }
    }

    protected void setAbility(String showdownInput) {
        ability = showdownInput.substring("Ability:".length()).trim();
    }

    protected void setLevel(String showdownInput) {
        level = Integer.parseInt(showdownInput.substring("Level:".length()).trim());
    }

    protected void setNature(String showdownInput) {
        nature = showdownInput.split("\\s+")[0];
    }

    protected void addMove(String showdownInput) {
        moves.add(showdownInput.substring("- ".length()).trim());
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "speciesName='" + speciesName + '\'' +
                '}';
    }
}
