package com.example.ignas.tracking;

public class Months {

    String name;
    String id;
    int year;
    double dujos;
    double bustas;
    double elektra;
    double sildymas;
    double vanduo;
    double nuoma;
    double sum;
    boolean ifPaid;

    public Months() {
    }

    public Months(String name, String id, int year, boolean ifPaid, double dujos, double bustas, double elektra, double sildymas, double vanduo, double nuoma, double sum) {

        this.name = name;
        this.id = id;
        this.ifPaid = ifPaid;
        this.dujos = dujos;
        this.bustas = bustas;
        this.elektra = elektra;
        this.sildymas = sildymas;
        this.vanduo = vanduo;
        this.nuoma = nuoma;
        this.sum = sum;
        this.year = year;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSum() {
        return dujos + bustas + elektra + sildymas + vanduo + nuoma;
    }

    public boolean isIfPaid() {
        return ifPaid;
    }
}
