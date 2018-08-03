package com.hikki.sergey_natalenko.testapp.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hikki.sergey_natalenko.testapp.R;
import com.hikki.sergey_natalenko.testapp.classes.User;
import com.hikki.sergey_natalenko.testapp.utils.AppLab;

import java.util.Date;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private static final String TAG = "MessagesActivity";

    private RecyclerView mChatsRecyclerView;
    private ChatAdapter mAdapter;
    private AppLab mAppLab;
    private List<User> mUsers;
    private View mProgressView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        mAppLab = AppLab.get(this);

        mProgressView = (View) findViewById(R.id.chats_progress);
        mChatsRecyclerView = (RecyclerView) findViewById(R.id.chats_recycler_view);
        mChatsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        new ChatsLoadTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_chats, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "QueryTextSubmit: " + s);
                //QueryPreferences.setStoredQuery(getActivity(), s);
                //updateItems();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "QueryTextChange: " + s);
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String query = QueryPreferences.getStoredQuery(getActivity());
                //searchView.setQuery(query,false);
                searchView.clearFocus();
            }
        });
        return true;
    }

    private class ChatHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private User mUser;

        private TextView mUserNameTextView;
        private ImageView mUserImageView;
        private TextView mChatDateTextView;

        public ChatHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_chat, parent, false));

            mUserNameTextView = (TextView) itemView.findViewById(R.id.companion_text_view);
            mUserImageView = (ImageView) itemView.findViewById(R.id.companion_image_view);
            mChatDateTextView = (TextView)itemView.findViewById(R.id.date_text_view);

        }
        public void bind(User user, final int position){
            mUser = user;
            Drawable drawable = mUser.getName().equals("Иванов Иван") ? getResources().getDrawable(R.drawable.icon_okabe) : getResources().getDrawable(R.drawable.icon_koko);
            Log.i("Name", mUser.getName());
            mUserImageView.setImageDrawable(drawable);
            mUserNameTextView.setText(mUser.getName());
            String d = DateFormat.format("kk:mm dd MMM", new Date()).toString();
            mChatDateTextView.setText(d);

        }

        @Override
        public void onClick(View view){
            Intent intent = new Intent(MessagesActivity.this, ChatActivity.class);
            startActivity(intent);
        }
    }

    private class ChatAdapter extends RecyclerView.Adapter<MessagesActivity.ChatHolder>{
        private List<User> mUsers;

        public ChatAdapter(List<User> users){
            mUsers = users;
        }

        @Override
        public MessagesActivity.ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(MessagesActivity.this);
            return new MessagesActivity.ChatHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(MessagesActivity.ChatHolder holder, int position) {
            User user = mUsers.get(position);
            holder.bind(user, position);
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }
    }

    private class ChatsLoadTask extends AsyncTask<Void,Void,List<User>> {
        @Override
        protected void onPreExecute() {
            showProgress(true);
        }

        @Override
        protected List<User> doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            List<User> users = mAppLab.getUsers();

            return users;
        }

        @Override
        protected void onPostExecute(List<User> users){
            showProgress(false);
            mUsers = users;
            mAdapter = new MessagesActivity.ChatAdapter(users);
            mChatsRecyclerView.setAdapter(mAdapter);
        }
    }

    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}
