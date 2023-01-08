package com.example.fifafixture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.fifafixture.manager.RequestManager;
import com.example.fifafixture.manager.adapter.FixtureAdapter;
import com.example.fifafixture.model.FixtureResponse;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerView;
ProgressDialog dialog;
RequestManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycler_fixture);

        dialog=new ProgressDialog(this);
        dialog.setTitle("Loading....");

        manager=new RequestManager(this);
        manager.getFixture(listener,1331);
        dialog.show();
    }
    private final ResponseListener listener=new ResponseListener() {
        @Override
        public void didFetch(FixtureResponse response, String massage) {
        dialog.dismiss();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
            FixtureAdapter adapter=new FixtureAdapter(MainActivity.this,response.data);
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void didError(String massage) {
            dialog.dismiss();
            Toast.makeText(MainActivity.this,"massage",Toast.LENGTH_LONG).show();
        }
    };
}