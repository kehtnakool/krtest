package model;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.List;


public class Model {
    boolean vale_taht = true;
    //kui edetabeli_ridade_arv < 10, siis mahuvad kõik tulemused edetabelisse
    int edetabeli_ridade_arv = 0;
    //kui kõik tulemusedd edetabelisse ei mahu, siis tuleb võrrelda senise halvima tulemusega
    int senine_halvim_tulemus = 0;
    //kasutame võimatut vaikeväärtust, tegelikult senise_halvima_tulemuse_id saab olla kuni edetabeli ridade arv
    int senise_halvima_tulemuse_id = -1;
    String andmebaasi_nimi = "words.db";
    String vaikimisi_kategooria = "All categories";
    HashMap<String, List<String>> sonastik = new HashMap<>();
    String edetabel = "";
    Boolean game_running = false;
    //mängu alustades saab arvatav_sona väärtuseks sõna, mida peab ära aravma
    String arvatav_sona = "LET'SPLAY";
    //mängu alustades saab arvatav_sona_massiiv_1 väärtuseks listi, mille pikkus vastab arvatavale sõnale
    // ja mille iga element on alakriips
    ArrayList<String> arvatav_sona_massiiv_1 = new ArrayList<>();
    ArrayList<String> valed_tahed_list = new ArrayList<>();
    String valed_tahed = "";
    //Boolean vale_taht = true;
    int valede_tahtede_arv = 0;
    //String arvatav_sona_sildil = "";
    String imagesFolder = "images";
    int piltide_arv = 12;
    ImageIcon[] imageFiles = new ImageIcon[piltide_arv];
    int image_index = 0;

    /**
     * Mudeli konstruktor. Sellel on 3 ülesannet:
     * 1. Võtame andmebaaist kõik sõnad ja paneme nad vastavalt kategooriatele sõtnastikku (hashmap, dictionary)
     * Võti on kategooria ja väärtus on selle kategooria alla kuuluvate sõnade list (ArrayList)
     * 2. Võtame andebaasist edetabeli sisu ja loome selle html vormingus tabeli (lihtsalt ühe pika stringi),
     * mida saab näidata GUI peal edetabeli aknas.
     * 3. Täidame piltide kaustast võetud piltide alusel loodud ImageIcon objektidega massiivi.
     * Tuleks välistada muud kui png laiendid, samuti ei kontrolli, me kas piltide arv õn õige,
     * või kas piltide hulka on kopeeritud mõni tekstifail, jne. Seda kõike võiks teha.
     */
    public Model() {
        sonad_sonastikku();
        taida_edetabel();
        fillImagesList();
    }//konstruktorfunktsiooni LÕPP

    /**
     * Meil on äraarvatava sõna kohta list, mis sisaldab alakriipse.
     * Õige tähe korral asendame vastavad alakriipsud kasutaja sisendiga.
     * Kui sisestati vale täht, siis kontrollime, kas kas see on seniste valede tähtede hulgas.
     * Kui pole siis paneme tähe valedesse tähtedesse.
     * Kui mäng sai läbi, sest sisend osutus viimaseks võimalikuks, siis lõpetame mängu,
     * kas "kaotuse" või "võiduga".
     *
     * @param sisend See on ühe tähe pikkune string, mille kasutaja sisestab GUI pealt.
     */

    public void tootle_kasutaja_sisend(String sisend) {
        vale_taht = true;
        //käime kõik arvatava sõna tähed läbi ja võrdleme neid sisendiga
        for (int i = 0; i < arvatav_sona.length(); i++) {
            //sisestati õige täht
            if (sisend.compareTo(String.valueOf(arvatav_sona.charAt(i))) == 0) {

                vale_taht = false;
                arvatav_sona_massiiv_1.remove(i);
                arvatav_sona_massiiv_1.add(i, String.valueOf(arvatav_sona.charAt(i)));
            }//end if ledsime õige tähe
        }//end for käime kõik arvatava sõna tähed läbi
        //kui sisestati vale täht, siis kontrollime, kas kas see on seniste valede tähtede hulgas
        if (vale_taht) { //sisestati vale täht
            if (!valed_tahed.contains(sisend)) {
                valede_tahtede_arv++;
                valed_tahed += sisend;
                valed_tahed_list.add(sisend);
            }
        }//end if vale täht

        //mäng sai läbi sellepärast, et arvatava sõna massiivis pole rohkem alakriipse
//ehk siis mäng lõppes "võiduga"
        if (!arvatav_sona_massiiv_1.contains("_")) {
            game_running = false;
        }
    }//funktsioon tootle_kasutaja_sisend() LÕPP

    /**
     * Lähtestame valed tähed ja valede tähtede massiivi.
     * Samuti lähtestame listi, milles paikneb äraarvatav sõna.
     * Miks meil on valed tähed nii stringi kui ka listi kujul?
     * Töö käigus mõtted muutusid. Võimalik, et stringist saaks kergesti loobuda.
     */
    public void reset() {
        valed_tahed = "";
        valed_tahed_list.clear();
        arvatav_sona_massiiv_1.clear();
        valede_tahtede_arv = 0;
    }

    public boolean get_vale_taht() {
        return vale_taht;
    }

    /*
    public String get_arvatav_sona() {
        return arvatav_sona;
    }
*/

    /**
     * Väljastame GUI peal näitamiseks seni sisestatud valed tähed
     *
     * @return string, milles paiknevad seni sisestatud valed tähed, komade ja tühikutega eraldatult
     */
    public String get_valed_tahed() {
        return String.join(", ", valed_tahed_list).toUpperCase();
    }

    //public String get_valed_tahed(){return valed_tahed;}

    public int get_valede_tahtede_arv() {
        return valede_tahtede_arv;
    }

    public ImageIcon[] get_imageFiles() {
        return imageFiles;
    }

    /**
     * Pildid paiknevad massiivis. Võimaldame selle massiivi pointerit väljastpoolt klassi muuta.
     *
     * @param sisend täisarv, mida kasutame piltide massiivi pointerina
     */
    public void set_image_index(int sisend) {
        image_index = sisend;
    }

    public void set_game_running(boolean sisend) {
        game_running = sisend;
    }

    public int get_image_index() {
        return image_index;
    }

    public int get_piltide_arv() {
        return piltide_arv;
    }

    /**
     * Äraarvatav sõna GUI peal näitamiseks on massiivis, kus on alakriipsud ja tähed.
     * Loome sellest stringi ja paneme vahele tühikud ja väljastame selle.
     *
     * @return string, milles on senise äraarvamise tulemus, st alakriipsud ja juba õra arvatud tähed,
     * eraldatud tühikutega
     */
    public String get_arvatav_sona_sildil() {
        String sildi_tekst = "";
        for (int i = 0; i < arvatav_sona_massiiv_1.size(); i++) {
            sildi_tekst += arvatav_sona_massiiv_1.get(i);
        }
        return sildi_tekst.replaceAll("", " ").trim();
    }

    /**
     * Sõna, mida ära arvatakse peab algselt olema asendaud alakriipsudega.
     * Selleks on meil List, mis kannab eksitavat nime massiiv.
     * Lähtestame selle listi.
     */
    public void reset_arvatav_sona_sildil() {
        arvatav_sona_massiiv_1.clear();
        for (int i = 0; i < arvatav_sona.length(); i++) {
            arvatav_sona_massiiv_1.add(i, String.valueOf(arvatav_sona.charAt(i)));
        }
    }

    /**
     * Kontroller küsib mudeli dictionaryst sõna ja seejärel saadab selle mudelile tagasi.
     * Mudel paneb selle sisendi muutujasse arvatav_sona.
     * Võiks ka otse. Selline meetod muudab dictionaryst sõna küsimise funktsiooni sõltumatumaks.
     *
     * @param sisend on string, millest saab äraarvatav sõna.
     */
    public void set_arvatav_sona(String sisend) {
        arvatav_sona = sisend;
        arvatav_sona_massiiv_1.clear();
        for (int i = 0; i < arvatav_sona.length(); i++) {
            arvatav_sona_massiiv_1.add("_");
        }
    }//set_arvatav_sona() ends

    public Boolean get_game_running() {
        return game_running;
    }

    /*
    public void set_game_running(Boolean sisend) {

        game_running = sisend;
    }
*/

    /**
     * Sõnad asuvad sõnastikus key, mis on string ja kategooria nimi, value, mis on sõnade/stringide list.
     * GUI peal peame komboboxis näitama kategooriaid.
     * Selleks väljastame sõnastikust kõik võtmed listi.
     *
     * @return List, mis sisaldab kõiki sõnastiku võtmeid, st kategooriad tähistavaid stringe
     */

    public ArrayList<String> get_kategooriad() {
        ArrayList<String> kategooriate_list = new ArrayList<>();
        kategooriate_list.add(vaikimisi_kategooria);
        for (String key : sonastik.keySet()) {
            if (!key.equals(vaikimisi_kategooria)) {
                kategooriate_list.add(key);
            }
        }
        return kategooriate_list;
    } //get_kategooriad() ends

    /**
     * Sisendiks saabub kategooria. Kasutame seda võtmena ja väljastame vastava võtme alt sõnastikust juhusliku sõna.
     * Juhul kui sisendiks saadeti jama, st vastav kategooria sõnastikus puudub, siis väljastame tühja stringi
     *
     * @param kategooria string, milleks on kategooria
     * @return juhuslik sõna
     */

    public String get_random_word(String kategooria) {
        //in-place shuffle
        //Collections.shuffle(sonastik.get(kategooria));
        //return sonastik.get(kategooria).get(0);
        try {
            //System.out.println(sonastik.get(kategooria).size());
            return sonastik.get(kategooria).get(new Random().nextInt(sonastik.get(kategooria).size()));

        } catch (Exception e) {
            return "";
        }
    } //get_random_word() ends

    //(LocalDateTime gameTime, String playerName, String word, String missedLetters,
    //                         int timeSeconds)
    //LocalDateTime playerTime = LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    /**
     * @return Edetabel on html vormingus string, väljastame selle.
     */
    public String get_edetabel() {
        return edetabel;
    }
    //public String get_edetabel(){return edetabel;}

    /**
     * @return piltide massiivist parajasti kehtiva pointeri alusel pilt
     */
    public ImageIcon get_image() {
        return imageFiles[image_index];
    }

    /**
     * Suurendame piltide massiivi pointerit ühe võrra.
     * Lõpuni kerimise korral nullistame
     */
    public void increment_image_index() {
        image_index++;
        if (image_index + 1 > piltide_arv) {
            image_index = 0;
        }
    }

    /**
     * teeme pildifailidest pilte sialdava listi
     * seejuures kontrollime, et pilte on õige arv ja seda, et me ei võta kaasa
     * juhuslikult piltide kaustas olevaid txt, java ja muid faile, vaid ainult png failid
     * Pildid sorteerime vastavalt nimele
     */

    public void fillImagesList() {
        File folder = new File(imagesFolder);
        File[] files = folder.listFiles();
        Arrays.sort(files, (java.io.File a, java.io.File b) -> {
            return a.compareTo(b);
        });
        //Can be replaced with 'Comparator.naturalOrder'
        int counter = 0;
        for (File file : files) {
            //System.out.println(file.getClass());
            //new ImageIcon(file.getAbsolutePath());
            imageFiles[counter] = new ImageIcon(file.getAbsolutePath());
            counter++;

        }
    }

    /**
     * Võtame andmebaasist kõik sõnad sõnastikku (hashmap, dictionary)
     * Võti on string, milleks on kategooria ja väärtus on selle kategooria sõnade/stringide list
     */

    public void sonad_sonastikku() {
        try (Connection andmebaasi_yhendus = DriverManager.getConnection("jdbc:sqlite:" + andmebaasi_nimi);
             Statement sql_1 = andmebaasi_yhendus.createStatement();
             Statement sql_2 = andmebaasi_yhendus.createStatement()) {
            sql_1.setQueryTimeout(6);
            sql_1.executeUpdate("PRAGMA foreign_keys='1'");
            sql_2.setQueryTimeout(6);
            sql_2.executeUpdate("PRAGMA foreign_keys='1'");

            sonastik.put(vaikimisi_kategooria, new ArrayList<String>());
            //Explicit type argument String can be replaced with <>
            ResultSet sonad = sql_2.executeQuery("select sona from sonad");

            while (sonad.next()) {
                sonastik.get(vaikimisi_kategooria).add(sonad.getString(1));
            }

            ResultSet kategooriad = sql_1.executeQuery("select * from kategooriad");

            while (kategooriad.next()) {
                sonad = sql_2.executeQuery("select sona from sonad where kat_id='" + kategooriad.getString(1) + "'");
                sonastik.put(kategooriad.getString(2), new ArrayList<String>());
                while (sonad.next()) {
                    sonastik.get(kategooriad.getString(2)).add(sonad.getString(1));
                }
            }// end while kategooriad.next()

        }//end try
        catch (SQLException e) {
            System.out.println("viga: " + e.getMessage());
            System.out.println("SQLi viga peaks tegelikult mängu kohe lõpetama");
        }
    }//sonad_sonastikku() lõpp

    //--------------------------------------------------------------------------

    /*Kontrollime, kas lõppenud mängu tulemus mahub edetabelisse.
     * Tulemus mahub edetabelisse siis, kui edetabelis on vähem kui 10 rida või siis kui mängu tulemus on parem
     * kui senine halvim tulemus
     * */
    public boolean check_benchmark(int sisend) {
        //'if' statement can be simplified
        if (sisend < senine_halvim_tulemus || edetabeli_ridade_arv < 10) {
            return true;
        }
        //if (sisend<Integer.parseInt(senine_halvim_tulemus)||edetabeli_ridade_arv<10){return true;}
        else {
            return false;
        }
    }


    /*
     * Salvestame olukorra, mis ütleb meile, kas lõppenud mängu tulemus mahub edetabelisse ("benchmark").
     * St, me salvestame edetabeli ridade arvu ja senise halvima tulemuse
     * samuti salvestame senise halvima tulemuse id, sest uue rekordi salvestamise korral me selle rea asendame
     * */
    public void reset_benchmark() {
        //String ajutine_tulemus="";
        try (Connection andmebaasi_yhendus = DriverManager.getConnection("jdbc:sqlite:" + andmebaasi_nimi);
             Statement sql_1 = andmebaasi_yhendus.createStatement()) {
            sql_1.setQueryTimeout(6);
            sql_1.executeUpdate("PRAGMA foreign_keys='1'");
            //ResultSet paringu_vastus = sql_1.executeQuery("select count(timeSeconds) from edetabel;");
            ResultSet paringu_vastus = sql_1.executeQuery("select * from edetabel;");
            edetabeli_ridade_arv = 0;
            while (paringu_vastus.next()) {
                edetabeli_ridade_arv++;
            }//end while
            //System.out.println(edetabeli_ridade_arv);
            paringu_vastus = sql_1.executeQuery("select id, timeSeconds from edetabel order by timeSeconds, gameTime;");
            while (paringu_vastus.next()) {
                senise_halvima_tulemuse_id = Integer.parseInt(paringu_vastus.getString(1));
                senine_halvim_tulemus = Integer.parseInt(paringu_vastus.getString(2));
            }//end while


        }//end try
        catch (SQLException e) {
            System.out.println("viga: " + e.getMessage());
            System.out.println("SQLi viga peaks tegelikult mängu kohe lõpetama");
        }
    }//funktsiooni reset_benchmark() lõpp

    /**
     * Esitame andmebaasile päringu edetabeli kohta.
     * Tulemuse alusel teeme html vormingus stringi GUI peal näitamiseks
     */
    public void taida_edetabel() {
        try (Connection andmebaasi_yhendus = DriverManager.getConnection("jdbc:sqlite:" + andmebaasi_nimi);
             Statement sql_1 = andmebaasi_yhendus.createStatement()) {
            sql_1.setQueryTimeout(6);
            sql_1.executeUpdate("PRAGMA foreign_keys='1'");
            ResultSet edetabeli_sisu = sql_1.executeQuery("select strftime('%d.%m.%Y %H:%M:%S',gameTime,'unixepoch'),playerName,missedLetters,timeSeconds from edetabel order by timeSeconds, gametime;");
            //"<html><table>"+"" ei võrdu "<html><table>" ??
            edetabel = "<html><table><tr><td>kuupäev</td>" +
                    "<td>kasutajanimi</td>" +
                    "<td>valed tähed</td>" +
                    "<td>aeg min:sek</td><tr>";
            while (edetabeli_sisu.next()) {
                edetabel += "<tr>";
                edetabel += "<td>" + edetabeli_sisu.getString(1) + "</td>";
                edetabel += "<td>" + edetabeli_sisu.getString(2) + "</td>";
                edetabel += "<td>" + edetabeli_sisu.getString(3).toUpperCase() + "</td>";
                edetabel += "<td>" + convertSecToMMSS(Integer.parseInt(edetabeli_sisu.getString(4))) + "</td>";
                edetabel += "</tr>";
            }//end while


        }//end try
        catch (SQLException e) {
            System.out.println("viga: " + e.getMessage());
            System.out.println("SQLi viga peaks tegelikult mängu kohe lõpetama");
        }
        //me ei saanud andmebaasist ühtegi rida, edetabel on tühi
        if (edetabel.equals("<html><table>")) {
            edetabel = "";
            return;
        }
        edetabel += "</table></html>";

    }//funktsiooni taida_edetabel() lõpp

    //  public void lisa_uus_rekord(int sisend_gametame, String sisend_kasutajanimi){}


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

    /**
     * Lisame andmebaasi uue rekordi.
     *
     * @param sisend_gametame
     * @param sisend_kasutajanimi
     */
    public void lisa_uus_rekord(int sisend_gametame, String sisend_kasutajanimi) {
        String paring;
        if (edetabeli_ridade_arv < 10) {
            if (valed_tahed.isEmpty()) {
                paring = "INSERT INTO edetabel (gameTime,playerName,timeSeconds) VALUES (strftime('%s'),'" + sisend_kasutajanimi + "'," + String.valueOf(sisend_gametame) + ")";
            } else {
                paring = "INSERT INTO edetabel (gameTime,playerName,missedLetters,timeSeconds) VALUES (strftime('%s'),'" + sisend_kasutajanimi + "','" + valed_tahed + "'," + String.valueOf(sisend_gametame) + ")";
            }
        }//end edetabeli ridade arv < 10
        else {

            if (valed_tahed.isEmpty()) {
                paring = "UPDATE edetabel SET gameTime=strftime('%s'),playerName='" + sisend_kasutajanimi + "',timeSeconds=" + String.valueOf(sisend_gametame) + " WHERE id=" + senise_halvima_tulemuse_id;
            } else {
                paring = "UPDATE edetabel SET gameTime=strftime('%s'),playerName='" + sisend_kasutajanimi + "',missedLetters='" + valed_tahed + "',timeSeconds=" + String.valueOf(sisend_gametame) + " WHERE id=" + senise_halvima_tulemuse_id;
            }
        }
        try (Connection andmebaasi_yhendus = DriverManager.getConnection("jdbc:sqlite:" + andmebaasi_nimi);
             Statement sql_1 = andmebaasi_yhendus.createStatement()) {
            sql_1.setQueryTimeout(6);
            sql_1.executeUpdate("PRAGMA foreign_keys='1'");
            sql_1.executeUpdate(paring);
        }//end try
        catch (SQLException e) {
            System.out.println("viga: " + e.getMessage());
            System.out.println("SQLi viga peaks tegelikult mängu kohe lõpetama");
        }
    }//lisa_uus_rekord() lõpp


}//klassi Model_1 lõpp
