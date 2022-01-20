package com.ohyea777.skypebot;

import com.skype.ChatMessage;
import com.skype.SkypeException;

public abstract class Command {

    public abstract String getCommand();

    public abstract String getHelp();

    public abstract void onCommand(ChatMessage message, String[] args, String label) throws SkypeException;

    protected void sendMessage(ChatMessage receivedChatMessage, String message) throws SkypeException {
        String commandLabel = receivedChatMessage.getContent().substring(1).split(" ")[0];

        receivedChatMessage.getChat().send(message.replace("%prefix%", Messages.PREFIX).replace("%command%", commandLabel));
    }

}
