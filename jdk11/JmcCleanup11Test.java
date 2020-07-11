/*
 * Copyright 2020, akashche at redhat.com
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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Collections.singletonList;
import static support.AppData.findOutAppDataDir;
import static support.Assert.*;
import static support.IO.deleteDirectory;
import static support.Install.TESTJDK_MSI_PATH;
import static support.Install.install;
import static support.Uninstall.uninstall;

/**
 * @test
 * @library ..
 */

public class JmcCleanup11Test {

    public static void main(String[] args) throws Exception {
        Path scratchDir = Paths.get("").toAbsolutePath();
        String msiPath = System.getenv(TESTJDK_MSI_PATH);
        Path appDataDir = findOutAppDataDir(msiPath);

        // cleanup
        if (Files.exists(appDataDir)) {
            deleteDirectory(appDataDir);
        }
        assertNoPath(appDataDir.toString());

        // install
        install("ADDLOCAL=jdk,jmc");
        try {

            assertPath("jdk/missioncontrol");
            assertPath("jdk/missioncontrol/jmc.exe");

            // spawn jmc to populate appdata
            Process proc = new ProcessBuilder(scratchDir + "/jdk/missioncontrol/jmc.exe").inheritIO().start();
            for (int i = 0; i < 32; i++) {
                Thread.sleep(1000);
                if (Files.exists(Paths.get(appDataDir.toString() + "/missioncontrol/.metadata"))) {
                    break;
                }
            }
            proc.destroyForcibly().waitFor();
            assertThat("Mission Control process killed", !proc.isAlive());

            // check dirs created
            assertPath(appDataDir.toString());
            assertPath(appDataDir.toString() + "/missioncontrol");
            assertPath(appDataDir.toString() + "/missioncontrol/.metadata");

            // uninstall
            uninstall();

            // check cleanup performed
            assertNoPath(appDataDir.toString() + "/missioncontrol");
            assertNoPath(appDataDir.toString());
            assertNoPath("jdk/missioncontrol");
            assertNoPath("jdk");

        } catch (Exception e) {
            uninstall();
        }
    }
}
