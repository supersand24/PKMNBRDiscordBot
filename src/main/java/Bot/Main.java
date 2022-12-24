package Bot;

import Pokemon.Type;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private static final int pkmnGeneration = 4;
    private static final List<Type> types = new ArrayList<>();

    public static void main(String[] args) {

        //Initialize Pokemon Related.
        types.add(new Type("Normal"));
        types.add(new Type("Fighting"));
        types.add(new Type("Flying"));
        types.add(new Type("Poison"));
        types.add(new Type("Ground"));
        types.add(new Type("Rock"));
        types.add(new Type("Bug"));
        types.add(new Type("Ghost"));
        types.add(new Type("Steel"));
        types.add(new Type("Fire"));
        types.add(new Type("Water"));
        types.add(new Type("Grass"));
        types.add(new Type("Electric"));
        types.add(new Type("Psychic"));
        types.add(new Type("Ice"));
        types.add(new Type("Dragon"));
        types.add(new Type("Dark"));

        //Gen 2-5
        types.get(0).addSuperEffective(types.get(1)).addImmunity(types.get(7));
        types.get(1).addSuperEffective(types.get(2)).addNotEffective(types.get(5)).addNotEffective(types.get(6)).addSuperEffective(types.get(13)).addNotEffective(types.get(16));
        types.get(2).addNotEffective(types.get(1)).addImmunity(types.get(4)).addSuperEffective(types.get(5)).addNotEffective(types.get(6)).addNotEffective(types.get(11)).addSuperEffective(types.get(14));
        types.get(3).addNotEffective(types.get(1)).addNotEffective(types.get(3)).addSuperEffective(types.get(4)).addNotEffective(types.get(6)).addNotEffective(types.get(11)).addSuperEffective(types.get(13));
        types.get(4).addNotEffective(types.get(3)).addNotEffective(types.get(5)).addSuperEffective(types.get(10)).addSuperEffective(types.get(11)).addImmunity(types.get(12)).addSuperEffective(types.get(14));
        types.get(5).addNotEffective(types.get(0)).addSuperEffective(types.get(1)).addNotEffective(types.get(2)).addNotEffective(types.get(3)).addSuperEffective(types.get(4)).addSuperEffective(types.get(8)).addNotEffective(types.get(9)).addSuperEffective(types.get(10)).addSuperEffective(types.get(11));
        types.get(6).addNotEffective(types.get(1)).addSuperEffective(types.get(2)).addNotEffective(types.get(4)).addSuperEffective(types.get(5)).addSuperEffective(types.get(9)).addNotEffective(types.get(11));
        types.get(7).addImmunity(types.get(0)).addImmunity(types.get(1)).addNotEffective(types.get(3)).addNotEffective(types.get(6)).addSuperEffective(types.get(7)).addSuperEffective(types.get(16));
        types.get(8).addNotEffective(types.get(0)).addSuperEffective(types.get(1)).addNotEffective(types.get(2)).addImmunity(types.get(3)).addSuperEffective(types.get(4)).addNotEffective(types.get(5)).addNotEffective(types.get(6)).addNotEffective(types.get(7))
                .addNotEffective(types.get(8)).addSuperEffective(types.get(9)).addNotEffective(types.get(11)).addNotEffective(types.get(13)).addNotEffective(types.get(14)).addNotEffective(types.get(15)).addNotEffective(types.get(16));
        types.get(9).addSuperEffective(types.get(4)).addSuperEffective(types.get(5)).addNotEffective(types.get(6)).addNotEffective(types.get(8)).addNotEffective(types.get(9)).addSuperEffective(types.get(10)).addNotEffective(types.get(11)).addNotEffective(types.get(14));
        types.get(10).addNotEffective(types.get(8)).addNotEffective(types.get(9)).addNotEffective(types.get(10)).addSuperEffective(types.get(11)).addSuperEffective(types.get(12)).addNotEffective(types.get(14));
        types.get(11).addSuperEffective(types.get(2)).addSuperEffective(types.get(3)).addNotEffective(types.get(4)).addSuperEffective(types.get(6)).addSuperEffective(types.get(9)).addNotEffective(types.get(10)).addNotEffective(types.get(11)).addNotEffective(types.get(12)).addSuperEffective(types.get(14));
        types.get(12).addNotEffective(types.get(2)).addSuperEffective(types.get(4)).addNotEffective(types.get(8)).addNotEffective(types.get(12));
        types.get(13).addNotEffective(types.get(1)).addSuperEffective(types.get(6)).addSuperEffective(types.get(7)).addNotEffective(types.get(13)).addSuperEffective(types.get(16));
        types.get(14).addSuperEffective(types.get(1)).addSuperEffective(types.get(5)).addSuperEffective(types.get(8)).addSuperEffective(types.get(9)).addNotEffective(types.get(14));
        types.get(15).addNotEffective(types.get(9)).addNotEffective(types.get(10)).addNotEffective(types.get(11)).addNotEffective(types.get(12)).addSuperEffective(types.get(14)).addSuperEffective(types.get(15));
        types.get(16).addSuperEffective(types.get(1)).addSuperEffective(types.get(6)).addNotEffective(types.get(7)).addImmunity(types.get(13)).addNotEffective(types.get(16));

        //Initialize Discord JDA.
        JDABuilder builder = JDABuilder.create(
                getToken(),
                GatewayIntent.GUILD_MEMBERS
        ).setMemberCachePolicy(MemberCachePolicy.ALL).disableCache(
                CacheFlag.STICKER,
                CacheFlag.EMOJI
        );

        builder.addEventListeners( new Listener() );

        try {
            JDA jda = builder.build();
            jda.awaitReady();

            //Slash Commands
            //jda.getGuilds().get(0).upsertCommand("balance","Checks how points you have.").queue();
            jda.getGuilds().get(0).upsertCommand("type-matchup","Checks the weaknesses and resistances of a type combo").addOptions(
                    new OptionData(OptionType.INTEGER,"primary","The Primary Type").addChoices(getTypeChoices()).setRequired(true),
                    new OptionData(OptionType.INTEGER,"secondary","The Secondary Type").addChoices(getTypeChoices())
            ).queue();

            //System.out.println(GoogleAuthorizeUtil.getData("1cZU7dmaiQnoL15qKuxz_uHN5yhRIIt4RJuM0eGmcFPY","Tracker","A1:G24").toString());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getToken() {
        try {
            return (String) Files.readAllLines(Paths.get("bot.token")).get(0);
        } catch (NoSuchFileException var1) {
            log.error("Could not find the bot.token file!");
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        System.exit(1);
        return "";
    }

    private static List<Command.Choice> getTypeChoices() {
        List<Command.Choice> choices = new ArrayList<>();
        for (int i = 0; i < types.size(); i++) {
            choices.add(new Command.Choice(types.get(i).getName(),i));
        }
        return choices;
    }

    public static List<Type> getTypes() {
        return types;
    }

}
