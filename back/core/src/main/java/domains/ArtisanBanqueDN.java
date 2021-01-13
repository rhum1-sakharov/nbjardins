package domains;

import lombok.Data;

@Data
public class ArtisanBanqueDN extends Domain {

    private String iban;
    private String rib;
    private ArtisanDN artisan;
}
