package runner;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JenkinsUtils {

    public static void main(String[] args) {
        deleteJobs();
    }

    private static final HttpClient client = HttpClient.newBuilder().build();

    private static String sessionId;

    private static String getCrumbFromPage(String page) {
        final String CRUMB_TAG = "data-crumb-value=\"";

        int crumbTagBeginIndex = page.indexOf(CRUMB_TAG) + CRUMB_TAG.length();
        int crumbTagEndIndex = page.indexOf('"', crumbTagBeginIndex);

        return page.substring(crumbTagBeginIndex, crumbTagEndIndex);
    }

    private static Set<String> getJobsFromPage(String page) {
        final String JOB_TAG = "href=\"job/";
        Set<String> result = new HashSet<>();

        int beginIndex = page.indexOf(JOB_TAG);
        while (beginIndex != -1) {
            int endIndex = page.indexOf('/', beginIndex + JOB_TAG.length());
            result.add(page.substring(beginIndex + JOB_TAG.length(), endIndex));
            beginIndex = page.indexOf(JOB_TAG, endIndex);
        }

        return result;
    }

    private static String[] getHeader() {
        List<String> result = new ArrayList<>(List.of("Content-Type", "application/x-www-form-urlencoded"));
        if (sessionId != null) {
            result.add("Cookie");
            result.add(sessionId);
        }

        return result.toArray(String[]::new);
    }

    private static HttpResponse<String> getHttp(String url) {
        try {
            return client.send(
                    HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .headers(getHeader())
                            .GET()
                            .build(),
                    HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpResponse<String> postHttp(String url, String body) {
        try {
            return client.send(
                    HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .headers(getHeader())
                            .POST(HttpRequest.BodyPublishers.ofString(body))
                            .build(),
                    HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getMainPage(String user, String password) {
        HttpResponse<String> mainPage = getHttp(ProjectUtils.getUrl());
        if (mainPage.statusCode() != 200) {
            final String HEAD_COOKIE = "set-cookie";

            HttpResponse<String> loginPage = getHttp(ProjectUtils.getUrl() + "login?from=%2F");
            sessionId = loginPage.headers().firstValue(HEAD_COOKIE).orElse(null);

            HttpResponse<String> indexPage = postHttp(ProjectUtils.getUrl() + "j_spring_security_check",
                    String.format("j_username=%s&j_password=%s&from=%%2F&Submit=", user, password));
            sessionId = indexPage.headers().firstValue(HEAD_COOKIE).orElse("");

            mainPage = getHttp(ProjectUtils.getUrl());
        }

        return mainPage.body();
    }

    public static void deleteJobs() {
        String mainPage = getMainPage(ProjectUtils.getUserName(), ProjectUtils.getPassword());
        String crumb = getCrumbFromPage(mainPage);

        for (String jobName : getJobsFromPage(mainPage)) {
            postHttp(String.format(ProjectUtils.getUrl() + "job/%s/doDelete", jobName),
                    String.format("Jenkins-Crumb=%s", crumb));
        }
    }
}
