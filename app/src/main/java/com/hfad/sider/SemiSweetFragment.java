package com.hfad.sider;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SemiSweetFragment extends Fragment {

    int maxInt; // Общее количество строк с сидром полученных из БД
    String[] ssweetNames;
    String[] ssweetType;
    String[] ssweetAlc;
    String[] ssweetDes;
    String[] ssweetCmp;
    String[] ssweetCom;
    Double[] ssweetPrc;
    Double[] ssweetScr;
    Double[] ssweetVol;
    Bitmap[] ssweetImg; // картинка, раскодированная в MainActivity
    int[] index;

    String[] type;
    int count;
    int ssweet;

    Sider sider;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView siderRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_semi_sweet, container, false);


        Log.v("test","Create");

        // Принимаем значения из MainActivity(из БД)
            ssweet = (((MainActivity) getActivity()).getSsweet());
            sider = (((MainActivity) getActivity()).getSider());
            maxInt = (((MainActivity) getActivity()).getMaxInt());


            // Инициализируем массивы
            ssweetNames = new String[ssweet];
            ssweetType = new String[ssweet];
            ssweetAlc = new String[ssweet];
            ssweetDes = new String[ssweet];
            ssweetCmp = new String[ssweet];
            ssweetCom = new String[ssweet];
            ssweetPrc = new Double[ssweet];
            ssweetScr = new Double[ssweet];
            ssweetVol = new Double[ssweet];
            ssweetImg = new Bitmap[ssweet];
            type = new String[maxInt];
            index = new int[ssweet];

            // Записываем полученные данные из MA в массивы
            int i;
            count = 0;
            for (i = 0; i < maxInt; i++) {

                type[i] = sider.getType(i);
                Log.v("test","Type " + type[i]);

                if (type[i].equals("Полусладкий")) {
                    Log.v("test","Poisk");


                    ssweetNames[count] = sider.getName(i);
                    ssweetType[count] = sider.getType(i);
                    ssweetAlc[count] = sider.getAlc(i);
                    ssweetDes[count] = sider.getDes(i);
                    ssweetCmp[count] = sider.getCmp(i);
                    ssweetCom[count] = sider.getCom(i);
                    ssweetPrc[count] = sider.getPrc(i);
                    ssweetScr[count] = sider.getScr(i);
                    ssweetVol[count] = sider.getVol(i);
                    ssweetImg[count] = sider.getImg(i);
                    index[count] = sider.getIndex(i);
                    count++;
                }

            }


            // Передаем данные в адаптер(Имя и картинку)
            CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(ssweetNames, ssweetImg);
            siderRecycler.setAdapter(adapter);
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

            adapter.setListener(new CaptionedImagesAdapter.Listener() {
                public void onClick(int position) {
                    Intent intent = new Intent(getActivity(), SiderDetailActivity.class);
                    intent.putExtra(SiderDetailActivity.EXTRA_NAMES, ssweetNames[position]); // Передаем имя по позиции в DetailActivity
                    intent.putExtra(SiderDetailActivity.EXTRA_TYPE, ssweetType[position]);
                    intent.putExtra(SiderDetailActivity.EXTRA_ALC, ssweetAlc[position]);
                    intent.putExtra(SiderDetailActivity.EXTRA_DES, ssweetDes[position]);
                    intent.putExtra(SiderDetailActivity.EXTRA_CMP, ssweetCmp[position]);
                    intent.putExtra(SiderDetailActivity.EXTRA_COM, ssweetCom[position]);
                    intent.putExtra(SiderDetailActivity.EXTRA_PRC, ssweetPrc[position]);
                    intent.putExtra(SiderDetailActivity.EXTRA_SCR, ssweetScr[position]);
                    intent.putExtra(SiderDetailActivity.EXTRA_VOL, ssweetVol[position]);
                    intent.putExtra(SiderDetailActivity.EXTRA_INT, position);
                    intent.putExtra(SiderDetailActivity.EXTRA_INDEX, index[position]);
                    getActivity().startActivity(intent);
                }
            });
            siderRecycler.setLayoutManager(layoutManager);

        return siderRecycler;
    }


}
