package com.ensta.librarymanager.model;

import java.util.NoSuchElementException;

public enum Abonnement {
        BASIC("BASIC"),
        PREMIUM("PREMIUM"),
        VIP("VIP");

        private String name;

        Abonnement(String nom)
        {
            this.name=nom;
        }

        public String getName() {
            return this.name;
        }

        public static Abonnement fromString(String name)
        {
                for (Abonnement abonnement : Abonnement.values())
                {
                        if (abonnement.name.equals(name))
                                return abonnement;
                }
                throw new NoSuchElementException("no enum for name " + name);
        }
}
