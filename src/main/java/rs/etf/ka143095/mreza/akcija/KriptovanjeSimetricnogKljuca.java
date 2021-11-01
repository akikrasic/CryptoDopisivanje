package rs.etf.ka143095.mreza.akcija;

import rs.etf.ka143095.main.Main;

/**
 * Created by aki on 7/7/16.
 */
public class KriptovanjeSimetricnogKljuca extends Akcija {

    public KriptovanjeSimetricnogKljuca(Main main){
        super(main);
    }
    @Override
    public void izvrsiteAkciju(String poruka) {
        String simetricniKljucKriptovani = poruka;
        kripto.dekriptujteIPostaviteSimetricniKljuc(simetricniKljucKriptovani);
        main.getForma().sagovornikSeUspesnoPovezaoSaVama();

    }
}
