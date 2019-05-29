package com.hfad.sider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SweetFragment extends Fragment {

    int maxInt; // Общее количество строк с сидром полученных из БД
    String[] sweetNames;
    String[] sweetType;
    String[] sweetAlc;
    String[] sweetDes;
    String[] sweetCmp;
    String[] sweetCom;
    Double[] sweetPrc;
    Double[] sweetScr;
    Double[] sweetVol;
    Bitmap[] sweetImg; // картинка, раскодированная в MainActivity
    int[] index;

    String[] type;
    int count;
    int sweet;

    Sider sider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView siderRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_sweet, container, false);

        // Принимаем значения из MainActivity(из БД)
        sider = (((MainActivity) getActivity()).getSider());
        maxInt = (((MainActivity) getActivity()).getMaxInt());
        sweet = (((MainActivity) getActivity()).getSweet());

        // Инициализируем массивы
        sweetNames = new String[sweet];
        sweetType = new String[sweet];
        sweetAlc = new String[sweet];
        sweetDes = new String[sweet];
        sweetCmp = new String[sweet];
        sweetCom = new String[sweet];
        sweetPrc = new Double[sweet];
        sweetScr = new Double[sweet];
        sweetVol = new Double[sweet];
        sweetImg = new Bitmap[sweet];
        type = new String[maxInt];
        index = new int[sweet];

        // Записываем полученные данные из MA в массивы
        int i;
        count = 0;
        for (i = 0; i < maxInt; i++) {
            type[i] = sider.getType(i);

            if (type[i].equals("Сладкий")) {


                sweetNames[count] = sider.getName(i);
                sweetType[count] = sider.getType(i);
                sweetAlc[count] = sider.getAlc(i);
                sweetDes[count] = sider.getDes(i);
                sweetCmp[count] = sider.getCmp(i);
                sweetCom[count] = sider.getCom(i);
                sweetPrc[count] = sider.getPrc(i);
                sweetScr[count] = sider.getScr(i);
                sweetVol[count] = sider.getVol(i);
                sweetImg[count] = sider.getImg(i);
                index[count] = sider.getIndex(i);
                count++;
            }

        }


        // Передаем данные в адаптер(Имя и картинку)
        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(sweetNames, sweetImg);
        siderRecycler.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), SiderDetailActivity.class);
                intent.putExtra(SiderDetailActivity.EXTRA_NAMES, sweetNames[position]); // Передаем имя по позиции в DetailActivity
                intent.putExtra(SiderDetailActivity.EXTRA_TYPE, sweetType[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_ALC, sweetAlc[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_DES, sweetDes[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_CMP, sweetCmp[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_COM, sweetCom[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_PRC, sweetPrc[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_SCR, sweetScr[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_VOL, sweetVol[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_INT, position);
                intent.putExtra(SiderDetailActivity.EXTRA_INDEX, index[position]);
                getActivity().startActivity(intent);
            }
        });
        siderRecycler.setLayoutManager(layoutManager);

        return siderRecycler;
    }
}



