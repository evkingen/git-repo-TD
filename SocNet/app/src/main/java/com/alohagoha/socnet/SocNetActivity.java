package com.alohagoha.socnet;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SocNetActivity extends AppCompatActivity {

    private String cities[] = {"Moscow","Saint-Petersburg","Samara","Kazan","Lubertsy","Rostov","Novgorod","Kaliningrad","Ekaterinburg","Sochi","Saransk"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soc_net);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(getResources());
        SocNetAdapter socNetAdapter = new SocNetAdapter(dataSourceBuilder.build());
        recyclerView.setAdapter(socNetAdapter);

        final Activity that = this;

        socNetAdapter.setListener(new SocNetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(that,String.format("Выбранный город - %s",cities[position]),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
