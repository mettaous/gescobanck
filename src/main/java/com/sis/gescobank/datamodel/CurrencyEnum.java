package com.sis.gescobank.datamodel;

public enum CurrencyEnum {

    EURO("€", "EUR"),
    POUND_STERLING("£", "GBP"),
    DOLLAR_US("$", "USD"),
    DIRHAM_MOROCCAN(null, "MAD");

    private String symbol;
    private String codeIso;

    CurrencyEnum(String symbol, String codeIso) {
        this.symbol = symbol;
        this.codeIso = codeIso;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCodeIso() {
        return codeIso;
    }
}
