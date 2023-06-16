package br.com.dlweb.maternidade.medico;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.dlweb.maternidade.R;
import br.com.dlweb.maternidade.database.DatabaseHelper;

public class ListarFragment extends Fragment {

    public ListarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.medico_fragment_listar, container, false);
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        RecyclerView recyclerViewMedicos = v.findViewById(R.id.recyclerViewMedicos);

        LinearLayoutManager manager = new LinearLayoutManager(v.getContext());
        recyclerViewMedicos.setLayoutManager(manager);
        recyclerViewMedicos.addItemDecoration(new DividerItemDecoration(v.getContext(), LinearLayoutManager.VERTICAL));
        recyclerViewMedicos.setHasFixedSize(true);

        Cursor dataMedico = databaseHelper.getAllMedico();
        List<Medico> medicos = new ArrayList<Medico>();
        while (dataMedico.moveToNext()) {
            Medico m = new Medico();
            int idColumnIndex = dataMedico.getColumnIndex("_id");
            m.setId(Integer.parseInt(dataMedico.getString(idColumnIndex)));
            int nameColumnIndex = dataMedico.getColumnIndex("nome");
            m.setNome(dataMedico.getString(nameColumnIndex));
            int celularColumnIndex = dataMedico.getColumnIndex("celular");
            m.setCelular(dataMedico.getString(celularColumnIndex));
            medicos.add(m);
        }
        dataMedico.close();
        //No método getAllMedico() apenas abre a conexão
        databaseHelper.closeDBConnection();

        MedicoAdapter adapterMedicos = new MedicoAdapter(medicos, getActivity());
        recyclerViewMedicos.setAdapter(adapterMedicos);
        return v;
    }
}