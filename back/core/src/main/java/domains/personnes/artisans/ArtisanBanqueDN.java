package domains.personnes.artisans;

import domains.Domain;
import lombok.Data;

@Data
public class ArtisanBanqueDN extends Domain {

    private String iban;
    private String rib;
    private ArtisanDN artisan;
    private String banque;
    private Boolean prefere;
}
