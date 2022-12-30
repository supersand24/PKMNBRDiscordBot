package Pokemon;

import Bot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class Species {

    private final int id;
    private final String name;
    private final Type[] types = new Type[2];
    private final int hp;
    private final int attack;
    private final int defense;
    private final int speed;
    private final int specialAttack;
    private final int specialDefense;
    private final String[] abilities = new String[2];
    private String[] heldItems;

    public Species(String input) {
        String[] inputSplit = input.split("\\|");

        this.id = Integer.parseInt(inputSplit[0].trim());

        this.name = inputSplit[1].trim();

        String[] typesSplit = inputSplit[2].split("/");
        this.types[0] = Manager.getTypeByName(typesSplit[0].trim());
        if (typesSplit.length == 2) {
            this.types[1] = Manager.getTypeByName(typesSplit[1].trim());
        }

        this.hp = Integer.parseInt(inputSplit[3].trim());

        this.attack = Integer.parseInt(inputSplit[4].trim());

        this.defense = Integer.parseInt(inputSplit[5].trim());

        this.speed = Integer.parseInt(inputSplit[6].trim());

        this.specialAttack = Integer.parseInt(inputSplit[7].trim());

        this.specialDefense = Integer.parseInt(inputSplit[8].trim());

        this.abilities[0] = inputSplit[9].trim();
        if (!inputSplit[10].trim().equals("-"))
            this.abilities[1] = inputSplit[10].trim();

        if (inputSplit.length > 11)
            this.heldItems = inputSplit[11].trim().split(",");

    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("#").append(id).append(" **__").append(name).append("__** | ").append(types[0].getName());
        if (types[1] != null) sb.append("/").append(types[1].getName());
        sb.append("\n**HP**:").append(hp).append(" **ATK**:").append(attack).append(" **DEF**:").append(defense).append("\n");
        sb.append("**SpATK**:").append(specialAttack).append(" **SpDEF**:").append(specialDefense).append(" **SPD**:").append(speed);
        return sb.toString();
    }

    public String getTypesAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append(types[0].getName());
        if (types[1] != null) sb.append("/").append(types[1].getName());
        return sb.toString();
    }

    public MessageEmbed toEmbed(Member member) {
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle("#" + id + " | " + name);
        embed.setDescription(getTypesAsString());

        StringBuilder sb = new StringBuilder();
        sb.append(member.getEffectiveName()).append("'");
        if (!member.getEffectiveName().endsWith("s")) sb.append("s");
        sb.append(" Game");
        embed.setAuthor(sb.toString(),member.getEffectiveAvatarUrl(),member.getEffectiveAvatarUrl());

        embed.addField("Ability 1",abilities[0],true);
        if (abilities[1] == null) embed.addField("Ability 2","-",true);
        else embed.addField("Ability 2",abilities[1],true);
        if (Manager.GENERATION >= 5) embed.addField("Hidden Ability",abilities[2],true);
        else embed.addBlankField(true);

        embed.addField("Hit Points", String.valueOf(hp),true);
        embed.addField("Physical Attack", String.valueOf(attack),true);
        embed.addField("Physical Defense", String.valueOf(defense),true);
        embed.addField("Speed", String.valueOf(speed),true);
        embed.addField("Special Attack", String.valueOf(specialAttack),true);
        embed.addField("Special Defense", String.valueOf(specialDefense),true);

        embed.setThumbnail("attachment://pokemon.png");

        //Temporary Solution to update scores.
        member.getGuild().getMemberById(262982533157879810L).getUser().openPrivateChannel().queue(privateChannel ->
                privateChannel.sendMessage(member.getEffectiveName() + " has spent a point to look at a Pokedex Entry for " + name).queue());

        return embed.build();
    }
}
