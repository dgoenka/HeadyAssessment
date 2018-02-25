package com.divyanshgoenka.headyassessment.android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
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

import com.divyanshgoenka.headyassessment.R;
import com.divyanshgoenka.headyassessment.android.adapter.ProductRankingRecyclerViewAdapter;
import com.divyanshgoenka.headyassessment.log.Logger;
import com.divyanshgoenka.headyassessment.pojo.Product;
import com.divyanshgoenka.headyassessment.pojo.Ranking;
import com.divyanshgoenka.headyassessment.presenter.MainPresenter;
import com.divyanshgoenka.headyassessment.util.Validations;
import com.divyanshgoenka.headyassessment.view.MainView;
import com.divyanshgoenka.headyassessment.view.RankingView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a ranking of products.
 * <p/>
 */
public class RankingFragment extends BaseFragment implements RankingView, AdapterView.OnItemSelectedListener {


    private static final String SAVED_LAYOUT_MANAGER = "SAVED_LAYOUT_MANAGER";
    private static final String LIST_VERSION_AT = "LIST_VERSION_AT";
    private static final String SAVED_SPINNER_STATE = "SAVED_SPINNER_STATE";
    private static final String SAVED_SPINNER_POSITION = "SAVED_SPINNER_POSITION";
    private static final String TAG = RankingFragment.class.getSimpleName();
    private static final String SAVE_RECYCLER_POSITION = "SAVE_RECYCLER_POSITION";
    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    View progress;
    Spinner rankingSpinner;
    private MainView mainView;
    private MainPresenter mainPresenter;
    private Parcelable layoutManagerSavedState;
    private Parcelable rankingSpinnerSavedState;
    private Long listVersionAt;
    private int selectedPosition =0;
    private List<Ranking> rankings;
    private int recyclerViewLastPositon = 0;

    // TODO: Customize parameter initialization
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
        Logger.d("in RankingFragment onCreateView");
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        Context context = view.getContext();
        ButterKnife.bind(this,view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        if(savedInstanceState!=null) {
            listVersionAt = savedInstanceState.getLong(RankingFragment.LIST_VERSION_AT, -1);
            layoutManagerSavedState = savedInstanceState.getParcelable(RankingFragment.SAVED_LAYOUT_MANAGER);
            rankingSpinnerSavedState = savedInstanceState.getParcelable(RankingFragment.SAVED_SPINNER_STATE);
            selectedPosition = savedInstanceState.getInt(RankingFragment.SAVED_SPINNER_POSITION, 0);
            recyclerViewLastPositon = savedInstanceState.getInt(RankingFragment.SAVE_RECYCLER_POSITION, 0);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mainPresenter.loadRankings();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RankingFragment.SAVED_LAYOUT_MANAGER, recyclerView.getLayoutManager().onSaveInstanceState());
        if (listVersionAt != null) {
            outState.putLong(RankingFragment.LIST_VERSION_AT, listVersionAt);
        }
        outState.putInt(RankingFragment.SAVE_RECYCLER_POSITION, ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition());
        if (rankingSpinner != null) {
            outState.putParcelable(RankingFragment.SAVED_SPINNER_STATE, rankingSpinner.onSaveInstanceState());
            outState.putInt(RankingFragment.SAVED_SPINNER_POSITION, rankingSpinner.getSelectedItemPosition());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.rankings,menu);
        MenuItem menuItem = menu.findItem(R.id.ranking_spinner);
        rankingSpinner = (Spinner) menuItem.getActionView();
        setSpinnerAdapter();
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
        setSpinnerAdapter();
        if(this.listVersionAt!=listVersionAt){
            this.listVersionAt = listVersionAt;
            recyclerViewLastPositon = 0;
        }else{
            if (layoutManagerSavedState != null) {
                recyclerView.getLayoutManager().onRestoreInstanceState(layoutManagerSavedState);
            }
            recyclerView.getLayoutManager().scrollToPosition(recyclerViewLastPositon);
            if (rankingSpinnerSavedState != null) {
                rankingSpinner.onRestoreInstanceState(rankingSpinnerSavedState);
            }
        }
    }

    public void changeAdapter() {
        Ranking ranking = rankings.get(selectedPosition);
        List<Product> productList = ranking.getProducts();
        recyclerView.setAdapter(new ProductRankingRecyclerViewAdapter(productList, mainPresenter, ranking.getRanking()));
    }

    /**
     * Since the (@link Spinner) is in the Action Bar, there can be a mismatch in when the spinner is created and data is available. The purpose of this method is to safely test and care for both
     */
    private void setSpinnerAdapter() {
        if (!Validations.isEmptyOrNull(rankings) && rankingSpinner != null) {
            ArrayAdapter<Ranking> adapter = new ArrayAdapter<>(getContext(), R.layout.simple_spinner_item, rankings);
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            rankingSpinner.setAdapter(adapter);
            rankingSpinner.setSelection(selectedPosition);
        }
    }

    @Override
    public void setLoading(boolean loading) {

    }

    @Override
    public void onLoadRankingException(Throwable e) {
        Logger.e(e);
    }

    @Override
    public void showLoading() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int selectedPosition, long l) {
        this.selectedPosition = selectedPosition;
        changeAdapter();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
