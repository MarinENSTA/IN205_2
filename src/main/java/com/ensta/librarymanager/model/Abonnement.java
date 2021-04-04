package com.ensta.librarymanager.model;

import java.util.NoSuchElementException;

public enum Abonnement {
        BASIC("BASIC", 2),
        PREMIUM("PREMIUM", 5),
        VIP("VIP", 20);

        private String name;
        private int simultaneous;

        Abonnement(String nom, int simultaneous)
        {
                this.simultaneous=simultaneous;
                this.name=nom;
        }

        public String getName() {
            return this.name;
        }

        public int getSimultaneous ()
        {
                return this.simultaneous;
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
