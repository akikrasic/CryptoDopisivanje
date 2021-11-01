package rs.etf.ka143095.mreza.akcija;

import rs.etf.ka143095.main.Main;

import javax.swing.*;

/**
 * Created by aki on 7/7/16.
 */
public class SlanjePoruke extends Akcija{

    public SlanjePoruke(Main main){
        super(main);
    }

    @Override
    public void izvrsiteAkciju(String poruka) {
        String dekriptovanaPoruka =kripto.dekriptujteSimetricnimKljucem(poruka);
        SwingUtilities.invokeLater(()->{
            main.getForma().dodajtePorukuSagovornika(dekriptovanaPoruka);

        });

    }
}
