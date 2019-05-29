package com.hfad.sider;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.GridLayoutManager;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;


public class DryFragment extends Fragment {

    int maxInt; // Общее количество строк с сидром полученных из БД
    String[] dryNames;
    String[] dryType;
    String[] dryAlc;
    String[] dryDes;
    String[] dryCmp;
    String[] dryCom;
    Double[] dryPrc;
    Double[] dryScr;
    Double[] dryVol;
    Bitmap[] dryImg; // картинка, раскодированная в MainActivity
    int[] index;

    String[] type;
    int count;
    int dry;

    Sider sider;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView siderRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_dry, container, false);

        // Принимаем значения из MainActivity(из БД)
        sider = (((MainActivity) getActivity()).getSider());
        maxInt = (((MainActivity) getActivity()).getMaxInt());
        dry = (((MainActivity) getActivity()).getDry());

        // Инициализируем массивы
        dryNames = new String[dry];
        dryType = new String[dry];
        dryAlc = new String[dry];
        dryDes = new String[dry];
        dryCmp = new String[dry];
        dryCom = new String[dry];
        dryPrc = new Double[dry];
        dryScr = new Double[dry];
        dryVol = new Double[dry];
        dryImg = new Bitmap[dry];
        type = new String[maxInt];
        index = new int[dry];

        // Записываем полученные данные из MA в массивы
        int i;
        count = 0;
        for (i = 0; i < maxInt; i++) {
            type[i] = sider.getType(i);

            if (type[i].equals("Сухой")) {


                dryNames[count] = sider.getName(i);
                dryType[count] = sider.getType(i);
                dryAlc[count] = sider.getAlc(i);
                dryDes[count] = sider.getDes(i);
                dryCmp[count] = sider.getCmp(i);
                dryCom[count] = sider.getCom(i);
                dryPrc[count] = sider.getPrc(i);
                dryScr[count] = sider.getScr(i);
                dryVol[count] = sider.getVol(i);
                dryImg[count] = sider.getImg(i);
                index[count] = sider.getIndex(i);
                count++;
            }

        }


        // Передаем данные в адаптер(Имя и картинку)
        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(dryNames, dryImg);
        siderRecycler.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), SiderDetailActivity.class);
                intent.putExtra(SiderDetailActivity.EXTRA_NAMES, dryNames[position]); // Передаем имя по позиции в DetailActivity
                intent.putExtra(SiderDetailActivity.EXTRA_TYPE, dryType[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_ALC, dryAlc[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_DES, dryDes[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_CMP, dryCmp[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_COM, dryCom[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_PRC, dryPrc[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_SCR, dryScr[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_VOL, dryVol[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_INT, position);
                intent.putExtra(SiderDetailActivity.EXTRA_INDEX, index[position]);
                getActivity().startActivity(intent);
            }
        });
        siderRecycler.setLayoutManager(layoutManager);

        return siderRecycler;
    }
}
