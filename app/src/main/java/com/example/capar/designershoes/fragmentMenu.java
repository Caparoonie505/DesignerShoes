package com.example.capar.designershoes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class fragmentMenu extends Fragment {
    private static final String TAG = "fragmentMenu";

    private Button btnCatMensCasual;
    private Button btnCatMensFancy;
    private Button btnCatMensActive;
    private Button btnCatWomenCasual;
    private Button btnCatWomenFancy;
    private Button btnCatWomenActive;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu,container,false);

        btnCatMensCasual = (Button) view.findViewById(R.id.categoryButtonCasualMens);
        btnCatMensFancy = (Button) view.findViewById(R.id.categoryButtonDressMens);
        btnCatMensActive = (Button) view.findViewById(R.id.categoryButtonActiveMens);

        btnCatWomenCasual = (Button) view.findViewById(R.id.categoryButtonCasualWomens);
        btnCatWomenFancy = (Button) view.findViewById(R.id.categoryButtonDressWomens);
        btnCatWomenActive = (Button) view.findViewById(R.id.categoryButtonActiveWomens);

        btnCatMensFancy.setVisibility(View.INVISIBLE);
        btnCatWomenFancy.setVisibility(View.INVISIBLE);

        btnCatMensCasual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Men's Casual Clicked!", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setViewPager(1);
            }
        });
        btnCatMensFancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Men's Fancy Clicked!", Toast.LENGTH_SHORT).show();
//                ((MainActivity)getActivity()).setViewPager(2);
            }
        });
        btnCatMensActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Men's Active Clicked!", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setViewPager(2);
            }
        });

        btnCatWomenCasual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Women's Casual Clicked!", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setViewPager(3);
            }
        });
        btnCatWomenFancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Women's Fancy Clicked!", Toast.LENGTH_SHORT).show();
//                ((MainActivity)getActivity()).setViewPager(5);
            }
        });
        btnCatWomenActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Women's Active Clicked!", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setViewPager(4);
            }
        });

        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CustomViewPager mViewPager = (CustomViewPager) view.findViewById(R.id.container);
        mViewPager.setAdapter(new SectionStatePageAdapter(getChildFragmentManager()));
    }
}
