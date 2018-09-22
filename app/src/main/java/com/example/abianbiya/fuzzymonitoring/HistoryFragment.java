package com.example.abianbiya.fuzzymonitoring;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<Entry> entries;
    List<Entry> entries2;
    LineChart chart;

    private List<Data> dataList;
    private DataAdapter adapter;
    private RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        dataList = new ArrayList<>();

        recyclerView = v.findViewById(R.id.recycler_view);

        queryAPI();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        chart =  v.findViewById(R.id.chart);
        Description des = new Description();
        des.setText("Grafik Monitoring");
        chart.setDescription(des);

        entries = new ArrayList<Entry>();
        entries2 = new ArrayList<Entry>();

        return v;
    }

    public void queryAPI(){
        APIInterface service = APIClient.getClient();
        final Gson gson = new Gson();


        Call<ResponseBody> call = service.listData();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String respon = response.body().string();

                        JSONObject json = new JSONObject(respon);

                        String api_status = json.getString("api_message");
                        Log.d("status", respon);

                        if (api_status.equals("sukses")) {
                            int i;

                            JSONArray item = json.getJSONArray("data");
                            for (i=0; i<item.length(); i++) {
                                JSONObject obj = item.getJSONObject(i);
                                Data data = gson.fromJson(obj.toString(), Data.class);
                                dataList.add(data);

//                                entries.add(new Entry(Float.valueOf(data.getDateTime()), Float.valueOf(data.getSuhu())));
                                entries.add(new Entry(i, Float.valueOf(data.getSuhu())));
                                entries2.add(new Entry(i, Float.valueOf(data.getPh())));
                            }

                            LineDataSet dataSet = new LineDataSet(entries, "Suhu");
                            dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                            dataSet.setColor(getResources().getColor(android.R.color.black));
//                            dataSet.setColors(ColorTemplate.PASTEL_COLORS);
                            dataSet.setCircleColor(getResources().getColor(android.R.color.black));
//                            dataSet.setDrawCircleHole(false);

                            LineDataSet dataSet2 = new LineDataSet(entries2, "pH");
                            dataSet2.setAxisDependency(YAxis.AxisDependency.LEFT);
                            dataSet2.setColor(getResources().getColor(android.R.color.holo_red_light));
//                            dataSet2.setColors(ColorTemplate.MATERIAL_COLORS);
                            dataSet2.setCircleColor(getResources().getColor(android.R.color.holo_red_light));


                            List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(dataSet);
                            dataSets.add(dataSet2);

                            LineData lineData = new LineData(dataSets);
                            chart.setData(lineData);
                            chart.setX(1);
                            final XAxis axis = chart.getXAxis();

                            axis.setValueFormatter(new IAxisValueFormatter() {
                                @Override
                                public String getFormattedValue(float value, AxisBase axiss) {
                                    return dataList.get(Math.round(value)).getTgl();
                                }
                            });
                            chart.invalidate();
                            adapter = new DataAdapter(getContext(), dataList);
                            recyclerView.setAdapter(adapter);
                        } else{
                            Toast.makeText(getContext(), "Gagal mengambil data!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(),"Tidak ada respon.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Data Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
