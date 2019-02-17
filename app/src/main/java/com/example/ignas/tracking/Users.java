package com.example.ignas.tracking;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Users extends Fragment {


    private DatabaseReference databaseReference;
    private int position = 0;
    private EditText elektra;
    private EditText sildymas;
    private EditText vanduo;
    private EditText dujos;
    private EditText bustas;
    private EditText nuoma;
    private TextView monthName;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.months_menu, menu);

        for (int x = 0; x < 3; x++) {

            menu.getItem(x).setTitle(ToPay.monthsList.get(x).name);

        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        switch (item.getItemId()){

            case R.id.action0:
                if (position != 0){

                    position = 0;
                    updateFragment();

                }

                Toast.makeText(getActivity(), ToPay.monthsList.get(position).name, Toast.LENGTH_SHORT).show();
                break;
            case R.id.action1:
                if (position != 1){

                    position = 1;
                    updateFragment();

                }
                Toast.makeText(getActivity(), ToPay.monthsList.get(position).name, Toast.LENGTH_SHORT).show();
                break;
            case R.id.action2:
                if (position != 2){

                    position = 2;
                    updateFragment();

                }
                Toast.makeText(getActivity(), ToPay.monthsList.get(position).name, Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

    public void updateFragment () {

        final FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getUid() != null) {

            databaseReference = FirebaseDatabase.getInstance().getReference("month").child(auth.getUid()).child(ToPay.monthsList.get(position).id);

        }

        monthName.setText(ToPay.monthsList.get(position).name);

        if (ToPay.monthsList.get(position).elektra == 0) {

            elektra.getText().clear();
            elektra.setHint("€");

        }
        else {

            elektra.setText(Double.toString(ToPay.monthsList.get(position).elektra));

        }

        if (ToPay.monthsList.get(position).sildymas == 0) {

            sildymas.getText().clear();
            sildymas.setHint("€");

        }
        else {

            sildymas.setText(Double.toString(ToPay.monthsList.get(position).sildymas));

        }

        if (ToPay.monthsList.get(position).vanduo == 0) {

            vanduo.getText().clear();
            vanduo.setHint("€");

        }
        else {

            vanduo.setText(Double.toString(ToPay.monthsList.get(position).vanduo));

        }

        if (ToPay.monthsList.get(position).dujos == 0) {

            dujos.getText().clear();
            dujos.setHint("€");

        }
        else {

            dujos.setText(Double.toString(ToPay.monthsList.get(position).dujos));

        }

        if (ToPay.monthsList.get(position).bustas == 0) {

            bustas.getText().clear();
            bustas.setHint("€");

        }
        else {

            bustas.setText(Double.toString(ToPay.monthsList.get(position).bustas));

        }

        if (ToPay.monthsList.get(position).nuoma == 0) {

            nuoma.getText().clear();
            nuoma.setHint("€");

        }
        else {

            nuoma.setText(Double.toString(ToPay.monthsList.get(position).nuoma));

        }

        if (ToPay.monthsList.get(position).ifPaid) {

            elektra.setEnabled(false);
            sildymas.setEnabled(false);
            vanduo.setEnabled(false);
            dujos.setEnabled(false);
            bustas.setEnabled(false);
            nuoma.setEnabled(false);

            monthName.setTextColor(Color.parseColor("#FF00D921"));

        }
        else {

            elektra.setEnabled(true);
            sildymas.setEnabled(true);
            vanduo.setEnabled(true);
            dujos.setEnabled(true);
            bustas.setEnabled(true);
            nuoma.setEnabled(true);

            monthName.setTextColor(Color.parseColor("#000000"));

        }

        elektra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                databaseReference = FirebaseDatabase.getInstance().getReference("month").child(auth.getUid()).child(ToPay.monthsList.get(position).id);

                if (!elektra.getText().toString().isEmpty()) {

                    ToPay.monthsList.get(position).elektra = Double.parseDouble(elektra.getText().toString());
                    databaseReference.setValue(ToPay.monthsList.get(position));

                } else {

                    elektra.getText().clear();
                    elektra.setHint("€");
                    ToPay.monthsList.get(position).elektra = 0;
                    databaseReference.setValue(ToPay.monthsList.get(position));

                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        sildymas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                databaseReference = FirebaseDatabase.getInstance().getReference("month").child(auth.getUid()).child(ToPay.monthsList.get(position).id);

                if (!sildymas.getText().toString().isEmpty()) {

                    ToPay.monthsList.get(position).sildymas = Double.parseDouble(sildymas.getText().toString());
                    databaseReference.setValue(ToPay.monthsList.get(position));

                } else {

                    sildymas.getText().clear();
                    sildymas.setHint("€");
                    ToPay.monthsList.get(position).sildymas = 0;
                    databaseReference.setValue(ToPay.monthsList.get(position));

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        vanduo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                databaseReference = FirebaseDatabase.getInstance().getReference("month").child(auth.getUid()).child(ToPay.monthsList.get(position).id);

                if (!vanduo.getText().toString().isEmpty()) {

                    ToPay.monthsList.get(position).vanduo = Double.parseDouble(vanduo.getText().toString());
                    databaseReference.setValue(ToPay.monthsList.get(position));

                } else {

                    vanduo.getText().clear();
                    vanduo.setHint("€");
                    ToPay.monthsList.get(position).vanduo = 0;
                    databaseReference.setValue(ToPay.monthsList.get(position));

                }

            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });
        dujos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                databaseReference = FirebaseDatabase.getInstance().getReference("month").child(auth.getUid()).child(ToPay.monthsList.get(position).id);

                if (!dujos.getText().toString().isEmpty()) {

                    ToPay.monthsList.get(position).dujos = Double.parseDouble(dujos.getText().toString());
                    databaseReference.setValue(ToPay.monthsList.get(position));

                } else {

                    dujos.getText().clear();
                    dujos.setHint("€");
                    ToPay.monthsList.get(position).dujos = 0;
                    databaseReference.setValue(ToPay.monthsList.get(position));

                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        bustas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                databaseReference = FirebaseDatabase.getInstance().getReference("month").child(auth.getUid()).child(ToPay.monthsList.get(position).id);

                if (!bustas.getText().toString().isEmpty()) {

                    ToPay.monthsList.get(position).bustas = Double.parseDouble(bustas.getText().toString());
                    databaseReference.setValue(ToPay.monthsList.get(position));

                } else {

                    bustas.getText().clear();
                    bustas.setHint("€");
                    ToPay.monthsList.get(position).bustas = 0;
                    databaseReference.setValue(ToPay.monthsList.get(position));

                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        nuoma.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                databaseReference = FirebaseDatabase.getInstance().getReference("month").child(auth.getUid()).child(ToPay.monthsList.get(position).id);

                if (!nuoma.getText().toString().isEmpty()) {

                    ToPay.monthsList.get(position).nuoma = Double.parseDouble(nuoma.getText().toString());
                    databaseReference.setValue(ToPay.monthsList.get(position));

                } else {

                    nuoma.getText().clear();
                    nuoma.setHint("€");
                    ToPay.monthsList.get(position).nuoma = 0;
                    databaseReference.setValue(ToPay.monthsList.get(position));

                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_users, container, false);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in);

        LinearLayout linearLayout = v.findViewById(R.id.linear);

        linearLayout.startAnimation(animation);

        setHasOptionsMenu(true);

        elektra = v.findViewById(R.id.elektraEdit);
        sildymas = v.findViewById(R.id.sildymasEdit);
        vanduo = v.findViewById(R.id.vanduoEdit);
        dujos = v.findViewById(R.id.dujosEdit);
        bustas = v.findViewById(R.id.bustasEdit);
        nuoma = v.findViewById(R.id.nuomaEdit);

        monthName = v.findViewById(R.id.monthName);

        updateFragment();

        return v;
    }
}
