package view;

import view.panels.ClientConsole;
import view.panels.ServerConsole;

public class MainView {

    public MainView() {

        ServerConsole sc = new ServerConsole("test", 400, 400);
        sc.location(300, 300);
        sc.display();

        ClientConsole cc = new ClientConsole("test", 400, 400);
        cc.display();

    }

}
