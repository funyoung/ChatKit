package phos.fri.aiassistant.net;


import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import phos.fri.aiassistant.entity.ApiResponse;
import phos.fri.aiassistant.entity.OcrData;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author e13310@gmail.com
 */
public interface UploadService {
    @Multipart
    @POST("ai/api/ocr2/parse")
    Observable<ResponseBody> uploadFile(
            @Part MultipartBody.Part file
    );
}