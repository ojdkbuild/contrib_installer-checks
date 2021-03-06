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

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static support.Format.formatCommandLine;

public class Install {

    public static String TESTJDK_MSI_PATH = "TESTJDK_MSI_PATH";
    public static String TESTJDK_PREV_MSI_PATH = "TESTJDK_PREV_MSI_PATH";

    public static void install(String... options) throws Exception {
        String msiPath = System.getenv(TESTJDK_MSI_PATH);
        if (null == msiPath) {
            throw new Exception("'" + TESTJDK_MSI_PATH + "' environment variable must be specified");
        }
        install(msiPath, Arrays.asList(options));
    }

    public static void install(String msiPath, List<String> options) throws Exception {
        ArrayList<String> cline = new ArrayList<>(Arrays.asList(
                System.getenv("WINDIR") + "/system32/msiexec.exe",
                "/q",
                "/i",
                msiPath,
                "/l*v",
                "install.log",
                "INSTALLDIR=" + Paths.get("jdk").toAbsolutePath().toString()
        ));
        cline.addAll(options);

        System.out.println("Spawning install process, command line: [" + formatCommandLine(cline) + "]");
        int code = new ProcessBuilder(cline)
                .inheritIO()
                .start()
                .waitFor();

        if (0 != code) {
            throw new Exception("Install failure, code: [" + code + "]");
        }
    }
}
