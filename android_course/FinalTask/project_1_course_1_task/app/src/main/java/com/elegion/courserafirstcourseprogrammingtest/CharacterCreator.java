package com.elegion.courserafirstcourseprogrammingtest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class CharacterCreator extends Observable implements Serializable{

    public enum Specialization {
        WARRIOR, ARCHER, MAGE
    }

    public enum Race {
        HUMAN, ELF, ORC, DWARF
    }

    public enum Attribute {
        STRENGTH, AGILITY, INTELLECT, STAMINA, LUCK
    }

    public enum Perk {
        BERSERK, CALM, LIGHTWEIGHT, HEAVYARMORED, OBSERVANT, MEDITATIONS
    }

    private String mName;
    private Specialization mSpecialization;
    private Race mRace;
    private int mAvailablePoints;

    private Map<String, Integer> mAttributesMap = new HashMap<>();
    private Map<String, Boolean> mPerksMap = new HashMap<>();


    public CharacterCreator() {
        mRace = Race.HUMAN;
        mSpecialization = Specialization.WARRIOR;
        mAvailablePoints = 5;
        mAttributesMap.put(Attribute.STRENGTH.name(), 5);
        mAttributesMap.put(Attribute.AGILITY.name(), 5);
        mAttributesMap.put(Attribute.INTELLECT.name(), 5);
        mAttributesMap.put(Attribute.STAMINA.name(), 5);
        mAttributesMap.put(Attribute.LUCK.name(), 5);
    }


    public String[] getSpecializations() {
        String[] spec = new String[Specialization.values().length];
        int i = 0;
        for (Specialization s: Specialization.values()) {
            spec[i] = s.toString().substring(0, 1).toUpperCase() + s.toString().substring(1);
            i++;
        }
        return spec;

    }

    public void setSpecialization(int position) {
        if (position < 0) mSpecialization = Specialization.values()[0];
        else if (position > Specialization.values().length) mSpecialization = Specialization.values()[2];
        else mSpecialization = Specialization.values()[position];
    }

    public String[] getRaces() {
        String[] races = new String[Race.values().length];
        int i = 0;
        for (Race r: Race.values()) {
            races[i] = r.toString().substring(0, 1).toUpperCase() + r.toString().substring(1);
            i++;
        }
        return races;
    }

    public void setRace(int position) {
        if (position < 0) mRace = Race.values()[0];
        else if (position > Specialization.values().length) mSpecialization = Specialization.values()[3];
        else mSpecialization = Specialization.values()[position];
    }

    public String[] getAttributes() {
        String[] attributes = new String[Attribute.values().length];
        int i = 0;
        for (Attribute a: Attribute.values()) {
            attributes[i] = a.toString().substring(0, 1).toUpperCase() + a.toString().substring(1);
            i++;
        }
        return attributes;
    }

    public String[] getPerks() {
        String[] perks = new String[Perk.values().length];
        int i = 0;
        for (Perk p: Perk.values()) {
            perks[i] = p.toString().substring(0, 1).toUpperCase() + p.toString().substring(1);
            i++;
        }
        return perks;

    }
    public void updateAttributeValue(int position, int updateTo) {
        String attribute = getAttributes()[position].toUpperCase();
        int currentValue = mAttributesMap.get(attribute);
        if (updateTo > 0) {
            if (mAvailablePoints >= updateTo) {
                mAttributesMap.put(attribute, currentValue + updateTo);
                mAvailablePoints = mAvailablePoints - updateTo;
            }
        } else {
            if (mAttributesMap.get(attribute) + updateTo > 0) {
                mAttributesMap.put(attribute, currentValue + updateTo);
                mAvailablePoints = mAvailablePoints - updateTo;
            }
        }
        setChanged();
        notifyObservers();
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAvailablePoints() {
        return String.valueOf(mAvailablePoints);
    }

    public Map<String, Integer> getAttributesMap() {
        return mAttributesMap;
    }

    public void checkPerk(String text, boolean isChecked) {
        mPerksMap.put(text, isChecked);
    }

    public Character create() {
        Character character = new Character();
        character.setName(mName);
        character.setRace(mRace);
        character.setSpecialization(mSpecialization);
        character.setAttributes(mAttributesMap);
        character.setPerks(mPerksMap);
        character.calculateParameters();
        return character;
    }

    public Specialization getSpecialization() {
        return mSpecialization;
    }

    public Race getRace() {
        return mRace;
    }

    public Map<String, Boolean> getPerksMap() {
        return mPerksMap;
    }

    public void setAvailablePoints(int availablePoints) {
        mAvailablePoints = availablePoints;
    }

    public int getRacePosition() {
        return mRace.ordinal();
    }

    public int getSpecializationPosition() {
        return mSpecialization.ordinal();
    }
}
