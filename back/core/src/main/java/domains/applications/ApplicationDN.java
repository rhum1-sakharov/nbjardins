package domains.applications;

import domains.Domain;
import lombok.Data;

@Data
public class ApplicationDN extends Domain {

    private String nom;
    private String token;
    private String site;
}
