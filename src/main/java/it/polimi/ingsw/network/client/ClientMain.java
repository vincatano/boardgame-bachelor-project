package it.polimi.ingsw.network.client;

import it.polimi.ingsw.view.cli.CliHandler;
import it.polimi.ingsw.view.gui.GuiHandler;
import it.polimi.ingsw.view.gui.GuiView;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 * The client main class set the parameters passed from command line,creates the Client object and according to the passed value initialize gui or cli
 */
public class ClientMain {
    private static String addr = "127.0.0.1";

    public static void main(String[] args){
        OptionParser parser = new OptionParser();
        parser.accepts("a","server address").withRequiredArg().ofType(String.class).defaultsTo(addr);
        parser.accepts("gui","gui interface");
        parser.accepts("cli","cli interface");
        OptionSet options = parser.parse(args);
        addr = (String) options.valueOf("a");
        int ui;
        if(options.has("cli")){
            ui=2;
        }else{
            ui=1;
        }
        Client client = new Client(addr);

        if(ui==1) {
            GuiHandler guiHandler = new GuiHandler();
            client.setQueue(guiHandler);
            guiHandler.setQueue(client.getQueue());
            GuiView.setGuiHandler(guiHandler);
            GuiView.setClient(client);
            GuiView.launching();
        }else{
            CliHandler cliHandler = new CliHandler(client);
            client.setQueue(cliHandler);
            cliHandler.setQueue(client.getQueue());
            cliHandler.welcome();
            cliHandler.initialize();
            cliHandler.receiveCommand();

        }

    }
}

