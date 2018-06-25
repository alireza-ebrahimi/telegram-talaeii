package com.google.gms.googleservices;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.thin.downloadmanager.BuildConfig;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

public class GoogleServicesTask extends DefaultTask {
    private static final Pattern GOOGLE_APP_ID_REGEX = Pattern.compile("(\\d+):(\\d+):(\\p{Alnum}+):(\\p{XDigit}+)");
    private static final String GOOGLE_APP_ID_VERSION = "1";
    private static final String OAUTH_CLIENT_TYPE_WEB = "3";
    private static final String STATUS_DISABLED = "1";
    private static final String STATUS_ENABLED = "2";
    @OutputDirectory
    public File intermediateDir;
    @Input
    public String moduleGroup;
    @Input
    public String moduleGroupFirebase;
    @Input
    public String moduleVersion;
    @Input
    public String packageName;
    @InputFile
    @Optional
    public File quickstartFile;
    @Input
    public String searchedLocation;

    @TaskAction
    public void action() throws IOException {
        checkVersionConflict();
        if (this.quickstartFile.isFile()) {
            getProject().getLogger().warn("Parsing json file: " + this.quickstartFile.getPath());
            deleteFolder(this.intermediateDir);
            if (this.intermediateDir.mkdirs()) {
                JsonElement root = new JsonParser().parse(Files.newReader(this.quickstartFile, Charsets.UTF_8));
                if (root.isJsonObject()) {
                    JsonObject rootObject = root.getAsJsonObject();
                    Map<String, String> resValues = new TreeMap();
                    Map<String, Map<String, String>> resAttributes = new TreeMap();
                    handleProjectNumberAndProjectId(rootObject, resValues);
                    handleFirebaseUrl(rootObject, resValues);
                    JsonObject clientObject = getClientForPackageName(rootObject);
                    if (clientObject != null) {
                        handleAnalytics(clientObject, resValues);
                        handleMapsService(clientObject, resValues);
                        handleGoogleApiKey(clientObject, resValues);
                        handleGoogleAppId(clientObject, resValues);
                        handleWebClientId(clientObject, resValues);
                        File values = new File(this.intermediateDir, "values");
                        if (values.exists() || values.mkdirs()) {
                            Files.write(getValuesContent(resValues, resAttributes), new File(values, "values.xml"), Charsets.UTF_8);
                            return;
                        }
                        throw new GradleException("Failed to create folder: " + values);
                    }
                    throw new GradleException("No matching client found for package name '" + this.packageName + "'");
                }
                throw new GradleException("Malformed root json");
            }
            throw new GradleException("Failed to create folder: " + this.intermediateDir);
        }
        throw new GradleException(String.format("File %s is missing. The Google Services Plugin cannot function without it. %n Searched Location: %s", new Object[]{this.quickstartFile.getName(), this.searchedLocation}));
    }

    private void checkVersionConflict() {
        Project project = getProject();
        ConfigurationContainer<Configuration> configurations = project.getConfigurations();
        if (configurations != null) {
            boolean hasConflict = false;
            for (Configuration configuration : configurations) {
                if (configuration != null) {
                    DependencySet<Dependency> dependencies = configuration.getDependencies();
                    if (dependencies != null) {
                        for (Dependency dependency : dependencies) {
                            if (!(dependency == null || dependency.getGroup() == null || dependency.getVersion() == null)) {
                                String dependencyGroup = dependency.getGroup();
                                String dependencyVersion = dependency.getVersion();
                                if ((dependencyGroup.equals(this.moduleGroup) || dependencyGroup.equals(this.moduleGroupFirebase)) && !dependencyVersion.equals(this.moduleVersion)) {
                                    hasConflict = true;
                                    project.getLogger().warn("Found " + dependencyGroup + ":" + dependency.getName() + ":" + dependencyVersion + ", but version " + this.moduleVersion + " is needed for the google-services plugin.");
                                }
                            }
                        }
                    }
                }
            }
            if (hasConflict) {
                throw new GradleException("Please fix the version conflict either by updating the version of the google-services plugin (information about the latest version is available at https://bintray.com/android/android-tools/com.google.gms.google-services/) or updating the version of " + this.moduleGroup + " to " + this.moduleVersion + ".");
            }
        }
    }

    private void handleFirebaseUrl(JsonObject rootObject, Map<String, String> resValues) throws IOException {
        JsonObject projectInfo = rootObject.getAsJsonObject("project_info");
        if (projectInfo == null) {
            throw new GradleException("Missing project_info object");
        }
        JsonPrimitive firebaseUrl = projectInfo.getAsJsonPrimitive("firebase_url");
        if (firebaseUrl != null) {
            resValues.put("firebase_database_url", firebaseUrl.getAsString());
        }
    }

    private void handleProjectNumberAndProjectId(JsonObject rootObject, Map<String, String> resValues) throws IOException {
        JsonObject projectInfo = rootObject.getAsJsonObject("project_info");
        if (projectInfo == null) {
            throw new GradleException("Missing project_info object");
        }
        JsonPrimitive projectNumber = projectInfo.getAsJsonPrimitive("project_number");
        if (projectNumber == null) {
            throw new GradleException("Missing project_info/project_number object");
        }
        resValues.put("gcm_defaultSenderId", projectNumber.getAsString());
        JsonPrimitive projectId = projectInfo.getAsJsonPrimitive("project_id");
        if (projectId == null) {
            throw new GradleException("Missing project_info/project_id object");
        }
        resValues.put("project_id", projectId.getAsString());
        JsonPrimitive bucketName = projectInfo.getAsJsonPrimitive("storage_bucket");
        if (bucketName != null) {
            resValues.put("google_storage_bucket", bucketName.getAsString());
        }
    }

    private void handleWebClientId(JsonObject clientObject, Map<String, String> resValues) {
        JsonArray array = clientObject.getAsJsonArray("oauth_client");
        if (array != null) {
            int count = array.size();
            for (int i = 0; i < count; i++) {
                JsonElement oauthClientElement = array.get(i);
                if (oauthClientElement != null && oauthClientElement.isJsonObject()) {
                    JsonObject oauthClientObject = oauthClientElement.getAsJsonObject();
                    JsonPrimitive clientType = oauthClientObject.getAsJsonPrimitive("client_type");
                    if (clientType != null) {
                        if (OAUTH_CLIENT_TYPE_WEB.equals(clientType.getAsString())) {
                            JsonPrimitive clientId = oauthClientObject.getAsJsonPrimitive("client_id");
                            if (clientId != null) {
                                resValues.put("default_web_client_id", clientId.getAsString());
                                return;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    private void handleAnalytics(JsonObject clientObject, Map<String, String> resValues) throws IOException {
        JsonObject analyticsService = getServiceByName(clientObject, "analytics_service");
        if (analyticsService != null) {
            JsonObject analyticsProp = analyticsService.getAsJsonObject("analytics_property");
            if (analyticsProp != null) {
                JsonPrimitive trackingId = analyticsProp.getAsJsonPrimitive("tracking_id");
                if (trackingId != null) {
                    resValues.put("ga_trackingId", trackingId.getAsString());
                    File xml = new File(this.intermediateDir, "xml");
                    if (xml.exists() || xml.mkdirs()) {
                        Files.write(getGlobalTrackerContent(trackingId.getAsString()), new File(xml, "global_tracker.xml"), Charsets.UTF_8);
                        return;
                    }
                    throw new GradleException("Failed to create folder: " + xml);
                }
            }
        }
    }

    private void handleMapsService(JsonObject clientObject, Map<String, String> resValues) throws IOException {
        if (getServiceByName(clientObject, "maps_service") != null) {
            String apiKey = getAndroidApiKey(clientObject);
            if (apiKey != null) {
                resValues.put("google_maps_key", apiKey);
                return;
            }
            throw new GradleException("Missing api_key/current_key object");
        }
    }

    private void handleGoogleApiKey(JsonObject clientObject, Map<String, String> resValues) {
        String apiKey = getAndroidApiKey(clientObject);
        if (apiKey != null) {
            resValues.put("google_api_key", apiKey);
            resValues.put("google_crash_reporting_api_key", apiKey);
            return;
        }
        throw new GradleException("Missing api_key/current_key object");
    }

    private String getAndroidApiKey(JsonObject clientObject) {
        JsonArray array = clientObject.getAsJsonArray("api_key");
        if (array != null) {
            int count = array.size();
            for (int i = 0; i < count; i++) {
                JsonElement apiKeyElement = array.get(i);
                if (apiKeyElement != null && apiKeyElement.isJsonObject()) {
                    JsonPrimitive currentKey = apiKeyElement.getAsJsonObject().getAsJsonPrimitive("current_key");
                    if (currentKey != null) {
                        return currentKey.getAsString();
                    }
                }
            }
        }
        return null;
    }

    private static void findStringByName(JsonObject jsonObject, String stringName, Map<String, String> resValues) {
        JsonPrimitive id = jsonObject.getAsJsonPrimitive(stringName);
        if (id != null) {
            resValues.put(stringName, id.getAsString());
        }
    }

    private JsonObject getClientForPackageName(JsonObject jsonObject) {
        JsonArray array = jsonObject.getAsJsonArray("client");
        if (array != null) {
            int count = array.size();
            for (int i = 0; i < count; i++) {
                JsonElement clientElement = array.get(i);
                if (clientElement != null && clientElement.isJsonObject()) {
                    JsonObject clientObject = clientElement.getAsJsonObject();
                    JsonObject clientInfo = clientObject.getAsJsonObject("client_info");
                    if (clientInfo != null) {
                        JsonObject androidClientInfo = clientInfo.getAsJsonObject("android_client_info");
                        if (androidClientInfo != null) {
                            JsonPrimitive clientPackageName = androidClientInfo.getAsJsonPrimitive("package_name");
                            if (clientPackageName != null && this.packageName.equals(clientPackageName.getAsString())) {
                                return clientObject;
                            }
                        }
                        continue;
                    } else {
                        continue;
                    }
                }
            }
        }
        return null;
    }

    private void handleGoogleAppId(JsonObject clientObject, Map<String, String> resValues) throws IOException {
        JsonObject clientInfo = clientObject.getAsJsonObject("client_info");
        if (clientInfo == null) {
            throw new GradleException("Client does not have client info");
        }
        JsonPrimitive googleAppId = clientInfo.getAsJsonPrimitive("mobilesdk_app_id");
        String googleAppIdStr = googleAppId == null ? null : googleAppId.getAsString();
        if (Strings.isNullOrEmpty(googleAppIdStr)) {
            throw new GradleException("Missing Google App Id. Please follow instructions on https://firebase.google.com/ to get a valid config file that contains a Google App Id");
        }
        Matcher matcher = GOOGLE_APP_ID_REGEX.matcher(googleAppIdStr);
        if (matcher.matches()) {
            if (BuildConfig.VERSION_NAME.equals(matcher.group(1))) {
                String platform = matcher.group(3);
                if (platform.equals(AbstractSpiCall.ANDROID_CLIENT_TYPE)) {
                    resValues.put("google_app_id", googleAppIdStr);
                    return;
                }
                throw new GradleException("Expect Google App Id for Android App, but get " + platform);
            }
            throw new GradleException("Google App Id Version is incompatible with this plugin. Please update the plugin version.");
        }
        throw new GradleException("Unexpected format of Google App ID. Please follow instructions on https://firebase.google.com/ to get a config file that contains a valid Google App Id or update the plugin version if you believe your Google App Id [" + googleAppIdStr + "] is correct.");
    }

    private JsonObject getServiceByName(JsonObject clientObject, String serviceName) {
        JsonObject services = clientObject.getAsJsonObject("services");
        if (services == null) {
            return null;
        }
        JsonObject service = services.getAsJsonObject(serviceName);
        if (service == null) {
            return null;
        }
        JsonPrimitive status = service.getAsJsonPrimitive("status");
        if (status == null) {
            return null;
        }
        String statusStr = status.getAsString();
        if (BuildConfig.VERSION_NAME.equals(statusStr)) {
            return null;
        }
        if (STATUS_ENABLED.equals(statusStr)) {
            return service;
        }
        getLogger().warn(String.format("Status with value '%1$s' for service '%2$s' is unknown", new Object[]{statusStr, serviceName}));
        return null;
    }

    private static String getGlobalTrackerContent(String ga_trackingId) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n    <string name=\"ga_trackingId\" translatable=\"false\">" + ga_trackingId + "</string>\n</resources>\n";
    }

    private static String getValuesContent(Map<String, String> values, Map<String, Map<String, String>> attributes) {
        StringBuilder sb = new StringBuilder(256);
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");
        for (Entry<String, String> entry : values.entrySet()) {
            String name = (String) entry.getKey();
            sb.append("    <string name=\"").append(name).append("\" translatable=\"false\"");
            if (attributes.containsKey(name)) {
                for (Entry<String, String> attr : ((Map) attributes.get(name)).entrySet()) {
                    sb.append(" ").append((String) attr.getKey()).append("=\"").append((String) attr.getValue()).append("\"");
                }
            }
            sb.append(">").append((String) entry.getValue()).append("</string>\n");
        }
        sb.append("</resources>\n");
        return sb.toString();
    }

    private static void deleteFolder(File folder) {
        if (folder.exists()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteFolder(file);
                    } else if (!file.delete()) {
                        throw new GradleException("Failed to delete: " + file);
                    }
                }
            }
            if (!folder.delete()) {
                throw new GradleException("Failed to delete: " + folder);
            }
        }
    }
}
