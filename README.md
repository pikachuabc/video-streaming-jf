# 在线视频播放(mp4) v1.1 
***
基于springboot，servlet一套最基本的视频流在线播放后端 `FileSystemResources`
帮助完成填充 `206 response`以及`content-Range`， 
避免了采用`StreamingBody`或者`byte[]`需要自己写分段请求头以及视频流中产生的`IOException`)
***
使用说明

修改`src/main/resources/application.properties`中的`custom.path`，改成你的视频存放的位置根目录即可，如果是多级目录请自行修改
videoServiceImp中的Files.walk执行DFS的最大深度 

如果没有前端跑起来后访问localhost:8082/file/allVideo

Api: 
- Get /file/allVideo： 返回目录下最大深度为3的所有视频
- Get /file/videoFolderList: 返回根目录下所有目录
- Get /file/episodeList?__folderName__= XXX: 返回指定目录下的所有视频
- Get /file/streamVideoByName?__folderName__=XXX&__fileName__=XX: 在线播放指定视频
- Get /file/downloadVideoByName?__folderName__=XXX&__fileName__=XX: 下载指定视频
***
# Online video streaming for mp4
***
based on springboot and servlet (we still have webflux as option for a better performance since
this is roughly an IO intense task). `FileSystemResources` automatically handle
 request with `content-range` and gives `206 response` to achieve dragging function when it handles the video
resources. Alternatively, `StreamingBody` or `byte[]` is an option as return type, but you have to fill 
the response headers (`content-range,content-length,accept-typt`) and handle `IOException` during the transmission 
which I don't know exactly why)
***
Usage 

change`custom.path` in `src/main/resources/application.properties`to your actual movie dictionary.
Note this has a maximum 3 depth, if you wish to have a deep tree structure, go edit `Files.walk`in`videoServiceImp.java`

localhost:8082/file/allVideo

Api:
- Get /file/allVideo： get all video from root path
- Get /file/videoFolderList: get all folder from root path
- Get /file/episodeList?__folderName__= XXX: get all video info from given folder
- Get /file/streamVideoByName?__folderName__=XXX&__fileName__=XX: streaming video
- Get /file/downloadVideoByName?__folderName__=XXX&__fileName__=XX: download video
