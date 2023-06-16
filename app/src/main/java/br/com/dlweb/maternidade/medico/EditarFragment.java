package br.com.dlweb.maternidade.medico;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import br.com.dlweb.maternidade.R;
import br.com.dlweb.maternidade.database.DatabaseHelper;

public class EditarFragment extends Fragment {

    EditText etNome;
    EditText etEspecialidade;
    EditText etCelular;
    DatabaseHelper databaseHelper;
    Medico m;

    public EditarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.medico_fragment_editar, container, false);
        Bundle b = getArguments();
        int id_medico = b.getInt("id");
        databaseHelper = new DatabaseHelper(getActivity());
        m = databaseHelper.getByIdMedico(id_medico);

        etNome = v.findViewById(R.id.editTextNome);
        etEspecialidade = v.findViewById(R.id.editTextEspecialidade);
        etCelular = v.findViewById(R.id.editTextCelular);

        etNome.setText(m.getNome());
        etEspecialidade.setText(m.getEspecialidade());
        etCelular.setText(m.getCelular());

        Button btnEditar = v.findViewById(R.id.buttonEditar);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(id_medico);
            }
        });

        Button btnExcluir = v.findViewById(R.id.buttonExcluir);

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.dialog_excluir_medico);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir(id_medico);
                    }
                });
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Não faz nada
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return v;
    }

    private void editar (int id) {
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (etEspecialidade.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a especialidade!", Toast.LENGTH_LONG).show();
        } else if (etCelular.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o celular!", Toast.LENGTH_LONG).show();
        } else {
            m = new Medico();
            m.setId(id);
            m.setNome(etNome.getText().toString());
            m.setEspecialidade(etEspecialidade.getText().toString());
            m.setCelular(etCelular.getText().toString());
            databaseHelper.updateMedico(m);
            Toast.makeText(getActivity(), "Médico editado!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMedico, new ListarFragment()).commit();
        }
    }

    private void excluir (int id) {
        m = new Medico();
        m.setId(id);
        databaseHelper.deleteMedico(m);
        Toast.makeText(getActivity(), "Médico excluído!", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMedico, new ListarFragment()).commit();
    }
}