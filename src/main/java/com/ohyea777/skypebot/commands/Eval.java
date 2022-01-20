package com.ohyea777.skypebot.commands;

import com.ohyea777.skypebot.Command;
import com.ohyea777.skypebot.CommandHandler;
import com.skype.ChatMessage;
import com.skype.SkypeException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Arrays;
import java.util.List;

@CommandHandler
public class Eval extends Command {

    private static final List<Character> allowedChars = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '/', '*', '^', '%', '.', '(', ')', ' ');

    @Override
    public String getCommand() {
        return "Eval";
    }

    @Override
    public String getHelp() {
        return "To evaluate a math expression do '!eval <expression>'";
    }

    @Override
    public void onCommand(ChatMessage message, String[] args, String label) throws SkypeException {
        if (args.length <= 0) {
            sendMessage(message, "%prefix% Eval: Invalid expression!");

            return;
        } else {
            String patched = patchArgs(args);

            for (char c : patched.toCharArray()) {
                if (!allowedChars.contains(c)) {
                    sendMessage(message, "%prefix% Eval: Expression contains illegal chars!");

                    return;
                }
            }

            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");

            try {
                sendMessage(message, "%prefix% Eval: " + engine.eval(patched));
            } catch (Exception e) {
                sendMessage(message, "%prefix% Eval: Invalid expression!");
            }
        }
    }

    private String patchArgs(String[] args) {
        if (args.length == 1) {
            return args[0];
        }

        String patched = args[0];

        for (int i = 1; i < args.length; i ++) {
            patched += " " + args[i];
        }

        return patched;
    }

}
