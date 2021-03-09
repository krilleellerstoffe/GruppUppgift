package view.server;

import javax.swing.*;

/**
 * En klass som ger alla underklasser till Viewer unikt content.
 * @version 1.0
 * @author Viktor Johansson
 */
public interface IViewer {

    /**
     * Används för att hämta content från Viewer underklasserna samt att ge de ett unikt utseende.
     *
     * @return JPanel innehållet i fönstrena.
     */
    public JPanel content();
}
