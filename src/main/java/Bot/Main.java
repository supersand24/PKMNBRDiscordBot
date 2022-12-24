package Bot;

import Pokemon.Manager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        //Initialize Pokemon Data.
        Pokemon.Manager.init();

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
                    new OptionData(OptionType.INTEGER,"primary","The Primary Type").addChoices(Manager.getTypeChoices()).setRequired(true),
                    new OptionData(OptionType.INTEGER,"secondary","The Secondary Type").addChoices(Manager.getTypeChoices())
            ).queue();

            jda.getGuilds().get(0).upsertCommand("buy","Buys an item from the Battle Royale Shop.").addSubcommands(
                    new SubcommandData("pokedex-entry","COST 1: Checks your game data, and sends you up to date information of a Pokemon.").addOptions(
                            new OptionData(OptionType.STRING,"pokemon","The Pokemon to Lookup").setRequired(true)
                    )
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

}
