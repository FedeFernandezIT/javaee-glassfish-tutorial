/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nunait.glassfish.javaeetutorial.compositecomponentexample;

import java.io.Serializable;
import java.math.BigInteger;
import javax.enterprise.inject.Model;
import javax.validation.constraints.Size;

/**
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 2 de mayo de 2017 10:54:16 ART
 */
@Model
public class PrimeBean implements Serializable {

    private static final long serialVersionUID = -4040210654121590647L;
    private static int[] primes;

    @Size(min = 1, max = 45)
    private String name;
    private boolean prime;
    private String response;
    private int totalSum;

    /**
     * Crea una nueva instancia de PrimeBean
     */
    public PrimeBean() {
        setPrimes();
    }

    /**
     * Suma los valores de letras, y determina si el valor de la suma es un
     * número primo.
     *
     * @return String de la página inicial
     */
    public String calculate() {
        final String letters = "abcdefghijklmnopqrstuvwxyz";
        int sum = 0;

        for (int m = 0; m < name.length(); m++) {
            char let = name.charAt(m);
            for (int n = 0; n < 26; n++) {
                char tc = Character.toLowerCase(let);
                if (tc == letters.charAt(n)) {
                    int letVal = n + 1;
                    System.out.println("[WEB-APP:COMPOSITE] Valor de letra "
                            + let + " es " + letVal);
                    sum += letVal;
                }
            }
        }
        System.out.println("[WEB-APP:COMPOSITE] Suma es " + sum);
        prime = false;
        if (sum == 0) {
            setResponse("El texto no contiene letras");
        } else if (sum % 2 == 0 && sum != 2) {
            setResponse("La suma de letras no es un primo");
        } else if (sum % 3 == 0 && sum != 3) {
            setResponse("La suma de letras no es un primo");
        } else {
            for (int n = 0; n < 194; n++) {
                if (sum == primes[n]) {
                    prime = true;
                }
            }
            if (prime) {
                setResponse("La suma de letras es un primo");
            } else {
                setResponse("La suma de letras no es un primo");
            }
        }
        totalSum = sum;
        return "index";
    }

    public static int[] getPrimes() {
        return primes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrime() {
        return prime;
    }

    public void setPrime(boolean prime) {
        this.prime = prime;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }

    /**
     * Crea un array de números primos hasta 1171 (uno mayor que la suma de los
     * valores de 45 letras Z, ya que la longitud máxima de la entrada es 45
     * caracteres.
     */
    public static void setPrimes() {
        BigInteger i;
        BigInteger lastNum;
        int count = 0;

        primes = new int[194];
        i = new BigInteger("1");
        lastNum = new BigInteger("1171");
        do {
            primes[count] = i.intValue();
            i = i.nextProbablePrime();
            count++;
        } while (i.compareTo(lastNum) <= 0x0);
    }

}
