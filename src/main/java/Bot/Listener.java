package Bot;

import Pokemon.Type;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Listener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        switch (e.getName()) {
            case "type-matchup" -> {
                //Input
                Type[] types = new Type[e.getOptions().size()];
                for (int i = 0; i < e.getOptions().size(); i++) {
                    types[i] = Main.getTypes().get(e.getOptions().get(i).getAsInt());
                }

                //Initialize Calculations
                StringBuilder sb = new StringBuilder();
                HashMap<Type,Integer> effectiveness = new HashMap<>();
                for (Type type : Main.getTypes()) {
                    effectiveness.put(type,0);
                }
                sb.append("**__Type Matchup for ");
                if (types.length == 2)
                    sb.append(types[0].getName()).append(" ").append(types[1].getName());
                else
                    sb.append(types[0].getName());
                sb.append("__**\n");

                //Calculations
                for (Type type : types) {
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

                //Force Immunities to -100 (For String Builder)
                for (Map.Entry<Type,Integer> entries : effectiveness.entrySet()) {
                    if (entries.getValue() < -50) {
                        effectiveness.put(entries.getKey(),-100);
                    }
                }

                //Output
                if (types.length == 2) {
                    if (effectiveness.containsValue(2)) {
                        List<String> x4 = new ArrayList<>();
                        sb.append("**4x** -> ");
                        for (Map.Entry<Type, Integer> entries : effectiveness.entrySet()) {
                            if (entries.getValue() == 2) {
                                x4.add(entries.getKey().getName());
                            }
                        }
                        sb.append(x4).append("\n");
                    }
                }

                if (effectiveness.containsValue(1)) {
                    List<String> x2 = new ArrayList<>();
                    sb.append("**2x** -> ");
                    for (Map.Entry<Type, Integer> entries : effectiveness.entrySet()) {
                        if (entries.getValue() == 1) {
                            x2.add(entries.getKey().getName());
                        }
                    }
                    sb.append(x2).append("\n");
                }

                if (effectiveness.containsValue(0)) {
                    List<String> x1 = new ArrayList<>();
                    sb.append("**1x** -> ");
                    for (Map.Entry<Type, Integer> entries : effectiveness.entrySet()) {
                        if (entries.getValue() == 0) {
                            x1.add(entries.getKey().getName());
                        }
                    }
                    sb.append(x1).append("\n");
                }

                if (effectiveness.containsValue(-100)) {
                    List<String> x0 = new ArrayList<>();
                    sb.append("**0x** -> ");
                    for (Map.Entry<Type, Integer> entries : effectiveness.entrySet()) {
                        if (entries.getValue() == -100) {
                            x0.add(entries.getKey().getName());
                        }
                    }
                    sb.append(x0).append("\n");
                }

                if (effectiveness.containsValue(-1)) {
                    List<String> x05 = new ArrayList<>();
                    sb.append("**0.5x** -> ");
                    for (Map.Entry<Type, Integer> entries : effectiveness.entrySet()) {
                        if (entries.getValue() == -1) {
                            x05.add(entries.getKey().getName());
                        }
                    }
                    sb.append(x05).append("\n");
                }

                if (types.length == 2) {
                    if (effectiveness.containsValue(-2)) {
                        List<String> x025 = new ArrayList<>();
                        sb.append("**0.25x** -> ");
                        for (Map.Entry<Type, Integer> entries : effectiveness.entrySet()) {
                            if (entries.getValue() == -2) {
                                x025.add(entries.getKey().getName());
                            }
                        }
                        sb.append(x025).append("\n");
                    }
                }

                e.reply(sb.toString()).setEphemeral(!e.getChannel().getName().equals("bot-spam")).queue();

            }
        }
    }
}
