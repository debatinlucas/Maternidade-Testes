package br.com.dlweb.maternidade.bebe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import br.com.dlweb.maternidade.R;
import br.com.dlweb.maternidade.database.DatabaseHelper;

public class AdicionarFragment extends Fragment {

    EditText etNome;
    EditText etDataNascimento;
    EditText etPeso;
    EditText etAltura;
    Spinner spMae;
    Spinner spMedico;
    ArrayList<Integer> listMaeId;
    ArrayList<String> listMaeName;
    ArrayList<Integer> listMedicoId;
    ArrayList<String> listMedicoName;
    DatabaseHelper databaseHelper;

    public AdicionarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bebe_fragment_adicionar, container, false);

        spMae = v.findViewById(R.id.spinnerMae);
        spMedico = v.findViewById(R.id.spinnerMedico);
        etNome = v.findViewById(R.id.editTextNome);
        etDataNascimento = v.findViewById(R.id.editTextDataNascimento);
        etPeso = v.findViewById(R.id.editTextPeso);
        etAltura = v.findViewById(R.id.editTextAltura);

        databaseHelper = new DatabaseHelper(getActivity());

        listMaeId = new ArrayList<>();
        listMaeName = new ArrayList<>();
        databaseHelper.getAllNameMae(listMaeId, listMaeName);

        listMedicoId = new ArrayList<>();
        listMedicoName = new ArrayList<>();
        databaseHelper.getAllNameMedico(listMedicoId, listMedicoName);

        ArrayAdapter<String> spMaeArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listMaeName);
        spMae.setAdapter(spMaeArrayAdapter);

        ArrayAdapter<String> spMedicoArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listMedicoName);
        spMedico.setAdapter(spMedicoArrayAdapter);

        Button btnSalvar = v.findViewById(R.id.buttonAdicionar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionar();
            }
        });

        return v;
    }

    private void adicionar () {
        if (spMae.getSelectedItem() == null) {
            Toast.makeText(getActivity(), "Por favor, selecione a mãe!", Toast.LENGTH_LONG).show();
        } else if (spMedico.getSelectedItem() == null) {
            Toast.makeText(getActivity(), "Por favor, selecione o médico!", Toast.LENGTH_LONG).show();
        } else if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (etDataNascimento.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a data nascimento!", Toast.LENGTH_LONG).show();
        } else if (etPeso.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o peso!", Toast.LENGTH_LONG).show();
        } else if (etAltura.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o altura!", Toast.LENGTH_LONG).show();
        } else {
            Bebe b = new Bebe();
            String nomeMae = spMae.getSelectedItem().toString();
            b.setId_mae(listMaeId.get(listMaeName.indexOf(nomeMae)));
            String nomeMedico = spMedico.getSelectedItem().toString();
            b.setId_medico(listMedicoId.get(listMedicoName.indexOf(nomeMedico)));
            b.setNome(etNome.getText().toString());
            b.setData_nascimento(etDataNascimento.getText().toString());
            b.setPeso(Float.parseFloat(etPeso.getText().toString()));
            b.setAltura(Integer.parseInt(etAltura.getText().toString()));
            databaseHelper.createBebe(b);
            Toast.makeText(getActivity(), "Bebê salvo!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameBebe, new ListarFragment()).commit();
        }
    }
}