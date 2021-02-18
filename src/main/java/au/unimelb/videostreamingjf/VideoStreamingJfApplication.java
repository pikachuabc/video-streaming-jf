package au.unimelb.videostreamingjf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class VideoStreamingJfApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoStreamingJfApplication.class, args);
    }

}
