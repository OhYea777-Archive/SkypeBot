package com.ohyea777.skypebot;

import com.skype.ChatMessage;
import com.skype.ChatMessageAdapter;
import com.skype.SkypeException;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandRegistry extends ChatMessageAdapter {

    private Map<String, Command> commands;

    public CommandRegistry() {
        commands = new HashMap<String, Command>();

        init();
    }

    private void init() {
        Reflections reflections = new Reflections("com.ohyea777.skypebot");

        for (Class<?> clazz : reflections.getSubTypesOf(Command.class)) {
            if (clazz.isAnnotationPresent(CommandHandler.class)) {
                try {
                    register((Command) clazz.newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void register(Command command) {
        if (!commands.containsKey(command.getCommand().toLowerCase())) {
            commands.put(command.getCommand().toLowerCase(), command);
        }
    }

    @Override
    public void chatMessageSent(ChatMessage sentChatMessage) throws SkypeException {
        chatMessageReceived(sentChatMessage);
    }

    @Override
    public void chatMessageReceived(ChatMessage receivedChatMessage) throws SkypeException {
        if (receivedChatMessage.getType().equals(ChatMessage.Type.SAID) && receivedChatMessage.getContent().startsWith("!")) {
            String commandLabel = receivedChatMessage.getContent().substring(1).split(" ")[0];

            if (commandLabel.equalsIgnoreCase("help")) {
                sendMessage(receivedChatMessage, "%prefix% Help:");

                for (Command command : commands.values()) {
                    sendHelpMessage(receivedChatMessage, command);
                }
            } else if (commands.containsKey(commandLabel.toLowerCase())) {
                String[] args = null;

                if (receivedChatMessage.getContent().split(" ").length > 1) {
                    args = Arrays.copyOfRange(receivedChatMessage.getContent().split(" "), 1, receivedChatMessage.getContent().split(" ").length);
                } else {
                    args = new String[0];
                }

                commands.get(commandLabel.toLowerCase()).onCommand(receivedChatMessage, args, commandLabel);
            } else {
                sendMessage(receivedChatMessage, "%prefix% " + Messages.COMMAND_INVALID);
            }
        }
    }

    private void sendMessage(ChatMessage receivedChatMessage, String message) throws SkypeException{
        String commandLabel = receivedChatMessage.getContent().substring(1).split(" ")[0];

        receivedChatMessage.getChat().send(message.replace("%prefix%", Messages.PREFIX).replace("%command%", commandLabel));
    }

    private void sendHelpMessage(ChatMessage receivedChatMessage, Command command) throws SkypeException {
        String message = "-] " + command.getCommand() + ": " + command.getHelp();

        sendMessage(receivedChatMessage, message);
    }

}
