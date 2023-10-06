package controller;

import model.Model;
import view.View;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/* Klass Controller.java. Juhib programmi tööd. Loob ise 3 objekti: Model_2, View_2 ja Timer.
 * _2 on tööversioonide tähised ja sisulist tähtsust ei oma. Otsustasin aja kontrolli paigutada Controllerisse,
 * sest aeg ja juhtimine on lähedaselt seotud. Oleks võinud panna ka Modelisse ja sealt meetoditega küsida.
 * Swing GUI tundub aeglane, oma teadmiste põhjal oletasin, et otstarbekam on timer ja aja funktsioonid hoida Controlleris
 * */

public class Controller implements WindowListener, ActionListener, KeyListener {
    Model model = new Model();
    View vjuu = new View();
    Timer timer = new Timer(1000, this);
    int game_seconds = 0;
    int game_seconds_salvestatud = 0;

    /**
     * Klassi Controller konstrukor.
     * Peamised ülesande konstruktoris on:
     * 1. täidame GUI (klass View_2) peal comboboxi mudelist saaadud kategooriatega
     * 2. näitame GUI peal mudelist saaadud pilti
     * 3. näitame GUI peal aratava sõna sildil mudelist saadud algväärtust
     * 4. registreemime kontrolleri GUI elementide sündmuste kuulajaks
     * 5. kõik on valmis, toome programmi akna nähtavale
     * <p>
     * Ühtlasi resetime mudeli benchmargi (senine halvim tulemus edetabelis)
     * ja äraarvatava sõna. Need on täpsemalt dokumenteeritud mudelis
     */
    public Controller() {
        model.reset_benchmark();
        model.reset_arvatav_sona_sildil();
//täidame gui peal dropdowni mudelist saaadud kategooriatega
        for (String kategooria : model.get_kategooriad()) {
            vjuu.get_kategooriabox().addItem(kategooria);
        }
//näitame gui peal mudelist saaadud pilti
        vjuu.get_pilt().setIcon(model.get_image());
        //näitame aratava sõna sildil mudelist saadud algväärtust
        vjuu.get_silt_arvatav_sona().setText(model.get_arvatav_sona_sildil());
//registreemime kontrolleri gui elementide sündmuste kuulajaks
        vjuu.get_nupp_cancel().addActionListener(this);
        vjuu.get_nupp_leader().addActionListener(this);
        vjuu.get_nupp_new().addActionListener(this);
        vjuu.get_nupp_send().addActionListener(this);
        vjuu.get_kategooriabox().addActionListener(this);
        vjuu.get_sisestuskast().addActionListener(this);
        vjuu.get_sisestuskast().addKeyListener(this);
        timer.start();
//kõik on valmis, toome programmi akna nähtavale
        vjuu.get_programmi_aken().setVisible(true);
    }// objekti Controller konstruktori lõpp

    /**
     * Liidese ActionListener realiseerimine.
     * Kontroller juhib programmi tööd vastusena kasutaja interaktsioonile GUI elementidega.
     * Vastavad sündmused saab kontroller liideste WindowListener, ActionListener, KeyListener kaudu.
     * ActionListener vahendab nupuvajutusi. Ühtlasi saab ActionListener endale kõik taimeri sündmused, mis tähendab,
     * et kord sekundis saabub timeri sündmus.
     */
    public void actionPerformed(ActionEvent actionEvent) {
        //------------------------------------------ taimeri sündmusele reageerides kuvame mängu aega/kellaaega
        if (!model.get_game_running()) {
            vjuu.get_kuupaev().setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        } else {
            //vjuu.get_kuupaev().setText(String.valueOf(game_seconds));
            vjuu.get_kuupaev().setText(convertSecToMMSS(game_seconds));

            game_seconds++;
        }
        //----------------------------- cancel pressed
        if (actionEvent.getSource() == vjuu.get_nupp_cancel()) {
            game_seconds = 0;
            game_seconds_salvestatud = 0;
            model.set_game_running(false);
            vjuu.get_nupp_new().setEnabled(true);
            vjuu.get_kategooriabox().setEnabled(true);
            vjuu.get_nupp_cancel().setEnabled(false);
            vjuu.get_nupp_send().setEnabled(false);
            vjuu.get_sisestuskast().setEnabled(false);
        }
        // ------------------------------ show scoreboard pressed
        if (actionEvent.getSource() == vjuu.get_nupp_leader()) {
            if (model.get_game_running()) {
                game_seconds_salvestatud = game_seconds;
            }
            model.set_game_running(false);
            vjuu.passivate();
            vjuu.naita_edetabelit(model.get_edetabel());
            vjuu.get_edetabel().addWindowListener(this);
            //System.out.println(model.get_edetabel());
        }
        //------------------------------------------------------------- new game pressed

        if (actionEvent.getSource() == vjuu.get_nupp_new()) {
            game_seconds_salvestatud = 0;
            vjuu.get_nupp_new().setEnabled(false);
            vjuu.get_kategooriabox().setEnabled(false);
            vjuu.get_nupp_send().setEnabled(true);
            vjuu.get_nupp_cancel().setEnabled(true);
            vjuu.get_sisestuskast().setEnabled(true);
            vjuu.get_pilt().setIcon(model.get_imageFiles()[0]);
            String sona = model.get_random_word(vjuu.get_kategooria());
            //System.out.println(sona);
            model.set_arvatav_sona(sona);
            model.set_game_running(true);
            vjuu.get_silt_arvatav_sona().setText(model.get_arvatav_sona_sildil());
        }
        //----------------------------------------------- send pressed
        if (vjuu.get_sisestuskast().getText().trim().length() > 0 && actionEvent.getSource() == vjuu.get_nupp_send()) {
            saada_sisend_mudelisse_tootlemiseks();

        }//end send pressed

    }// Liidese ActionListener realiseerimise LÕPP

    /**
     * Kui mäng sai läbi siis paneme GUI ja mudeli vajalikud väärtused lähteasendisse.
     * Samuti lähtestame mängu aja.
     */
    private void reset() {
        vjuu.get_nupp_send().setEnabled(false);
        vjuu.get_nupp_cancel().setEnabled(false);
        vjuu.get_nupp_new().setEnabled(true);
        vjuu.get_kategooriabox().setEnabled(true);
        vjuu.get_sisestuskast().setEnabled(true);
        vjuu.get_silt_valede_tahtede_arv().setText("Wrong 0 letters");
        model.set_image_index(0);
        game_seconds = 0;
        game_seconds_salvestatud = 0;
        //model.
    }


    private void saada_sisend_mudelisse_tootlemiseks() {
        //String kategooria = vjuu.get_kategooria();
        model.tootle_kasutaja_sisend(vjuu.get_sisestuskast().getText().toLowerCase());
        vjuu.get_sisestuskast().setText("");
        vjuu.get_silt_arvatav_sona().setText(model.get_arvatav_sona_sildil().toUpperCase());
        vjuu.get_silt_valede_tahtede_arv().setText("Wrong " + String.valueOf(model.get_valede_tahtede_arv()) + " letters "
                + model.get_valed_tahed());
        if (model.get_vale_taht()) {
            if (model.get_image_index() + 1 < model.get_piltide_arv()) {
                //System.out.println("image_index "+String.valueOf(model.get_image_index()));
                model.increment_image_index();
                vjuu.get_pilt().setIcon(model.get_image());
                // mäng sai läbi sellepärast, et on sisestatud piisavalt valesid tähti
//seda näitab meile see, et mudelis on image_index lõpuni kerinud
//ehk siis mäng lõppes "kaotusega"
                if (model.get_image_index() == model.get_piltide_arv() - 1) {
                    model.set_game_running(false);
                    model.reset();
                    reset();

                    //System.out.println("kaotus");
                    return;
                }
            }//if (model.get_vale_taht())
        }

        //kui saadetud täht osutus viimaseks õigeks täheks,
        // siis sai mäng läbi ja lõppes "võiduga"
        if (!model.get_game_running()) {
            //kontrollime, kas tulemus mahub edetabelisse
            if (model.check_benchmark(game_seconds)) {
                model.lisa_uus_rekord(game_seconds, vjuu.ask_username());

                //System.out.println("võit");
                //võtame andmebaasist äsja muudetud edetabeli
                model.taida_edetabel();
                //uuendame senise halvima tulemuse andmed
                model.reset_benchmark();
            }
            model.reset();
            reset();
        }//võit ends
    }//saada_sisend_mudelisse_tootlemiseks() ENDS

    /**
     * Converts full seconds to minutes and seconds. 100 sec => 01:30 (1 min 30 sec)
     *
     * @param seconds full seconds
     * @return 00:00 in format
     */
    private String convertSecToMMSS(int seconds) {
        int min = (seconds / 60);
        int sec = seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }

    public void windowOpened(WindowEvent windowEvent) {
    }

    /**
     * Liidese WindowListener realiseerimine.
     * WindowListener teatab meile sellest kui  edetabeli aken nurgast kinni pannakse.
     * Kui see juhtub, siis võtame uuesti kasutusele edetabeli vaatamise ajaks pausile pandud mängu aja,
     * käivitame mudelis uuesti mängu ja aktiveerime GUI elemendid
     */
    public void windowClosing(WindowEvent windowEvent) {
        if (game_seconds_salvestatud > 0) {
            game_seconds = game_seconds_salvestatud;
            model.set_game_running(true);
        }
        vjuu.activate();
    }

    public void windowClosed(WindowEvent windowEvent) {
    }

    public void windowIconified(WindowEvent windowEvent) {
    }

    public void windowDeiconified(WindowEvent windowEvent) {
    }

    public void windowActivated(WindowEvent windowEvent) {
    }

    public void windowDeactivated(WindowEvent windowEvent) {
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (vjuu.get_sisestuskast().getText().trim().length() > 0 && keyEvent.getKeyCode() == KeyEvent.VK_ENTER && model.get_game_running()) {
            saada_sisend_mudelisse_tootlemiseks();
        }

    }

    /**
     * Liidese KeyListener realiseerimine.
     * Keylisteneri kaudu reageerime sisestuskasti tähtede sisestamisele.
     * Tekitame olukorra, kus korraga üle ühe tähe sisestada ei saa.
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {

        //System.out.println(vjuu.get_sisestuskast().getText().length());
        if (vjuu.get_sisestuskast().getText().trim().length() > 1) {
            String ajutine = vjuu.get_sisestuskast().getText().trim();
            vjuu.get_sisestuskast().setText(ajutine.substring(0, 1));
        }
        keyEvent.consume();
    }


}//klass Controller lõpp
