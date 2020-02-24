/*
 * Copyright 2019, akashche at redhat.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package support;

import java.nio.file.Path;
import java.nio.file.Paths;

import static support.Registry.REGISTRY_ENV_PATH;
import static support.Registry.queryRegistry;

public class AppData {

    public static Path findOutAppDataDir(String msiPath) throws Exception {
        //String appData = queryRegistry(REGISTRY_ENV_PATH, "LOCALAPPDATA").get();
        String appData = "c:/Users/" + System.getProperty("user.name") + "/AppData/Local";
        String vendor = msiPath.contains("redhat") ? "RedHat" : "ojdkbuild";
        String msiFile = Paths.get(msiPath).getFileName().toString();
        String dir = msiFile.substring(0, msiFile.length() - 4);
        return Paths.get(appData + "/" + vendor  + "/" + dir);
    }
}
