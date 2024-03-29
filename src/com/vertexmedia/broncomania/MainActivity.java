package com.vertexmedia.broncomania;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.facebook.AppEventsLogger;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
     * derivative, which will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    ViewPager mViewPager;
    
    static TextView tv1;
    
    private UiLifecycleHelper uiHelper;

	public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        uiHelper = new UiLifecycleHelper(this, null);
        uiHelper.onCreate(savedInstanceState);
        
        final int[] ICONS = new int[] {
                R.drawable.home,
                R.drawable.money,
                R.drawable.calendar,
                R.drawable.candidates
        };

        
        //Remove title bar
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        // Specify that we will be NOT displaying the title bar.
        actionBar.setDisplayShowTitleEnabled(false);
        
        // Specify that we will be NOT displaying the home icon.
        actionBar.setDisplayShowHomeEnabled(false);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab().setIcon(ICONS[i])
                            .setTabListener(this));
        }
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
	        @Override
	        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	            Log.e("Activity", String.format("Error: %s", error.toString()));
	        }

	        @Override
	        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	            Log.i("Activity", "Success!");
	        }
	    });
	}
	
	@Override
	protected void onResume() {
	  super.onResume();

	  uiHelper.onResume();
	  // Logs 'install' and 'app activate' App Events.
	  AppEventsLogger.activateApp(this);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onPause() {
	  super.onPause();
	  uiHelper.onPause();
	  // Logs 'app deactivate' App Event.
	  AppEventsLogger.deactivateApp(this);
	}
	
	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                	Fragment fragment = new HomeSectionFragment();
                	Bundle args = new Bundle();
                    args.putInt(HomeSectionFragment.ARG_SECTION_NUMBER, i + 1);
                    fragment.setArguments(args);
                	return fragment;

                case 1:
                    fragment = new DonateSectionFragment();
                    args = new Bundle();
                    args.putInt(DonateSectionFragment.ARG_SECTION_NUMBER, i + 1);
                    fragment.setArguments(args);
                    return fragment;
                case 2:
                	fragment = new CalendarSectionFragment();
                    args = new Bundle();
                    args.putInt(CalendarSectionFragment.ARG_SECTION_NUMBER, i + 1);
                    fragment.setArguments(args);
                	return fragment;
                case 3:
                	fragment = new CandidatesSectionFragment();
                    args = new Bundle();
                    args.putInt(CandidatesSectionFragment.ARG_SECTION_NUMBER, i + 1);
                    fragment.setArguments(args);
                	return fragment;
            }
			return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Section " + (position + 1);
        }
    }

    /**
     * A fragment representing a section of the app
     */
    public static class HomeSectionFragment extends Fragment {
    	
    	public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);

            return rootView;
        }
    }

    /**
     * A fragment representing a section of the app
     */
    public static class CalendarSectionFragment extends Fragment {

        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
            return rootView;
        }
    }
    
    /**
     * A fragment representing a section of the app
     */
    @SuppressLint("SimpleDateFormat")
	public static class DonateSectionFragment extends Fragment {

        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_donate, container, false);


            try {
            	SimpleDateFormat simpleDateFormat = 
                        new SimpleDateFormat("dd/M/yyyy hh:mm");
    			Date dealine = (Date) simpleDateFormat.parse("06/06/2015 23:59");
    			Date now = (Date) Calendar.getInstance().getTime(); 
    			long diff = dealine.getTime() - now.getTime();
    			long seconds = diff / 1000;
    			long minutes = seconds / 60;
    			long hours = minutes / 60;
    			long days = hours / 24;
    			tv1 = (TextView) rootView.findViewById(R.id.coutdown_days_text_thousands);
    			tv1.setText("0");
    			
    			tv1 = (TextView) rootView.findViewById(R.id.coutdown_days_text_hundreds);
    			tv1.setText("0");
    			
    			tv1 = (TextView) rootView.findViewById(R.id.coutdown_days_text_tens);
    	        tv1.setText(Long.toString(days / 10));
    	        
    	        tv1 = (TextView) rootView.findViewById(R.id.coutdown_days_text_units);
    	        tv1.setText(Long.toString(days % 10));
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            return rootView;
        }
    }
    
    /**
     * A fragment representing a section of the app
     */
    public static class CandidatesSectionFragment extends Fragment {

        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_candidates, container, false);
            
            return rootView;
        }
    }
    
    public void onClickFunc(View view) {
    	
    	String candidate = (String) view.getTag(); 
    	String captionEnding = ", ahora te toca a tí";
    	
    	if (FacebookDialog.canPresentShareDialog(getApplicationContext(), 
                FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {

	    	FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
	    			.setApplicationName("Broncomania")
	    			.setCaption("Yo ya ayudé a ".concat(candidate).concat(captionEnding))
	    			.setName("Apoya a la Broncomania")
	    			.setLink("bit.ly/SumatePorNL")
			        .setDescription("Ayuda a Nuevo León")	
			        .build();
	    	uiHelper.trackPendingDialogCall(shareDialog.present());
    	} else {
    	}
    }
    
    public void openBroncoWebsite(View view){
    	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://unete.jaimerodriguez.mx/register"));
    	startActivity(browserIntent);
    }
    
    public void openTransparencyWebsite(View view){
    	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.transparenciapresupuestaria.gob.mx/es/PTP/Datos_Abiertos"));
    	startActivity(browserIntent);
    }
}
