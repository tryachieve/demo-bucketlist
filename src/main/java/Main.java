import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner accessKeyObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter accessKey:");

        String accessKey = accessKeyObj.nextLine();  // Read user input
        System.out.println("accessKey is: " + accessKey);  // Output user input

        System.out.println("Enter secretKey:");


        String secretKey1 = readPassword(" ");

        BasicAWSCredentials awsCred2s = new BasicAWSCredentials(accessKey, String.valueOf(secretKey1));
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion("Regions.US_EAST_1")
                .withCredentials(new AWSStaticCredentialsProvider(awsCred2s))
                .build();

        List<com.amazonaws.services.s3.model.Bucket> buckets = s3Client.listBuckets();
        System.out.println("Your Amazon S3 buckets are:");
        for (Bucket b : buckets) {
            System.out.println("* name => " + b.getName() + ", creation time => " + b.getCreationDate());
        }

    }

    public static String readPassword(String prompt) {
        EraserThread et = new EraserThread(prompt);
        Thread mask = new Thread(et);
        mask.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String password = "";

        try {
            password = in.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        // stop masking
        et.stopMasking();
        // return the password entered by the user
        return password;
    }

}
