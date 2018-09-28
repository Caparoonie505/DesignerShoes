package com.example.capar.designershoes;

import android.gesture.Gesture;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class fragmentMensActive extends Fragment {
    private static final String TAG = "fragmentMensActive";

    static String web_url = "https://www.academy.com/shop/browse/footwear/mens-footwear/mens-athletic-sneakers";
    static int item = 0;
    TextView text;

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> images = new ArrayList<>();
    ArrayList<String> prices = new ArrayList<>();

    private Button btnReturn;
    private Button resetButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mens_active,container,false);

        new fragmentMensActive.UpdateProductInfo().execute();
        updateSettingList();


        btnReturn = (Button) view.findViewById(R.id.returnButton);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Returning to Menu...", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setViewPager(0);
            }
        });

        resetButton = (Button) view.findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Returning to first search item...", Toast.LENGTH_SHORT).show();
                item = 0;
                new fragmentMensActive.UpdateProductInfo().execute();
            }
        });

        return view;
    }

    private void initRecyclerView(View view){
        Log.d(TAG, "initRecyclerView: init RecyclerView");
        RecyclerView recyclerView = view.findViewById(R.id.mensCasualRecyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(names,images,prices,this.getContext());
        recyclerView.setAdapter(adapter);
        //Snap to center of products
        SnapHelper helper = new LinearSnapHelper();
        recyclerView.setOnFlingListener(null);
        helper.attachToRecyclerView(recyclerView);
        //Set recyclerView to layout as horizontal recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,true));
    }

    private void updateSettingList(){



    }

    //TEST IMPLEMENTATION OF TAKING TEXT FROM WEBSITE
    public class UpdateProductInfo extends AsyncTask<Void,Void,Void>{

        ArrayList<Element> tempPrices = new ArrayList<>();
        String LSize;

        @Override
        protected Void doInBackground(Void... voids) {
            Document doc;
            Element list;
            Element prodTitle;
            Element prodImg;
            String prodPrice = "";
            Element listSize;
            Elements prodPrices;
            String src;
            for(item = 0; item < 30; item++) {
                try {
                    doc = Jsoup.connect(web_url).get();
                    list = doc.getElementsByAttributeValue("id", "productCardListing").first();
                    list = list.getElementsByTag("a").get(item);
                    prodImg = list.getElementsByTag("img").first();
                    src = prodImg.attr("src");
                    if (!src.startsWith("https:")) {
                        src = "https:" + src;
                    }
                    //Gather size of product list for future implementation of dynamic information gathering
                    listSize = doc.getElementsByAttributeValueContaining("data-auid","listingPagination_pageInfo").get(0);
                    LSize = listSize.text();

                    prodTitle = list.getElementsByAttributeValueContaining("class", "product__description").get(0);
                    try {
                        prodPrices = list.getElementsByAttributeValueContaining("class", "c-price__sub");
                        if(prodPrices.size() == 0){
                            throw new Exception("Only one price value");
                        }
                        for(int i = 0; i < prodPrices.size(); i++){
                            tempPrices.add(prodPrices.get(i));
                            if(i == 0){
                                prodPrice = tempPrices.get(i).text();
                                String[] tempBreak = prodPrice.split("");
                                prodPrice = "";
                                for(int j = 0; j < tempBreak.length; j++){
                                    if(!(j == tempBreak.length-2)){
                                        prodPrice = prodPrice.concat(tempBreak[j]);
                                    }else{
                                        prodPrice = prodPrice.concat("." + tempBreak[j]);

                                    }
                                }
                            }
                            else if(i == 1){
                                if(list.getElementsByAttributeValueContaining("class","list-price").size() == 0) {
                                    prodPrice = prodPrice + " - " + tempPrices.get(i).text();
                                    String[] tempBreak = prodPrice.split("");
                                    prodPrice = "";
                                    for(int j = 0; j < tempBreak.length; j++){
                                        if(!(j == tempBreak.length-2)){
                                            prodPrice = prodPrice.concat(tempBreak[j]);
                                        }else{
                                            prodPrice = prodPrice.concat("." + tempBreak[j]);
                                        }
                                    }
                                }
                            }
                        }
                    }catch(Exception e){
                        prodPrice = list.getElementsByAttributeValueContaining("class", "price-compare").text();
                    }

                    names.add(prodTitle.text());
                    prices.add(prodPrice);
                    images.add(src);

                    tempPrices.clear();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            initRecyclerView(getView());
        }
    }
}
