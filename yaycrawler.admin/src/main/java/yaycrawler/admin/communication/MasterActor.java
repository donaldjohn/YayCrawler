package yaycrawler.admin.communication;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import yaycrawler.common.model.CommunicationAPIs;
import yaycrawler.common.model.CrawlerRequest;
import yaycrawler.common.model.RestFulResult;
import yaycrawler.common.utils.HttpUtils;

/**
 * Created by ucs_yuananyun on 2016/5/13.
 */
@Component
public class MasterActor {
    @Value("${master.server.address}")
    private String masterServerAddress;

    /**
     * Admin向Master发送任务
     *
     * @return
     */
    public boolean publishTasks(CrawlerRequest ... crawlerRequests) {
        String targetUrl = CommunicationAPIs.getFullRemoteUrl(masterServerAddress, CommunicationAPIs.ADMIN_POST_MASTER_TASK_REGEDIT);
        RestFulResult result = HttpUtils.doSignedHttpExecute(targetUrl, HttpMethod.POST, crawlerRequests);
        return result != null && !result.hasError();
    }

    public String retrievedWorkerRegistrations()
    {
        String targetUrl = CommunicationAPIs.getFullRemoteUrl(masterServerAddress, CommunicationAPIs.ADMIN_POST_MASTER_RETRIVED_WORKERS);
        RestFulResult result = HttpUtils.doSignedHttpExecute(targetUrl, HttpMethod.POST, null);
        return JSON.toJSONString(result.getData());
    }

}