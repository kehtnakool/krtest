//import paketinimi.klassinimi

import controller.Controller;

import javax.swing.*;

/**
 * Klass Main.java
 * Tegemist on mingi standardvõttega Swing GUI programmide käivitamiseks.
 * Millegipärast tuleb programmi klasside loomine paigutada meetodisse start() ja mitte meetodisse main()
 * Asja põhimõte on üldiselt vist selles, et kõik GUI elemendid saaksid kindlasti valmis enne,
 * kui mingi osa GUIst käivitatakse
 */
public class Main {
    private static void start() {
        new Controller();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::start);
    }
}//klass Main lõpp