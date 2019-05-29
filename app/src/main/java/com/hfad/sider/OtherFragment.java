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

public class OtherFragment extends Fragment {

    int maxInt; // Общее количество строк с сидром полученных из БД
    String[] otherNames;
    String[] otherType;
    String[] otherAlc;
    String[] otherDes;
    String[] otherCmp;
    String[] otherCom;
    Double[] otherPrc;
    Double[] otherScr;
    Double[] otherVol;
    Bitmap[] otherImg; // картинка, раскодированная в MainActivity
    int[] index;

    String[] type;
    int count;
    int other;

    Sider sider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView siderRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_other, container, false);

        // Принимаем значения из MainActivity(из БД)
        sider = (((MainActivity) getActivity()).getSider());
        maxInt = (((MainActivity) getActivity()).getMaxInt());
        other = (((MainActivity) getActivity()).getOther());

        // Инициализируем массивы
        otherNames = new String[other];
        otherType = new String[other];
        otherAlc = new String[other];
        otherDes = new String[other];
        otherCmp = new String[other];
        otherCom = new String[other];
        otherPrc = new Double[other];
        otherScr = new Double[other];
        otherVol = new Double[other];
        otherImg = new Bitmap[other];
        type = new String[maxInt];
        index = new int[other];

        // Записываем полученные данные из MA в массивы
        int i;
        count = 0;
        for (i = 0; i < maxInt; i++) {
            type[i] = sider.getType(i);

            if (type[i].equals("Остальные")) {


                otherNames[count] = sider.getName(i);
                otherType[count] = sider.getType(i);
                otherAlc[count] = sider.getAlc(i);
                otherDes[count] = sider.getDes(i);
                otherCmp[count] = sider.getCmp(i);
                otherCom[count] = sider.getCom(i);
                otherPrc[count] = sider.getPrc(i);
                otherScr[count] = sider.getScr(i);
                otherVol[count] = sider.getVol(i);
                otherImg[count] = sider.getImg(i);
                index[count] = sider.getIndex(i);
                count++;
            }

        }


        // Передаем данные в адаптер(Имя и картинку)
        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(otherNames, otherImg);
        siderRecycler.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), SiderDetailActivity.class);
                intent.putExtra(SiderDetailActivity.EXTRA_NAMES, otherNames[position]); // Передаем имя по позиции в DetailActivity
                intent.putExtra(SiderDetailActivity.EXTRA_TYPE, otherType[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_ALC, otherAlc[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_DES, otherDes[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_CMP, otherCmp[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_COM, otherCom[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_PRC, otherPrc[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_SCR, otherScr[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_VOL, otherVol[position]);
                intent.putExtra(SiderDetailActivity.EXTRA_INT, position);
                intent.putExtra(SiderDetailActivity.EXTRA_INDEX, index[position]);
                getActivity().startActivity(intent);
            }
        });
        siderRecycler.setLayoutManager(layoutManager);

        return siderRecycler;
    }
}
