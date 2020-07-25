import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.sorting.SortingMethod;
import net.masterthought.cucumber.Configuration;

public class TestRunner {

    @Test
    public void testParallel() throws Exception {
        Results result = Runner.path("classpath:features")
                .hook(new KarateExecutionHook()).tags("~@ignore,@smoke")
                .parallel(10);
        generateReport(result.getReportDir());
        Assert.assertTrue(result.getErrorMessages(), result.getFailCount() == 0);
    }

    public static void generateReport(String karateOutputPath) throws Exception {
        Collection<File> jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[] { "json" }, true);
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(new File("target"), "qa");
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
                File dir = new File("GQL-Queries");
                dir.mkdirs();
                String rawQuery = context.vars.get("query").getValue().toString();
                String[] splittedQuery = rawQuery.split("\n", 2);
                String firstLineQuery = splittedQuery[0];
                String queryName = firstLineQuery.replace("query","").replace("mutation", "").replaceAll("\\(.*?\\{", "").replaceAll(" ","").replace("}","").replace("{","")+ ".graphql";
                System.out.println(queryName);
                System.out.println(context.vars.get("query").getValue().toString());
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
