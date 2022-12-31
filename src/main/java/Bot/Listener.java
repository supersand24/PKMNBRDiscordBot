package Bot;

import Pokemon.Manager;
import Pokemon.Player;
import Pokemon.Species;
import Pokemon.Type;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

public class Listener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        switch (e.getName()) {
            case "type-matchup" -> {
                //Input
                Type[] types = new Type[e.getOptions().size()];
                types[0] = Manager.getTypeList().get(e.getOptionsByName("primary").get(0).getAsInt());
                if (types.length == 2 && e.getOptionsByName("primary").get(0).getAsInt() != e.getOptionsByName("secondary").get(0).getAsInt())
                    types[1] = Manager.getTypeList().get(e.getOptionsByName("secondary").get(0).getAsInt());

                //Initialize Calculations
                StringBuilder sb = new StringBuilder();
                HashMap<Type,Integer> effectiveness = new HashMap<>();
                for (Type type : Manager.getTypeList()) {
                    effectiveness.put(type,0);
                }
                sb.append("**__Type Matchup for ");
                if (types.length == 2 && e.getOptionsByName("primary").get(0).getAsInt() != e.getOptionsByName("secondary").get(0).getAsInt())
                    sb.append(types[0].getName()).append(" ").append(types[1].getName());
                else
                    sb.append(types[0].getName());
                sb.append("__**\n");

                //Calculations
                for (Type type : types) {
                    if (type == null) break;
                    for (Type superEffectiveType : type.getSuperEffectives()) {
                        effectiveness.put(superEffectiveType,effectiveness.get(superEffectiveType) + 1);
                    }
                    for (Type notEffectiveType : type.getNotEffectives()) {
                        effectiveness.put(notEffectiveType,effectiveness.get(notEffectiveType) - 1);
                    }
                    for (Type immunities : type.getImmunities()) {
                        effectiveness.put(immunities,-100);
                    }
                }

                List<String> x4 = new ArrayList<>();
                List<String> x2 = new ArrayList<>();
                List<String> x1 = new ArrayList<>();
                List<String> x0 = new ArrayList<>();
                List<String> x05 = new ArrayList<>();
                List<String> x025 = new ArrayList<>();

                //Force Immunities to -100 (For String Builder)
                if (types.length == 2) {
                    for (Map.Entry<Type, Integer> entries : effectiveness.entrySet()) {
                        if (entries.getValue() < -50) {
                            effectiveness.put(entries.getKey(), -100);
                        }
                    }

                //Output

                    if (effectiveness.containsValue(2)) {
                        for (Map.Entry<Type, Integer> entries : effectiveness.entrySet()) {
                            if (entries.getValue() == 2) {
                                x4.add(entries.getKey().getName());
                            }
                        }
                    }
                }

                if (effectiveness.containsValue(1)) {
                    for (Map.Entry<Type, Integer> entries : effectiveness.entrySet()) {
                        if (entries.getValue() == 1) {
                            x2.add(entries.getKey().getName());
                        }
                    }
                }

                if (effectiveness.containsValue(0)) {
                    for (Map.Entry<Type, Integer> entries : effectiveness.entrySet()) {
                        if (entries.getValue() == 0) {
                            x1.add(entries.getKey().getName());
                        }
                    }
                }

                if (effectiveness.containsValue(-100)) {
                    for (Map.Entry<Type, Integer> entries : effectiveness.entrySet()) {
                        if (entries.getValue() == -100) {
                            x0.add(entries.getKey().getName());
                        }
                    }
                }

                if (effectiveness.containsValue(-1)) {
                    for (Map.Entry<Type, Integer> entries : effectiveness.entrySet()) {
                        if (entries.getValue() == -1) {
                            x05.add(entries.getKey().getName());
                        }
                    }
                }

                if (types.length == 2) {
                    if (effectiveness.containsValue(-2)) {
                        for (Map.Entry<Type, Integer> entries : effectiveness.entrySet()) {
                            if (entries.getValue() == -2) {
                                x025.add(entries.getKey().getName());
                            }
                        }
                    }
                }
                if (!x4.isEmpty()) sb.append("**4x** -> ").append(x4).append("\n");
                if (!x2.isEmpty()) sb.append("**2x** -> ").append(x2).append("\n");
                if (!x1.isEmpty()) sb.append("**1x** -> ").append(x1).append("\n");
                if (!x0.isEmpty()) sb.append("**0x** -> ").append(x0).append("\n");
                if (!x05.isEmpty()) sb.append("**0.5x** -> ").append(x05).append("\n");
                if (!x025.isEmpty()) sb.append("**0.25x** -> ").append(x025).append("\n");

                e.reply(sb.toString()).setEphemeral(!e.getChannel().getName().equals("bot-spam")).queue();

            }
            case "buy" -> {
                switch (e.getSubcommandName()) {
                    case "pokedex-entry" -> {
                        String search = e.getOptions().get(0).getAsString();
                        Member member = e.getMember();
                        if (member == null) { e.reply("That user is not in the server.").queue(); return; }
                        Player player = Manager.getPlayerFromMember(member);
                        Species pokemon = Manager.getSpeciesByName(search);
                        if (pokemon == null) {
                            e.reply("Sorry, I could not find that Pokemon.").queue();
                        } else {
                            File file = new File("pokemonSprites\\" + pokemon.getName().toUpperCase() + ".png");
                            e.replyEmbeds(pokemon.toEmbed(member,0)).addFiles(FileUpload.fromData(file,"pokemon.png")).setEphemeral(!e.getChannel().getName().equals("bot-spam")).queue();
                        }
                    }
                }
            }
        }
    }
}
