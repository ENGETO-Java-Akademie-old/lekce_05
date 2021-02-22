package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        File zdrojovySoubor = new File("vat-eu.txt");

        Scanner scanner = new Scanner(zdrojovySoubor);

        List<DanoveSazbyStatu> sazbyStatu = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String nactenyRadek = scanner.nextLine();
            sazbyStatu.add(rozparsujRadek(nactenyRadek));
        }

        //vypisInformaceOStatech(vyfilstrujStatySeSazbamiVetsimiNez(sazbyStatu, 23.0F));

        Scanner nacitaniZPrikazovehoRadku = new Scanner(System.in);

        String zkratka;

        System.out.println("Zadej zkratku požadovaného státu:");

        zkratka = nacitaniZPrikazovehoRadku.nextLine();

        //System.out.println("Vybral sis tento stat: " + zkratka);

        DanoveSazbyStatu hledanaSazba = najdiStatPodleZkratky(sazbyStatu, zkratka);

        if (hledanaSazba == null) {
            System.out.println("Pro tento stát nemáme žádné informace!");
        } else {
            System.out.println("Jméno: " + hledanaSazba.getJmenoStatu());
            System.out.println("Plná danová sazba: " + hledanaSazba.getPlnaDanovaSazba());
            System.out.println("Snížená daňová sazba: " + hledanaSazba.getSnizenaDanovaSazba());
        }

    }

    private static DanoveSazbyStatu rozparsujRadek(String nactenyRadek) {

        String[] rozsekanyRadek = nactenyRadek.split("\t");

        DanoveSazbyStatu danoveSazbyStatu = new DanoveSazbyStatu();

        danoveSazbyStatu.setZkratkaStatu(rozsekanyRadek[0]);
        danoveSazbyStatu.setJmenoStatu(rozsekanyRadek[1]);
        danoveSazbyStatu.setPlnaDanovaSazba(Float.valueOf(rozsekanyRadek[2].replace(",", ".")));
        danoveSazbyStatu.setSnizenaDanovaSazba(Float.valueOf(rozsekanyRadek[3].replace(",", ".")));
        danoveSazbyStatu.setSpecialniDanovaSazba(Boolean.valueOf(rozsekanyRadek[4]));

        return danoveSazbyStatu;

    }

    private static List<DanoveSazbyStatu> vyfilstrujStatySeSazbamiVetsimiNez(List<DanoveSazbyStatu> kFiltrovani, Float sazba) {
        List<DanoveSazbyStatu> danoveSazbyStatu = new ArrayList<>();
        for (DanoveSazbyStatu dss : kFiltrovani) {
            if (dss.getPlnaDanovaSazba() > sazba) {
                danoveSazbyStatu.add(dss);
            }
        }
        return danoveSazbyStatu;
    }

    private static void vypisInformaceOStatech(List<DanoveSazbyStatu> kVypsani) {
        for (DanoveSazbyStatu dss : kVypsani) {
            System.out.println(dss.getJmenoStatu() + " (" + dss.getPlnaDanovaSazba() +
                    "%/" + dss.getSnizenaDanovaSazba() + "%)");
        }
    }

    private static DanoveSazbyStatu najdiStatPodleZkratky(List<DanoveSazbyStatu> prohledavanySeznam, String zkratka) {
        for (DanoveSazbyStatu dss : prohledavanySeznam) {
            if (dss.getZkratkaStatu().equals(zkratka)) {
                return dss;
            }
        }
        return null;
    }

}
