package cycleshop.com.testofkhan;

public class UploadModel {

    private boolean success;
    private String message;

    @Override
    public String toString() {
        return "UploadModel{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }

    public UploadModel(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
