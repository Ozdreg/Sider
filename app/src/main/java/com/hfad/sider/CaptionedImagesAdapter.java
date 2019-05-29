package com.hfad.sider;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

class CaptionedImagesAdapter extends
        RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder>{

    // Переменные для названий и идентификаторов графических ресурсов.
    private String[] captions;
    //private int[] imageIds;
    private Listener listener;
    Bitmap[] bitmap;

    interface Listener {
        void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        // Каждый объект ViewHolder отображает CardView.
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    // Данные передаются адаптеру в конструкторе.
    public CaptionedImagesAdapter(String[] captions, Bitmap[] bitmap){
        this.captions = captions;
        this.bitmap = bitmap;
    }

    // Количество элементов данных
    @Override
    public int getItemCount(){
        return captions.length;
    }

    // Активности и фрагменты используют этот метод для регистрации себя в качестве слушателя.
    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType){
        // Использовать макет для представлений CardView.
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cv);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView)cardView.findViewById(R.id.info_image);
        //Drawable drawable =
            //    ContextCompat.getDrawable(cardView.getContext(), imageIds[position]);
        // Заполнить данными компоненты ImageView и TextView внутри CardView
        //imageView.setImageDrawable(drawable);
        imageView.setImageBitmap(bitmap[position]);
        imageView.setContentDescription(captions[position]);
        TextView textView = (TextView)cardView.findViewById(R.id.info_text);
        textView.setText(captions[position]);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }
}