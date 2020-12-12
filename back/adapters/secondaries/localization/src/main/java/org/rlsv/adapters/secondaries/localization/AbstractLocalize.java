package org.rlsv.adapters.secondaries.localization;

import lombok.Getter;

import java.util.Locale;

@Getter
public abstract class AbstractLocalize {

    final Locale frLocale = new Locale("fr", "FR");
    final Locale enLocale = new Locale("en", "EN");
    final Locale workerLocale = frLocale;

    public AbstractLocalize() {
    }
}
