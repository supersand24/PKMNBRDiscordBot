package Pokemon;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Species {

    private final int dexNumber;
    private final String name;
    private final List<Type[]> types = new ArrayList<>();
    private final List<Integer[]> baseStats = new ArrayList<>();
    private final List<String[]> abilities = new ArrayList<>();
    private final List<String[]> heldItems = new ArrayList<>();

    public Species(String input, int numOfPlayers) {
        //Split the String
        String[] inputSplit = input.split("\\|");

        //Dex Number & Name
        this.dexNumber = Integer.parseInt(inputSplit[0].trim());
        this.name = inputSplit[1].trim();

        for (int i = 1; i <= numOfPlayers; i++) {

            //Types
            types.add(new Type[2]);

            //Base Stats
            switch (Manager.GENERATION) {
                case 1 -> baseStats.add(new Integer[5]);
                default -> baseStats.add(new Integer[6]);
            }

            //Abilities
            abilities.add(new String[2]);

            //Held Items
            heldItems.add(new String[5]);
        }
    }

    public void updatePlayerData(String input, int playerNum) {
        String[] inputSplit = input.split("\\|");
        //Types
        String[] typesSplit = inputSplit[2].split("/");
        types.get(playerNum)[0] = Manager.getTypeByName(typesSplit[0].trim());
        if (typesSplit.length == 2) {
            types.get(playerNum)[1] = Manager.getTypeByName(typesSplit[1].trim());
        } else {
            types.get(playerNum)[1] = null;
        }

        //Base Stats
        baseStats.get(playerNum)[0] = Integer.parseInt(inputSplit[3].trim());
        baseStats.get(playerNum)[1] = Integer.parseInt(inputSplit[4].trim());
        baseStats.get(playerNum)[2] = Integer.parseInt(inputSplit[5].trim());
        baseStats.get(playerNum)[3] = Integer.parseInt(inputSplit[6].trim());
        baseStats.get(playerNum)[4] = Integer.parseInt(inputSplit[7].trim());
        baseStats.get(playerNum)[5] = Integer.parseInt(inputSplit[8].trim());

        //Abilities
        abilities.get(playerNum)[0] = inputSplit[9].trim();
        if (!inputSplit[10].trim().equals("-"))
            abilities.get(playerNum)[1] = inputSplit[10].trim();
    }

    public String getName() {
        return name;
    }

    public String getTypesAsString(int playerNum) {
        StringBuilder sb = new StringBuilder();
        sb.append(types.get(playerNum)[0].getName());
        if (types.get(playerNum)[1] != null) sb.append("/").append(types.get(playerNum)[1].getName());
        return sb.toString();
    }

    public MessageEmbed toEmbed(Member member, int playerNum) {
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle("#" + dexNumber + " | " + getName());
        embed.setDescription(getTypesAsString(playerNum));

        StringBuilder sb = new StringBuilder();
        sb.append(member.getEffectiveName()).append("'");
        if (!member.getEffectiveName().endsWith("s")) sb.append("s");
        sb.append(" Game");
        embed.setAuthor(sb.toString(),member.getEffectiveAvatarUrl(),member.getEffectiveAvatarUrl());

        embed.addField("Ability 1",abilities.get(playerNum)[0],true);
        if (abilities.get(playerNum)[1] == null) embed.addField("Ability 2","-",true);
        else embed.addField("Ability 2",abilities.get(playerNum)[1],true);
        if (Manager.GENERATION >= 5) embed.addField("Hidden Ability",abilities.get(playerNum)[2],true);
        else embed.addBlankField(true);

        embed.addField("Hit Points", String.valueOf(baseStats.get(playerNum)[0]),true);
        embed.addField("Physical Attack", String.valueOf(baseStats.get(playerNum)[1]),true);
        embed.addField("Physical Defense", String.valueOf(baseStats.get(playerNum)[2]),true);
        embed.addField("Speed", String.valueOf(baseStats.get(playerNum)[3]),true);
        embed.addField("Special Attack", String.valueOf(baseStats.get(playerNum)[4]),true);
        embed.addField("Special Defense", String.valueOf(baseStats.get(playerNum)[5]),true);

        embed.setThumbnail("attachment://pokemon.png");

        //Temporary Solution to update scores.
        member.getGuild().getMemberById(262982533157879810L).getUser().openPrivateChannel().queue(privateChannel ->
                privateChannel.sendMessage(member.getEffectiveName() + " has spent a point to look at a Pokedex Entry for " + getName() + " #" + dexNumber).queue());

        return embed.build();
    }
}