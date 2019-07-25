package com.elegion.courserafirstcourseprogrammingtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * @author Azret Magometov
 */

public class CharacterFragment extends Fragment {


    public static final String ARG_CHARACTER = "ARG_CHARACTER";

    private Character mCharacter;

    private TextView mName;
    private TextView mRace;
    private TextView mSpecialization;
    private TextView mHp;
    private TextView mMana;
    private TextView mPhysicDefense;
    private TextView mMagicDefense;
    private TextView mAttack;
    private TextView mCriticalHit;
    private TextView mDodge;

    public static CharacterFragment newInstance(Character character) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CHARACTER, character);
        CharacterFragment fragment = new CharacterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCharacter = (Character) getArguments().getSerializable(ARG_CHARACTER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_character, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mName = view.findViewById(R.id.value_name);
        mName.setText(mCharacter.getName());

        mRace = view.findViewById(R.id.value_race);
        mRace.setText(mCharacter.getRace());

        mSpecialization = view.findViewById(R.id.value_spec);
        mSpecialization.setText(mCharacter.getSpecialization());

        mHp = view.findViewById(R.id.value_hp);
        mHp.setText(mCharacter.getHitPoints());

        mAttack = view.findViewById(R.id.value_attack);
        mAttack.setText(mCharacter.getAttack());

        mMana = view.findViewById(R.id.value_mana);
        mMana.setText(mCharacter.getManaPoints());

        mPhysicDefense = view.findViewById(R.id.value_defense_physics);
        mPhysicDefense.setText(mCharacter.getPhysicalDefense());

        mMagicDefense = view.findViewById(R.id.value_defense_magic);
        mMagicDefense.setText(mCharacter.getMagicDefense());

        mDodge = view.findViewById(R.id.value_dodge);
        mDodge.setText(mCharacter.getDodge());

        mCriticalHit = view.findViewById(R.id.value_critical_hit_chance);
        mCriticalHit.setText(mCharacter.getCriticalHitChance());
    }


}
