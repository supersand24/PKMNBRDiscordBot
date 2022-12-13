package Bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
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

        } catch (InterruptedException e) {
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
