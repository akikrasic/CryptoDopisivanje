package rs.etf.ka143095.gui;

import rs.etf.ka143095.main.Main;
import rs.etf.ka143095.mreza.IpAdresa;
import rs.etf.ka143095.mreza.Klijent;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by aki on 7/6/16.
 */
public class Forma extends JFrame{

    private Main main;
    private Klijent klijent;


    private String ip;
    private String toolTipPorukaOAdresi = "Двокликом се адреса копира у клипборд.";
    private JTextField ipAdresaLabel = new JTextField();
    private JTextField unosIpKorisnikaTF = new JTextField();
    private JButton poveziteSeSaKorisnikomDugme= new JButton("Повежите се са корисником");
    private JTextArea dopisivanjeTA= new JTextArea();
    private JTextField porukaTF= new JTextField();
    private JButton posaljitePorukuDugme= new JButton("Пошаљите поруку");
    private JTextField portTF;

    private void ipAdresaLabelDodavanje(){
        ipAdresaLabel.setText(this.porukaIpAdresa());
        ipAdresaLabel.setBounds(490,10, 300, 30);
        ipAdresaLabel.setEditable(false);
        ipAdresaLabel.setToolTipText(toolTipPorukaOAdresi);
        ipAdresaLabel.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    Forma.this.kopirajteIpAdresuUKlipbord();
                    ipAdresaLabel.setText(porukaIpAdresa());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        add(ipAdresaLabel);
    }
    private void unosIpKorisnikaTFDodavanje(){
        unosIpKorisnikaTF.setBounds(10,10,200,30);

        add(unosIpKorisnikaTF);

    }
    private void unosPortaTFDodavanje() {

        portTF = new JTextField();
        portTF.setBounds(230,10,100,30);
        add(portTF);

    }
    private void poveziteSeSaKorisnikomDugmeDodavanje(){
        poveziteSeSaKorisnikomDugme.setBounds(230,10,250,30);
        poveziteSeSaKorisnikomDugme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip=unosIpKorisnikaTF.getText().trim();
               // int port = Integer.parseInt(portTF.getText().trim());
                if(daLiJeUnetaIspravnaIpAdresaKlijenta(ip)){
                    Forma.this.poveziteSeSaServerom(ip);
                    unosIpKorisnikaTF.setText("");
                    Forma.this.poveziteSeSaServerom(ip);
                }
                else{
                    JOptionPane.showMessageDialog(Forma.this, "Унесите ИП адресу у исправном формату.");
                    unosIpKorisnikaTF.selectAll();
                    unosIpKorisnikaTF.requestFocus();
                }
            }
        });
        add(poveziteSeSaKorisnikomDugme);

    }
    private void dopisivanjeTADodavanje(){
        //dopisivanjeTA.setBounds(10,50,830,500);
        dopisivanjeTA.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(dopisivanjeTA);
        scroll.setBounds(10,50,830,500);
        add(scroll);
        scroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable a=e.getAdjustable();
                a.setValue(a.getMaximum());
            }
        });
    }
    private void porukaTFDodavanje(){
        porukaTF.setBounds(10,560,620,30);
        add(porukaTF);
        porukaTF.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    posaljitePorukuDugme.doClick();

                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    posaljitePorukuDugme.doClick();

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
    private void posaljitePorukuDugmeDodavanje(){

        this.posaljitePorukuDugme.setBounds(640,560,200,30);
        posaljitePorukuDugme.addActionListener((e)->{
            String poruka =porukaTF.getText().trim();
            if(!poruka.equals("")) {
                String p = new StringBuilder("Ви: ").append(poruka).toString();
                dodajteUTextAreu(p);
                porukaTF.setText("");
                porukaTF.requestFocus();
                posaljitePoruku(poruka);
            }
        });
        add(this.posaljitePorukuDugme);
    }

    private void dodajteListenerZaZatvaranjeProzora(){
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                klijent.zatvoriteKonekciju();
                main.getServer().zatvoriteKonekciju();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    private void dodajteKomponenteNaFormu(){
       this.ipAdresaLabelDodavanje();
        unosIpKorisnikaTFDodavanje();
        //unosPortaTFDodavanje();
        poveziteSeSaKorisnikomDugmeDodavanje();
        dopisivanjeTADodavanje();
        porukaTFDodavanje();
        posaljitePorukuDugmeDodavanje();
        dodajteListenerZaZatvaranjeProzora();

    }


    private void dobaviteIpAdresu(){
        ip= new IpAdresa().dobaviteIpadresu();

    }

    private void kopirajteIpAdresuUKlipbord(){
        dobaviteIpAdresu();
        StringSelection selection = new StringSelection(ip);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);

    }


    private String porukaIpAdresa(){
        this.kopirajteIpAdresuUKlipbord();
        return String.format("Ваша ИП адреса је: %s .", ip);
    }

    public Forma(Main main){
        this.main = main;
        this.klijent=main.getKlijent();
        this.setSize(870,650);
        this.setTitle("CryptoDopisivanje Krasic Aleksandar 3095/2014");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setResizable(false);
        this.getContentPane().setLayout(null);
        this.dodajteKomponenteNaFormu();
        repaint();
    }

    public void dodajteUTextAreu(String poruka){

        dopisivanjeTA.append(new StringBuilder(poruka).append("\n\n").toString());

    }


    public boolean daLiJeUnetaIspravnaIpAdresaKlijenta(String zaProveru){
        //preuzeto sa: http://www.mkyong.com/regular-expressions/how-to-validate-ip-address-with-regular-expression/
        String IPADDRESS_PATTERN =
                "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        //kraj preuzetog koda
        Matcher m = pattern.matcher(zaProveru);
        return m.matches();
    }
    public void posaljitePoruku(String poruka){
        klijent.posaljitePoruku(poruka);
    }
    public void poveziteSeSaServerom(String ip){
        klijent.uspostaviteVezu(ip,IpAdresa.PORT);
        klijent.posaljiteZahtevZaUspostavomVeze();
    }
    public void uspesnoSteSePovezaliNaServer(){
        JOptionPane.showMessageDialog(this, "Успешно сте се повезали са Вашим саговорником.");

    }
    public void sagovornikSeUspesnoPovezaoSaVama(){
        JOptionPane.showMessageDialog(this, "Саговорник је успешно повезан са Вама.");

    }
    public void krajKomunikacije(){
        JOptionPane.showMessageDialog(this, "Ваш саговорник је завршио комуникацију са Вама..");

    }

    public void dodajtePorukuSagovornika(String poruka){
        this.dodajteUTextAreu(String.format("Саговорник: %s",poruka));
    }

    public JTextField getUnosIpKorisnikaTF() {
        return unosIpKorisnikaTF;
    }

    public void setUnosIpKorisnikaTF(JTextField unosIpKorisnikaTF) {
        this.unosIpKorisnikaTF = unosIpKorisnikaTF;
    }

    public JButton getPoveziteSeSaKorisnikomDugme() {
        return poveziteSeSaKorisnikomDugme;
    }

    public void setPoveziteSeSaKorisnikomDugme(JButton poveziteSeSaKorisnikomDugme) {
        this.poveziteSeSaKorisnikomDugme = poveziteSeSaKorisnikomDugme;
    }

    public JTextArea getDopisivanjeTA() {
        return dopisivanjeTA;
    }

    public void setDopisivanjeTA(JTextArea dopisivanjeTA) {
        this.dopisivanjeTA = dopisivanjeTA;
    }

    public JButton getPosaljitePorukuDugme() {
        return posaljitePorukuDugme;
    }

    public void setPosaljitePorukuDugme(JButton posaljitePorukuDugme) {
        this.posaljitePorukuDugme = posaljitePorukuDugme;
    }

    public JTextField getPorukaTF() {
        return porukaTF;
    }

    public void setPorukaTF(JTextField porukaTF) {
        this.porukaTF = porukaTF;
    }

    public JTextField getIpAdresaLabel() {
        return ipAdresaLabel;
    }

    public void setIpAdresaLabel(JTextField ipAdresaLabel) {
        this.ipAdresaLabel = ipAdresaLabel;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Klijent getKlijent() {
        return klijent;
    }

    public void setKlijent(Klijent klijent) {
        this.klijent = klijent;
    }

    public String getToolTipPorukaOAdresi() {
        return toolTipPorukaOAdresi;
    }

    public void setToolTipPorukaOAdresi(String toolTipPorukaOAdresi) {
        this.toolTipPorukaOAdresi = toolTipPorukaOAdresi;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public JTextField getPortTF() {
        return portTF;
    }

    public void setPortTF(JTextField portTF) {
        this.portTF = portTF;
    }
}
