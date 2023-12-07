package com.activities.shopping_list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
    private final List<ShoppingItem> shoppingItems;
    private final Context context;

    public ShoppingListAdapter(Context context, List<ShoppingItem> shoppingItems) {
        this.context = context;
        this.shoppingItems = shoppingItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping_list, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.itemView.setOnLongClickListener(view1 -> {
            int position = viewHolder.getAdapterPosition();
            showEditDialog(shoppingItems.get(position), position);
            return true;
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final ShoppingItem item = shoppingItems.get(position);
        holder.itemNameTextView.setText(item.getItemName());

        holder.itemView.setOnClickListener(view -> showEditDialog(item, position));
    }

    private void showEditDialog(final ShoppingItem item, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Item");

        final EditText editText = new EditText(context);
        editText.setText(item.getItemName());
        builder.setView(editText);

        builder.setPositiveButton("Save", (dialogInterface, i) -> {
            String newItemName = editText.getText().toString().trim();
            if (!newItemName.isEmpty()) {
                item.setItemName(newItemName);
                notifyItemChanged(position);
            }
        });

        builder.setNegativeButton("Delete", (dialogInterface, i) -> showDeleteDialog(position));

        builder.setNeutralButton("Cancel", null);

        builder.show();
    }

    private void showDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Item");
        builder.setMessage("Are you sure you want to delete this item?");

        builder.setPositiveButton("Yes", (dialogInterface, i) -> deleteItem(position));

        builder.setNegativeButton("No", null);

        builder.show();
    }

    private void deleteItem(int position) {
        shoppingItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return shoppingItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
        }
    }
}
