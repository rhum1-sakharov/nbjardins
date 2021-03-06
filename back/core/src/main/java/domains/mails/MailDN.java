package domains.mails;

import domains.Domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MailDN extends Domain {

     String subject;
     String from;
     String to;
     String message;

}
