package com.nbjardins.domain;

import com.nbjardins.domain.entities.Mail;

public class Main {

    public static void main(String[] args) {
        Mail mail = new Mail("clean architecture","romain","nicolas","that s good");

        System.out.println(mail);
    }

}
