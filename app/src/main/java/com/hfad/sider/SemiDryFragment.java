package com.hfad.sider;

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


public class SemiDryFragment extends Fragment {

    int maxInt; // Общее количество строк с сидром полученных из БД
    String[] sdryNames;
    String[] sdryType;
    String[] sdryAlc;
    String[] sdryDes;
    String[] sdryCmp;
    String[] sdryCom;
    Double[] sdryPrc;
    Double[] sdryScr;
    Double[] sdryVol;
    Bitmap[] sdryImg; // картинка, раскодированная в MainActivity
    int[] index;

    String[] type;
    int count;
    int sdry;

    Sider sider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView siderRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_semi_dry, container, false);

        // Принимаем значения из MainActivity(из БД)
            sdry = (((MainActivity) getActivity()).getSdry());
            sider = (((MainActivity) getActivity()).getSider());
            maxInt = (((MainActivity) getActivity()).getMaxInt());


            // Инициализируем массивы
            sdryNames = new String[sdry];
            sdryType = new String[sdry];
            sdryAlc = new String[sdry];
            sdryDes = new String[sdry];
            sdryCmp = new String[sdry];
            sdryCom = new String[sdry];
            sdryPrc = new Double[sdry];
            sdryScr = new Double[sdry];
            sdryVol = new Double[sdry];
            sdryImg = new Bitmap[sdry];
            type = new String[maxInt];
            index = new int[sdry];

            // Записываем полученные данные из MA в массивы
            int i;
            count = 0;
            for (i = 0; i < maxInt; i++) {
                type[i] = sider.getType(i);

                if (type[i].equals("Полусухой")) {



                    sdryNames[count] = sider.getName(i);
                    sdryType[count] = sider.getType(i);
                    sdryAlc[count] = sider.getAlc(i);
                    sdryDes[count] = sider.getDes(i);
                    sdryCmp[count] = sider.getCmp(i);
                    sdryCom[count] = sider.getCom(i);
                    sdryPrc[count] = sider.getPrc(i);
                    sdryScr[count] = sider.getScr(i);
                    sdryVol[count] = sider.getVol(i);
                    sdryImg[count] = sider.getImg(i);
                    index[count] = sider.getIndex(i);
                    count++;
                }

            }


            // Передаем данные в адаптер(Имя и картинку)
            CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(sdryNames, sdryImg);

            siderRecycler.setAdapter(adapter);
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

            adapter.setListener(new CaptionedImagesAdapter.Listener() {
                public void onClick(int position) {
                    Intent intent = new Intent(getActivity(), SiderDetailActivity.class);
                    intent.putExtra(SiderDetailActivity.EXTRA_NAMES, sdryNames[position]); // Передаем имя по позиции в DetailActivity
                    intent.putExtra(SiderDetailActivity.EXTRA_TYPE, sdryType[position]);
                    intent.putExtra(SiderDetailActivity.EXTRA_ALC, sdryAlc[position]);
                    intent.putExtra(SiderDetailActivity.EXTRA_DES, sdryDes[position]);
                    intent.putExtra(SiderDetailActivity.EXTRA_CMP, sdryCmp[position]);
                    intent.putExtra(SiderDetailActivity.EXTRA_COM, sdryCom[position]);
                    intent.putExtra(SiderDetailActivity.EXTRA_PRC, sdryPrc[position]);
                    intent.putExtra(SiderDetailActivity.EXTRA_SCR, sdryScr[position]);
                    intent.putExtra(SiderDetailActivity.EXTRA_VOL, sdryVol[position]);
                    intent.putExtra(SiderDetailActivity.EXTRA_INT, position);
                    intent.putExtra(SiderDetailActivity.EXTRA_INDEX, index[position]);
                    getActivity().startActivity(intent);
                }
            });
            siderRecycler.setLayoutManager(layoutManager);

        return siderRecycler;
    }
}


