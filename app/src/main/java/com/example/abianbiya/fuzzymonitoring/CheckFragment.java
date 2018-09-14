package com.example.abianbiya.fuzzymonitoring;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
 * {@link CheckFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CheckFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CheckFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckFragment newInstance(String param1, String param2) {
        CheckFragment fragment = new CheckFragment();
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
        View v = inflater.inflate(R.layout.fragment_check, container, false);

        final Button cek = v.findViewById(R.id.btn_hitung);
        final TextView tanggal = v.findViewById(R.id.timestamp);
        final TextView ph = v.findViewById(R.id.val_ph);
        final TextView suhu = v.findViewById(R.id.val_suhu);
        final TextView simpulan = v.findViewById(R.id.val_simpulan);

        final ProgressBar progph = v.findViewById(R.id.prog_ph);
        final ProgressBar progsuhu = v.findViewById(R.id.prog_suhu);

        APIInterface service = APIClient.getClient();
        final Gson gson = new Gson();
        Call<ResponseBody> call = service.dataSatuan();
        call.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
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

                            JSONArray items = json.getJSONArray("data");

                            JSONObject item = items.getJSONObject(0);


                            Data data = gson.fromJson(item.toString(), Data.class);

                            tanggal.setText(data.getDateTime());
                            ph.setText(data.getPh());
                            suhu.setText(data.getSuhu());
                            simpulan.setText(data.getKesimpulan());

                            progph.setMax(14);
                            progph.setProgress(Math.round(Float.valueOf(data.getPh())));

                            progsuhu.setMax(35);
                            progsuhu.setProgress(Math.round(Float.valueOf(data.getSuhu())));

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



        cek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIInterface service = APIClient.getClient();
                final Gson gson = new Gson();
                Call<ResponseBody> call = service.dataSatuan();
                call.enqueue(new Callback<ResponseBody>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
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

                                    JSONArray items = json.getJSONArray("data");

                                    JSONObject item = items.getJSONObject(0);


                                    Data data = gson.fromJson(item.toString(), Data.class);

                                    tanggal.setText(data.getDateTime());
                                    ph.setText(data.getPh());
                                    suhu.setText(data.getSuhu());
                                    simpulan.setText(data.getKesimpulan());

                                    progph.setMax(14);
                                    progph.setProgress(Math.round(Float.valueOf(data.getPh())));

                                    progsuhu.setMax(35);
                                    progsuhu.setProgress(Math.round(Float.valueOf(data.getSuhu())));

                                    Toast.makeText(getContext(),"Updated Successfully", Toast.LENGTH_SHORT).show();

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
        });



        return v;
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
