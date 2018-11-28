package mahesh.cubex.saloonadmin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> {
    private Context context;
    private List<ApprovalRequestPojo> cartList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description;
        public ImageView thumbnail;
        public Button viewMore;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.news_title);
            description = view.findViewById(R.id.news_description);
            thumbnail = view.findViewById(R.id.thumbnail);
            viewMore = view.findViewById(R.id.news_viewMore);
        }
    }


    public NewsListAdapter(Context context, List<ApprovalRequestPojo> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ApprovalRequestPojo recipe = cartList.get(position);
        holder.title.setText("Account Type : " + recipe.getCustomerType().replace("_", " ").toUpperCase());
        holder.description.setText("Account ID : " + recipe.getCustomerID());


        Glide.with(context)
                .load(recipe.getImageUrl())
                .into(holder.thumbnail);

        holder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.account = cartList.get(position);
                context.startActivity(new Intent(context,ViewDetails.class));
            }
        });

    }

    // recipe
    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
