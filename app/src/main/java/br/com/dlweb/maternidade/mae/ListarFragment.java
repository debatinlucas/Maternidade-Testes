package br.com.dlweb.maternidade.mae;

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
        View v = inflater.inflate(R.layout.mae_fragment_listar, container, false);
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        RecyclerView recyclerViewMaes = v.findViewById(R.id.recyclerViewMaes);

        LinearLayoutManager manager = new LinearLayoutManager(v.getContext());
        recyclerViewMaes.setLayoutManager(manager);
        recyclerViewMaes.addItemDecoration(new DividerItemDecoration(v.getContext(), LinearLayoutManager.VERTICAL));
        recyclerViewMaes.setHasFixedSize(true);

        Cursor dataMae = databaseHelper.getAllMae();
        List<Mae> maes = new ArrayList<Mae>();
        while (dataMae.moveToNext()) {
            Mae m = new Mae();
            int idColumnIndex = dataMae.getColumnIndex("_id");
            m.setId(Integer.parseInt(dataMae.getString(idColumnIndex)));
            int nameColumnIndex = dataMae.getColumnIndex("nome");
            m.setNome(dataMae.getString(nameColumnIndex));
            int celularColumnIndex = dataMae.getColumnIndex("celular");
            m.setCelular(dataMae.getString(celularColumnIndex));
            maes.add(m);
        }
        dataMae.close();
        //No método getAllMae() apenas abre a conexão
        databaseHelper.closeDBConnection();

        MaeAdapter adapterMaes = new MaeAdapter(maes, getActivity());
        recyclerViewMaes.setAdapter(adapterMaes);
        return v;
    }
}