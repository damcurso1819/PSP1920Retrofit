package org.izv.ad.psp1920retrofit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.izv.ad.psp1920retrofit.model.data.Type;
import org.izv.ad.psp1920retrofit.view.MainViewAdapter;
import org.izv.ad.psp1920retrofit.view.MainViewModel;
import org.izv.ad.psp1920retrofit.view.settings.SettingsActivity;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "xyz";
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rvList = findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        final MainViewAdapter adapter = new MainViewAdapter(this);
        rvList.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.getLiveTypeList().observe(this, new Observer<List<Type>>() {
            @Override
            public void onChanged(@Nullable final List<Type> types) {
                adapter.setData(types);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*List<Type> typeList = viewModel.getTypeList();
                for(Type t: typeList) {
                    Log.v(TAG, t.toString());
                }*/
                Type type = new Type();
                Random r = new Random();
                type.setType("type " + r.nextInt(1000));
                viewModel.addType(type);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this /* Activity context */);
        String url = sharedPreferences.getString("url", "");
        viewModel.setUrl(url);
    }
}
