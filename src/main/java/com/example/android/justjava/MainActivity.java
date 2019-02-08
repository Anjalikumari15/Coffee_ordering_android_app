package com.example.android.justjava;

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {
      CheckBox whipcream_checkbox = (CheckBox) findViewById(R.id.Whipcream_checkbox);
        boolean whipcream = whipcream_checkbox.isChecked();
        CheckBox chocolate_checkbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean chocolate = chocolate_checkbox.isChecked();
        int price = calculatePrice(whipcream, chocolate);
        EditText name_field = findViewById(R.id.name_field) ;
        String name = name_field.getText().toString();
        String priceMessage = createOrderSummary( price , whipcream, chocolate, name);
       // displayMessage(priceMessage);
        //Log.v("MainActivity ", "Has whipped cream " + whipcream);
        String subject = "Just Java order for " + name + "\n";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        //   intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean iswhip, boolean ischoco) {

        int basePrice = 5;
        if(iswhip)
        {
            basePrice += 1;
        }
        if(ischoco)
        {
            basePrice+= 2;
        }
        return basePrice*quantity;
    }

    public void increment(View view) {
        if(quantity==100)
        {
          //  displayMessage("Sorry but that's the maximum we can serve.");
            Toast toastmsg = Toast.makeText(this, "Sorry but that's the maximum we can serve.", Toast.LENGTH_SHORT);
            toastmsg.show();
            return;
        }
        else {
                quantity = quantity + 1 ;
                displayQuantity(quantity);
            }
    }
    public void decrement(View view) {

        if(quantity==0)
        {
           // displayMessage("oops!! i have never seen a negative cup of coffee");
            Toast toastmsg = Toast.makeText(this, "oops!! i have never seen a negative cup of coffee", Toast.LENGTH_SHORT);
            toastmsg.show();
            return;
        }
        else
        {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */
    private String createOrderSummary(int number, boolean whippedcream, boolean choco, String name_of_orderer) {
        String pricemsg = getString(R.string.order_summary_name, name_of_orderer);
        pricemsg += "\n" + getString(R.string.order_summary_whipped_cream, whippedcream);
        pricemsg += "\n" + getString(R.string.order_summary_chocolate, choco);
        pricemsg += "\n" + getString(R.string.order_summary_quantity, quantity);
        pricemsg += "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(number));
        pricemsg += "\n" + getString(R.string.thank_you);
        return pricemsg;
    }
}