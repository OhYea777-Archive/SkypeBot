package com.ohyea777.skypebot.commands;

import com.ohyea777.skypebot.CommandHandler;
import com.ohyea777.skypebot.Command;
import com.skype.ChatMessage;
import com.skype.SkypeException;

@CommandHandler
public class About extends Command {

    @Override
    public String getCommand() {
        return "About";
    }

    @Override
    public String getHelp() {
        return "Type '!about' for information about the bot";
    }

    @Override
    public void onCommand(ChatMessage message, String[] args, String label) throws SkypeException {
        sendMessage(message, "%prefix% About:");
        sendMessage(message, "-] Author: OhYea777");
    }

}
