package com.ohyea777.skypebot.commands;

import com.ohyea777.skypebot.Command;
import com.ohyea777.skypebot.CommandHandler;
import com.skype.ChatMessage;
import com.skype.SkypeException;

@CommandHandler
public class Min extends Command {

    @Override
    public String getCommand() {
        return "Min";
    }

    @Override
    public String getHelp() {
        return "Find the min of two numbers '!min <num> <num>'";
    }

    @Override
    public void onCommand(ChatMessage message, String[] args, String label) throws SkypeException {
        if (args.length != 2) {
            sendMessage(message, "%prefix% Min: Invalid number of args!");

            return;
        } else {
            double[] numbers = new double[args.length];

            for (int i = 0; i < args.length; i ++) {
                try {
                    numbers[i] = Double.parseDouble(args[i]);
                } catch (NumberFormatException e) {
                    sendMessage(message, "%prefix% Min: One of the args were not a number!");

                    return;
                }
            }

            sendMessage(message, "%prefix% Min: " + Math.min(numbers[0], numbers[1]));
        }
    }

}
