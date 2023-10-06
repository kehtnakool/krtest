package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;


public class View {
    //GUI elementide deklaratsioonide, kõikvõimalike taustavärvide ja suuruste muutmine toimub siin
    // --------------------- programmi aken
    JFrame programmiAken = new JFrame();
    String programmiAknaTiitliTekst = "Hangman summer 2023";
    int programmiAknaLaius = 800;
    int programmiAknaKorgus = 256;
    Color programmiAknaTaustavarv = new Color(0xAABBCC);
    //-------------------------------------------------------------
    JPanel yleminePaneel = new JPanel(new GridBagLayout());
    int ypRaamiLaius = 10;
    Color ypTaustavarv = new Color(0xFFFFFF);
    //--------------------------------------------------------------
    JPanel aluminePaneel = new JPanel(new GridBagLayout());
    //--------------------------------------------------------------
    Font fontArvatavSona = new Font("Verdana", Font.BOLD, 24);
    //--------------------------------------------------------------
    JLabel siltKuupaev = new JLabel();
    JLabel siltKategooria = new JLabel();
    JLabel siltSisestaTaht = new JLabel();
    //JLabel silt_valed_tahed = new JLabel();
    JLabel siltValedeTahtedeArv = new JLabel();
    JLabel siltPilt = new JLabel();
    JLabel siltArvatavSona = new JLabel();
    //pildifail peab olema projekti kaustas
    //pildi puudumise korral asi ilma hoiatuse või veateateta käitub ettearvamatult
    ImageIcon pilt = new ImageIcon();
    //--------------------------------------------------------------
    JButton nuppCancel = new JButton();
    JButton nuppLeader = new JButton();
    JButton nuppNew = new JButton();
    JButton nuppSend = new JButton();
    //--------------------------------------------------------------
    JComboBox<String> kategooriabox = new JComboBox<>();
    //--------------------------------------------------------------
    JTextField sisestuskast = new JTextField("", 14);
    //paigutame gui elemendid massiividesse, et neid saaks kollektiivselt sisse ja välja lülitada
    JComponent[] maha = {nuppCancel, nuppLeader, nuppNew, nuppSend, kategooriabox, sisestuskast};
    JComponent[] peale = {nuppCancel, nuppLeader, nuppNew, nuppSend, kategooriabox, sisestuskast};
    //---------------- edetabel
    JFrame edetabel = null;
    String edetabeli_tiitli_tekst = "Sõnade äraarvamise mängu edetabel";
    int edetabeliLaius = 600;
    int edetabeliKorgus = 360;
    Color edetabeliTaustavarv = new Color(0xFFFFFF);
    //üldine utiliitmuutuja, mida kasutatakse elementide paneelile paigutamiseks griidbäägleiaudi korral
    GridBagConstraints gbc = new GridBagConstraints();

    /**
     * // GUI KLASSI KONSTRUKTOR
     */
    public View() {

        //---------------- teeme programmi akna -----------------------
        programmiAken.setDefaultCloseOperation(3);//exit_on_close
        programmiAken.setTitle(programmiAknaTiitliTekst);
        programmiAken.setSize(programmiAknaLaius, programmiAknaKorgus);
        programmiAken.getContentPane().setBackground(programmiAknaTaustavarv);
        programmiAken.setResizable(false);
        //programmi_aken.setVisible(true);
        programmiAken.setLayout(new BorderLayout());
        //programmi_aken.setMinimumSize(new Dimension(programmi_akna_laius,programmi_akna_korgus));

        //----------------- programmi aken valmis ------------------
        //----------------- teeme ülemise paneeli ------------------

        yleminePaneel.setBackground(ypTaustavarv);
        yleminePaneel.setBorder(BorderFactory.createMatteBorder(ypRaamiLaius, ypRaamiLaius, 0, ypRaamiLaius, programmiAknaTaustavarv));

        programmiAken.add(yleminePaneel, BorderLayout.NORTH);

        aluminePaneel.setBackground(programmiAknaTaustavarv);
        aluminePaneel.add(siltArvatavSona);

        programmiAken.add(aluminePaneel, BorderLayout.CENTER);
        //----------------- paneme ülemisele paneelile sildid
        siltKuupaev.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));

        siltKategooria.setText("Category");
        siltSisestaTaht.setText("Input character");
        //silt_valed_tahed.setText("valed tähed");
        siltValedeTahtedeArv.setText("Wrong 0 letters");
        siltArvatavSona.setText("L E T ' S P L A Y");

        siltArvatavSona.setFont(fontArvatavSona); // Set label font and style

        siltPilt.setIcon(pilt);

        nuppCancel.setText("Cancel");
        //sellise meetodiga väldime fookuse kadumist sisestuskastilt
        nuppSend.setFocusable(false);
        nuppLeader.setText("View scoreboard");
        nuppSend.setFocusable(false);
        nuppNew.setText("New game");
        nuppSend.setFocusable(false);
        nuppSend.setText("Send");
        nuppSend.setFocusable(false);
        kategooriabox.setFocusable(false);
        //ekraani loomise hetkel ei ole send nupp aktiivne
        //send nupu aktiviseerib uue mängu alustamine
        nuppSend.setEnabled(false);
        nuppCancel.setEnabled(false);
        sisestuskast.setEnabled(false);
        //--------- alustame elementide paneelile ladumist ülemisele paneelile

        // ESIMENE TULP

        //----------------------------------------------- 1
        gbc.gridy = 1; // rida
        gbc.gridx = 0; // Veerg
        //gbc.gridwidth = 3; // A label over three columns
        yleminePaneel.add(siltKategooria, gbc);
        //----------------------------------------------- 1
        gbc.gridy = 2; // rida
        gbc.gridx = 0; // Veerg
        //gbc.gridwidth = 3; // A label over three columns
        yleminePaneel.add(siltSisestaTaht, gbc);
        //----------------------------------------------- 1
        /*
        gbc.gridy = 3; // rida
        gbc.gridx = 0; // Veerg
        //gbc.gridwidth = 3; // A label over three columns
        ylemine_paneel.add(silt_valed_tahed, gbc);
         */
        //----------------------------------------------- 1
        gbc.gridy = 4; // rida
        gbc.gridx = 0; // Veerg
        //gbc.gridwidth = 3; // A label over three columns
        yleminePaneel.add(nuppCancel, gbc);

        //              teine tulp

        //----------------------------------------------- 1
        gbc.gridy = 0; // rida
        gbc.gridx = 1; // Veerg
        //gbc.gridwidth = 3; // A label over three columns
        yleminePaneel.add(siltKuupaev, gbc);

        //----------------------------------------------- 1
        gbc.gridy = 1; // rida
        gbc.gridx = 1; // Veerg
        //gbc.gridwidth = 3; // A label over three columns
        yleminePaneel.add(kategooriabox, gbc);

        //----------------------------------------------- 1
        gbc.gridy = 2; // rida
        gbc.gridx = 1; // Veerg
        //gbc.gridwidth = 3; // A label over three columns
        yleminePaneel.add(sisestuskast, gbc);

        //----------------------------------------------- 1
        gbc.gridy = 3; // rida
        gbc.gridx = 1; // Veerg
        //gbc.gridwidth = 3; // A label over three columns
        //silt_valede_tahtede_arv.setBackground(yp_taustavarv);
        yleminePaneel.add(siltValedeTahtedeArv, gbc);

        //----------------------------------------------- 1
        gbc.gridy = 4; // rida
        gbc.gridx = 1; // Veerg
        //gbc.gridwidth = 3; // A label over three columns
        //silt_valede_tahtede_arv.setBackground(yp_taustavarv);
        yleminePaneel.add(nuppLeader, gbc);

        //           kolmas tulp

        //----------------------------------------------- 1
        gbc.gridy = 1; // rida
        gbc.gridx = 2; // Veerg
        //gbc.gridwidth = 3; // A label over three columns
        yleminePaneel.add(nuppNew, gbc);
        //----------------------------------------------- 1
        gbc.gridy = 2; // rida
        gbc.gridx = 2; // Veerg
        //gbc.gridwidth = 3; // A label over three columns
        yleminePaneel.add(nuppSend, gbc);
        //----------------------------------------------- 1

//neljas tulp
        //----------------------------------------------- 1
        gbc.gridy = 1; // rida
        gbc.gridx = 3; // Veerg
        gbc.gridheight = 4; // A label over three columns
        yleminePaneel.add(siltPilt, gbc);
        //----------------------------------------------- 1
    }//GUI konstruktori LÕPP

    //--------------- getterid gui elementidele ----------
    public JButton get_nupp_cancel() {
        return nuppCancel;
    }

    public JButton get_nupp_leader() {
        return nuppLeader;
    }

    public JButton get_nupp_new() {
        return nuppNew;
    }

    public JButton get_nupp_send() {
        return nuppSend;
    }

    //soovime muuta programmi akna nähtavaks kontrollerist pärast eventlisteneride määramist
    public JFrame get_programmi_aken() {
        return programmiAken;
    }

    public JLabel get_kuupaev() {
        return siltKuupaev;
    }

    public JLabel get_pilt() {
        return siltPilt;
    }

    public JLabel get_silt_arvatav_sona() {
        return siltArvatavSona;
    }
    //public JLabel get_silt_valed_tahed() {return silt_valed_tahed;}

    public JLabel get_silt_valede_tahtede_arv() {
        return siltValedeTahtedeArv;
    }

    public JFrame get_edetabel() {
        return edetabel;
    }

    //Raw use of parameterized class 'JComboBox'
    public JComboBox get_kategooriabox() {
        return kategooriabox;
    }

    /**
     * @return Väljastame kategooriate komboboxist valitud kategooria stringi kujul
     */
    public String get_kategooria() {
        return (String) kategooriabox.getSelectedItem();
    }

    //public String get_user_input = {return sisestuskast}
    public JTextField get_sisestuskast() {
        return sisestuskast;
    }

    /**
     * Edetabeli väärilise tulemuse korral küsime kasutajalt kasutajanime, kasutades selleks
     * JOptionPane elementi. Kui kasutaja vajutab cancel, siis paneme "noname"
     * Kui sisestatakse üle 20 märgi pikkune, siis trimmime sellest teatamata
     *
     * @return string kasutajanimi
     */

    public String ask_username() {
        String inputValue = JOptionPane.showInputDialog("Sattusid edetabelisse, sisesta palun oma nimi");
        if (inputValue == null) {
            inputValue = "noname";
        }
        if (inputValue.length() > 20) {
            inputValue = inputValue.substring(0, 19);
        }
        return inputValue;
    }

    /**
     * Loome uue JFrame, paneme sellelel JLabel
     * Ja näitame sellel sisendiks saabunud stringi, milleks on edetabeli sisu
     *
     * @param edetabeli_sisu html tabelina vormindatud  string
     */
    public void naita_edetabelit(String edetabeli_sisu) {
        edetabel = new JFrame();
        edetabel.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        edetabel.setTitle(edetabeli_tiitli_tekst);
        edetabel.setSize(edetabeliLaius, edetabeliKorgus);
        edetabel.getContentPane().setBackground(edetabeliTaustavarv);
        edetabel.setResizable(false);
        edetabel.setLayout(null);
        JLabel silt_edetabel = new JLabel();
        silt_edetabel.setText(edetabeli_sisu);
        silt_edetabel.setVerticalAlignment(SwingConstants.TOP);
        silt_edetabel.setBounds(40, 20, edetabeliLaius - 40, edetabeliKorgus - 20);
        edetabel.add(silt_edetabel);
        edetabel.setVisible(true);
    }//naita_edetabelit() ends

    /**
     * muudame massiivi paigutatud GUI elemendid mitteaktiivseks
     */
    public void passivate() {
        for (JComponent gui_element : maha) {
            gui_element.setEnabled(false);
        }
    }

    /**
     * muudame massiivi paigutatud GUI elemendid aktiivseks
     */
    public void activate() {
        for (JComponent gui_element : peale) {
            gui_element.setEnabled(true);
        }
    }

}//class View lõpp
