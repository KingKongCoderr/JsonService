package jaihind.godblessamerica.jsonservice;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity  {
    
    Button startServiceButton;
    ImageView imageView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startServiceButton = (Button) findViewById(R.id.start_service_button);
        imageView = (ImageView)findViewById(R.id.dog_image_view);
        String imgUrl = getIntent().getStringExtra("notificationresult");
        Picasso.with(this)
                .load(imgUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .resize(800,800)
                .into(imageView);
        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PendingIntent servicePendingIntent =createPendingResult(100,new Intent(),0);
                Intent jsonServiceIntent = new Intent(MainActivity.this,MyJsonService.class);
                jsonServiceIntent.putExtra("pendingintent",servicePendingIntent);
                startService(jsonServiceIntent);
            }
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 100 && resultCode == 200){
            Log.d("insideresult","inside on activity result");
            String imgUrl = data.getStringExtra("imageurl");
            Log.d("resultimage",imgUrl+"");
            Picasso.with(MainActivity.this)
                    .load(imgUrl)
                    .resize(800,800)
                    .placeholder(R.drawable.loading)
                    .into(imageView);
        }
        
    }
}
