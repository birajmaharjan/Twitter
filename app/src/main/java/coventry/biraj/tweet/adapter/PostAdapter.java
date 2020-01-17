package coventry.biraj.tweet.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import coventry.biraj.twitter.R;
import coventry.biraj.tweet.model.Posts;
import coventry.biraj.tweet.url.Url;
import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.DetailViewHolder> {


    Context context;
    List<Posts> postsList;

    public PostAdapter(Context context, List<Posts> postsList) {
        this.context =  context;
        this.postsList = postsList;
    }

    @NonNull
    @Override
    public PostAdapter.DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.story_recycler_view,parent,false);
        return new DetailViewHolder(view);
    }

    private void StrictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.DetailViewHolder holder, int position) {
        final Posts details = postsList.get(position);
        String imgPath = Url.base_url + "uploads/" + details.getImage();
        StrictMode();

        try {
            URL url = new URL(imgPath);
            holder.imgProfile.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.tvUsername.setText(details.getUsername());
        holder.tvName.setText(details.getName());
        holder.tvTime.setText(details.getTime());
        holder.tvStory.setText(details.getStory());
        holder.tvComment.setText(details.getLike());
        holder.tvTweet.setText(details.getComment());
        holder.tvLike.setText(details.getRetweet());
//        holder.tvProfile.set(details.getimage());
//        holder.tvImage.setText(details.getuimage());

    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvUsername, tvTime, tvStory, tvComment, tvTweet, tvLike;
        CircleImageView imgProfile;
        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvusername);
            tvName = itemView.findViewById(R.id.tvname);
            tvTime = itemView.findViewById(R.id.tvtime);
            tvStory = itemView.findViewById(R.id.tvstory);
            tvComment = itemView.findViewById(R.id.tvComment);
            tvTweet = itemView.findViewById(R.id.tvTweets);
            tvLike = itemView.findViewById(R.id.tvLike);
            imgProfile = itemView.findViewById(R.id.imgProfile);
        }
    }
}
