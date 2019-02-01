package org.wso2.ei.config;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Paths;

public class ConfigParserTest {

    @Test(dataProvider = "scenarios")
    public void getTestParseConfig(String scenario) throws IOException, ValidationException {

        String deploymentConfiguration = FileUtils.getFile("src", "test", "resources", scenario, "deployment" +
                ".toml").getAbsolutePath();
        String inferConfiguration =
                FileUtils.getFile("src", "test", "resources", scenario, "infer.json").getAbsolutePath();
        String mappingConfiguration =
                FileUtils.getFile("src", "test", "resources", scenario, "key-mappings.toml").getAbsolutePath();
        String templateConfiguration =
                FileUtils.getFile("src", "test", "resources", scenario, "template").getAbsolutePath();
        String validatorConfiguration =
                FileUtils.getFile("src", "test", "resources", scenario, "validator.json").getAbsolutePath();
        String result =
                FileUtils.getFile("src", "test", "resources", scenario, "result").getAbsolutePath();

        ConfigParser configParser = new ConfigParser.ConfigParserBuilder()
                .withDeploymentConfigurationPath(deploymentConfiguration)
                .withInferConfigurationFilePath(inferConfiguration)
                .withMappingFilePath(mappingConfiguration)
                .withValidatorFilePath(validatorConfiguration)
                .withTemplateFilePath(templateConfiguration)
                .build();
        String output = configParser.parse();
        String actual = Files.toString(Paths.get(result).toFile(), Charsets.UTF_8);
        Assert.assertEquals(output, actual);
    }

    @DataProvider(name = "scenarios")
    public Object[] scenarios() {

        return new Object[]{"scenario-1", "scenario-3"};
    }
}
