package com.example.profofconceptlogin;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.profofconceptlogin.databinding.FragmentFirstBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FirstFragment extends Fragment implements GestureDetector.OnGestureListener {

    private FragmentFirstBinding binding;
    private static final String POST_BODY = "4189";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callApi();
        binding.buttonGoToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                      .navigate(R.id.action_FirstFragment_to_SecondFragment);
                /*FTPServer f = new FTPServer();
                f.startServer();*/
            }
        });



        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                    if (event.isLongPress()) {
                        Toast.makeText(getContext(), "Volume Up Pressed", Toast.LENGTH_SHORT).show();
                        return true; // Return true to indicate that the event has been consumed
                    }
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        return true; // Return true to indicate that the event has been consumed
                    }
                } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        Toast.makeText(getContext(), "Volume down Pressed", Toast.LENGTH_SHORT).show();
                        return true; // Return true to indicate that the event has been consumed
                    }
                }

                // Return false to allow the event to propagate to other listeners
                return false;
            }
        });
    }

    public void callApi(){
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getContext());
                String url = "https://jsonplaceholder.typicode.com/comments/1";

                Map<String, String> postParams = new HashMap<>();
                postParams.put("body", POST_BODY);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String name = response.getString("name");
                                    String email = response.getString("email");

                                    binding.textviewFirst.setText("Title: " + name + "\nCompleted: " + email);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        return postParams;
                    }
                };

                queue.add(jsonObjectRequest);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        // This method is required by the GestureDetector but can be left empty.
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // This method is optional and can be left empty.
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Toast.makeText(getContext(), "Single Click", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // This method is optional and can be left empty.
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Toast.makeText(getContext(), "Hello, World!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // This method is optional and can be left empty.
        return false;
    }
}