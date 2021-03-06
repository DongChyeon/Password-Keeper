package com.dongchyeon.passwordkeeper.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dongchyeon.passwordkeeper.R;
import com.dongchyeon.passwordkeeper.database.entity.Item;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> implements OnItemClickListener {
    private List<Item> items = new ArrayList<>();
    OnItemClickListener listener;

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<Item> getItems() { return items; }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder viewHolder, int position) {
        viewHolder.onBind(items.get(position), position);
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) throws NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView icon;

        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.titleText);

            view.setOnClickListener(view1 -> {
                int position = getAdapterPosition();
                if (listener != null) {
                    try {
                        listener.onItemClick(ViewHolder.this, view1, position);
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidAlgorithmParameterException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public void onBind(Item item, int position) {
            title.setText(((Item)item).getTitle());
        }
    }

    public void setItems(List<Item> data) {
        items = data;
        notifyDataSetChanged();
    }

    public Item getItem(int position) {
        return items.get(position);
    }
}
