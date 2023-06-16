package br.com.dlweb.maternidade.bebe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.dlweb.maternidade.R;
import br.com.dlweb.maternidade.database.DatabaseHelper;
import br.com.dlweb.maternidade.mae.Mae;

public class BebeAdapter extends RecyclerView.Adapter<BebeAdapter.BebeViewHolder>{
    private final List<Bebe> bebes;
    private int id_bebe;
    private final FragmentActivity activity;

    BebeAdapter(List<Bebe> bebes, FragmentActivity activity){
        this.bebes = bebes;
        this.activity = activity;
    }

    static class BebeViewHolder extends RecyclerView.ViewHolder {
        private final TextView nomeView;
        private final TextView nomeMaeView;

        BebeViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeView = itemView.findViewById(R.id.tvListBebeNome);
            nomeMaeView = itemView.findViewById(R.id.tvListBebeMaeNome);
        }
    }

    @NonNull
    @Override
    public BebeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bebe_item, parent, false);
        return new BebeViewHolder(v);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BebeViewHolder viewHolder, int i) {
        viewHolder.nomeView.setText(bebes.get(i).getNome());
        DatabaseHelper databaseHelper = new DatabaseHelper(activity);
        Mae m = databaseHelper.getByIdMae(bebes.get(i).getId_mae());
        viewHolder.nomeMaeView.setText(m.getNome());
        id_bebe = bebes.get(i).getId();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt("id", id_bebe);

                EditarFragment editarFragment = new EditarFragment();
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                editarFragment.setArguments(b);
                ft.replace(R.id.frameBebe, editarFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bebes.size();
    }
}
