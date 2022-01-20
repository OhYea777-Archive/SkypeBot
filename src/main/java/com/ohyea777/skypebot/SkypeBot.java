package com.ohyea777.skypebot;

import com.skype.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SkypeBot {

    private CommandRegistry commandRegistry;
    private static List<String> facts;

    static {
        facts = Arrays.asList(new String[] {
               "Woodpecker scalps, porpoise teeth, and giraffe tails have all been used as money.",
                "There is no solid proof of who built the Taj Mahal.",
                "Australian Rules football was originally designed to give cricketers something to play during the off season.",
                "The male seahorse carries the eggs until they hatch instead of the female.",
                "Ignoramus: The grand jury used to write ignoramus on the back of indictments not found or not to be sent to court. This was often constructed as an indication of the stupidity of the jury, hence its present meaning.",
                "Camels chew in a figure 8 pattern.",
                "India has a Bill of Rights for cows.",
                "Jackals have one more pair of chromosomes than dogs or wolves.",
                "Dartboards are made out of horse hairs.",
                "The average life of a taste bud is 10 days.",
                "In 1980, a Las Vegas hospital suspended workers for betting on when patients would die.",
                "Dibble means to drink like a duck.",
                "It was once against the law to have a pet dog in a city in Iceland.",
                "A B-25 bomber crashed into the 79th floor of the Empire State Building on July 28, 1945."
        });
    }

    public SkypeBot() {
        commandRegistry = new CommandRegistry();

        try {
            Skype.addChatMessageListener(new ChatMessageAdapter() {
                @Override
                public void chatMessageReceived(ChatMessage receivedChatMessage) throws SkypeException {
                    receivedChatMessage.getChat().send(facts.get(new Random().nextInt(facts.size() - 1)));
                }
            });
        } catch (SkypeException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Skype.setDaemon(false);

        new SkypeBot();
    }

}
