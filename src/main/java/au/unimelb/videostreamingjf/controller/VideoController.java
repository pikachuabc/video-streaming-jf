package au.unimelb.videostreamingjf.controller;

import au.unimelb.videostreamingjf.domain.VideoFileInfo;
import au.unimelb.videostreamingjf.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/file")
public class VideoController {

    @Autowired
    VideoService videoService;

    /**
     * return a raw html for browser, if you wish to have a restful response, eg, list of urls,
     * change this return type to List<VideoFileInfo> and return fileInfos will do the trick.
     */
    @GetMapping(value = "/videoList")
    public String getListVideoInfo(Model model) {

        try {
            List<VideoFileInfo> fileInfos = videoService.loadAll().map(path -> {
                String fileName = path.toString();
                String url = MvcUriComponentsBuilder.fromMethodName(VideoController.class, "getVideoByName", path.toString()).build().toString();
                return new VideoFileInfo(fileName, url);
            }).collect(Collectors.toList());

            model.addAttribute("fileInfos",fileInfos);

        } catch (IOException e) {
           e.printStackTrace();
        }

        return "videoList";


    }


    /**
     * get single video file from server
     * @param fileName name of the video file
     * @return resource for browser
     */
    @GetMapping("/videoList/{fileName:.+}")
    public @ResponseBody
    ResponseEntity<FileSystemResource> getVideoByName(@PathVariable String fileName) {
        return ResponseEntity.ok().header("Content-Type", "video/mp4").body(videoService.loadResource(fileName));
    }

}