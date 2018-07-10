package io.github.haanjiankur.todoapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        final String list = bundle.getString("list");
        setTitle(list);
        ImageButton button = (ImageButton)findViewById(R.id.button2);
        final EditText editText = (EditText)findViewById(R.id.editText2);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(SecondActivity.this));
        final DBManager dbManager = new DBManager(SecondActivity.this,null,null,1);
        final String[] strings;
        strings = dbManager.showItems(list);
        RecyclerViewAdapterSecond recyclerViewAdapter = new RecyclerViewAdapterSecond(list,strings);

        recyclerView.setAdapter(recyclerViewAdapter);
        registerForContextMenu(recyclerView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().trim().length() > 0) {
                    if (dbManager.addItem(list,editText.getText().toString().trim()) == true)
                        ;
                    else {
                        final Toast toast = Toast.makeText(SecondActivity.this, "Item is already added.", Toast.LENGTH_SHORT);
                        toast.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toast.cancel();
                            }
                        }, 800);
                    }
                    editText.setText("");
                    String[] strings = dbManager.showItems(list);
                    RecyclerViewAdapterSecond recyclerViewAdapter = new RecyclerViewAdapterSecond(list,strings);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    registerForContextMenu(recyclerView);
                }
                else{
                    final Toast toast = Toast.makeText(SecondActivity.this, "Enter item name first!", Toast.LENGTH_SHORT);
                    toast.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 800);
                    editText.setText("");
                }
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Toast toast = Toast.makeText(SecondActivity.this,"Enter the name and touch to add the item.",Toast.LENGTH_SHORT);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 800);
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
    }
}