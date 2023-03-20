import com.bondtech.poc.retroift.interceptors.AuthenticationInterceptor;
import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.OpenAiResponse;
import com.theokanning.openai.model.Model;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ChatGPTAPIClient {

    public static void main(String[] args) {

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(
                new AuthenticationInterceptor("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"))
                .build();

        Retrofit retrofit = new Retrofit
                .Builder()
                .client(client)
                .baseUrl("https://api.openai.com")
                .addConverterFactory(GsonConverterFactory.create())
                //.addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        OpenAiApi openAiApi=retrofit.create(OpenAiApi.class);
        Single<OpenAiResponse<Model>> result = openAiApi.listModels();
        Disposable disposable = result.subscribeWith(new DisposableSingleObserver<OpenAiResponse<Model>>() {

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onSuccess(OpenAiResponse<Model> value) {
                System.out.println(value);
            }
        });
       disposable.dispose();
        System.out.println("end of programme");

        //GitHubService service = retrofit.create(GitHubService.class);
    }

}
