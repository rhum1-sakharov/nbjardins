package com.nbjardins.adapters.secondaries.localization;

import lombok.Getter;

import java.util.Locale;

@Getter
public abstract class AbstractLocalize {

    final Locale frLocale = new Locale("fr", "FR");
    final Locale enLocale = new Locale("en", "EN");

    public AbstractLocalize() {
    }
}
