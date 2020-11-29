package com.nbjardins.adatpers.secondaries.mails;

import domain.entities.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Mail extends Entity{

     String subject;
     String from;
     String to;
     String message;

}
