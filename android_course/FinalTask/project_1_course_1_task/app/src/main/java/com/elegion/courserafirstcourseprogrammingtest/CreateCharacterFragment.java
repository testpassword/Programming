package com.elegion.courserafirstcourseprogrammingtest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 * @author Azret Magometov
 */

public class CreateCharacterFragment extends Fragment implements Observer {

    public static final String TAG = CreateCharacterFragment.class.getSimpleName();

    private EditText mNameEt;
    private Spinner mRacesSpinner;
    private TextView mAvailablePoints;
    private TextView[] mParamValues;
    private ImageButton[] mParamControlButtons;
    private LinearLayout mParamsContainer;
    private RadioGroup mSpecializationsRadioGroup;
    private LinearLayout mPerksContainer;

    private CharacterCreator mCreator;

    private Callback mCallback;

    public static CreateCharacterFragment newInstance() {
        return new CreateCharacterFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = context instanceof Callback ? ((Callback) context) : null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_character, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mNameEt = view.findViewById(R.id.et_name);
        mRacesSpinner = view.findViewById(R.id.spinner_race);
        mAvailablePoints = view.findViewById(R.id.tv_available_points);
        mParamsContainer = view.findViewById(R.id.ll_params_container);
        mSpecializationsRadioGroup = view.findViewById(R.id.rg_rerson_classes);
        mPerksContainer = view.findViewById(R.id.ll_perks_container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        obtainCreator();
        addRaces();
        addSpecializations();
        addParametersList();
        addPerks();
    }

    private void obtainCreator() {
        mCallback.onCreateCharacterStarted();
    }

    public void configureCreator(CharacterCreator creator) {
        mCreator = creator;
        mCreator.addObserver(this);
    }

    private void addRaces() {
        String[] races = mCreator.getRaces();
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, races);

        mRacesSpinner.setAdapter(spinnerAdapter);
        mRacesSpinner.setSelection(mCreator.getRacePosition());
        mRacesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCreator.setRace(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void addSpecializations() {
        String[] specializations = mCreator.getSpecializations();
        for (String s : specializations) {
            RadioButton button = new RadioButton(getActivity());
            button.setText(s);
            button.setId(View.generateViewId());
            mSpecializationsRadioGroup.addView(button);
        }

        mSpecializationsRadioGroup.check(mSpecializationsRadioGroup.getChildAt(mCreator.getSpecializationPosition()).getId());
        mSpecializationsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checked = group.findViewById(checkedId);
                int position = group.indexOfChild(checked);
                mCreator.setSpecialization(position);
            }

        });
    }

    private void addParametersList() {
        String[] params = mCreator.getAttributes();
        mParamValues = new TextView[params.length];
        mParamControlButtons = new ImageButton[params.length * 2];

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        for (int i = 0, size = params.length; i < size; i++) {
            View paramsRow = inflater.inflate(R.layout.li_parameter, mParamsContainer, false);
            TextView paramName = paramsRow.findViewById(R.id.tv_param_name);
            paramName.setText(params[i]);

            TextView paramValue = paramsRow.findViewById(R.id.tv_param_value);
            mParamValues[i] = paramValue;
            mParamValues[i].setTag(params[i]);

            ImageButton decreaseParam = paramsRow.findViewById(R.id.ib_param_decrease);
            mParamControlButtons[i] = decreaseParam;

            ImageButton increaseParam = paramsRow.findViewById(R.id.ib_param_increase);
            mParamControlButtons[i + size] = increaseParam;

            mParamsContainer.addView(paramsRow);
        }

        // TODO: 11.12.2017  раскоментируйте это после того, как доделаете логику CharacterCreator.updateAttributeValue();

//        for (int i = 0, size = mParamControlButtons.length; i < size; i++) {
//            int rowCount = size / 2;
//            final int row = i < rowCount ? i : i - rowCount;
//            final int action = i < rowCount ? -1 : 1;
//
//            mParamControlButtons[i].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mCreator.updateAttributeValue(row, action);
//                }
//            });
//        }
    }

    private void addPerks() {

        String perks[] = mCreator.getPerks();

        for (String perk : perks) {
            CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setText(perk);
            checkBox.setTag(perk);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mCreator.checkPerk(buttonView.getText().toString(), isChecked);
                }
            });
            mPerksContainer.addView(checkBox);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        update(mCreator, null);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof CharacterCreator) {
            mAvailablePoints.setText(mCreator.getAvailablePoints());

            Map<String, Integer> attributesMap = mCreator.getAttributesMap();
            for (TextView view : mParamValues) {
                String key = ((String) view.getTag()).toUpperCase();
                String value = String.valueOf(attributesMap.get(key));
                view.setText(value);
            }

            Map<String, Boolean> perksMap = mCreator.getPerksMap();
            Set<String> tags = perksMap.keySet();
            for (String tag : tags) {
                CheckBox checkBox = mPerksContainer.findViewWithTag(tag);
                checkBox.setChecked(perksMap.get(tag));
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.create_character, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.create) {
            mCreator.setName(mNameEt.getText().toString());
            mCallback.onCreateCharacterCompleted(mCreator.create());
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }

    public interface Callback {
        void onCreateCharacterStarted();

        void onCreateCharacterCompleted(Character character);
    }
}
