package rs.etf.ka143095.crypto;

import rs.etf.ka143095.main.Main;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Created by aki on 7/7/16.
 */
public class Kripto {
    private Cipher rsaCipher;
    private Cipher aesCipher;
    private Main main;
    private KeyPair parKljuceva;
    private PublicKey javniKljucSagovornika;
    private SecretKey simetricniKljuc;
    public Kripto(Main main){
        try {
            rsaCipher = Cipher.getInstance("RSA");
            aesCipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        this.main=main;
        generisiteSimetricniKljuc();
        generisitePrivatniIJavniKljuc();

    }
    public void generisitePrivatniIJavniKljuc() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            parKljuceva = generator.generateKeyPair();
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }
    public void generisiteSimetricniKljuc(){
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(128);
            simetricniKljuc = generator.generateKey();
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    public String enkriptujteJavnimKljucemSagovornika(String plainText){
        String cipherText = null;
        try {
            rsaCipher.init(Cipher.ENCRYPT_MODE, this.javniKljucSagovornika);

            cipherText=new String(enkodujte(rsaCipher.doFinal(plainText.getBytes())));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return cipherText;
    }

    public String dekriptujteSvojimPrivatnimKljucem(String cipherText){
        String plainText = null;
        byte[] dekodovaniCipherText = dekodujte(cipherText);
        try {
            rsaCipher.init(Cipher.DECRYPT_MODE, this.getPrivatniKljuc());
            plainText=new String(rsaCipher.doFinal(dekodovaniCipherText));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return plainText;
    }

    public String enkriptujteSimetricnimKljucem(String plainText){
        String cipherText=null;
        try {
            this.aesCipher.init(Cipher.ENCRYPT_MODE, simetricniKljuc);
            cipherText=enkodujte(aesCipher.doFinal(plainText.getBytes()));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return cipherText;
    }

    public String dekriptujteSimetricnimKljucem(String cipherText){
        String plainText = null;
        try {
            this.aesCipher.init(Cipher.DECRYPT_MODE, simetricniKljuc);
            plainText=new String(aesCipher.doFinal(dekodujte(cipherText)));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return plainText;
    }

    public String vratiteSimetricniKljucZaSlanje(){
        return enkodujte(simetricniKljuc.getEncoded());
    }
    public void postaviteSimetricniKljuc(String kljucZaUbacivanje){
        byte[] nizBajtovaKljuca = dekodujte(kljucZaUbacivanje);
        simetricniKljuc = new SecretKeySpec(nizBajtovaKljuca, 0, nizBajtovaKljuca.length, "AES");
    }
    public String getJavniKljucEnkodovani(){
         return enkodujte(parKljuceva.getPublic().getEncoded());
    }

    public void dekriptujteIPostaviteSimetricniKljuc(String kljucZaUbacivanje){
        String rezultat = this.dekriptujteSvojimPrivatnimKljucem(kljucZaUbacivanje);
        this.postaviteSimetricniKljuc(rezultat);
    }

    public String enkriptujteSimetricniKljucJavnimKljucemSagovornika(){
        String simetricniKljucEnkodovani = enkodujte(simetricniKljuc.getEncoded());
        return this.enkriptujteJavnimKljucemSagovornika(simetricniKljucEnkodovani);
    }

    public String enkodujte(byte[] nizBajtova){
        return Base64.getEncoder().encodeToString(nizBajtova);
    }
    public byte[] dekodujte(String zaDekodiranje){
        return Base64.getDecoder().decode(zaDekodiranje);
    }
    public void postaviteJavniKljucSagovornika(String slanjeJavnogKljuca) {
        byte[] dekodovaniKljuc = Base64.getDecoder().decode(slanjeJavnogKljuca);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(dekodovaniKljuc);
        KeyFactory zaPravljenje = null;
        try {
            zaPravljenje = KeyFactory.getInstance("RSA");
            PublicKey dekodovaniJavniKljuc  = zaPravljenje.generatePublic(keySpec);
            this.javniKljucSagovornika=dekodovaniJavniKljuc;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }





    public PublicKey getJavniKljuc(){
        return parKljuceva.getPublic();
    }
    public PrivateKey getPrivatniKljuc(){
        return parKljuceva.getPrivate();
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public KeyPair getParKljuceva() {
        return parKljuceva;
    }

    public void setParKljuceva(KeyPair parKljuceva) {
        this.parKljuceva = parKljuceva;
    }

    public SecretKey getSimetricniKljuc() {
        return simetricniKljuc;
    }

    public void setSimetricniKljuc(SecretKey simetricniKljuc) {
        this.simetricniKljuc = simetricniKljuc;
    }

    public PublicKey getJavniKljucSagovornika() {
        return javniKljucSagovornika;
    }

    public void setJavniKljucSagovornika(PublicKey javniKljucSagovornika) {
        this.javniKljucSagovornika = javniKljucSagovornika;
    }



}
