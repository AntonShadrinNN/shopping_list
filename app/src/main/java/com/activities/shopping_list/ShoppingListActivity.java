package com.activities.shopping_list;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    private List<ShoppingItem> shoppingItems;
    private ShoppingListAdapter adapter;
    private EditText newItemEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        shoppingItems = new ArrayList<>();
        adapter = new ShoppingListAdapter(this, shoppingItems);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        newItemEditText = findViewById(R.id.newItemEditText);

        ImageButton addItemButton = findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(view -> addItem());
    }
    @SuppressLint("NotifyDataSetChanged")
    private void addItem() {
        String itemName = newItemEditText.getText().toString().trim();
        if (!itemName.isEmpty()) {
            ShoppingItem newItem = new ShoppingItem(itemName);
            shoppingItems.add(newItem);
            adapter.notifyDataSetChanged();
            newItemEditText.getText().clear();
        }
    }

}
