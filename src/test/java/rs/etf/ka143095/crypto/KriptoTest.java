package rs.etf.ka143095.crypto;

import org.junit.Before;
import org.junit.Test;
import rs.etf.ka143095.main.Main;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static org.junit.Assert.*;

/**
 * Created by aki on 7/7/16.
 */
public class KriptoTest {
   private Kripto kripto;
    @Before
    public void setUp(){
        Main main = new Main();
        kripto = main.getKripto();
    }
    @Test
    public void mainNotNull(){
        assertNotNull(kripto.getMain());
    }
    @Test
    public void  proveriteJavniKljuc(){
        assertNotNull(kripto.getJavniKljuc());
    }
    @Test
    public void proveriteJavniKljucEnkodovani() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String enkodovani = kripto.getJavniKljucEnkodovani();
        byte[] dekodovaniKljuc = Base64.getDecoder().decode(enkodovani);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(dekodovaniKljuc);
        KeyFactory zaPravljenje =KeyFactory.getInstance("RSA");
        PublicKey dekodovaniJavniKljuc  = zaPravljenje.generatePublic(keySpec);
        assertEquals(dekodovaniJavniKljuc, kripto.getJavniKljuc());
    }
    @Test
    public void  proveritePrivatniKljuc(){
        assertNotNull(kripto.getPrivatniKljuc());
    }

    @Test
    public void generisiteSimetricniKljuc1() throws Exception {
        kripto.generisiteSimetricniKljuc();
        assertNotNull(kripto.getSimetricniKljuc());
    }

    @Test
    public void generisitePrivatniIJavniKljuc() throws Exception {
        kripto.generisitePrivatniIJavniKljuc();
        assertNotNull(kripto.getJavniKljuc());
        assertNotNull(kripto.getPrivatniKljuc());
    }
    @Test
    public void postaviteSimetricniKljuc(){
        SecretKey kljucPreSlanja = kripto.getSimetricniKljuc();

        String simetricniKljuc=kripto.vratiteSimetricniKljucZaSlanje();
        kripto.postaviteSimetricniKljuc(simetricniKljuc);
        assertNotNull(kripto.getSimetricniKljuc());
        String enkodiraniKljuc = Base64.getEncoder().encodeToString(kripto.getSimetricniKljuc().getEncoded());
        //assertEquals(simetricniKljuc, enkodiraniKljuc);
        assertEquals(kljucPreSlanja, kripto.getSimetricniKljuc());
    }

    @Test
    public void rsaEnkripcijaiDekripcija(){
        String zaEnkripciju = "Ово је стринг за енкрипцију.";
        kripto.setJavniKljucSagovornika(kripto.getJavniKljuc());
        String enkriptovaniString = kripto.enkriptujteJavnimKljucemSagovornika(zaEnkripciju);
        assertEquals(zaEnkripciju, kripto.dekriptujteSvojimPrivatnimKljucem(enkriptovaniString));

    }
    @Test
    public void aesEnkripcijaIDekripcija(){
        String zaEnkripciju = "Ovo je string za enkripciju";
        String enkriptovaniString = kripto.enkriptujteSimetricnimKljucem(zaEnkripciju);
        assertEquals(zaEnkripciju, kripto.dekriptujteSimetricnimKljucem(enkriptovaniString));

    }

    @Test
    public void prebacivanjeSimetricnogKljucaPomocuJavnogiPrivatnog() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        kripto.setJavniKljucSagovornika(kripto.getJavniKljuc());//zbog testiranja
        SecretKey stariKljuc = kripto.getSimetricniKljuc();


        String kriptovaniSimetricniKljucJavnimKljucem = kripto.enkriptujteSimetricniKljucJavnimKljucemSagovornika();
        kripto.setSimetricniKljuc(null);
       // System.out.println(kriptovaniSimetricniKljucJavnimKljucem);
        assertNull(kripto.getSimetricniKljuc());
        kripto.dekriptujteIPostaviteSimetricniKljuc(kriptovaniSimetricniKljucJavnimKljucem);
        assertEquals(stariKljuc, kripto.getSimetricniKljuc());
        String poruka="Порука за енкрипцију и декрипцију.";
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE, stariKljuc);
        String cipherText = kripto.enkodujte(c.doFinal(poruka.getBytes()));
        c.init(Cipher.DECRYPT_MODE, kripto.getSimetricniKljuc());
        String rez = new String(c.doFinal(kripto.dekodujte(cipherText)));
        //System.out.println(poruka);
       // System.out.println(rez);
        assertEquals(poruka, rez);

    }
    @Test
    public void postavljanjeJavnogKljucaSagovornika(){
        PublicKey stariKljuc = kripto.getJavniKljuc();
    //ovde ce se upotrebiti isti kljuc kako bi se lakse testiralo...
        String slanjeJavnogKljuca= kripto.getJavniKljucEnkodovani();
        kripto.postaviteJavniKljucSagovornika(slanjeJavnogKljuca);
    }

    @Test
    public void konacniTest(){
        //slanje javnog kljuca pa slanje privatnoog pa onda enkriptovanje i dekriotovanje poruke
        String javniKljuc = kripto.getJavniKljucEnkodovani();
        kripto.postaviteJavniKljucSagovornika(javniKljuc);
        String simetricniKljuc = kripto.vratiteSimetricniKljucZaSlanje();
        String poruka="Порука која треба да се пошаље.";
        String cipherText = kripto.enkriptujteSimetricnimKljucem(poruka);
        kripto.postaviteSimetricniKljuc(simetricniKljuc);
        String plainText = kripto.dekriptujteSimetricnimKljucem(cipherText);
        assertEquals(plainText, poruka);
    }


}