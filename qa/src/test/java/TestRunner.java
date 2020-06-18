import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.intuit.karate.Results;
import com.intuit.karate.Runner;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.sorting.SortingMethod;
import net.masterthought.cucumber.Configuration;

public class TestRunner {

    @Test
    public void testParallel() throws Exception {
        Results results = Runner.parallel(getClass(), 20);
        generateReport(results.getReportDir());
        Assert.assertTrue(results.getErrorMessages(), results.getFailCount() == 0);
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
