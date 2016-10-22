package com.nickblomberg.kivation.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nickblomberg.kivation.EndlessRecyclerViewScrollListener;
import com.nickblomberg.kivation.KivationApp;
import com.nickblomberg.kivation.R;
import com.nickblomberg.kivation.models.Loan;
import com.nickblomberg.kivation.network.NetworkService;
import com.nickblomberg.kivation.presenters.NewestLoansPresenter;
import com.nickblomberg.kivation.views.adapters.LoanAdapter;

import java.util.List;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * An activity to display the most recent loans which have been added to the Kiva platform.
 */
public class NewestLoansActivity extends BaseActivity {

    RecyclerView mRecyclerView;

    private LoanAdapter mAdapter;
    private NewestLoansPresenter mNewestLoansPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        ButterKnife.bind(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.loans_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new LoanAdapter(this);
        mAdapter.setOnItemClickListener(new LoanAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Loan clickedLoan = mAdapter.getItem(position);
                Timber.d("Clicked: %s ", clickedLoan);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        NetworkService networkService = ((KivationApp) getApplicationContext()).getNetworkService();

        mNewestLoansPresenter = new NewestLoansPresenter(this, networkService);
        mNewestLoansPresenter.loadNewestLoans(1);

        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener((LinearLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mNewestLoansPresenter.loadNewestLoans(page);
            }
        });
    }

    public void displayNewestLoans(List<Loan> loans) {
        mAdapter.appendLoans(loans);
        mAdapter.notifyDataSetChanged();
    }
}