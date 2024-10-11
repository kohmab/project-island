package com.javarush.pavlichenko.controller;

import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.helpers.Helpers;
import com.javarush.pavlichenko.view.ConsoleView;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;

import java.util.HashSet;
import java.util.Set;


public class GameController {
    private final Terminal terminal;
    private final LineReader reader;
    private final ConsoleView view;


    public GameController(Terminal terminal, ConsoleView view) {
        this.terminal = terminal;
        this.view = view;

        Set<String> commands = new HashSet<>() {{
            addAll(Helpers.getClassNames());
            add("count");
            add("exit");
            add("help");
            add("next");
            add("story");

        }};
        reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(new StringsCompleter(commands))
                .build();
    }

    public void invoke(){
        view.printDayStats();
        showPrompt();
    }

    public void showPrompt() {

        String command;
        try {
            command = reader.readLine("\n > ").trim();
        } catch (UserInterruptException | EndOfFileException e) {
            command = "exit";
        }

        if ("exit".equals(command)) {
            System.exit(0);
        } else if ("next".equals(command)) {
            return;
        } else if ("count".equals(command)) {
            view.printTotalCount();
        } else if ("story".equals(command)) {
            view.printRandomBiography();
        } else if (Helpers.getClassNames().contains(command)) {
            Class<? extends IslandEntity> entityClass = Helpers.getClassNamed(command);
            view.printCountOf(entityClass);
        } else {
            terminal.writer().println(HELP_MESSAGE);
        }

        showPrompt();
    }


    private final String HELP_MESSAGE =
            """
                       Available commands:
                       XXX    -   show distribution of corresponding entity on the island
                                  (for XXX in %s);
                       count  -   print the number of all entities;                                         \s
                       exit   -   exit the game;
                       help   -   show this message;
                       next   -   next iteration;
                       story  -   display story of the random perished entity;
                    """.formatted(Helpers.getClassNames());


}
