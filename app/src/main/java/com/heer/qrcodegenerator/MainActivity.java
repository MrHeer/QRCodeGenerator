package com.heer.qrcodegenerator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.sumimakito.awesomeqr.AwesomeQRCode;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private static Bitmap qr_1_background;
    private static Bitmap qr_2_background;
    private static Bitmap qr_3_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        qr_1_background = BitmapFactory.decodeResource(getResources(), R.drawable.qr_1_background);
        qr_2_background = BitmapFactory.decodeResource(getResources(), R.drawable.qr_2_background);
        qr_3_background = new AwesomeQRCode.Renderer()
                .dotScale(1.0f)
                .contents("LCY, I love you.    —MrHeer")
                .size(1000).margin(0).render();


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = View.inflate(this, R.layout.about_main, null);
            builder.setView(view);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "图样1";
                case 1:
                    return "图样2";
                case 2:
                    return "图样3";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ImageView imgQRCodeArea = (ImageView) rootView.findViewById(R.id.imgQRCodeArea);
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    imgQRCodeArea.setImageBitmap(qr_1_background);
                    break;
                case 2:
                    imgQRCodeArea.setImageBitmap(qr_2_background);
                    break;
                case 3:
                    System.out.println(qr_3_background);
                    imgQRCodeArea.setImageBitmap(qr_3_background);
                    break;
                default:
                    break;
            }
            return rootView;
        }
    }

    public void onClick(View btnQRCodeGenerate) {
        Fragment currentFragment = getSupportFragmentManager().getFragments().get(mViewPager.getCurrentItem());
        EditText editContent = (EditText) currentFragment.getView().findViewById(R.id.editContent);
        ImageView imgQRCodeArea = (ImageView) currentFragment.getView().findViewById(R.id.imgQRCodeArea);
        switch (mViewPager.getCurrentItem()) {
            case 0:
                genernate(editContent.getText().toString(), qr_1_background, 0.3f, imgQRCodeArea);
                break;
            case 1:
                genernate(editContent.getText().toString(), qr_2_background, 0.3f, imgQRCodeArea);
                break;
            case 2:
                genernate(editContent.getText().toString(), null, 1.0f, imgQRCodeArea);
                break;
            default:
                break;
        }
    }

    private void genernate(String contents, Bitmap background, float dataDotScale, final ImageView imageView) {
        new AwesomeQRCode.Renderer()
                .dotScale(dataDotScale)
                .contents(contents)
                .size(1000).margin(0)
                .background(background)
                .renderAsync(new AwesomeQRCode.Callback() {
                    @Override
                    public void onRendered(AwesomeQRCode.Renderer renderer, final Bitmap bitmap) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 提示: 这里使用 runOnUiThread(...) 来规避从非 UI 线程操作 UI 控件时产生的问题。
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    }

                    @Override
                    public void onError(AwesomeQRCode.Renderer renderer, Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
