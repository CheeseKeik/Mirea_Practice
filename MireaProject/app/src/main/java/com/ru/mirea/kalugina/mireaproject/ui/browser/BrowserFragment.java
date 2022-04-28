package com.ru.mirea.kalugina.mireaproject.ui.browser;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ru.mirea.kalugina.mireaproject.R;
import com.ru.mirea.kalugina.mireaproject.databinding.FragmentBrowserBinding;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BrowserFragment extends Fragment {

    private FragmentBrowserBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BrowserViewModel browserViewModel =
                new ViewModelProvider(this).get(BrowserViewModel.class);

        binding = FragmentBrowserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.buttonFind.setOnClickListener(this::onSearchClick);

        final TextView textView = binding.textBrowser;
        browserViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        new SendHttpRequestTask().execute("https://i.aipserver.ru/EwiniWYXEAQ8Q_F.jpg");
        return root;
    }

    private void onSearchClick(View view) {
        String searchText = binding.editTextBrowser.getText().toString();
        if (searchText.isEmpty()) return;
        String searchUrl = "https://www.google.com/search?q=" + searchText;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchUrl));
        startActivity(intent);
    }

    private class SendHttpRequestTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            }catch (Exception e){
                Log.d(TAG,e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            ImageView imageView = (ImageView) getActivity().findViewById(R.id.pictureFox);
            imageView.setImageBitmap(result);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}