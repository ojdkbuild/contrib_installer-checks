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

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Common {
    static void install(String label, String... options) throws Exception {
        ArrayList<String> cline = new ArrayList<>(Arrays.asList(
            System.getenv("WINDIR") + "/system32/msiexec.exe",
            "/q",
            "/i",
            System.getenv("TESTJDK_MSI_PATH"),
            "/l*v",
            label + "_install.log",
            "INSTALLDIR=" + Paths.get("jdk").toAbsolutePath().toString()
        ));
        System.out.println(cline.getClass());
        cline.addAll(Arrays.asList(options));

        System.out.println("Spawning install process, command line: [" + cline + "]");
        int code = new ProcessBuilder(cline).inheritIO().start().waitFor();

        if (0 != code) {
            throw new Exception("Install failure, code: [" + code + "]");
        }
    }

    static void uninstall(String label) throws Exception {
        List<String> cline = Arrays.asList(
            System.getenv("WINDIR") + "/system32/msiexec.exe",
            "/q",
            "/x",
            System.getenv("TESTJDK_MSI_PATH"),
            "/l*v",
            label + "_uninstall.log"
        );

        System.out.println("Spawning uninstall process, command line: [" + cline + "]");
        int code = new ProcessBuilder(cline).inheritIO().start().waitFor();

        if (0 != code) {
            throw new Exception("Uninstall failure, code: [" + code + "]");
        }
    }

}
