package winnow.ak.study.thread;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Winyu
 * @Date: 2021/6/26
 */
public class ThreadPoolDownload {

    public static void main(String[] args) {
         final int CORE_SIZE = 2;
         final int MAX_SIZE = 10;
         final int KEEP_ALIVE_TIME = 60;
         final TimeUnit UNIT = TimeUnit.SECONDS;
         final int workQueueCapacity = 100;
         final ArrayBlockingQueue WORK_QUEUE = new ArrayBlockingQueue(workQueueCapacity);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_SIZE, MAX_SIZE, KEEP_ALIVE_TIME, UNIT, WORK_QUEUE);

//        public void executeDownloadTask(Integer downloadTaskId) {
//            String limit = KeyBuilder.withColon(executeCacheKey, String.valueOf(downloadTaskId));
//            catRedisClient.setex(limit, Thread.currentThread().getName(), 300);
//            LogBuilder lb = LogBuilder.create().business("downloadTask").function("execute").setInput(String.valueOf(downloadTaskId));
//            DownloadTask downloadTask = downloadTaskDao.findByTaskId(downloadTaskId);
//            DownloadTask task = new DownloadTask();
//            task.setId(downloadTaskId);
//            DateTime start = new DateTime();
//            String classPath = "classpath:file";
//            String tempFileName = String.format("temp-%s.xlsx", downloadTaskId);
//            File tempFile = null;
//            FileInputStream inputStream = null;
//            try {
//                if (downloadTask == null || TaskStatus.FINISHED.equals(downloadTask.getTaskStatus()) || Status.OFF.equals(downloadTask.getStatus())) {
//                    logger.info(lb.setRemark("not task").build());
//                    return;
//                }
//                boolean containsTask = downloadTaskFactory.containsTask(downloadTask.getBizType());
//                if (!containsTask) {
//                    logger.info(lb.setRemark("not biz").build());
//                    return;
//                }
//                AbstractDownloadTaskService abstractDownloadTaskService = downloadTaskFactory.getBizType(downloadTask.getBizType());
//
//                String path = ResourceUtils.getURL(classPath).getPath();
//                File templateFile = new File(path + File.separator + "业绩归属下载任务模板.xlsx");
//                String tempPath = path + File.separator + tempFileName;
//                tempFile = new File(tempPath);
//                boolean exists = tempFile.exists();
//                if (!exists) {
//                    exists = tempFile.createNewFile();
//                    logger.info(lb.category("createFile").setRemark(String.valueOf(exists)).build());
//                }
//                ExcelWriter excelWriter = EasyExcel.write(tempFile).withTemplate(templateFile).build();
//                WriteSheet writeSheet1 = EasyExcel.writerSheet().build();
//                int pageNo = 1, totalPage = 1;
//                downloadTask.setPageSize(50);
//                task.setTaskStatus(TaskStatus.GENERATING);
//                downloadTaskDao.update(task);
//                logger.info(lb.category("GENERATING").build());
//                //分批获取数据
//                do {
//                    downloadTask.setPageNo(pageNo);
//                    PageInfo<Object> objectPageInfo = abstractDownloadTaskService.pageGetTaskData(downloadTask);
//                    List<Object> list = objectPageInfo.getList();
//                    if (CollectionUtils.isEmpty(list)) {
//                        task.setTaskStatus(TaskStatus.FAILURE);
//                        task.setRemark("无数据");
//                        logger.info(lb.category("taskNotData").setRemark(String.format("pageNo=%s,TotalPage=%s", pageNo, totalPage)).build());
//                        return;
//                    }
//                    //填充至临时文件
//                    excelWriter.fill(list, writeSheet1);
//                    totalPage = objectPageInfo.getTotalPage();
//                    pageNo++;
//                    logger.info(lb.category("taskData").setRemark(String.format("pageNo=%s,TotalPage=%s", pageNo, totalPage)).build());
//                } while (pageNo <= totalPage);
//                logger.info(lb.category("init data finish").build());
//                excelWriter.finish();
//                inputStream = new FileInputStream(tempFile);
//                int fileSize = inputStream.available() / 1000;
//                //上传至服务器
//                String fileUrl = babelUploadHelper.uploadFile(inputStream, tempFileName);
//                if (!fileUrl.endsWith(".xlsx")) {
//                    logger.info(lb.category("fileUpload").setRemark("failed").build());
//                    task.setTaskStatus(TaskStatus.FAILURE);
//                    task.setRemark("上传文件失败");
//                    return;
//                }
//                task.setFileSize(fileSize);
//                task.setFileUrl("https://static.iquanwai.com/" + fileUrl);
//                task.setTaskStatus(TaskStatus.FINISHED);
//                task.setRemark("生成完成");
//                DateTime end = new DateTime();
//                task.setFinishTime(end.toDate());
//                long l = end.getMillis() - start.getMillis();
//                task.setCostMills(l);
//                logger.info(lb.category("costMills").setRemark(String.valueOf(l)).build());
//            } catch (Exception e) {
//                task.setTaskStatus(TaskStatus.FAILURE);
//                task.setRemark("数据异常");
//                logger.error(lb.setThrowable(e).category("exception").build(), e);
//                e.printStackTrace();
//            } finally {
//                uniSender.send(() -> QwEvent.create("download_task_execute", "mgmtDownloadTask", EventTypeEnum.trade, JSON.parseObject(JSONObject.toJSONString(downloadTask))));
//                if (inputStream != null) {
//                    try {
//                        inputStream.close();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                //删除临时文件
//                if (tempFile != null && tempFile.exists()) {
//                    boolean delete = tempFile.delete();
//                    logger.info(lb.setRemark("delete").build());
//                }
//                // 更新状态
//                if (task.getTaskStatus() != null) {
//                    int update = downloadTaskDao.update(task);
//                    logger.info(lb.setRemark("update downloadTask").setResult(String.valueOf(update)).setRemark(task.toString()).build());
//                }
//                // 任务删除
//                if (TaskStatus.FINISHED.equals(task.getTaskStatus())) {
//                    catRedisClient.lrem(cacheKey, 0, String.valueOf(task.getId()));
//                    logger.info(lb.setRemark("finish").build());
//                }
//
//                if (TaskStatus.FAILURE.equals(task.getTaskStatus()) && "无数据".equals(task.getRemark())) {
//                    catRedisClient.lrem(cacheKey, 0, String.valueOf(task.getId()));
//                    logger.info(lb.setRemark("failed delete").build());
//                }
//                catRedisClient.delete(limit);
//            }
//        }
    }

}
