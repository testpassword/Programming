package com.elegion.courserafirstcourseprogrammingtest;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Azret Magometov
 */

public class Character implements Serializable {

    private static final int ATTACK_SEED = 4;
    private static final int PHYSICAL_DEFENSE_SEED = 4;
    private static final int MAGIC_DEFENSE_SEED = 4;
    private static final int DODGE_SEED = 4;
    private static final int CRITICAL_ATTACK_SEED = 4;
    private static final int HP_SEED = 4;
    private static final int MANA_SEED = 4;


    private String mName;
    private CharacterCreator.Race mRace;
    private CharacterCreator.Specialization mSpecialization;
    private Map<String, Integer> mAttributesMap;

    private int mClassParameter;
    private float mAttack;
    private float mPhysicalDefense;
    private float mMagicDefense;
    private float mDodge;
    private float mCriticalHitChance;
    private int mHitPoints;
    private int mManaPoints;

    private Map<String, Boolean> mPerks;

    public void setName(String name) {
        mName = name;
    }

    public void setRace(CharacterCreator.Race race) {
        mRace = race;
    }

    public void setSpecialization(CharacterCreator.Specialization personClass) {
        mSpecialization = personClass;
    }

    public String getAttack() {
        return String.valueOf(mAttack);
    }

    public String getPhysicalDefense() {
        return String.valueOf(mPhysicalDefense);
    }

    public String getMagicDefense() {
        return String.valueOf(mMagicDefense);
    }

    public String getHitPoints() {
        return String.valueOf(mHitPoints);
    }

    public String getManaPoints() {
        return String.valueOf(mManaPoints);
    }

    public String getDodge() {
        return String.valueOf(mDodge);
    }

    public String getCriticalHitChance() {
        return String.valueOf(mCriticalHitChance);
    }

    public void calculateParameters() {
        switch (mSpecialization) {
            case WARRIOR:
                mClassParameter = mAttributesMap.get(CharacterCreator.Attribute.STRENGTH.name());
                mManaPoints = 0;
                break;
            case MAGE:
                mClassParameter = mAttributesMap.get(CharacterCreator.Attribute.INTELLECT.name());
                mManaPoints = mClassParameter * MANA_SEED;
                break;
            case ARCHER:
                mClassParameter = mAttributesMap.get(CharacterCreator.Attribute.AGILITY.name());
                mManaPoints = 0;
                break;
        }
        mAttack = mClassParameter * ATTACK_SEED;
        mMagicDefense = mAttributesMap.get(CharacterCreator.Attribute.INTELLECT.name()) * MAGIC_DEFENSE_SEED;
        mPhysicalDefense = mAttributesMap.get(CharacterCreator.Attribute.STRENGTH.name()) * PHYSICAL_DEFENSE_SEED;
        mDodge = mAttributesMap.get(CharacterCreator.Attribute.AGILITY.name()) * DODGE_SEED;
        mCriticalHitChance = mAttributesMap.get(CharacterCreator.Attribute.LUCK.name()) * CRITICAL_ATTACK_SEED + mClassParameter;
        mHitPoints = mAttributesMap.get(CharacterCreator.Attribute.STAMINA.name()) * HP_SEED + mClassParameter;

        Boolean isBerserk = mPerks.get(CharacterCreator.Perk.BERSERK.name());
        if (isBerserk != null && isBerserk) {
            mAttack *= 1.1;
            mPhysicalDefense *= 0.85;
            mMagicDefense *= 0.85;
        }

        Boolean isCalm = mPerks.get(CharacterCreator.Perk.CALM.name());
        if (isCalm != null && isCalm) {
            mAttack *= 0.85;
            mPhysicalDefense *= 1.1;
            mMagicDefense *= 1.1;
        }

        Boolean isLigthWeight = mPerks.get(CharacterCreator.Perk.LIGHTWEIGHT.name());
        if (isLigthWeight != null && isLigthWeight) {
            mDodge *= 1.1;
            mPhysicalDefense *= 0.85;
        }

        Boolean isHeavyArmored = mPerks.get(CharacterCreator.Perk.HEAVYARMORED.name());
        if (isHeavyArmored != null && isHeavyArmored) {
            mPhysicalDefense *= 1.1;
            mDodge *= 0.85;
        }

        Boolean isMeditations = mPerks.get(CharacterCreator.Perk.MEDITATIONS.name());
        if (isMeditations != null && isMeditations) {
            mMagicDefense *= 1.1;
            mCriticalHitChance *= 0.85;
        }

        Boolean isObservant = mPerks.get(CharacterCreator.Perk.OBSERVANT.name());
        if (isObservant != null && isObservant) {
            mCriticalHitChance *= 1.1;
            mMagicDefense *= 0.85;
        }

    }

    @Override
    public String toString() {
        return "Character{" +
                "mRace=" + mRace +
                ", mSpecialization=" + mSpecialization +
                ", mAttack=" + mAttack +
                ", mPhysicalDefense=" + mPhysicalDefense +
                ", mMagicDefense=" + mMagicDefense +
                ", mDodge=" + mDodge +
                ", mCriticalHitChance=" + mCriticalHitChance +
                ", mHitPoints=" + mHitPoints +
                ", mManaPoints=" + mManaPoints +
                '}';
    }

    public void setAttributes(Map<String, Integer> attributesMap) {
        mAttributesMap = attributesMap;
    }

    public void setPerks(Map<String, Boolean> perks) {
        mPerks = perks;
    }


    public String getName() {
        return mName;
    }

    public String getRace() {
        return mRace.name().substring(0, 1) + mRace.name().substring(1).toLowerCase();
    }

    public String getSpecialization() {
        return mSpecialization.name().substring(0, 1) + mSpecialization.name().substring(1).toLowerCase();
    }
}
