/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ujisistemcassandra.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class listToLowercase {
    public List<String> listLowercase(List<String> listUppercase) {
        List<String> lower = new ArrayList<>();

        for (String str : listUppercase){
            lower.add(str.toLowerCase());
        }

        Collections.sort(lower);

        return lower;
    }
}
