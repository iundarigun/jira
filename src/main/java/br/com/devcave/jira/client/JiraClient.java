package br.com.devcave.jira.client;

import br.com.devcave.jira.vo.IssueRequest;
import br.com.devcave.jira.vo.IssueResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "issue-client", url = "${jira.url}")
public interface JiraClient {

    @PostMapping("/rest/api/2/issue")
    IssueResponse createIssue(@RequestHeader("Authorization") String token, @RequestBody IssueRequest issueRequest);

}
