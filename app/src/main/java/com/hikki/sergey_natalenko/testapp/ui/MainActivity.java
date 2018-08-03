package com.hikki.sergey_natalenko.testapp.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hikki.sergey_natalenko.testapp.R;
import com.hikki.sergey_natalenko.testapp.classes.Note;
import com.hikki.sergey_natalenko.testapp.classes.User;
import com.hikki.sergey_natalenko.testapp.ui.components.AttachedCardView;
import com.hikki.sergey_natalenko.testapp.utils.AppLab;
import com.hikki.sergey_natalenko.testapp.utils.UserPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;
    private AppLab mAppLab;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private View mHeader;
    private TextView mUserName;
    private TextView mUserEmail;
    private RecyclerView mNewsRecyclerView;
    private MainActivity.NoteAdapter mAdapter;
    private View mProgressView;
    private List<Note> mNotes;
    private MainActivity.NewsLoadTask mNewsLoadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mAppLab = AppLab.get(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setLogo(R.drawable.logo1);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mHeader = mNavigationView.getHeaderView(0);

        mUserName = (TextView) mHeader.findViewById(R.id.header_username_name);
        mUserEmail = (TextView) mHeader.findViewById(R.id.header_username_email);

        if (!UserPreferences.getStoredIsLogged(this)){
            startLogin();
        } else {
            User user = mAppLab.getSelf();
            mUserName.setText(user.getName());
            mUserEmail.setText(user.getEmail());
        }

        mNewsRecyclerView = (RecyclerView) findViewById(R.id.news_recycler_view);
        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressView = (View) findViewById(R.id.news_progress);
        mNewsLoadTask = new MainActivity.NewsLoadTask();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_main, menu);

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

        MenuItem addNewNoteIte = menu.findItem(R.id.menu_item_new_note);
        addNewNoteIte.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startNewNote();
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_news:
                break;
            case R.id.nav_send:
                startChats();
                break;
            case R.id.nav_manage:
                startSettings();
                break;
            case R.id.nav_about:

                break;
            case R.id.nav_exit:
                logout();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
        if (mNewsLoadTask.getStatus() != AsyncTask.Status.RUNNING) updateNews();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mNewsLoadTask = null;
    }

    private void updateNews(){
        mNewsLoadTask = new MainActivity.NewsLoadTask();
        mNewsLoadTask.execute();
    }

    private void logout(){
        UserPreferences.setStoredIsLogged(getApplicationContext(), false);
        startLogin();
    }

    private void startLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void startSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void startNewNote(){
        Intent intent = new Intent(this, EditorNoteActivity.class);
        startActivity(intent);
    }

    private void startChats(){
        Intent intent = new Intent(this, MessagesActivity.class);
        startActivity(intent);
    }

    private class NoteHolder extends RecyclerView.ViewHolder {

        private Note mNote;

        private TextView mAuthorTextView;
        private TextView mDateTextView;
        private TextView mContentTextView;
        private ImageButton mAuthorImageButton;
        private TextView mLikes;
        private TextView mComments;
        private ImageButton mLikeButton;
        private LinearLayout mAttachedFiles;

        public NoteHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_card, parent, false));

            mAuthorTextView = (TextView) itemView.findViewById(R.id.author_text);
            mAuthorImageButton = (ImageButton) itemView.findViewById(R.id.author_image_button);

            mDateTextView = (TextView) itemView.findViewById(R.id.date_text);
            mContentTextView = (TextView) itemView.findViewById(R.id.content_text);

            mLikes = (TextView) itemView.findViewById(R.id.like_text);
            mLikeButton = (ImageButton) itemView.findViewById(R.id.like_image_button);
            mAttachedFiles = (LinearLayout) itemView.findViewById(R.id.attached_files);
        }
        public void bind(Note note, final int position){
            mNote = note;
            mAuthorTextView.setText(AppLab.get(MainActivity.this).getUser(getUserId()).getName());
            String d = DateFormat.format("kk:mm dd-MM-yyyy", mNote.getDate()).toString();
            mDateTextView.setText(d);
            mContentTextView.setText(mNote.getText());
            mAuthorImageButton.setImageDrawable(getResources().getDrawable(R.drawable.icon_okabe));
            mLikes.setText(""+mNote.getLikes());
            if (mNote.isLiked(getUserId())){
                mLikeButton.setImageResource(R.drawable.ic_card_like_fill);
            } else{
                mLikeButton.setImageResource(R.drawable.ic_card_like_border);
            }
            mLikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNote.addLikes(getUserId());
                    if (mNote.isLiked(getUserId())){
                        mLikeButton.setImageResource(R.drawable.ic_card_like_fill);
                    } else{
                        mLikeButton.setImageResource(R.drawable.ic_card_like_border);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            });
            if(!mNote.getHasFiles()){
                mAttachedFiles.setVisibility(View.VISIBLE);
                mAttachedFiles.addView(new AttachedCardView(MainActivity.this,0,"","Someeqweqweqweqweqweqweqweqweqweqweqwething.jpg"));
            }


        }
    }

    private class NoteAdapter extends RecyclerView.Adapter<MainActivity.NoteHolder>{
        private List<Note> mNotes;

        public NoteAdapter(List<Note> notes){
            mNotes = notes;
        }

        @Override
        public MainActivity.NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            return new MainActivity.NoteHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(MainActivity.NoteHolder holder, int position) {
            Note note = mNotes.get(position);
            holder.bind(note, position);
        }

        @Override
        public int getItemCount() {
            return mNotes.size();
        }
    }

    private class NewsLoadTask extends AsyncTask<Void,Void,List<Note>> {
        @Override
        protected void onPreExecute() {
            showProgress(true);
        }

        @Override
        protected List<Note> doInBackground(Void... params) {
            List<Note> notes = new ArrayList<>();
            try {
                notes = mAppLab.getNotes();
            } catch (Throwable e) {

            }

            return notes;
        }

        @Override
        protected void onPostExecute(List<Note> notes){
            showProgress(false);
            mNotes = notes;
            mAdapter = new MainActivity.NoteAdapter(notes);
            mNewsRecyclerView.setAdapter(mAdapter);
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

    private UUID getUserId(){
        return UserPreferences.getUserLoggedId(this.getApplicationContext());
    }
}
