package com.dx.demi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dx.demi.R;
import com.dx.demi.UrlService;
import com.dx.demi.bean.ListInfo;
import com.dx.demi.bean.Profits;
import com.dx.demi.bean.ResponseData;
import com.dx.demi.factory.ProfitsConverterFactory;
import com.dx.demi.view.ProfitsChartView;
import com.dx.demi.view.TabContainer;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by demi on 2017/5/16.
 */

public class ProfitsChartFragment extends Fragment {
    private TabContainer tab;
    private ProfitsChartView chart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profit_chart, container, false);
        tab = (TabContainer) view.findViewById(R.id.tab);
        chart = (ProfitsChartView) view.findViewById(R.id.chart);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDatas();
    }


    public void getDatas() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fe-api.zixuangu8.com/stock-portfolios/")
                .addConverterFactory(new ProfitsConverterFactory())
                .client(okHttpClient)
                .build();
        UrlService service = retrofit.create(UrlService.class);
        Call<ResponseData<ListInfo<Profits>>> call = service.getDatas("520");
        call.enqueue(new Callback<ResponseData<ListInfo<Profits>>>() {
            @Override
            public void onResponse(Call<ResponseData<ListInfo<Profits>>> call, Response<ResponseData<ListInfo<Profits>>> response) {
                // JSONObject js =new JSONObject(json);
                chart.setData(response.body().getInfo());
            }

            @Override
            public void onFailure(Call<ResponseData<ListInfo<Profits>>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
