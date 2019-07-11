package com.example.database;

import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Phone> phoneList;
    private EditText nameIn;
    private EditText modelIn;
    private EditText description;
    private TextView nameOut;
    private TextView modelOut;
    private TextView descriptionOut;
    private Button addItem;
    private Button deleteItem;
    private Button nextItem;
    private Button prevItem;
    private Button showItems;
    DatabaseAdapter databaseAdapter;

    private int currentValue;
    private int maxValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializationOfElements();
        initializationOfOnClickListeners();
    }

    protected void initializationOfElements(){
        nameIn = (EditText) findViewById(R.id.nameIn);
        modelIn = (EditText) findViewById(R.id.modelIn);
        description = (EditText) findViewById(R.id.descriptionIn);

        nameOut = (TextView) findViewById(R.id.nameOut);
        modelOut = (TextView) findViewById(R.id.modelOut);
        descriptionOut = (TextView) findViewById(R.id.descriptionOut);

        addItem = (Button) findViewById(R.id.add);
        deleteItem = (Button) findViewById(R.id.delete);
        nextItem = (Button) findViewById(R.id.next);
        prevItem = (Button) findViewById(R.id.prev);
        showItems = (Button) findViewById(R.id.showItems);

        databaseAdapter = new DatabaseAdapter(this);

        phoneList = new ArrayList<>();
    }

    public void addItemToDatabase(){
        Phone phone = new Phone(0, nameIn.getText().toString(), modelIn.getText().toString(), description.getText().toString());
        databaseAdapter.insertPhone(phone);
        getItemsFromDatabase();
    }

    public void getItemsFromDatabase(){
        phoneList.clear();
        SQLiteCursor cursor = databaseAdapter.getData();
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                phoneList.add(new Phone(Long.parseLong(cursor.getString(0)),cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }
        }

        maxValue = phoneList.size();
        currentValue = 0;
        if(maxValue != 0)
            showItem(phoneList.get(0));
        else{
            showItem(null);
        }
    }

    public void showItem(Phone phone){
        if(phone != null) {
            nameOut.setText(phone.getName());
            modelOut.setText(phone.getModel());
            descriptionOut.setText(phone.getDescription());
        }else{
            nameOut.setText("Brak danych");
            modelOut.setText("Brak danych");
            descriptionOut.setText("Brak danych");
        }
    }

    public void deleteItem(String id){
        databaseAdapter.deleteData(id);
    }

    protected void initializationOfOnClickListeners(){
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.add:
                        addItemToDatabase();
                        Toast.makeText(MainActivity.this,"Dodano telefon do bazy danych.", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.prev:
                        currentValue--;
                        if(currentValue < 0) currentValue = maxValue-1;
                        if(maxValue != 0)
                            showItem(phoneList.get(currentValue));
                        else showItem(null);
                        break;
                    case R.id.next:
                        currentValue++;
                        if(currentValue >= maxValue) currentValue = 0;
                        if(maxValue != 0)
                            showItem(phoneList.get(currentValue));
                        else showItem(null);
                        break;
                    case R.id.delete:
                        if(maxValue != 0) {
                            deleteItem(Long.toString(phoneList.get(currentValue).getId()));
                            getItemsFromDatabase();
                            Toast.makeText(MainActivity.this, "Usunięto telefon z bazy danych.", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.showItems:
                        getItemsFromDatabase();
                        Toast.makeText(MainActivity.this,"Pobrano dane z bazy danych." ,Toast.LENGTH_LONG).show();
                        break;
                    default: break;
                }
            }
        };
        addItem.setOnClickListener(listener);
        nextItem.setOnClickListener(listener);
        prevItem.setOnClickListener(listener);
        deleteItem.setOnClickListener(listener);
        showItems.setOnClickListener(listener);
    }

}
