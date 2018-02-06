package com.divyanshgoenka.headyassessment.android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.divyanshgoenka.headyassessment.android.adapter.ProductRankingRecyclerViewAdapter;
import com.divyanshgoenka.headyassessment.pojo.Listable;
import com.divyanshgoenka.headyassessment.pojo.Product;
import com.divyanshgoenka.headyassessment.pojo.Ranking;
import com.divyanshgoenka.headyassessment.presenter.MainPresenter;
import com.divyanshgoenka.headyassessment.R;
import com.divyanshgoenka.headyassessment.view.MainView;
import com.divyanshgoenka.headyassessment.view.RankingView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * A fragment representing a ranking of products.
 * <p/>
 */
public class RankingFragment extends Fragment implements RankingView, AdapterView.OnItemSelectedListener {


    private static final String SAVED_LAYOUT_MANAGER = "SAVED_LAYOUT_MANAGER";
    private static final String LIST_VERSION_AT = "LIST_VERSION_AT";
    private static final String SAVED_SPINNER_STATE = "SAVED_SPINNER_STATE";
    private static final String SAVED_SPINNER_POSITION = "SAVED_SPINNER_POSITION";
    private static final String TAG = RankingFragment.class.getSimpleName();

    private MainView mainView;
    private MainPresenter mainPresenter;
    @BindView(R.id.list)
    RecyclerView recyclerView;
    Spinner rankingSpinner;
    private Parcelable layoutManagerSavedState;
    private Parcelable rankingSpinnerSavedState;
    private Long listVersionAt;
    private int selectedPosition =0;
    private List<Ranking> rankings;

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RankingFragment newInstance() {
        RankingFragment fragment = new RankingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.title_rankings);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Timber.i(TAG,"in RankingFragment onCreateView");
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        Context context = view.getContext();
        ButterKnife.bind(this,view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        if(savedInstanceState!=null) {
            listVersionAt = savedInstanceState.getLong(LIST_VERSION_AT, -1);
            layoutManagerSavedState = savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER);
            rankingSpinnerSavedState = savedInstanceState.getParcelable(SAVED_SPINNER_STATE);
            selectedPosition = savedInstanceState.getInt(SAVED_SPINNER_POSITION,0);
        }
        mainPresenter.loadRankings();
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_LAYOUT_MANAGER,recyclerView.getLayoutManager().onSaveInstanceState());
        outState.putParcelable(SAVED_SPINNER_STATE, rankingSpinner.onSaveInstanceState());
        outState.putLong(LIST_VERSION_AT,listVersionAt);
        outState.putInt(SAVED_SPINNER_POSITION, rankingSpinner.getSelectedItemPosition());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.rankings,menu);
        MenuItem menuItem = menu.findItem(R.id.ranking_spinner);
        rankingSpinner = (Spinner) menuItem.getActionView();
        rankingSpinner.setOnItemSelectedListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainView) {
            mainView = (MainView) getActivity();
            mainPresenter = mainView.getPresenter();
            mainPresenter.setRankingView(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainView = null;
        mainPresenter.setRankingView(null);
        mainPresenter = null;
    }


    @Override
    public void showProducts(List<Ranking> rankings, Long listVersionAt) {
        this.rankings = rankings;
        ArrayAdapter<Ranking> adapter = new ArrayAdapter<Ranking>(getContext(),android.R.layout.simple_spinner_item, rankings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeAdapter(selectedPosition);
        rankingSpinner.setAdapter(adapter);
        if(this.listVersionAt!=listVersionAt){
            this.listVersionAt = listVersionAt;
        }else{
            if (layoutManagerSavedState != null)
                recyclerView.getLayoutManager().onRestoreInstanceState(layoutManagerSavedState);
            if(rankingSpinnerSavedState !=null)
                rankingSpinner.onRestoreInstanceState(rankingSpinnerSavedState);
        }
    }

    public void changeAdapter(int selectedPosition){
        this.selectedPosition = selectedPosition;
        Ranking ranking = rankings.get(selectedPosition);
        List<Product> productList = ranking.getProducts();
        recyclerView.setAdapter(new ProductRankingRecyclerViewAdapter(productList, mainPresenter, ranking.getRanking()));
    }

    @Override
    public void setLoading(boolean loading) {

    }

    @Override
    public void onLoadRankingException(Throwable e) {
        Timber.e(e);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int selectedPosition, long l) {
        changeAdapter(selectedPosition);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
