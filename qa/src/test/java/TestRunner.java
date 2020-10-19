import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.core.ExecutionContext;
import com.intuit.karate.core.ExecutionHook;
import com.intuit.karate.core.Feature;
import com.intuit.karate.core.FeatureResult;
import com.intuit.karate.core.PerfEvent;
import com.intuit.karate.core.Scenario;
import com.intuit.karate.core.ScenarioContext;
import com.intuit.karate.core.ScenarioResult;
import com.intuit.karate.core.Step;
import com.intuit.karate.core.StepResult;
import com.intuit.karate.http.HttpRequestBuilder;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.sorting.SortingMethod;

public class TestRunner {
    @Test
    public void testParallel() throws Exception {
        String retryPath = System.getProperty("user.dir") + "/target/test-classes/features";
        int retryCount = 3;
        // Run parallelly and retry according to retry count
        Results result = Runner.path("classpath:features").hook(new KarateExecutionHook()).tags("~@ignore")
                .parallel(10);

        if (result.getFailCount() > 0) {
            for (int i = 0; i < retryCount; i++) {
                PrintWriter pw = new PrintWriter(System.getProperty("user.dir") + "/target/karate.log");
                pw.close();
                System.out.println("====Retrying test====");
                result = Runner.path(retryPath).hook(new KarateExecutionHook()).tags("@retry","~@ignore").parallel(10);
                if(result.getFailCount() == 0)
                break;
            }
        }
        Assert.assertTrue(result.getErrorMessages(), result.getFailCount() == 0);
    }

    public static void generateReport(String karateOutputPath) throws Exception {
        Collection<File> jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[] { "json" }, true);
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(new File("target"), "karate-graphql");
        config.setSortingMethod(SortingMethod.ALPHABETICAL);
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }
}

class KarateExecutionHook implements ExecutionHook {

    @Override
    public boolean beforeScenario(Scenario scenario, ScenarioContext context) {
        return true;
    }

    @Override
    public void afterScenario(ScenarioResult result, ScenarioContext context) {
        File f = null;
        f = context.rootFeatureContext.feature.getPath().toFile();
        String filePath = f.toString();
        int line = 0;
        Path path = Paths.get(filePath);

        if (result.isFailed()) {
            try {
                String annotation = Files.readAllLines(path).get(line);
                if (!annotation.contains("@retry")) {
                    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
                    lines.add(line, "@retry");
                    Files.write(path, lines, StandardCharsets.UTF_8);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Charset charset = StandardCharsets.UTF_8;
                String content = new String(Files.readAllBytes(path), charset);
                String pattern = "@retry";
                content = content.replaceAll(pattern, "");
                Files.write(path, content.getBytes(charset));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean beforeFeature(Feature feature, ExecutionContext context) {
        return true;
    }

    @Override
    public void afterFeature(FeatureResult result, ExecutionContext context) {
    }

    @Override
    public void beforeAll(Results results) {
    }

    @Override
    public void afterAll(Results results) {
    }

    @Override
    public boolean beforeStep(Step step, ScenarioContext context) {
        if (step.getText().trim().contains("request {")) {
            try {
                File dir = new File("GQL-Requests");
                dir.mkdirs();
                String rawQuery = context.vars.get("query").getValue().toString();
                String[] splittedQuery = rawQuery.split("\n", 2);
                String firstLineQuery = splittedQuery[0];
                String queryName = firstLineQuery.replace("query", "").replace("mutation", "")
                        .replaceAll("\\(.*?\\{", "").replaceAll(" ", "") + ".graphql";
                File file = new File(dir, queryName);
                FileWriter myWriter = new FileWriter(file);
                myWriter.write(context.vars.get("query").getValue().toString());
                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public void afterStep(StepResult result, ScenarioContext context) {
    }

    @Override
    public String getPerfEventName(HttpRequestBuilder req, ScenarioContext context) {
        return null;
    }

    @Override
    public void reportPerfEvent(PerfEvent event) {
    }
}
