package io.github.technocrats.capstone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import io.github.technocrats.capstone.adapters.ProductQuantityListAdapter;
import io.github.technocrats.capstone.models.ProductQuantity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderSummaryActivity extends AppCompatActivity implements View.OnClickListener {
    TextView totalTextView;
    TextView orderTextView;
    TextView dateDisplay;
    TextView storeNumberDisplay;

    ListView ProductQuantityListView;

    Button btnSubmitOrder;

    Toolbar toolbar;

    private SharedPreferences sharedPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        ProductQuantityListView = (ListView) findViewById(R.id.ProductQuantityListView);

        this.sharedPlace = getSharedPreferences("SharedPlace", MODE_PRIVATE);
        checkIfLoggedIn();

        toolbar = (Toolbar) findViewById(R.id.ordersSummaryToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Order Summary");

        btnSubmitOrder = (Button) findViewById(R.id.btnSubmitOrder);
        btnSubmitOrder.setOnClickListener(this);

        dateDisplay = (TextView) findViewById(R.id.txtOrderSummaryDateDisplay);
        storeNumberDisplay = (TextView) findViewById(R.id.txtOrderSummaryStoreNumber);

        DisplayDate();

        String storeID = sharedPlace.getString("storeID", "");

        storeNumberDisplay.setText("Store Number: " + storeID);

        String order = getIntent().getStringExtra("order");

        ArrayList<String> list = new ArrayList<>();

        String[] temp = order.split("\n");

        for(int i = 0; i < temp.length; i ++)
        {
            list.add(temp[i]);
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);

        ProductQuantityListView.setAdapter(adapter);

        String total = getIntent().getStringExtra("total");

        totalTextView = (TextView) findViewById(R.id.orderSummaryTotal);
        totalTextView.setText(total);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnMenuCheckInventory:
                return true;
            case R.id.btnMenuRecommendations:
                return true;
            case R.id.btnMenuSetInventory:
                return true;
            case R.id.btnMenuNewOrder:
                startActivity(new Intent(getApplicationContext(), CreateOrderActivity.class));
                return true;
            case R.id.btnMenuTrackOrder:
                startActivity(new Intent(getApplicationContext(), TrackOrderActivity.class));
                return true;
            case R.id.btnMenuUsage:
                return true;
            case R.id.btnMenuProfile:
                return true;
            case R.id.btnLogout:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void DisplayDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
        String date = sdf.format(new Date());

        dateDisplay.setText(date);
    }

    private void checkIfLoggedIn(){
        if (this.sharedPlace.getString("username", "").equals("")){
            Toast.makeText(this, "You are not logged in", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View view) {
         if (view.getId() == R.id.btnSubmitOrder){
            // TODO: generate a text file
        }
    }
}